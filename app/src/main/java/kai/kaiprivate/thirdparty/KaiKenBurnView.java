package kai.kaiprivate.thirdparty;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Interpolator;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;

import kai.kaiprivate.R;

public class KaiKenBurnView extends ActionBarActivity {

//    KenBurnsView kbv = (KenBurnsView) findViewById(R.id.image);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_ken_burn_view);

//        RandomTransitionGenerator generator = new RandomTransitionGenerator(duration, interpolator);
//        kbv.setTransitionGenerator(generator);
    }

}
