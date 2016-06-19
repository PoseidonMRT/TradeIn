package com.tt.tradein.mvp.presenter;

import android.content.Context;

import com.tt.tradein.mvp.models.Goods;

import java.util.List;

/**
 * Created by tuozhaobing on 16-5-12.
 */
public interface PublishActivityPresenter {
    public boolean publishGoods(Context context,String title,String description,List<String> images,String kind,String secondKind,String price,String newDegree,String location,String prince);
    public boolean uploadPicture(Context context,String[]images);
}
