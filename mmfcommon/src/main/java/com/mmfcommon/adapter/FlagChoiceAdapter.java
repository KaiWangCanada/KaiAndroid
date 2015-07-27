package com.mmfcommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mmfcommon.R;
import com.mmfcommon.bean.AreaCodeBean;
import com.mmfcommon.adapter.ChooseBaseAdapter;

import java.util.List;

/**
 * Created by longbin on 15/4/30.
 */
public class FlagChoiceAdapter extends ChooseBaseAdapter<AreaCodeBean.AreaCode> {
    private Context context;

    public FlagChoiceAdapter(List<AreaCodeBean.AreaCode> itemDatas) {
        super(itemDatas);
    }

    @Override
    public boolean contains(List<AreaCodeBean.AreaCode> datas, AreaCodeBean.AreaCode itemData) {
        if (null == itemData || null == itemData.id) {
            return false;
        }
        for (int i = 0; i < datas.size(); i++) {
            if (null == datas.get(i)) {
                continue;
            }
            if (itemData.id.equals(datas.get(i).id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            context = parent.getContext();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_choose, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_choose_checked);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_choose);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AreaCodeBean.AreaCode areaCode = itemDatas.get(position);
        if (null == areaCode) {
            return convertView;
        }
        String text = areaCode.areaName;
        if (null != text) {
            holder.textView.setText(text);
        }
        if (contains(selectedItem, areaCode)) {
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }
        return convertView;
    }
}
