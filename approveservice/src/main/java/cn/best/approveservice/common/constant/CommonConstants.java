package cn.best.approveservice.common.constant;

import cn.best.approveservice.common.utils.SecurityUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Author: zhuyl
 * @Date: 2020/6/4
 */
/*提示语*/
public interface CommonConstants {

    static String getConfigInfo(String cfgkey) {

        String cfgval = null;
        try {
            Properties properties=new Properties();
            String path = SecurityUtils.class.getClassLoader().getResource("").getPath();
            FileInputStream fileinStream = new FileInputStream(path + "/commonConstants.yml");
            BufferedReader bf = new BufferedReader(new InputStreamReader(fileinStream,"UTF-8"));
            properties.load(bf);
            cfgval =properties.getProperty(cfgkey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cfgval;
    }

    //网站域名
    String SystemName = getConfigInfo("SystemName");

    /**用户未登录提示*/
    String FAIL_MSG = getConfigInfo("FAIL_MSG");

    /**未登录请求api提示*/
    String SOCKET_MSG = getConfigInfo("SOCKET_MSG");

    /**url中sessionId的key  x-token*/
    String URL_TOKEN = getConfigInfo("URL_TOKEN");



}
