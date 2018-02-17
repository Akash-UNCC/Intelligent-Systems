package com.example.sanket.smp1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//contain minMax function as level of game is difficult
public class GUI extends AppCompatActivity {
    List<PointsAndScores> rootsChildrenScores; //store the point of each move and it's corresponding state score
    List<Point1> availablePoints; //store the list of available point
    boolean flag =true;
    int [][] idList; //store the unique id of each button in the layout
    int x=-1,y=-1;
    int  choice;
    public String Board[][]; //Mainten thecurrent status of board
    int MoveCount=0; //store the number of move
    int count=0;
    String turn="O";
    android.app.AlertDialog alert1;
    Random random;
    private Button[][]buttons; //store the button views
    public int n;
    Random randomforRow; //random object for row
    Random randomforColumn; //random object for column


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
        GUI.ButtonHandler buttonHandler = new GUI.ButtonHandler();
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


        int choice =1;
        randomforRow = new Random();
        randomforColumn = new Random();

        if(!(FirstActivity.firstMoveFlag)){  //execute below code If user select "second move" option
            Point1 p = new Point1(randomforRow.nextInt(n),randomforColumn.nextInt(n));
            MarkAMove(p,"X");
            MoveCount++;
            buttons[p.x][p.y].setText("X");
        }

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
           Intent intent = new Intent(GUI.this,FirstActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    public void updateTable(int i, int j){  //This function update move of user in the virtual tic tac toe board
        Board[i][j] ="O";
    }


    public  void MarkAMove(Point1 point, String player){//This function mark the move of user or machine in the virtual tic tac toe Board
        Board[point.x][point.y] =player;
        if(player=="X") {
            turn = "O";
        }else{
            turn="X";
        }
    }


    private void Callminmax(int i, String x) throws GUI.AlsCustomException { //This function call the min max function
        rootsChildrenScores = new ArrayList<>();
        minimax(Integer.MIN_VALUE, Integer.MAX_VALUE,i, x);

    }


    public Point1 returnBestMove() { //This function take best move amoung the all possible move
        int MAX = -100000000;
        int best = -1;

        for (int i = 0; i < rootsChildrenScores.size(); ++i) {
            if (MAX < rootsChildrenScores.get(i).score) {
                MAX = rootsChildrenScores.get(i).score;
                best = i;
            }
        }

        return rootsChildrenScores.get(best).point;
    }


    public int minimax(int alpha,int beta,int depth ,String turn) throws GUI.AlsCustomException {    //This function return the Minvalue or MaxValue

        if(beta<=alpha){//alpha beta logic:if alpha>=beta then pruning happen
            Log.d("Pruning at depth = ", ""+depth);
            if(turn == "X")
                return Integer.MAX_VALUE;
            else
                return Integer.MIN_VALUE;
        }


        if(depth==2){ //set cut off depth =2, Warning:id we increase the depth game start to hang for bard size above 3
            return calculateScore(Board,turn,depth);
        }

        if (depth ==1 ) { //Goal state found at depth=1
            if (checkEnd1()) {
                if(x!=-1 && y!=-1){
                    flag=false;
                    Log.d("demo",Board.toString());
                    buttons[x][y].setText("X");
                }
            }
        }

        List<Point1> pointsAvailable = getAvailableStates();//Array to store points which are available
        if(pointsAvailable.size()==0){
            Toast.makeText(this,"Game is Over",Toast.LENGTH_LONG);
            return 0;
        }

        int maxValue = Integer.MIN_VALUE, minValue = Integer.MAX_VALUE;


        List<Integer> scores = new ArrayList<>();//list of integer to store the score of each node

        for (int i = 0; i < pointsAvailable.size(); ++i) {
            Point1 point = pointsAvailable.get(i);

            if (turn == "X") { //X's turn select the highest from below minimax() call
                MarkAMove(point, "X");
                x=point.x;
                y=point.y;
                count++;
                int currentScore = minimax(alpha,beta,depth + 1, "O");
                maxValue = Math.max(maxValue, currentScore);
                alpha = Math.max(currentScore, alpha);

                scores.add(currentScore);

                Log.d("depth", "" + currentScore);
                if (depth == 0) {
                    rootsChildrenScores.add(new PointsAndScores(currentScore, point));
                }
            }else if (turn == "O") {//O's turn select the lowest from below minimax() call
                MarkAMove(point, "O");
                count++;
                int currentScore=minimax(alpha,beta,depth + 1, "X");
                minValue = Math.min(minValue, currentScore);
                beta = Math.min(currentScore, beta);
                scores.add(currentScore);
            }
            Board[point.x][point.y] = ""; //Reset the point

        }
        return turn == "X" ? maxValue : minValue;


    }


    private int calculateScore(String[][] board,String turn,int depth) {  //function to calcualte the score of particular state

        List<Integer> scoreList = new ArrayList<Integer>();
        int scoreListindex=0;
        int score;
        for(int j=0;j<n;j++){
            score=0;
            for(int k=0;k<n;k++){
                if(Board[j][k]=="X"){
                    score=score+10;
                }else if(Board[j][k]=="O"){
                    score=score-10;
                }
            }
            scoreList.add(score);
        }

        for(int j=0;j<n;j++){
            score=0;
            for(int k=0;k<n;k++){
                if(Board[k][j]=="X"){
                    score=score+10;
                }else if(Board[k][j]=="O"){
                    score=score-10;
                }
            }
            scoreList.add(score);
        }
        score=0;
        for(int j=0;j<n;j++){
            for(int k=0;k<n;k++){
                if(j==k) {
                    if (Board[k][j] == "X") {
                        score = score + 10;
                    } else if (Board[k][j] == "O") {
                        score = score - 10;
                    }
                }
            }
        }
        scoreList.add(score);


        score=0;
        for(int j=0;j<n;j++){
            for(int k=0;k<n;k++){
                if(j+k ==n-1) {
                    if (Board[k][j] == "X") {
                        score = score + 10;
                    } else if (Board[k][j] == "O") {
                        score = score - 10;
                    }
                }
            }
        }
        scoreList.add(score);

        if(!(depth%2==0)){
            return returnMax(scoreList);
        }else{
            return returnMin(scoreList);
        }    }

    public int returnMax(List<Integer> list) { //Return maximum valuefrom list of integer
        int max = Integer.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) > max) {
                max = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

    public int returnMin(List<Integer> list) { //Return minimum valuefrom list of integer
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) < min) {
                min = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

    public List<Point1> getAvailableStates() { //Return the available state which are not yet expanded
        availablePoints = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (Board[i][j] =="") {
                    availablePoints.add(new Point1(i, j));
                }
            }
        }
        return availablePoints;
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


