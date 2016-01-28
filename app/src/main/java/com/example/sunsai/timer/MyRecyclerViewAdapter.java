package com.example.sunsai.timer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sunsai on 2016/1/28.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {


    private List<String> list;

    private Context context;

    public MyRecyclerViewAdapter(Context context, List<String> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String item = getItem(position);
        holder.getTextView().setText(item);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_view_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String getItem(int pos) {
        return list.get(getItemCount() -1 - pos);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private View view;
        public TextView getTextView() {
            return textView;
        }
        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            textView = (TextView) itemView.findViewById(R.id.text_view);
        }
    }
}
