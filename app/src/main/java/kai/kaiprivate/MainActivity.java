package kai.kaiprivate;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import kai.kaiprivate.kai.KaiTextViewTimePicker4MMF;
import kai.kaiprivate.kai.KaiUIL4MMF;
import kai.kaiprivate.pattern.observer.KaiObserver;
import kai.kaiprivate.thirdparty.KaiAndroidBootStrap;
import kai.kaiprivate.thirdparty.KaiBitmapUtils;
import kai.kaiprivate.thirdparty.KaiCaligraphy;
import kai.kaiprivate.thirdparty.KaiColorArt;
import kai.kaiprivate.thirdparty.KaiEventBus;
import kai.kaiprivate.thirdparty.KaiHttpUtils;
import kai.kaiprivate.thirdparty.KaiKenBurnView;
import kai.kaiprivate.thirdparty.discrollview.KaiDiscroll;
import kai.kaiprivate.thirdparty.gmaputils.KaiGMapUtils;
import kai.kaiprivate.thirdparty.gmaputils.KaiGMapUtilsCustomMarker;
import kai.kaiprivate.thirdparty.parse.KaiParse;

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

        mClass =
//        KaiMultiImageSelectorActivity.class;
//        KaiPhotoGallary.class;
//        PinyinListActivity.class;
//        UseLovelyView.class;
//        KaiPullToRefresh.class;
//        KaiPhotoView.class;
//        KaiPhotoViewB.class;
//        KaiPhotoViewPngAbove.class;
//        KaiOpenCV.class;
//        KaiUniversalImageLoader.class;
//        KaiUltraPullToRefresh.class;
//        KaiUIL4MMF.class;
//        KaiGIF.class;
//        KaiKenBurnView.class;
//        KaiTextViewTimePicker4MMF.class;
//        KaiBitmapUtils.class;
//        KaiColorArt.class;
//        KaiHttpUtils.class;
//        KaiAndroidBootStrap.class;
//        KaiCaligraphy.class;
//        KaiGMapUtils.class;
//        KaiGMapUtilsCustomMarker.class;
//        KaiObserver.class;
//        KaiDiscroll.class;
//        KaiEventBus.class;
                KaiParse
                .class;


        Intent intent = new Intent(MainActivity.this, mClass);
        startActivityForResult(intent, RESULT);













    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
