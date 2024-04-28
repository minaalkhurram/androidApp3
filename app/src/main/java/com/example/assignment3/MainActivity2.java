package com.example.assignment3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    ImageButton arrowBtn;
    TextView userDisplay,passtxt,addPasstxt,binTxt;



    usersDB mydb;
    String myUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        init();
        getExtra();
        mydb=new usersDB(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        arrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLogoutDialog();
            }
        });


          addPasstxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity2.this, addpass2.class);
                intent.putExtra("username", myUser);
                startActivity(intent);
            }
        });
          passtxt.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent=new Intent(MainActivity2.this, userPasswords.class);
                  intent.putExtra("username", myUser);
                  startActivity(intent);
              }
          });

          binTxt.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent=new Intent(MainActivity2.this, recyclebin.class);
                  intent.putExtra("username", myUser);
                  startActivity(intent);
              }
          });

    }

    public void init()
    {
        userDisplay=findViewById(R.id.userDisplay);
        arrowBtn=findViewById(R.id.arrow);
        passtxt=findViewById(R.id.passwordstxt);
        addPasstxt=findViewById(R.id.addTxt);
        binTxt=findViewById(R.id.binTxt);

    }
    public void getExtra()
    {
        Intent intent=getIntent();
        if(intent!=null)
        {
            myUser=intent.getStringExtra("username");
            if(myUser!=null)
            {

                userDisplay.setText(myUser);
            }
        }
    }
    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                        startActivity(intent);
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
}