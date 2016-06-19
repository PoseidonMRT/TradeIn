package com.tt.tradein.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tt.tradein.R;
import com.tt.tradein.app.MyApp;
import com.tt.tradein.di.component.AppComponent;
import com.tt.tradein.di.component.DaggerSearchGoodsComponent;
import com.tt.tradein.di.modules.SearchGoodsModule;
import com.tt.tradein.mvp.models.Goods;
import com.tt.tradein.mvp.models.User;
import com.tt.tradein.mvp.presenter.SearchGoodsPresenter;
import com.tt.tradein.mvp.views.SearchGoodsView;
import com.tt.tradein.ui.activity.base.BaseActivity;
import com.tt.tradein.ui.adapter.ExpandableListViewAdapter;
import com.tt.tradein.utils.GlobalDefineValues;
import com.tt.tradein.widget.CustomExpandableListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by TuoZhaoBing on 2016/5/19 0019.
 */
public class SearchResultActivity extends BaseActivity implements SearchGoodsView {
    public static final String TAG = "SearchResultActivity";
    @Inject
    SearchGoodsPresenter presenter;
    @Bind(R.id.activity_search_result_back)
    ImageView mActivitySearchResultBack;
    @Bind(R.id.activity_search_result_top)
    TextView activitySearchResultTop;
    @Bind(R.id.activity_search_result_goods_list)
    CustomExpandableListView listView;
    private String key;
    private String type;
    private ExpandableListViewAdapter adapter;
    private List<Goods> goodses;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        setupComponent(((MyApp) getApplicationContext()).getAppComponent());
    }

    @Override
    public void initData() {
        key = getIntent().getStringExtra(GlobalDefineValues.Search_Key_Words);
        type = getIntent().getStringExtra(GlobalDefineValues.REQUEST_SEARCH_RESULT);
        activitySearchResultTop.setText(key);
        if (type.equals(GlobalDefineValues.REQUEST_SEARCH_RESULT_KEY_WORDS)) {
            presenter.queryGoodsByKeyWords(this,key);
        } else if (type.equals(GlobalDefineValues.REQUEST_SEARCH_RESULT_SECOND_KIND)) {
            presenter.queryGoodsBySecondKind(this,key);
        }else if (type.equals(GlobalDefineValues.REQUEST_SEARCH_RESULT_KIND)){
            presenter.queryGoodsBYKind(this,key);
        }
    }

    @Override
    public void showKeyWordsResult(List<Goods> goodsList) {
        this.goodses = goodsList;
        presenter.parseGoodsUser(this, goodses);
    }

    @Override
    public void onLoadGoodsError(String str) {

    }

    @Override
    public void parseUser(List<User> users) {
        adapter = new ExpandableListViewAdapter(this, goodses, users);
        listView.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            listView.expandGroup(i);
        }
    }

    @Override
    public void showSecondKindResult(List<Goods> goodses) {
        this.goodses = goodses;
        presenter.parseGoodsUser(this, goodses);
    }

    protected void setupComponent(AppComponent appComponent) {
        DaggerSearchGoodsComponent
                .builder()
                .appComponent(appComponent)
                .searchGoodsModule(new SearchGoodsModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.activity_search_result_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_search_result_back:
                finish();
                break;
        }
    }
}
