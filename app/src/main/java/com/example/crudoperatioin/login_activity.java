package com.example.crudoperatioin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login_activity extends AppCompatActivity {
    EditText editTextemail,editTextpassword;
    TextView textViewforgot,textViewview;
    Button btnlogin;

    DatabaseReference userreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextemail = findViewById(R.id.edittxt1);
        editTextpassword = findViewById(R.id.edittxt2);
        textViewforgot = findViewById(R.id.txtforgot);
        textViewview = findViewById(R.id.txtview);
        btnlogin = findViewById(R.id.btnlogin);

        userreference = FirebaseDatabase.getInstance().getReference("user");
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertrecord();

            }
        });
        textViewview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Viewdata.class);
                startActivity(intent);
            }
        });

    }
    private void insertrecord(){
        String id = userreference.push().getKey();

        String email = editTextemail.getText().toString();
        String password = editTextpassword.getText().toString();

        UserModel userModel = new UserModel(id,email,password);
        userreference.child(id).setValue(userModel);
        Toast.makeText(getApplicationContext(),"data inserted",Toast.LENGTH_SHORT).show();


    }
}