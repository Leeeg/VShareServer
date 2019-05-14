package com.lee.vshare.common.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "v_account")
public class Account implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户Id
     */
    @Column(name = "account_id")
    private Long accountId;

    /**
     * 用户Name
     */
    @Column(name = "account_name")
    private String accountName;

    /**
     * 用户Password
     */
    @Column(name = "account_password")
    private String accountPassword;

    /**
     * 用户PhoneNumber
     */
    @Column(name = "account_phone")
    private String accountPhone;

    /**
     * 用户Email
     */
    @Column(name = "account_email")
    private String accountEmail;

    /**
     * 用户性别 0表示女
     */
    @Column(name = "account_sex")
    private Integer accountSex;

    /**
     * 用户注册时间
     */
    @Column(name = "account_create_time")
    private Date accountCreateTime;

    /**
     * 用户最后登陆时间
     */
    @Column(name = "account_login_time")
    private Date accountLoginTime;

    /**
     * 用户头像url
     */
    @Column(name = "acount_icon")
    private String acountIcon;

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
     * 获取用户Id
     *
     * @return account_id - 用户Id
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * 设置用户Id
     *
     * @param accountId 用户Id
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取用户Name
     *
     * @return account_name - 用户Name
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置用户Name
     *
     * @param accountName 用户Name
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    /**
     * 获取用户Password
     *
     * @return account_password - 用户Password
     */
    public String getAccountPassword() {
        return accountPassword;
    }

    /**
     * 设置用户Password
     *
     * @param accountPassword 用户Password
     */
    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword == null ? null : accountPassword.trim();
    }

    /**
     * 获取用户PhoneNumber
     *
     * @return account_phone - 用户PhoneNumber
     */
    public String getAccountPhone() {
        return accountPhone;
    }

    /**
     * 设置用户PhoneNumber
     *
     * @param accountPhone 用户PhoneNumber
     */
    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone == null ? null : accountPhone.trim();
    }

    /**
     * 获取用户Email
     *
     * @return account_email - 用户Email
     */
    public String getAccountEmail() {
        return accountEmail;
    }

    /**
     * 设置用户Email
     *
     * @param accountEmail 用户Email
     */
    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail == null ? null : accountEmail.trim();
    }

    /**
     * 获取用户性别 0表示女
     *
     * @return account_sex - 用户性别 0表示女
     */
    public Integer getAccountSex() {
        return accountSex;
    }

    /**
     * 设置用户性别 0表示女
     *
     * @param accountSex 用户性别 0表示女
     */
    public void setAccountSex(Integer accountSex) {
        this.accountSex = accountSex;
    }

    /**
     * 获取用户注册时间
     *
     * @return account_create_time - 用户注册时间
     */
    public Date getAccountCreateTime() {
        return accountCreateTime;
    }

    /**
     * 设置用户注册时间
     *
     * @param accountCreateTime 用户注册时间
     */
    public void setAccountCreateTime(Date accountCreateTime) {
        this.accountCreateTime = accountCreateTime;
    }

    /**
     * 获取用户最后登陆时间
     *
     * @return account_login_time - 用户最后登陆时间
     */
    public Date getAccountLoginTime() {
        return accountLoginTime;
    }

    /**
     * 设置用户最后登陆时间
     *
     * @param accountLoginTime 用户最后登陆时间
     */
    public void setAccountLoginTime(Date accountLoginTime) {
        this.accountLoginTime = accountLoginTime;
    }

    /**
     * 获取用户头像url
     *
     * @return acount_icon - 用户头像url
     */
    public String getAcountIcon() {
        return acountIcon;
    }

    /**
     * 设置用户头像url
     *
     * @param acountIcon 用户头像url
     */
    public void setAcountIcon(String acountIcon) {
        this.acountIcon = acountIcon == null ? null : acountIcon.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", accountId=").append(accountId);
        sb.append(", accountName=").append(accountName);
        sb.append(", accountPassword=").append(accountPassword);
        sb.append(", accountPhone=").append(accountPhone);
        sb.append(", accountEmail=").append(accountEmail);
        sb.append(", accountSex=").append(accountSex);
        sb.append(", accountCreateTime=").append(accountCreateTime);
        sb.append(", accountLoginTime=").append(accountLoginTime);
        sb.append(", acountIcon=").append(acountIcon);
        sb.append("]");
        return sb.toString();
    }
}