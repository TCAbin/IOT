package cn.com.gree.service;

import cn.com.gree.entity.Devices;
import net.sf.json.JSONObject;

import java.util.List;

public interface DevicesService {

    /**
     * @author 260172
     * @date 2018/6/27 16:52
     * 获取所有设备
     */
    List<Devices> getDevices();

    /**
     * @author 260172
     * @date 2018/6/28 14:19
     * 获取所有设备信息
     */
    List<JSONObject> getDevicesOption();

    /**
     * @author 260172
     * @date 2018/6/27 17:28
     * 获取下拉框的信息
     */
    List<JSONObject> getAllDevices();


    /**
     * @author 260172
     * @date 2018/6/28 14:15
     * 保存设备信息数据
     */
    boolean updateOption(List<Devices> devices);

}
