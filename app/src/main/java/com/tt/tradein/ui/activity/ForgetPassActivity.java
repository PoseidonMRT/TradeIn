package com.tt.tradein.ui.activity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tt.tradein.R;
import com.tt.tradein.app.MyApp;
import com.tt.tradein.di.component.AppComponent;
import com.tt.tradein.di.component.DaggerForgetActivityComponent;
import com.tt.tradein.di.component.DaggerLoginActivityComponent;
import com.tt.tradein.di.modules.ForgetActivityModule;
import com.tt.tradein.di.modules.LoginActivityModule;
import com.tt.tradein.mvp.presenter.ForgetPassPresenter;
import com.tt.tradein.mvp.views.ForgetPassView;
import com.tt.tradein.ui.activity.base.BaseActivity;
import com.tt.tradein.utils.GlobalDefineValues;
import com.tt.tradein.utils.ToastUtil;
import com.tt.tradein.utils.ToolsUtils;
import com.tt.tradein.utils.UIUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ForgetPassActivity extends BaseActivity implements ForgetPassView,View.OnClickListener {

    @Bind(R.id.activity_forget_password_back_ll)
    LinearLayout mBackLinearLayout;
    @Bind(R.id.activity_forget_password_input_phone_number_textview)
    TextView mInputPhoneNumberTextView;
    @Bind(R.id.activity_forget_password_input_check_number_textview)
    TextView mInputCheckNumberTextView;
    @Bind(R.id.activity_forget_password_input_password_textview)
    TextView mInputNewPassWordTextView;
    @Bind(R.id.activity_forget_password_checknumber_input_ll)
    LinearLayout mInputCheckNumberLinearLayout;
    @Bind(R.id.activity_forget_password_input_phonenumber_ll)
    LinearLayout mInputPhoneNumberLinearLayout;
    @Bind(R.id.activity_reinput_password_input_ll)
    LinearLayout mInputPassWordLinearLayout;
    @Bind(R.id.activity_reinput_confirm_password_input_ll)
    LinearLayout mConfirmInputPassWordLinearLayout;
    @Bind(R.id.activity_forget_password_sure_textview)
    TextView mSureTextView;
    @Bind(R.id.activity_forget_password_confirm_input_new_password_edittext)
    EditText mConfirmNewPassWordEditText;
    @Bind(R.id.activity_forget_password_check_number_edittext)
    EditText mInputPhoneNumberEditText;
    @Bind(R.id.activity_forget_password_check_number_input_edittext)
    EditText mInputCheckNumberEditText;
    @Bind(R.id.activity_forget_password_input_new_password_edittext)
    EditText mInputNewPassWordEditText;
    @Inject
    ForgetPassPresenter forgetPassPresenter;
    private String phoneNum ;
    private String mWorkMode = GlobalDefineValues.ForgetPassWordInputPhoneNumber;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forget_pass);
        ButterKnife.bind(this);
        setupComponent(((MyApp) getApplicationContext()).getAppComponent());
    }

    protected void setupComponent(AppComponent appComponent) {
        DaggerForgetActivityComponent
                .builder()
                .appComponent(appComponent)
                .forgetActivityModule(new ForgetActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initData() {
        mBackLinearLayout.setOnClickListener(this);
        mSureTextView.setOnClickListener(this);
        if (mWorkMode.equals(GlobalDefineValues.ForgetPassWordInputPhoneNumber)){
            processInputPhoneNumber();
        }else if (mWorkMode.equals(GlobalDefineValues.ForgetPassWordInputCheckNUmber)){
            processInputCheckNumber();
        }else if (mWorkMode.equals(GlobalDefineValues.ForgetPassWordInputPassWord)){
            processInputNewPassWord();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_forget_password_back_ll:{
                finish();
                break;
            }
            case R.id.activity_forget_password_sure_textview:{
                if (mWorkMode.equals(GlobalDefineValues.ForgetPassWordInputPhoneNumber)){
                    phoneNum = mInputPhoneNumberEditText.getText().toString();
                    if (ToolsUtils.checkPhone(phoneNum)){
                        forgetPassPresenter.requestSendSMSCode(ForgetPassActivity.this,phoneNum);
                    }else{
                        ToastUtil.showToast(ForgetPassActivity.this,"手机号格式错误");
                    }
                }else if (mWorkMode.equals(GlobalDefineValues.ForgetPassWordInputCheckNUmber)){
                    if (ToolsUtils.isNullOrEmpty(mInputCheckNumberEditText.getText().toString())){
                        forgetPassPresenter.checkSMSCode(ForgetPassActivity.this,phoneNum,mInputCheckNumberEditText.getText().toString());
                    }else {
                        ToastUtil.showToast(this,"验证码错误");
                    }
                }else if (mWorkMode.equals(GlobalDefineValues.ForgetPassWordInputPassWord)){
                    if (mInputNewPassWordEditText.getText().toString().equals(mConfirmNewPassWordEditText.getText().toString())){
                        forgetPassPresenter.modifyPass(this,phoneNum,mInputNewPassWordEditText.getText().toString());
                    }else{
                        ToastUtil.showToast(ForgetPassActivity.this,"两次密码不一致！");
                    }
                }
                break;
            }
        }
    }


    @Override
    public void foundSuccess() {
        ToastUtil.showToast(this,"找回成功，请登录");
        UIUtils.nextPage(this,LoginActivity.class);
        finish();
    }

    @Override
    public void foundFailed(String str) {
        ToastUtil.showToast(this,str);
        processInputPhoneNumber();
    }

    @Override
    public void checkRight() {
        mWorkMode = GlobalDefineValues.ForgetPassWordInputPassWord;
        processInputNewPassWord();
    }

    @Override
    public void checkError(String str) {
        ToastUtil.showToast(this,str);
    }

    @Override
    public void getSMSCodeSuccess() {
        ToastUtil.showToast(this,"验证码发送成功");
        mWorkMode = GlobalDefineValues.ForgetPassWordInputCheckNUmber;
        processInputCheckNumber();
    }

    @Override
    public void getSMSCodeFailed(String str) {
        ToastUtil.showToast(this,str);
    }

    public void processInputPhoneNumber(){
        mInputPhoneNumberTextView.setSelected(true);
        mInputNewPassWordTextView.setSelected(false);
        mInputCheckNumberTextView.setSelected(false);
        mInputPhoneNumberLinearLayout.setVisibility(View.VISIBLE);
        mInputCheckNumberLinearLayout.setVisibility(View.GONE);
        mInputPassWordLinearLayout.setVisibility(View.GONE);
        mConfirmInputPassWordLinearLayout.setVisibility(View.GONE);
        mSureTextView.setText(R.string.activity_register_get_check_sms);
    }

    public void processInputCheckNumber(){
        mInputPhoneNumberTextView.setSelected(false);
        mInputNewPassWordTextView.setSelected(false);
        mInputCheckNumberTextView.setSelected(true);
        mInputPhoneNumberLinearLayout.setVisibility(View.GONE);
        mInputCheckNumberLinearLayout.setVisibility(View.VISIBLE);
        mInputPassWordLinearLayout.setVisibility(View.GONE);
        mConfirmInputPassWordLinearLayout.setVisibility(View.GONE);
        mSureTextView.setText(R.string.activity_register_check_string);
    }

    public void processInputNewPassWord(){
        mInputPhoneNumberTextView.setSelected(false);
        mInputNewPassWordTextView.setSelected(true);
        mInputCheckNumberTextView.setSelected(false);
        mInputNewPassWordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mConfirmNewPassWordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mInputPhoneNumberLinearLayout.setVisibility(View.GONE);
        mInputCheckNumberLinearLayout.setVisibility(View.GONE);
        mInputPassWordLinearLayout.setVisibility(View.VISIBLE);
        mConfirmInputPassWordLinearLayout.setVisibility(View.VISIBLE);
        mSureTextView.setText(R.string.activity_forget_password_confirm_change);
    }
}
