package com.tt.tradein.mvp.views;

import android.widget.ImageView;

import com.tt.tradein.mvp.models.Goods;
import com.tt.tradein.mvp.models.User;
import com.tt.tradein.mvp.models.WeatherResultBean;
import com.tt.tradein.ui.adapter.HomeHorizentalListViewAdapter;

import java.util.List;

/**
 * Created by TuoZhaoBing on 2016/4/13 0013.
 */
public interface HomeView {
    void showweatherInfor(WeatherResultBean weatherResultBean);
    void errorLoad(Throwable t);
    void setHirizentalListViewData(HomeHorizentalListViewAdapter adapter);
    void showBannerData(List<ImageView> images);
    void onLoadGoodsInforSuccess(List<Goods> goods);
    void onLoadGoodsError(String str);
    void parseUser(List<User> users);
}
