package com.example.testme;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddAppliance extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    private RecyclerView.Adapter madapter ;
    private RecyclerView.LayoutManager mlayoutManager;
    Button btn_done ;
    EditText a_name , a_num ;

    ArrayList<recycle> List = new ArrayList<recycle>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apliance);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("keys");
        FloatingActionButton fab = findViewById(R.id.fab);
        btn_done = findViewById(R.id.btn2);
        a_name = findViewById(R.id.a_name);
        a_num = findViewById(R.id.a_num);

        mRecyclerview = findViewById(R.id.recycler_view);
        mRecyclerview.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(this);
        madapter = new Recycler_Adapter(List);
        mRecyclerview.setLayoutManager(mlayoutManager);
        mRecyclerview.setAdapter(madapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String app_name = a_name.getText().toString();
                String a = (a_num.getText()).toString();
                int app_numb = Integer.valueOf(a);
                if(!app_name.isEmpty()) {
                    if (!a.isEmpty()) {
                        List.add(new recycle(app_name,app_numb));
                        for(int i=0;i<List.size();i++){
                            recycle current_item = List.get(i);

                            Log.d("VAL","S"+current_item.getApp_numb());
                        }
                        Log.d("Array","A"+List);
                        madapter.notifyDataSetChanged();
                    }else a_num.setError("Complete this field first");
                }else a_name.setError("Complete this field first");
            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference  = FirebaseDatabase.getInstance().getReference("Appliances");
                reference.addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String d = "Daily";
                        String m = "Monthly";
                        String w = "Weekly";
                        String a = "Amount";
//                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//                        Date date = new Date();
//                        String da = format.format(date);
                        String ct =" CuConTo";
                        String cp = "CuCobpv";
                        String y = "Yearly";

                        for(int i = 0 ;i<List.size();i++){
                            recycle current_item = List.get(i);
                            String Appliance_name = current_item.getApp_name();
                            int amount = current_item.getApp_numb();
                            reference.child(id).child(d).child(Appliance_name).child(a).setValue(amount);
                            reference.child(id).child(d).child(Appliance_name).child(cp).setValue(0);
                            reference.child(id).child(d).child(Appliance_name).child(ct).setValue(0);
                            reference.child(id).child(m).child(String.valueOf(java.time.LocalDate.now())).child(Appliance_name).setValue(0);
                            reference.child(id).child(w).child(String.valueOf(java.time.LocalDate.now())).child(Appliance_name).setValue(0);
                            reference.child(id).child(y).child(String.valueOf(java.time.LocalDate.now())).child(Appliance_name).setValue(0);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent intent = new Intent(AddAppliance.this, home.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }

}