package com.example.testme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDetails extends AppCompatActivity {

    EditText edtlocation , edtprovider;
    RadioGroup rdgroup;
    RadioButton rdbtn;
    Button btn_sign_up ;
    DatabaseReference reference ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("key");

        edtlocation = findViewById(R.id.edtlocation);
        edtprovider = findViewById(R.id.edtprovider);
        btn_sign_up = findViewById(R.id.btn_sign_up);
        rdgroup = findViewById(R.id.radioGroup);

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int radioId = rdgroup.getCheckedRadioButtonId();
                rdbtn = findViewById(radioId);
                String radiobtn = rdbtn.getText().toString();
                Log.i("rdbutton_val","r"+radiobtn);
                ExtraInfo(id,radiobtn);
                Intent intent = new Intent(getApplicationContext(),AddAppliance.class);
                Bundle bundle = new Bundle();
                bundle.putString("keys",id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

         void ExtraInfo(String id,String radiobtn){
            int charperut = 0;
            String location = edtlocation.getText().toString();
            String provider = edtprovider.getText().toString();
            String u = "ID";
            String h = "ElecProvider";
            String l ="Location";
            String c = "Charperut";
            String i = "Id";
            reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Addval addval = new Addval(location,provider,charperut);
                       reference.child(id).child(h).setValue(addval.provider);
                       reference.child(id).child(u).child(radiobtn).child(l).setValue(addval.location);
                       reference.child(id).child(u).child(radiobtn).child(c).setValue(addval.charperut);
                       reference.child(id).child(u).child(radiobtn).child(i).setValue(id);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}