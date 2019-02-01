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

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter2";
    private String message;
    private Context context;
    public RecyclerViewAdapter2(Context context,String message)
    {
        this.context = context;
        this.message = message;
    }
    @NonNull
    @Override
    public RecyclerViewAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_layout2,viewGroup,false);
        RecyclerViewAdapter2.ViewHolder viewHolder = new RecyclerViewAdapter2.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter2.ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder2: called");
        viewHolder.message_textview2.setText(message);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView message_textview2;
        RelativeLayout chat_layout2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message_textview2 = (TextView) itemView.findViewById(R.id.chat_textview2);
            chat_layout2 = (RelativeLayout) itemView.findViewById(R.id.chat_layout2);
        }
    }
}
