package com.lee.vshare.common.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sys_log")
public class SystemLog implements Serializable {
    /**
     * 主键
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 操作地址的IP
     */
    private String ip;

    /**
     * 操作时间
     */
    @Column(name = "create_by")
    private Date createBy;

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
    @Column(name = "operate_by")
    private String operateBy;

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
     * @return create_by - 操作时间
     */
    public Date getCreateBy() {
        return createBy;
    }

    /**
     * 设置操作时间
     *
     * @param createBy 操作时间
     */
    public void setCreateBy(Date createBy) {
        this.createBy = createBy;
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
     * @return operate_by - 操作的浏览器
     */
    public String getOperateBy() {
        return operateBy;
    }

    /**
     * 设置操作的浏览器
     *
     * @param operateBy 操作的浏览器
     */
    public void setOperateBy(String operateBy) {
        this.operateBy = operateBy == null ? null : operateBy.trim();
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
        sb.append(", remark=").append(remark);
        sb.append(", operateUrl=").append(operateUrl);
        sb.append(", operateBy=").append(operateBy);
        sb.append("]");
        return sb.toString();
    }
}