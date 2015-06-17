package kai.kaiprivate;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import kai.kaiprivate.func.customview.UseLovelyView;

public class MainActivity extends ActionBarActivity {

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
        mClass = UseLovelyView.class;

        Intent intent = new Intent(MainActivity.this, mClass);
        startActivity(intent);

    }

}
