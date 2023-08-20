package com.example.crudoperatioin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Viewdata extends AppCompatActivity {
    ListView listView;
    List<UserModel> userModelList;
    DatabaseReference userreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdata);
        listView = findViewById(R.id.list);
        userModelList = new ArrayList<>();
        userreference = FirebaseDatabase.getInstance().getReference("user");
        userreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userModelList.clear();
                for (DataSnapshot usersnap : snapshot.getChildren()){
                    UserModel userModel = usersnap.getValue(UserModel.class);
                    userModelList.add(userModel);

                    ListAdapter listAdapter = new ListAdapter(Viewdata.this,userModelList);
                    listView.setAdapter(listAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                UserModel userModel=userModelList.get(position);

                deletedilog(userModel.getId(),userModel.getEmail());
                return false;

            }

        });
    }
    private void deletedilog(String id, String email){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogview = inflater.inflate(R.layout.deletedilogue,null);
        builder.setView(dialogview);
        EditText emails = dialogview.findViewById(R.id.edemail);
        EditText passwords = dialogview.findViewById(R.id.edpassword);
        Button delete = dialogview.findViewById(R.id.btndelete);
        Button update = dialogview.findViewById(R.id.btnupdate);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleterecord(id);
                userModelList.remove(id);
                alertDialog.dismiss();

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =emails.getText().toString();
                String password = passwords.getText().toString();
                updaterecord(id,email,password);
                alertDialog.dismiss();

            }
        });

    }
    private  void deleterecord(String id){
        DatabaseReference userreference = FirebaseDatabase.getInstance().getReference("user").child(id);
        Task<Void> task = userreference.removeValue();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"deleted",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "not deleted", Toast.LENGTH_SHORT).show();


            }
        });

    }
    private  void updaterecord(String id,String email,String password){
        DatabaseReference userreference = FirebaseDatabase.getInstance().getReference("user").child(id);
        UserModel userModel = new UserModel(id,email,password);
        userreference.setValue(userModel);

    }

}