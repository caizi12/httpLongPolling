package com.lf.spider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.trip.tripspider.extractor.domain.exception.TrspExtractException;
import com.alibaba.trip.tripspider.spider.crawler.TrspCrawlerExtractorAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 亚马逊商品详情页解析器
 *
 * @author lfeng
 * @date 2017/10/23
 */
public class AmazonItemDetailParser extends TrspCrawlerExtractorAdapter {
    @Override
    protected JSONArray doExtract(String html, JSONObject jsonObject, List<String> list) throws TrspExtractException {
        if (html == null) {return null;}
        Document document = Jsoup.parse(html);

        JSONArray resultJSONArray = new JSONArray();
        JSONObject resultJSONObject = new JSONObject();

        resultJSONObject.put("item_id",jsonObject.getString("item_id"));
        resultJSONObject.put("item_title",jsonObject.getString("item_title"));
        resultJSONObject.put("item_url",jsonObject.getString("url"));

        //一级类目
        String cate_name=findText(document,"#nav-subnav > a:eq(0) > span");
        if(isEmpty(cate_name)){
            cate_name=findText(document,"#wayfinding-breadcrumbs_feature_div ul li:eq(0) a");
        }
        resultJSONObject.put("cate_name",cate_name);


        resultJSONObject.put("sale_price", findText(document,"#priceblock_ourprice"));
        resultJSONObject.put("factory_price",findText(document,"#price > table.a-lineitem > tbody > tr:eq(0) >td:eq(1)"));
        resultJSONObject.put("brand",findText(html,brandPattern,brandReplaceStrs));
        resultJSONObject.put("item_pack",findText(html,itemPackPattern,itemPackReplaceStrs));

        String itemSize=findText(html,itemSizePattern,itemSizeReplaceStrs);
        String item_weight=findText(html,itemWeightPattern,itemWeightReplaceStrs);
        if(isEmpty(itemSize) || isEmpty(item_weight)){
            Elements itemBaseInfoHtmlElements=document.select("#detail_bullets_id table td.bucket div li");
            String tempText=null;
            for(Element element:itemBaseInfoHtmlElements){
                tempText=element.text();
                if(tempText!=null && tempText.split(":").length==2){
                    if(tempText.indexOf("商品尺寸")==0 ){
                        itemSize=tempText.split(":")[1];
                    }else if(tempText.indexOf("商品重量")==0 ){
                        item_weight=tempText.split(":")[1];
                    }
                }else {
                    continue;
                }
            }
        }

        resultJSONObject.put("item_size",trim(itemSize));
        resultJSONObject.put("item_weight",trim(item_weight));
        //评价数
        resultJSONObject.put("evaluate_num",findText(document,"span.totalReviewCount"));

        resultJSONArray.add(resultJSONObject);
        return resultJSONArray;
    }


    private String findText(Document document,String cssQuery){
        Elements elements= document.select(cssQuery);
        if(elements!=null){
            return trim(elements.text().replaceAll("[￥,]",""));
        }
        return null;
    }

    private static String salePriceRegex="<span id=\"priceblock_ourprice\" class=\"a-size-medium a-color-price\">.*?</span>";
    private static Pattern salePricePattern=Pattern.compile(salePriceRegex);
    private static String[] salePriceReplaceStrs=new String[]{"<span id=\"priceblock_ourprice\" class=\"a-size-medium a-color-price\">","<//span>"};

    private static String brandRegex="<tr><td class=\"label\">品牌</td><td class=\"value\">.*?</td></tr>";
    private static Pattern brandPattern=Pattern.compile(brandRegex);
    private static String[] brandReplaceStrs=new String[]{"<tr><td class=\"label\">品牌</td><td class=\"value\">","</td></tr>"};

    private static String itemPackRegex="<tr><td class=\"label\">包装信息</td><td class=\"value\">.*?</td></tr>";
    private static Pattern itemPackPattern=Pattern.compile(itemPackRegex);
    private static String[] itemPackReplaceStrs=new String[]{"<tr><td class=\"label\">包装信息</td><td class=\"value\">","</td></tr>"};

    private static String itemSizeRegex="<tr class=\"size-weight\"><td class=\"label\">商品尺寸</td><td class=\"value\">.*?</td></tr>";
    private static Pattern itemSizePattern=Pattern.compile(itemSizeRegex);
    private static String[] itemSizeReplaceStrs=new String[]{"<tr class=\"size-weight\"><td class=\"label\">商品尺寸</td><td class=\"value\">","</td></tr>"};

    private static String itemWeightRegex="<tr class=\"size-weight\"><td class=\"label\">商品重量</td><td class=\"value\">.*?</td></tr>";
    private static Pattern itemWeightPattern=Pattern.compile(itemWeightRegex);
    private static String[] itemWeightReplaceStrs=new String[]{"<tr class=\"size-weight\"><td class=\"label\">商品重量</td><td class=\"value\">","</td></tr>"};

    /**
     * 查找文本
     * @param pattern
     * @param html
     * @param replaceStrs
     * @return
     */
    private String findText(String html,Pattern pattern,String[] replaceStrs){
        Matcher matcher=pattern.matcher(html);
        if(matcher.find()){
            String findStr=matcher.group(0);
            if(replaceStrs!=null){
                for (String replaceStr:replaceStrs){
                    findStr=findStr.replaceAll(replaceStr,"");
                }
            }
            return trim(findStr);
        }
        return null;
    }


    private boolean isEmpty(String str){
        if(str==null || str.trim().length()==0){
            return true;
        }
        return false;
    }
    private String trim(String str){
        if(str!=null ){
          return   str.trim();
        }
        return str;
    }

    public static void main(String[] args) throws IOException {
        String path = AmazonItemDetailParser.class.getResource(".").getPath();
        System.out.println(path);
        File itemHtmlFile = new File(
            "/Users/lfeng/project/modelbrige/javaProjectTest/java-web/src/main/java/com/lf/spider/item_detail2.htm");

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(itemHtmlFile), "utf-8"));
        StringBuilder sb = new StringBuilder(10000);
        String str = null;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        br.close();
        JSONArray js = new AmazonItemDetailParser().doExtract(sb.toString(), new JSONObject(), null);

        System.out.println(js.toJSONString());
    }


}
