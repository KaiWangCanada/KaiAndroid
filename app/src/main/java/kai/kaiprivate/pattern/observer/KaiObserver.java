package kai.kaiprivate.pattern.observer;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Observable;
import java.util.Observer;

import kai.kaiprivate.R;

public class KaiObserver extends Activity implements Observer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_observer);

        // add observer
        SyncManager.getInstance().addObserver(this);
    }

    @Override
    protected void onDestroy() {
        // delete observer
        SyncManager.getInstance().deleteObserver(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kai_observer, menu);
        return true;
    }

    private final Handler uiHandler = new Handler() {
    };

    @Override
    public void update(Observable observable, Object data) {
        Log.v("kai", "udpate");

        if (data instanceof SyncUpdateMessage) {

            SyncUpdateMessage msg = (SyncUpdateMessage) data;
            Log.v("kai", "info: " + msg.getWhat());

            if (msg.getMessageCode() != SyncUpdateMessage.SYNC_SUCCESSFUL)
                return;

            Runnable visuals = new Runnable() {

                @Override
                public void run() {
                    uiHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            // make changes on the UI here
//                            refreshListViewItems();

                        }
                    });
                }
            };
            new Thread(visuals).start();
        }
    }

    public void click(View view) {
        Log.v("kai", "click");
        SyncManager.getInstance().sync("null", "null", 3);
    }
}
