package com.bds.system.auth_demo.controller.noaop;

import com.alibaba.fastjson.JSONObject;
import com.bds.system.auth_demo.model.Const;
import com.bds.system.auth_demo.model.Result;
import com.bds.system.auth_demo.service.DataService;
import com.bds.tool.bds_auth.impl.AuthImpl;
import com.bds.tool.bds_auth.utils.RequestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by guoyu on 2018/7/9.
 */
@Controller
public class DataController1 {
    @Resource
    private DataService dataService;

    @RequestMapping(value = "/service/get_data1", method = RequestMethod.GET)
    @ResponseBody
    public Result auth(HttpServletRequest request) throws Exception {
        Result result = new Result();

        String token = request.getHeader("Authorization") == null
                ? RequestUtils.getCookieValueByName(request.getCookies(), "token")
                : request.getHeader("Authorization");
        JSONObject jsonObject = new AuthImpl().auth(Const.APP_ID, Const.SECRET, token, null);
        if (jsonObject != null) {
            String certResult = dataService.getData(jsonObject);
            if (certResult == null) {
                result.setCode("0");
            } else {
                result.setCode("1");
                result.setData(certResult);
            }
        } else {
            result.setCode("0");
        }
        return result;
    }
}
