package Sudoku;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Values are represented as a enum for future-proofing against larger Sudokus.
 * @author Mark
 *
 */
public enum CellValue {
	ONE('1'),
	TWO('2'),
	THREE('3'),
	FOUR('4'),
	FIVE('5'),
	SIX('6'),
	SEVEN('7'),
	EIGHT('8'),
	NINE('9');
	
	private char visualRepresentation;
	private CellValue(char visualRepresentation) {
		this.visualRepresentation = visualRepresentation;
	}
	
	@Override
	public String toString() {
		return "" + visualRepresentation;
	}
	
	/**
	 * Provides randomly ordered values that the Sudoku is generated with
	 * @return
	 */
	static List<CellValue> getAllUnordered() {
		var list = Arrays.asList(CellValue.values());
		Collections.shuffle(list);
		return list;
	}
}
