package com.lf.spider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.trip.tripspider.extractor.domain.exception.TrspExtractException;
import com.alibaba.trip.tripspider.spider.crawler.TrspCrawlerExtractorAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.List;

/**
 * zol.com.con一级类目页面解析，主要用来获取二级类目
 *
 * @author lfeng
 * @date 2017/11/10
 */
public class ZolCateLevel1Parser extends TrspCrawlerExtractorAdapter {

    private static final String ZOL_URL="http://detail.zol.com.cn";
    private static final String PAGE_PARAM="$_pageIndex.html";
    private static final String PATTNER_PAGE="_1.html";
    private static final String PATTNER_PAGE_REPLACE="_1\\.html";
    private static final String PAGE_PARAM_REPLACE="_\\$_pageIndex.html";

    @Override
    protected JSONArray doExtract(String html, JSONObject jsonObject, List<String> list) throws TrspExtractException {
        if (html == null) {return null;}
        Document document = Jsoup.parse(html);

        JSONArray resultJSONArray = new JSONArray();
        JSONObject resultJSONObject =null;

        Elements elements= document.select("#J_CategoryCurrent dd ul.category-current-list li");
        String cate_level2_url=null;
        String cate_level2_name=null;
        for(Element element:elements){
            cate_level2_url=formatCateLevel2Url(element.select("a").attr("href"));
            resultJSONObject= new JSONObject();
            resultJSONObject.put("cate_level2_url", cate_level2_url);
            resultJSONObject.put("cate_level2_name",element.select("a").text());
            resultJSONArray.add(resultJSONObject);
        }

        resultJSONArray.add(resultJSONObject);
        return resultJSONArray;
    }

    private String formatCateLevel2Url(String cate_level2_url){
        if(!isEmpty(cate_level2_url)){

            if(cate_level2_url.indexOf("http")==-1){
                cate_level2_url=ZOL_URL+cate_level2_url;
            }

            if(cate_level2_url.indexOf(".html")==-1){
                cate_level2_url+=PAGE_PARAM;
            }else if(cate_level2_url.endsWith(PATTNER_PAGE)){
                cate_level2_url=cate_level2_url.replaceAll(PATTNER_PAGE_REPLACE,PAGE_PARAM_REPLACE);
            }
        }

        return cate_level2_url;
    }

    private String findText(Document document,String cssQuery){
        Elements elements= document.select(cssQuery);
        if(elements!=null){
            return trim(elements.text());
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
        String path = ZolCateLevel1Parser.class.getResource(".").getPath();
        System.out.println(path);
        File itemHtmlFile = new File(
            "/Users/lfeng/workspace/httpLongPolling/java-web/src/main/java/com/lf/spider/zol_cate.htm");

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(itemHtmlFile), "GBK"));
        StringBuilder sb = new StringBuilder(10000);
        String str = null;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        br.close();
        JSONArray js = new ZolCateLevel1Parser().doExtract(sb.toString(), new JSONObject(), null);

        System.out.println(js.toJSONString());
    }


}
