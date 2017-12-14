/**
 * This is the algorithm part for the Sudoku game. 
 * In general, a 9*9 grid is created with 9 3*3 subsections. 
 * In each section, the rows and columns must use numbers in the range of 1-9, 
 * and each number only can be used once. 
 * 
 * @author Zhonghao Zhao & Tianjiao Pu
 */
public class Sudoku implements Cloneable {
	
    int[][] sudokuArray; // initialize the sudoku table as a matrix

    /**
     * Set up the sudoku table 
     * @param table
     */
    public Sudoku(int[][] table) {
    			sudokuArray = table;    		
    }
   
    /**
     * Copy the sudoku table with all the features
     * @return sudokuTable
     */
    public int[][] getArray() {
	     return sudokuArray.clone();
    }

    /**
     * Obtain the array 
     *
     * @param orginalReference 
     * @return sudokuTable(true)， getArray(false)
     */
    public int[][] getArray(boolean orginalReference) {
    		if (orginalReference) {
         	    return sudokuArray;
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
     * check if the table is filled. 
     *
     * @return true/false
     */
    public boolean AllFilled() {
    		for (int a[] : sudokuArray) {
    			for (int b : a) {
    				if (b == 0) {
    					return false;
    				}
    			}
    		}
    	return true;
    }
    /**
     * check if the sudoku is correct. 
     *
     * @param ignoreZero or not
     * @return if zero exits, return false 
     */
    public boolean isCorrect(boolean ignoreZero) {
    		if (!ignoreZero && !AllFilled()) {
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
    public boolean isCorrect(int x, int y) {
    		if (sudokuArray[x][y] == 0) {
    			return true;
    		}
    		// check col
    		for (int col = 0; col < 9; col++) { 			
    			if (sudokuArray[x][col] == sudokuArray[x][y] && (col != y)) {
    				return false;
    			}
    		}
       // check row
    		for (int row = 0; row < 9; row++) {
    			if (sudokuArray[row][y] == sudokuArray[x][y] && (row != x)) {
    				return false;
    			}
    		}

    		// check the larger 3*3 region but assign each subsection a id"a"
	    int subgridrow = (x / 3) + 1;
	    int subgridcol = (y / 3) + 1;
	    
		for (int i = (subgridrow-1)*3 ; i < subgridrow*3; i++) {
			for (int j = (subgridcol-1)*3; j < subgridcol*3; j++) {
				if (sudokuArray[i][j] == sudokuArray[x][y] && ( i != x && j!= y)) {
					return false;
				}
			}
		}
	return true;
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
						if (sudokuArray[i][j] == 0) {
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

   

}
