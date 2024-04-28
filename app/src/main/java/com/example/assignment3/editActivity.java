package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class editActivity extends AppCompatActivity {

    Button confirmbtn;
    EditText pass,username,url;

    usersDB mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);
        init();
        mydb=new usersDB(this);

        mydb.open();

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                mydb.updatePassword(intent.getStringExtra("oldpass"),intent.getStringExtra("oldurl"),username.getText().toString(),pass.getText().toString(),url.getText().toString());
                setResult(RESULT_OK);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void init()
    {
        pass=findViewById(R.id.passTxt);
        username=findViewById(R.id.userNameTxt);
        confirmbtn=findViewById(R.id.confirmbtn);
        url=findViewById(R.id.urlTxt);
    }

}