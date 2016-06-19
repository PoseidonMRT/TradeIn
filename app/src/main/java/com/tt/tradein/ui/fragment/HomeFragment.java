package com.tt.tradein.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.tt.tradein.R;
import com.tt.tradein.app.MyApp;
import com.tt.tradein.di.component.AppComponent;
import com.tt.tradein.di.component.DaggerMainActivityComponent;
import com.tt.tradein.di.modules.MainActivityModule;
import com.tt.tradein.domain.service.WeatherApiService;
import com.tt.tradein.mvp.models.Goods;
import com.tt.tradein.mvp.models.User;
import com.tt.tradein.mvp.models.WeatherResultBean;
import com.tt.tradein.mvp.presenter.HomeViewPresenter;
import com.tt.tradein.mvp.views.HomeView;
import com.tt.tradein.ui.activity.GoodsDetailActivity;
import com.tt.tradein.ui.activity.KindActivity;
import com.tt.tradein.ui.activity.SearchActivity;
import com.tt.tradein.ui.adapter.ExpandableListViewAdapter;
import com.tt.tradein.ui.adapter.HomeHorizentalListViewAdapter;
import com.tt.tradein.utils.GlobalDefineValues;
import com.tt.tradein.utils.UIUtils;
import com.tt.tradein.widget.CustomExpandableListView;
import com.tt.tradein.widget.HorizontalListView;
import com.tt.tradein.widget.ImageFlipper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by TuoZhaoBing on 2016/4/6 0006.
 */
public class HomeFragment extends Fragment implements HomeView {
    public static final String TAG = "HomeFragment";
    @Bind(R.id.imageFliper)
    ImageFlipper mImageFliper;
    @Bind(R.id.dot_one)
    View mDotOne;
    @Bind(R.id.dot_two)
    View mDotTwo;
    @Bind(R.id.dot_three)
    View mDotThree;
    @Bind(R.id.dot_four)
    View mDotFour;
//    @Bind(R.id.home_horizontal_list_view)
//    HorizontalListView mHomeHorizontalListView;
    @Bind(R.id.icon_home_item)
    ImageView mIconHomeItem;
    @Bind(R.id.icon_search_imageview)
    ImageView mIconSearchImageview;
    @Bind(R.id.home_goods_list)
    CustomExpandableListView mHomeGoodsList;
    @Bind(R.id.dots_ll)
    LinearLayout dotsLl;
    @Bind(R.id.home_scroll_view)
    ScrollView mHomeScrollView;
    private Context mContext;
    private View mRootView;
    private List<ImageView> imageViews;
    private ExpandableListViewAdapter adapter;
    private List<Goods> goodses;

    @Bind(R.id.text3)
    TextView mGetWeatherTextView;
    @Inject
    HomeViewPresenter presenter;
    @Inject
    WeatherApiService weatherApiService;
    private Boolean isFirstCreated = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.home_fragment, container, false);
            isFirstCreated = false;
        }
        ButterKnife.bind(this, mRootView);
        setupComponent(((MyApp) mContext.getApplicationContext()).getAppComponent());
        initData();
        return mRootView;
    }

    public void initData() {
//        mHomeGoodsList.setFocusable(false);
        imageViews = new ArrayList<>();
//        presenter.loadHorizentalListViewData(mContext);
        presenter.loadBannerData(mContext, imageViews);
        presenter.loadGoodsInfor(mContext);
    }

    public void initDots(int i) {
        switch (i) {
            case 0:
                mDotOne.setBackgroundResource(R.drawable.indicator_shape_selected);
                mDotTwo.setBackgroundResource(R.drawable.indictor_shape_normal);
                mDotThree.setBackgroundResource(R.drawable.indictor_shape_normal);
                mDotFour.setBackgroundResource(R.drawable.indictor_shape_normal);
                break;
            case 1:
                mDotOne.setBackgroundResource(R.drawable.indictor_shape_normal);
                mDotTwo.setBackgroundResource(R.drawable.indicator_shape_selected);
                mDotThree.setBackgroundResource(R.drawable.indictor_shape_normal);
                mDotFour.setBackgroundResource(R.drawable.indictor_shape_normal);
                break;
            case 2:
                mDotOne.setBackgroundResource(R.drawable.indictor_shape_normal);
                mDotTwo.setBackgroundResource(R.drawable.indictor_shape_normal);
                mDotThree.setBackgroundResource(R.drawable.indicator_shape_selected);
                mDotFour.setBackgroundResource(R.drawable.indictor_shape_normal);
                break;
            case 3:
                mDotOne.setBackgroundResource(R.drawable.indictor_shape_normal);
                mDotTwo.setBackgroundResource(R.drawable.indictor_shape_normal);
                mDotThree.setBackgroundResource(R.drawable.indictor_shape_normal);
                mDotFour.setBackgroundResource(R.drawable.indicator_shape_selected);
                break;
            default:
                mDotOne.setBackgroundResource(R.drawable.indicator_shape_selected);
                mDotTwo.setBackgroundResource(R.drawable.indictor_shape_normal);
                mDotThree.setBackgroundResource(R.drawable.indictor_shape_normal);
                mDotFour.setBackgroundResource(R.drawable.indictor_shape_normal);
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && !isFirstCreated){
            presenter.loadGoodsInfor(mContext);
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected void setupComponent(AppComponent appComponent) {
        DaggerMainActivityComponent.builder()
                .appComponent(appComponent)
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.text3, R.id.icon_home_item, R.id.icon_search_imageview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text3:
                presenter.load(GlobalDefineValues.baiduKey, "北京");
                break;
            case R.id.icon_home_item:
                UIUtils.nextPage(mContext, KindActivity.class);
                break;
            case R.id.icon_search_imageview:
                UIUtils.nextPage(mContext, SearchActivity.class);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showBannerData(List<ImageView> images) {
        for (int i = 0; i < images.size(); i++) {
            mImageFliper.addView(images.get(i));
        }
        mImageFliper.setOnPageChangeListener(new ImageFlipper.OnPageChangeListener() {
            @Override
            public void onPageSelected(int index) {
                initDots(index);
            }
        });
    }

    @Override
    public void showweatherInfor(WeatherResultBean weatherResultBean) {
        mGetWeatherTextView.setText(weatherResultBean.getRetData().getCity() + "" + weatherResultBean.getRetData().getH_tmp());
        mGetWeatherTextView.invalidate();
    }

    @Override
    public void errorLoad(Throwable t) {

    }

    @Override
    public void setHirizentalListViewData(HomeHorizentalListViewAdapter adapter) {
//        mHomeHorizontalListView.setAdapter(adapter);
    }

    @Override
    public void parseUser(final List<User> users) {
        Log.e("User", users.size() + "");
        Log.e("Goods", goodses.size() + "");
        adapter = new ExpandableListViewAdapter(mContext, goodses, users);
        mHomeGoodsList.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            mHomeGoodsList.expandGroup(i);
        }
        mHomeGoodsList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                Toast.makeText(mContext,""+goodses.get(groupPosition).getPrice(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext,GoodsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Goods",goodses.get(groupPosition));
                bundle.putSerializable("User",users.get(groupPosition));
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            }
        });
        mHomeGoodsList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                Toast.makeText(mContext,""+goodses.get(groupPosition).getPrice(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext,GoodsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Goods",goodses.get(groupPosition));
                bundle.putSerializable("User",users.get(groupPosition));
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public void onLoadGoodsInforSuccess(List<Goods> goods) {
        this.goodses = goods;
        presenter.parseGoodsUser(mContext, goods);
    }

    @Override
    public void onLoadGoodsError(String str) {

    }

}
