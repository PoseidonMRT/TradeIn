package com.tt.tradein.di.modules;

import com.tt.tradein.di.scope.UserScope;
import com.tt.tradein.mvp.presenter.PublishActivityPresenter;
import com.tt.tradein.mvp.presenter.PublishActivityPresenterImpl;
import com.tt.tradein.mvp.presenter.RegisterActivityPresenter;
import com.tt.tradein.mvp.presenter.RegisterActivityPresenterImpl;
import com.tt.tradein.mvp.views.PublishView;
import com.tt.tradein.mvp.views.RegisterView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tuozhaobing on 16-5-14.
 */
@UserScope
@Module
public class RegisterActivityModule {
    private RegisterView view;

    public RegisterActivityModule(RegisterView view) {
        this.view = view;
    }

    @Provides
    public RegisterView provideView() {
        return view;
    }

    @Provides
    public RegisterActivityPresenter providePresenter(RegisterView homeView) {
        return new RegisterActivityPresenterImpl(homeView);
    }
}
