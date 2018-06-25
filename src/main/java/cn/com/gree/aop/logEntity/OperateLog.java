package cn.com.gree.aop.logEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_opterate_log")
public class OperateLog {
    /**
     * Id
     *
     */
    @Id
    @GeneratedValue
    private int opterateId;
    /**
     * 操作时间
     */
    @Column
    private Date date;
    /**
     * 操作模块
     */
    @Column(length = 30)
    private String module;
    /**
     * 操作表名
     */
    @Column(length = 30)
    private String operateTable;
    /**
     * 操作类型
     */
    @Column(length = 30)
    private String operation;
    /**
     * 用户id
     */
    @Column
    private int userId;

    public int getOpterateId() {
        return opterateId;
    }

    public void setOpterateId(int opterateId) {
        this.opterateId = opterateId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getOperateTable() {
        return operateTable;
    }

    public void setOperateTable(String operateTable) {
        this.operateTable = operateTable;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
