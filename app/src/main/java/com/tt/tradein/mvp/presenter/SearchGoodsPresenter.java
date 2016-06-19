package com.tt.tradein.mvp.presenter;

import android.content.Context;

import com.tt.tradein.mvp.models.Goods;

import java.util.List;

/**
 * Created by TuoZhaoBing on 2016/5/19 0019.
 */
public interface SearchGoodsPresenter {
    public static final String TAG = "SearchGoodsPresenter";
    void queryGoodsBYKind(Context context,String kind);
    void queryGoodsBySecondKind(Context context, String secondkind);
    void queryGoodsByKeyWords(Context context,String keyWords);
    void parseGoodsUser(Context context, final List<Goods> goods);
}
