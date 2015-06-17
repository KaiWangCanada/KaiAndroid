package kai.kaiprivate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;

import kai.kaiprivate.func.Funcs;

public class MainActivity extends ActionBarActivity {

    Class mClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Funcs.useApplication((KaiApplication) getApplication());
//        Funcs.useTelephoneManager((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE));
//
//        mClass = KaiMultiImageSelectorActivity.class;
        mClass = KaiPhotoGallary.class;

        Intent intent = new Intent(MainActivity.this, mClass);
        startActivity(intent);

    }

}
