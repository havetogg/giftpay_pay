package com.giftpay.pay.common.service.impl;

import com.giftpay.pay.common.dao.UserDao;
import com.giftpay.pay.common.model.User;
import com.giftpay.pay.common.result.CommonResult;
import com.giftpay.pay.common.result.PageResult;
import com.giftpay.pay.common.service.UserService;
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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service("UserService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private Logger logger = Logger.getLogger(UserServiceImpl.class);
    
    @Resource
    private UserDao userDao;

    @Value(value = "#{configProperties['userKey']}")
    private String key;

    public User getUserById(Long userId) {
        return userDao.selectUserById(userId);
    }

    @Override
    public PageResult<User> get(int pageNum, int pageSize) {
        if(pageSize>1000){
            return null;
        }
        PageResult<User> pageResult = new PageResult<>();
        pageResult.setPageSize(pageSize);
        pageResult.setPageNum(pageNum);
        User user = new User();
        user.pageNum = pageNum;
        user.pageSize = pageSize;
        int count = userDao.getCount();
        pageResult.setTotalCount(count);
        List<User> users = userDao.getPageByCondition(user);
        pageResult.setDatas(users);
        return pageResult;
    }

    public User getUserByPhoneOrEmail(String emailOrPhone, Short state) {
        return userDao.selectUserByPhoneOrEmail(emailOrPhone,state);
    }
    
    public List<User> getAllUser() {
        return userDao.selectAllUser();
    }

    public CommonResult checConfig(HttpServletRequest request, HttpServletResponse response){
        CommonResult commonResult = null;
        String timeStamp = request.getParameter("timeStamp");
        String userId = request.getParameter("userId");
        String sign = request.getParameter("sign");

        if(StringUtils.isEmpty(timeStamp)||StringUtils.isEmpty(userId)||StringUtils.isEmpty(sign)){
            logger.error("有参数为空");
            commonResult = CommonResult.getInstance(1002,"有参数为空");
            return commonResult;
        }
        logger.info("timeStamp为"+timeStamp);
        logger.info("userId为"+userId);
        logger.info("sign为"+sign);

        long computeTimeStamp = Long.parseLong(AESUtil.AESDncode(key,timeStamp));
        Long nowTime = System.currentTimeMillis();
        Long s = (nowTime - computeTimeStamp) / (1000 * 60);
        if(s>=5){
            logger.error("时间戳时间距离当前时间太久");
            commonResult = CommonResult.getInstance(1003,"时间戳时间距离当前时间太久");
            return commonResult;
        }

        Map<String,String> sortedMap = new TreeMap<>();
        sortedMap.put("timeStamp",timeStamp);
        sortedMap.put("userId",userId);
        sortedMap.put("sign",sign);

        StringBuffer stringBuffer = new StringBuffer();
        for(String key:sortedMap.keySet()){
            if(!"sign".equals(key)){
                stringBuffer.append("&"+key+"="+sortedMap.get(key));
            }
        }
        stringBuffer.append("&key="+key);
        stringBuffer.deleteCharAt(0);
        logger.error("stringBuffer为"+stringBuffer);
        String computeSign = MD5Util.getMD5(stringBuffer.toString());
        logger.error("computeSign为"+computeSign);
        if(computeSign.equals(sign)){
            logger.info("参数匹配正确，拦截器跳转控制层");
            commonResult = CommonResult.getInstance(0,null);
            return commonResult;
        }else{
            logger.error("sign校验错误");
            commonResult = CommonResult.getInstance(1003,"sign校验错误");
            return commonResult;
        }
    }
}
