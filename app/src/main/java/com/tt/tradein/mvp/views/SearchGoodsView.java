package com.tt.tradein.mvp.views;

import com.tt.tradein.mvp.models.Goods;
import com.tt.tradein.mvp.models.User;

import java.util.List;

/**
 * Created by TuoZhaoBing on 2016/5/19 0019.
 */
public interface SearchGoodsView {
    public static final String TAG = "SearchGoodsView";
    void showKeyWordsResult(List<Goods> goodsList);
    void showSecondKindResult(List<Goods> goodses);
    void parseUser(List<User> users);
    void onLoadGoodsError(String str);
}
