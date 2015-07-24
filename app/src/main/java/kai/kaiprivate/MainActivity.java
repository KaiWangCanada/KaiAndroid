package kai.kaiprivate;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import kai.kaiprivate.kai.KaiTextViewTimePicker4MMF;
import kai.kaiprivate.kai.KaiUIL4MMF;
import kai.kaiprivate.thirdparty.KaiBitmapUtils;
import kai.kaiprivate.thirdparty.KaiKenBurnView;

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
//        mClass = KaiPhotoViewPngAbove.class;
//        mClass = KaiOpenCV.class;
//        mClass = KaiUniversalImageLoader.class;
//        mClass = KaiUltraPullToRefresh.class;
//        mClass = KaiUIL4MMF.class;
//        mClass = KaiGIF.class;
//        mClass = KaiKenBurnView.class;
//        mClass = KaiTextViewTimePicker4MMF.class;
        mClass = KaiBitmapUtils.class;

        Intent intent = new Intent(MainActivity.this, mClass);
        startActivityForResult(intent, RESULT);










    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
