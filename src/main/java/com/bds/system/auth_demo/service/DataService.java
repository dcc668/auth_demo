package com.bds.system.auth_demo.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

/**
 * Created by guoyu on 2018/7/9.
 */
@Service
public class DataService {
    public String getData() {
        return "这是需要授权才能获得的数据";
    }

    public String getData(JSONObject jsonObject) {
        return "这是需要授权才能获得的数据：用户ID：" + jsonObject.getJSONObject("data").getString("user_id");
    }
}
