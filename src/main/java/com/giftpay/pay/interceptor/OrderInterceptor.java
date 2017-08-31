package com.giftpay.pay.interceptor;

import com.alibaba.fastjson.JSON;
import com.giftpay.pay.common.result.CommonResult;
import com.giftpay.pay.common.service.UserService;
import com.giftpay.pay.common.service.impl.AccessTokenServiceImpl;
import com.giftpay.pay.common.service.impl.UserServiceImpl;
import com.giftpay.pay.order.service.impl.OrderServiceImpl;
import com.giftpay.pay.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by admin on 2017/3/14.
 */
public class OrderInterceptor extends HandlerInterceptorAdapter {
    private final Logger log = LoggerFactory.getLogger(OrderInterceptor.class);

    private final String _GETPAYAPI = "/api/getPayApi";

    private final String _TOPAY = "/api/toPay";


    /*
     * 利用正则映射到需要拦截的路径

    private String mappingURL;

    public void setMappingURL(String mappingURL) {
               this.mappingURL = mappingURL;
    }
  */
    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true
     *    执行下一个拦截器,直到所有的拦截器都执行完毕
     *    再执行被拦截的Controller
     *    然后进入拦截器链,
     *    从最后一个拦截器往回执行所有的postHandle()
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        CommonResult commonResult = CommonResult.getInstance(0,null);
        String PROJECT_NAME=request.getContextPath();
        String URL=request.getRequestURI();
        //过滤相应的URL
        String COMPARE_URL=URL.substring(PROJECT_NAME.length());


        if(COMPARE_URL.startsWith(_GETPAYAPI)){
            if ("GET".equalsIgnoreCase(request.getMethod())) {
                log.error("请求方式出错");
                commonResult = CommonResult.getInstance(1001,"请求方式出错");
            }
            /*commonResult = ((UserServiceImpl)SpringContextUtil.getBean("UserService")).checConfig(request,response);
            if(commonResult.getCode()==0){
                return true;
            }*/
        }else if(COMPARE_URL.startsWith(_TOPAY)){
            commonResult = ((CheckService)SpringContextUtil.getBean("CheckService")).checkOrder(request,response);
        }

        if(commonResult.getCode()!=0){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.append(JSON.toJSONString(commonResult));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
                return false;
            }
        }
        return true;
    }


    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 可在modelAndView中加入数据，比如当前时间
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info("==============执行顺序: 2、postHandle================");
        if(modelAndView != null){  //加入当前时间
            modelAndView.addObject("var", "测试postHandle");
        }
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     *
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        log.info("==============执行顺序: 3、afterCompletion================");
    }

}
