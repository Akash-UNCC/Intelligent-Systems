package com;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RandomRestart {

	Random random = new Random();
	static int boardSize;
	static int[] rows;
	static int restarts;
	static int total_states=0;

	RandomRestart(int n) {
		rows = new int[n];
		randomize();
    }
	

	RandomRestart(int[] rows) {
	this.rows = rows;
    }

	void randomize() {
		for (int i = 0, n = rows.length; i < n; i++) {
			rows[i] = i;
		}
		for (int i = 0, n = rows.length; i < n; i++) {
			int j = random.nextInt(n);
			int rowToSwap = rows[i];
			rows[i] = rows[j];
			rows[j] = rowToSwap;
		}

		 /* for (int i = 0, n = rows.length; i < n; i++) {
              System.out.print(rows[i]);
              }*/
		  //System.out.println("--");
		  restarts++;
	}

	/**
	 * Returns the number of queens that conflict with (row,col), not counting
	 * the queen in column col.
	 */
	int getHeuristic(int array[]) {
		int weight = 0;
		int queen;
		for (queen = 0; queen < array.length; queen++) { // for each queen
			int nextqueen;
			for (nextqueen = queen + 1; nextqueen < array.length; nextqueen++) {
				if (array[queen] == array[nextqueen]
						|| Math.abs(queen - nextqueen) == Math.abs(array[queen] - array[nextqueen])) { // if
																										// conflict
					weight++;
				}
			}
		}
		return weight;
	}

	/**
	 * Fills the board with a legal arrangement of queens.
	 */
	void solve(int array[]) {

		int weight = getHeuristic(array);
		if (weight == 0) {

			//print(array);
            System.out.println("No. of restarts= "+restarts);
            System.out.println("Total no. of states Generated = "+total_states);
            RandomRestart.print(System.out);
		} else {

			ArrayList nextrow = new ArrayList();
			ArrayList nextcol = new ArrayList();
			nextrow.clear();
			nextcol.clear();
			int nextweight = weight;
			int queen;
			for (queen = 0; queen < array.length; queen++) { // for each
																// queen/row -|
				int origcol = array[queen]; // save the original column |
				int validcol; // |--- searching the whole board
				for (validcol = 0; validcol < array.length; validcol++) {   // for
																			// each
																			// valid
																			// column
																			// |
					if (validcol != origcol) { // not including the current one
												// -|
						array[queen] = validcol; // place the queen in the next
													// column
						int newweight = getHeuristic(array); // get the weight of
															// the modified
															// board
						if (newweight < nextweight) { // if it's a better move
							int i;
							nextrow.clear();
							nextcol.clear();
							nextrow.add(queen);
							nextcol.add(validcol);
							nextweight = newweight;
						} // else if (newweight == nextweight){

					}
				}
				array[queen] = origcol;
			}
			// once we're done searching the board
			if (nextrow.size() != 0 && nextcol.size() != 0) { // if we've found
				int i = 0;
				array[(int) nextrow.get(i)] = (int) nextcol.get(i);
				total_states++;// make it
				solve(array);
				
			} else {
				int i;
				RandomRestart board = new RandomRestart(boardSize);
				solve(rows);
			}
		}
	}

 /*	void print(int[] array) {
		for (int r = 0; r < array.length; r++) {
			System.out.println(array[r]);
		}
	}*/
	
	static void print(PrintStream stream) {
            for (int r = 0; r < rows.length; r++) {
                for (int c = 0; c < rows.length; c++) {
                    stream.print(rows[c] == r ? 'Q' : '.');
                }
                stream.println();
            }
            System.out.println("Completed !!");
        }
    
	
	public static void exceRandomRestart(int[] rows) {
		Scanner sc = new Scanner(System.in);
		//System.out.println("Enter the Board Size");
		//boardSize = sc.nextInt();
		boardSize = rows.length;
		RandomRestart board = new RandomRestart(rows);
	
		long start = System.currentTimeMillis();
		board.solve(rows);
		long stop = System.currentTimeMillis();
		long randomRestartTime = stop - start;
		System.out.println("Execution Time for Hill Climbing with Random Restart= "+ randomRestartTime+" milisec");
		
		System.out.println(" ");
	}
}