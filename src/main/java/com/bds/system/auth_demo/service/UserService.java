package com.bds.system.auth_demo.service;

import com.alibaba.fastjson.JSONObject;
import com.bds.system.auth_demo.model.Const;
import com.bds.tool.bds_auth.CertInterface;
import com.bds.tool.bds_auth.RegInterface;
import com.bds.tool.bds_auth.impl.CertImpl;
import com.bds.tool.bds_auth.impl.RegImpl;
import com.bds.tool.bds_auth.utils.MD5Util;
import org.nutz.dao.entity.Record;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    public JSONObject login(String userName, String pwd) {
        String extData = null;
        extData = "phone,create_time";
        Long maxAge = 1000L * 3600 * 8;

        // 认证
        CertInterface certInterface = new CertImpl();
        JSONObject jsonObject = null;
        try {
            jsonObject = certInterface.cert(Const.APP_ID, Const.SECRET, userName, pwd, extData, maxAge);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean reg(String userName, String pwd,String phone,String email) {
        Record record = new Record();
        record.put("user_name", userName);
        record.put("pwd", MD5Util.MD5(pwd));
        record.put("phone", phone);
        record.put("email", email);
        record.put("create_time", new Date());

        RegInterface regInterface = new RegImpl();
        try {
            regInterface.reg(Const.APP_ID, Const.SECRET, record.toString());
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
