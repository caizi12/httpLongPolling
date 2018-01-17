package com.lf.spider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.trip.tripspider.extractor.domain.exception.TrspExtractException;
import com.alibaba.trip.tripspider.spider.crawler.TrspCrawlerExtractorAdapter;

import java.io.*;
import java.util.List;

/**
 * 7fresh商品详情页解析器
 *
 * @author lfeng
 * @date 2017/10/23
 */
public class JD7FreshItemDetailParser extends TrspCrawlerExtractorAdapter {
    @Override
    protected JSONArray doExtract(String json, JSONObject jsonObject, List<String> list) throws TrspExtractException {
        if (json == null) {
            return null;
        }

        JSONArray resultJSONArray = new JSONArray();
        JSONObject resultJSONObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        try {
            json = json.replace("\\", "");
            json = json.substring(1, json.length() - 1);
            JSONObject dataRootObject = JSONObject.parseObject(json).getJSONObject("data");
            dataObject=dataRootObject.getJSONObject("wareInfo");
//            resultJSONObject.put("dataRootObject", dataRootObject);
            resultJSONObject.put("sku_name", dataObject.getString("skuName"));
            resultJSONObject.put("sku_id", dataObject.getString("skuId"));
            resultJSONObject.put("store_id", dataObject.getString("storeId"));
            resultJSONObject.put("jd_price", dataObject.getString("jdPrice"));
            resultJSONObject.put("buy_unit", dataObject.getString("buyUnit"));
            resultJSONObject.put("sale_spec_desc", dataObject.getString("saleSpecDesc"));
        } catch (Exception e) {
        }

        resultJSONArray.add(resultJSONObject);
        return resultJSONArray;
    }


    public static void main(String[] args) throws IOException {
       String json= "{\"code\":\"0\",\"data\":{\"wareInfo\":{\"skuName\":\"北菜园有机尖椒 250g\",\"skuId\":104715,\"productId\":104665,\"jdPrice\":\"13.9\",\"saleNum\":3,\"addCart\":true,\"imageInfoList\":[{\"index\":1,\"imageUrl\":\"https://img11.360buyimg.com/xstore/s800x800_jfs/t16588/267/352520249/52047/2cb6a041/5a2f6245N631751cf.jpg!q90\"},{\"index\":2,\"imageUrl\":\"https://img11.360buyimg.com/xstore/s800x800_jfs/t12022/236/1988705229/60363/f01e4381/5a2f6245Nde5eedb3.jpg!q90\"},{\"index\":3,\"imageUrl\":\"https://img11.360buyimg.com/xstore/s800x800_jfs/t14149/248/615127371/55007/db1630c4/5a2f6225Ne8373b3a.jpg!q90\"},{\"index\":4,\"imageUrl\":\"https://img11.360buyimg.com/xstore/s800x800_jfs/t14566/159/494177833/92588/5d8a2204/5a2f6245Nb56f2a4b.jpg!q90\"},{\"index\":5,\"imageUrl\":\"https://img11.360buyimg.com/xstore/s800x800_jfs/t14494/246/497135161/64873/3a616b08/5a2f624cNa551921d.jpg!q90\"}],\"storeId\":131229,\"weightDesc\":\"0.250kg\",\"saleSpecDesc\":\"250g\",\"serviceTagId\":0,\"status\":2,\"showCheckbox\":true,\"salemode\":1,\"saleUnit\":\"3\",\"buyUnit\":\"/份\",\"startBuyUnitNum\":\"1\",\"startBuyDesc\":\"1份起购,限购999份\",\"stepBuyUnitNum\":\"1\",\"maxBuyUnitNum\":\"999\",\"isGift\":false,\"bigFieldUrl\":\"https://7fresh.m.jd.com/detail1.html?storeId=131229&skuId=104715\",\"buyButtonType\":0,\"deliveryType\":0},\"success\":true},\"ret\":200}";
        System.out.println(json.replace("\\",""));
    }


}
