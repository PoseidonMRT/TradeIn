package com.tt.tradein.mvp.views;

import com.tt.tradein.mvp.models.Goods;
import com.tt.tradein.mvp.models.User;

import java.util.List;

/**
 * Created by tuozhaobing on 16-5-16.
 */
public interface NearByView {
    void onLoadGoodsInforSuccess(List<Goods> goods);
    void onLoadGoodsError(String str);
    void parseUser(List<User> users);
}
