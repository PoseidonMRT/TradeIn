package com.tt.tradein.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tt.tradein.R;
import com.tt.tradein.app.MyApp;
import com.tt.tradein.di.component.AppComponent;
import com.tt.tradein.di.component.DaggerLoginActivityComponent;
import com.tt.tradein.di.component.DaggerPublishActivityComponent;
import com.tt.tradein.di.modules.LoginActivityModule;
import com.tt.tradein.di.modules.PublishActivityModule;
import com.tt.tradein.mvp.models.Goods;
import com.tt.tradein.mvp.models.User;
import com.tt.tradein.mvp.presenter.LoginActivityPresenter;
import com.tt.tradein.mvp.views.LoginActivityView;
import com.tt.tradein.ui.activity.base.BaseActivity;
import com.tt.tradein.utils.GlobalDefineValues;
import com.tt.tradein.utils.ToolsUtils;
import com.tt.tradein.utils.UIUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity implements LoginActivityView{

    @Bind(R.id.login_user_name)
    EditText mLoginUserName;
    @Bind(R.id.login_user_pass)
    EditText mLoginUserPass;
    @Bind(R.id.login_textview)
    TextView mLoginTextview;
    @Bind(R.id.login_error)
    TextView mForgetPass;
    @Bind(R.id.register_now)
    TextView mRegisterNow;
    @Bind(R.id.qq_login)
    ImageView mQQLogin;
    @Bind(R.id.weixin_login)
    ImageView mWeixinLogin;
    @Bind(R.id.weibo_login)
    ImageView mWeiboLogin;
    private UMShareAPI mShareAPI;
    @Inject
    LoginActivityPresenter presenter;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setupComponent(((MyApp) getApplicationContext()).getAppComponent());
        mShareAPI = UMShareAPI.get(this);
    }

    protected void setupComponent(AppComponent appComponent) {
        DaggerLoginActivityComponent
                .builder()
                .appComponent(appComponent)
                .loginActivityModule(new LoginActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.login_textview, R.id.login_error, R.id.register_now, R.id.qq_login, R.id.weixin_login, R.id.weibo_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_textview:
                presenter.login(mLoginUserName.getText().toString(),mLoginUserPass.getText().toString(),this);
                break;
            case R.id.login_error:
                UIUtils.nextPage(this,ForgetPassActivity.class);
                break;
            case R.id.register_now:
                UIUtils.nextPage(this,RegisterActivity.class);
                break;
            case R.id.qq_login:
                break;
            case R.id.weixin_login:
                processWeiXinLogin();
                break;
            case R.id.weibo_login:
                processWBLogin();
                break;
        }
    }

    public void processWeiXinLogin(){
        Log.e(TAG,"WeiXin");
        SHARE_MEDIA platform = SHARE_MEDIA.WEIXIN;
        mShareAPI.doOauthVerify(this, platform, umAuthListener);
    }

    public void processWBLogin(){
        SHARE_MEDIA platform = SHARE_MEDIA.SINA;
        mShareAPI.doOauthVerify(this, platform, umAuthListener);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText( getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void loginSuccess(User user) {
        Toast.makeText(this,user.getMobilePhoneNumber(),Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString(GlobalDefineValues.MainActivityWorkMode,GlobalDefineValues.PersonCenterWorkMode);
        UIUtils.nextPage(this,MainActivity.class,bundle);
        finish();
    }

    @Override
    public void loginFailed(String str) {
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            UIUtils.nextPage(this,MainActivity.class);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
