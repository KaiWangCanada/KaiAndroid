package kai.kaiprivate.thirdparty;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Config;
import android.util.FloatMath;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import kai.kaiprivate.R;
import uk.co.senab.photoview.PhotoView;

public class KaiPhotoViewPngAbove extends AppCompatActivity implements View.OnTouchListener{

    ImageView mPvHair;
    RelativeLayout mLayoutImage;
    Point mP;
    Point mQ;
    Point dP;

//    Point mPast;

    Bitmap _bitmapHair;
    int _height;
    int _width;
    int _size;
    double _scale = 0.5;
    int mLayoutImageHeight;
    int mLayoutImageWidth;
    int mOff_x = 0;
    int mOff_y = 0;
    int[] pixels;
    int[] resPixels;

    boolean mIsWarping;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_photo_view_png_above);

        mPvHair = (ImageView) findViewById(R.id.pv_hair);
        mLayoutImage = (RelativeLayout) findViewById(R.id.l_hair);

        _bitmapHair = BitmapFactory.decodeResource(getResources(), R.drawable.hair);
        _height = _bitmapHair.getHeight();
        _width = _bitmapHair.getWidth();
        _bitmapHair = Bitmap.createScaledBitmap(_bitmapHair, (int)
                (_width * _scale), (int) (_height * _scale), false);
        _height = _bitmapHair.getHeight();
        _width = _bitmapHair.getWidth();
        _size = _height * _width;

        pixels = new int[_size];
        resPixels = new int[_size];
//        Log.v("kai", String.valueOf(_bitmapHair));
        mPvHair.setImageBitmap(_bitmapHair);
        mPvHair.setOnTouchListener(this);

        mP = new Point();
        mQ = new Point();
        dP = new Point();
//        mPast = new Point();

        mIsWarping = false;
        mHandler = new Handler();

        //get image matrix
        _bitmapHair.getPixels(pixels, 0, _width, 0, 0, _width, _height);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.v("kai", "touch");

        mLayoutImageHeight = mLayoutImage.getHeight();
        mLayoutImageWidth = mLayoutImage.getWidth();
        if(_width > mLayoutImageWidth) {
            mOff_x = (_width - mLayoutImageWidth) / 2;
        }
        if(_height > mLayoutImageHeight) {
            mOff_y = (_height - mLayoutImageHeight) / 2;
        }
//        Log.v("kai", "mLayoutImageHeight: " + String.valueOf(mLayoutImageHeight));
//        Log.v("kai", "mLayoutImageWidth: " + String.valueOf(mLayoutImageWidth));
//        Log.v("kai", "mOff_x: " + String.valueOf(mOff_x));
//        Log.v("kai", "mOff_y: " + String.valueOf(mOff_y));

        final int _x = (int) (event.getX()) + mOff_x;
        final int _y = (int) (event.getY()) + mOff_y;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.v("kai", "action down");
                mP.x = _x;
                mP.y = _y;
