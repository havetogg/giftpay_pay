package com.giftpay.pay.common.service.impl;

import com.giftpay.pay.common.dao.AccessTokenDao;
import com.giftpay.pay.common.model.AccessToken;
import com.giftpay.pay.common.result.CommonResult;
import com.giftpay.pay.common.service.AccessTokenService;
import com.giftpay.pay.utils.AESUtil;
import com.giftpay.pay.utils.MD5Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;


@Service("AccessTokenService")
@Transactional(rollbackFor = Exception.class)
public class AccessTokenServiceImpl implements AccessTokenService{

    private Logger logger = Logger.getLogger(AccessTokenServiceImpl.class);

    @Resource
    private AccessTokenDao accessTokenDao;

    @Value(value = "#{configProperties['tokenKey']}")
    private String key;

    @Override
    public void insertAccessToken(AccessToken accessToken) {
        accessTokenDao.insertAccessToken(accessToken);
    }

    @Override
    public AccessToken getAccessTokenByUid(String uid) {
        return accessTokenDao.getAccessTokenByUid(uid);
    }


}
