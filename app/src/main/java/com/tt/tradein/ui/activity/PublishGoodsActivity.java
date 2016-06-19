package com.tt.tradein.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.tt.tradein.R;
import com.tt.tradein.app.MyApp;
import com.tt.tradein.di.component.AppComponent;
import com.tt.tradein.di.component.DaggerPublishActivityComponent;
import com.tt.tradein.di.modules.PublishActivityModule;
import com.tt.tradein.mvp.presenter.PublishActivityPresenter;
import com.tt.tradein.mvp.views.PublishView;
import com.tt.tradein.photogallery.activity.AlbumActivity;
import com.tt.tradein.photogallery.util.Bimp;
import com.tt.tradein.photogallery.util.ImageItem;
import com.tt.tradein.photogallery.util.Res;
import com.tt.tradein.ui.activity.base.BaseActivity;
import com.tt.tradein.ui.adapter.PublishGoodsGridAdapter;
import com.tt.tradein.utils.GlobalDefineValues;
import com.tt.tradein.utils.ToastUtil;
import com.tt.tradein.utils.ToolsUtils;
import com.tt.tradein.widget.LoadingDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 首页面activity
 *
 * @version 2014年10月18日  下午11:48:34
 */
@SuppressLint("HandlerLeak")
public class PublishGoodsActivity extends BaseActivity implements PublishView {
    @Bind(R.id.noScrollgridview)
    GridView noScrollgridview;
    @Bind(R.id.goods_kind_text)
    TextView mGoodsKindText;
    private PublishGoodsGridAdapter adapter;
    private View parentView;
    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    public static Bitmap bimap;
    @Bind(R.id.activity_selectimg_send)
    TextView mPublishGoodsTextView;
    @Bind(R.id.word_message)
    EditText mGoodsDescription;
    @Bind(R.id.title)
    EditText mGoodsTitle;
    @Bind(R.id.price)
    EditText mGoodsPrice;
    @Bind(R.id.new_degree)
    EditText mGoodsNewDegree;
    @Bind(R.id.location)
    TextView mGoodsLocation;
    @Bind(R.id.mount)
    EditText mGoodsMount;
    @Bind(R.id.kind)
    RelativeLayout mGoodsKind;
    private Uri photoUri; //得到清晰的图片
    private String[] imagePath;
    private LoadingDialog mLoadingDialog;
    @Inject
    PublishActivityPresenter presenter;
    private String mCurrentLocation;
    private String mCurrentPrince;
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private File img;
    private String kind, secondkind;
    private static final int TAKE_PICTURE = 1;

