package test.com.lf.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class XiaohongshuCateDataParse {
    public static void main(String[] args) throws IOException {
        File file=new File("/Users/lfeng/workspace/httpLongPolling/java-web/src/main/java/test/com/lf/http/xiaohongshu.json");
        String dataJson=FileUtils.readFileToString(file);

        JSONObject jsonObject=JSONObject.parseObject(dataJson);
        JSONArray array=jsonObject.getJSONArray("data");
        String catename=null;
        for(Object obj: array){
            catename=((JSONObject)obj).getString("name");

//            if("服装".equals(catename)||"美食".equals(catename)){

             JSONArray  jsonArray=   ((JSONObject)obj).getJSONArray("entries");
             JSONObject temObj=null;
             String cate2name=null;
             for(Object objTwo:jsonArray){
                 temObj=((JSONObject)objTwo);
                 cate2name=temObj.getString("name");

                 JSONArray  jsonArray3=  temObj.getJSONArray("entries");
                 if(jsonArray3==null){
                     continue;
                 }
                 JSONObject temObj3=null;
                 for(Object obj3:jsonArray3){
                     temObj3=((JSONObject)obj3);
                     System.out.println("\"cateLevel1\":\""+catename+"\",\"cateLevel2\":\""+cate2name+"\",\"cateLevel3\":\""+temObj3.getString("name")+"\"");
                 }
             }
//            }
        }
//        System.out.println(dataJson);
    }


}
