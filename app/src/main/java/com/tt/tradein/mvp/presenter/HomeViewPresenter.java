package com.tt.tradein.mvp.presenter;

import android.content.Context;
import android.widget.ImageView;

import com.tt.tradein.mvp.models.Goods;

import java.util.List;

/**
 * Created by TuoZhaoBing on 2016/4/13 0013.
 */
public interface HomeViewPresenter {
    public static final String TAG = "HomeViewPresenter";
    public void load(String apikey,String cityname);
    public void loadHorizentalListViewData(Context context);
    public void loadBannerData(Context context, List<ImageView> imageViews);
    void loadGoodsInfor(Context context);
    void parseGoodsUser(Context context,List<Goods> goods);
}
