package cn.com.gree.service.impl;

import cn.com.gree.dao.BaseDao;
import cn.com.gree.entity.Devices;
import cn.com.gree.service.DevicesService;
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
        return baseDao.getByJpql(" select o from Devices o group by o.deviceId");
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
}
