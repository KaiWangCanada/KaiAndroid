package com.mmfcommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.mmfcommon.R;

import com.lidroid.xutils.BitmapUtils;
import com.mmfcommon.bean.ServiceBean;
import com.mmfcommon.utils.CircleIconBitmapLoadCallBack;
import com.mmfcommon.utils.ViewUtils;
import com.unit.common.utils.FrameBitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务项目
 * <p/>
 * Created by 黄东鲁 on 15/2/12.
 */
public final class ServiceAdapter extends BaseAdapter {

    private List<ServiceBean> services = new ArrayList<>();
    private LayoutInflater mLayoutInflater = null;

    BitmapUtils mBitmapUtils = null;
    Context context = null;
    public ServiceAdapter(Context context, List<ServiceBean> services) {
        this.services = services;
        mLayoutInflater = LayoutInflater.from(context);
        mBitmapUtils = FrameBitmapUtils.getInstance(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public ServiceBean getItem(int position) {
        return services.get(position);
    }

    @Override
    public long getItemId(int position) {
        return services.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_service, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        ServiceBean service = services.get(position);
        mBitmapUtils.display(holder.ivIcon, service.getAvatarPath(), new CircleIconBitmapLoadCallBack());

        holder.tvPrice           .setText(context.getString(R.string.price_with_¥, service.getMin_price()));
        holder.tvOriginalPrice   .setText(context.getString(R.string.price_with_¥, service.getMaxStorePrice()));

        if (service.getMin_price().equals(service.getMaxStorePrice())) {
            holder.tvOriginalPrice.setVisibility(View.GONE);
        } else {
            holder.tvOriginalPrice.setVisibility(View.VISIBLE);
        }
        holder.tvSalonServiceName.setText(service.getSalonServiceName());
        holder.tvServiceName     .setText(service.getServiceName());

        return convertView;
    }

    class Holder {
        ImageView ivIcon;
        TextView tvServiceName;
        TextView tvSalonServiceName;
        TextView tvPrice;
        TextView tvOriginalPrice;

        public Holder(View convertView) {

            ivIcon = findView(convertView, R.id.iv_icon);
            tvServiceName = findView(convertView, R.id.tv_service_name);
            tvSalonServiceName = findView(convertView, R.id.tv_salon_service_name);
            tvPrice = findView(convertView, R.id.tv_price);
            tvOriginalPrice = findView(convertView, R.id.tv_original_price);

            ViewUtils.addDelLine(tvOriginalPrice);
        }
    }

    private <T extends View> T findView(View v, int resId) {
        return (T) v.findViewById(resId);
    }
}
