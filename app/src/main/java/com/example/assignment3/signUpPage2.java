package com.example.assignment3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class signUpPage2 extends AppCompatActivity {

    EditText nameTxt,userTxt,passTxt;
    Button confirmBtn;
    Context con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con=this;
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_page2);
        init();
        usersDB db=new usersDB(con);
        db.open();
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=nameTxt.getText().toString();
                String pass=passTxt.getText().toString();
                String uname=userTxt.getText().toString();

                if(!db.usernameExists(uname))
                {
                    db.addNewUser(name,uname,pass);
                    if(db.verifyUser(uname,pass)) {
                        Toast.makeText(con, "Account Created ! ", Toast.LENGTH_LONG).show();

                        Intent intent=new Intent(signUpPage2.this, MainActivity2.class);
                        intent.putExtra("username",uname);
                        startActivity(intent);
                        db.close();
                        finish();
                    }



                }
                else
                {
                    Toast.makeText(con, "Username Already Exists", Toast.LENGTH_SHORT).show();

                }


            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void init()
    {
        nameTxt=findViewById(R.id.nameTxt);
        userTxt=findViewById(R.id.userNameTxt);
        passTxt=findViewById(R.id.passTxt);
        //
        confirmBtn=findViewById(R.id.confirmbtn);

    }
}
