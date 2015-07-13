package kai.kaiprivate.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

import kai.kaiprivate.R;

public class KaiOpenCV extends ActionBarActivity {
    ImageView mIvHair;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {

        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    // OpenCV code
                    Bitmap oriBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hair);
                    Mat pMat;
                    pMat = new Mat(oriBitmap.getHeight(), oriBitmap.getWidth(), CvType.CV_8UC4);
                    Utils.bitmapToMat(oriBitmap, pMat);
                    /*try {
                        pMat = Utils.loadResource(KaiOpenCV.this, R.drawable.lena);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    Log.v("kai", String.valueOf(pMat.type()));

//                    Imgproc.cvtColor(pMat, pMat, Imgproc.COLOR_RGB2GRAY);

                    Bitmap bitmap = Bitmap.createBitmap(pMat.cols(), pMat.rows(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(pMat, bitmap);

                    mIvHair.setImageBitmap(bitmap);

                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_open_cv);

        mIvHair = (ImageView) findViewById(R.id.iv_hair);





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
