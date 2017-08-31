package com.giftpay.pay.common.dao;

import com.giftpay.pay.common.model.AccessToken;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by Administrator on 2017/3/22.
 */
@Repository
public interface AccessTokenDao {
    void insertAccessToken(AccessToken accessToken);

    AccessToken getAccessTokenByUid(@Param("uid") String uid);

    Date getCreateDateByUid(@Param("uid") String uid);
}
