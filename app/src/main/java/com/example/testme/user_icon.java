package com.example.testme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class user_icon extends AppCompatActivity {

    TextView txtname,txtemail,txtlocation,txtnumber,txtprovider,change;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_icon);

        Bundle bundle = getIntent().getExtras();
        String id  = bundle.getString("id");
        txtname = findViewById(R.id.txtname);
        txtemail = findViewById(R.id.txtemail);
        txtlocation = findViewById(R.id.txtlocation);
        txtnumber = findViewById(R.id.txtnumber);
        txtprovider = findViewById(R.id.txtprovider);
        change = findViewById(R.id.change_details);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 String name = snapshot.child(id).child("Name").getValue(String.class);
                 String email = snapshot.child(id).child("Email").getValue(String.class);
                 String provider = snapshot.child(id).child("ElecProvider").getValue(String.class);
                 String number = snapshot.child(id).child("PhoneNo").getValue(String.class);
                 String location = snapshot.child(id).child("ID").child("Home").child("Location").getValue(String.class);
                Log.d("usericons","V"+name+email+provider+number+location);
                 txtname.setText(name);
                 txtemail.setText(email);
                 txtlocation.setText(location);
                 txtnumber.setText(number);
                 txtprovider.setText(provider);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ChangeDetails.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });



    }
}