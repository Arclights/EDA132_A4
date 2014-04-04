package printers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;

public class ConfusionPrinterDefault {
	public static void printConfusionTable(
			HashMap<String, HashMap<String, Integer>> confTable)
			throws FileNotFoundException {
		System.setOut(new PrintStream(new File("Confusion Table.txt")));
		int columnWidth = 0;
		for (String s : confTable.keySet()) {
			if (s.length() > columnWidth) {
				columnWidth = s.length();
			}
		}

		HashMap<String, Integer> columns = new HashMap<>();
		int columnCounter = 0;
		int rowLength = 0;
		// Head
		String columnTitle = getCell("", columnWidth);
		rowLength += columnTitle.length();
		System.out.print(columnTitle);
		for (String s : confTable.keySet()) {
			columns.put(s, columnCounter);
			columnCounter++;
			columnTitle = getCell(s, columnWidth);
			rowLength += columnTitle.length();
			System.out.print(columnTitle);
		}
		System.out.println();
		System.out.println("Columns: " + columnCounter);
		String divider = repeatString("-", rowLength);
		System.out.println(divider);

		for (String s : confTable.keySet()) {
			HashMap<String, Integer> row = confTable.get(s);
			if (row == null) {
				System.out.println("oj då!");
			}
			int[] freqs = new int[columnCounter];
			for (String s2 : row.keySet()) {
				if (columns.containsKey(s2)) {
					freqs[columns.get(s2)] = row.get(s2);
				}
			}
			printRow(s, freqs, columnWidth, divider);
		}
	}

	private static void printRow(String title, int[] row, int columnWidth,
			String divider) {
		System.out.print(getCell(title, columnWidth));
		for (int cell : row) {
			System.out.print(getCell(cell + "", columnWidth));
		}
		System.out.println();
		System.out.println(divider);
	}

	private static String getCell(String content, int columnWidth) {
		return getPaddedString(content, columnWidth) + "|";
	}

	private static String getPaddedString(String s, int columnWidth) {
		return repeatString(" ", columnWidth - s.length()) + s;
	}

	private static String repeatString(String s, int amount) {
		return new String(new char[amount]).replace("\0", s);
	}
}
