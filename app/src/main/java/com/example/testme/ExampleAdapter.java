package com.example.testme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter  extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private static int TYPE_HEAD = 0;
    private static final int TYPE_LIST = 1;
    private ArrayList<ExampleItem> mExampleList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{

        int view_type;
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView title_app,title_power;

        public ExampleViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            if(viewType == TYPE_LIST)
            {
                mTextView1 = itemView.findViewById(R.id.textView1);
                mTextView2 = itemView.findViewById(R.id.textView2);
                view_type = 1;
            }
            else if(viewType == TYPE_HEAD)
            {
                title_app = (TextView) itemView.findViewById(R.id.text1_head);
                title_power = (TextView) itemView.findViewById(R.id.text2_head);
                view_type = 0;
            }

        }
    }

    public ExampleAdapter(ArrayList<ExampleItem> exampleList) {
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_LIST)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,parent,false);
            ExampleViewHolder evh = new ExampleViewHolder(v,viewType);
            return evh;
        }
        else if(viewType == TYPE_HEAD)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_head,parent,false);
            ExampleViewHolder evh = new ExampleViewHolder(v,viewType);
            return evh;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {

        if(holder.view_type == TYPE_LIST)
        {
            ExampleItem currentItem = mExampleList.get(position-1);
            holder.mTextView1.setText(currentItem.getText1());
            holder.mTextView2.setText(currentItem.getText2());
        }

    }

    @Override
    public int getItemCount() {
        return mExampleList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return TYPE_HEAD;
        return TYPE_LIST;
    }
}
