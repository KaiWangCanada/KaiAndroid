package kai.kaiprivate.thirdparty.gmaputils;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class GMapItem implements ClusterItem {
    private final LatLng mPosition;

    public GMapItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}

