package kai.kaiprivate.thirdparty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import kai.kaiprivate.R;

public class KaiYoyo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_yoyo);

    }

    public void click(View v) {
        YoYo.with(Bounce.BounceIn)
                .duration(700)
                .playOn(findViewById(R.id.tv));
    }

}
