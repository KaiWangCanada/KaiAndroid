package kai.kaiprivate.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

import kai.kaiprivate.R;

public class KaiOpenCV extends AppCompatActivity implements View.OnTouchListener {
    ImageView mIvHair;
    int mPaddingX;
    int mPaddingY;

    RelativeLayout mLayoutImage;
    Bitmap mBitmapHair;

    Mat mMatHair;
    Mat mMatHairNew;

    Mat mMid_x, mMid_y;
    Mat mPre_x, mPre_y;
    Mat mMap_x, mMap_y;

    Point mP;
    Point mQ;
    Point dP;
    int mHeight;
    int mWidth;
    int mSize;
    double mScale = .5;
    double mSigma = 1600.;
    double mSensitivityRatio = 10;
    double mRobustRatio = 0.4;

    int mLayoutImageHeight;
    int mLayoutImageWidth;
    int mOff_x = 0;
    int mOff_y = 0;
    int[] mPixels;
    int[] mResPixels;

    boolean mIsWarping;
    Handler mHandler;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {

        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    // OpenCV code
                    doOpenCV();

                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    private void doOpenCV() {
        mIvHair = (ImageView) findViewById(R.id.iv_hair);
        mBitmapHair = BitmapFactory.decodeResource(getResources(), R.drawable.hair);
        mLayoutImage = (RelativeLayout) findViewById(R.id.l_hair);
        mHeight = mBitmapHair.getHeight();
        mWidth = mBitmapHair.getWidth();
        mBitmapHair = Bitmap.createScaledBitmap(mBitmapHair, (int) (mWidth * mScale), (int)
                (mHeight * mScale), false);
        mHeight = mBitmapHair.getHeight();
        mWidth = mBitmapHair.getWidth();
        mSize = mHeight * mWidth;

        mPixels = new int[mSize];
        mResPixels = new int[mSize];

        mIvHair.setOnTouchListener(this);

        mP = new Point();
        mQ = new Point();
        dP = new Point();

        mIsWarping = false;
        mHandler = new Handler();

        //get image matrix
        mBitmapHair.getPixels(mPixels, 0, mWidth, 0, 0, mWidth, mHeight);

        mMatHair = new Mat(mBitmapHair.getHeight(), mBitmapHair.getWidth(), CvType.CV_8UC4);
        Utils.bitmapToMat(mBitmapHair, mMatHair);
//        mMatHairNew.create(mMatHair.size(), mMatHair.type());
//        mMap_x.create(mMatHair.size(), CvType.CV_32FC1);
//        mMap_y.create(mMatHair.size(), CvType.CV_32FC1);
        mMatHairNew = new Mat(mMatHair.size(), mMatHair.type());
        mMid_x = new Mat(mMatHair.size(), CvType.CV_32FC1);
        mMid_y = new Mat(mMatHair.size(), CvType.CV_32FC1);
        mPre_x = new Mat(mMatHair.size(), CvType.CV_32FC1);
        mPre_y = new Mat(mMatHair.size(), CvType.CV_32FC1);
        mMap_x = new Mat(mMatHair.size(), CvType.CV_32FC1);
        mMap_y = new Mat(mMatHair.size(), CvType.CV_32FC1);

        for(int i = 0; i < mWidth; i++) {
            for(int j = 0; j < mHeight; j++) {
                mPre_x.put(j,i,i);
                mPre_y.put(j,i,j);
            }
        }

        mMid_x = mPre_x;
        mMid_y = mPre_y;

//        Bitmap _BitmapHair = Bitmap.createBitmap(mMatHair.cols(), mMatHair.rows(), Bitmap.Config.ARGB_8888);
//        Utils.matToBitmap(mMatHair, _BitmapHair);

        mIvHair.setImageBitmap(mBitmapHair);
        // change hair location
        mPaddingX = 100;
        mPaddingY = 0;
        mIvHair.setPadding(mPaddingX, mPaddingY, 0, 0);
//        mIvHair.setLayoutParams(cardParams);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.v("kai", "touch");

        mLayoutImageHeight = mLayoutImage.getHeight();
        mLayoutImageWidth = mLayoutImage.getWidth();
        if(mWidth > mLayoutImageWidth) {
            mOff_x = (mWidth - mLayoutImageWidth) / 2;
        }
        if(mHeight > mLayoutImageHeight) {
            mOff_y = (mHeight - mLayoutImageHeight) / 2;
        }
//        Log.v("kai", "mLayoutImageHeight: " + String.valueOf(mLayoutImageHeight));
//        Log.v("kai", "mLayoutImageWidth: " + String.valueOf(mLayoutImageWidth));
//        Log.v("kai", "mOff_x: " + String.valueOf(mOff_x));
//        Log.v("kai", "mOff_y: " + String.valueOf(mOff_y));

        final int _x = (int) (event.getX()) + mOff_x - mPaddingX;
        final int _y = (int) (event.getY()) + mOff_y - mPaddingY;

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
                if(Math.sqrt((_x - mP.x) * (_x - mP.x) + (_y - mP.y) * (_y - mP.y)) > mSensitivityRatio) {
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

                                for(int k = 0; k < mSize; k++) {
                                    int j = k / mWidth;
                                    int i = k - j * mWidth;
                                    // todo: change .get && .put to array, make it faster
                                    // find midx, midy
                                    double midx = mMid_x.get(j, i)[0];
                                    double midy = mMid_y.get(j, i)[0];
//                                    Log.v("kai", String.valueOf(mMid_y.get(j, i)[0]));
//                                    Log.v("kai", String.valueOf(i));
//                                    double midx = i;
//                                    double midy = j;
//                                    double d = Math.sqrt((i - mQ.x) * (i - mQ.x) + (j - mQ.y) * (j - mQ.y));
                                    double d = (midx - mQ.x) * (midx - mQ.x) + (midy - mQ.y) * (midy - mQ.y);
//                                    double t = 1. - Math.sqrt(d) / 300.;
                                    double t = Math.exp(- d / mSigma);
                                    if(t < 0) t = 0;

                                    // get map_x, map_y
                                    mMid_x.put(j, i, midx - dP.x * t);
                                    mMid_y.put(j, i, midy - dP.y * t);
                                    mMap_x.put(j, i, midx - dP.x * t);
                                    mMap_y.put(j, i, midy - dP.y * t);

//                                    double ti = (i - dP.x * t);
//                                    double tj = (j - dP.y * t);
//                                    int ri = (int) ti;
//                                    int rj = (int) tj;
////                                    int ri = getRound(ti);
////                                    int rj = getRound(tj);
//                                    double tx = ti - ri;
//                                    double ty = tj - rj;
//
//                                    if(ri < mWidth - 1 && ri >= 0 && rj < mHeight - 1 && rj >= 0) {
//                                        mResPixels[k] = mPixels[rj * mWidth + ri];
//                                        {
//                                            int ta = 0, tr = 0, tg = 0, tb = 0;
//                                            int t11 = 0, t12 = 0, t21 = 0, t22 = 0;
//                                            t11 = mPixels[rj * mWidth + ri];
//                                            t12 = mPixels[rj * mWidth + (ri + 1)];
//                                            t21 = mPixels[(rj + 1) * mWidth + ri];
//                                            t22 = mPixels[(rj + 1) * mWidth + (ri + 1)];
//                                            ta = (int) ((Color.alpha(t11) * (1 - ty) + Color.alpha(t21) * ty) * (1 - tx) + (Color.alpha(t12) * (1 - ty) + Color.alpha(t22) * ty) * tx);
//                                            if (ta != 0) {
//                                                tr = (int) ((Color.red(t11) * (1 - ty) + Color.red(t21) * ty) * (1 - tx) + (Color.red(t12) * (1 - ty) + Color.red(t22) * ty) * tx);
//                                                tg = (int) ((Color.green(t11) * (1 - ty) + Color.green(t21) * ty) * (1 - tx) + (Color.green(t12) * (1 - ty) + Color.green(t22) * ty) * tx);
//                                                tb = (int) ((Color.blue(t11) * (1 - ty) + Color.blue(t21) * ty) * (1 - tx) + (Color.blue(t12) * (1 - ty) + Color.blue(t22) * ty) * tx);
//                                            }
//                                            mResPixels[k] = Color.argb(ta, tr, tg, tb);
////                                            mResPixels[k] = (int) ((mPixels[rj * mWidth + ri] * (1 - ty) + mPixels[(rj + 1) * mWidth + ri] * ty) * (1 - tx) + (mPixels[rj * mWidth + ri + 1] * (1 - ty) + mPixels[(rj + 1) * mWidth + ri + 1] * ty) * tx);
//
//                                        }
//                                    }
                                }

//                                mMap_x = mMid_x - mPre_x;
//                                mMap_y = mMid_y - mPre_y;

                                // remap
                                Imgproc.remap(mMatHair, mMatHairNew, mMap_x, mMap_y, Imgproc
                                        .INTER_LINEAR, 0, new Scalar(0, 0, 0));
//                                Imgproc.remap(mMatHair, mMatHairNew, mMap_x, mMap_y, Imgproc
//                                        .CV_WARP_INVERSE_MAP, 0, new Scalar(0, 0, 0));

                                final Bitmap _BitmapHairNew = Bitmap.createBitmap(mMatHairNew.cols(), mMatHairNew.rows(), Bitmap.Config.ARGB_8888);
                                Utils.matToBitmap(mMatHairNew, _BitmapHairNew);

//                                final Bitmap _BitmapHairNew = Bitmap.createBitmap(mWidth, mHeight,
//                                        Bitmap.Config.ARGB_8888);
//                                _BitmapHairNew.setPixels(mResPixels, 0, mWidth, 0, 0, mWidth,
//                                        mHeight);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mIvHair.setImageBitmap(_BitmapHairNew);
                                    }
                                });

                                // reset mP
                                mP.x = mQ.x;
                                mP.y = mQ.y;

                                // reset mMatHair
//                                mMatHair = mMatHairNew;

                                // reset mPixels
//                                for(int i = 0; i < mSize; i ++) {
//                                    mPixels[i] = mResPixels[i];
//                                }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_open_cv);



    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
//            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }
}
