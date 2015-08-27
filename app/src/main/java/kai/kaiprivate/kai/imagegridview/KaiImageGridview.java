package kai.kaiprivate.kai.imagegridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import kai.kaiprivate.R;

public class KaiImageGridview extends AppCompatActivity {
    GridView gv;
    ImageGridviewAdapter imageGridviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_image_gridview);

        gv = (GridView) findViewById(R.id.gv);
        imageGridviewAdapter = new ImageGridviewAdapter(this);
        gv.setAdapter(imageGridviewAdapter);

    }

}
