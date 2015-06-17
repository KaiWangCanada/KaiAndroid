package kai.kaiprivate;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.wq.photo.MediaChoseActivity;

import java.util.ArrayList;


public class KaiPhotoGallary extends ActionBarActivity {

    private static final int REQUEST_IMAGE = 1000;
    ArrayList<String> mSelectedPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_photo_gallary);

        Intent intent = new Intent(KaiPhotoGallary.this, MediaChoseActivity.class);
        intent.putExtra("chose_mode", 1);
        //最多支持选择多少张
        intent.putExtra("max_chose_count", 6);
        /*//是否剪裁图片(只有单选模式才有剪裁)
        intent.putExtra("crop", true);
        //输出剪裁图片的宽度
        intent.putExtra("crop_image_w", 720);
        //输出剪裁图片的高度
        intent.putExtra("crop_image_h", 720);*/
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(REQUEST_IMAGE == requestCode) {
            if(RESULT_OK == resultCode) {
                mSelectedPath = data.getStringArrayListExtra("data");
                Log.v("kai", String.valueOf(mSelectedPath));
            }
        }
        finish();
    }
}
