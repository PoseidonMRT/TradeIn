package com.tt.tradein.di.component;

import com.tt.tradein.di.modules.LoginActivityModule;
import com.tt.tradein.di.modules.PublishActivityModule;
import com.tt.tradein.mvp.presenter.LoginActivityPresenter;
import com.tt.tradein.mvp.presenter.PublishActivityPresenter;
import com.tt.tradein.ui.activity.LoginActivity;
import com.tt.tradein.ui.activity.PublishGoodsActivity;

import dagger.Component;

/**
 * Created by tuozhaobing on 16-5-14.
 */
@Component(dependencies = AppComponent.class,modules = LoginActivityModule.class)
public interface LoginActivityComponent {
    void inject(LoginActivity activity);
    LoginActivityPresenter getLoginPresenter();
}