package com;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MinConflict {

    public static class Board {
        Random random = new Random();

 
        int[] rows;
        static int total_states;
        static int restarts;
        /**
         * Creates a new n x n board and randomly fills it with one
         * queen in each column.
         */
        Board(int n) {
            rows = new int[n];
            randomize();
            System.out.println();
        }

        int getN()
        {
        	return rows.length;
        }
        /**
         * Randomly fills the board with one queen in each column.
         */
        void randomize() {
            for (int i = 0, n = rows.length; i < n; i++) {
                rows[i] = i;
            }
            for (int i = 0, n = rows.length; i < n; i++) {
                int j = random.nextInt(n);
                //System.out.println("j = "+ j );
                int rowToSwap = rows[i];
                rows[i] = rows[j];
                rows[j] = rowToSwap;
            }
            //System.out.println("test");
            for (int i = 0, n = rows.length; i < n; i++) {
                //System.out.print(rows[i]);
            }
            
            
        }

        /**
         * Returns the number of queens that conflict with not
         * counting the queen in column col.
         */
        int conflicts(int row, int col) {
            int count = 0;
            for (int c = 0; c < rows.length; c++) {
                if (c == col) continue;
                int r = rows[c];
                if (r == row || Math.abs(r-row) == Math.abs(c-col)) count++;
            }
            return count;
        }

        /**
         * Fills the board with a legal arrangement of queens.
         */
        void solve() {
            int moves = 0;

            // This would be a lot faster if we used arrays of ints instead.
            ArrayList<Integer> temp = new ArrayList<Integer>();

            while (true) {
            	total_states++;
                // Find worst queen
                int maxConflicts = 0;
                temp.clear();
                for (int c = 0; c < rows.length; c++) {
                    int conflicts = conflicts(rows[c], c);
                    if (conflicts == maxConflicts) {
                        temp.add(c);
                    } else if (conflicts > maxConflicts) {
                        maxConflicts = conflicts;
                        temp.clear();
                        temp.add(c);
                    }
                }

                if (maxConflicts == 0) {
                    // Checked *every* queen and found no conflicts
                	System.out.println("No. of restarts= "+restarts);
                	System.out.println("Total no. of states Generated = "+total_states);
                    return;
                }


                int wQCol =
                        temp.get(random.nextInt(temp.size()));

                // Move her to the place with the least conflicts.
                int minConflicts = rows.length;
                temp.clear();
                for (int r = 0; r < rows.length; r++) {
                    int conflicts = conflicts(r, wQCol);
                    if (conflicts == minConflicts) {
                        temp.add(r);
                    } else if (conflicts < minConflicts) {
                        minConflicts = conflicts;
                        temp.clear();
                        temp.add(r);
                    }
                }

                if (!temp.isEmpty()) {
                    rows[wQCol] =
                        temp.get(random.nextInt(temp.size()));
                }

                moves++;
                if (moves == rows.length * 2) {
                	restarts++;
                	randomize();
                    moves = 0;
                }
            }
        }

        /**
         * Prints the board
         */
       void print(PrintStream stream) {
            for (int r = 0; r < rows.length; r++) {
                for (int c = 0; c < rows.length; c++) {
                    stream.print(rows[c] == r ? 'Q' : '.');
                }
                stream.println();
            }
            System.out.println("Completed !!");
        }
    }

    /**
     * Runs the application.
     */
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the Number of Queens ");
		int boardSize = sc.nextInt();
		
        Board board = new Board(boardSize);
        System.out.println("Random Restart");
        int curr[] = new int[board.rows.length];
        curr = board.rows;
        RandomRestart.rows= curr;
        RandomRestart.exceRandomRestart(curr);
       
        System.out.println("Min Conflicts"); 
        long start = System.currentTimeMillis();
        board.solve();
       
        long stop = System.currentTimeMillis();
        long randomRestartTime = stop-start;
   
        System.out.println("Total Excecuation Time for Min Conflict= "+ randomRestartTime+" milisec");
        
        board.print(System.out);
        
        
    }
}