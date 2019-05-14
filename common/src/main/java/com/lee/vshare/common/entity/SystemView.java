package com.lee.vshare.common.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sys_view")
public class SystemView implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 访问IP
     */
    private String ip;

    /**
     * 访问时间
     */
    @Column(name = "create_by")
    private Date createBy;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取访问IP
     *
     * @return ip - 访问IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置访问IP
     *
     * @param ip 访问IP
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 获取访问时间
     *
     * @return create_by - 访问时间
     */
    public Date getCreateBy() {
        return createBy;
    }

    /**
     * 设置访问时间
     *
     * @param createBy 访问时间
     */
    public void setCreateBy(Date createBy) {
        this.createBy = createBy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ip=").append(ip);
        sb.append(", createBy=").append(createBy);
        sb.append("]");
        return sb.toString();
    }
}