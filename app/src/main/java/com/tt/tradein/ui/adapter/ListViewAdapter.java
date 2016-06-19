package com.tt.tradein.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tt.tradein.R;
import com.tt.tradein.mvp.models.Kind;

import java.util.List;

/**
 * Created by TuoZhaoBing on 2016/5/18 0018.
 */
public class ListViewAdapter extends BaseAdapter {
    public static final String TAG = "ListViewAdapter";
    private List<Kind> kinds;
    private Context mContext;
    private LayoutInflater inflator;
    public ListViewAdapter(Context context,List<Kind> kinds){
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
            convertView = inflator.inflate(R.layout.kind_listview_item,null);
            viewHolder.textView = (TextView)convertView.findViewById(R.id.kind_item_text);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(kinds.get(position).getKind());
        return convertView;
    }

    public class ViewHolder{
        TextView textView;
    }
}
