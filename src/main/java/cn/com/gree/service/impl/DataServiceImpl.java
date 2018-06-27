package cn.com.gree.service.impl;

import cn.com.gree.dao.BaseDao;
import cn.com.gree.entity.DeviceData;
import cn.com.gree.entity.Devices;
import cn.com.gree.service.DataService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service("DataService")
public class DataServiceImpl implements DataService {

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
}
