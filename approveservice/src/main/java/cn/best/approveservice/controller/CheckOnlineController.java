/*
package cn.best.approveservice.controller;

import cn.best.approveservice.response.Response;
import io.swagger.annotations.Api;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "获取数据源")
@RestController
@RequestMapping("/checkonline")
public class CheckOnlineController {
    @RequestMapping(value = "/dbenvironment",produces="application/json;charset=utf-8")
    @ResponseBody
    public Response sendHttpPost(@RequestParam("url")String url, @RequestParam("JSONBody") String JSONBody) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "text/html;charset=UTF-8");
        httpPost.addHeader("Authorization", "Basic aGJjb206aGJjb21AMTIzNDU=");
        httpPost.setEntity(new StringEntity(JSONBody,"utf-8"));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        response.close();
        httpClient.close();
        Response responses = new Response();
        Map<String, Object> data = new HashMap<>();
        data.put("data", responseContent);
        return responses.success(data);
    }
}
*/
