package com.lee.vshare.common.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "v_visit_log")
public class VisitLog implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 操作地址的IP
     */
    private String ip;

    /**
     * 操作时间
     */
    private Date time;

    /**
     * 操作内容
     */
    private String remark;

    /**
     * 操作的访问地址
     */
    @Column(name = "operate_url")
    private String operateUrl;

    /**
     * 操作的浏览器
     */
    private String browser;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取操作地址的IP
     *
     * @return ip - 操作地址的IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置操作地址的IP
     *
     * @param ip 操作地址的IP
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 获取操作时间
     *
     * @return time - 操作时间
     */
    public Date getTime() {
        return time;
    }

    /**
     * 设置操作时间
     *
     * @param time 操作时间
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * 获取操作内容
     *
     * @return remark - 操作内容
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置操作内容
     *
     * @param remark 操作内容
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取操作的访问地址
     *
     * @return operate_url - 操作的访问地址
     */
    public String getOperateUrl() {
        return operateUrl;
    }

    /**
     * 设置操作的访问地址
     *
     * @param operateUrl 操作的访问地址
     */
    public void setOperateUrl(String operateUrl) {
        this.operateUrl = operateUrl == null ? null : operateUrl.trim();
    }

    /**
     * 获取操作的浏览器
     *
     * @return browser - 操作的浏览器
     */
    public String getBrowser() {
        return browser;
    }

    /**
     * 设置操作的浏览器
     *
     * @param browser 操作的浏览器
     */
    public void setBrowser(String browser) {
        this.browser = browser == null ? null : browser.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ip=").append(ip);
        sb.append(", time=").append(time);
        sb.append(", remark=").append(remark);
        sb.append(", operateUrl=").append(operateUrl);
        sb.append(", browser=").append(browser);
        sb.append("]");
        return sb.toString();
    }
}