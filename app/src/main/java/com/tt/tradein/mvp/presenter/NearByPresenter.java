package com.tt.tradein.mvp.presenter;

import android.content.Context;

import com.tt.tradein.mvp.models.Goods;

import java.util.List;

/**
 * Created by tuozhaobing on 16-5-16.
 */
public interface NearByPresenter {
    void loadGoodsInfor(Context context,String city);
    void parseGoodsUser(Context context,List<Goods> goods);
}
