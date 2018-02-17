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

import java.util.Random;

//NO min max function as Level of game is Easy
public class EasyLevel extends AppCompatActivity {
    int [][] idList; //store the unique id of each button in the layout
    int x=-1,y=-1;
    String turn="X";
    public String Board[][]; //Mainten thecurrent status of board
    int MoveCount=0;
    Random random;
    private Button[][]buttons; //store the button views
    public int n;
    Random randomforRow; //random object for row
    Random randomforColumn;  //random object for column

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

        //2-D array to store button view
        buttons = new Button[n][n];
        EasyLevel.ButtonHandler buttonHandler = new EasyLevel.ButtonHandler();
        for(int row=0;row<n;row++){
            for(int col=0;col<n;col++){
                buttons[row][col] = new Button(this);
                buttons[row][col].setTextSize(wX/n);
                buttons[row][col].setTypeface(buttons[row][col].getTypeface(), Typeface.BOLD);
                buttons[row][col].setId(random.nextInt(n*n));
                buttons[row][col].setOnClickListener(buttonHandler);
                idList[row][col]=buttons[row][col].getId();
                Log.d("demo",buttons[row][col].getId()+"I am pressed");
                gridLayout.addView(buttons[row][col],wX,wy);
            }
        }
        setContentView(gridLayout);
        randomforRow = new Random();
        randomforColumn = new Random();

        //execute below code If user select "second move" option
        if(!(FirstActivity.firstMoveFlag)){
            Point1 p = new Point1(randomforRow.nextInt(n),randomforColumn.nextInt(n));
            MarkAMove(p,"X");
            turn="O";
            MoveCount++;
            buttons[p.x][p.y].setText("X");
        }

    }
    //This function mark the move of user or machine in the virtual tic tac toe Board
    public  void MarkAMove(Point1 point, String player){
        Board[point.x][point.y] =player;
        if(player=="X") {
            turn = "O";
        }else{
            turn="X";
        }
    }
    //This function update move of user in the virtual tic tac toe board
    public void updateTable(int i,int j,String turn){
        Board[i][j] =turn;
        if(turn=="X"){
            turn="O";
        }else{
            turn="X";
        }
    }

    public boolean HasXWon(){ //function return "true" if Player X has won else return false

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


    public boolean checkEnd(){ //check whether any player has win or not
        if(HasXWon()){
            Toast.makeText(EasyLevel.this,"Sorry You Loose",Toast.LENGTH_LONG).show();
            return true;
        }else if(HasOWon()){
            Toast.makeText(EasyLevel.this,"Congrats You Win",Toast.LENGTH_LONG).show();
            return true;
        }else if(MoveCount>=(n*n)){
            Toast.makeText(this,"IT'S DRAW!!",Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    public boolean HasOWon(){ //function return "true" if Player O has won else return false
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
            Intent intent = new Intent(EasyLevel.this,FirstActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private class ButtonHandler implements View.OnClickListener{ //button handler to handle onclick event on button

        @Override
        public void onClick(View view) {
            int row=-1,col=-1;
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    if(buttons[i][j] ==view){
                        if(buttons[i][j].getText().toString()!=""){
                            Toast.makeText(EasyLevel.this,"Wrong Move",Toast.LENGTH_LONG).show();
                            return;
                        }
                            MoveCount++;
                            updateTable(i, j,"O");
                            buttons[i][j].setText("O");
                            if(checkEnd()){
                                return;
                            }
                                Point1 p = new Point1(randomforRow.nextInt(n),randomforColumn.nextInt(n));
                                while (Board[p.x][p.y]!=""){
                                    Point1 p1 = new Point1(randomforRow.nextInt(n),randomforColumn.nextInt(n));
                                    p=p1;
                                }
                                MarkAMove(p,"X");
                                turn="O";
                                MoveCount++;
                                buttons[p.x][p.y].setText("X");
                            if(checkEnd()){
                            return;
                            }
                    }
                }
            }

        }
    }
}
