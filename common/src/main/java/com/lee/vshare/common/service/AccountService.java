package com.lee.vshare.common.service;

import com.lee.vshare.common.entity.Account;
import org.springframework.stereotype.Service;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 19-4-16
 * @Time 下午3:46
 */

public interface AccountService {

    Integer getCode(String phoneNumber, String code);

    Integer register(String userName, String password, String phone);

    Account login(String userName, String password);
}
