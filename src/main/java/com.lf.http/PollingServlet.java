package com.lf.http;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 长轮询方式获取数据
 */
public class PollingServlet extends HttpServlet {
    private static final long serialVersionUID = 4793060102193587649L;
    public static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //内存存储所有请求的request客户端
    public static Map<String, PollingClient> client = new ConcurrentHashMap<String, PollingClient>();

    static {
        //启动一个线程，扫描，关闭所有超时的连接
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                long current = System.currentTimeMillis();
                for (Map.Entry<String, PollingClient> entry : client.entrySet()) {
                    PollingClient pollingClient = entry.getValue();
                    if (pollingClient == null)
                        return;
                    if (current - pollingClient.vistTime > 20 * 1000) {
                        pollingClient.complete();
                        client.remove(entry.getKey());
                    }
                }
            }
        }, 10, 5000);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String actionType = req.getParameter("actionType");
        System.out.println("actionType:" + actionType);
        //poll主题
        if ("poll".equalsIgnoreCase(actionType)) {
            String topic = req.getParameter("topic");
            pollTopic(req, resp, topic);
        }
        //管理后台,改变数据
        else if ("set".equalsIgnoreCase(actionType)) {
            String topic = req.getParameter("topic");
            String msg = req.getParameter("msg");
            changeTopic(topic, msg);
            resp.getWriter().print("ok");
        }
        System.out.println("get over");
    }

    /**
     * poll数据，缓存请求的对象
     *
     * @param req
     * @param resp
     * @param topic
     */
    private void pollTopic(HttpServletRequest req, HttpServletResponse resp, String topic) {
        final AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(0L);
        client.put(topic, new PollingClient(asyncContext, topic));
    }

    /**
     * 数据发生变化
     *
     * @param topic
     * @param msg
     */
    private void changeTopic(String topic, String msg) {
        PollingClient pollingClient = client.get(topic);
        if (pollingClient != null)
            pollingClient.response(msg);
    }

    private static class PollingClient {
        public AsyncContext asyncContext;
        public String topic;
        public long vistTime;

        PollingClient(AsyncContext asyncContext, String topic) {
            this.topic = topic;
            this.asyncContext = asyncContext;
            this.vistTime = System.currentTimeMillis();
        }

        //主动关闭连接
        private void complete() {
            response("connect end");
            asyncContext.complete();
        }

        //响应数据
        private void response(String message) {
            Map<String, Object> content = new HashMap<String, Object>();
            content.put("messages", message);
            content.put("topic", topic);
            try {
                HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
                PrintWriter writer = response.getWriter();
                response.setHeader("Content-type", "text/html;charset=UTF-8");
                writer.println(format.format(new Date()) + ",receive:" + content + "<br/>");
                writer.flush();
            } catch (Exception se) {
            }
        }
    }
}