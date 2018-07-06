package cn.com.gree.service.impl;

import cn.com.gree.dao.BaseDao;
import cn.com.gree.entity.Devices;
import cn.com.gree.service.DevicesService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("DevicesService")
public class DevicesServiceImpl implements DevicesService {

    @Resource(name = "BaseDao")
    private BaseDao baseDao;

    @Override
    public List<Devices> getDevices() {
        return baseDao.getByJpql(" select o from Devices o order by o.id ");
    }

    @Override
    public List<JSONObject> getDevicesOption() {
        List<Devices> devices = getDevices();
        List<JSONObject> result = new ArrayList<>();
        for(Devices d : devices){
            JSONObject object = new JSONObject();
            object.put("id",d.getId());
            object.put("area",d.getArea());
            object.put("deviceName",d.getDeviceName());
            object.put("minTemperature",d.getMinTemperature());
            object.put("maxTemperature",d.getMaxTemperature());
            object.put("minHumidity",d.getMinHumidity());
            object.put("maxHumidity",d.getMaxHumidity());
            object.put("mail",d.getMail());
            object.put("propelMail",d.isPropelMail());
            result.add(object);
        }
        return result;
    }

    @Override
    public List<JSONObject> getAllDevices() {
        List<JSONObject> objects = new ArrayList<>();
        List<Devices> devices = getDevices();
        for(Devices device : devices){
            JSONObject object = new JSONObject();
            object.put("id",device.getId());
            object.put("deviceName",device.getDeviceName());
            objects.add(object);
        }
        return objects;
    }

    @Override
    public boolean updateOption(String data) {
        JSONArray array = JSONArray.fromObject(data);
        for(int i = 0 ; i < array.size() ; i++){
            JSONObject o = array.getJSONObject(i);
            Devices d = baseDao.getById(Devices.class, o.getLong("id"));
            d.setArea(o.getString("area"));
            d.setDeviceName(o.getString("deviceName"));
            d.setMinTemperature(o.getInt("minTemperature"));
            d.setMaxTemperature(o.getInt("maxTemperature"));
            d.setMinHumidity(o.getInt("minHumidity"));
            d.setMaxHumidity(o.getInt("maxHumidity"));
            d.setMail(o.getString("mail"));
            d.setPropelMail(o.getBoolean("propelMail"));
        }
        return true;
    }
}
