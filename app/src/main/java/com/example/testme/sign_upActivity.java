package com.example.testme;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class sign_upActivity extends AppCompatActivity {

    EditText edtname ,edtnumber , newemail,newpassword ;
    Button btn_sign_up ;
    ImageView img ;
    DatabaseReference reference ;
    TextView txt_sign_in;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtname = findViewById(R.id.edtname);
        edtnumber = findViewById(R.id.edtnumber);
        newemail = findViewById(R.id.newemail);
        newpassword = findViewById(R.id.newpassword);
        btn_sign_up = findViewById(R.id.done);
        txt_sign_in = findViewById(R.id.txt_sign_in);
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateUser();
            }
        });

        txt_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),login_in.class);
                startActivity(intent);
            }
        });


        }

     void CreateUser(){
        String name  = edtname.getText().toString();
        String email = newemail.getText().toString();
        String number =edtnumber.getText().toString();
        String password = newpassword.getText().toString();
        UUID uuid = UUID.randomUUID();

        String id = uuid.toString();

        name = name.trim();
        email = email.trim();
        number = number.trim();
        password = password.trim();

        String n = "Name";
         String pass = "Password";
         String e = "Email";
         String ph = "PhoneNo";

         Log.i("success","V" + email + name + password + number);

        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!name.isEmpty()){
                if(!password.isEmpty()){
                    if(!number.isEmpty()) {
                        AddUserVal addUserVal = new AddUserVal(name,email,password,number);
                      reference = FirebaseDatabase.getInstance().getReference("Users");
                        Log.i("success","VALUE" + addUserVal.Email + addUserVal.Name + addUserVal.Password + addUserVal.Number);
                        reference.child(id).child(n).setValue(addUserVal.Name);
                        reference.child(id).child(e).setValue(addUserVal.Email);
                        reference.child(id).child(pass).setValue(addUserVal.Password);
                        reference.child(id).child(ph).setValue(addUserVal.Number);

                      Intent intent  = new Intent(sign_upActivity.this,UserDetails.class);
                      Bundle bundle = new Bundle();
                      bundle.putString("key",id);
                      intent.putExtras(bundle);
                      startActivity(intent);

                    }else edtnumber.setError("Please complete this field");
                }else newpassword.setError("Please complete this field");
            }else edtname.setError("Please complete this field");
        }else newemail.setError("Please complete this field");

  }

}

