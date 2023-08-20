package com.example.crudoperatioin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ListAdapter extends ArrayAdapter {
    Activity context;
    List<UserModel> userModelList;
    public ListAdapter(Activity context, List<UserModel> userModelList) {
        super(context,R.layout.viewlist,userModelList);
        this.context = context;
        this.userModelList = userModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.viewlist,null,true);
        TextView textemail = item.findViewById(R.id.txtemail);
        TextView textpassword = item.findViewById(R.id.txtpassword);


        UserModel userModel = userModelList.get(position);
        textemail.setText(userModel.getEmail());
        textpassword.setText(userModel.getPassword());

        return item;
    }

}
