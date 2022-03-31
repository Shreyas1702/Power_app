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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login_in extends AppCompatActivity {

    EditText edtemail , edtpassword;
    Button btn_login ;
    TextView txt_sign_up;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);

        edtemail = findViewById(R.id.edtemail);
        edtpassword = findViewById(R.id.edtpassword);

        btn_login = findViewById(R.id.btn_login);
        txt_sign_up = findViewById(R.id.text_sign_up);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });

        txt_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),sign_upActivity.class);
                startActivity(intent);
            }
        });

    }

    void check(){
        String email = edtemail.getText().toString();
        String password = edtpassword.getText().toString();
        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!password.isEmpty()) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot datas : snapshot.getChildren()) {
                            String classnames = datas.getKey();
                            String ref1 = snapshot.child(classnames).child("Email").getValue(String.class);
                            String ref2 = snapshot.child(classnames).child("Password").getValue(String.class);
                            if (ref1.equals(email)) {
                                if (ref2.equals(password)) {
                                    Intent intent = new Intent(login_in.this,home.class);
                                    intent.putExtra("id",classnames);
                                    startActivity(intent);
                                } else edtpassword.setError("PASSWORD INCORRECT");
                            } else edtemail.setError("INCORRECT E-MAIL");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            } else {
                edtpassword.setError("EMPTY FIELDS ARE NOT ALLOWED");
            }
        }
        else if(!email.isEmpty()) {
            edtemail.setError("EMPTY FIELDS ARE NOT ALLOWED");
        }
        else{
            edtemail.setError("ENTER PROPER E-MAIL ID");
        }
    }
}