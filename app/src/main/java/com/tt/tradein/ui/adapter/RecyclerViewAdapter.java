package com.tt.tradein.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tt.tradein.R;
import com.tt.tradein.utils.MeasureUtils;

import java.util.List;

/**
 * Created by root on 2/3/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<String> images;

    public RecyclerViewAdapter(Context context, List<String> mobiles) {
        this.mContext = context;
        this.images = mobiles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cardView = inflater.inflate(R.layout.item_child, null, false);
        int width = MeasureUtils.getBaseCellWidth(MeasureUtils.getScreenWidth(mContext));
        cardView.setLayoutParams(new LinearLayoutCompat.LayoutParams(width,width));
        ViewHolder viewHolder = new ViewHolder(cardView);
        viewHolder.mobileImage = (ImageView) cardView.findViewById(R.id.image_picutre);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageView mobileImageView = (ImageView) holder.mobileImage;
        Glide.with(mContext).load(images.get(position)).into(mobileImageView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mobileImage;
        public ViewHolder(View itemView) {
            super(itemView);
            mobileImage = (ImageView) itemView.findViewById(R.id.image_picutre);
        }
    }

}
