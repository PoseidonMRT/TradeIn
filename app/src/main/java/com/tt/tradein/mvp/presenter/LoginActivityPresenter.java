package com.tt.tradein.mvp.presenter;

import android.content.Context;

import com.tt.tradein.mvp.models.User;

/**
 * Created by tuozhaobing on 16-5-14.
 */
public interface LoginActivityPresenter {
    void login(String username, String pass, Context context);
}
