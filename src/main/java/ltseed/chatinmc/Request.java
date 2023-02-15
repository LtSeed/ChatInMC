package ltseed.chatinmc;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class Request {

    /**
     * request
     *
     * @param url    请求URL
     * @param header header
     * @return result, null when can not connect to Internet
     */
    public static JSONObject request(String url, Map<String, String> header, Map<String, Object> params) {

        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            JSONObject j = new JSONObject(params);
//            if (params != null){
//                for (String key : params.keySet()) {
//                    j.addProperty(key, params.get(key).toString());
//                }
//            }

            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();
            // 创建http GET请求
            HttpPost httpRequest = new HttpPost(uri);
            httpRequest.setEntity(new StringEntity(j.toString()));
            if (header != null) {
                for (String key : header.keySet()) {
                    httpRequest.setHeader(key, header.get(key));
                }
            }


            // 执行请求
            try {
                response = httpClient.execute(httpRequest);
            } catch (ClientProtocolException e){
                System.out.println(e.getLocalizedMessage());
                return null;
            } catch (IOException e) {
                System.out.println("网络连接失败，请检查网络！");
                return null;
            }
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {

            } else System.out.println(response.getStatusLine().getStatusCode());
            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return JSONObject.parseObject(resultString);
    }

}

