package com.tt.tradein.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tt.tradein.R;
import com.tt.tradein.mvp.models.KindSort;
import com.tt.tradein.utils.MeasureUtils;

import java.util.List;

/**
 * Created by tuozhaobing on 16-5-17.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private List<KindSort> data;
    private LayoutInflater layoutInflater;
    private Context context;
    private HomeAdapterOnClickListener homeAdapterOnClickListener;

    public HomeAdapter(Context context,List<KindSort> data){
        this.context=context;
        this.data=data;
        this.layoutInflater= LayoutInflater.from(context);
    }

    public void setHomeAdapterOnClickListener(HomeAdapterOnClickListener homeAdapterOnClickListener) {
        this.homeAdapterOnClickListener = homeAdapterOnClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.kind_sort_item_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)
    {
        holder.text.setText(data.get(position).getKind());
        Glide.with(context).load(data.get(position).getFile1().getUrl()).into(holder.image1);
        Glide.with(context).load(data.get(position).getFile2().getUrl()).into(holder.image2);
        Glide.with(context).load(data.get(position).getFile3().getUrl()).into(holder.image3);
        holder.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeAdapterOnClickListener.onClick(position,1);
            }
        });
        holder.image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeAdapterOnClickListener.onClick(position,2);
            }
        });
        holder.image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeAdapterOnClickListener.onClick(position,3);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image1,image2,image3;
        public TextView text;
        public MyViewHolder(View view) {
            super(view);
            image1=(ImageView)view.findViewById(R.id.kind_sort_image1);
            image2=(ImageView) view.findViewById(R.id.kind_sort_image2);
            image3=(ImageView) view.findViewById(R.id.kind_sort_image3);
            int width = MeasureUtils.getBase3Width(MeasureUtils.getScreenWidth(context));
            image1.setLayoutParams(new LinearLayout.LayoutParams(width,width));
            image2.setLayoutParams(new LinearLayout.LayoutParams(width,width));
            image3.setLayoutParams(new LinearLayout.LayoutParams(width,width));
            text = (TextView)view.findViewById(R.id.kind_text);
        }
    }

    public interface HomeAdapterOnClickListener{
        void onClick(int item,int pos);
    }
}