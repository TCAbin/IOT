package cn.com.gree.test;

import cn.com.gree.entity.DeviceData;
import cn.com.gree.entity.Devices;
import cn.com.gree.service.DeviceDataService;
import cn.com.gree.service.DevicesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class GetData {

    @Resource(name = "DevicesService")
    private DevicesService devicesService;

    @Resource(name = "DeviceDataService")
    private DeviceDataService deviceDataService;

    @Test
    public void test(){
        List<Devices> devices = devicesService.getDevices();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Devices d : devices){
            DeviceData dd = deviceDataService.getDeviceData(d);
            System.out.println("device_status : " + dd.getDeviceStatus() + "\n" +
            "event_time : " + sdf.format(dd.getEventTime()) + "\n" +
            "humidity : " + dd.getHumidity() + "\n" +
            "temperature : " + dd.getTemperature() + "\n" +
            "time : " + sdf.format(dd.getTime()) + "\n" +
            "device_id : " + d.getId() + "\n");
        }
    }
}
