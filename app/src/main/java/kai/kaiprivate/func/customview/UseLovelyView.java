package kai.kaiprivate.func.customview;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import kai.kaiprivate.R;

public class UseLovelyView extends ActionBarActivity {

    private LovelyView mLovelyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_lovely_view);

        mLovelyView = (LovelyView) findViewById(R.id.custView);

    }

    public void show(View view) {
        mLovelyView.setCircleColor(Color.GREEN);
        mLovelyView.setCircleText("click");
        mLovelyView.setLabelColor(Color.MAGENTA);
    }
}
