package com.tt.tradein.mvp.presenter;

import android.content.Context;

import com.tt.tradein.mvp.models.Goods;
import com.tt.tradein.mvp.models.Kind;
import com.tt.tradein.mvp.models.SecondKind;
import com.tt.tradein.mvp.views.AllKindViews;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by TuoZhaoBing on 2016/5/18 0018.
 */
public class KindPresenterImpl implements KindPresenter {
    public static final String TAG = "KindPresenterImpl";
    private AllKindViews allKindViews;
    public KindPresenterImpl(AllKindViews allKindViews){
        this.allKindViews = allKindViews;
    }

    @Override
    public void loadKind(Context context) {
        BmobQuery<Kind> query = new BmobQuery<Kind>();
        query.findObjects(context, new FindListener<Kind>() {
            @Override
            public void onSuccess(List<Kind> list) {
                allKindViews.showKind(list);
            }

            @Override
            public void onError(int i, String s) {
                allKindViews.loadFailed(s);
            }
        });
    }

    @Override
    public void loadSecondKind(Context context,Kind kind) {
        BmobQuery<SecondKind> query = new BmobQuery<SecondKind>();
        query.addWhereEqualTo("parentid",kind.getObjectId());
        query.findObjects(context, new FindListener<SecondKind>() {
            @Override
            public void onSuccess(List<SecondKind> list) {
                allKindViews.showSecondKind(list);
            }

            @Override
            public void onError(int i, String s) {
                allKindViews.loadFailed(s);
            }
        });
    }
}
