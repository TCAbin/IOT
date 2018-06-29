package cn.com.gree.timer;

import cn.com.gree.dao.BaseDao;
import cn.com.gree.entity.DeviceData;
import cn.com.gree.entity.Devices;
import cn.com.gree.service.DeviceDataService;
import cn.com.gree.service.DevicesService;
import cn.com.gree.service.TokenDataService;
import cn.com.gree.utils.Result;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TimeToGetToken {

    @Resource(name = "BaseDao")
    private BaseDao baseDao;

    @Resource(name = "DevicesService")
    private DevicesService devicesService;

    @Resource(name = "DeviceDataService")
    private DeviceDataService deviceDataService;

    @Resource(name = "TokenDataService")
    private TokenDataService tokenDataService;


    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * @author 260172
     * @date 2018/6/26 9:33
     * 每小时刷新token
     */
    @Scheduled(cron = "0 45 * * * *")
    private void getToken(){
        tokenDataService.refreshToken();
    }

    /**
     * @author 260172
     * @date 2018/6/26 9:51
     * 每十分钟获取一次设备数据
     */
    @Scheduled(cron = "0 0/10 * * * *")
    private void getDeviceData(){
        boolean flag = setDeviceData();
        Result result = null;
        if(flag){
            List<JSONObject> objects = deviceDataService.getMaxDateData();
            result = new Result(true,"success",objects);
        }else{
            result = new Result(false,"huawei interface error");
        }
        messagingTemplate.convertAndSend("/topic/data/hello",JSONObject.fromObject(result).toString());
    }



    /**
     * @author 260172
     * @date 2018/6/26 13:42
     * 设置设备信息
     */
    private boolean setDeviceData(){
        List<Devices> devices = devicesService.getDevices();
        for(Devices d : devices){
            DeviceData dd = deviceDataService.getDeviceData(d);
            if(dd != null && deviceDataService.judgeDeviceDataIsExist(dd)){
                sendMail(d,dd);
                baseDao.save(dd);
            } else {
                return false;
            }
        }
        return true;
    }


    /**
     * @author 260172
     * @date 2018/6/27 16:56
     * 推送邮件
     */
    private void sendMail(Devices d,DeviceData dd){
        if(d.isPropelMail() && (dd.getTemperature() < d.getMinTemperature() || dd.getTemperature() > d.getMaxTemperature() ||
                dd.getHumidity() < d.getMinHumidity() || dd.getHumidity() > d.getMaxHumidity())){
            // 报警邮件
        }
    }


}
