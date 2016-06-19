package com.tt.tradein.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tt.tradein.R;
import com.tt.tradein.app.MyApp;
import com.tt.tradein.di.component.AppComponent;
import com.tt.tradein.di.component.DaggerLoginActivityComponent;
import com.tt.tradein.di.component.DaggerRegisterActivityComponent;
import com.tt.tradein.di.modules.LoginActivityModule;
import com.tt.tradein.di.modules.RegisterActivityModule;
import com.tt.tradein.mvp.presenter.RegisterActivityPresenter;
import com.tt.tradein.mvp.views.RegisterView;
import com.tt.tradein.ui.activity.base.BaseActivity;
import com.tt.tradein.utils.GlobalDefineValues;
import com.tt.tradein.utils.ToastUtil;
import com.tt.tradein.utils.ToolsUtils;
import com.tt.tradein.utils.UIUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements View.OnClickListener,RegisterView{

    @Bind(R.id.activity_register_back_ll)
    LinearLayout mBackLinearLayout;
    @Bind(R.id.activity_register_input_edittext)
    EditText mInputEditText;
    @Bind(R.id.activity_register_confirm_input_edittext)
    EditText mConfirmPassWordEditText;
    @Bind(R.id.activity_register_confirm_input_ll)
    LinearLayout mConfirmPassWordLL;
    @Bind(R.id.activity_register_sure_textview)
    TextView mSureTextView;
    @Bind(R.id.activity_register_input_phone_number_textview)
    TextView mInputPhoneTextView;
    @Bind(R.id.activity_register_input_check_number_textview)
    TextView mInputCheckNumberTextView;
    @Bind(R.id.activity_register_input_password_textview)
    TextView mSetPassWordTextView;
    @Bind(R.id.activity_register_input_username_ll)
    LinearLayout mInputUserNameLL;
    @Bind(R.id.activity_register_user_input_edittext)
    EditText mUserNameEditText;

    private String phoneNum;
    private int mWorkStateMode = GlobalDefineValues.RegisterActivityWorkFirstStep;
    @Inject
    RegisterActivityPresenter presenter;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setupComponent(((MyApp) getApplicationContext()).getAppComponent());
    }

    protected void setupComponent(AppComponent appComponent) {
        DaggerRegisterActivityComponent
                .builder()
                .appComponent(appComponent)
                .registerActivityModule(new RegisterActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initData() {
        mSureTextView.setOnClickListener(this);
        mBackLinearLayout.setOnClickListener(this);
        switch (mWorkStateMode){
            case GlobalDefineValues.RegisterActivityWorkFirstStep:{
                processInputPhoneNumber();
                break;
            }
            case GlobalDefineValues.RegisterActivityWorkSecondStep:{
                processInputCheckNumber();
                break;
            }
            case GlobalDefineValues.RegisterActivityWorkThirdStep:{
                processSetPassWord();
                break;
            }
        }
    }

    public void processInputPhoneNumber(){
        mInputPhoneTextView.setSelected(true);
        mInputCheckNumberTextView.setSelected(false);
        mSetPassWordTextView.setSelected(false);

        mConfirmPassWordLL.setVisibility(View.GONE);

        mSureTextView.setText(R.string.activity_register_get_check_sms);

        mInputEditText.setHint(R.string.activity_register_please_input_your_phone_number);
        mInputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    public void processSetPassWord(){
        mSetPassWordTextView.setSelected(true);

        mInputCheckNumberTextView.setSelected(false);
        mInputPhoneTextView.setSelected(false);
        mSureTextView.setText(R.string.activity_register_name);

        mInputEditText.setHint(R.string.activity_register_please_input_password);
        mInputEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mInputEditText.setText("");
        mConfirmPassWordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mConfirmPassWordEditText.setHint(R.string.activity_register_please_confirm_password);
        mInputUserNameLL.setVisibility(View.VISIBLE);
        mConfirmPassWordLL.setVisibility(View.VISIBLE);
    }

    public void processInputCheckNumber(){
        mInputCheckNumberTextView.setSelected(true);
        mInputPhoneTextView.setSelected(false);
        mSetPassWordTextView.setSelected(false);

        mConfirmPassWordLL.setVisibility(View.GONE);

        mSureTextView.setText(R.string.activity_register_check_string);

        mInputEditText.setHint(R.string.activity_register_please_input_check_number);
        mInputEditText.setText("");
    }

    public void processRegister(){
        if (ToolsUtils.isNullOrEmpty(mUserNameEditText.getText().toString())){
            ToastUtil.showToast(this,"请输入用户名");
        }
        if (mInputEditText.getText().equals("") || mConfirmPassWordEditText.getText().toString().equals("")){
            ToastUtil.showToast(this,"输入密码或再次确认密码");
        }else if (!mInputEditText.getText().toString().equals(mConfirmPassWordEditText.getText().toString())){
            ToastUtil.showToast(this,"两次输入密码不一致！");
        }else if (!ToolsUtils.isCorrectUserPwd(mInputEditText.getText().toString())){
            ToastUtil.showToast(this,"密码应为6-18位，由字母数字下划线组成");
        }else {
            presenter.register(this,phoneNum,mUserNameEditText.getText().toString(),mInputEditText.getText().toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_register_sure_textview:{
                if (mWorkStateMode == GlobalDefineValues.RegisterActivityWorkFirstStep){
                    mWorkStateMode = GlobalDefineValues.RegisterActivityWorkSecondStep;
                    if (mInputEditText.getText().toString() != null && !mInputEditText.getText().toString().equals("")){
                        //获取验证码
                        phoneNum = mInputEditText.getText().toString();
                        presenter.requestSendSMSCode(RegisterActivity.this,phoneNum);
                    }else{
                        ToastUtil.showToast(this,"请输入手机号，手机号不能为空");
                    }

                }else if (mWorkStateMode == GlobalDefineValues.RegisterActivityWorkSecondStep){
                    if (mInputEditText.getText().toString() != null && !mInputEditText.getText().toString().equals("")){
                        presenter.checkSMSCode(RegisterActivity.this,phoneNum,mInputEditText.getText().toString());
                    }
                }else if (mWorkStateMode == GlobalDefineValues.RegisterActivityWorkThirdStep){
                    processRegister();
                }
                break;
            }
            case R.id.activity_register_back_ll:{
                if (mWorkStateMode == GlobalDefineValues.RegisterActivityWorkFirstStep){
                    finish();
                }else if (mWorkStateMode == GlobalDefineValues.RegisterActivityWorkSecondStep){
                    processInputPhoneNumber();
                    mWorkStateMode = GlobalDefineValues.RegisterActivityWorkFirstStep;
                }else if (mWorkStateMode == GlobalDefineValues.RegisterActivityWorkThirdStep){
                    processInputPhoneNumber();
                    mWorkStateMode = GlobalDefineValues.RegisterActivityWorkFirstStep;
                }
                break;
            }
        }
    }

    @Override
    public void checkSmsCodeStatuse(String str) {

    }

    @Override
    public void registerSuccess() {
        ToastUtil.showToast(this,"恭喜您注册成功，立即登录");
        UIUtils.nextPage(this,LoginActivity.class);
        finish();
    }

    @Override
    public void registerFailed(String str) {
        ToastUtil.showToast(this,str);
        processInputPhoneNumber();
    }

    @Override
    public void checkRight() {
        processSetPassWord();
        mWorkStateMode = GlobalDefineValues.RegisterActivityWorkThirdStep;
    }

    @Override
    public void checkError(String string) {
        ToastUtil.showToast(RegisterActivity.this,string);
        mInputEditText.setText("");
    }

    @Override
    public void getSMSCodeSuccess() {
        ToastUtil.showToast(RegisterActivity.this,"验证码已成功发送，请注意查收");
        processInputCheckNumber();
    }

    @Override
    public void getSMSCodeFailed(String str) {
        ToastUtil.showToast(RegisterActivity.this,str);
        processInputPhoneNumber();
    }
}
