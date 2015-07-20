package kai.kaiprivate.thirdparty;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;

import kai.kaiprivate.R;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageButton;

public class KaiGIF extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_gif);

        GifImageButton gib = new GifImageButton( this );
        setContentView( gib );
        gib.setImageResource( R.drawable.giphy );
        final MediaController mc = new MediaController( this );
        mc.setMediaPlayer( (GifDrawable) gib.getDrawable() );
        mc.setAnchorView(gib);
        gib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mc.show();
            }
        });
    }

}
