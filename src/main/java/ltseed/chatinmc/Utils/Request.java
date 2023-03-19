package ltseed.chatinmc.Utils;


import com.alibaba.fastjson.JSONObject;
import ltseed.chatinmc.ChatInMC;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Map;

/**

 A utility class for making HTTP requests using the Apache HttpClient library and FastJson for JSON serialization/deserialization.
 @author ltseed
 @version 1.0
 */
public class Request {

    /**

     Sends a POST request to the specified URL with the given headers and parameters.

     @param url the URL to send the request to

     @param header a map of header values to include in the request

     @param params a map of parameters to include in the request body

     @return the JSON response from the server as a JSONObject, or null if the request fails
     */
    public static JSONObject post(String url, Map<String, String> header, Map<String, Object> params) {

        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri

            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();
            // 创建http请求
            HttpPost httpRequest = new HttpPost(uri);

            //将params以json格式添加到request中
            httpRequest.setEntity(new StringEntity(new JSONObject(params).toString()));

            if (header != null) {
                for (String key : header.keySet()) {
                    httpRequest.setHeader(key, header.get(key));
                }
            }

            // 执行请求
            try {
                response = httpClient.execute(httpRequest);
            } catch (ClientProtocolException e){
                ChatInMC.debug.debugA(e.getLocalizedMessage());
                return null;
            } catch (IOException e) {
                ChatInMC.debug.debugA("Connection failed, check the Internet!");
                return null;
            }
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() != 200)
                ChatInMC.debug.debugA(String.valueOf(response.getStatusLine().getStatusCode()));


            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");

        } catch (Exception e) {
            ChatInMC.debug.err(e.getLocalizedMessage());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                ChatInMC.debug.err(e.getLocalizedMessage());
            }
        }
        return JSONObject.parseObject(resultString);
    }

    /**

     Sends a GET request to the specified URL with the given headers.

     @param url the URL to send the request to

     @param header a map of header values to include in the request

     @return the JSON response from the server as a JSONObject, or null if the request fails
     */
    public static JSONObject get(String url, Map<String, String> header) {

        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            //            if (params != null){
//                for (String key : params.keySet()) {
//                    j.addProperty(key, params.get(key).toString());
////                }
//            }

            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();
            // 创建http请求
            HttpGet httpRequest = new HttpGet(uri);

            //将params以json格式添加到request中
            //httpRequest.(new StringEntity(new JSONObject(params).toString()));

            header.put("Content-Type","application/json; charset=utf-8");
            for (String key : header.keySet()) {
                httpRequest.setHeader(key, header.get(key));
            }

            // 执行请求
            try {
                response = httpClient.execute(httpRequest);
            } catch (ClientProtocolException e){
                ChatInMC.debug.debugA(e.getLocalizedMessage());
                return null;
            } catch (IOException e) {
                ChatInMC.debug.debugA("Connection failed, check the Internet!");
                return null;
            }
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() != 200)
                ChatInMC.debug.debugA(String.valueOf(response.getStatusLine().getStatusCode()));

            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");

        } catch (Exception e) {
            ChatInMC.debug.err(e.getLocalizedMessage());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                ChatInMC.debug.err(e.getLocalizedMessage());
            }
        }
        return JSONObject.parseObject(resultString);
    }

}

