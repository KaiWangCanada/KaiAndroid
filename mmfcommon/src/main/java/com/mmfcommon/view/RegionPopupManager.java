package com.mmfcommon.view;

import android.content.Context;
import com.mmfcommon.R;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mmfcommon.bean.DistrictBean;
import com.unit.common.utils.LogOutputUtils;

import java.util.ArrayList;
import java.util.List;

import static android.widget.PopupWindow.OnDismissListener;

/**
 *
 * Created by 黄东鲁 on 15/1/28.
 */
public class RegionPopupManager {

    private PopupWindow mPopupWindow;

    private List<String> list = new ArrayList<>();

    private  GridView mGridView;
    private  ArrayAdapter<String> mArrayAdapter;

    private OnDismissListener onDismissListener;
    private AdapterView.OnItemClickListener onItemClickListener;

    private Context context;

    private View.OnClickListener listener;

    private TextView tvCurrentCity;

    public RegionPopupManager(Context mContext) {
        this.context = mContext;
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        View contentView = mLayoutInflater.inflate(R.layout.popup_region, null);

        mGridView = (GridView) contentView.findViewById(R.id.grid_region);
        tvCurrentCity = (TextView) contentView.findViewById(R.id.tv_current_city);
        mArrayAdapter = new ArrayAdapter<String>(mContext, R.layout.item_text_region, list);
        mGridView.setAdapter(mArrayAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogOutputUtils.d(">>>>", "..... onItemClick ....");
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(parent, view, position, id);
                }
            }
        });
        mPopupWindow = new PopupWindow(contentView, -1, -1, true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setAnimationStyle(R.style.popup_view_anim);
        contentView.findViewById(R.id.popup_shadow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mPopupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if (onDismissListener != null) {
                    onDismissListener.onDismiss();
                }
            }
        });

        mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                    return true;
                } else if (event.getAction() == MotionEvent.BUTTON_BACK) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        contentView.findViewById(R.id.region_change_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });
    }

    public void show(View anchor, List<DistrictBean> districtBeans) {
        if (districtBeans != null) {
            list.clear();
            for (DistrictBean district : districtBeans) {
                list.add(district.getFullName());
            }
        }
        mArrayAdapter.notifyDataSetChanged();

        mPopupWindow.showAsDropDown(anchor);
    }

    public void dismiss() {
        mPopupWindow.dismiss();
    }

//    public void setCheckedItem(int checkedItem) {
//        mGridView.setItemChecked(checkedItem, true);
//    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnCityClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setCurrentCity(CharSequence text) {
        tvCurrentCity.setText(text);
    }

    public void notifyDataSetChanged() {
        mArrayAdapter.notifyDataSetChanged();
    }

}
