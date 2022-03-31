package com.example.testme;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Recycler_Adapter extends RecyclerView.Adapter<Recycler_Adapter.RecyclerViewHolder> {

    ArrayList<recycle> list ;

    public Recycler_Adapter(ArrayList<recycle> list) {


        this.list = list ;
    }

    @NonNull
    @Override
    public Recycler_Adapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle,parent,false);
        RecyclerViewHolder rh = new RecyclerViewHolder(v);
        Log.d("Layout","Aa gaya yaha pe ");

//        v.setOnLongClickListener(new RV_ItemListener());

        return rh;
    }

    @Override
    public void onBindViewHolder(@NonNull Recycler_Adapter.RecyclerViewHolder holder, int position) {
        recycle current_item = list.get(position);
        Log.d("Value","v"+current_item.getApp_name());
        Log.d("Value","v"+current_item.getApp_numb());
        holder.app_name.setText(current_item.getApp_name());
        holder.app_numb.setText(String.valueOf(current_item.getApp_numb()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView app_name , app_numb;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            app_name = itemView.findViewById(R.id.aname);
            app_numb = itemView.findViewById(R.id.anum);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    list.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    return true;
                }
            });
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), ""+list.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
        }
    }
}
