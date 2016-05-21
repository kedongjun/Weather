package cn.h2o2.weather;

import android.app.Application;

/**
 * Created by kent on 16/5/20.
 */
public class WApplication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }
}
