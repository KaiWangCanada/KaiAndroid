package kai.kaiprivate.thirdparty;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Config;
import android.util.FloatMath;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import kai.kaiprivate.R;
import uk.co.senab.photoview.PhotoView;

public class KaiPhotoViewPngAbove extends AppCompatActivity {

    PhotoView mPvHair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_photo_view_png_above);

        mPvHair = (PhotoView) findViewById(R.id.pv_hair);

        Bitmap _bitmapHair = BitmapFactory.decodeResource(getResources(), R.drawable.lena);
        Log.v("kai", String.valueOf(_bitmapHair));
        mPvHair.setImageBitmap(_bitmapHair);

        //get image matrix
        int _height = _bitmapHair.getHeight();
        int _width = _bitmapHair.getWidth();
        int _size = _height * _width;
        int[] pixels = new int[_size];
        int[] resPixels = new int[_size];
        _bitmapHair.getPixels(pixels, 0, _width, 0, 0, _width, _height);
//        Log.v("kai", String.valueOf(pixels));
//        Log.v("kai", String.valueOf((int)1.8));
        int[] p = {500, 500};
        int[] dp = {100,0};
        int[] q = {p[0] + dp[0], p[1] + dp[1]};
        for(int k = 0; k < _size; k++) {
            int j = k / _width;
            int i = k - j * _width;
            double d = Math.sqrt((i - q[0]) * (i - q[0]) + (j - q[1]) * (j - q[1]));
            double t = 1. - d / 400.;
            if(t < 0)
                t = 0;
            int ri = (int) (i - dp[0] * t);
            int rj = (int) (j - dp[1] * t);
            if(ri < _width && ri >= 0 && rj < _height && rj >=0) {
                resPixels[k] = pixels[rj * _width + ri];
            }

        }


//        for(int i = 0; i < pixels.length; i++) {
//            int A, R, G, B;
//            A = Color.alpha(pixels[i]);
//            R = Color.red(pixels[i]);
//            G = Color.green(pixels[i]);
//            B = Color.blue(pixels[i]);
//            pixels[i] = Color.argb(A,R,0,0);
//        }
        Bitmap _newBitmap = Bitmap.createBitmap(_width, _height, Bitmap.Config.ARGB_8888);
        _newBitmap.setPixels(resPixels, 0, _width, 0, 0, _width, _height);
        mPvHair.setImageBitmap(_newBitmap);
    }

}
