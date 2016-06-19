package com.tt.tradein.mvp.presenter;

import android.content.Context;

/**
 * Created by tuozhaobing on 16-5-14.
 */
public interface ForgetPassPresenter {
    void requestSendSMSCode(Context context, String phone);
    void checkSMSCode(Context context,String phone,String check);
    void modifyPass(Context context,String phone,String pass);
}
