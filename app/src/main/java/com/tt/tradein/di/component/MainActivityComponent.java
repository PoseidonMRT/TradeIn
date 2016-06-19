package com.tt.tradein.di.component;

import com.tt.tradein.di.modules.MainActivityModule;
import com.tt.tradein.mvp.presenter.HomeViewPresenter;
import com.tt.tradein.mvp.presenter.KindSortPresenter;
import com.tt.tradein.mvp.presenter.NearByPresenter;
import com.tt.tradein.mvp.presenter.PersonCenterFragmentPresenter;
import com.tt.tradein.ui.fragment.HomeFragment;
import com.tt.tradein.ui.fragment.KindSortFragment;
import com.tt.tradein.ui.fragment.NearByFragment;
import com.tt.tradein.ui.fragment.PersonCenterFragment;

import dagger.Component;

/**
 * Created by TuoZhaoBing on 2016/4/13 0013.
 */
@Component(dependencies = AppComponent.class,modules = MainActivityModule.class)
public interface MainActivityComponent {
    void inject(HomeFragment homeFragment);
    void inject(PersonCenterFragment personCenterFragment);
    void inject(NearByFragment nearByFragment);
    void inject(KindSortFragment kindSortFragment);
    KindSortPresenter getKindSortPresenter();
    HomeViewPresenter getMainPresenter();
    PersonCenterFragmentPresenter getPersonCenterPresenter();
    NearByPresenter getNearByPresenter();
}
