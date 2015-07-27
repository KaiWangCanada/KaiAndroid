package com.mmfcommon.adapter;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * the base choose list adapter.
 * Created by longbin on 15/4/17.
 */
public abstract class ChooseBaseAdapter<T> extends BaseAdapter {

    public List<T> itemDatas;
    public ArrayList<T> selectedItem = new ArrayList<>();

    public ChooseBaseAdapter(List<T> itemDatas) {
        this.itemDatas = itemDatas;
    }

    public ArrayList<T> getSelectedItem() {
        return selectedItem;
    }

    @Override
    public int getCount() {
        return null == itemDatas ? 0 : itemDatas.size();
    }

    @Override
    public T getItem(int position) {
        return itemDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public abstract boolean contains(List<T> datas, T itemData);

    public class ViewHolder {
        public TextView textView;
        public ImageView imageView;
    }
}
