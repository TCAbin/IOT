package cn.com.gree.service.impl;

import cn.com.gree.dao.BaseDao;
import cn.com.gree.dao.utils.QueryCondition;
import cn.com.gree.entity.DeviceData;
import cn.com.gree.entity.Devices;
import cn.com.gree.entity.TokenData;
import cn.com.gree.service.DeviceDataService;
import cn.com.gree.utils.IOTUtils.DataCollector;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("DeviceDataService")
public class DeviceDataServiceImpl implements DeviceDataService {

    @Resource(name = "BaseDao")
    private BaseDao baseDao;


    @Override
    public List<JSONObject> getMaxDateData() {
        List<JSONObject> objects = new ArrayList<>();
        String jpql = " select o from DeviceData o where o.time in (select max(time) from DeviceData group by device) ";
        List<DeviceData> list = baseDao.getByJpql(jpql);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(DeviceData d : list){
            JSONObject object = new JSONObject();
            Devices devices = d.getDevice();
            if(d.getTemperature() < devices.getMinTemperature() || d.getTemperature() > devices.getMaxTemperature() ||
                    d.getHumidity() < devices.getMinHumidity() || d.getHumidity() > devices.getMaxHumidity()){
                object.put("alert",true);
            } else {
                object.put("alert",false);
            }
            object.put("deviceName",devices.getDeviceName());
            object.put("area",devices.getArea());
            object.put("temperature",d.getTemperature() + "°C");
            object.put("humidity", d.getHumidity() + "%");
            object.put("eventTime",sdf.format(d.getEventTime()));
            object.put("onLine",d.getDeviceStatus());
            objects.add(object);

        }
        return objects;
    }

    @Override
    public boolean judgeDeviceDataIsExist(DeviceData dd) {
        List<QueryCondition> conditions = new ArrayList<>();
        QueryCondition condition = new QueryCondition("eventTime",QueryCondition.EQUAL,dd.getEventTime());
        conditions.add(condition);
        long count = baseDao.getRecordCount(DeviceData.class,conditions);
        return count == 0;
    }

    @Override
    public DeviceData getDeviceData(Devices d) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
            DeviceData dd = new DeviceData();
            dd.setDevice(d);
            dd.setTime(new Date());
            // 获取最新的token
            TokenData td = (TokenData) baseDao.getSingleResultByLimit(" select t from TokenData t order by t.date desc ",1);
            if(td != null){
                Map<String,String> map = DataCollector.getRemoteData(d.getDeviceId(),td.getToken());
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

    @Override
    public JSONObject getDeviceHistoryData(String id, String type, Date startTime, Date endTime) {
        JSONObject result = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<QueryCondition> conditions = new ArrayList<>();
        QueryCondition condition = new QueryCondition("device.id",QueryCondition.EQUAL,Long.valueOf(id));
        QueryCondition condition1 = new QueryCondition("eventTime",QueryCondition.GREATER_EQUAL,startTime);
        QueryCondition condition2 = new QueryCondition("eventTime",QueryCondition.LESS_EQUAL,endTime);
        conditions.add(condition);
        conditions.add(condition1);
        conditions.add(condition2);
        List<DeviceData> list = baseDao.get(DeviceData.class,conditions," order by eventTime ");

        List<String> xData = new ArrayList<>();
        List<String> yData = new ArrayList<>();

        if("temperature".equals(type)){
            for(DeviceData dd : list){
                xData.add(sdf.format(dd.getEventTime()));
                yData.add(dd.getTemperature() + "°C");
            }
        }else if ("humidity".equals(type)){
            for(DeviceData dd : list){
                xData.add(sdf.format(dd.getEventTime()));
                yData.add(dd.getHumidity() + "%");
            }
        }else{
            List<String> zData = new ArrayList<>();
            for(DeviceData dd : list){
                xData.add(sdf.format(dd.getEventTime()));
                yData.add(dd.getHumidity() + "%");
                zData.add(dd.getTemperature() + "°C");
            }
            result.put("data",zData);
        }
        result.put("xAxis",xData);
        result.put("yAxis",yData);
        return result;
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
}
