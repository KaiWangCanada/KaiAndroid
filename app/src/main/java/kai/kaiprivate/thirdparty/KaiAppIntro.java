package kai.kaiprivate.thirdparty;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import kai.kaiprivate.R;

public class KaiAppIntro extends AppIntro {

    // Please DO NOT override onCreate. Use init
    @Override
    public void init(Bundle savedInstanceState) {

        // Add your slide's fragments here
        // AppIntro will automatically generate the dots indicator and buttons.
//        addSlide(first_fragment);
//        addSlide(second_fragment);
//        addSlide(third_fragment);
//        addSlide(fourth_fragment);

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest
        String title = "hi title";
        String description = "hi description";
        for(int i = 0; i < 3; i++) {
            addSlide(AppIntroFragment.newInstance(title + " " + i, description, R.drawable.droid, R.color.redlight));

        }

        // OPTIONAL METHODS
        // Override bar/separator color
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button
        showSkipButton(true);
        showDoneButton(true);

        // Turn vibration on and set intensity
        // NOTE: you will probably need to ask VIBRATE permesssion in Manifest
//        setVibrate(true);
//        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed() {
        // Do something when users tap on Skip button.
        Log.v("kai", "skip");
    }

    @Override
    public void onDonePressed() {
        // Do something when users tap on Done button.
        Log.v("kai", "done");
    }
}
