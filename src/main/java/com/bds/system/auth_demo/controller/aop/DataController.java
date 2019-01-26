package com.bds.system.auth_demo.controller.aop;

import com.alibaba.fastjson.JSONObject;
import com.bds.system.auth_demo.model.Const;
import com.bds.system.auth_demo.model.Result;
import com.bds.system.auth_demo.service.DataService;
import com.bds.tool.bds_auth.impl.CertImpl;
import com.bds.tool.bds_auth.impl.DataImpl;
import com.bds.tool.bds_auth.utils.AuthConst;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by guoyu on 2018/7/11.
 */
@Controller
public class DataController {
    @Resource
    private DataService dataService;

    @RequestMapping(value = "/service/get_data", method = RequestMethod.GET)
    @ResponseBody
    public Result auth(HttpServletRequest request) throws Exception {
        Result result = new Result();

        String certResult = dataService.getData();
        if (certResult == null) {
            result.setCode("0");
        } else {
            result.setCode("1");
            result.setData(certResult);
        }

        return result;
    }

    @RequestMapping(value = "/service/get_data_by_id", method = RequestMethod.GET)
    @ResponseBody
    public Result getDataById(HttpServletRequest request, String uid) throws Exception {
        Result result = new Result();

        JSONObject certResult = new DataImpl()
                .getData(Const.APP_ID, Const.SECRET, Long.parseLong(uid), AuthConst.GET_USER_INFO_BY_UID);
        if (certResult == null) {
            result.setCode("0");
        } else {
            result.setCode("1");
            result.setData(certResult);
        }

        return result;
    }
}
