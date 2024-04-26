package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText userTxt,passTxt;
    Button signupBtn,loginBtn;

    public usersDB mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        init();
        mydb=new usersDB(this);
        mydb.open();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Log.d("Tag", "Message to print");


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, signUpPage2.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = userTxt.getText().toString();
                String pass = passTxt.getText().toString();
                if (!uname.isEmpty() && !pass.isEmpty()) {
                    if (mydb.verifyUser(uname,pass)) {
                        Intent intent = new Intent(MainActivity.this, UserActivity.class);
                        intent.putExtra("username", uname);
                        startActivity(intent);
                        mydb.close();

                    } else {
                        Toast.makeText(MainActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void init()
    {

        userTxt=findViewById(R.id.userTxt);
        passTxt=findViewById(R.id.passTxt);
        loginBtn=findViewById(R.id.loginbtn);
        signupBtn=findViewById(R.id.singUpbtn);
    }
}

