package com.lf.spider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.trip.tripspider.extractor.domain.exception.TrspExtractException;
import com.alibaba.trip.tripspider.spider.crawler.TrspCrawlerExtractorAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 小红书商品详情页面解析器
 *
 * @author lfeng
 * @date 2017/10/23
 */
public class XiaohongshuItemDetailParser extends TrspCrawlerExtractorAdapter {
    @Override
    protected JSONArray doExtract(String html, JSONObject jsonObject, List<String> list) throws TrspExtractException {
        if (html == null) {
            return null;
        }
        JSONArray itemDataArray = new JSONArray();

        String cate_level1_name = jsonObject.getString("cate_level1_name");
        String cate_level1_id = jsonObject.getString("cate_level1_id");

        JSONArray itemJsonArray = getCategoryItemList(html);
        JSONArray itemTmpList = null;
        JSONObject category = null;
        for (Object object : itemJsonArray) {
            itemTmpList = ((JSONObject) object).getJSONArray("itemList");
            category = ((JSONObject) object).getJSONObject("category");
            category.put("cate_level1_id", cate_level1_id);
            category.put("cate_level1_name", cate_level1_name);
            itemDataArray.addAll(parseItemList(itemTmpList, category));
        }

        return itemDataArray;
    }


    /**
     * 获取类目商品数据
     *
     * @param html
     * @return
     */
    private JSONArray getCategoryItemList(String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByTag("script");
        String tmpHtml = null;
        String dataJson = null;
        for (Element element : elements) {
            tmpHtml = element.html();
            if (tmpHtml != null && element.html().indexOf("json_Data") > -1) {
                dataJson = tmpHtml.substring(tmpHtml.indexOf("json_Data") + 10, tmpHtml.lastIndexOf("}")+1 );
                break;
            }
        }
        JSONArray categoryItemList = JSONObject.parseObject(dataJson).getJSONArray("categoryItemList");
        return categoryItemList;
    }

    private void setCategoryInfo(JSONObject category, JSONObject dataObj) {
        if (category != null && dataObj != null) {
            dataObj.put("cate_level2_name", category.getString("name"));
            dataObj.put("cate_level2_id", category.getString("id"));
            dataObj.put("cate_level1_id", category.getString("cate_level1_id"));
            dataObj.put("cate_level1_name", category.getString("cate_level1_name"));
        }
    }

    private JSONArray parseItemList(JSONArray itemJsonList, JSONObject category) {
        JSONArray itemList = new JSONArray();
        JSONObject itemObj = null;
        JSONObject objTmp = null;
        for (Object object : itemJsonList) {
            objTmp = (JSONObject) object;
            itemObj = new JSONObject();
            itemObj.put("id", objTmp.get("id"));
            itemObj.put("title", objTmp.get("name"));
            itemObj.put("promotion_price", objTmp.get("counterPrice"));
            itemObj.put("orig_price", objTmp.get("retailPrice"));
            setCategoryInfo(category, itemObj);
            itemList.add(itemObj);
        }
        return itemList;
    }


    private String readHtml() throws Exception {
        String path = "/Users/lfeng/workspace/httpLongPolling/java-web/src/main/java/com/lf/spider/neteasyCateitem.htm";
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
        XiaohongshuItemDetailParser parser = new XiaohongshuItemDetailParser();

        JSONArray itemDataArray = new JSONArray();

        JSONArray itemJsonArray = parser.getCategoryItemList(parser.readHtml());
        JSONArray itemTmpList = null;
        JSONObject category = null;
        for (Object object : itemJsonArray) {
            itemTmpList = ((JSONObject) object).getJSONArray("itemList");
            category = ((JSONObject) object).getJSONObject("category");
            itemDataArray.addAll(parser.parseItemList(itemTmpList, category));
        }

        System.out.println(itemDataArray);
    }


}
