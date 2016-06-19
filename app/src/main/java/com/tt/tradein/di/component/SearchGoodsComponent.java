package com.tt.tradein.di.component;

import com.tt.tradein.di.modules.SearchGoodsModule;
import com.tt.tradein.mvp.presenter.SearchGoodsPresenter;
import com.tt.tradein.ui.activity.SearchActivity;
import com.tt.tradein.ui.activity.SearchResultActivity;

import dagger.Component;

/**
 * Created by TuoZhaoBing on 2016/5/19 0019.
 */
@Component(dependencies = AppComponent.class,modules = SearchGoodsModule.class)
public interface SearchGoodsComponent {
    public static final String TAG = "SearchGoodsComponent";
    void inject(SearchResultActivity searchResultActivity);
    void inject(SearchActivity searchActivity);
    SearchGoodsPresenter getPresenter();
}
