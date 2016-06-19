package com.tt.tradein.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.tt.tradein.mvp.models.User;
import com.tt.tradein.mvp.views.ForgetPassView;
import com.tt.tradein.utils.GlobalDefineValues;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * Created by tuozhaobing on 16-5-14.
 */
public class ForcegetPassPresenterImpl implements ForgetPassPresenter {
    private ForgetPassView forgetPassView;

    public ForcegetPassPresenterImpl(ForgetPassView forgetPassView){
        this.forgetPassView = forgetPassView;
    }

    @Override
    public void requestSendSMSCode(final Context context,final String phone) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("mobilePhoneNumber", phone);
        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> object) {
                // TODO Auto-generated method stub
                BmobSMS.requestSMSCode(context, phone, GlobalDefineValues.FoundPass,new RequestSMSCodeListener() {
                    @Override
                    public void done(Integer smsId,BmobException ex) {
                        // TODO Auto-generated method stub
                        if(ex==null){
                            Log.i("bmob", "短信id："+smsId);
                            forgetPassView.getSMSCodeSuccess();
                        }else{
                            forgetPassView.getSMSCodeFailed(ex.getLocalizedMessage());
                        }
                    }
                });
            }
            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                forgetPassView.getSMSCodeFailed(msg);
            }
        });
    }

    @Override
    public void checkSMSCode(Context context, String phone, String check) {
        BmobSMS.verifySmsCode(context,phone, check, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException ex) {
                // TODO Auto-generated method stub
                if(ex==null){
                    forgetPassView.checkRight();
                }else{
                    forgetPassView.checkError(ex.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void modifyPass(final Context context, String phone, final String pass) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("mobilePhoneNumber", phone);
        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> object) {
                // TODO Auto-generated method stub
                object.get(0).setPassword(pass);
                object.get(0).update(context, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        forgetPassView.foundSuccess();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        forgetPassView.foundFailed(s);
                    }
                });
            }
            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                forgetPassView.foundFailed(msg);
            }
        });
    }
}
