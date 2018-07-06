package cn.com.gree.service;

import cn.com.gree.entity.DeviceData;
import cn.com.gree.entity.Devices;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.List;

public interface DeviceDataService {

    /**
     * @author 260172
     * @date 2018/6/27 16:20
     * 获取数据库中最新数据
     */
    List<JSONObject> getMaxDateData(boolean flag);

    /**
     * @author 260172
     * @date 2018/6/27 17:03
     * 判断数据库是否含有此条数据
     */
    boolean judgeDeviceDataIsExist(DeviceData dd);


    /**
     * @author 260172
     * @date 2018/6/27 17:27
     * 获取对应设备的最新一条数据
     */
    DeviceData getDeviceData(Devices d);


    /**
     * @author 260172
     * @date 2018/6/28 8:43
     * 获取今天温度数据
     */
    JSONObject getTodayTemperatureDeviceData();

    /**
     * @author Abin
     * @date 2018/7/5 18:10
     * 获取今天横坐标的数据
     */
    public List<String> getTodayXAxis();

    /**
     * @author 260172
     * @date 2018/6/28 8:48
     * 获取今天湿度数据
     */
    JSONObject getTodayHumidityDeviceData();

    /**
     * @author 260172
     * @date 2018/6/27 18:01
     * 获取设备的历史数据
     */
    JSONObject getDeviceHistoryData(String id, String type, Date startTime, Date endTime);


}
