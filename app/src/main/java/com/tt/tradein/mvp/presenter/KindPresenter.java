package com.tt.tradein.mvp.presenter;

import android.content.Context;

import com.tt.tradein.mvp.models.Kind;

/**
 * Created by TuoZhaoBing on 2016/5/18 0018.
 */
public interface KindPresenter {
    public static final String TAG = "KindPresenter";
    void loadKind(Context context);
    void loadSecondKind(Context context,Kind kind);
}
