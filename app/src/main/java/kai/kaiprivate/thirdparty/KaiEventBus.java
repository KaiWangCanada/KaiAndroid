package kai.kaiprivate.thirdparty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import kai.kaiprivate.R;

public class KaiEventBus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_event_bus);

        EventBus.getDefault().register(this);

    }

    public void click(View view) {
        EventBus.getDefault().post(new MessageEvent("send"));

    }

    // This method will be called when a MessageEvent is posted
    @Subscribe
    public void onEvent(MessageEvent event){
        Toast.makeText(this, (String) event.message, Toast.LENGTH_SHORT).show();
    }

//    @Override
//    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
//
//        super.onDestroy();
//    }

    public class MessageEvent {
        public final Object message;

        public MessageEvent(Object message) {
            this.message = message;
        }
    }
}
