package com.example.testme;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class List_Adapter extends BaseAdapter {

    ArrayList<list_class> list = new ArrayList<>();
    Context context;

    public List_Adapter(Context context, ArrayList<list_class> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return -1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            list_class list_text = list.get(i);
            view = LayoutInflater.from(context).inflate(R.layout.list_view,viewGroup,false);
            TextView t1 = view.findViewById(R.id.txtapp);
            View v1 = view.findViewById(R.id.colors);

            t1.setText(list_text.getApp_name());
            v1.setBackgroundColor(list_text.getColor_code());

            Log.d("color", String.valueOf(list_text.getColor_code()));
        }
        return view ;
    }
}
