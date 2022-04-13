package com.example.testme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeDetails extends AppCompatActivity {

    EditText cname,cemail,clocation,cprovider,cnumber;
    Button btnsubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_details);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");

        getid();

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
                cname.setText(name);
                cemail.setText(email);
                clocation.setText(location);
                cnumber.setText(number);
                cprovider.setText(provider);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = cname.getText().toString();
                        String email = cemail.getText().toString();
                        String provider =cprovider.getText().toString();
                        String number = cnumber.getText().toString();
                        String location = clocation.getText().toString();

                        name = name.trim();
                        email = email.trim();
                        number = number.trim();
                       provider = provider.trim();
                       location = location.trim();

                       if(!name.isEmpty()){
                           if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                               if(!number.isEmpty()){
                                   if(!provider.isEmpty()){
                                       if(!location.isEmpty()){
                                           reference.child(id).child("Name").setValue(name);
                                           reference.child(id).child("Email").setValue(email);
                                           reference.child(id).child("PhoneNo").setValue(number);
                                           reference.child(id).child("ElecProvider").setValue(provider);
                                           reference.child(id).child("ID").child("Home").child("Location").setValue(location);
                                       }else{
                                           clocation.setError("Please complete this field");
                                       }
                                   }else{
                                       cprovider.setError("Please complete this field");
                                   }
                               }else{
                                   cnumber.setError("Please complete this field");
                               }
                           }else{
                               cnumber.setError("Please complete this field");
                           }
                       }else{
                           cnumber.setError("Please complete this field");
                       }

                        Toast.makeText(getApplicationContext(),"Your details Have Been Changed Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),user_icon.class);
                        intent.putExtra("id",id);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }

    void getid(){
        cname = findViewById(R.id.edtcname);
        cemail = findViewById(R.id.edtcemail);
        cnumber = findViewById(R.id.edtcnumber);
        clocation = findViewById(R.id.edtclocation);
        cprovider = findViewById(R.id.edtcprovider);
        btnsubmit = findViewById(R.id.btn_submit);
    }
}