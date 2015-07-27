package com.mmfcommon.api;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mmfcommon.R;
import com.mmfcommon.activity.MmfCommonAppBaseApplication;
import com.mmfcommon.bean.CommonJsonBean;
import com.mmfcommon.bean.UpdateInfoBean;
import com.mmfcommon.common.MmfCommonSetting;
import com.mmfcommon.event.ResultListener;
import com.mmfcommon.utils.CommonJsonUtils;
import com.unit.common.config.CommonSetting;
import com.unit.common.httputils.HttpRequestBaseInfo;
import com.unit.common.httputils.HttpRequestCallback;
import com.unit.common.httputils.HttpRequestCallbackHandler;
import com.unit.common.httputils.JZHttpUtils;
import com.unit.common.service.UpdateService;
import com.unit.common.ui.DialogAndToast;
import com.unit.common.utils.DisplayUtils;
import com.unit.common.utils.LogOutputUtils;
import com.unit.common.utils.PackageInfoUtils;

/**
 * Created by longbin on 15/3/16.
 */
public class CheckUpdateUtils {

    private Activity activity;

    private boolean isShowNoUpdateInfo = true;
    private ResultListener checkListener;

    public CheckUpdateUtils(Activity activity) {
        this.activity = activity;
    }

    public void setShowNoUpdateInfo(boolean isShowDialog) {
        this.isShowNoUpdateInfo = isShowDialog;
    }

    public void setCheckListener(ResultListener listener) {
        this.checkListener = listener;
    }

//    public void checkUpdate() {
//        if (activity == null) {
//            return;
//        }
//        AppsHttpRequestBaseInfo appsHttpRequestBaseInfo = AppsHttpRequestBaseInfo.GetDewuRequestBaseInfo();
//        appsHttpRequestBaseInfo.addHeader(MmfCommonSetting.Host_Head_Key, MmfCommonSetting.CLIENT_APP_KEY);
//        String ts = AppsHttpRequestBaseInfo.getTs();
//        String sign = AppsHttpRequestBaseInfo.getSign(MmfCommonSetting.CLIENT_APP_SECRET, "", ts);
//        appsHttpRequestBaseInfo.addHeader(MmfCommonSetting.Host_Head_Sign, sign + "," + ts);
//        appsHttpRequestBaseInfo.addQueryStringParameter(MmfCommonSetting.Http_Request_Key_app_package,
//                PackageInfoUtils.GetPackageInfo(activity).packageName);
//        JZHttpUtils.getInstance().HttpGetRequest(MmfCommonSetting.Host_Check_Version, appsHttpRequestBaseInfo,
//                new HttpRequestCallback<String>(new HttpRequestCallbackHandler() {
//                    @Override
//                    public void onSuccess(String content) {
//                        super.onSuccess(content);
//                        parseCheckUpdate(content);
//                    }
//
//                    @Override
//                    public void onFailure(String content) {
//                        super.onFailure(content);
//                        ShowNoUpdateInfo();
//                        if (null != checkListener) {
//                            checkListener.onResponse(false, content);
//                        }
//                    }
//                }));
//    }

