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
import java.text.SimpleDateFormat;
import java.util.Date;
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
        JSONArray itemDataArray = getItemList(html);
        setItemInfo(itemDataArray, jsonObject);
        jsonObject.put("list",jsonObject);
        JSONArray resultDataArray = new JSONArray();
        resultDataArray.add(jsonObject);
        return resultDataArray;
    }


    /**
     * 获取类目商品数据
     *
     * @param html
     * @return
     */
    private JSONArray getItemList(String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByTag("script");
        String tmpHtml = null;
        String dataJson = null;
        String identify = "__INITIAL_SSR_STATE__=";
        for (Element element : elements) {
            tmpHtml = element.html();
            if (tmpHtml != null && element.html().indexOf(identify) > -1) {
                dataJson = tmpHtml.substring(tmpHtml.indexOf(identify) + identify.length(), tmpHtml.lastIndexOf("}") + 1);
                break;
            }
        }
        JSONArray itemList = JSONObject.parseObject(dataJson).getJSONObject("Main").
                getJSONObject("basicData").getJSONArray("items");
        return itemList;
    }


    private void setItemInfo(JSONArray itemJsonList, JSONObject itemObject) {
        JSONObject itemJson = null;
        for (Object object : itemJsonList) {
            itemJson = (JSONObject) object;
            if (itemObject.get("id").equals(itemJson.getString("id"))) {

                itemObject.put("title", itemJson.getString("name"));
                itemObject.put("gmt_create", getCurrentTime());
                itemObject.put("promotion_activity", itemJson.getString("couponText"));

                JSONObject brandObj = itemJson.getJSONObject("brand");
                if (brandObj != null) {
                    itemObject.put("brand", brandObj.getString("name"));
                }

                JSONObject shareInfoObj = itemJson.getJSONObject("shareInfo");
                if (shareInfoObj != null) {
                    itemObject.put("url", shareInfoObj.getString("url"));
                }

                JSONObject detailJson = itemJson.getJSONObject("detail");

                if (detailJson != null && detailJson.getJSONArray("attributes") != null) {
                    StringBuilder keyPropery = new StringBuilder();
                    JSONArray attrArray = detailJson.getJSONArray("attributes");
                    JSONObject attrJsonObj = null;
                    for (Object attrObj : attrArray) {
                        attrJsonObj = (JSONObject) attrObj;
                        keyPropery.append(attrJsonObj.getString("attributeName"));
                        keyPropery.append(":");
                        keyPropery.append(attrJsonObj.getString("text"));
                        keyPropery.append(";");
                    }
                    itemObject.put("key_property", keyPropery.toString());
                }

                break;
            }
        }
    }

    private String getCurrentTime(){
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        return format.format(new Date());
    }

    private String readHtml() throws Exception {
        String path = "/Users/lfeng/workspace/httpLongPolling/java-web/src/main/java/com/lf/spider/xiaohongshuItemDetail.htm";
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
        JSONArray itemJsonArray = parser.getItemList(parser.readHtml());
        JSONObject itemJson = new JSONObject();
        itemJson.put("id", "59c91ae846283926e6ef843e");
        parser.setItemInfo(itemJsonArray, itemJson);
        System.out.println(itemJson);
    }


}
