package com.example.testme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.Collection;

public class home extends AppCompatActivity {
    //
    PieChart pieChart;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<String> mSpinnerList = new ArrayList<>();
    private SpinnerAdapter mSpinnerAdapter;

    private ListView chartlist ;
    private ListAdapter listAdapter ;
    private ArrayList<String> list = new ArrayList<>();
    ArrayList<DataSnapshot> Appliances = new ArrayList<DataSnapshot>();
    ArrayList<String> Appliance = new ArrayList<String>();

    int arr1[] = {30,50,20};
    String arr2[] = {"first","second","third","fourth"};
    int arr3[] = {Color.parseColor("#FFA726"),Color.parseColor("#66BB6A"),Color.parseColor("#EF5350"),Color.parseColor("#29B6F6")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");

        chartlist = findViewById(R.id.chartlist);
        pieChart = findViewById(R.id.piechart);
        Spinner spinner = findViewById(R.id.spinner_spinner);
        mSpinnerList.add("Weekly");
        mSpinnerList.add("Monthly");
        mSpinnerList.add("Yearly");
        mSpinnerAdapter = new ArrayAdapter<String>(home.this, android.R.layout.simple_spinner_dropdown_item,mSpinnerList);
        spinner.setAdapter(mSpinnerAdapter);

        list.add("Bulb");
        list.add("Drill");
        list.add("Glue Gun");

//        list.add(new list_class("Bulb","#FFA726"));
//        list.add(new list_class("Drill","#66BB6A"));
//        list.add(new list_class("Glue Gun","#EF5350"));

        listAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,list);

        chartlist.setAdapter(listAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String clicked =  (String) adapterView.getItemAtPosition(i);
                Toast.makeText(home.this,clicked+" selected",Toast.LENGTH_SHORT).show();

                ArrayList<ExampleItem> examplelist = new ArrayList<>();
                String arr[] = {"Weekly","Monthly","Yearly"};
                for(int j=0 ; j< arr.length ; j++)
                {
                    if(arr[j].equals(clicked))
                    {
                        for(int k=0 ; k< arr1.length ; k++)
                            pieChart.addPieSlice(new PieModel(arr2[k],arr1[k],arr3[k]));
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Appliances");
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Iterable<DataSnapshot> iterator = snapshot.child(id).child("Daily").getChildren();
                                Collection<DataSnapshot> cn = getCollectionFromIterable(iterator);
                                Log.i("Appliances name", "V" + cn);
                                Appliances.addAll(cn);
                                for (int i = 0; i < Appliances.size(); i++) {
                                    if (!Appliances.get(i).getKey().equals("email")) {
                                        if (!Appliances.get(i).getKey().equals("name")) {
                                            if (!Appliances.get(i).getKey().equals("password")) {

                                                Log.d("Array", "A\t" + Appliances.get(i).getKey());
                                                String a = (String) Appliances.get(i).getKey();
                                                Appliance.add(Appliances.get(i).getKey());

                                            }
                                        }
                                    } else {
                                        continue;
                                    }

                                }
                                Log.d("Array", "A\t" + Appliance.get(1));

                                for (int j = 0; j < Appliance.size(); j++) {
                                    String a = Appliance.get(j);

                                 int va = Integer.valueOf(snapshot.child(id).child("Daily").child(a).child(" CuConTo").getValue(Integer.class));
                                      Log.i("message",""+va);
                                      String v = String.valueOf(va);
                                    Log.d("Array", "g" + a + v);
                                    examplelist.add(new ExampleItem(a, v));


                                }
                                mRecyclerView.setAdapter(mAdapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }

                pieChart.startAnimation();

                mRecyclerView = findViewById(R.id.homeView);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(home.this);
                mAdapter = new ExampleAdapter(examplelist);

                mRecyclerView.setLayoutManager(mLayoutManager);
//                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    // Converting iterables to Collection :
    private java.util.Collection<DataSnapshot> getCollectionFromIterable(Iterable<DataSnapshot> l) {

        Collection<DataSnapshot > cltn = new ArrayList<DataSnapshot>();

        for (DataSnapshot t : l )
            cltn.add(t);

        return cltn;

    }
}