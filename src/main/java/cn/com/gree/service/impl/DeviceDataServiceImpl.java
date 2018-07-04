package cn.com.gree.service.impl;

import cn.com.gree.dao.BaseDao;
import cn.com.gree.dao.utils.QueryCondition;
import cn.com.gree.entity.DeviceData;
import cn.com.gree.entity.Devices;
import cn.com.gree.entity.TokenData;
import cn.com.gree.service.DeviceDataService;
import cn.com.gree.service.DevicesService;
import cn.com.gree.service.TokenDataService;
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

    @Resource(name = "DevicesService")
    private DevicesService devicesService;

    @Resource(name = "TokenDataService")
    private TokenDataService tokenDataService;


    @Override
    public List<JSONObject> getMaxDateData() {
        List<JSONObject> objects = new ArrayList<>();
//        String jpql1 = " select o from DeviceData o where o.time in (select max(time) from DeviceData group by device) ";
//        String sql = " select a.device , max(a.time) as time from DeviceData a group by a.device ";
//        String sql1 = " select b from DeviceData b ";
//        StringBuffer jpql = new StringBuffer(" select d from ");
//        jpql.append(" (").append(sql).append(") c left join fetch ")
//                .append(" (").append(sql1).append(") d")
//                .append(" on c.device = d.device and c.time = d.time ");
//        List<DeviceData> list = baseDao.getByJpql(jpql.toString());
//        String str = " select d.* from " +
//                "( select a.device_id , max(a.time) as time from t_device_data a group by a.device_id ) c " +
//                " left join " +
//                "( select b.* from t_device_data b ) d " +
//                "on c.device_id = d.device_id and c.time = d.time ";
        String str = " select o.* from t_device_data o , " +
                "(select device_id,max(time) as time from t_device_data group by device_id) a " +
                "where a.device_id = o.device_id and o.time = a.time ";
        List<DeviceData> list = baseDao.getByNativeSQL(DeviceData.class,str);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(DeviceData d : list){
            JSONObject object = new JSONObject();
            Devices devices = d.getDevice();
            if(d.getTemperature() < devices.getMinTemperature() || d.getTemperature() > devices.getMaxTemperature()){
                object.put("alert","1");
            } else if(d.getHumidity() < devices.getMinHumidity() || d.getHumidity() > devices.getMaxHumidity()){
                object.put("alert","2");
            } else {
                object.put("alert","0");
            }
            object.put("deviceName",devices.getDeviceName());
            object.put("area",devices.getArea());
            object.put("temperature",d.getTemperature());
            object.put("humidity", d.getHumidity());
            object.put("eventTime",sdf.format(d.getEventTime()));
            object.put("onLine",d.getDeviceStatus());
            objects.add(object);
            if(d.getId() == 1){
                JSONObject o = new JSONObject();
                o.put("xAxis",sdf.format(d.getTime()));
                objects.add(o);
            }
        }
        return objects;
    }

    @Override
    public boolean judgeDeviceDataIsExist(DeviceData dd) {
        List<QueryCondition> conditions = new ArrayList<>();
        QueryCondition condition = new QueryCondition("eventTime",QueryCondition.EQUAL,dd.getEventTime());
        conditions.add(condition);
        List<DeviceData> list = baseDao.get(DeviceData.class,conditions);
        if(list != null && list.size() > 0){
            return !list.get(0).getDeviceStatus().equals(dd.getDeviceStatus());
        }
        return true;
    }

    @Override
    public DeviceData getDeviceData(Devices d) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
            DeviceData dd = null;
            // 获取最新的token
            TokenData td = (TokenData) baseDao.getSingleResultByLimit(" select t from TokenData t order by t.date desc ",1);
            if(td != null){
                dd = new DeviceData();
                dd.setDevice(d);
                dd.setTime(new Date());
                Map<String,String> map = DataCollector.getRemoteData(d.getDeviceId(),td.getToken());
                dd.setEventTime(setZone(sdf.parse(map.get("eventTime")),8));
                dd.setTemperature(Double.valueOf(map.get("Temperature")));
                dd.setHumidity(Double.valueOf(map.get("humidity")));
                dd.setDeviceStatus(Integer.valueOf(map.get("status")));
            }else{
                tokenDataService.refreshToken();
            }
            return dd;
        } catch (Exception e) {
            tokenDataService.refreshToken();
            System.out.println("设置设备" + d.getDeviceId() + "数据失败。" + e.getMessage());
            return null;
        }
    }

    /**
     * @author 260172
     * @date 2018/6/28 9:38
     * 获取今日视图的横坐标
     */
    private List<String> getTodayXAxis(){
        String jpql = " select o from DeviceData o where device.id = 1 ";
        List<DeviceData> xAxis = baseDao.getByJpql(jpql);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> result = new ArrayList<>();
        for(DeviceData dd : xAxis){
            result.add(sdf.format(dd.getTime()));
        }
        return result;
    }

    /**
     * @author 260172
     * @date 2018/6/28 9:44
     *
     */
    private void getTodayYAxis(JSONObject object,Devices devices,String dateStr,String yAxisName,boolean isTemperature){
        StringBuffer jpql = new StringBuffer(" select d from DeviceData d where d.eventTime >= '");
        jpql.append(dateStr).append("' and d.eventTime <= '").append(dateStr).append(" 23:59:59' ");
        jpql.append(" and d.device.id = ").append(devices.getId());
        jpql.append(" order by d.eventTime ");
        List<DeviceData> strings = baseDao.getByJpql(jpql.toString());
        List<String> data = new ArrayList<>();
        for(DeviceData dd : strings){
            if(isTemperature){
                data.add(String.valueOf(dd.getTemperature()));
            }else{
                data.add(String.valueOf(dd.getHumidity()));
            }
        }
        object.put(yAxisName,data);
    }
    
    @Override
    public JSONObject getTodayTemperatureDeviceData() {
        JSONObject object = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(new Date());
        List<Devices> devices = devicesService.getDevices();
        for(Devices d : devices){
            getTodayYAxis(object,d,dateStr,d.getDeviceName(),true);
        }
        object.put("xAxis",getTodayXAxis());
        return object;
    }

    @Override
    public JSONObject getTodayHumidityDeviceData() {
        JSONObject object = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(new Date());
        List<Devices> devices = devicesService.getDevices();
        for(Devices d : devices){
            getTodayYAxis(object,d,dateStr,d.getDeviceName(),false);
        }
        object.put("xAxis",getTodayXAxis());
        return object;
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
                yData.add(String.valueOf(dd.getTemperature()));
            }
        }else if ("humidity".equals(type)){
            for(DeviceData dd : list){
                xData.add(sdf.format(dd.getEventTime()));
                yData.add(String.valueOf(dd.getHumidity()));
            }
        }else{
            List<String> zData = new ArrayList<>();
            for(DeviceData dd : list){
                xData.add(sdf.format(dd.getEventTime()));
                yData.add(String.valueOf(dd.getHumidity()));
                zData.add(String.valueOf(dd.getTemperature()));
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
