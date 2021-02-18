package Sudoku;

import java.util.Optional;
/**
 * The box in which cell values can be written
 * Keeps the CellValues Enum unique to representing the possible values
 * An empty Cell acts as an identifier of an incomplete Sudoku
 * @author Mark
 *
 */
public class Cell{
	Optional<CellValue> value = Optional.empty();
	Cell(CellValue val){
		value = Optional.of(val);
	}
	Cell(){}
	
	public Cell clone(){
		if(value.isEmpty()) return new Cell();
		return new Cell(value.get());
	}
}