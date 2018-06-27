package cn.com.gree.service.impl;

import cn.com.gree.dao.BaseDao;
import cn.com.gree.entity.DeviceData;
import cn.com.gree.service.DataService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("DataService")
public class DataServiceImpl implements DataService {

    @Resource(name = "BaseDao")
    private BaseDao baseDao;


    @Override
    public List<JSONObject> getMaxDateData() {
        List<JSONObject> objects = new ArrayList<>();
        String jpql = " select o from DeviceData where in (select max(time) from test group by device) ";
        List<DeviceData> list = baseDao.getByJpql(jpql);
        for(DeviceData d : list){
            JSONObject object = new JSONObject();
            object.put("area",d.getDevice().getArea());
            object.put("temperature",d.getTemperature() + "°C");
            object.put("humidity", d.getHumidity() + "%");
            object.put("eventTime", d.getEventTime());
            objects.add(object);
        }
        return objects;
    }
}
