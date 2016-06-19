package com.tt.tradein.app;

import android.app.Application;
import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.tt.tradein.di.component.AppComponent;
import com.tt.tradein.di.component.DaggerAppComponent;
import com.tt.tradein.di.modules.AppModule;
import com.tt.tradein.ui.fragment.NearByFragment;
import com.tt.tradein.utils.GlobalDefineValues;
import com.umeng.socialize.PlatformConfig;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;

/**
 * Created by TuoZhaoBing on 2016/4/13 0013.
 */
public class MyApp extends Application {
    public static final String TAG = "MyApp";
    private AppComponent mAppComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        initBmob();
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        mAppComponent.inject(this);
        initBaiDuMap();
        initShare();
    }

    public void initShare(){
        PlatformConfig.setSinaWeibo("3400588978","c685e5d1ba7e8b6dddf82b9e5aea0a44");
        //新浪微博 appkey appsecret
        PlatformConfig.setWeixin("wx5a714face5a6640d", "03ac8c788f750d68a73c4742da77bb23");
        //微信 appid appsecret
    }

    public void initBmob(){
        Bmob.initialize(this, GlobalDefineValues.BmobApplicationID);
    }

    public AppComponent getAppComponent(){
        return mAppComponent;
    }

    public static MyApp getApplication(Context context) {
        return (MyApp) context.getApplicationContext();
    }

    public void initBaiDuMap(){
        SDKInitializer.initialize(getApplicationContext());
    }
}
