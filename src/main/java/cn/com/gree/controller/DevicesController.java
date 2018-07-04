package cn.com.gree.controller;

import cn.com.gree.aop.annotation.DataBaseLog;
import cn.com.gree.aop.annotation.Operate;
import cn.com.gree.service.DevicesService;
import cn.com.gree.utils.Result;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("devices")
public class DevicesController {

    @Resource(name = "DevicesService")
    private DevicesService devicesService;

    /**
     * @author 260172
     * @date 2018/6/28 14:24
     * 获取所有设备信息
     */
    @RequestMapping("getDevicesOption")
    public Result getDevicesOption(){
        List<JSONObject> devices = devicesService.getDevicesOption();
        if(devices.size() > 0){
            return new Result(true,"success",devices);
        }else{
            return new Result(false,"failed");
        }
    }

    /**
     * @author 260172
     * @date 2018/6/27 17:32
     * 获取下拉框的所有设备
     */
    @RequestMapping("getAllDevices")
    public Result getAllDevices(){
        List<JSONObject> objects = devicesService.getAllDevices();
        if(objects.size() > 0){
            return new Result(true,"success",objects);
        }else{
            return new Result(false,"failed");
        }
    }

    /**
     * @author 260172
     * @date 2018/6/28 14:14
     * 更新设备信息
     */
    @RequestMapping(value = "updateOption",method = RequestMethod.POST)
    @DataBaseLog(operate = Operate.UPDATE,table = "t_devices",module = "设备模块")
    public Result updateOption(String data){
        devicesService.updateOption(data);
        return new Result(true,"success");
    }

}
