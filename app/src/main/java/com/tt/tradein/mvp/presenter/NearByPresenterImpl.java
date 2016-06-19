package com.tt.tradein.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.tt.tradein.mvp.models.Goods;
import com.tt.tradein.mvp.models.User;
import com.tt.tradein.mvp.views.NearByView;
import com.tt.tradein.mvp.views.PersonCenterView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by tuozhaobing on 16-5-16.
 */
public class NearByPresenterImpl implements NearByPresenter {
    private NearByView nearByView;

    public NearByPresenterImpl(NearByView personCenterView) {
        this.nearByView = personCenterView;
    }
    @Override
    public void loadGoodsInfor(Context context, String city) {
        BmobQuery<Goods> query = new BmobQuery<Goods>();
        query.addWhereEqualTo("prince",city);
        query.order("-createdAt");
        query.findObjects(context, new FindListener<Goods>() {
            @Override
            public void onSuccess(List<Goods> list) {
                Log.e("NearBy",list.size()+"Goods");
                nearByView.onLoadGoodsInforSuccess(list);
            }

            @Override
            public void onError(int i, String s) {
                nearByView.onLoadGoodsError(s);
            }
        });
    }

    @Override
    public void parseGoodsUser(Context context,final List<Goods> goods) {
        final List<User> users = new ArrayList<>();
        for (int i=0;i<goods.size();i++){
            BmobQuery<User> query = new BmobQuery<User>();
            query.addWhereEndsWith("objectId",goods.get(i).getUserid());
            query.findObjects(context, new FindListener<User>() {
                @Override
                public void onSuccess(List<User> list) {
                    users.addAll(list);
                    if (users.size() == goods.size()){
                        Log.e("NearBy",users.size()+"Users");
                        nearByView.parseUser(users);
                    }
                }

                @Override
                public void onError(int i, String s) {

                }
            });
        }
    }
}
