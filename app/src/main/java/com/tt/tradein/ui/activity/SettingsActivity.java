package com.tt.tradein.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.tt.tradein.R;
import com.tt.tradein.ui.activity.base.BaseActivity;
import com.tt.tradein.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * Created by Explorer on 2016/5/11.
 */
public class SettingsActivity extends BaseActivity {

    @Bind(R.id.setting_back_ll)
    LinearLayout mBackLinearLayout;
    @Bind(R.id.sign_out)
    Button mSignOut;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
    }

    @OnClick({R.id.setting_back_ll,R.id.sign_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_back_ll:
                finish();
                break;
            case R.id.sign_out:
                BmobUser.logOut(this);
                UIUtils.nextPage(this,MainActivity.class);
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
