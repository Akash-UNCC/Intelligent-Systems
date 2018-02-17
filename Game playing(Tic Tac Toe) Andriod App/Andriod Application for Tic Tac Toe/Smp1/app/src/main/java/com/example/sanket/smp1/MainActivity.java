package com.example.sanket.smp1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.sanket.smp1.Point1;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static boolean multi=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.singlePlatyer).setOnClickListener(new View.OnClickListener() {//on click of singlePlayer button
            @Override
            public void onClick(View view) {
                multi=false;
                Intent intent=new Intent(MainActivity.this,FirstActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.Multiplayer).setOnClickListener(new View.OnClickListener() { //on click of Multiplayer button
            @Override
            public void onClick(View view) {
                multi=true;
                Intent intent=new Intent(MainActivity.this,FirstActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.exitGame).setOnClickListener(new View.OnClickListener() { //on click of Exit button
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });
    }
}
