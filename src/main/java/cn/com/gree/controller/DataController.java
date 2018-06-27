package cn.com.gree.controller;

import cn.com.gree.service.DataService;
import cn.com.gree.utils.Result;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class DataController {

    @Resource(name = "DataService")
    private DataService dataService;

    /**
     * @author 260172
     * @date 2018/6/27 8:44
     * 获取最新的设备数据
     */
    @RequestMapping("getDeviceData")
    public Result getDeviceData(){
        List<JSONObject> list = dataService.getMaxDateData();
        if(list != null && list.size() > 0){
            return new Result(true,"success", list);
        }else{
            return new Result(false,"data not found");
        }
    }
}
