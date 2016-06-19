package com.tt.tradein.mvp.views;

import com.tt.tradein.mvp.models.User;

/**
 * Created by tuozhaobing on 16-5-14.
 */
public interface LoginActivityView {
    void loginSuccess(User user);
    void loginFailed(String str);
}
