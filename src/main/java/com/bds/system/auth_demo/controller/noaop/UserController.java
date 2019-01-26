package com.bds.system.auth_demo.controller.noaop;

import com.alibaba.fastjson.JSONObject;
import com.bds.system.auth_demo.model.Result;
import com.bds.system.auth_demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by guoyu on 2018/7/9.
 */
@Controller
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/service/login", method = RequestMethod.GET)
    @ResponseBody
    public Result auth(HttpServletResponse response, String userName, String pwd) throws Exception {
        Result result = new Result();

        JSONObject authResult = userService.login(userName, pwd);
        if (authResult == null) {
            result.setCode("0");
        } else {
            result.setCode("1");
            result.setData(authResult.getString("data"));

            //可选操作，添加csrf认证，防止csrf攻击
//            Cookie[] cookies = new CsrfImpl().getCsrfCookies(Const.SECRET);
//            for (Cookie cookie : cookies) {
//                response.addCookie(cookie);
//            }
            Cookie cookie = new Cookie("token", authResult.getString("data"));
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return result;
    }

    @RequestMapping(value = "/service/reg", method = RequestMethod.GET)
    @ResponseBody
    public Result reg(String userName, String pwd,String phone,String email) {
        Result result = new Result();

//        boolean flag = userService.reg(userName, pwd,phone,email);
        boolean flag = userService.reg("cc", "1234","17693433417","dcc668@163.com");
        if (flag) {
            result.setCode("1");
        } else {
            result.setCode("0");
        }
        return result;
    }
}
