package kai.kaiprivate.thirdparty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import kai.kaiprivate.R;

public class KaiYoyo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_yoyo);
    }

    public void click(View v) {
        YoYo.with(Techniques.Tada)
                .duration(700)
                .playOn(findViewById(R.id.tv));
    }

}
