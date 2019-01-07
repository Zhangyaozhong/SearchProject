package com.bwie.android.exercise1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.android.exercise1.R;
import com.bwie.android.exercise1.bean.SearchBean;

import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.SearchViewHolder> {
    private Context context;
    private List<SearchBean.ResultBean> list;

    public GridAdapter(Context context, List<SearchBean.ResultBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_layout, parent, false);
        SearchViewHolder holder = new SearchViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.tv_name.setText(list.get(position).commodityName);
        holder.tv_price.setText("￥" + list.get(position).price);
        Glide.with(context)
                .load(list.get(position).masterPic)
                .into(holder.iv_icon);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_icon;
        private TextView tv_name;
        private TextView tv_price;

        public SearchViewHolder(View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_price = itemView.findViewById(R.id.tv_price);
        }
    }
}
