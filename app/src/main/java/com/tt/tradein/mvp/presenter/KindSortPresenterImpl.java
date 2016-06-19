package com.tt.tradein.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.tt.tradein.mvp.models.Goods;
import com.tt.tradein.mvp.models.KindSort;
import com.tt.tradein.mvp.models.SecondKind;
import com.tt.tradein.mvp.views.KindSortView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by tuozhaobing on 16-5-17.
 */
public class KindSortPresenterImpl implements KindSortPresenter {
    private KindSortView kindSortView;
    public KindSortPresenterImpl(KindSortView kindSortView){
        this.kindSortView = kindSortView;
    }

    @Override
    public void loadKindSortData(final Context context) {
        BmobQuery<KindSort> query = new BmobQuery<KindSort>();
        query.findObjects(context, new FindListener<KindSort>() {
            @Override
            public void onSuccess(List<KindSort> list) {
                kindSortView.presenKindSorts(list);
            }

            @Override
            public void onError(int i, String s) {
                kindSortView.presenterKindsError(s);
            }
        });
    }
}