    @Override
    public void initView(Bundle savedInstanceState) {
        Res.init(this);
        bimap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused);
        parentView = getLayoutInflater().inflate(R.layout.activity_selectimg, null);
        setContentView(parentView);
        ButterKnife.bind(this);
        initBaiduLocation();
        setupComponent(((MyApp) getApplicationContext()).getAppComponent());
        pop = new PopupWindow(PublishGoodsActivity.this);
    }

    public void initBaiduLocation() {
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);  //开启位置信息包括city
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @OnClick({R.id.kind})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.kind:
                processChooseKind();
                break;
        }
    }

    public void processChooseKind() {
        Intent intent = new Intent(this, AllKindActivity.class);
        startActivityForResult(intent, GlobalDefineValues.CHOOSE_KIND);
    }

    @Override
    public void initData() {
        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop.setWidth(LayoutParams.MATCH_PARENT);
        pop.setHeight(LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        //整个线性布局
        parent.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        //拍照
        bt1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                photo();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        //相册
        bt2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PublishGoodsActivity.this, AlbumActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        //取消
        bt3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        //发布商品
        mPublishGoodsTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                processPublish();
            }
        });
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new PublishGoodsGridAdapter(this);
        adapter.setListener(new PublishGoodsGridAdapter.OnPublishGridDeleteItemListener() {
            @Override
            public void deleteOnClick(View v, int position) {
                Bimp.tempSelectBitmap.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new OnItemClickListener() {//查看某个照片

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == Bimp.tempSelectBitmap.size()) {
                    ll_popup.startAnimation(AnimationUtils.loadAnimation(PublishGoodsActivity.this, R.anim.activity_translate_in));
                    pop.showAtLocation(parentView, Gravity.CENTER, 0, 0);
                } else {
                    Toast.makeText(PublishGoodsActivity.this, Bimp.tempSelectBitmap.get(arg2).imagePath, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    protected void setupComponent(AppComponent appComponent) {
        DaggerPublishActivityComponent
                .builder()
                .appComponent(appComponent)
                .publishActivityModule(new PublishActivityModule(this))
                .build()
                .inject(this);
    }

    public void processPublish() {
        if (ToolsUtils.isNullOrEmpty(mGoodsTitle.getText().toString())) {
            Toast.makeText(PublishGoodsActivity.this, "主题不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (ToolsUtils.isNullOrEmpty(mGoodsDescription.getText().toString())) {
            Toast.makeText(PublishGoodsActivity.this, "描述不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (ToolsUtils.isNullOrEmpty(mGoodsPrice.getText().toString()) || ToolsUtils.isNullOrEmpty(mGoodsNewDegree.getText().toString()) || ToolsUtils.isNullOrEmpty(mGoodsLocation.getText().toString())) {
            Toast.makeText(PublishGoodsActivity.this, "请输入必要的产品信息", Toast.LENGTH_LONG).show();
            return;
        }
        imagePath = new String[Bimp.tempSelectBitmap.size()];
        for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
            imagePath[i] = Bimp.tempSelectBitmap.get(i).getImagePath();
        }
        mLoadingDialog = new LoadingDialog();
        mLoadingDialog.show(getFragmentManager(), "Loading");
        presenter.uploadPicture(PublishGoodsActivity.this, imagePath);
    }

    @Override
    protected void onDestroy() {
        mLocClient.stop();
        super.onDestroy();
    }

    @Override
    public void publishSuccess() {
        mLoadingDialog.dismiss();
        ToastUtil.showToast(this, "发布成功");
        finish();
    }

    @Override
    public void publishError(String str) {
        ToastUtil.showToast(this, "发布失败");
    }

    @Override
    public void uploadSuccess(List<String> images) {
        ToastUtil.showToast(this, "上传成功");
        /*图片上传成功后加上左右的商品信息发布商品*/
        presenter.publishGoods(this, mGoodsTitle.getText().toString(), mGoodsDescription.getText().toString(), images, kind, secondkind, mGoodsPrice.getText().toString(), mGoodsNewDegree.getText().toString(), mCurrentLocation, mCurrentPrince);
    }

    @Override
    public void uploadFailed(String str) {
        ToastUtil.showToast(this, str);
    }

    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    @Override
    protected void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    protected void onRestart() {
        adapter.notifyDataSetChanged();
        super.onRestart();
    }

    public void photo() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (data == null) {
                    return;
                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap bm = extras.getParcelable("data");
                        Uri uri = saveBitmap(bm);
                        ImageItem takePhoto = new ImageItem();
                        takePhoto.setBitmap(bm);
                        takePhoto.setImagePath(uri.getPath());
                        Bimp.tempSelectBitmap.add(takePhoto);
                    }
                }
                break;
            case GlobalDefineValues.CHOOSE_KIND:
                if (data == null) {
                    return;
                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        kind = extras.getString(GlobalDefineValues.GOODS_KIND);
                        secondkind = extras.getString(GlobalDefineValues.GOODS_SECOND_KIND);
                        Log.e("kind", kind + "  " + secondkind);
                        mGoodsKindText.setText(kind + "  " + secondkind);
                    }
                }
                break;
        }
        adapter.notifyDataSetChanged();
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Uri saveBitmap(Bitmap bm) {
        File tmpDir;
        if (hasSD()) {
            tmpDir = new File(Environment.getExternalStorageDirectory() + "/tradein/");
        } else {
            tmpDir = new File(Environment.getDataDirectory() + "/tradein/");
        }

        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }
        img = new File(tmpDir.getAbsolutePath() + System.currentTimeMillis() + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 是否有SD卡
     */
    private boolean hasSD() {
        //如果有SD卡 则下载到SD卡中
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;

        } else {
            //如果没有SD卡
            return false;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            StringBuffer sb = new StringBuffer(256);

            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果

            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            mCurrentLocation = location.getAddrStr();
            mCurrentPrince = location.getCity();
            mGoodsLocation.setText(mCurrentLocation);
            mGoodsLocation.postInvalidate();
        }

        public void onReceivePoi(BDLocation poiLocation) {

        }


    }
}