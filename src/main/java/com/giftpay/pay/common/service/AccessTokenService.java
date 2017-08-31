package com.giftpay.pay.common.service;

import com.giftpay.pay.common.model.AccessToken;

/**
 * Created by Administrator on 2017/3/22.
 */
public interface AccessTokenService {
    void insertAccessToken(AccessToken accessToken);

    AccessToken getAccessTokenByUid(String uid);

}
