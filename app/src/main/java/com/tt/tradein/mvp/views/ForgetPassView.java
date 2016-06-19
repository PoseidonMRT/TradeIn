package com.tt.tradein.mvp.views;

/**
 * Created by tuozhaobing on 16-5-14.
 */
public interface ForgetPassView {
    void foundSuccess();
    void foundFailed(String str);
    void checkRight();
    void checkError(String str);
    void getSMSCodeSuccess();
    void getSMSCodeFailed(String str);
}
