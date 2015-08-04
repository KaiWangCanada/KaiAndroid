package kai.kaiprivate.thirdparty.gmaputils;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import kai.kaiprivate.R;

/**
 * Simple activity demonstrating ClusterManager.
 */
public class KaiGMapUtils extends BaseGMapActivity {
    private ClusterManager<GMapItem> mClusterManager;

    // ItemRenderer
    public class ItemRenderer extends DefaultClusterRenderer<GMapItem> {

        public ItemRenderer() {
            super(getApplicationContext(), getMap(), mClusterManager);
        }

        @Override
        protected void onBeforeClusterItemRendered(GMapItem item, MarkerOptions markerOptions) {
//            super.onBeforeClusterItemRendered(item, markerOptions);
            markerOptions.title(String.valueOf(item.getPosition()));
        }
    }

    @Override
    protected void startGMap() {
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0, 0), 10));

        // set manager, renderer
        mClusterManager = new ClusterManager<GMapItem>(this, getMap());
        mClusterManager.setRenderer(new ItemRenderer());

        // set listener
        getMap().setOnCameraChangeListener(mClusterManager);
        getMap().setOnMarkerClickListener(mClusterManager);

//        mClusterManager.setOnClusterItemClickListener(this);

        readItems();

        mClusterManager.cluster();
    }

    private void readItems() {
        ArrayList<GMapItem> items = new ArrayList<GMapItem>();
        for(int i = 0; i < 20; i++) {
            items.add(new GMapItem(0, i));
//            Log.v("kai", String.valueOf(gMapItem.getPosition()));
        }
        mClusterManager.addItems(items);
    }
}