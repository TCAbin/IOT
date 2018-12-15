package cn.com.gree.utils.IOTUtils;

import cn.com.gree.utils.IOTUtils.utils.HttpsUtil;
import cn.com.gree.utils.IOTUtils.utils.JsonUtil;
import cn.com.gree.utils.IOTUtils.utils.StreamClosedHttpResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DataCollector {

    /** https校验 */
    private static HttpsUtil httpsUtil = null;

    static {
        verifyHttps();
    }

    /**
     * @author Abin
     * @date 2018/8/7 15:58
     * Https验证
     */
    private static void verifyHttps(){
        httpsUtil = new HttpsUtil();
        try {
            httpsUtil.initSSLConfigForTwoWay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @author 260172
     * @date 2018/6/27 16:16
     * getToken
     */
    @SuppressWarnings("unchecked")
    public static String getToken(){
        String appId = Constant.APPID;
        String secret = Constant.SECRET;
        String urlLogin = Constant.APP_AUTH;

        try {
            Map<String, String> paramLogin = new HashMap<>();
            paramLogin.put("appId", appId);
            paramLogin.put("secret", secret);
            StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine(urlLogin, paramLogin);

            Map<String, String> data = new HashMap<>();
            data = JsonUtil.jsonString2SimpleObj(responseLogin.getContent(), data.getClass());
            return data.get("accessToken");
        } catch (Exception e) {
            verifyHttps();
            System.out.println("获取token失败。" + e.getMessage());
        }
        return null;
    }


    /**
     * @author 260172
     * @date 2018/6/26 13:39
     * 调用远程接口
     */
    @SuppressWarnings("unchecked")
    public static Map<String,String> getRemoteData(String deviceId,String accessToken) throws Exception {
        String appId = Constant.APPID;
        String urlQueryDeviceData = Constant.QUERY_DEVICE_DATA + "/" + deviceId;

        Map<String, String> paramQueryDeviceData = new HashMap<>();
        paramQueryDeviceData.put("appId", appId);

        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

        StreamClosedHttpResponse bodyQueryDeviceData = httpsUtil.doGetWithParasGetStatusLine(urlQueryDeviceData,
                paramQueryDeviceData, header);

        Map<String, String> data = new HashMap<>();
        data = JsonUtil.jsonString2SimpleObj(bodyQueryDeviceData.getContent(), data.getClass());
        JSONObject object = JSONArray.fromObject(data.get("services")).getJSONObject(0);
        String status = JSONArray.fromObject(data.get("deviceInfo")).getJSONObject(0).getString("status");

        data.clear();
        data.put("eventTime",object.getString("eventTime"));
        data.put("Temperature",object.getJSONObject("data").getString("Temperature").trim());
        data.put("humidity",object.getJSONObject("data").getString("humidity").trim());
        switch (status){
            case "ONLINE" : {
                data.put("status","1");
                break;
            }
            case "OFFLINE" : {
                data.put("status","2");
                break;
            }
            case "INBOX" : {
                data.put("status","3");
                break;
            }
            case "ABNORMAL" : {
                data.put("status","4");
                break;
            }
            default : {
                data.put("status","5");
            }
        }
        return data;
    }

}
