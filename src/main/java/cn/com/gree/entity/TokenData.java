package cn.com.gree.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_token")
public class TokenData {

    /** id */
    @Id
    @GeneratedValue
    private Long id;

    /** token */
    @Column(length = 50)
    private String token;

    /** 获取时间 */
    @Column
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
