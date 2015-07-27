package com.mmfcommon.utils;


import com.mmfcommon.bean.CommonJsonBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by longbin on 15/1/17.
 */
public class CommonJsonUtils {

    public static CommonJsonBean parseCommonJson(String content) {
        CommonJsonBean commonJsonBean = new CommonJsonBean();
        try {
            JSONObject contentObj = new JSONObject(content);
            int code = contentObj.optInt("code", 0);
            String msg = contentObj.optString("msg");
            String response = contentObj.optString("response");
            long ts = contentObj.optLong("ts");
            commonJsonBean.setCode(code);
            commonJsonBean.setMsg(msg);
            commonJsonBean.setResponse(response);
            commonJsonBean.setTs(ts);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commonJsonBean;
    }
}
