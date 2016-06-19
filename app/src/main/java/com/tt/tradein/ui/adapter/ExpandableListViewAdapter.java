package com.tt.tradein.ui.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.tt.tradein.R;
import com.tt.tradein.mvp.models.Goods;
import com.tt.tradein.mvp.models.QueryGood;
import com.tt.tradein.mvp.models.User;
import com.tt.tradein.utils.TimeUtils;
import com.tt.tradein.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by root on 2/3/16.
 */
public class ExpandableListViewAdapter implements ExpandableListAdapter {

    private Context context;
    private List<Goods> goods;
    private List<User> users;

    public ExpandableListViewAdapter(Context context, List<Goods> brands,List<User> users) {
        this.context = context;
        this.goods = brands;
        this.users = users;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return goods.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return goods.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return goods.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ParentHolder parentHolder = null;

        if(convertView == null) {
            LayoutInflater userInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = userInflater.inflate(R.layout.item_parent, null);
            convertView.setHorizontalScrollBarEnabled(true);
            parentHolder = new ParentHolder();
            convertView.setTag(parentHolder);
        } else {
            parentHolder = (ParentHolder) convertView.getTag();
        }
        parentHolder.mUserNameTextView = (TextView) convertView.findViewById(R.id.user_name);
        parentHolder.mUserPhotoImageView = (CircleImageView) convertView.findViewById(R.id.user_photo);
        parentHolder.mPriceTextView = (TextView)convertView.findViewById(R.id.goods_price);
        parentHolder.mDescriptionTextView = (TextView)convertView.findViewById(R.id.goods_description);
        parentHolder.mPriceTextView.setText("￥"+goods.get(groupPosition).getPrice());
        final CircleImageView circleView = parentHolder.mUserPhotoImageView;
        if (users.get(groupPosition).getPhoto() != null){
            Glide.with(context).load(users.get(groupPosition).getPhoto().getUrl()).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    circleView.setImageDrawable(resource);
                    circleView.postInvalidate();
                }
            });
        }else{
            parentHolder.mUserPhotoImageView.setImageResource(R.mipmap.icon_photo);
        }
        parentHolder.mUserNameTextView.setText(users.get(groupPosition).getUsername());
        parentHolder.mDescriptionTextView.setText(goods.get(groupPosition).getDescription());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildHolder childHolder = null;
        if (goods.get(groupPosition).getImages() == null){
            return new View(context);
        }
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_group_child, parent, false);
            childHolder = new ChildHolder();
            convertView.setTag(childHolder);
        }
        else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        childHolder.horizontalListView = (RecyclerView) convertView.findViewById(R.id.pictures);
        childHolder.mLocationTextView = (TextView)convertView.findViewById(R.id.location_textview);
        childHolder.mTimeTextView = (TextView)convertView.findViewById(R.id.goods_time);
        childHolder.mTimeTextView.setText(TimeUtils.convert_before(goods.get(groupPosition).getCreatedAt()));
        childHolder.mLocationTextView.setText(goods.get(groupPosition).getLocation());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        childHolder.horizontalListView.setLayoutManager(layoutManager);
        RecyclerViewAdapter horizontalListAdapter = new RecyclerViewAdapter(context, goods.get(groupPosition).getImages());
        childHolder.horizontalListView.setAdapter(horizontalListAdapter);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    private static class ChildHolder {
        static TextView mTimeTextView;
        static TextView mLocationTextView;
        static RecyclerView horizontalListView;
    }

    private static class ParentHolder {
        CircleImageView mUserPhotoImageView;
        TextView mUserNameTextView,mPriceTextView,mDescriptionTextView;
    }
}
