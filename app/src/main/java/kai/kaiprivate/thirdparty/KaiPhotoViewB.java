package kai.kaiprivate.thirdparty;

import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.logging.LogRecord;

import kai.kaiprivate.R;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class KaiPhotoViewB extends ActionBarActivity {

    PhotoView mPhotoView;
    float mScale;
    Matrix mRec;
    private final Handler mHandler = new android.os.Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_photo_view_b);

        mPhotoView = (PhotoView) findViewById(R.id.pv);
        mPhotoView.setImageResource(R.drawable.wallpaper);

        mScale = 1;
        mPhotoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
//                mPhotoView.setRotationBy(1);
//                mScale = (float) (mScale * 1.1);
//                mPhotoView.setScale(mScale);
                mRec = new Matrix();
                mRec.postScale((float) 0.5, (float) 0.5);
//                mPhotoView.setDisplayMatrix(mRec);
//                mPhotoView.setScale(2,true);
//                mPhotoView.setAlpha((float) 0.2);
//                mPhotoView.setColorFilter(Color.RED);
//                mPhotoView.setX((float) 10);
                loopx();
            }
        });

//        loop();
    }

    private void loop() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPhotoView.setRotationBy(1);
                loop();
            }
        }, 15);
   }

    private void loopx() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPhotoView.setX(mPhotoView.getX() + 1);
                loopx();
            }
        }, 15);
    }

}
