package com.unit.common.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import com.lidroid.xutils.ViewUtils;
import com.liucanwen.citylist.adapter.CityAdapter;
import com.liucanwen.citylist.model.IndexListItem;
import com.liucanwen.citylist.widget.ContactListViewImpl;
import com.unit.common.R;
import com.unit.common.activity.FrameworkBaseFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IndexListViewWithPinYinFragment extends FrameworkBaseFragment implements TextWatcher {

    private ContactListViewImpl listview;

    private EditText searchBox;
    private String searchString;

    private Object searchLock = new Object();
    boolean inSearchMode = false;

    ArrayList<IndexListItem> contactList;
    List<IndexListItem> filterList;
    private SearchListTask curSearchTask = null;
    public static final String IndexLists = "indexLists";
    public static final String ItemClick = "indexLists";
    private IndexItemClickListener indexItemClickListener;

    public IndexListViewWithPinYinFragment() {
        Log.i("INFO", "TestFragment non-parameter constructor");
    }

    public static IndexListViewWithPinYinFragment getInstance(ArrayList<IndexListItem> indexLists) {
        IndexListViewWithPinYinFragment fragment = new IndexListViewWithPinYinFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(IndexLists, indexLists);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.index_listview_layout, container, false);
        ViewUtils.inject(this, layout);

        if (getArguments() == null || (!getArguments().containsKey(IndexLists)) || getArguments().getParcelableArrayList(IndexLists) == null) {
            return layout;
        }

        setupData();
        setupView(layout);
        setListener();
        return layout;
    }

    void setupData() {
        filterList = new ArrayList<IndexListItem>();
        contactList = getArguments().getParcelableArrayList(IndexLists);
    }

    void setupView(View layout) {
        CityAdapter adapter = new CityAdapter(activity,
                R.layout.city_item, contactList);

        listview = (ContactListViewImpl) layout.findViewById(R.id.listview);
        listview.setEmptyView(layout.findViewById(R.id.rl_no_data));
        listview.setFastScrollEnabled(true);
        listview.setAdapter(adapter);
        searchBox = (EditText) layout.findViewById(R.id.input_search_query);
    }

    void setListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position,
                                    long id) {
                try {
                    List<IndexListItem> searchList = inSearchMode ? filterList
                            : contactList;

//                    Toast.makeText(activity,
//                            searchList.get(position).getDisplayInfo(),
//                            Toast.LENGTH_SHORT).show();
                    if (indexItemClickListener != null) {
                        indexItemClickListener.itemClick(v, position, searchList.get(position));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        searchBox.addTextChangedListener(this);
    }

    @Override
    public void afterTextChanged(Editable s) {
        searchString = searchBox.getText().toString().trim().toUpperCase();

        if (curSearchTask != null
                && curSearchTask.getStatus() != AsyncTask.Status.FINISHED) {
            try {
                curSearchTask.cancel(true);
            } catch (Exception e) {
                Log.i(TAG, "Fail to cancel running search task");
            }

        }
        curSearchTask = new SearchListTask();
        curSearchTask.execute(searchString);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // do nothing
    }

    private class SearchListTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            filterList.clear();

            String keyword = params[0];

            inSearchMode = (keyword.length() > 0);

            if (inSearchMode) {
                // get all the items matching this
                for (IndexListItem item : contactList) {
                    IndexListItem contact = (IndexListItem) item;

                    boolean isPinyin = contact.getIndexName().toUpperCase()
                            .indexOf(keyword) > -1;
                    boolean isChinese = contact.getNickName().indexOf(keyword) > -1;

                    if (isPinyin || isChinese) {
                        filterList.add(item);
                    }

                }

            }
            return null;
        }

        protected void onPostExecute(String result) {

            synchronized (searchLock) {

                if (inSearchMode) {

                    CityAdapter adapter = new CityAdapter(activity,
                            R.layout.city_item, filterList);
                    adapter.setInSearchMode(true);
                    listview.setInSearchMode(true);
                    listview.setAdapter(adapter);
                } else {
                    CityAdapter adapter = new CityAdapter(activity,
                            R.layout.city_item, contactList);
                    adapter.setInSearchMode(false);
                    listview.setInSearchMode(false);
                    listview.setAdapter(adapter);
                }
            }

        }
    }

    public void setIndexItemClickListener(IndexItemClickListener indexItemClickListener) {
        this.indexItemClickListener = indexItemClickListener;
    }

    public IndexItemClickListener getIndexItemClickListener() {
        return indexItemClickListener;
    }

    public static interface IndexItemClickListener extends Serializable {
        public void itemClick(View v, int position, IndexListItem indexListItem);
    }

}
