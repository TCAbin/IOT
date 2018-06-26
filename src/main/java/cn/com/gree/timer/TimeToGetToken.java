package cn.com.gree.timer;

import cn.com.gree.dao.BaseDao;
import cn.com.gree.entity.DeviceData;
import cn.com.gree.entity.Devices;
import cn.com.gree.entity.TokenData;
import cn.com.gree.utils.IOTUtils.Constant;
import cn.com.gree.utils.IOTUtils.DataCollector;
import cn.com.gree.utils.IOTUtils.utils.HttpsUtil;
import cn.com.gree.utils.IOTUtils.utils.JsonUtil;
import cn.com.gree.utils.IOTUtils.utils.StreamClosedHttpResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class TimeToGetToken {

    @Resource(name = "BaseDao")
    private BaseDao baseDao;

    /**
     * @author 260172
     * @date 2018/6/26 9:33
     * 每小时刷新token
     */
//    @Scheduled(cron = "0 59 * * * *")
    private void getToken(){
        refreshToken();
    }

    /**
     * @author 260172
     * @date 2018/6/26 9:51
     * 每十分钟获取一次设备数据
     */
//    @Scheduled(cron = "0 10 * * * *")
    private void getDeviceData(){
        setDeviceData();
    }

    /**
     * @author 260172
     * @date 2018/6/26 13:43
     * 刷新token
     */
    private void refreshToken(){
        String token = DataCollector.getToken();
        TokenData data = new TokenData();
        data.setDate(new Date());
        data.setToken(token);
        baseDao.save(data);
    }


    /**
     * @author 260172
     * @date 2018/6/26 13:42
     * 设置设备信息
     */
    private void setDeviceData(){
        List<Devices> devices = getDevices();
        for(Devices d : devices){
            DeviceData dd = getDeviceData(d);
            if(dd != null){
                baseDao.save(dd);
            }
        }
    }

    /**
     * @author 260172
     * @date 2018/6/26 13:38
     * 获取所有设备
     */
    private List<Devices> getDevices(){
        return baseDao.getByJpql(" select o from Devices o group by o.deviceId");
    }



    /**
     * @author 260172
     * @date 2018/6/26 13:39
     * 获取设备信息
     */
    private DeviceData getDeviceData(Devices d){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
            DeviceData dd = new DeviceData();
            dd.setDevice(d);
            dd.setTime(new Date());
            TokenData td = (TokenData) baseDao.getSingleResultByLimit(" select t from TokenData t order by t.date desc ",1);
            if(td != null){
                Map<String,String> map = getRemoteData(d.getDeviceId(),td.getToken());
                dd.setEventTime(setZone(sdf.parse(map.get("eventTime")),8));
                dd.setTemperature(Double.valueOf(map.get("Temperature")));
                dd.setHumidity(Double.valueOf(map.get("humidity")));
                dd.setDeviceStatus(Integer.valueOf(map.get("status")));
            }
            return dd;
        } catch (Exception e) {
            System.out.println("设置设备" + d.getDeviceId() + "数据失败。" + e.getMessage());
            return null;
        }
    }

    /**
     * @autor 260172
     * @date 2018/6/26 14:53
     * 增加hour小时
     */
    private Date setZone(Date date,int hour){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);
        return cal.getTime();
    }

    /**
     * @author 260172
     * @date 2018/6/26 13:39
     * 调用远程接口
     */
    @SuppressWarnings("unchecked")
    private Map<String,String> getRemoteData(String deviceId,String accessToken) throws Exception {
        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

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