    public void checkUpdate(String name) {
        if (activity == null) {
            return;
        }
        HttpRequestBaseInfo appsHttpRequestBaseInfo = new HttpRequestBaseInfo();
        if (!TextUtils.isEmpty(name)) {
            appsHttpRequestBaseInfo.addQueryStringParameter(MmfCommonSetting.Http_Request_Key_Update_name, name);
        }

        JZHttpUtils.getInstance().HttpGetRequest(MmfCommonSetting.Host_Common_Check_Update, appsHttpRequestBaseInfo,
                new HttpRequestCallback<String>(new HttpRequestCallbackHandler() {
                    @Override
                    public void onSuccess(String content) {
                        super.onSuccess(content);
                        try {
                            CommonJsonBean commonJsonBean = CommonJsonUtils.parseCommonJson(content);
                            if (null != commonJsonBean && commonJsonBean.isCodeValueOne()) {
                                String response = commonJsonBean.getResponse();
                                parseCheckUpdate(response);
                            } else {
                                if (null != checkListener) {
                                    checkListener.onResponse(false, content);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (null != checkListener) {
                                checkListener.onResponse(false, content);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String content) {
                        super.onFailure(content);
                        ShowNoUpdateInfo();
                        if (null != checkListener) {
                            checkListener.onResponse(false, content);
                        }
                    }
                }));
    }

    //    private void parseCheckUpdate(String content) {
//        CommonJsonBean commonJsonBean = CommonJsonUtils.parseCommonJson(content);
//        String message = commonJsonBean.getMsg();
//        if (commonJsonBean.isCodeValueOne()) {
//            String response = commonJsonBean.getResponse();
//            AppUpdateBean appUpdateBean = new Gson().fromJson(response, AppUpdateBean.class);
//            if (null == appUpdateBean) {
//                if (null != checkListener) {
//                    checkListener.onResponse(false, message);
//                }
//                ShowNoUpdateInfo();
//                return;
//            }
//            int lastVersionCode = appUpdateBean.getVersionCode();
//            int appVersionCode = PackageInfoUtils.GetVersionCode(activity);
//            if (lastVersionCode > appVersionCode) {
//
//                if (null != checkListener) {
//                    checkListener.onResponse(true, message);
//                }
//                showLogoutDialog(appUpdateBean.getContent(), appUpdateBean.getVersionName(), appUpdateBean.getDownloadUrl());
//
//            } else {
//                if (null != checkListener) {
//                    checkListener.onResponse(false, message);
//                }
//                ShowNoUpdateInfo();
//            }
//        }
//
//    }

    public static final String IS_NEED_UPDATE = "IS_NEED_UPDATE";

    private void parseCheckUpdate(String content) {
        UpdateInfoBean appUpdateBean = new Gson().fromJson(content, UpdateInfoBean.class);
        if (null == appUpdateBean) {
            if (null != checkListener) {
                checkListener.onResponse(false, MmfCommonAppBaseApplication.appContext.getString(R.string.error_network));
            }
            ShowNoUpdateInfo();
            return;
        }
        int lastVersionCode = appUpdateBean.versionCode;
        int appVersionCode = PackageInfoUtils.GetVersionCode(activity);
        if (lastVersionCode > appVersionCode) {
            //如果服务器版本号大于当前版本则点击按钮才回调结果
            boolean needUpdate = appUpdateBean.needUpdate == 1 ? true : false;
            showLogoutDialog(appUpdateBean.versionSummary, appUpdateBean.versionName, appUpdateBean.fileUrl, needUpdate);

        } else {
            if (null != checkListener) {
                checkListener.onResponse(false, MmfCommonAppBaseApplication.appContext.getString(R.string.error_network));
            }
            ShowNoUpdateInfo();
        }

    }

    void ShowNoUpdateInfo() {
        if (isShowNoUpdateInfo)
            DialogAndToast.showLongToast(activity, activity.getString(R.string.no_new_app_version));
    }

    private void showLogoutDialog(String content, String versionName, final String url, final boolean needUpdate) {
        final Dialog dialog = new Dialog(activity, R.style.ChangeImage);
        dialog.setContentView(R.layout.dialog_ios_style);
        int width = DisplayUtils.getWidthInPx(activity);
        if (width > 0) {
            width = (int) (0.8 * width);
            dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        tvTitle.setText(activity.getString(R.string.find_new_app_version, versionName));
        TextView tvMessage = (TextView) dialog.findViewById(R.id.tv_dialog_message);
        tvMessage.setGravity(Gravity.CENTER | Gravity.LEFT);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_dialog_ok);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.tv_dialog_cancel);
        tvMessage.setText(content);
        if (needUpdate) {
            tvCancel.setVisibility(View.GONE);
            dialog.setCancelable(false);
        }
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkListener.onResponse(true, null);
                dialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOutputUtils.d("checkupdate","ok click url ->"+url);
                downloadAppByUrl(url);
                if (null != checkListener) {
                    if (needUpdate) {
                        checkListener.onResponse(true, IS_NEED_UPDATE);
                    } else {
                        dialog.dismiss();
                        checkListener.onResponse(true, null);
                    }
                }
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (null != checkListener) {
                    checkListener.onResponse(true, null);
                }
            }
        });
        dialog.show();
    }

    private void downloadAppByUrl(String url) {
//        MmfCommonAppBaseApplication.isDownLoadAppCompleted = false;
        Intent intent = new Intent(activity, UpdateService.class);
        intent.putExtra(CommonSetting.UPDATE_APK_URL, url);
        activity.startService(intent);
    }
}
