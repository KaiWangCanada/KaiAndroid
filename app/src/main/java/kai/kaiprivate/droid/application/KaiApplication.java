package kai.kaiprivate.droid.application;

import android.app.Application;

/**
 * Created by Administrator on 2015/6/16.
 */
public class KaiApplication extends Application{

    public String mStr;

    @Override
    public void onCreate() {
        super.onCreate();
        mStr = "application ok";
    }
}
