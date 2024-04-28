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

public class addpass2 extends AppCompatActivity {

    EditText usernameTxt,passTxt,urlTxt;
    Button confirmBtn;

    String myUser;
    usersDB mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addpass2);

        init();
        getExtra();
        mydb=new usersDB(this);
        mydb.open();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname=usernameTxt.getText().toString();
                String pass=passTxt.getText().toString();
                String url=urlTxt.getText().toString();
                if(!uname.isEmpty()&&!pass.isEmpty()&&!url.isEmpty())
                {

                    mydb.addPassword(myUser,uname,pass,url);
                    Toast.makeText(addpass2.this,"Password Added ",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(addpass2.this, MainActivity2.class);
                    intent.putExtra("username",myUser);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void init()
    {
        usernameTxt=findViewById(R.id.userNameTxt);
        passTxt=findViewById(R.id.passTxt);
        urlTxt=findViewById(R.id.urlTxt);
        confirmBtn=findViewById(R.id.confirmbtn);

    }
    public void getExtra()
    {
        Intent intent=getIntent();
        if(intent!=null)
        {
            myUser=intent.getStringExtra("username");
        }
    }

}