package Sudoku;

import java.util.List;

public interface Sudoku {
	void generateSudoku();
	
	List<Cell> getRow(SudokuPosition pos);
	List<Cell> getColumn(SudokuPosition pos);
	List<Cell> getTheBlockRegionPositionIsInside(SudokuPosition pos);
	
	boolean isPositionFilled(SudokuPosition pos);
	Sudoku attemptFillPosition(SudokuPosition pos, CellValue value);
	
	boolean isFilled();
	boolean isNotValid();
	
	int getWidth();
	int getHeight();
	
	Sudoku clone();
	String toString();
}
