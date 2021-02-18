package Sudoku;

import java.awt.Point;

/**
 * The Sudoku position contains additional methods such as getNextSudokuPos that distinguish
 * from a normal point object
 * 
 * Valid numbers are 0 to sudoku.width-1 inclusive
 * @author Mark
 *
 */
public class SudokuPosition extends Point{
	private static final long serialVersionUID = 3111196224540965045L;

	SudokuPosition(int x, int y){super(x, y);};
	
	SudokuPosition getNextSudokuPos(Sudoku sudoku) {
		if(x >= sudoku.getWidth()-1) {
			if(y >= sudoku.getHeight()-1) return null;
			return new SudokuPosition(0, y+1);
		}
		return new SudokuPosition(x+1, y);
	}
}
