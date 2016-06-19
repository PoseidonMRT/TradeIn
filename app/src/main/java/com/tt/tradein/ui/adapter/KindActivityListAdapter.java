package com.tt.tradein.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tt.tradein.R;
import com.tt.tradein.mvp.models.Kind;

import java.util.List;

/**
 * Created by TuoZhaoBing on 2016/5/19 0019.
 */
public class KindActivityListAdapter extends BaseAdapter {
    public static final String TAG = "KindActivityListAdapter";
    private List<Kind> kinds;
    private Context mContext;
    private LayoutInflater inflator;
    public KindActivityListAdapter(Context context,List<Kind> kinds){
        mContext = context;
        this.kinds = kinds;
    }
    @Override
    public int getCount() {
        return kinds.size();
    }

    @Override
    public Object getItem(int position) {
        return kinds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.activity_kind_item_layout,null);
            viewHolder.kindTextView = (TextView)convertView.findViewById(R.id.activity_kind_item_kind_textview);
            viewHolder.descriptionTextView = (TextView)convertView.findViewById(R.id.activity_kind_item_description_textview);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.activity_kind_item_imageview);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.kindTextView.setText(kinds.get(position).getKind());
        Glide.with(mContext).load(kinds.get(position).getImage().getUrl()).into(viewHolder.imageView);
        viewHolder.descriptionTextView.setText(kinds.get(position).getDescription());
        return convertView;
    }

    public class ViewHolder{
        TextView kindTextView,descriptionTextView;
        ImageView imageView;
    }
}
