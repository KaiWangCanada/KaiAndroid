package kai.kaiprivate.thirdparty.gmaputils;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

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

    @Override
    protected void startGMap() {
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0, 0), 10));

        mClusterManager = new ClusterManager<GMapItem>(this, getMap());
        getMap().setOnCameraChangeListener(mClusterManager);

        try {
            readItems();
        } catch (JSONException e) {
            Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }
    }

    private void readItems() throws JSONException {
        ArrayList<GMapItem> items = new ArrayList<GMapItem>();
        for(int i = 0; i < 20; i++) {
            items.add(new GMapItem(0, i));
//            Log.v("kai", String.valueOf(gMapItem.getPosition()));
        }
        mClusterManager.addItems(items);
    }
}