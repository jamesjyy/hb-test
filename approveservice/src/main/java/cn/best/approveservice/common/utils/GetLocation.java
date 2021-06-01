package cn.best.approveservice.common.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetLocation {
    /**
     * 百度地图通过经纬度来获取地址,传入参数纬度lat、经度lng
     * @param lat
     * @param lng
     * @return
     */
    public static String getAddress(String lat, String lng) throws IOException {
        JSONObject obj = getLocationInfo(lat, lng).getJSONObject("result");
        //System.out.println(obj);
        return obj.getString("formatted_address");
    }

    public static JSONObject getLocationInfo(String lat, String lng) throws IOException {
        String urlString = "http://api.map.baidu.com/reverse_geocoding/v3/?ak=RiYgEn8vkgl0rrqR1TkQENjn5nbTMt0y&output=json&coordtype=wgs84ll&location="+lat+","+lng;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
        String line;
        String res = "";
        while((line = in.readLine())!= null) {
            res += line+"\n";
        }
        in.close();
        JSONObject jsonObject = JSONObject.parseObject(res);
        return jsonObject;
    }
}
