package com.unit.common.ui;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.unit.common.R;
import com.unit.common.resource.MResource;
import com.unit.common.utils.DisplayUtils;

import org.w3c.dom.Text;

public class DialogAndToast {

    public static String POSITIVE = "确定";
    public static String NEGATIVE = "取消";
    public static String PROGRESS_DIALOG_MSG = "正在加载,请稍后...";

    /**
     * 作用:选择对话框
     */
    public static void gotoDialog(final Context context, String title, String msg, String positiveString,
                                  String negativeString, DialogInterface.OnClickListener positiveOnClickListener,
                                  OnClickListener negativeClickListener) {

        AlertDialog.Builder builder = new Builder(context);

        builder.setMessage(msg);

        builder.setTitle(title);

        builder.setPositiveButton(positiveString, positiveOnClickListener);
        builder.setNegativeButton(negativeString, negativeClickListener);

        builder.show();
    }

    public static void showCustomDialog(Context context, int theme, String message, final View.OnClickListener onClickListener) {
        if (theme == -1) {
            theme = R.style.ChangeImage;
        }
        if(TextUtils.isEmpty(message)){
            message=context.getResources().getString(R.string.no_msg_toast);
        }
        final Dialog dialog = new Dialog(context, theme);
        dialog.setContentView(R.layout.dialog_ios_style);
        int width = DisplayUtils.getWidthInPx(context);
        if (width > 0) {
            width = (int) (0.8 * width);
            dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
//        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.tv_dialog_message);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_dialog_ok);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.tv_dialog_cancel);
        tvMessage.setText(message);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 作用:progress对话框
     */
    public static ProgressDialog getProgressDialog(Context context, String msg) {
        if (TextUtils.isEmpty(msg)) {
            msg = context.getString(R.string.ruis_common_idLoading);
        }
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context, R.style.Theme_AppCompat_Dialog);
        progressDialog.setTitle(null);
        progressDialog.setIcon(null);
        progressDialog.setMessage(msg);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return progressDialog;
    }

    public static void showShortToast(Context context, String message) {
        if(TextUtils.isEmpty(message)){
            message=context.getResources().getString(R.string.no_msg_toast);
        }
        showToast(context, message, Toast.LENGTH_SHORT);
    }

    public static void showShortToast(Context context, int msgRes) {
        if(msgRes==0)
            msgRes=R.string.no_msg_toast;
        showToast(context, msgRes, Toast.LENGTH_SHORT);
    }

    private static void showToast(Context context, String message, int time) {
        if(TextUtils.isEmpty(message)){
            message=context.getResources().getString(R.string.no_msg_toast);
        }
        Toast toast = Toast.makeText(context, message, time);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private static void showToast(Context context, int msgRes, int time) {
        if(msgRes==0)
            msgRes=R.string.no_msg_toast;
        Toast toast = Toast.makeText(context, msgRes, time);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showLongToast(Context context, String message) {
        if(TextUtils.isEmpty(message)){
            message=context.getResources().getString(R.string.no_msg_toast);
        }
        showToast(context, message, Toast.LENGTH_LONG);
    }

    public static void showLongToast(Context context, int msgRes) {
        if(msgRes==0)
            msgRes=R.string.no_msg_toast;
        showToast(context, msgRes, Toast.LENGTH_LONG);
    }

    /**
     * 提示网络异常
     *
     * @param context
     */
    public static void showNetworkException(Context context) {
        showToast(context, context.getResources().getString(MResource.getIdByName(context, "string", "ruis_common_NetworkError")), Toast.LENGTH_SHORT);
    }

    /**
     * 提示数据为空(读取数据失败)
     *
     * @param context
     */
    public static void getDataFailure(Context context) {
        showToast(context, context.getResources().getString(MResource.getIdByName(context, "string", "ruis_common_DataError")), Toast.LENGTH_SHORT);
    }

    /**
     * 解析数据异常(数据错误)
     *
     * @param context
     */
    public static void showJsonException(Context context) {
        showToast(context, context.getResources().getString(MResource.getIdByName(context, "string", "ruis_common_DataError")), Toast.LENGTH_SHORT);
    }

    /**
     * 没有更多数据(数据错误)
     *
     * @param context
     */
    public static void showNoMoreDataException(Context context) {
        showToast(context, context.getResources().getString(MResource.getIdByName(context, "string", "ruis_common_no_more")), Toast.LENGTH_SHORT);
    }

}
