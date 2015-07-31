package kai.kaiprivate.thirdparty.gmaputils;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import kai.kaiprivate.R;

public abstract class BaseGMapActivity extends FragmentActivity {
    private GoogleMap mMap;

    protected int getLayoutId() {
        return R.layout.activity_base_gmap;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap != null) {
            return;
        }
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        if (mMap != null) {
            startGMap();
        }
    }

    /**
     * Run the demo-specific code.
     */
    protected abstract void startGMap();

    protected GoogleMap getMap() {
        setUpMapIfNeeded();
        return mMap;
    }
}