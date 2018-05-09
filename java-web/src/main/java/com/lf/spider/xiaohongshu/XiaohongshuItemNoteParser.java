package com.lf.spider.xiaohongshu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.trip.tripspider.extractor.domain.exception.TrspExtractException;
import com.alibaba.trip.tripspider.spider.crawler.TrspCrawlerExtractorAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 小红书商品笔记解析器
 *
 * @author lfeng
 * @date 2018/05/08
 */
public class XiaohongshuItemNoteParser extends TrspCrawlerExtractorAdapter {
    @Override
    protected JSONArray doExtract(String json, JSONObject jsonObject, List<String> list) throws TrspExtractException {
        if (json == null) {
            return null;
        }
        JSONArray noteArray = getNoteList(json,jsonObject.getString("id"));
        return noteArray;
    }


    /**
     * 获取类目商品数据
     *
     * @param json
     * @return
     */
    private JSONArray getNoteList(String json,String itemId) {
        JSONArray returnNotesArray = new JSONArray();

        JSONObject root = JSONObject.parseObject(json);
        JSONArray notes = root.getJSONObject("data").getJSONArray("normal_notes");
        for (Object obj : notes) {
            returnNotesArray.add(transfer(obj,itemId));
        }
        return notes;
    }


    private JSONObject transfer(Object obj,String itemId) {
        JSONObject jsonObj = (JSONObject) obj;
        jsonObj.put("item_id",itemId);
        jsonObj.put("features", jsonObj.toJSONString());
        JSONObject userObj = jsonObj.getJSONObject("user");
        if (userObj != null) {
            jsonObj.put("user_id", userObj.getString("id"));
            jsonObj.put("user_nickname", userObj.getString("nickname"));
            jsonObj.remove("user");
        }

        jsonObj.put("gmt_create", getCurrentTime());
        return jsonObj;
    }


    private String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        return format.format(new Date());
    }

    private String readHtml() throws Exception {
        String path = "/Users/lfeng/workspace/httpLongPolling/java-web/src/main/java/com/lf/spider/xiaohongshu/xiaohongshuItemNote.json";
        File itemHtmlFile = new File(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(itemHtmlFile), "utf-8"));
        StringBuilder sb = new StringBuilder(10000);
        String str = null;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        br.close();
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        XiaohongshuItemNoteParser parser = new XiaohongshuItemNoteParser();
        JSONArray itemJsonArray = parser.getNoteList(parser.readHtml(),"test123");
        System.out.println(itemJsonArray);
    }


}
