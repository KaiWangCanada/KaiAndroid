package com.wq.photo;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by wangqiong on 15/3/27.
 */
public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  //public static final int CHOSE_MODE_SINGLE = 0;
  public static final int CHOSE_MODE_MULTIPLE = 1;

  public int max_chose_count = 9;

  LayoutInflater inflater;
  List<String> images;
  Context context;
  public static RecyclerView.LayoutParams params;
  LinkedHashMap<String, String> hashMap;
  public int currentChoseMode;
  int sWidthPix;

  public PhotoAdapter(Context context, List<String> images, int choseMode) {
    inflater = LayoutInflater.from(context);
    this.context = context;
    this.images = images;
    sWidthPix = context.getResources().getDisplayMetrics().widthPixels;
    params = new RecyclerView.LayoutParams(sWidthPix / 3, sWidthPix / 3);
    int dp3 = dip2px(context, 1);
    params.setMargins(dp3, dp3, dp3, dp3);
    currentChoseMode = choseMode;
    hashMap = ((MediaChoseActivity) context).getImageChoseMap();
  }

  public LinkedHashMap getCHoseImages() {
    return hashMap;
  }

  public void setMaxChoseCount(int max_chose_count) {
    this.max_chose_count = max_chose_count;
  }

  String imgDir;

  public void setDir(String dir) {
    imgDir = dir;
  }

  public String getDir() {
    if (imgDir.equals("")) {
      return "";
    } else {
      return imgDir + "/";
    }
  }

  boolean isNeedCamera = true;

  /**
   * 是否在第一个item现实相机
   */
  public void setNeedCamera(boolean isNeedCamera) {
    this.isNeedCamera = isNeedCamera;
  }

  /**
   * DIP转换成PX
   */
  public static int dip2px(Context context, float dipValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dipValue * scale + 0.5f);
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    RecyclerView.ViewHolder holder;
    if (viewType == TYPE_CAMERA) {
      holder =
          new CameraViewHolder(inflater.inflate(R.layout.item_photo_camera_layout, parent, false));
    } else {
      holder =
          new ImageViewHolder(inflater.inflate(R.layout.item_photo_image_layout, parent, false));
    }
    return holder;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
    if (getItemViewType(position) == TYPE_IMAGE) {
      final ImageViewHolder ivholder = (ImageViewHolder) holder;
      final String imagePath = getDir() + this.images.get(position);
      displayImage(imagePath, ivholder.iv_image);
      if (currentChoseMode == CHOSE_MODE_MULTIPLE) {
        ivholder.checkBox.setVisibility(View.VISIBLE);
        ivholder.checkBox.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            if (hashMap.containsKey(imagePath)) {
              hashMap.remove(imagePath);
              ivholder.alpha_view.setVisibility(View.GONE);
              ivholder.checkBox.setSelected(false);
            } else {
              if (hashMap.size() >= max_chose_count) {
                //Toast.makeText(context, "你最多只能选择" + max_chose_count + "张照片", Toast.LENGTH_SHORT)
                //    .show();
                return;
              }
              hashMap.put(imagePath, imagePath);
              ivholder.alpha_view.setVisibility(View.VISIBLE);
              ivholder.checkBox.setSelected(true);
            }
            ((Activity) context).invalidateOptionsMenu();
          }
        });
        if (hashMap.containsKey(imagePath)) {
          ivholder.alpha_view.setVisibility(View.VISIBLE);
          ivholder.checkBox.setSelected(true);
        } else {
          ivholder.alpha_view.setVisibility(View.GONE);
          ivholder.checkBox.setSelected(false);
        }
        ivholder.alpha_view.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            ((MediaChoseActivity) context).startPreview(getCHoseImages(), imagePath);
          }
        });
      } else {
        ivholder.checkBox.setVisibility(View.GONE);
        ivholder.alpha_view.setVisibility(View.GONE);
        ivholder.iv_image.setClickable(true);
        ivholder.iv_image.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            getCHoseImages().clear();
            getCHoseImages().put(imagePath, imagePath);
            ((MediaChoseActivity) context).startPreview(getCHoseImages(), imagePath);
          }
        });
      }
    } else {
      CameraViewHolder holder1 = (CameraViewHolder) holder;
      holder1.camera_lin.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (currentChoseMode == CHOSE_MODE_MULTIPLE) {
            if (getCHoseImages().size() < max_chose_count) {
              ((MediaChoseActivity) context).sendStarCamera();
            } else {
              //Toast.makeText(context, "你最多只能选择" + max_chose_count + "张照片", Toast.LENGTH_SHORT)
              //    .show();
            }
          } else {
            if (getCHoseImages().size() > 0) {
              getCHoseImages().clear();
            }
            ((MediaChoseActivity) context).sendStarCamera();
          }
        }
      });
    }
  }

  /**
   * imageScaleType:
   * EXACTLY :图像将完全按比例缩小的目标大小
   * EXACTLY_STRETCHED:图片会缩放到目标大小完全
   * IN_SAMPLE_INT:图像将被二次采样的整数倍
   * IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
   * NONE:图片不会调整
   */
  public void displayImage(String url, ImageView view) {
    // 显示图片
    Picasso.with(context).load("file://" + url).resize(sWidthPix / 3, sWidthPix / 3).placeholder(
        R.drawable.loadfaild)
        //.error(R.drawable.default_error)
        .centerCrop().into(view);
  }

  public static int TYPE_IMAGE = 10;
  public static int TYPE_CAMERA = 11;

  @Override public int getItemViewType(int position) {
    if (position == 0 && isNeedCamera) {
      return TYPE_CAMERA;
    }
    return TYPE_IMAGE;
  }

  @Override public int getItemCount() {
    if (images.size() == 0) {
      return 1;
    }
    return images.size();
  }

  public static class ImageViewHolder extends RecyclerView.ViewHolder {
    ImageView iv_image;
    ImageButton checkBox;
    View alpha_view;

    public ImageViewHolder(View itemView) {
      super(itemView);
      itemView.setLayoutParams(params);
      iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
      alpha_view = itemView.findViewById(R.id.alpha_view);
      checkBox = (ImageButton) itemView.findViewById(R.id.checkimages);
    }
  }

  public static class CameraViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout camera_lin;

    public CameraViewHolder(View itemView) {
      super(itemView);
      itemView.setLayoutParams(params);
      camera_lin = (LinearLayout) itemView.findViewById(R.id.camera_lin);
    }
  }
}
