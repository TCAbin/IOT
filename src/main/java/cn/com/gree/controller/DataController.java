package cn.com.gree.controller;

import cn.com.gree.service.DeviceDataService;
import cn.com.gree.utils.Result;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("data")
public class DataController {

    @Resource(name = "DeviceDataService")
    private DeviceDataService deviceDataService;

    /**
     * @author 260172
     * @date 2018/6/27 8:44
     * 获取最新的设备数据
     */
    @RequestMapping("getDeviceData")
    public Result getDeviceData(){
        List<JSONObject> list = deviceDataService.getMaxDateData();
        if(list != null && list.size() > 0){
            return new Result(true,"success", list);
        }else{
            return new Result(false,"data not found");
        }
    }

    /**
     * @author 260172
     * @date 2018/6/28 8:43
     * 获取今天温度数据
     */
    @RequestMapping("getTodayTemperatureDeviceData")
    public Result getTodayTemperatureDeviceData(){
        JSONObject object = deviceDataService.getTodayTemperatureDeviceData();
        if(object.size() > 0){
            return new Result(true,"success", object);
        }else{
            return new Result(false,"data not found");
        }
    }

    /**
     * @author 260172
     * @date 2018/6/28 10:06
     * 获取今天湿度数据
     */
    @RequestMapping("getTodayHumidityDeviceData")
    public Result getTodayHumidityDeviceData(){
        JSONObject object = deviceDataService.getTodayHumidityDeviceData();
        if(object.size() > 0){
            return new Result(true,"success", object);
        }else{
            return new Result(false,"data not found");
        }
    }



    /**
     * @author 260172
     * @date 2018/6/28 8:31
     * 获取历史数据
     */
    @RequestMapping("getDeviceHistoryData")
    public Result getDeviceHistoryData(String id, String type, Date startTime, Date endTime){
        JSONObject object = deviceDataService.getDeviceHistoryData(id, type, startTime, endTime);
        if(object.size() > 0){
            return new Result(true,"success", object);
        }else{
            return new Result(false,"data not found");
        }
    }

}
