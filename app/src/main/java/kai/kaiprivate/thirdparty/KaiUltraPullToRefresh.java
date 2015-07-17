package kai.kaiprivate.thirdparty;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import kai.kaiprivate.R;

public class KaiUltraPullToRefresh extends ActionBarActivity{

    ImageView mImageView;
    PtrFrameLayout ptrFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_ultra_pull_to_refresh);

        mImageView = (ImageView) findViewById(R.id.store_house_ptr_image);
        ptrFrame = (PtrFrameLayout) findViewById(R.id.store_house_ptr_frame);

        // header
        StoreHouseHeader header = new StoreHouseHeader(getApplicationContext());
        header.setPadding(0, 15, 0, 0);
        header.setPadding(0, 20, 0, 20);
        header.initWithString("Kai Ultra PTR");

        ptrFrame.setDurationToCloseHeader(1500);
        ptrFrame.setHeaderView(header);
        ptrFrame.addPtrUIHandler(header);
        ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        Log.v("kai", "refresh complete");
                        mImageView.setImageResource(R.drawable.lena);
                        ptrFrame.refreshComplete();
                    }
                }, 1800);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//                Log.v("kai", "can do refresh");
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }
}
