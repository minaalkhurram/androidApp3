package com.example.assignment3;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recycledataAdapter extends RecyclerView.Adapter<recycledataAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<UserData> userDataList;
    private static final int REQUEST_CODE_EDIT = 1001;

    public String myUser;

    public usersDB mydb;

    public recycledataAdapter(Context context, ArrayList<UserData> ulist,String user,usersDB db ) {
        this.context = context;
        this.userDataList = ulist;
        this.myUser=user;
        this.mydb=db;
        mydb.open();
    }

    @NonNull
    @Override
    public recycledataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerow_item, parent, false);
        return new recycledataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recycledataAdapter.ViewHolder holder, int position) {
        UserData user = userDataList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView usernametxt,passtxt,urltxt;
        ImageButton restore;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernametxt = itemView.findViewById(R.id.usernametxt);
            passtxt = itemView.findViewById(R.id.passtxt);
            urltxt = itemView.findViewById(R.id.urltxt);
            restore=itemView.findViewById(R.id.restorebtn);


            restore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showrestoreDialog();
                }
            });

        }
        private void showrestoreDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure you want to Restore ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            mydb.restorePass(usernametxt.getText().toString(),passtxt.getText().toString(),urltxt.getText().toString());
                            setData();
                            notifyDataSetChanged();
                            Toast.makeText(context,"Restored ",Toast.LENGTH_LONG);

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and show it
            AlertDialog dialog = builder.create();
            dialog.show();
        }


        public void bind(UserData user) {
            usernametxt.setText(user.getUsername());
            passtxt.setText(user.getPassword());
            urltxt.setText(user.getUrl());



        }



    }

    public void setData() {

        userDataList = mydb.getrecycleBin(myUser);
    }


}


