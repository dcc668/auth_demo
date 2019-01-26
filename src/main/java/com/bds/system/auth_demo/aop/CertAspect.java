package com.bds.system.auth_demo.aop;

import com.alibaba.fastjson.JSONObject;
import com.bds.system.auth_demo.model.Const;
import com.bds.tool.bds_auth.AuthInterface;
import com.bds.tool.bds_auth.impl.AuthImpl;
import com.bds.tool.bds_auth.utils.RequestUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Base64;

/**
 * Created by guoyu on 2018/7/9.
 */
@Aspect
@Component
public class CertAspect {
    @Pointcut("execution(public * com.bds.system.auth_demo.controller.aop.*.*(..))")
    public void doCert() {
    }

    @Before("doCert()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        System.out.println("URL : " + request.getRequestURL().toString());
        System.out.println("IP : " + request.getRemoteAddr());
        System.out.println("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        System.out.println("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        String token = request.getHeader("Authorization");

        if (!request.getRequestURL().toString().contains("service/login")) {
            if (token == null) {
                //可选操作，验证cookie，防止csrf
                Cookie[] cookies = request.getCookies();
                //获取token
                token = RequestUtils.getCookieValueByName(cookies, "token");
            }
//            if (!new CsrfImpl().checkCsrfCookies(cookies, Const.SECRET)) {
//                System.out.println("认证csrf异常");
//                throw new RuntimeException("认证csrf异常");
//            }

            // 授权
            AuthInterface authInterface = new AuthImpl();
            JSONObject jsonObject1 = null;
            try {
                jsonObject1 = authInterface.auth(Const.APP_ID, Const.SECRET, token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (jsonObject1.getString("code").equals("suc")) {
                System.out.println(jsonObject1);
            } else {
                System.out.println("认证异常");
                throw new RuntimeException("认证异常");
            }
        }
    }

//    @AfterReturning(returning = "ret", pointcut = "doCert()")
//    public void doAfterReturning(Object ret) throws Throwable {
//        // 处理完请求，返回内容
//        System.out.println("方法的返回值 : " + ret);
//    }
//
//    //环绕通知,环绕增强，相当于MethodInterceptor
//    @Around("doCert()")
//    public Object arround(ProceedingJoinPoint pjp) {
//        System.out.println("方法环绕start.....");
//        try {
//            Object o = pjp.proceed();
//            System.out.println("方法环绕proceed，结果是 :" + o);
//            return o;
//        } catch (Throwable e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
