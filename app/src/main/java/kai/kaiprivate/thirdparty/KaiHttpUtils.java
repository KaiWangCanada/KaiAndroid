package kai.kaiprivate.thirdparty;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

import kai.kaiprivate.R;

public class KaiHttpUtils extends ActionBarActivity {

    private final String mUrl = "http://75.98.195.38:125/locations/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_http_utils);

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, mUrl, new
                RequestCallBack<String>() {
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        Log.v("kai", current + "/" + total);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.v("kai", "result: " + responseInfo.result);

                        // Gson
                        Gson gson = new Gson();
                        Restaurants restaurants = gson.fromJson(responseInfo.result, Restaurants.class);
                        Log.v("kai", restaurants.getRestaurant(0).toString());
                    }

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    }
                });
    }

    public class Restaurants {
        @SerializedName("items")
        private List<Restaurant> items;

        public Restaurant getRestaurant(int position) {
            return items.get(position);
        }
    }

    public class Restaurant {
        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        @Override
        public String toString() {
            return id + " - " + name;
        }
    }
}
