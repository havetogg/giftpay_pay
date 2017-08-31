package com.giftpay.pay.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 18:59 2017/8/14
 * @Modified By:
 */
@Service
@Lazy(false)
public class SpringContextUtil implements ApplicationContextAware {
    // Spring应用上下文环境
    private static ApplicationContext applicationContext;
    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     *
     * @param applicationContext
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }
    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    /**
     * 获取对象
     *
     * @param name
     * @return Object
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }
}