    public boolean checkEnd(){ //check whether any player has win or not
        Log.d("count",""+MoveCount);
        if(HasXWon()){
            Toast.makeText(this,"Sry YOU Loose",Toast.LENGTH_LONG).show();
            return true;
        }else if(HasOWon()){
            Toast.makeText(this,"YOU Win",Toast.LENGTH_LONG).show();
            return true;
        }else if(MoveCount>=(n*n)){
            Toast.makeText(this,"IT'S DRAW!!",Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    public boolean checkEnd1(){
        if(HasXWon()){
            Toast.makeText(this,"Sry YOU Loose",Toast.LENGTH_LONG).show();
            return true;
        }else if(HasOWon()){
            Toast.makeText(this,"YOU Win",Toast.LENGTH_LONG).show();
            return true;
        }else if(MoveCount>=(n*n)){
            Toast.makeText(this,"IT'S DRAW!!",Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    class ButtonHandler implements View.OnClickListener{ //button handler to handle onclick event on button
        @Override
        public void onClick(View view) {
            int row = -1, col = -1;
            outerloop:
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (buttons[i][j] == view) {
                        if (buttons[i][j].getText().toString() != "") {
                            Toast.makeText(GUI.this, "Wrong Move", Toast.LENGTH_LONG).show();
                            return;
                        }
                        updateTable(i, j);
                        buttons[i][j].setText("O");
                        MoveCount++;
                        break outerloop;
                    }
                }
            }
            if (checkEnd()) {
                return;
            }
            int i = n / 2;
            if (FirstActivity.firstMoveFlag && Board[i][i] == "") {
                if(MoveCount==1) {
                    Board[i][i] = "X";
                    buttons[i][i].setText("X");
                    MoveCount++;
                }
            }else{
                if (!checkEnd()) {
                    try {
                        Callminmax(0, "X");
                    } catch (GUI.AlsCustomException e) {
                        e.printStackTrace();
                    }
                    if (flag) {
                        Point1 point = returnBestMove();
                        MarkAMove(point, "X");
                        buttons[point.x][point.y].setText("X");
                        MoveCount++;
                        if (checkEnd()) {
                            return;
                        }
                    }
                }
            }
        }
    }
    class AlsCustomException extends Exception
    {
        public AlsCustomException(String message)
        {
            super(message);
        }
    }

}


