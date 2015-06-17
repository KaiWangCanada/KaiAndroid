package kai.kaiprivate.tframe;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.liucanwen.citylist.model.IndexListItem;
import com.unit.common.fragment.IndexListViewWithPinYinFragment;

import java.util.ArrayList;

import kai.kaiprivate.R;

public class PinyinListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinyin_list);

        ArrayList<IndexListItem> pinyinList = new ArrayList<>();
        IndexListItem _item = new IndexListItem();
        _item.setNickName("王");
        _item.setIndexName("wang");
        _item.setItemId("0");
        pinyinList.add(_item);

        _item.setNickName("申屠");
        _item.setIndexName("shentu");
        _item.setItemId("1");
        pinyinList.add(_item);

        _item.setNickName("高");
        _item.setIndexName("gao");
        _item.setItemId("2");
        pinyinList.add(_item);

        IndexListViewWithPinYinFragment indexListViewWithPinYinFragment = IndexListViewWithPinYinFragment.getInstance(pinyinList);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.index_listview_layout, indexListViewWithPinYinFragment).commit();
    }

}
