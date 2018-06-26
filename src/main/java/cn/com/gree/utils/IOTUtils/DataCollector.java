package cn.com.gree.utils.IOTUtils;

import cn.com.gree.utils.IOTUtils.utils.HttpsUtil;
import cn.com.gree.utils.IOTUtils.utils.JsonUtil;
import cn.com.gree.utils.IOTUtils.utils.StreamClosedHttpResponse;

import java.util.HashMap;
import java.util.Map;

public class DataCollector {

    @SuppressWarnings("unchecked")
    public static String getToken(){
        String appId = Constant.APPID;
        String secret = Constant.SECRET;
        String urlLogin = Constant.APP_AUTH;

        HttpsUtil httpsUtil = new HttpsUtil();
        try {
            httpsUtil.initSSLConfigForTwoWay();
            Map<String, String> paramLogin = new HashMap<>();
            paramLogin.put("appId", appId);
            paramLogin.put("secret", secret);
            StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine(urlLogin, paramLogin);

            Map<String, String> data = new HashMap<>();
            data = JsonUtil.jsonString2SimpleObj(responseLogin.getContent(), data.getClass());
            return data.get("accessToken");
        } catch (Exception e) {
            System.out.println("获取token失败。" + e.getMessage());
        }
        return null;
    }


}
