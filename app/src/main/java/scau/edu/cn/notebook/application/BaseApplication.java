package scau.edu.cn.notebook.application;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;

/**
 * 作用：全局Application
 */
public class BaseApplication extends Application {
    public static Context getmContext() {
        return sContext;
    }

    static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
