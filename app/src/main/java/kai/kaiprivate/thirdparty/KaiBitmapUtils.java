package kai.kaiprivate.thirdparty;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;

import kai.kaiprivate.R;

public class KaiBitmapUtils extends ActionBarActivity {
    private ImageView mIv;

    private void assignViews() {
        mIv = (ImageView) findViewById(R.id.iv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_bitmap_utils);

        assignViews();
        BitmapUtils bitmapUtils = new BitmapUtils(this);
        // 加载网络图片
        bitmapUtils.display(mIv, "http://bbs.lidroid.com/static/image/common/logo.png");

    }

}
