package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclebin extends AppCompatActivity {

    private RecyclerView myrecycler;
    private recycledataAdapter adapter;
    public ArrayList<UserData> userpasswords;
    String myUser;

    usersDB mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recyclebin);

        init();
        //getExtra();
        mydb=new usersDB(this);
        mydb.open();
        myrecycler.setLayoutManager(new LinearLayoutManager(this));
        userpasswords=new ArrayList<>();
        getExtra();
        userpasswords=mydb.getrecycleBin(myUser);


        adapter=new recycledataAdapter(this,userpasswords,myUser,mydb);
        myrecycler.setAdapter(adapter);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void init()
    {
        myrecycler=findViewById(R.id.recyclerView);

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