package com.example.sanket.smp1;

/**
 * Created by sanket on 11/14/2017.
 */

public class PointsAndScores { //store the x and y value of row and column of Board and it's score

    int score;
    Point1 point;

    PointsAndScores(int score, Point1 point) {
        this.score = score;
        this.point = point;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Point1 getPoint() {
        return point;
    }

    public void setPoint(Point1 point) {
        this.point = point;
    }
}
