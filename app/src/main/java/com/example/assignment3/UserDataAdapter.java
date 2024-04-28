package com.example.assignment3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.Nullable; // For @Nullable annotation
import android.app.Activity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;


public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<UserData> userDataList;
    private static final int REQUEST_CODE_EDIT = 1001;

    public String myUser;

    public usersDB mydb;

    public UserDataAdapter(Context context, ArrayList<UserData> ulist,String user,usersDB db ) {
        this.context = context;
        this.userDataList = ulist;
        this.myUser=user;
        this.mydb=db;
        mydb.open();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserData user = userDataList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView usernametxt,passtxt,urltxt;
        ImageButton editbtn,deletebtn;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernametxt = itemView.findViewById(R.id.usernametxt);
            passtxt = itemView.findViewById(R.id.passtxt);
            urltxt = itemView.findViewById(R.id.urltxt);

            editbtn=itemView.findViewById(R.id.editbtn);
            deletebtn=itemView.findViewById(R.id.deletebtn);

            editbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"username "+usernametxt.getText(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, editActivity.class);
                    intent.putExtra("oldpass",passtxt.getText().toString());
                    intent.putExtra("oldurl",urltxt.getText().toString());

                    ((Activity) context).startActivityForResult(intent, REQUEST_CODE_EDIT);

                }
            });
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showdeleteDialog();
                }
            });



        }
        private void showdeleteDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure you want to delete ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            mydb.deletePass(usernametxt.getText().toString(),passtxt.getText().toString(),urltxt.getText().toString());
                            setData();
                            notifyDataSetChanged();
                            Toast.makeText(context,"Deleted ! Restore from recycle bin",Toast.LENGTH_LONG);

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

        userDataList = mydb.getAllUserPasswords(myUser);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_EDIT && resultCode == Activity.RESULT_OK) {
            // Update data
            setData();
            // Notify adapter
            notifyDataSetChanged();
        }
    }
}

