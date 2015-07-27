package com.mmfcommon.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mmfcommon.R;
import com.mmfcommon.bean.HairStylistBean;
import com.mmfcommon.bean.ServiceBean;
import com.mmfcommon.utils.CircleIconBitmapLoadCallBack;
import com.mmfcommon.utils.TextGenderViewUtils;
import com.unit.common.utils.FrameBitmapUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * 发型师列表适配器
 * <p/>
 * Created by 黄东鲁 on 15/1/10.
 */
public class HairStylistListAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private List<HairStylistBean> hairStylists = new ArrayList<>();
    private FrameBitmapUtils mBitmapUtils;

    private Context context;
    private boolean hideAge = false;
    public static int serviceCount = 2;

    public HairStylistListAdapter(Context context, List<HairStylistBean> hairStylists) {
        mLayoutInflater = LayoutInflater.from(context);
        this.hairStylists = hairStylists;
        mBitmapUtils = FrameBitmapUtils.getInstance(context);
        this.context = context;

    }

    public View setConvertView(View convertView) {
        StylistViewHolder viewHolder = new StylistViewHolder(convertView);
        convertView.setTag(viewHolder);
        return convertView;
    }

    @Override
    public int getCount() {
        return hairStylists.size();
    }

    @Override
    public HairStylistBean getItem(int position) {
        return hairStylists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StylistViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_stylist, parent, false);
            viewHolder = new StylistViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (StylistViewHolder) convertView.getTag();
        }
        HairStylistBean stylist = hairStylists.get(position);
        viewHolder.tvStylistName.setText(stylist.getStylistName());
        viewHolder.tvRank.setText(stylist.getRank());


        if (TextUtils.isEmpty(stylist.getStars())) {
            //viewHolder.tvRatingNoStar.setVisibility(VISIBLE);
            viewHolder.rbStars.setVisibility(GONE);
        } else {
            float rating = Float.valueOf(stylist.getStars());
            if (Float.compare(rating, 0f) == 0) {
                //viewHolder.tvRatingNoStar.setVisibility(VISIBLE);
                viewHolder.rbStars.setVisibility(GONE);
            } else {
                viewHolder.rbStars.setRating(rating);
                viewHolder.rbStars.setVisibility(VISIBLE);
                //viewHolder.tvRatingNoStar.setVisibility(GONE);
            }
        }

        viewHolder.tvAge.setText(stylist.getAge());


        if (stylist.getGender() != null) {
            if (stylist.getGender().trim().equals(context.getString(R.string.male))) {
                TextGenderViewUtils.setBoy(viewHolder.tvAge);
            } else if (stylist.getGender().trim().equals(context.getString(R.string.female))) {
                TextGenderViewUtils.setGirl(viewHolder.tvAge);
            }
        }

        if (hideAge) {
            viewHolder.tvAge.setVisibility(View.INVISIBLE);
            viewHolder.tvAge.setText("");
            viewHolder.tvAge.setCompoundDrawables(null, null, null, null);
        }

        if (!TextUtils.isEmpty(stylist.getKiloMeters())) {
            viewHolder.tvKm.setText(stylist.getKiloMeters() + "km");
            viewHolder.tvKm.setVisibility(VISIBLE);
        }

        List<ServiceBean> services = stylist.getServices();
        if (services != null) {
            for (int i = 0; i < services.size() && i < serviceCount; i++) {
                viewHolder.tvServices[i].setText(services.get(i).getServiceName());
                viewHolder.tvServices[i].setVisibility(View.VISIBLE);
            }
        }
        mBitmapUtils.display(viewHolder.ivStylist, stylist.getAvatar(), new CircleIconBitmapLoadCallBack());
        return convertView;
    }

    public static class StylistViewHolder extends RecyclerView.ViewHolder {

        public TextView tvStylistName;
        public ImageView ivStylist;
        public TextView[] tvServices = new TextView[3];
        public TextView tvAge;
        public TextView tvRank;
        public RatingBar rbStars;
        //public TextView   tvRatingNoStar;
        public TextView tvKm;

        public StylistViewHolder(View itemView) {
            super(itemView);
            tvStylistName = (TextView) itemView.findViewById(R.id.tv_stylist_name);
            ivStylist = (ImageView) itemView.findViewById(R.id.iv_stylist_avatar);
            tvAge = (TextView) itemView.findViewById(R.id.tv_stylist_age);
            tvServices[0] = (TextView) itemView.findViewById(R.id.tv_stylist_service01);
            tvServices[1] = (TextView) itemView.findViewById(R.id.tv_stylist_service02);
            tvServices[2] = (TextView) itemView.findViewById(R.id.tv_stylist_service03);
            tvRank = (TextView) itemView.findViewById(R.id.tv_stylist_rank);
            rbStars = (RatingBar) itemView.findViewById(R.id.rb_stylist_rating);
            //tvRatingNoStar = (TextView)  itemView.findViewById(R.id.tv_stylist_rating_no_star);
            tvKm = (TextView) itemView.findViewById(R.id.tv_km);

        }
    }

    public void hideAge() {
        this.hideAge = true;
    }

}
