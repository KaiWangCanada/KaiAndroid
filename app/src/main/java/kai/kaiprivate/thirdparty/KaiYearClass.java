package kai.kaiprivate.thirdparty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.device.yearclass.YearClass;

import kai.kaiprivate.R;

public class KaiYearClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int year = YearClass.get(getApplicationContext());
        Log.v("kai", String.valueOf(year));
    }

}
