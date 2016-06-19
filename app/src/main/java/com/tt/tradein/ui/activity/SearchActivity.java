package com.tt.tradein.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

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
import com.tt.tradein.utils.GlobalDefineValues;
import com.tt.tradein.utils.SearchHistoryCacheUtils;
import com.tt.tradein.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Explorer on 2016/5/14.
 */
public class SearchActivity extends BaseActivity {

    private static final int DO_SEARCH = 1;

    @Bind(R.id.et_search_content)
    EditText etSearchContent;
    @Bind(R.id.lv_search_result)
    ListView mListViewResult;
    @Bind(R.id.lv_search_history)
    ListView mListViewHistory;
    @Bind(R.id.ll_search_history)
    LinearLayout llHistory;
    @Bind(R.id.ll_search_result)
    LinearLayout llResult;
    @Bind(R.id.search_clear_content_iv)
    ImageView ivClear;
    @Bind(R.id.search_iv_back)
    ImageView back;
    ArrayAdapter<String> historyAdapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            StartSearch();
        }
    };

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.search_activity);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        etSearchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {//相关课程listview隐藏 搜索历史显示
                    llResult.setVisibility(View.GONE);
                    llHistory.setVisibility(View.VISIBLE);
                } else {//相关课程listview显示 搜索历史隐藏
                    if (llHistory.getVisibility() == View.VISIBLE) {
                        llHistory.setVisibility(View.GONE);
                    }
                    if (ivClear.getVisibility() == View.GONE) {
                        ivClear.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    ivClear.setVisibility(View.GONE);
                }

                mHandler.sendEmptyMessageDelayed(DO_SEARCH, 600);
            }
        });
        initSearchHistory();
    }

    /**
     * 初始化搜索历史的记录显示
     */
    private void initSearchHistory() {
        String cache = SearchHistoryCacheUtils.getCache(SearchActivity.this);
        if (cache != null) {
            List<String> historyRecordList = new ArrayList<>();
            for (String record : cache.split(",")) {
                historyRecordList.add(record);
            }
            historyAdapter = new ArrayAdapter<String>(SearchActivity.this,
                    R.layout.item_search_history, historyRecordList);
            if (historyRecordList.size() > 0) {
                mListViewHistory.setAdapter(historyAdapter);
                mListViewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        etSearchContent.setText("");
                        etSearchContent.setText(historyAdapter.getItem(position));
                    }
                });
            }
        } else {
            llHistory.setVisibility(View.GONE);
        }

    }

    private void save(String text) {
        String oldCache = SearchHistoryCacheUtils.getCache(SearchActivity.this);
        StringBuilder builder = new StringBuilder(text);
        if (oldCache == null) {
            SearchHistoryCacheUtils.setCache(builder.toString(), SearchActivity.this);
            updateData();
        } else {
            builder.append("," + oldCache);
            if (!oldCache.contains(text)) {//避免缓存重复的数据
                SearchHistoryCacheUtils.setCache(builder.toString(), SearchActivity.this);
                updateData();
            }
        }
    }

    /**
     * 更新搜索历史数据显示
     */
    private void updateData() {
        String cache = SearchHistoryCacheUtils.getCache(SearchActivity.this);
        String[] recordData = new String[]{};
        if (cache != null) {
            recordData = cache.split(",");
        }
        historyAdapter = new ArrayAdapter<String>(SearchActivity.this, R.layout.item_search_history, recordData);
        mListViewHistory.setAdapter(historyAdapter);
        mListViewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etSearchContent.setText("");
                etSearchContent.setText(historyAdapter.getItem(position));
            }
        });
        historyAdapter.notifyDataSetChanged();
    }

    /**
     * 开始"搜索"
     */
    public void StartSearch() {
        String text = etSearchContent.getText().toString();
        if (TextUtils.isEmpty(text)) {
            return;
        }
        //缓存搜索历史
        save(text);
        Bundle bundle = new Bundle();
        bundle.putString(GlobalDefineValues.Search_Key_Words, text);
        bundle.putString(GlobalDefineValues.REQUEST_SEARCH_RESULT, GlobalDefineValues.REQUEST_SEARCH_RESULT_KEY_WORDS);
        UIUtils.nextPage(this, SearchResultActivity.class, bundle);
        //网络请求
    }

    @OnClick({R.id.clear_history_btn, R.id.search_clear_content_iv, R.id.search_iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear_history_btn:   //清除搜索历史
                SearchHistoryCacheUtils.ClearCache(SearchActivity.this);
                updateData();
                break;
            case R.id.search_clear_content_iv:   //清空输入框
                etSearchContent.setText("");
                ivClear.setVisibility(View.GONE);
                break;
            case R.id.search_iv_back:
                finish();
                break;
        }
    }
}
