package com.mmfcommon.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.TextView;
import com.mmfcommon.R;
import com.mmfcommon.bean.HairTypeBigBean;
import com.mmfcommon.bean.StyleBean;
import com.unit.common.config.CommonSetting;
import com.unit.common.utils.DisplayUtils;
import com.unit.common.utils.ViewHolderUtils;
import java.util.List;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * 多选发型的扩展列表适配器
 */
public class HairProductionMulSelectAdapter extends BaseAdapter
    implements StickyListHeadersAdapter {

  private Activity mContext;

  private LayoutInflater mInflater;
  List<HairTypeBigBean> bigBeanList;  //发型大类别
  MulSelectSmallListener listener;//点击发型小类别监听器
  int layoutPadding = 0, itemPaddingLeft = 0;

  public HairProductionMulSelectAdapter(Activity context, List<HairTypeBigBean> bigBeanList,
      MulSelectSmallListener listener) {
    mContext = context;
    this.bigBeanList = bigBeanList;
    mInflater = LayoutInflater.from(context);
    this.listener = listener;
    layoutPadding = DisplayUtils.convertDIP2PX(context, 10);
  }

  @Override public int getCount() {
    return bigBeanList.size();
  }

  @Override public Object getItem(int position) {

    return null;
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {

    GridLayoutViewHolder gridLayoutViewHolder;
    if (convertView == null) {
      convertView =
          mInflater.inflate(R.layout.hair_production_type_samll_child_gridlayout, parent, false);
      gridLayoutViewHolder = new GridLayoutViewHolder(convertView);
      convertView.setTag(gridLayoutViewHolder);
    } else {
      gridLayoutViewHolder = (GridLayoutViewHolder) convertView.getTag();
    }

    gridLayoutViewHolder.gridLayout.removeAllViews();
    //        if (gridView.getChildCount() <= 0) {

    //遍历生成发型子类
    List<StyleBean> smallBeanLists = bigBeanList.get(position).getSmallBeanList();
    for (int i = 0; i < smallBeanLists.size(); i++) {
      final StyleBean styleBean = smallBeanLists.get(i);

      View childView = mContext.getLayoutInflater()
          .inflate(R.layout.hair_production_type_samll_child_itemlayout, null);

      if (i % 3 == 1 || i % 3 == 2) {
        if (itemPaddingLeft == 0) {
          itemPaddingLeft = (CommonSetting.screenWidth
              - layoutPadding * 2
              - DisplayUtils.convertDIP2PX(mContext, 90) * 3) / 2;
        }
        childView.setPadding(itemPaddingLeft, layoutPadding, 0, 0);
      } else {
        childView.setPadding(layoutPadding, layoutPadding, 0, layoutPadding);
      }
      TextView textView =
          (TextView) childView.findViewById(R.id.hair_productiont_detail_small_child_item_text);
      textView.setText(styleBean.getStyleName());
      if (styleBean.isSelect()) {
        textView.setBackgroundResource(R.color.text_selected);
      } else {
        textView.setBackgroundColor(Color.WHITE);
      }
      textView.setOnClickListener(new OnClickListener() {
        @Override public void onClick(View view) {
          // 点击了samllItem

          listener.itemClick(view, styleBean);
        }
      });

      gridLayoutViewHolder.gridLayout.setOnClickListener(new OnClickListener() {
        @Override public void onClick(View view) {

        }
      });
      gridLayoutViewHolder.gridLayout.addView(childView);
    }
    //        }

    return convertView;
  }

  @Override public View getHeaderView(int position, View convertView, ViewGroup parent) {

    if (convertView == null) convertView = mInflater.inflate(R.layout.header, parent, false);

    // set header text as first char in name
    CharSequence headerChar = bigBeanList.get(position).getBigTypeName();
    TextView textView = (TextView) ViewHolderUtils.get(convertView, R.id.head);
    textView.setTextColor(mContext.getResources().getColor(R.color.text_80));
    textView.setText(headerChar);

    return convertView;
  }

  /**
   * Remember that these have to be static, postion=1 should always return
   * the same Id that is.
   */
  @Override public long getHeaderId(int position) {
    // return the first character of the country as ID because this is what
    // headers are based upon
    return HeadIndex++;
  }

  long HeadIndex = 0;

  public void clear() {
    bigBeanList.clear();
    notifyDataSetChanged();
  }

  private static class GridLayoutViewHolder {
    public GridLayout gridLayout;

    public GridLayoutViewHolder(View view) {
      gridLayout =
          (GridLayout) ViewHolderUtils.get(view, R.id.hair_productiont_detail_small_gridlayout);
    }
  }

  public interface MulSelectSmallListener {
    /**
     * 判断选中了哪几个item
     *
     * @param clickView 点击的textview
     * @param smallBean 点击view的bean
     */
    public void itemClick(View clickView, StyleBean smallBean);
  }
}
