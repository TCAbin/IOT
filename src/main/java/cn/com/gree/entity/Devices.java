package cn.com.gree.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_devices")
public class Devices {

    /** id */
    @Id
    @GeneratedValue
    private Long id;

    /** 设备名称 */
    @Column(length = 30)
    private String deviceName;

    /** 设备ID
     * 00f6274b-4a47-478c-8a7f-7ebd3c3e25f9
     */
    @Column
    private String deviceId;

    /** 区域 */
    @Column(length = 30)
    private String area;

    /** 最小温度 */
    @Column
    private Integer minTemperature;

    /** 最大温度 */
    @Column
    private Integer maxTemperature;

    /** 最小湿度 */
    @Column
    private Integer minHumidity;

    /** 最大湿度 */
    @Column
    private Integer maxHumidity;

    /** 是否启用邮件推送 */
    private boolean propelMail;

    /** 邮箱号 */
    @Column
    private String mail;

    /** 关联的设备 */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id")
    private List<DeviceData> deviceData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Integer minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Integer getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Integer maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Integer getMinHumidity() {
        return minHumidity;
    }

    public void setMinHumidity(Integer minHumidity) {
        this.minHumidity = minHumidity;
    }

    public Integer getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(Integer maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public List<DeviceData> getDeviceData() {
        return deviceData;
    }

    public void setDeviceData(List<DeviceData> deviceData) {
        this.deviceData = deviceData;
    }

    public boolean isPropelMail() {
        return propelMail;
    }

    public void setPropelMail(boolean propelMail) {
        this.propelMail = propelMail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
