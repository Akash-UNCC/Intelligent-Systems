package com.example.sanket.smp1;

/**
 * Created by sanket on 11/14/2017.
 */

public class Point1 { //store the x and y value of row and column of Board
    int x, y;

    public Point1(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
