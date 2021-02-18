package Sudoku;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import static Sudoku.CellValue.*;

public class ArraySudokuTest {

	//Be careful when visual reading this, it looks transposed. So it reads Y rightwards X downwards
	ArraySudoku testCase = new ArraySudoku(new Cell[][] {
			{new Cell(ONE), new Cell(TWO), new Cell(THREE), new Cell(FOUR), new Cell(FIVE), new Cell(SIX), new Cell(SEVEN), new Cell(EIGHT), new Cell(ONE)},
			{new Cell(ONE), new Cell(TWO), new Cell(THREE), new Cell(FOUR), new Cell(FIVE), new Cell(SIX), new Cell(SEVEN), new Cell(EIGHT), new Cell(TWO)},
			{new Cell(ONE), new Cell(TWO), new Cell(THREE), new Cell(FOUR), new Cell(FIVE), new Cell(SIX), new Cell(SEVEN), new Cell(EIGHT), new Cell(THREE)},
			{new Cell(ONE), new Cell(TWO), new Cell(THREE), new Cell(FOUR), new Cell(FIVE), new Cell(SIX), new Cell(SEVEN), new Cell(EIGHT), new Cell(FOUR)},
			{new Cell(ONE), new Cell(TWO), new Cell(THREE), new Cell(FOUR), new Cell(FIVE), new Cell(SIX), new Cell(SEVEN), new Cell(EIGHT), new Cell(FIVE)},
			{new Cell(ONE), new Cell(TWO), new Cell(THREE), new Cell(FOUR), new Cell(FIVE), new Cell(SIX), new Cell(SEVEN), new Cell(EIGHT), new Cell(SIX)},
			{new Cell(ONE), new Cell(TWO), new Cell(THREE), new Cell(FOUR), new Cell(FIVE), new Cell(SIX), new Cell(SEVEN), new Cell(EIGHT), new Cell(SEVEN)},
			{new Cell(ONE), new Cell(TWO), new Cell(THREE), new Cell(FOUR), new Cell(FIVE), new Cell(SIX), new Cell(SEVEN), new Cell(EIGHT), new Cell(EIGHT)},
			{new Cell(ONE), new Cell(TWO), new Cell(THREE), new Cell(FOUR), new Cell(FIVE), new Cell(SIX), new Cell(SEVEN), new Cell(EIGHT), new Cell(NINE)}
	});
	
	@Test
	public void testGetRow() {
		List<Cell> row = testCase.getRow(new SudokuPosition(2, 6));
		for(Cell cell : row) {
			assertEquals(SEVEN.name(), cell.value.get().name());
		}
	}
	
	@Test
	public void testGetColumn() {
		List<Cell> column = testCase.getColumn(new SudokuPosition(2, 6));
		Cell lastVal = column.get(column.size()-1);
		assertEquals(THREE.name(), lastVal.value.get().name());
	}
	
	@Test
	public void testGetBlock() {
		List<Cell> block = testCase.getTheBlockRegionPositionIsInside(new SudokuPosition(2, 6));
		List<CellValue> values = block.stream().map(v -> v.value.get()).collect(Collectors.toList());
		
		Assert.assertEquals(values.contains(ONE), true);
		Assert.assertEquals(values.contains(TWO), true);
		Assert.assertEquals(values.contains(THREE), true);
		Assert.assertEquals(values.contains(FOUR), false);
		Assert.assertEquals(values.contains(FIVE), false);
		Assert.assertEquals(values.contains(SIX), false);
		Assert.assertEquals(values.contains(SEVEN), true);
		Assert.assertEquals(values.contains(EIGHT), true);
		Assert.assertEquals(values.contains(NINE), false);
	}

	@Test
	public void testIfListContainsDuplicates() {
		List<CellValue> noDuplicates = Arrays.asList(new CellValue[]{ONE, TWO, THREE});
		List<CellValue> duplicates = Arrays.asList(new CellValue[]{ONE, TWO, THREE, ONE});
		
		Assert.assertFalse(listContainsDuplicates(noDuplicates));
		Assert.assertTrue(listContainsDuplicates(duplicates));
	}
	private boolean listContainsDuplicates(List<CellValue> values) {
		Set<CellValue> set = new HashSet<CellValue>(values);
		return set.size() != values.size();
	}
	
	
	@Test
	public void testIfSudokuGeneratedCorrectly() {
		new ArraySudoku(9, 9);
	}
}
