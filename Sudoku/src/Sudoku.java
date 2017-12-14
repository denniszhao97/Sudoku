/**
 * This is the algorithm part for the Sudoku game. 
 * In general, a 9*9 grid is created with 9 3*3 subsections. 
 * In each section, the rows and columns must use numbers in the range of 1-9, 
 * and each number only can be used once. 
 * 
 * @author Zhonghao Zhao & Tianjiao Pu
 */
public class Sudoku {
	
    public int[][] sudokuArray; // initialize the sudoku table as a matrix

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
	     return sudokuArray;
    }

    /**
     * check if the table is filled. 
     *
     * @return true/false
     */
    public boolean AllFilled() {
    	   for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (this.sudokuArray[i][j] == 0) {
					return false;
				}
			}
		}
    	return true;
    }

    /**
     * check whether the value at position (x,y) is correct or not. 
     *
     * @param y column 
     * @param x row 
     * @return true/false 
     */
    public boolean isCorrect(int x, int y) {
    	
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
     * check if the sudoku is correct. 
     *
     * @return if zero exits, return false 
     */
    public boolean FinalCheck() {
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
     * recursively call solve until every cell is not zero and follow the isCorrect rule;
     * @param S the Sudoku player wants to solve
     * @return whether is solvable
     */
    public boolean solve(Sudoku S) {
    	    int[][] s = S.sudokuArray;
    	    for (int i = 0; i < 9; i++) {
    	        for (int j = 0; j < 9; j++) {
    	            if (s[i][j] != 0) {
    	                continue;
    	            }
    	            for (int value = 1; value <= 9; value++) {
    	            	    s[i][j] = value;
    	                if (isCorrect(i, j)) {
    	                    if (solve(this)) {
    	                        return true;
    	                    } else {
    	                        s[i][j] = 0;
    	                    }
    	                } else {
    	                	  s[i][j] = 0;
    	                }
    	            }
    	            return false;
    	        }
    	    }
    	    return true;
    }
    /**
     * @return If solved, return the sudoku
     */
    public Sudoku solveSudoku() {
        if(this.solve(this)) {
        	    return this;
        } else {
        	    return null;
        }
    	}
}