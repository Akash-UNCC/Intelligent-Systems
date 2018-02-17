package com.example.sanket.smp1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class Multiplayer extends AppCompatActivity {
    int [][] idList; //store the unique id of each button in the layout
    int x=-1,y=-1;
    int turn=1;
    public String Board[][];//Mainten thecurrent status of board
    int MoveCount=0;
    Random random;
    private Button[][]buttons;//store the button views
    public int n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        n=FirstActivity.boardSize1;
        Board = new String[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                Board[i][j] ="";
            }
        }

        random = new Random();
        Point size = new Point();
        idList = new int[n][n];
        getWindowManager().getDefaultDisplay().getSize(size);

        int wX= size.x/n;
        int wy= size.y/n;
        wy=wy-40;
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(n);
        gridLayout.setRowCount(n);
        buttons = new Button[n][n];
        Multiplayer.ButtonHandler buttonHandler = new Multiplayer.ButtonHandler();
        for(int row=0;row<n;row++){
            for(int col=0;col<n;col++){
                buttons[row][col] = new Button(this);
                buttons[row][col].setId(random.nextInt(n*n));
                buttons[row][col].setTextSize(wX/n);
                buttons[row][col].setTypeface(buttons[row][col].getTypeface(), Typeface.BOLD);
                buttons[row][col].setOnClickListener(buttonHandler);
                idList[row][col]=buttons[row][col].getId();
                Log.d("demo",buttons[row][col].getId()+"I am pressed");
                gridLayout.addView(buttons[row][col],wX,wy);
            }
        }
        setContentView(gridLayout);
    }
    public void updateTable(int i,int j,String turn){
        Board[i][j] =turn;
    }

    public boolean HasXWon(){

        boolean flag =true;
        for(int j=0;j<n;j++){
            for(int k=0;k<n;k++){
                if(Board[j][k]=="X"){

                }else{
                    flag=false;

                }
            }
            if(flag){
                return true;
            }else{
                flag=true;
            }

        }

        flag =true;
        for(int j=0;j<n;j++){
            for(int k=0;k<n;k++){
                if(Board[k][j]=="X"){

                }else{
                    flag=false;

                }
            }
            if(flag){
                return true;
            }else{
                flag=true;
            }

        }
        flag =true;

        for(int j=0;j<n;j++){
            for(int k=0;k<n;k++){
                if(j==k){
                    if(Board[j][k]=="X"){

                    }else{
                        flag=false;

                    }
                }
            }
        }
        if(flag){
            return true;
        }

        flag =true;
        for(int j=0;j<n;j++){
            for(int k=0;k<n;k++){
                if(j+k==n-1){
                    if(Board[j][k]=="X"){

                    }else{
                        flag=false;
                    }
                }
            }
        }

        if(flag){
            return true;
        }

        return false;
    }


    public boolean checkEnd(){
        if(HasXWon()){
            Toast.makeText(Multiplayer.this,"Player X has been Win",Toast.LENGTH_LONG).show();
            return true;
        }else if(HasOWon()){
            Toast.makeText(Multiplayer.this,"Player O has been Win",Toast.LENGTH_LONG).show();
            return true;
        }else if(MoveCount>=(n*n)){
            Toast.makeText(this,"IT'S DRAW!!",Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    public boolean HasOWon(){
        boolean flag =true;

        for(int j=0;j<n;j++){
            for(int k=0;k<n;k++){
                if(Board[j][k]=="O"){

                }else{
                    flag=false;

                }
            }
            if(flag){
                return true;
            }else{
                flag=true;
            }

        }

        flag =true;
        for(int j=0;j<n;j++){
            for(int k=0;k<n;k++){
                if(Board[k][j]=="O"){

                }else{
                    flag=false;

                }
            }
            if(flag){
                return true;
            }else{
                flag=true;
            }

        }

        flag =true;

        for(int j=0;j<n;j++){
            for(int k=0;k<n;k++){
                if(j==k){
                    if(Board[j][k]=="O"){

                    }else{
                        flag=false;
                    }
                }
            }
        }
        if(flag){
            return true;
        }

        flag =true;
        for(int j=0;j<n;j++){
            for(int k=0;k<n;k++){
                if(j+k==n-1){
                    if(Board[j][k]=="O"){

                    }else{
                        flag=false;
                    }
                }
            }
        }

        if(flag){
            return true;
        }

        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.back) {
            Intent intent = new Intent(Multiplayer.this,FirstActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private class ButtonHandler implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            int row=-1,col=-1;
            outerloop:
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    if(buttons[i][j] ==view){
                        if(buttons[i][j].getText().toString()!=""){
                            Toast.makeText(Multiplayer.this,"Wrong Move",Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(turn==1) {
                            MoveCount++;
                            turn=2;
                            updateTable(i, j,"X");
                            buttons[i][j].setText("X");
                            if(checkEnd()){
                                return;
                            }
                        }else {
                            turn=1;
                            MoveCount++;
                            updateTable(i, j,"O");
                            buttons[i][j].setText("O");
                            if(checkEnd()){
                                return;
                            }
                        }
                        break outerloop;
                    }
                }
            }

        }
    }
}
