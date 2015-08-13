package kai.kaiprivate;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import kai.kaiprivate.kai.KaiReflection;
import kai.kaiprivate.thirdparty.guava.KaiGuavaOrderList;
import kai.kaiprivate.thirdparty.rxjava.KaiRxJava;

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
//KaiMultiImageSelectorActivity;
//KaiPhotoGallary;
//PinyinListActivity;
//UseLovelyView;
//KaiPullToRefresh;
//KaiPhotoView;
//KaiPhotoViewB;
//KaiPhotoViewPngAbove;
//KaiOpenCV;
//KaiUniversalImageLoader;
//KaiUltraPullToRefresh;
//KaiUIL4MMF;
//KaiGIF;
//KaiKenBurnView;
//KaiTextViewTimePicker4MMF;
//KaiBitmapUtils;
//KaiColorArt;
//KaiHttpUtils;
//KaiAndroidBootStrap;
//KaiCaligraphy;
//KaiGMapUtils;
//KaiGMapUtilsCustomMarker;
//KaiObserver;
//KaiDiscroll;
//KaiEventBus;
//KaiParse
//KaiRxJava
//KaiGuavaOrderList
KaiReflection











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
