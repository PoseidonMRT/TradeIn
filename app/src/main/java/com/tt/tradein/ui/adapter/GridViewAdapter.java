package com.tt.tradein.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tt.tradein.ui.activity.base.BaseActivity;

import java.util.List;

/**
 * Created by tuozhaobing on 16-6-14.
 */
public class GridViewAdapter extends BaseAdapter {
    private List<String> mImages;
    private Context mContext;

    public GridViewAdapter(List<String> mImages, Context mContext) {
        this.mImages = mImages;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageview; // 声明ImageView的对象
        if (convertView == null) {
            imageview = new ImageView(mContext); // 实例化ImageView的对象
            imageview.setScaleType(ImageView.ScaleType.CENTER_INSIDE); // 设置缩放方式
        } else {
            imageview = (ImageView) convertView;
        }
        Glide.with(mContext).load(mImages.get(position)).into(imageview); // 为ImageView设置要显示的图片
        return imageview;
    }
}
