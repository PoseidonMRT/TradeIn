package com.tt.tradein.di.component;

import com.tt.tradein.di.modules.AllKindModule;
import com.tt.tradein.mvp.presenter.KindPresenter;
import com.tt.tradein.ui.activity.AllKindActivity;
import com.tt.tradein.ui.activity.KindActivity;
import com.tt.tradein.ui.activity.SecondKindActivity;

import dagger.Component;

/**
 * Created by TuoZhaoBing on 2016/5/18 0018.
 */
@Component(dependencies = AppComponent.class,modules = AllKindModule.class)
public interface AllKindComponent {
    public static final String TAG = "AllKindComponent";
    void inject(AllKindActivity allKindActivity);
    void inject(KindActivity kindActivity);
    void inject(SecondKindActivity secondKindActivity);
    KindPresenter getKindPresenter();
}
