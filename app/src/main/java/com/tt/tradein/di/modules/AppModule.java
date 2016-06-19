package com.tt.tradein.di.modules;

import android.content.Context;

import com.tt.tradein.app.MyApp;
import com.tt.tradein.domain.RestApiAdapter;
import com.tt.tradein.domain.service.WeatherApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by TuoZhaoBing on 2016/4/13 0013.
 */
@Module
public class AppModule {
    public static final String TAG = "AppModule";
    private final MyApp application;

    public AppModule(MyApp application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application;
    }
}
