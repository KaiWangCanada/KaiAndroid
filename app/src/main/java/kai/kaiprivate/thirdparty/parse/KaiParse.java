package kai.kaiprivate.thirdparty.parse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;

import kai.kaiprivate.R;

public class KaiParse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_parse);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "qvlln3WyVJF51AdojGjBXMJQRIP3BpPNMHmDjgQN",
                "hTgfF6mKPTuSGzMQ5HwiPfEyDss7EJxis2HaUY2C");

        // test
//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();

        // get url
        ParseQuery<ParseObject> query = ParseQuery.getQuery("featuredSalon");
        query.getInBackground("XIYWGmpx5t", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, com.parse.ParseException e) {
                if (e == null) {
                    // object will be your game score
                    String _url = parseObject.getString("url");
                    Log.v("kai", _url);
                    String[] _separated = _url.split(";");
                    for(int i = 0; i < _separated.length; i++) {
                        Log.v("kai", _separated[i]);
                    }

                } else {
                    // something went wrong
                }
            }

        });

    }

}
