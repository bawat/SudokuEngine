package SudokuSolvingCode;

import Sudoku.ArraySudoku;

public class Solver {
	public static void main(String[] args) {
		ArraySudoku sudoku = new ArraySudoku(9,9);
		System.out.println(sudoku + " Printing sudoku...");
		System.out.println(sudoku);
	}
}
