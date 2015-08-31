package kai.kaiprivate.thirdparty;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import kai.kaiprivate.R;

public class KaiFresco extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Fresco.initialize(this);

        setContentView(R.layout.activity_kai_fresco);

        Uri uri = Uri.parse("https://raw.githubusercontent" +
                ".com/facebook/fresco/gh-pages/static/fresco-logo.png");

        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
        draweeView.setImageURI(uri);
    }

}
