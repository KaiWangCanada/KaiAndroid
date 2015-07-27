package com.mmfcommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mmfcommon.R;
import com.mmfcommon.bean.SidebarItemBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 黄东鲁 on 15/3/30.
 */
public class SidebarAdapter extends BaseAdapter {

    protected List<SidebarItemBean> sidebarItems = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    //    private Context context;
    private int selectedId = -1;

    public SidebarAdapter(Context context, List<SidebarItemBean> sidebarItems) {
        this.sidebarItems = sidebarItems;

        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return sidebarItems.size();
    }

    @Override
    public SidebarItemBean getItem(int position) {
        return sidebarItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            Holder holder;
            SidebarItemBean sidebarItem = sidebarItems.get(position);

            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.item_sidebar, parent, false);
                holder = new Holder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            if (holder != null && holder.tvText != null) {
                holder.tvText.setText(sidebarItem.text);
                if (sidebarItem.drawableLeft != null)
                    holder.tvText.setCompoundDrawablesWithIntrinsicBounds(sidebarItem.drawableLeft, null, null, null);
                convertView.setId(sidebarItem.id);
                if (sidebarItem.id == getSelectedId()) {
                    holder.ivSelected.setVisibility(View.VISIBLE);
                } else {
                    holder.ivSelected.setVisibility(View.INVISIBLE);
                }

                if (sidebarItem.showRedPoint) {
                    holder.ivRedPoint.setVisibility(View.VISIBLE);
                } else {
                    holder.ivRedPoint.setVisibility(View.GONE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return convertView;
    }

    private static class Holder {
        TextView tvText;
        ImageView ivSelected;
        ImageView ivRedPoint;

        public Holder(View itemView) {
            tvText = (TextView) itemView.findViewById(R.id.tv_item_sidebar);
            ivSelected = (ImageView) itemView.findViewById(R.id.iv_selected);
            ivRedPoint = (ImageView) itemView.findViewById(R.id.iv_red_point);
        }
    }

    public int getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(int selectedId) {
        this.selectedId = selectedId;
    }

    public void setIsRedPoint(int id, boolean isShow) {
        sidebarItems.get(sidebarItems.indexOf(new SidebarItemBean(id))).showRedPoint = isShow;
    }
}
