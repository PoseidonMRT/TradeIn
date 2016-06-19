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
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.tt.tradein.R;
import com.tt.tradein.app.MyApp;
import com.tt.tradein.di.component.AppComponent;
import com.tt.tradein.di.component.DaggerMainActivityComponent;
import com.tt.tradein.di.modules.MainActivityModule;
import com.tt.tradein.mvp.models.Goods;
import com.tt.tradein.mvp.models.User;
import com.tt.tradein.mvp.presenter.NearByPresenter;
import com.tt.tradein.mvp.views.NearByView;
import com.tt.tradein.ui.activity.GoodsDetailActivity;
import com.tt.tradein.ui.activity.SearchActivity;
import com.tt.tradein.ui.adapter.ExpandableListViewAdapter;
import com.tt.tradein.utils.UIUtils;
import com.tt.tradein.widget.CustomExpandableListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by TuoZhaoBing on 2016/4/6 0006.
 */
public class NearByFragment extends Fragment implements NearByView {
    public static final String TAG = "NearByFragment";
    @Bind(R.id.near_by_item_mode)
    ImageView mNearByItemMode;
    @Bind(R.id.near_by_search_imageview)
    ImageView mNearBySearchImageview;
    @Bind(R.id.current_address)
    TextView mCurrentAddress;
    @Bind(R.id.near_by_goods_list)
    CustomExpandableListView mNearByGoodsList;
    private Context mContext;
    private View mRootView = null;
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private String mCurrentPrince = "北京";
    private ExpandableListViewAdapter adapter;
    private List<Goods> goodses;
    @Inject
    NearByPresenter presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.near_by_fragment, container, false);
        }
        ButterKnife.bind(this, mRootView);
        initData();
        return mRootView;
    }

    public void initData() {
        setupComponent(((MyApp) mContext.getApplicationContext()).getAppComponent());
        mNearByGoodsList.setFocusable(false);
        initBaiduLocation();
    }

    protected void setupComponent(AppComponent appComponent) {
        DaggerMainActivityComponent.builder()
                .appComponent(appComponent)
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
    }

    public void initBaiduLocation() {
        // 定位初始化
        mLocClient = new LocationClient(mContext);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan();设置请求定位的间隔时间
        option.setIsNeedAddress(true);  //开启位置信息包括city
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            Log.e("REFRESH", " shuaxinshuju");
            presenter.loadGoodsInfor(mContext, mCurrentPrince);
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onDestroyView() {
        mLocClient.stop();
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.near_by_item_mode, R.id.near_by_search_imageview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.near_by_item_mode:

                break;
            case R.id.near_by_search_imageview:
                UIUtils.nextPage(mContext, SearchActivity.class);
                break;
        }
    }

    @Override
    public void parseUser(final List<User> users) {
        adapter = new ExpandableListViewAdapter(mContext, goodses, users);
        mNearByGoodsList.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            mNearByGoodsList.expandGroup(i);
        }
        mNearByGoodsList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(mContext,""+goodses.get(groupPosition).getPrice(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext,GoodsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Goods",goodses.get(groupPosition));
                bundle.putSerializable("User",users.get(groupPosition));
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            }
        });
        mNearByGoodsList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(mContext,""+goodses.get(groupPosition).getPrice(),Toast.LENGTH_SHORT).show();
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

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            StringBuffer sb = new StringBuffer(256);

            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果

            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            mCurrentAddress.setText(location.getAddrStr());
            mCurrentAddress.postInvalidate();
            mCurrentPrince = location.getCity();
            presenter.loadGoodsInfor(mContext, mCurrentPrince);
        }

        public void onReceivePoi(BDLocation poiLocation) {

        }
    }

    @Override
    public void onDestroy() {
        // 退出时销毁定位
        super.onDestroy();
    }
}
