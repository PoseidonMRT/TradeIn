package com.tt.tradein.ui.activity.own;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tt.tradein.R;
import com.tt.tradein.mvp.models.Goods;
import com.tt.tradein.mvp.models.User;
import com.tt.tradein.ui.activity.base.BaseActivity;
import com.tt.tradein.ui.adapter.ExpandableListViewAdapter;
import com.tt.tradein.widget.CustomExpandableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class MyPublishActivity extends BaseActivity {

    @Bind(R.id.my_publish_goods_list)
    CustomExpandableListView mHomeGoodsList;
    @Bind(R.id.my_publish_back)
    ImageView myPublishBack;
    @Bind(R.id.goods_detail_top)
    RelativeLayout goodsDetailTop;
    private ExpandableListViewAdapter adapter;
    private List<Goods> goodses;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_publish);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        loadGoodsInfor(this);
    }

    public void loadGoodsInfor(Context context) {
        BmobQuery<Goods> query = new BmobQuery<Goods>();
        final User user = BmobUser.getCurrentUser(this, User.class);
        ;
        final List<User> users = new ArrayList<>();
        query.addWhereEqualTo("userid", user.getObjectId());
        query.findObjects(context, new FindListener<Goods>() {
            @Override
            public void onSuccess(List<Goods> list) {
                for (int i = 0; i < list.size(); i++) {
                    users.add(user);
                }
                adapter = new ExpandableListViewAdapter(MyPublishActivity.this, list, users);
                mHomeGoodsList.setAdapter(adapter);
                for (int i = 0; i < adapter.getGroupCount(); i++) {
                    mHomeGoodsList.expandGroup(i);
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(MyPublishActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.my_publish_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_publish_back:
                finish();
                break;
        }
    }
}
