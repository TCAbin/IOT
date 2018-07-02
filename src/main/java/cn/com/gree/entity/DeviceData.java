package cn.com.gree.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_device_data")
public class DeviceData {

    /** id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 设备温度 */
    @Column
    private Double temperature;

    /** 设备湿度 */
    @Column
    private Double humidity;

    /** 设备status
     * 1 online 在线
     * 2 offline 离线
     * 3 inbox  ？？
     * 4 abnormal 异常
     * 5 未知
     * */
    @Column
    private Integer deviceStatus;

    /** 数据时间 */
    @Column
    private Date eventTime;

    /** 写入数据时间 */
    @Column
    private Date time;

    /** 关联的设备 */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id")
    private Devices device;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Devices getDevice() {
        return device;
    }

    public void setDevice(Devices device) {
        this.device = device;
    }
}
