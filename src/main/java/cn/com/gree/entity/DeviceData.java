package cn.com.gree.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_device_data")
public class DeviceData {

    /** id */
    @Id
    @GeneratedValue
    private Long id;

    /** 设备温度 */
    @Column
    private Integer temperature;

    /** 设备湿度 */
    @Column
    private Integer humidity;

    /** 是否在线 */
    @Column
    private boolean onLine;

    /** 数据时间 */
    @Column
    private Date eventTime;

    /** 写入数据时间 */
    @Column
    private Date time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public boolean isOnLine() {
        return onLine;
    }

    public void setOnLine(boolean onLine) {
        this.onLine = onLine;
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
}
