package com.mmfcommon.event;


import com.mmfcommon.bean.CommonJsonBean;

/**
 * Created by longbin on 15/1/20.
 */
public abstract class MMFHttpResponse implements HttpResponseListener {
    @Override
    public void onSuccess(CommonJsonBean commonJsonBean) {

    }

    @Override
    public void onFailure(String content) {

    }

    @Override
    public void onNotLogin() {

    }
}
