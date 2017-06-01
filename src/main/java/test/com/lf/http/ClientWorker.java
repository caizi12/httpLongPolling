package test.com.lf.http;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by lfeng on 17/6/1.
 *
 * @author lfeng
 * @date 2017/06/01
 */
public class ClientWorker {
    private void test(){

        //定时任务，每个1ms从服务器拉去变化的数据
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("com.taobao.diamond.client.Worker."+ env.serverMgr.name);
                t.setDaemon(true);
                return t;
            }
        });
        //scheduleWithFixedDelay 按固定时间进行执行,执行前都会等待前一次任务执行完
        executor.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                try {
//                    checkLocalConfigInfo();
////获取服务器的变化的配置信息
//                    checkServerConfigInfo();
                } catch (Throwable e) {
                    log.error(env.getName(), "DIAMOND-XXXX", "[sub-check] rotate check error", e);
                }
            }
        }, 1L, 1L, TimeUnit.MILLISECONDS);
    }
}
