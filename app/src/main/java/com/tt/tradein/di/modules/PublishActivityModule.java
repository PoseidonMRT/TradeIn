package com.tt.tradein.di.modules;

import com.tt.tradein.di.scope.UserScope;
import com.tt.tradein.domain.service.WeatherApiService;
import com.tt.tradein.mvp.presenter.HomeViewPresenter;
import com.tt.tradein.mvp.presenter.HomeViewPresenterImpl;
import com.tt.tradein.mvp.presenter.PublishActivityPresenter;
import com.tt.tradein.mvp.presenter.PublishActivityPresenterImpl;
import com.tt.tradein.mvp.views.HomeView;
import com.tt.tradein.mvp.views.PublishView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tuozhaobing on 16-5-12.
 */
@UserScope
@Module
public class PublishActivityModule {
    private PublishView view;

    public PublishActivityModule(PublishView view) {
        this.view = view;
    }

    @Provides
    public PublishView provideView() {
        return view;
    }

    @Provides
    public PublishActivityPresenter providePresenter(PublishView homeView) {
        return new PublishActivityPresenterImpl(homeView);
    }

}
