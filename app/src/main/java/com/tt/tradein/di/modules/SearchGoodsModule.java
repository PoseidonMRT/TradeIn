package com.tt.tradein.di.modules;

import com.tt.tradein.di.scope.UserScope;
import com.tt.tradein.mvp.presenter.SearchGoodsPresenter;
import com.tt.tradein.mvp.presenter.SearchGoodsPresenterImpl;
import com.tt.tradein.mvp.views.SearchGoodsView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by TuoZhaoBing on 2016/5/19 0019.
 */
@UserScope
@Module
public class SearchGoodsModule {
    public static final String TAG = "SearchGoodsModule";
    private SearchGoodsView searchGoodsView;
    public SearchGoodsModule(SearchGoodsView searchGoodsView){
        this.searchGoodsView = searchGoodsView;
    }

    @Provides
    public SearchGoodsView provideView(){
        return searchGoodsView;
    }

    @Provides
    public SearchGoodsPresenter providePresenter(SearchGoodsView searchGoodsView){
        return new SearchGoodsPresenterImpl(searchGoodsView);
    }
}
