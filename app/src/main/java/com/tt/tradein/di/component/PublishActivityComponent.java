package com.tt.tradein.di.component;

import com.tt.tradein.di.modules.MainActivityModule;
import com.tt.tradein.di.modules.PublishActivityModule;
import com.tt.tradein.mvp.presenter.HomeViewPresenter;
import com.tt.tradein.mvp.presenter.PublishActivityPresenter;
import com.tt.tradein.ui.activity.PublishGoodsActivity;
import com.tt.tradein.ui.fragment.HomeFragment;

import dagger.Component;

/**
 * Created by tuozhaobing on 16-5-12.
 */
@Component(dependencies = AppComponent.class,modules = PublishActivityModule.class)
public interface PublishActivityComponent {
    void inject(PublishGoodsActivity activity);
    PublishActivityPresenter getMainPresenter();
}
