package com.nank.shas.shas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView start,help,about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (ImageView)findViewById(R.id.start);
        help =(ImageView)findViewById(R.id.help);
        about = (ImageView) findViewById(R.id.about);
        start.setOnClickListener(this);
        help.setOnClickListener(this);
        about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       //on start automation
        if(v==start){
            Intent intent = new Intent(MainActivity.this,StartAutomation.class);
            startActivity(intent);
            finish();
        }
        else if(v==about){

        }
        else if(v==help){

        }
        else Toast.makeText(MainActivity.this,"Select valid input",Toast.LENGTH_SHORT).show();


    }
}
