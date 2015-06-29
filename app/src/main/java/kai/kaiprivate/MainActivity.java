package kai.kaiprivate;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import kai.kaiprivate.func.customview.UseLovelyView;
import kai.kaiprivate.thirdparty.KaiPhotoView;
import kai.kaiprivate.thirdparty.KaiPhotoViewB;
import kai.kaiprivate.thirdparty.KaiPhotoViewPngAbove;
import kai.kaiprivate.thirdparty.KaiPullToRefresh;

public class MainActivity extends ActionBarActivity {
    private static final int RESULT = 0;
    Class mClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        Funcs.useApplication((KaiApplication) getApplication());
//        Funcs.useTelephoneManager((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE));
//        Funcs.useMResource(MainActivity.this, "id", "tv");

//
//        mClass = KaiMultiImageSelectorActivity.class;
//        mClass = KaiPhotoGallary.class;
//        mClass = PinyinListActivity.class;
//        mClass = UseLovelyView.class;
//        mClass = KaiPullToRefresh.class;
//        mClass = KaiPhotoView.class;
//        mClass = KaiPhotoViewB.class;
        mClass = KaiPhotoViewPngAbove.class;

        Intent intent = new Intent(MainActivity.this, mClass);
        startActivityForResult(intent, RESULT);










    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
