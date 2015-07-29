package kai.kaiprivate.thirdparty;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.beardedhen.androidbootstrap.FontAwesomeText;

import kai.kaiprivate.R;

public class KaiAndroidBootStrap extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_android_boot_strap);

        //get access to some FontAwesomeText items in the layout
        FontAwesomeText tv1 = (FontAwesomeText) findViewById(R.id.lblOne);
        FontAwesomeText tv2 = (FontAwesomeText) findViewById(R.id.lblTwo);
        FontAwesomeText tv3 = (FontAwesomeText) findViewById(R.id.lblThree);

        //flashing forever FAST
        tv1.startFlashing(this, true, FontAwesomeText.AnimationSpeed.FAST);

        //rotating clockwise slowly
        tv2.startRotate(this, true, FontAwesomeText.AnimationSpeed.SLOW);

        //rotating anti-clockwise at medium speed
        tv3.startRotate(this, false, FontAwesomeText.AnimationSpeed.MEDIUM);
    }

}
