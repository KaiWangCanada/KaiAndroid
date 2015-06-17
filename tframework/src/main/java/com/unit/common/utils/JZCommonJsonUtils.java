package com.unit.common.utils;

import com.unit.common.R;
import com.unit.common.application.FrameBaseApplication;
import com.unit.common.db.JZCommonJsonBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by longbin on 15/1/17.
 */
public class JZCommonJsonUtils {

    public static JZCommonJsonBean parseCommonJson(String content) {
        JZCommonJsonBean commonJsonBean = new JZCommonJsonBean();
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
            commonJsonBean.setMsg(FrameBaseApplication.appContext.getString(R.string.no_data));
            e.printStackTrace();
        }
        return commonJsonBean;
    }
}
