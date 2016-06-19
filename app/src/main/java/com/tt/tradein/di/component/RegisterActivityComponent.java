package com.tt.tradein.di.component;

import com.tt.tradein.di.modules.PublishActivityModule;
import com.tt.tradein.di.modules.RegisterActivityModule;
import com.tt.tradein.mvp.presenter.PublishActivityPresenter;
import com.tt.tradein.mvp.presenter.RegisterActivityPresenter;
import com.tt.tradein.ui.activity.PublishGoodsActivity;
import com.tt.tradein.ui.activity.RegisterActivity;

import dagger.Component;

/**
 * Created by tuozhaobing on 16-5-14.
 */
@Component(dependencies = AppComponent.class,modules = RegisterActivityModule.class)
public interface RegisterActivityComponent {
    void inject(RegisterActivity activity);
    RegisterActivityPresenter getRegisterPresenter();
}
