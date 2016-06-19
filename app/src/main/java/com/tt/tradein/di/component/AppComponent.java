package com.tt.tradein.di.component;

import com.tt.tradein.app.MyApp;
import com.tt.tradein.di.modules.AppModule;
import com.tt.tradein.di.scope.UserScope;


import dagger.Component;

/**
 * Created by TuoZhaoBing on 2016/4/13 0013.
 */
@Component(
        modules = {
                AppModule.class,
        }
)
public interface AppComponent {
    MyApp inject(MyApp rxRetrofitApplication);
}

