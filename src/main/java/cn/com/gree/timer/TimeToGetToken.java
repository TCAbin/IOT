package cn.com.gree.timer;

import cn.com.gree.dao.BaseDao;
import cn.com.gree.entity.DeviceData;
import cn.com.gree.entity.Devices;
import cn.com.gree.service.DeviceDataService;
import cn.com.gree.service.DevicesService;
import cn.com.gree.service.TokenDataService;
import cn.com.gree.utils.Result;
import cn.com.gree.utils.springMail.SpringMail;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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
    @Scheduled(cron = "0 0/16 * * * *")
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
                baseDao.save(dd);
//                sendMail(d,dd);
            } else {
                return false;
            }
        }
        return true;
    }


    /**
     * @author 260172
     * @date 2018/6/27 16:56
     * 监测是否要报警
     */
    private void sendMail(Devices d,DeviceData dd){
        if(dd.getTemperature() < d.getMinTemperature() || dd.getTemperature() > d.getMaxTemperature()){
            // 推送温度邮件
            mail(d,"温度",dd);
        }
        if (dd.getHumidity() < d.getMinHumidity() || dd.getHumidity() > d.getMaxHumidity()){
            // 推送湿度邮件
            mail(d,"湿度",dd);
        }
    }

    /**
     * @author Abin
     * @date 2018/6/29 16:52
     * 邮件
     */
    private void mail(Devices d,String title,DeviceData dd){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuffer mail = new StringBuffer("<div>");
        mail.append("<div><h3>").append(title).append("报警</h3></div>")
                .append("<div>数据获取时间 : ").append(sdf.format(dd.getTime())).append("</div>")
                .append("<div>数据监测时间 : ").append(sdf.format(dd.getEventTime())).append("</div>")
                .append("<div>设备名称 : ").append(d.getDeviceName()).append("</div>")
                .append("<div>地点 : ").append(d.getArea()).append("</div>");
        if(title.contains("温度")){
            mail.append("<div>温度值 : ").append("<font style='color:red'>").append(dd.getTemperature()).append("℃</font></div>");
            mail.append("<div>湿度值 : ").append(dd.getHumidity()).append("%</div>");
        }else{
            mail.append("<div>温度值 : ").append(dd.getTemperature()).append("℃</div>");
            mail.append("<div>湿度值 : ").append("<font style='color:red'>").append(dd.getHumidity()).append("%</font></div>");
        }
        mail.append("<div><a href='' target='_blank' style='text-decoration: underline;'>系统主页</a></div>")
                .append("</div>");
        SpringMail.sendMail(d.getMail(),"iamatestmail@163.com","合肥基地机房" + title + "报警",mail.toString(),null);
    }


}
