package kai.kaiprivate.thirdparty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.plattysoft.leonids.ParticleSystem;

import kai.kaiprivate.R;

public class KaiParticle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_particle);
    }

    public void click(View view) {
        new ParticleSystem(this, 20, R.drawable.snow20, 1000)
                .setSpeedRange(0.2f, 0.5f)
                .oneShot(view, 20);
    }

}
