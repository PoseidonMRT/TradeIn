package com.tt.tradein.di.modules;

import com.tt.tradein.di.scope.UserScope;
import com.tt.tradein.mvp.presenter.LoginActivityPresenter;
import com.tt.tradein.mvp.presenter.LoginActivityPresenterImpl;
import com.tt.tradein.mvp.presenter.PublishActivityPresenter;
import com.tt.tradein.mvp.presenter.PublishActivityPresenterImpl;
import com.tt.tradein.mvp.views.LoginActivityView;
import com.tt.tradein.mvp.views.PublishView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tuozhaobing on 16-5-14.
 */
@UserScope
@Module
public class LoginActivityModule {
    private LoginActivityView view;

    public LoginActivityModule(LoginActivityView view) {
        this.view = view;
    }

    @Provides
    public LoginActivityView provideView() {
        return view;
    }

    @Provides
    public LoginActivityPresenter providePresenter(LoginActivityView homeView) {
        return new LoginActivityPresenterImpl(homeView);
    }
}
