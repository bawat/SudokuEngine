package Sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A Sudoku implementation that uses primitive Arrays
 * @author Mark
 *
 */
public class ArraySudoku implements Sudoku{
	
	private Cell[][] sudokuCells;
	private void initialiseCells(){
		for(Cell[] column : sudokuCells) {
			Arrays.fill(column, new Cell());
		}
	}
	private Stream<Cell> eachCell(){
		return Arrays.stream(sudokuCells).flatMap(Arrays::stream);
	}
	
	public ArraySudoku(int width, int height){
		sudokuCells = new Cell[width][height];
		initialiseCells();
		generateSudoku();
	}
	public ArraySudoku(Cell[][] prePopulatedSudokuCells){
		this.sudokuCells = clone2DCellArray(prePopulatedSudokuCells);
	}
	public ArraySudoku(ArraySudoku toClone){
		this(toClone.sudokuCells);
	}
	private Cell[][] clone2DCellArray(Cell[][] toClone){
		var clonedCells = toClone.clone();
		for(int subArrayN = 0; subArrayN < clonedCells[0].length; subArrayN++) {
			clonedCells[subArrayN] = clonedCells[subArrayN].clone();
			for(int cellN = 0; cellN < clonedCells[subArrayN].length; cellN++) {
				clonedCells[subArrayN][cellN] = clonedCells[subArrayN][cellN].clone();
			}
		}
		return clonedCells;
	}
	
	
	@Override
	public void generateSudoku() {
		Sudoku result = recursivePopulate(new FocusedSudokuStateCell(this, new SudokuPosition(0, 0)));
		sudokuCells = ((ArraySudoku)result).sudokuCells;
		
		System.out.println("Generated!");
	}	
	private class FocusedSudokuStateCell{
		Sudoku sudoku;
		SudokuPosition pos;
		FocusedSudokuStateCell(Sudoku sudoku, SudokuPosition pos){
			this.sudoku = sudoku;
			this.pos = pos;
		}
	}
	private Sudoku recursivePopulate(FocusedSudokuStateCell iterationData) {
		
		SudokuPosition positionToCheck = iterationData.pos;
		Sudoku sudoku = iterationData.sudoku;
		if(sudoku.isNotValid()) return null;
		if(sudoku.isFilled()) return sudoku;
		
		for(var newValue : CellValue.values()) {
			var newPossibleSudoku = sudoku.clone().attemptFillPosition(positionToCheck, newValue);
			if(newPossibleSudoku.isNotValid()) continue;

			var potentiallySolvedSudoku = attemptToSolveRestOfSudokuUsingTheNewValue(newPossibleSudoku, positionToCheck);
			if(potentiallySolvedSudoku != null) return potentiallySolvedSudoku;
		}
		return null;
	}
	private Sudoku attemptToSolveRestOfSudokuUsingTheNewValue(Sudoku newSudoku, SudokuPosition positionWeJustChecked) {
		return recursivePopulate(new FocusedSudokuStateCell(newSudoku, positionWeJustChecked.getNextSudokuPos(newSudoku)));
	}

	@Override
	public List<Cell> getRow(SudokuPosition pos) {
		List<Cell> result = new ArrayList<Cell>();
		for(int x = 0; x < sudokuCells.length; x++) {
			result.add(sudokuCells[x][pos.y]);
		}
		return result;
	}
	public List<CellValue> getContainedValuesFromRowOfCells(SudokuPosition pos){
		return getContainedValuesFromCells(getRow(pos));
	}

	@Override
	public List<Cell> getColumn(SudokuPosition pos) {
		return Arrays.asList(sudokuCells[pos.x]);
	}
	public List<CellValue> getContainedValuesFromColumnOfCells(SudokuPosition pos){
		return getContainedValuesFromCells(getColumn(pos));
	}

	@Override
	/**
	 * Takes a position, remaps it to the top left hand corner of one of the nine sudoku regions
	 * Returns all the cells in the region
	 *
		012345678
		/3
		000111222
		*3
		000333666
	 */
	public List<Cell> getTheBlockRegionPositionIsInside(SudokuPosition pos) {
		int topLeftRegionX = (pos.x/3)*3, topLeftRegionY = (pos.y/3)*3;
		
		List<Cell> result = new ArrayList<Cell>();
		for(int y = topLeftRegionY; y < topLeftRegionY + 3; y++) {
			for(int x = topLeftRegionX; x < topLeftRegionX + 3; x++) {
				result.add(sudokuCells[x][y]);
			}
		}
		return result;
	}
	public List<CellValue> getContainedValuesFromBlockRegionOfCells(SudokuPosition pos){
		return getContainedValuesFromCells(getTheBlockRegionPositionIsInside(pos));
	}
	
	private List<CellValue> getContainedValuesFromCells(List<Cell> cells){
		return cells.stream().filter(e -> e.value.isPresent()).map(e -> e.value.get()).collect(Collectors.toList());
	}

	@Override
	public boolean isPositionFilled(SudokuPosition pos) {
		return sudokuCells[pos.x][pos.y].value.isPresent();
	}

	@Override
	public Sudoku attemptFillPosition(SudokuPosition pos, CellValue value) {
		if(!isPositionFilled(pos)) sudokuCells[pos.x][pos.y].value = Optional.of(value);
		return this;
	}

	@Override
	public boolean isFilled() {
		return eachCell().map(e -> e.value)
				.filter(Optional::isEmpty)
				.findAny().isEmpty();
	}

	@Override
	public boolean isNotValid() {
		for(int x = 0; x < sudokuCells.length; x++) {
			for(int y = 0; y < sudokuCells[0].length; y++) {
				List<CellValue> cellsInColumn = getContainedValuesFromColumnOfCells(new SudokuPosition(x, y));
				List<CellValue> cellsInRow = getContainedValuesFromRowOfCells(new SudokuPosition(x, y));
				List<CellValue> cellsInBlock = getContainedValuesFromBlockRegionOfCells(new SudokuPosition(x, y));
				
				if(listContainsDuplicates(cellsInColumn)) return true;
				if(listContainsDuplicates(cellsInRow)) return true;
				if(listContainsDuplicates(cellsInBlock)) return true;
			}
		}
		return false;
	}
	private static boolean listContainsDuplicates(List<CellValue> values) {
		Set<CellValue> set = new HashSet<CellValue>(values);
		return set.size() != values.size();
	}
	
	@Override
	public Sudoku clone() {
		return new ArraySudoku(this);
	}
	
	@Override
	public int getWidth() {
		return sudokuCells.length;
	}
	
	@Override
	public int getHeight() {
		return sudokuCells[0].length;
	}
	
	@Override
	public String toString() {
		return SudokuDrawer.sudokuToString(this);
	}
}
