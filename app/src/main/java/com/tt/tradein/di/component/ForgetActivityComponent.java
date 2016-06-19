package com.tt.tradein.di.component;

import com.sina.weibo.sdk.api.VoiceObject;
import com.tt.tradein.di.modules.ForgetActivityModule;
import com.tt.tradein.di.modules.LoginActivityModule;
import com.tt.tradein.mvp.presenter.ForgetPassPresenter;
import com.tt.tradein.ui.activity.ForgetPassActivity;

import dagger.Component;

/**
 * Created by tuozhaobing on 16-5-14.
 */
@Component(dependencies = AppComponent.class,modules = ForgetActivityModule.class)
public interface ForgetActivityComponent {
    void inject(ForgetPassActivity activity);
    ForgetPassPresenter getForgetPassPresenter();
}