//                mPast.x = _x;
//                mPast.y = _y;
                break;

            case MotionEvent.ACTION_MOVE:
                Log.v("kai", "moving");
                //if move stopped
                if(Math.sqrt((_x - mP.x) * (_x - mP.x) + (_y - mP.y) * (_y - mP.y)) > 20) {
                    Log.v("kai", "move enough, warping");
                    // do warp
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(!mIsWarping) {
                                mIsWarping = true;
                                mQ.x = _x;
                                mQ.y = _y;
                                dP.x = mQ.x - mP.x;
                                dP.y = mQ.y - mP.y;
//                                Log.v("kai", "_x: " + String.valueOf(_x));
//                                Log.v("kai", "_y: " + String.valueOf(_y));
//                                Log.v("kai", "mP.x: " + String.valueOf(mP.x));
//                                Log.v("kai", "mP.y: " + String.valueOf(mP.y));
//                                Log.v("kai", "mQ.x: " + String.valueOf(mQ.x));
//                                Log.v("kai", "mQ.y: " + String.valueOf(mQ.y));
//                                Log.v("kai", "dP.x: " + String.valueOf(dP.x));
//                                Log.v("kai", "dP.y: " + String.valueOf(dP.y));

                                for(int k = 0; k < _size; k++) {
                                    int j = k / _width;
                                    int i = k - j * _width;
//                                    double d = Math.sqrt((i - mQ.x) * (i - mQ.x) + (j - mQ.y) * (j - mQ.y));
                                    double d = (i - mQ.x) * (i - mQ.x) + (j - mQ.y) * (j - mQ.y);
                                    double t = 1. - Math.sqrt(d) / 300.;
//                                    double t = Math.exp(- d / 9000);
                                    if(t < 0) t = 0;

                                    /*double ti = (i - dP.x * t);
                                    double tj = (j - dP.y * t);
                                    int ri = (int) ti;
                                    int rj = (int) tj;
//                                    int ri = getRound(ti);
//                                    int rj = getRound(tj);
                                    double tx = ti - ri;
                                    double ty = tj - rj;

                                    if(ri < _width - 1 && ri >= 0 && rj < _height - 1 && rj >= 0) {
                                        resPixels[k] = pixels[rj * _width + ri];
                                        {
                                            int ta = 0, tr = 0, tg = 0, tb = 0;
                                            int t11 = 0, t12 = 0, t21 = 0, t22 = 0;
                                            t11 = pixels[rj * _width + ri];
                                            t12 = pixels[rj * _width + (ri + 1)];
                                            t21 = pixels[(rj + 1) * _width + ri];
                                            t22 = pixels[(rj + 1) * _width + (ri + 1)];
                                            ta = (int) ((Color.alpha(t11) * (1 - ty) + Color.alpha(t21) * ty) * (1 - tx) + (Color.alpha(t12) * (1 - ty) + Color.alpha(t22) * ty) * tx);
                                            if (ta != 0) {
                                                tr = (int) ((Color.red(t11) * (1 - ty) + Color.red(t21) * ty) * (1 - tx) + (Color.red(t12) * (1 - ty) + Color.red(t22) * ty) * tx);
                                                tg = (int) ((Color.green(t11) * (1 - ty) + Color.green(t21) * ty) * (1 - tx) + (Color.green(t12) * (1 - ty) + Color.green(t22) * ty) * tx);
                                                tb = (int) ((Color.blue(t11) * (1 - ty) + Color.blue(t21) * ty) * (1 - tx) + (Color.blue(t12) * (1 - ty) + Color.blue(t22) * ty) * tx);
                                            }
                                            resPixels[k] = Color.argb(ta, tr, tg, tb);
//                                            resPixels[k] = (int) ((pixels[rj * _width + ri] * (1 - ty) + pixels[(rj + 1) * _width + ri] * ty) * (1 - tx) + (pixels[rj * _width + ri + 1] * (1 - ty) + pixels[(rj + 1) * _width + ri + 1] * ty) * tx);

                                        }
                                    }*/
                                }

                                final Bitmap _newBitmap = Bitmap.createBitmap(_width, _height,
                                        Bitmap.Config.ARGB_8888);
                                _newBitmap.setPixels(resPixels, 0, _width, 0, 0, _width,
                                        _height);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mPvHair.setImageBitmap(_newBitmap);
                                    }
                                });

                                // reset mP
                                mP.x = mQ.x;
                                mP.y = mQ.y;

                                // reset pixels
                                for(int i = 0; i < _size; i ++) {
                                    pixels[i] = resPixels[i];
                                }

                                // reset mIsWarping
                                mIsWarping = false;
                            }
                        }
                    }).start();
                }
                // reset mPast
//                mPast.x = _x;
//                mPast.y = _y;
                break;

            case MotionEvent.ACTION_UP:

                break;
        }

        return true;
    }

    private class Point {
        int x;
        int y;
    }

    private int getRound(double t) {
        int a = (int) t;
        if((t - a) >= 0.5)
            a++;
        return a;
    }

}

