package com.liucanwen.citylist.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.liucanwen.citylist.R;
import com.liucanwen.citylist.model.IndexListItem;
import com.liucanwen.citylist.widget.ContactListAdapter;

import java.util.List;

public class CityAdapter extends ContactListAdapter {

    public CityAdapter(Context _context, int _resource,
                       List<IndexListItem> _items) {
        super(_context, _resource, _items);
    }

    public void populateDataForRow(View parentView, IndexListItem item,
                                   int position) {
        View infoView = parentView.findViewById(R.id.infoRowContainer);
        TextView nicknameView = (TextView) infoView
                .findViewById(R.id.cityName);

        nicknameView.setText(item.getDisplayInfo());
    }

}
