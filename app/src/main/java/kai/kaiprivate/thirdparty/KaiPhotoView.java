package kai.kaiprivate.thirdparty;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import kai.kaiprivate.R;
import uk.co.senab.photoview.PhotoViewAttacher;

public class KaiPhotoView extends ActionBarActivity {
    ImageView mImageView;
    PhotoViewAttacher mAttacher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_photo_view);

        // Any implementation of ImageView can be used!
        mImageView = (ImageView) findViewById(R.id.iv_photo);

        // Set the Drawable displayed
        Drawable bitmap = getResources().getDrawable(R.drawable.wallpaper);
        mImageView.setImageDrawable(bitmap);

        // Attach a PhotoViewAttacher, which takes care of all of the zooming functionality.
        mAttacher = new PhotoViewAttacher(mImageView);
    }

}
