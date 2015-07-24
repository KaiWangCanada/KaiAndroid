package kai.kaiprivate.thirdparty;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.michaelevans.colorart.library.ColorArt;

import kai.kaiprivate.R;

public class KaiColorArt extends ActionBarActivity {
    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;

    private void assignViews() {
        mTv1 = (TextView) findViewById(R.id.tv1);
        mTv2 = (TextView) findViewById(R.id.tv2);
        mTv3 = (TextView) findViewById(R.id.tv3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_color_art);
        assignViews();

        // get a bitmap and analyze it
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lena);
        ColorArt colorArt = new ColorArt(bitmap);

        // get the colors
        mTv1.setTextColor(colorArt.getBackgroundColor());
        mTv2.setTextColor(colorArt.getPrimaryColor());
        mTv3.setTextColor(colorArt.getSecondaryColor());
//        colorArt.getDetailColor()
    }

}
