package com.tt.tradein.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.tt.tradein.R;
import com.tt.tradein.app.MyApp;
import com.tt.tradein.di.component.AppComponent;
import com.tt.tradein.di.component.DaggerMainActivityComponent;
import com.tt.tradein.di.modules.MainActivityModule;
import com.tt.tradein.mvp.models.User;
import com.tt.tradein.mvp.presenter.PersonCenterFragmentPresenter;
import com.tt.tradein.mvp.views.PersonCenterView;
import com.tt.tradein.ui.activity.LoginActivity;
import com.tt.tradein.ui.activity.MainActivity;
import com.tt.tradein.ui.activity.SettingsActivity;
import com.tt.tradein.ui.activity.own.MyFavorActivity;
import com.tt.tradein.ui.activity.own.MyPublishActivity;
import com.tt.tradein.ui.activity.own.MyQiuGouActivity;
import com.tt.tradein.utils.UIUtils;
import com.tt.tradein.widget.CircleImageView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * Created by TuoZhaoBing on 2016/4/6 0006.
 */
public class PersonCenterFragment extends Fragment implements PersonCenterView{

	public static final String TAG = "PersonCenterFragment";
	@Bind(R.id.person_center_photo)
	CircleImageView mPersonCenterPhoto;
	@Bind(R.id.login_right_now_textview)
	TextView mLoginRightNowTextview;
	@Bind(R.id.login_right_now_rl)
	RelativeLayout mLoginRightNowRl;
	@Bind(R.id.user_name)
	TextView mUserName;
	@Bind(R.id.user_specified)
	TextView mUserSpecified;
	@Bind(R.id.photo_rl)
	RelativeLayout photoRl;
	@Bind(R.id.my_post_rl)
	RelativeLayout mMyPostRl;

	RelativeLayout mIntroduceToFriendRl;
	@Bind(R.id.setting_rl)
	RelativeLayout mSettingRl;
	@Bind(R.id.logined_rl)
	RelativeLayout mLoginedRl;
	private Context mContext;
	private View mRootView = null;
	@Inject
	PersonCenterFragmentPresenter personCenterFragmentPresenter;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mRootView == null) {
			mRootView = inflater.inflate(R.layout.person_center_fragment, container, false);
		}
		ButterKnife.bind(this, mRootView);
		setupComponent(((MyApp) mContext.getApplicationContext()).getAppComponent());
		initData();
		return mRootView;
	}

	protected void setupComponent(AppComponent appComponent) {
		DaggerMainActivityComponent.builder()
				.appComponent(appComponent)
				.mainActivityModule(new MainActivityModule(this))
				.build()
				.inject(this);
	}

	public void initData(){
		User user = BmobUser.getCurrentUser(mContext,User.class);
		if(user != null){
			//已登录
			mLoginRightNowRl.setVisibility(View.GONE);
			mLoginedRl.setVisibility(View.VISIBLE);
			if (user.getPhoto()!=null){
				personCenterFragmentPresenter.loadPhoto(mContext,user.getPhoto().getUrl());
			}else{
				mPersonCenterPhoto.setImageResource(R.mipmap.icon_photo);
			}
			mUserName.setText(user.getUsername());
			if (user.getSignature() == null || user.getSignature().equals("")){
				mUserSpecified.setText("您的签名空空如也，立即设置？");
			}else {
				mUserSpecified.setText(user.getSignature());
			}
		}else{
			mLoginedRl.setVisibility(View.GONE);
			mLoginRightNowRl.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	@OnClick({R.id.person_center_photo, R.id.login_right_now_textview, R.id.login_right_now_rl, R.id.my_post_rl,  R.id.setting_rl, R.id.logined_rl})
	public void onClick(View view) {
		User user = BmobUser.getCurrentUser(mContext,User.class);
		switch (view.getId()) {
			case R.id.person_center_photo:

				break;
			case R.id.login_right_now_textview:
				UIUtils.nextPage(mContext, LoginActivity.class);
				((MainActivity)mContext).finish();
				break;
			case R.id.my_post_rl:
				if (user == null){
					UIUtils.nextPage(mContext,LoginActivity.class);
					((MainActivity)mContext).finish();
				}else {
					UIUtils.nextPage(mContext, MyPublishActivity.class);
				}
				break;
			/*case R.id.my_qiugou_rl:
				if (user == null){
					UIUtils.nextPage(mContext,LoginActivity.class);
					((MainActivity)mContext).finish();
				}else {
					UIUtils.nextPage(mContext, MyQiuGouActivity.class);
				}
				break;
			case R.id.my_favor_rl:
				if (user == null){
					UIUtils.nextPage(mContext,LoginActivity.class);
					((MainActivity)mContext).finish();
				}else {
					UIUtils.nextPage(mContext, MyFavorActivity.class);
				}
				break;
			case R.id.introduce_to_friend_rl:
				if (user == null){
					UIUtils.nextPage(mContext,LoginActivity.class);
					((MainActivity)mContext).finish();
				}else{
					processIntroductionTofriend();
				}
				break;*/
			case R.id.setting_rl:
				UIUtils.nextPage(mContext, SettingsActivity.class);
				break;
		}
	}

	public void processIntroductionTofriend(){

	}

	@Override
	public void showPhoto(GlideDrawable drawable) {
		mPersonCenterPhoto.setImageDrawable(drawable);
	}
}
