package com.tt.tradein.mvp.presenter;


import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tt.tradein.R;
import com.tt.tradein.domain.service.WeatherApiService;
import com.tt.tradein.mvp.models.Banner;
import com.tt.tradein.mvp.models.Goods;
import com.tt.tradein.mvp.models.QueryGood;
import com.tt.tradein.mvp.models.User;
import com.tt.tradein.mvp.models.WeatherResultBean;
import com.tt.tradein.mvp.views.HomeView;
import com.tt.tradein.ui.adapter.HomeHorizentalListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by TuoZhaoBing on 2016/4/13 0013.
 */
public class HomeViewPresenterImpl implements HomeViewPresenter {
    public static final String TAG = "HomeViewPresenterImpl";
    private HomeView homeView;
    WeatherApiService weatherApiService;
    private ImageView imageView;
    private HomeHorizentalListViewAdapter homeHorizentalListViewAdapter;
    int[] titles = {R.string.str_latest_post,R.string.str_book , R.string.str_school_message, R.string.str_near_by, R.string.str_house, R.string.str_part_time};
    final int[] ids = {R.mipmap.icon_time, R.mipmap.icon_book, R.mipmap.icon_send_message, R.mipmap.icon_location, R.mipmap.icon_house, R.mipmap.icon_house};

    @Override
    public void parseGoodsUser(Context context, final List<Goods> goods) {
        final List<User> users = new ArrayList<>();
        for (int i=0;i<goods.size();i++){
            BmobQuery<User> query = new BmobQuery<User>();
            query.addWhereEndsWith("objectId",goods.get(i).getUserid());
            query.findObjects(context, new FindListener<User>() {
                @Override
                public void onSuccess(List<User> list) {
                    users.addAll(list);
                    if (users.size() == goods.size()){
                        homeView.parseUser(users);
                    }
                }

                @Override
                public void onError(int i, String s) {

                }
            });
        }
    }

    @Override
    public void loadGoodsInfor(Context context) {
        BmobQuery<Goods> query = new BmobQuery<Goods>();
        query.order("-createdAt");
        query.findObjects(context, new FindListener<Goods>() {
            @Override
            public void onSuccess(List<Goods> list) {
                homeView.onLoadGoodsInforSuccess(list);
            }

            @Override
            public void onError(int i, String s) {
                homeView.onLoadGoodsError(s);
            }
        });
    }

    @Override
    public void loadBannerData(final Context context,final List<ImageView> imageViews) {
        BmobQuery<Banner> query = new BmobQuery<Banner>();
        query.findObjects(context, new FindListener<Banner>() {
            @Override
            public void onSuccess(List<Banner> list) {
                for (int i=0;i<list.size();i++){
                    imageView = new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(context)
                            .load(list.get(i).getFile().getUrl())
                            .into(imageView);
                    imageViews.add(imageView);
                }
                homeView.showBannerData(imageViews);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void loadHorizentalListViewData(Context context) {
        homeHorizentalListViewAdapter = new HomeHorizentalListViewAdapter(context,titles,ids);
        homeView.setHirizentalListViewData(homeHorizentalListViewAdapter);
    }

    @Override
    public void load(String apikey, String strname) {
        Observable<WeatherResultBean> observable = weatherApiService.queryWeather(apikey,strname);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherResultBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        homeView.errorLoad(e);
                    }

                    @Override
                    public void onNext(WeatherResultBean daily) {
                        homeView.showweatherInfor(daily);
                    }
                });
    }

    public HomeViewPresenterImpl(HomeView homeView, WeatherApiService weatherApiService) {
        this.homeView = homeView;
        this.weatherApiService = weatherApiService;
    }
}
