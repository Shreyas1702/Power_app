package com.example.testme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.Collection;

public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //
    PieChart pieChart;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<String> mSpinnerList = new ArrayList<>();
    private SpinnerAdapter mSpinnerAdapter;

    private ListView chartlist ;
    private ListAdapter listAdapter ;
    private ArrayList<list_class> list = new ArrayList<>();
    ArrayList<DataSnapshot> Appliances = new ArrayList<DataSnapshot>();
    ArrayList<String> Appliance = new ArrayList<String>();
    TextView UserIcon ;
    NavigationView nav ;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    Toolbar bar;
    String id ;

    int arr1[] = {30,50,20};
    String arr2[] = {"first","second","third","fourth"};
    int arr3[] = {Color.parseColor("#FFF60808"),Color.parseColor("#FF00137C"),Color.parseColor("#FF42ED49"),Color.parseColor("#29B6F6")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");

        chartlist = findViewById(R.id.chartlist);
        pieChart = findViewById(R.id.piechart);
        UserIcon = findViewById(R.id.userIcon);
        Spinner spinner = findViewById(R.id.spinner_spinner);
        mSpinnerList.add("Weekly");
        mSpinnerList.add("Monthly");
        mSpinnerList.add("Yearly");
        mSpinnerAdapter = new ArrayAdapter<String>(home.this, android.R.layout.simple_spinner_dropdown_item,mSpinnerList);
        spinner.setAdapter(mSpinnerAdapter);

        bar = findViewById(R.id.toolbar);
        setSupportActionBar(bar);

        nav  = findViewById(R.id.nav_menu);
        drawer = findViewById(R.id.drawer);

        toggle = new ActionBarDrawerToggle(this,drawer,bar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Log.d("Reaching","Outside On Itemenu");

        nav.setNavigationItemSelectedListener(this);

        UserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),user_icon.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        list.add(new list_class("Bulb",Color.RED));
        list.add(new list_class("Drill",Color.BLUE));
        list.add(new list_class("Glue Gun",Color.GREEN));

       ListAdapter listAdapter = new List_Adapter(this,list);

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

                                 int va = Integer.valueOf(snapshot.child(id).child("Daily").child(a).child(" CuConpv").getValue(Integer.class));
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        Log.d("Reached","Inside On ItemMenu");
        switch(item.getItemId()){
            case R.id.menu_home:
                Toast.makeText(getApplicationContext(),"Home Panel is opened",Toast.LENGTH_LONG).show();
//                Bundle bundle = new Bundle();
//                bundle.putString("id",id);
//                getSupportFragmentManager().beginTransaction().add(R.id.fragment_conatiner,new MessageFragment()).commit();
                break;

            case R.id.menu_setting:
                Toast.makeText(getApplicationContext(),"Setting Panel is opened",Toast.LENGTH_LONG).show();
                break;

            case R.id.menu_update:
                Toast.makeText(getApplicationContext(),"Update Panel is opened",Toast.LENGTH_LONG).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}