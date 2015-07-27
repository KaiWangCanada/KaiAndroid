package com.mmfcommon.event;


import com.mmfcommon.bean.CommonJsonBean;

/**
 * Created by longbin on 15/1/20.
 */
public interface HttpResponseListener {

    public void onSuccess(CommonJsonBean commonJsonBean);

    public void onFailure(String content);

    public void onNotLogin();
}
