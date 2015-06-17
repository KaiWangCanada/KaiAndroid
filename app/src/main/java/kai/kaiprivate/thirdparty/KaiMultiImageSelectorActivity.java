package kai.kaiprivate.thirdparty;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import kai.kaiprivate.R;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;


public class KaiMultiImageSelectorActivity extends ActionBarActivity {

    private static final int REQUEST_IMAGE = 2;
    private ArrayList<String> mSelectedPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_multi_image_selector);

        Intent intent = new Intent(KaiMultiImageSelectorActivity.this, MultiImageSelectorActivity.class);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 3);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity
                .MODE_MULTI);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(REQUEST_IMAGE == requestCode) {
            if(RESULT_OK == resultCode) {
                mSelectedPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                Log.v("kai", String.valueOf(mSelectedPath));
            }
        }
        finish();
    }

}
