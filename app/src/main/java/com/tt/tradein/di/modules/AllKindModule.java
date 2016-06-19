package com.tt.tradein.di.modules;

import com.tt.tradein.di.scope.UserScope;
import com.tt.tradein.mvp.presenter.KindPresenter;
import com.tt.tradein.mvp.presenter.KindPresenterImpl;
import com.tt.tradein.mvp.views.AllKindViews;

import dagger.Module;
import dagger.Provides;

/**
 * Created by TuoZhaoBing on 2016/5/18 0018.
 */
@UserScope
@Module
public class AllKindModule {
    public static final String TAG = "AllKindModule";
    private AllKindViews view;

    public AllKindModule(AllKindViews view) {
        this.view = view;
    }

    @Provides
    public AllKindViews provideView() {
        return view;
    }

    @Provides
    public KindPresenter providePresenter(AllKindViews homeView) {
        return new KindPresenterImpl(homeView);
    }
}
