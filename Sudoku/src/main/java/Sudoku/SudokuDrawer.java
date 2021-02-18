package Sudoku;

/**
 * Used to represent the Sudoku to the user in various different ways
 * @author Mark
 *
 */
public class SudokuDrawer {
	static String sudokuToString(Sudoku sudoku) {
		String result = "#".repeat(sudoku.getWidth()), footer = result;
		result += System.lineSeparator();
		
		for(int y = 0; y < sudoku.getWidth(); y++) {
			for(var cell : sudoku.getRow(new SudokuPosition(0, y))) {
				result += (!cell.value.isPresent())? "?" : cell.value.get();
			}
			result += System.lineSeparator();
		}
		return result + footer;
	}
	public static void drawToConsole(Sudoku sudoku) {
		if(sudoku == null) System.out.println("Failed to generate Sudoku.");
		System.out.println(sudokuToString(sudoku));
	}
}
