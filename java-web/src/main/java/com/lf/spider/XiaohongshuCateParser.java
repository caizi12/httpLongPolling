package com.lf.spider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.trip.tripspider.extractor.domain.exception.TrspExtractException;
import com.alibaba.trip.tripspider.spider.crawler.TrspCrawlerExtractorAdapter;

import java.io.IOException;
import java.util.List;

/**
 * 小红书app类目信息解析
 *
 * @author lfeng
 * @date 2017/10/23
 */
public class XiaohongshuCateParser extends TrspCrawlerExtractorAdapter {
    @Override
    protected JSONArray doExtract(String json, JSONObject jsonObject, List<String> list) throws TrspExtractException {
        if (json == null) {
            return null;
        }

        JSONArray resultJSONArray = new JSONArray();
        JSONObject dataObject = null;
        JSONArray dataArray = JSONObject.parseObject(json).getJSONArray("data");
        String cate_level1_name = null;
        String cate_level1_id = null;
        String cate_level2_name = null;
        String cate_level2_id = null;
        for (Object obj : dataArray) {
            cate_level1_name = ((JSONObject) obj).getString("name");
            cate_level1_id = ((JSONObject) obj).getString("id");

            JSONArray cate2Array = ((JSONObject) obj).getJSONArray("entries");
            JSONObject cate2Obj = null;
            for (Object objTwo : cate2Array) {
                cate2Obj = ((JSONObject) objTwo);
                cate_level2_name = cate2Obj.getString("name");
                cate_level2_id = cate2Obj.getString("id");

                JSONArray cate3Array = cate2Obj.getJSONArray("entries");
                if (cate3Array == null) {
                    continue;
                }
                JSONObject cate3JsonObj = null;
                for (Object cat3Obj : cate3Array) {
                    cate3JsonObj = (JSONObject) cat3Obj;
                    dataObject = new JSONObject();
                    dataObject.put("url", cate3JsonObj.get("link"));
                    dataObject.put("image", cate3JsonObj.get("image"));
                    dataObject.put("cate_level1_id", cate_level1_id);
                    dataObject.put("cate_level1_name", cate_level1_name);
                    dataObject.put("cate_Level2_id", cate_level2_id);
                    dataObject.put("cate_Level2_name", cate_level2_name);
                    dataObject.put("cate_level3_name", cate3JsonObj.get("name"));
                    dataObject.put("cate_level3_id", cate3JsonObj.get("id"));
                    resultJSONArray.add(dataObject);
                }
            }
        }

        return resultJSONArray;
    }


    public static void main(String[] args) throws IOException {
    }
}
