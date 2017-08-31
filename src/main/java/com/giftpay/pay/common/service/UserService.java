package com.giftpay.pay.common.service;

import com.giftpay.pay.common.model.User;
import com.giftpay.pay.common.result.PageResult;

import java.util.List;

public interface UserService {

    List<User> getAllUser();

    User getUserByPhoneOrEmail(String emailOrPhone, Short state);

    User getUserById(Long userId);

    PageResult<User> get(int pageNum,int pageSize);
}
