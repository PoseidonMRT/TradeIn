package com.tt.tradein.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tt.tradein.R;
import com.tt.tradein.mvp.models.Goods;
import com.tt.tradein.mvp.models.User;
import com.tt.tradein.ui.activity.base.BaseActivity;
import com.tt.tradein.ui.adapter.GridViewAdapter;
import com.tt.tradein.widget.MyGridView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tuozhaobing on 16-6-12.
 */
public class GoodsDetailActivity extends BaseActivity {
    @Bind(R.id.goods_detail_back)
    ImageView goodsDetailBack;
    @Bind(R.id.goods_detail_top)
    RelativeLayout goodsDetailTop;
    @Bind(R.id.goods_detail_title)
    TextView goodsDetailTitle;
    @Bind(R.id.goods_detail_description)
    TextView goodsDetailDescription;
    @Bind(R.id.goods_detail_location)
    TextView goodsDetailLocation;
    @Bind(R.id.goods_detail_price)
    TextView goodsDetailPrice;
    @Bind(R.id.goods_detail_image_grid)
    ListView gridView;
    private Goods mGoods;
    private User mUsers;
    private GridViewAdapter gridViewAdapter;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        mGoods = (Goods) getIntent().getSerializableExtra("Goods");
        mUsers = (User) getIntent().getSerializableExtra("User");
        goodsDetailTitle.setText(mGoods.getTitle());
        goodsDetailDescription.setText(mGoods.getDescription());
        goodsDetailLocation.setText(mGoods.getLocation());
        goodsDetailPrice.setText(mGoods.getPrice());
        gridViewAdapter = new GridViewAdapter(mGoods.getImages(),this);
        gridView.setAdapter(gridViewAdapter);
    }

    @OnClick({R.id.goods_detail_back})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goods_detail_back:
                finish();
                break;
        }
    }
}
