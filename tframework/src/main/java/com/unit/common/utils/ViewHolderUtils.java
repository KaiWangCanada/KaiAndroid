package com.unit.common.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by kingkong on 15/1/15.
 */
public class ViewHolderUtils {

    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int viewId) {
        View childView = null;
        try {
            SparseArray<View> viewHolder = (SparseArray) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                view.setTag(viewHolder);
            }
            childView = viewHolder.get(viewId);
            if (childView == null) {
                childView = view.findViewById(viewId);
                viewHolder.put(viewId, childView);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (T) childView;
    }
}
