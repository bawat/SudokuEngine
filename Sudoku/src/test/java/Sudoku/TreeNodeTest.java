package Sudoku;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class TreeNodeTest {
	
	@Test
	public void benchmarkSudokuGeneration() throws IOException {
		List<Long> timesTaken = new ArrayList<Long>();
		for(int i = 0; i < 1000; i++) {
			timesTaken.add(timeTakenToRun(() -> new ArraySudoku(9, 9)));
		}
		String csv = timesTaken.stream().map(Object::toString).collect(Collectors.joining(","));
		File generatedFile = new File("timesTaken.csv");
		generatedFile.createNewFile();
		FileWriter fw = new FileWriter(generatedFile);
		fw.write(csv);
		fw.close();
	}
	
	private long timeTakenToRun(Runnable toRun) {
		var currentTime = System.currentTimeMillis();
		toRun.run();
		return System.currentTimeMillis() - currentTime;
	}
}
