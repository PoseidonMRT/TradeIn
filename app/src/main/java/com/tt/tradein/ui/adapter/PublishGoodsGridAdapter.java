package com.tt.tradein.ui.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tt.tradein.R;
import com.tt.tradein.photogallery.util.Bimp;
import com.tt.tradein.utils.GlobalDefineValues;
import com.tt.tradein.utils.MeasureUtils;

/**
 * Created by tuozhaobing on 16-5-15.
 */
public class PublishGoodsGridAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int selectedPosition = -1;
    private boolean shape;
    private Context mContext;
    private OnPublishGridDeleteItemListener listener;

    public PublishGoodsGridAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setListener(OnPublishGridDeleteItemListener listener) {
        this.listener = listener;
    }

    public boolean isShape() {
        return shape;
    }

    public void setShape(boolean shape) {
        this.shape = shape;
    }

    public int getCount() {
        if(Bimp.tempSelectBitmap.size() == 9){
            return 9;
        }
        return (Bimp.tempSelectBitmap.size() + 1);
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
            holder = new ViewHolder();
            int width = MeasureUtils.getBase5Width(MeasureUtils.getScreenWidth(mContext));
            convertView.setLayoutParams(new AbsListView.LayoutParams(width,width));
            holder.deleteImageView = (ImageView)convertView.findViewById(R.id.item_grida_delete);
            holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.deleteOnClick(v,position);
            }
        });
        if (position ==Bimp.tempSelectBitmap.size()) {
            holder.image.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_addpic_unfocused));
            holder.deleteImageView.setVisibility(View.INVISIBLE);
            if (position == 9) {
                holder.image.setVisibility(View.GONE);
            }
        } else {
            holder.deleteImageView.setVisibility(View.VISIBLE);
            holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
        }

        return convertView;
    }
    public class ViewHolder {
        public ImageView deleteImageView;
        public ImageView image;
    }

    public interface OnPublishGridDeleteItemListener{
        void deleteOnClick(View v,int position);
    }
}
