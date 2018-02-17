package com.example.sanket.smp1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.nio.file.FileStore;

public class FirstActivity extends AppCompatActivity {

    public static int boardSize1;
    boolean moveselectedFlag=false;
    boolean levelSelected=false;
    String levelofDifficulty="";
    public static boolean firstMoveFlag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        float[] hsv = new float[3];
        if(MainActivity.multi){
            //MainActivity.multi=false;
            findViewById(R.id.Single_Player).setVisibility(View.GONE);
            findViewById(R.id.Multi_Player).setVisibility(View.VISIBLE);
            findViewById(R.id.move).setVisibility(View.GONE);
            findViewById(R.id.levelDifficulty).setVisibility(View.GONE);
        }else{
            findViewById(R.id.Single_Player).setVisibility(View.VISIBLE);
            findViewById(R.id.Multi_Player).setVisibility(View.GONE);
        }
        RadioGroup rg = (RadioGroup) findViewById(R.id.levelDifficulty);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //on selection of level difficluty
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i==R.id.easy){
                    levelSelected = true;
                    levelofDifficulty="easy";
                    Log.d("demo","Hii eay");
                }else if(i==R.id.medium){
                    levelSelected = true;
                    levelofDifficulty="medium";
                }else if(i==R.id.difficult){
                    levelSelected = true;
                    levelofDifficulty="difficult";
                }
            }
        });
        final EditText boardSize=(EditText)findViewById(R.id.boardSize);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FirstActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(boardSize.getText().toString().trim().equalsIgnoreCase("")){ //check whether Board size is not empty
                    Toast.makeText(FirstActivity.this,"Please Enter the Board Size",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(findViewById(R.id.move).getVisibility()==View.GONE)) { //check whether the move is selected or not
                    if (!moveselectedFlag) {
                        Toast.makeText(FirstActivity.this, "Please select the Move", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if(!levelSelected && (!(findViewById(R.id.levelDifficulty).getVisibility()==View.GONE))){ //check whether the difficulty level is selected or not
                    Toast.makeText(FirstActivity.this,"Please select the level of Difficulty",Toast.LENGTH_SHORT).show();
                    return;
                }
                boardSize1 = Integer.parseInt(boardSize.getText().toString());
                if(boardSize1%2==0){
                    Toast.makeText(FirstActivity.this, "Please Enter the Board size as odd number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(MainActivity.multi){
                   // MainActivity.multi=false;
                    Intent intent = new Intent(FirstActivity.this, Multiplayer.class);
                    startActivity(intent);

                }else {
                    if(levelofDifficulty=="easy"){
                        Intent intent = new Intent(FirstActivity.this, EasyLevel.class);
                        startActivity(intent);
                    }else if(levelofDifficulty=="medium"){
                        Intent intent = new Intent(FirstActivity.this, GUI.class);
                        startActivity(intent);
                    }else if(levelofDifficulty=="difficult"){
                        Intent intent = new Intent(FirstActivity.this, GUI.class);
                        startActivity(intent);
                    }
                }

            }
        });

        RadioGroup rGroup = (RadioGroup)findViewById(R.id.move);

        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //select the move
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                if(R.id.firstMove==i){
                    firstMoveFlag=true;
                    moveselectedFlag=true;
                }else if(R.id.secondMove==i){
                    moveselectedFlag=true;
                    firstMoveFlag=false;
                }
            }
        });
    }
}
