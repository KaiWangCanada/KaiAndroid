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

        // 1. ArrayList<IndexListItem>
        ArrayList<IndexListItem> pinyinList = new ArrayList<>();
        String[] nickName = {"一", "二", "三", "一", "二", "三", "一", "二", "三"};
        String[] indexName = {"yi", "er", "san", "yi", "er", "san", "yi", "er", "san"};
        for (int i = 0; i < nickName.length; i++) {
            // 2. IndexListItem
            IndexListItem _item = new IndexListItem();
            _item.setNickName(nickName[i]);
            _item.setIndexName(indexName[i]);
            _item.setItemId(String.valueOf(i));
            pinyinList.add(_item);
        }

        // 3. IndexListViewWithPinYinFragment
        IndexListViewWithPinYinFragment indexListViewWithPinYinFragment = IndexListViewWithPinYinFragment.getInstance(pinyinList);
        // 4. output to FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.index_listview_layout, indexListViewWithPinYinFragment).commit();
    }

}
