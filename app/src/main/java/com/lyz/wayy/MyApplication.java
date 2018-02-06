package com.lyz.wayy;

import android.app.Application;

/**
 * For developer startup JPush SDK
 *
 * 一般建议在自定义 Application 类里初始化。也可以在主 Activity 里。
 */
public class MyApplication extends Application {
    private static final String TAG = "JIGUANG-Example";

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
