package com.lee.vshare.common.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tbl_message")
public class Message implements Serializable {
    /**
     * 主键
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 留言/评论内容
     */
    private String content;

    /**
     * 创建日期
     */
    @Column(name = "create_by")
    private Date createBy;

    /**
     * 邮箱，用于回复消息
     */
    private String email;

    /**
     * 用户自己定义的名称
     */
    private String name;

    /**
     * 留言/评论IP
     */
    private String ip;

    /**
     * 是否有效，默认为1为有效，0为无效
     */
    @Column(name = "is_effective")
    private Boolean isEffective;

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
     * 获取留言/评论内容
     *
     * @return content - 留言/评论内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置留言/评论内容
     *
     * @param content 留言/评论内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取创建日期
     *
     * @return create_by - 创建日期
     */
    public Date getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建日期
     *
     * @param createBy 创建日期
     */
    public void setCreateBy(Date createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取邮箱，用于回复消息
     *
     * @return email - 邮箱，用于回复消息
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱，用于回复消息
     *
     * @param email 邮箱，用于回复消息
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取用户自己定义的名称
     *
     * @return name - 用户自己定义的名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户自己定义的名称
     *
     * @param name 用户自己定义的名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取留言/评论IP
     *
     * @return ip - 留言/评论IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置留言/评论IP
     *
     * @param ip 留言/评论IP
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 获取是否有效，默认为1为有效，0为无效
     *
     * @return is_effective - 是否有效，默认为1为有效，0为无效
     */
    public Boolean getIsEffective() {
        return isEffective;
    }

    /**
     * 设置是否有效，默认为1为有效，0为无效
     *
     * @param isEffective 是否有效，默认为1为有效，0为无效
     */
    public void setIsEffective(Boolean isEffective) {
        this.isEffective = isEffective;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", content=").append(content);
        sb.append(", createBy=").append(createBy);
        sb.append(", email=").append(email);
        sb.append(", name=").append(name);
        sb.append(", ip=").append(ip);
        sb.append(", isEffective=").append(isEffective);
        sb.append("]");
        return sb.toString();
    }
}