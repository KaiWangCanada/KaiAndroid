package com.mmfcommon.activity;

import android.content.Intent;

import com.mmfcommon.adapter.ChooseBaseAdapter;
import com.mmfcommon.adapter.FlagChoiceAdapter;
import com.mmfcommon.bean.AreaCodeBean;
import com.mmfcommon.common.MmfCommonSetting;

import java.util.ArrayList;

/**
 * Created by longbin on 15/4/30.
 */
public class FlagChoiceActivity extends ChooseBaseActivity<AreaCodeBean.AreaCode> {
    FlagChoiceAdapter adapter;

    @Override
    public ChooseBaseAdapter getBaseAdapter() {
        return adapter = new FlagChoiceAdapter(itemDatas);
    }

    @Override
    public void processIntent(Intent intent) {
        itemDatas = (ArrayList<AreaCodeBean.AreaCode>) intent.getSerializableExtra(MmfCommonSetting.Item_Data);
        selectedDatas = (ArrayList<AreaCodeBean.AreaCode>) intent.getSerializableExtra(MmfCommonSetting.Selected_Item);
    }

    @Override
    public void setFinishIntentExtraData(Intent intent, ArrayList<AreaCodeBean.AreaCode> selectedItems) {
        intent.putExtra(MmfCommonSetting.Selected_Item, selectedItems);
    }

    @Override
    public void removeSelected(ArrayList<AreaCodeBean.AreaCode> selectedDatas, AreaCodeBean.AreaCode data) {
        if (null == data || null == data.id || null == selectedDatas) {
            return;
        }
        for (int i = 0; i < selectedDatas.size(); i++) {
            AreaCodeBean.AreaCode other = selectedDatas.get(i);
            if (null == other) {
                continue;
            }
            if (data.id.equals(other.id)) {
                selectedDatas.remove(i);
                return;
            }
        }
    }
}
