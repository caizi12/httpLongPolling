package test.com.lf.http;

import com.alibaba.common.lang.StringUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.util.RequestUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Parse7FreshItemJsonData {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/lfeng/workspace/httpLongPolling/java-web/src/main/java/test/com/lf/http/7fresh.json");
        String dataJson = FileUtils.readFileToString(file).replace("\n", "");
        String[] datas = dataJson.split(";");

        JSONObject obj = new JSONObject();
        for (String str : datas) {
            String[] tmp = str.split(",");
            if (tmp.length == 3) {
                continue;
            }
            String item_url = tmp[3];
            if (StringUtil.isNotBlank(item_url) && item_url.indexOf("https") > -1) {
                Map<String, String[]> map = new HashMap<String, String[]>();
                RequestUtil.parseParameters(map, item_url.replace("html?storeId", "html&storeId"), "utf-8");
                obj.put("storeId", map.get("storeId")[0]);
                obj.put("skuId", map.get("skuId")[0]);

                obj.put("cate_name_level1", tmp[0]);
                obj.put("cate_name_level2", tmp[1]);
                obj.put("cate_name_level3", tmp[2]);
                obj.put("item_url", item_url);
                System.out.println(obj.toString().replaceAll("[\\{\\}]",""));
            }
        }
    }
}
