package com.tt.tradein.di.modules;

import com.tt.tradein.di.scope.UserScope;
import com.tt.tradein.mvp.presenter.ForcegetPassPresenterImpl;
import com.tt.tradein.mvp.presenter.ForgetPassPresenter;
import com.tt.tradein.mvp.presenter.LoginActivityPresenter;
import com.tt.tradein.mvp.presenter.LoginActivityPresenterImpl;
import com.tt.tradein.mvp.views.ForgetPassView;
import com.tt.tradein.mvp.views.LoginActivityView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tuozhaobing on 16-5-14.
 */
@UserScope
@Module
public class ForgetActivityModule {
    private ForgetPassView view;

    public ForgetActivityModule(ForgetPassView view) {
        this.view = view;
    }

    @Provides
    public ForgetPassView provideView() {
        return view;
    }

    @Provides
    public ForgetPassPresenter providePresenter(ForgetPassView homeView) {
        return new ForcegetPassPresenterImpl(homeView);
    }
}
