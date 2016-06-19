package com.tt.tradein.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tt.tradein.R;
import com.tt.tradein.app.MyApp;
import com.tt.tradein.di.component.AppComponent;
import com.tt.tradein.di.component.DaggerMainActivityComponent;
import com.tt.tradein.di.modules.MainActivityModule;
import com.tt.tradein.mvp.models.KindSort;
import com.tt.tradein.mvp.presenter.KindSortPresenter;
import com.tt.tradein.mvp.views.KindSortView;
import com.tt.tradein.ui.activity.KindActivity;
import com.tt.tradein.ui.activity.MainActivity;
import com.tt.tradein.ui.activity.SearchResultActivity;
import com.tt.tradein.ui.adapter.HomeAdapter;
import com.tt.tradein.utils.GlobalDefineValues;
import com.tt.tradein.utils.UIUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by TuoZhaoBing on 2016/4/6 0006.
 */
public class KindSortFragment extends Fragment implements KindSortView{

    public static final String TAG = "KindSortFragment";
    @Bind(R.id.kind_sort_list)
    RecyclerView kindSortList;
    @Bind(R.id.phone_ll)
    LinearLayout mPhoneLl;
    @Bind(R.id.tablet_ll)
    LinearLayout mTabletLl;
    @Bind(R.id.computer_ll)
    LinearLayout mComputerLl;
    @Bind(R.id.camera_ll)
    LinearLayout mCameraLl;
    @Bind(R.id.bicycle_ll)
    LinearLayout mBicycleLl;
    @Bind(R.id.music_ll)
    LinearLayout mMusicLl;
    @Bind(R.id.book_ll)
    LinearLayout mBookLl;
    @Bind(R.id.stationary_ll)
    LinearLayout mStationaryLl;
    @Bind(R.id.see_all_kind)
    TextView mSeeAllKind;
    private Context mContext;
    private View mRootView = null;
    @Inject
    KindSortPresenter kindSortPresenter;
    private HomeAdapter myAdspter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.kind_sort_fragment, container, false);
        }
        ButterKnife.bind(this, mRootView);
        setupComponent(((MyApp) mContext.getApplicationContext()).getAppComponent());
        initData();
        return mRootView;
    }

    public void initData() {
        final Toolbar toolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        ((MainActivity) mContext).setSupportActionBar(toolbar);
        ((MainActivity) mContext).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setTitle("");
        CollapsingToolbarLayout toolbar_layout = (CollapsingToolbarLayout) mRootView.findViewById(R.id.toolbar_layout);
        toolbar_layout.setTitleEnabled(false);
        toolbar_layout.setTitle("搜索");
        kindSortPresenter.loadKindSortData(mContext);
    }

    protected void setupComponent(AppComponent appComponent) {
        DaggerMainActivityComponent.builder()
                .appComponent(appComponent)
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.phone_ll, R.id.tablet_ll, R.id.computer_ll, R.id.camera_ll, R.id.bicycle_ll, R.id.music_ll, R.id.book_ll, R.id.stationary_ll, R.id.see_all_kind})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.phone_ll:
                bundle.putString(GlobalDefineValues.REQUEST_SEARCH_RESULT,GlobalDefineValues.REQUEST_SEARCH_RESULT_KIND);
                bundle.putString(GlobalDefineValues.Search_Key_Words,"手机");
                UIUtils.nextPage(mContext, SearchResultActivity.class,bundle);
                break;
            case R.id.tablet_ll:
                bundle.putString(GlobalDefineValues.REQUEST_SEARCH_RESULT,GlobalDefineValues.REQUEST_SEARCH_RESULT_KIND);
                bundle.putString(GlobalDefineValues.Search_Key_Words,"平板");
                UIUtils.nextPage(mContext, SearchResultActivity.class,bundle);
                break;
            case R.id.computer_ll:
                bundle.putString(GlobalDefineValues.REQUEST_SEARCH_RESULT,GlobalDefineValues.REQUEST_SEARCH_RESULT_KIND);
                bundle.putString(GlobalDefineValues.Search_Key_Words,"电脑");
                UIUtils.nextPage(mContext, SearchResultActivity.class,bundle);
                break;
            case R.id.camera_ll:
                bundle.putString(GlobalDefineValues.REQUEST_SEARCH_RESULT,GlobalDefineValues.REQUEST_SEARCH_RESULT_KIND);
                bundle.putString(GlobalDefineValues.Search_Key_Words,"数码");
                UIUtils.nextPage(mContext, SearchResultActivity.class,bundle);
                break;
            case R.id.bicycle_ll:
                bundle.putString(GlobalDefineValues.REQUEST_SEARCH_RESULT,GlobalDefineValues.REQUEST_SEARCH_RESULT_KIND);
                bundle.putString(GlobalDefineValues.Search_Key_Words,"代步");
                UIUtils.nextPage(mContext, SearchResultActivity.class,bundle);
                break;
            case R.id.music_ll:
                bundle.putString(GlobalDefineValues.REQUEST_SEARCH_RESULT,GlobalDefineValues.REQUEST_SEARCH_RESULT_KIND);
                bundle.putString(GlobalDefineValues.Search_Key_Words,"玩具");
                UIUtils.nextPage(mContext, SearchResultActivity.class,bundle);
                break;
            case R.id.book_ll:
                bundle.putString(GlobalDefineValues.REQUEST_SEARCH_RESULT,GlobalDefineValues.REQUEST_SEARCH_RESULT_KIND);
                bundle.putString(GlobalDefineValues.Search_Key_Words,"书籍");
                UIUtils.nextPage(mContext, SearchResultActivity.class,bundle);
                break;
            case R.id.stationary_ll:
                bundle.putString(GlobalDefineValues.REQUEST_SEARCH_RESULT,GlobalDefineValues.REQUEST_SEARCH_RESULT_KIND);
                bundle.putString(GlobalDefineValues.Search_Key_Words,"办公");
                UIUtils.nextPage(mContext, SearchResultActivity.class,bundle);
                break;
            case R.id.see_all_kind:
                UIUtils.nextPage(mContext, KindActivity.class);
                break;
        }
    }

    @Override
    public void presenKindSorts(List<KindSort> kindSorts) {
        myAdspter = new HomeAdapter(mContext,kindSorts);
        kindSortList.setLayoutManager(new LinearLayoutManager(mContext));
        kindSortList.setAdapter(myAdspter);
        myAdspter.setHomeAdapterOnClickListener(new HomeAdapter.HomeAdapterOnClickListener() {
            @Override
            public void onClick(int item, int pos) {
                Bundle bundle = new Bundle();
                switch (item){
                    case 0:
                        if (pos == 1){
                            bundle.putString(GlobalDefineValues.REQUEST_SEARCH_RESULT,GlobalDefineValues.REQUEST_SEARCH_RESULT_SECOND_KIND);
                            bundle.putString(GlobalDefineValues.Search_Key_Words,"小米");
                            UIUtils.nextPage(mContext, SearchResultActivity.class,bundle);
                        }else if (pos==2){
                            bundle.putString(GlobalDefineValues.REQUEST_SEARCH_RESULT,GlobalDefineValues.REQUEST_SEARCH_RESULT_SECOND_KIND);
                            bundle.putString(GlobalDefineValues.Search_Key_Words,"华为");
                            UIUtils.nextPage(mContext, SearchResultActivity.class,bundle);
                        }else if (pos == 3){
                            bundle.putString(GlobalDefineValues.REQUEST_SEARCH_RESULT,GlobalDefineValues.REQUEST_SEARCH_RESULT_SECOND_KIND);
                            bundle.putString(GlobalDefineValues.Search_Key_Words,"IPhone");
                            UIUtils.nextPage(mContext, SearchResultActivity.class,bundle);
                        }
                        break;
                    case 1:
                        if (pos == 1){
                            bundle.putString(GlobalDefineValues.REQUEST_SEARCH_RESULT,GlobalDefineValues.REQUEST_SEARCH_RESULT_SECOND_KIND);
                            bundle.putString(GlobalDefineValues.Search_Key_Words,"华硕");
                            UIUtils.nextPage(mContext, SearchResultActivity.class,bundle);
                        }else if (pos==2){
                            bundle.putString(GlobalDefineValues.REQUEST_SEARCH_RESULT,GlobalDefineValues.REQUEST_SEARCH_RESULT_SECOND_KIND);
                            bundle.putString(GlobalDefineValues.Search_Key_Words,"联想");
                            UIUtils.nextPage(mContext, SearchResultActivity.class,bundle);
                        }else if (pos == 3){
                            bundle.putString(GlobalDefineValues.REQUEST_SEARCH_RESULT,GlobalDefineValues.REQUEST_SEARCH_RESULT_SECOND_KIND);
                            bundle.putString(GlobalDefineValues.Search_Key_Words,"三星");
                            UIUtils.nextPage(mContext, SearchResultActivity.class,bundle);
                        }
                        break;
                    case 2:
                        if (pos == 1){

                        }else if (pos==2){

                        }else if (pos == 3){

                        }
                        break;
                }
            }
        });
    }

    @Override
    public void presenterKindsError(String string) {

    }
}
