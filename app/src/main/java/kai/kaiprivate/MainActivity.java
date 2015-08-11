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
//        KaiMultiImageSelectorActivity;
//        KaiPhotoGallary;
//        PinyinListActivity;
//        UseLovelyView;
//        KaiPullToRefresh;
//        KaiPhotoView;
//        KaiPhotoViewB;
//        KaiPhotoViewPngAbove;
//        KaiOpenCV;
//        KaiUniversalImageLoader;
//        KaiUltraPullToRefresh;
//        KaiUIL4MMF;
//        KaiGIF;
//        KaiKenBurnView;
//        KaiTextViewTimePicker4MMF;
//        KaiBitmapUtils;
//        KaiColorArt;
//        KaiHttpUtils;
//        KaiAndroidBootStrap;
//        KaiCaligraphy;
//        KaiGMapUtils;
//        KaiGMapUtilsCustomMarker;
//        KaiObserver;
//        KaiDiscroll;
//        KaiEventBus;
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
