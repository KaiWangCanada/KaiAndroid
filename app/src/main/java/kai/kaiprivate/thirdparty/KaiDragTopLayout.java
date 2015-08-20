package kai.kaiprivate.thirdparty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import github.chenupt.dragtoplayout.DragTopLayout;
import kai.kaiprivate.R;

public class KaiDragTopLayout extends AppCompatActivity {

    DragTopLayout dragTopLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_drag_top_layout);

        dragTopLayout = (DragTopLayout) findViewById(R.id.drag_layout);
//        dragTopLayout.over
    }

}
