package com.lee.vshare.service.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lee.vshare.service.dao.AccountMapper;
import com.lee.vshare.common.entity.Account;
import com.lee.vshare.common.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import tk.mybatis.mapper.entity.Example;

import java.net.URI;
import java.util.Date;

import static com.lee.vshare.service.common.Container.SMS_APPCODE;
import static com.lee.vshare.service.common.Container.SMS_HOST;
import static com.lee.vshare.service.common.Container.SMS_TPL_ID;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 19-4-16
 * @Time 下午3:45
 */
@Service(version = "1.0.0")
public class AccountServiceImpl extends BaseService<Account> implements AccountService {

    Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AccountMapper accountMapper;

    @Override
    public Integer getCode(String phoneNumber, String code) {
        Example example = new Example(Account.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.orEqualTo("accountPhone", phoneNumber);
        int count = accountMapper.selectCountByExample(example);
        if (count > 0) {//已注册
            return 2;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "APPCODE " + SMS_APPCODE);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("mobile", phoneNumber);
        body.add("param", "code:" + code);
        body.add("tpl_id", SMS_TPL_ID);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(SMS_HOST, requestEntity, String.class);
        logger.info("getCode : response = " + response.toString());
        if (200 != response.getStatusCodeValue()) {
            return -1;
        }
        if (null == response.getBody() || !response.getBody().contains("00000")) {
            return -2;
        }
        logger.info("getCode : body = " + response.getBody());
        return 1;
    }

    @Override
    public Integer register(String userName, String password, String phone) {
        Date data = new Date();
        Account account = new Account();
        account.setAccountId(10001L);
        account.setAccountEmail("");
        account.setAcountIcon("");
        account.setAccountSex(0);
        account.setAccountCreateTime(data);
        account.setAccountPhone(phone);
        account.setAccountName(userName);
        account.setAccountPassword(password);
        account.setAccountLoginTime(data);
        return accountMapper.insert(account);
    }

    @Override
    public Account login(String userName, String password) {
        Example example = new Example(Account.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.orEqualTo("accountName", userName);
        criteria.orEqualTo("accountPhone", userName);
        criteria.andEqualTo("accountPassword", password);
        return accountMapper.selectOneByExample(example);
    }

    private void getWeather(String city) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(
                "http://v.juhe.cn/weather/index?cityname={cityname}&dtype={dtype}&format={format}&key={key}")
                .build()
                .expand(city, "json", "1", "f3ad4c0279fbcc4f43ced93812171eaa")
                .encode();
        URI uri = uriComponents.toUri();
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        int code = response.getStatusCodeValue();
        logger.info("getWeather : code = " + code);
        logger.info("getWeather : response = " + response.toString());

    }
}
