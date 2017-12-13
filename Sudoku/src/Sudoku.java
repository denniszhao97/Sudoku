/**
 * This is the algorithm part for the Sudoku game. 
 * In general, a 9*9 grid is created with 9 3*3 subsections. 
 * In each section, the rows and columns must use numbers in the range of 1-9, 
 * and each number only can be used once. 
 * 
 * @author Zhonghao Zhao & Tianjiao Pu
 */
public class Sudoku implements Cloneable {
	
    int[][] sudokuTable; // initialize the sudoku table as a matrix

    /**
     * Set up the sudoku table 
     * @param table
     */
    public Sudoku(int[][] table) {
    			sudokuTable = table;    		
    }
   
    /**
     * Copy the sudoku table with all the features
     * @return sudokuTable
     */
    public int[][] getArray() {
	     return sudokuTable.clone();
    }

    /**
     * Obtain the array 
     *
     * @param orginalReference 
     * @return sudokuTable(true)， getArray(false)
     */
    public int[][] getArray(boolean orginalReference) {
    		if (orginalReference) {
         	    return sudokuTable;
    		} else {
    			return getArray();
    		}
    }
    
    /**
     * copy the new sudoku 
     *
     * @return sudoku 
     */
    public Sudoku copy() {
    		return new Sudoku(getArray());
    	}
    
    /**
     * Solve the sudoku
     * Backtracking Algorithm is applied in this case.
     *
     * @return If the solution is valid, return the new sudoku object
     * otherwise, return null 
     */
    public Sudoku solveSudoku() {
    	
	if (!isCorrect(true)) { 
	    return null;
		} else if (isCorrect(false)) { 
			return this.copy();
			} else { 
				int length = 0;
				int[][] zeroPosition = new int[81][2]; 
				// in the worst case, there are 81 zeros
				
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						if (sudokuTable[i][j] == 0) {
							zeroPosition[length][0] = i;
							zeroPosition[length][1] = j;
							length++;
						}
					}
				}
				
				//copy a new sudoku(buffer) to work on the solution
				Sudoku tempSudoku = this.copy(); 
				int[][] tempArray = tempSudoku.getArray(true);
	    
				for (int i = 0; i < length && i >= 0; i++) {
					for (int j = tempArray[zeroPosition[i][0]][zeroPosition[i][1]] + 1; j <= 9; j++) {
						tempArray[zeroPosition[i][0]][zeroPosition[i][1]] = j;
						if (tempSudoku.isCorrect(zeroPosition[i][0], zeroPosition[i][1])) { 
							//if the values are correct, stop.
							break;
						}
	    			
						if (j == 9 && !tempSudoku.isCorrect(zeroPosition[i][0], zeroPosition[i][1])) { 
							//if the values are not correct, and all numbers are used, 
							//adjust from the last value that is not 9 
							tempArray[zeroPosition[i][0]][zeroPosition[i][1]] = 0;
							i--;
							for (; i >= 0; i--) {
								//if 9 is reached, change it into 0
								if (tempArray[zeroPosition[i][0]][zeroPosition[i][1]] == 9) { 
									tempArray[zeroPosition[i][0]][zeroPosition[i][1]] = 0;
								} else {
									break;
								}
							}
							i--;
							break;
						}
					}
				}
				if (tempSudoku.isCorrect(false)) {
					return tempSudoku;
				}
			}
		return null;
    	}

    /**
     * Set values for each position 
     *
     * @param n the value 
     * @param x the column 
     * @param y the row 
     */
    public void setArrayElement(int n, int x, int y) {
	    sudokuTable[x][y] = n;
    }

    /**
     * check if the sudoku is correct. 
     *
     * @param skipZero 
     * @return if zero exits, return false 
     */
    public boolean isCorrect(boolean skipZero) {
    		if (!skipZero && !AllFilled()) {
    			return false;
    		}
    		for (int i = 0; i < 9; i++) {
    			for (int j = 0; j < 9; j++) {
    				if (!isCorrect(i, j)) {
    					return false;
    				}
    			}
    		}
    		return true;
    	}

    /**
     * check whether the value at position (m,n) is correct or not. 
     *
     * @param m column 
     * @param n row 
     * @return true/false 
     */
    public boolean isCorrect(int m, int n) {
    		if (sudokuTable[m][n] == 0) {
    			return true;
    		}
    		
    		for (int i = 0; i < 9; i++) {
    			if (i == n) {
    				continue;
    			}  			
    			if (sudokuTable[m][i] == sudokuTable[m][n]) {
    				return false;
    			}
    		}

    		for (int i = 0; i < 9; i++) {
    			if (i == m) {
    				continue;
    			}
    			if (sudokuTable[i][n] == sudokuTable[m][n]) {
    				return false;
    			}
    		}

    		// check the larger 3*3 region but assign each subsection a id"a"
		int a; 
		if (m >= 0 && m <= 2 && n >= 0 && n <= 2) {
		    a = 0;
		} else if (m >= 0 && m <= 2 && n >= 3 && n <= 5) {
		    a = 1;
		} else if (m >= 0 && m <= 2 && n >= 6 && n <= 8) {
		    a = 2;
		} else if (m >= 3 && m <= 5 && n >= 0 && n <= 2) {
		    a = 3;
		} else if (m >= 3 && m <= 5 && n >= 3 && n <= 5) {
		    a = 4;
		} else if (m >= 3 && m <= 5 && n >= 6 && n <= 8) {
		    a = 5;
		} else if (m >= 6 && m <= 8 && n >= 0 && n <= 2) {
		    a = 6;
		} else if (m >= 6 && m <= 8 && n >= 3 && n <= 5) {
		    a = 7;
		} else {
		    a = 8;
		}
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int x = i + (a / 3) * 3;
				int y = j + (a % 3) * 3;
				if (x == m && y == n) {
					continue;
				}
				if (sudokuTable[x][y] == sudokuTable[m][n]) {
					return false;
				}
			}
		}
	return true;
    }

    /**
     * check if the table is filled. 
     *
     * @return true/false
     */
    public boolean AllFilled() {
    		for (int a[] : sudokuTable) {
    			for (int b : a) {
    				if (b == 0) {
    					return false;
    				}
    			}
    		}
    	return true;
    }

    /**
     * 
     * The function convert the values in the matrix in string format.
     * @return the matrix in string format 
     */
    public String toString() {
    		StringBuilder StringOutput = new StringBuilder();
    		for (int i = 0; i < 9; i++) {
    			StringOutput.append(java.util.Arrays.toString(sudokuTable[i]));
    			if (i != 8) {
    				StringOutput.append("\n");
    			}
    		}
    		return StringOutput.toString();
    	}

}
