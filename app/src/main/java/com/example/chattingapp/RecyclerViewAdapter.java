package com.example.chattingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private String message;
    private Context context;
    public RecyclerViewAdapter(Context context,String message)
    {
        this.context = context;
        this.message = message;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_layout,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: called");
        viewHolder.message_textview.setText(message);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView message_textview;
        RelativeLayout chat_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message_textview = (TextView) itemView.findViewById(R.id.chat_textview);
            chat_layout = (RelativeLayout) itemView.findViewById(R.id.chat_layout);
        }
    }
}
