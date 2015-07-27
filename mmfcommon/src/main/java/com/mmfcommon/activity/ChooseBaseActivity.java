package com.mmfcommon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mmfcommon.R;
import com.mmfcommon.adapter.ChooseBaseAdapter;
import com.mmfcommon.common.MmfCommonSetting;
import com.unit.common.ui.StatusBarManager;

import java.util.ArrayList;

/**
 * the common activity for choose some condition.
 * Created by longbin on 15/4/17.
 */
public abstract class ChooseBaseActivity<T> extends AppBaseActivity {

//    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

//    @ViewInject(R.id.lv_choose)
    private ListView lvChoose;
    private ChooseBaseAdapter adapter;

    private boolean multiSelectable = false;
    public ArrayList<T> itemDatas;

    public ArrayList<T> selectedDatas;

    private String title;

    public abstract ChooseBaseAdapter getBaseAdapter();

    public abstract void processIntent(Intent intent);

    public abstract void setFinishIntentExtraData(Intent intent, ArrayList<T> selectedItems);

    public abstract void removeSelected(ArrayList<T> selectedDatas, T data);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
//        ViewUtils.inject(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvChoose = (ListView) findViewById(R.id.lv_choose);

        handleIntent(getIntent());
        initToolBarSetting();

        int choiceMode = multiSelectable ? ListView.CHOICE_MODE_MULTIPLE : ListView.CHOICE_MODE_SINGLE;
        lvChoose.setChoiceMode(choiceMode);
        adapter = getBaseAdapter();
        adapter.getSelectedItem().clear();
        if (null != selectedDatas) {
            adapter.getSelectedItem().addAll(selectedDatas);
        }
        lvChoose.setAdapter(adapter);
        initSelectedItem();
        lvChoose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!multiSelectable) {
                    itemClickInSingleMode(position);
                } else {
                    itemClickInMultiMode(position);
                }
            }
        });
    }

    private void initSelectedItem() {
        if (itemDatas == null || itemDatas.isEmpty() || null == selectedDatas || selectedDatas.isEmpty()) {
            return;
        }
        for (int i = 0; i < itemDatas.size(); i++) {
            if (adapter.contains(selectedDatas, itemDatas.get(i))) {
                lvChoose.setItemChecked(i, true);
            } else {
                lvChoose.setItemChecked(i, false);
            }
        }
    }

    private void itemClickInSingleMode(int position) {
        adapter.getSelectedItem().clear();
        adapter.getSelectedItem().add(adapter.getItem(position));
        lvChoose.clearChoices();
        lvChoose.setItemChecked(position, true);
        adapter.notifyDataSetChanged();
    }

    private void itemClickInMultiMode(int position) {
        SparseBooleanArray checked = lvChoose.getCheckedItemPositions();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int positionTemp = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            boolean isChecked = checked.valueAt(i);
            if (positionTemp == position) {
                if (isChecked) {
                    adapter.getSelectedItem().add(adapter.getItem(position));
                } else {
                    removeSelected(adapter.getSelectedItem(), (T) adapter.getItem(position));
//                    adapter.getSelectedItem().remove(adapter.getItem(position));
                }
                lvChoose.setItemChecked(position, isChecked);
                break;
            }
        }
        adapter.notifyDataSetChanged();

    }


    /**
     * init the toolbar and setting the click event listener.
     */
    private void initToolBarSetting() {
        StatusBarManager.setStatusBarTranslucent(this, toolbar, R.color.toolbar);

        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.slt_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.inflateMenu(R.menu.menu_choose);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menu_choose) {
                    finishChoose();
                }
                return true;
            }
        });
    }

    private void handleIntent(Intent intent) {
        multiSelectable = intent.getBooleanExtra(MmfCommonSetting.MultiSelectable, false);
//        itemDatas = intent.getStringArrayListExtra(OAConstants.Item_Data);
        processIntent(intent);
        title = intent.getStringExtra(MmfCommonSetting.Title);
    }

    private void finishChoose() {
        Intent intent = new Intent();
        if (null != adapter) {
//            intent.putStringArrayListExtra(OAConstants.Selected_Item, adapter.getSelectedItem());
            setFinishIntentExtraData(intent, adapter.getSelectedItem());
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    public ListView getLvChoose() {
        return lvChoose;
    }
}
