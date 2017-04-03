package com.pix.weibohomedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v){
        Button btn = (Button) v;
        if(btn.getText().equals("OldActivity")) {
            startActivity(new Intent(this,OldActivity.class));
        }
     }

}
