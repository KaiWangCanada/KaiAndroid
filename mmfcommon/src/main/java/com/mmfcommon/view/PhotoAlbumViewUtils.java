package com.mmfcommon.view;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mmfcommon.R;


/**
 * Created by longbin on 15/3/12.
 */
public class PhotoAlbumViewUtils {

    private Activity activity;
    private Dialog photoAlbumDialog;

    private View.OnClickListener albumListener;
    private View.OnClickListener photoListener;

    public PhotoAlbumViewUtils(Activity activity) {
        this.activity = activity;
    }

    public void setAlbumListener(View.OnClickListener albumListener) {
        this.albumListener = albumListener;
    }

    public void setPhotoListener(View.OnClickListener photoListener) {
        this.photoListener = photoListener;
    }

    public void showUpdateImageDialog() {
//        if (null == updateAvatarUtils) {
//            updateAvatarUtils = new UpdateAvatarUtils(this);
//        }
        photoAlbumDialog = new Dialog(activity, R.style.ChangeImage);
        photoAlbumDialog.setContentView(R.layout.activity_dialog_avatar_change);
        photoAlbumDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        photoAlbumDialog.getWindow().setGravity(Gravity.BOTTOM);
        photoAlbumDialog.getWindow().setWindowAnimations(android.R.style.Animation_InputMethod);
        TextView tvCancel = (TextView) photoAlbumDialog.findViewById(R.id.tv_photo_cancel);
        TextView tvPhotoAlbum = (TextView) photoAlbumDialog.findViewById(R.id.tv_photo_album);
        TextView tvPhotoGraph = (TextView) photoAlbumDialog.findViewById(R.id.tv_photo_graph);
        // 取消
        tvCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != photoAlbumDialog && photoAlbumDialog.isShowing()) {
                    photoAlbumDialog.dismiss();
                }
            }
        });
        // 相册
        tvPhotoAlbum.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                updateAvatarUtils.getBitmapByLocal();
                if (null != albumListener) {
                    albumListener.onClick(v);
                }
                if (null != photoAlbumDialog && photoAlbumDialog.isShowing()) {
                    photoAlbumDialog.dismiss();
                }
            }
        });
        // 拍照
        tvPhotoGraph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                updateAvatarUtils.getBitmapByCamera();
                if (null != photoListener) {
                    photoListener.onClick(v);
                }
                if (null != photoAlbumDialog && photoAlbumDialog.isShowing()) {
                    photoAlbumDialog.dismiss();
                }
            }
        });
        photoAlbumDialog.show();
    }
}
