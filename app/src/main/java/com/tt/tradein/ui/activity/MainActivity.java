package com.tt.tradein.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.tt.tradein.R;
import com.tt.tradein.mvp.models.User;
import com.tt.tradein.ui.activity.base.BaseActivity;
import com.tt.tradein.ui.fragment.HomeFragment;
import com.tt.tradein.ui.fragment.KindSortFragment;
import com.tt.tradein.ui.fragment.NearByFragment;
import com.tt.tradein.ui.fragment.PersonCenterFragment;
import com.tt.tradein.utils.GlobalDefineValues;
import com.tt.tradein.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private List<Fragment> mFragments;
    private HomeFragment mHomeFragment;
    private KindSortFragment mKindSortFragment;
    private NearByFragment mNearByFragment;
    private PersonCenterFragment mPersonCenterFragment;
    private List<TextView> mViews;
    private TextView mHomeTextView,mNearByTextView,mKindSortTextView,mPersonCenterTextView;

    private TextView mPublishMenu;

    private int mCurrentIndex = 0;
    private int mOldIndex = 0;
    private String mWorkMode;

    private FragmentTransaction ft;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        initViews();
        initFragments();
    }

    @Override
    public void initData() {
        if (getIntent()!=null && getIntent().getExtras()!=null){
            mWorkMode = getIntent().getExtras().getString(GlobalDefineValues.MainActivityWorkMode);
            if (mWorkMode!= null && mWorkMode.equals(GlobalDefineValues.PersonCenterWorkMode)){
                showCurrentFragment(3);
            }else {
                mHomeTextView.setSelected(true);
            }
        }else{
            mHomeTextView.setSelected(true);
        }
        mHomeTextView.setOnClickListener(this);
        mNearByTextView.setOnClickListener(this);
        mKindSortTextView.setOnClickListener(this);
        mPersonCenterTextView.setOnClickListener(this);
        mPublishMenu.setOnClickListener(this);
        mPublishMenu.setOnClickListener(this);
    }

    /**
     * 初始化所用到的view；
     */
    private void initViews() {
        mHomeTextView = (TextView) findViewById(R.id.home);
        mNearByTextView = (TextView) findViewById(R.id.near_by);
        mKindSortTextView = (TextView) findViewById(R.id.kind_sort);
        mPersonCenterTextView = (TextView) findViewById(R.id.person_center);

        mViews = new ArrayList<TextView>();
        mViews.add(mHomeTextView);
        mViews.add(mNearByTextView);
        mViews.add(mKindSortTextView);
        mViews.add(mPersonCenterTextView);

        mPublishMenu = (TextView) findViewById(R.id.menu_add);
    }


    /**
     * 初始化用到的Fragment
     */
    private void initFragments() {
        mHomeFragment = new HomeFragment();
        mNearByFragment = new NearByFragment();
        mKindSortFragment = new KindSortFragment();
        mPersonCenterFragment = new PersonCenterFragment();

        mFragments = new ArrayList<Fragment>();
        mFragments.add(mHomeFragment);
        mFragments.add(mNearByFragment);
        mFragments.add(mKindSortFragment);
        mFragments.add(mPersonCenterFragment);
        //默认加载前两个Fragment，其中第一个展示，第二个隐藏
        getSupportFragmentManager().beginTransaction().add(R.id.content,mHomeFragment).add(R.id.content,mNearByFragment).hide(mNearByFragment).show(mHomeFragment).commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                mCurrentIndex = 0;
                break;
            case R.id.near_by:
                mCurrentIndex = 1;
                break;
            case R.id.kind_sort:
                mCurrentIndex = 2;
                break;
            case R.id.person_center:
                mCurrentIndex = 3;
                break;
            case R.id.menu_add:{
                processPublish();
                break;
            }
        }
        if (mCurrentIndex != 4){
            showCurrentFragment(mCurrentIndex);
        }
    }


    /**
     * 展示当前选中的Fragment
     * @param currentIndex
     */
    private void showCurrentFragment(int currentIndex) {
        if (currentIndex != mOldIndex){
            ft = getSupportFragmentManager().beginTransaction();
            mPublishMenu.setSelected(false);
            mViews.get(mOldIndex).setSelected(false);
            mViews.get(currentIndex).setSelected(true);
            ft.hide(mFragments.get(mOldIndex));
            if (!mFragments.get(currentIndex).isAdded()){
                ft.add(R.id.content,mFragments.get(currentIndex)).show(mFragments.get(currentIndex)).commit();
            }else{
                ft.show(mFragments.get(currentIndex)).commit();
            }
            mOldIndex = currentIndex;
        }
    }


    public void processPublish(){
        User user = BmobUser.getCurrentUser(this,User.class);
        if (user == null){
            UIUtils.nextPage(this,LoginActivity.class);
        }else {
            mCurrentIndex = 4;
            mViews.get(mOldIndex).setSelected(false);
            mPublishMenu.setSelected(true);
            mOldIndex = 0;
            UIUtils.nextPage(MainActivity.this, PublishGoodsActivity.class);
        }
    }

    @Override
    protected void onResume() {
        if (mCurrentIndex == 4){
            ft = getSupportFragmentManager().beginTransaction();
            mPublishMenu.setSelected(false);
            mViews.get(3).setSelected(true);
            mCurrentIndex = 3;
            if (!mFragments.get(3).isAdded()){
                ft.add(R.id.content,mFragments.get(3)).hide(mFragments.get(mOldIndex)).show(mFragments.get(3)).commit();
            }else{
                ft.hide(mFragments.get(mOldIndex)).show(mFragments.get(3)).commit();
            }
            mOldIndex = mCurrentIndex;
        }
        super.onResume();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showExitAppDialog(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showExitAppDialog(final Activity activity) {
        if (activity == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(getResources().getString(R.string.str_notice));
        builder.setMessage(getResources().getString(R.string.exit_notice_msg));

        builder.setPositiveButton(getResources().getString(R.string.str_yes), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.str_no), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }
}
