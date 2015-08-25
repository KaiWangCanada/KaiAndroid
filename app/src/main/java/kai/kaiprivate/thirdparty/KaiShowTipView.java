package kai.kaiprivate.thirdparty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import net.frederico.showtipsview.ShowTipsBuilder;
import net.frederico.showtipsview.ShowTipsView;
import net.frederico.showtipsview.ShowTipsViewInterface;

import kai.kaiprivate.R;

public class KaiShowTipView extends AppCompatActivity {

    Button btn_one;
    ShowTipsView showtips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_show_tip_view);

        btn_one = (Button) findViewById(R.id.button1);
        Button btn_two = (Button) findViewById(R.id.button2);

        showtips = new ShowTipsBuilder(this)
                .setTarget(btn_one).setTitle("A magnific button")
                .setDescription("This button do nothing very well")
//                .setDelay(1000)
                .build();

        showtips.setCallback(new ShowTipsViewInterface() {
            @Override
            public void gotItClicked() {
                //Lunch new showtip
                Log.v("kai", "got it");
            }
        });
    }

    public void click(View view) {
        // ShowTipsView
        showtips.show(this);
    }
}
