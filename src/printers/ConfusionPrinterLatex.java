package printers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Set;

public class ConfusionPrinterLatex {
	public static void printConfusionTable(
			HashMap<String, HashMap<String, Integer>> confTable)
			throws FileNotFoundException {
		System.setOut(new PrintStream(new File("Confusion Table.tex")));

		System.out.println("\\begin{tabular}{ c ||*{"
				+ confTable.keySet().size() + "}{c}}");
		System.out.println("\\hline");
		System.out
				.println("$\\downarrow$ Correct & \\multicolumn{2}{|c}{Tagger $\\rightarrow$} \\\\");

		HashMap<String, Integer> columns = printTitleRow(confTable.keySet());
		System.out.println("\\hline");
		for (String s : confTable.keySet()) {
			HashMap<String, Integer> row = confTable.get(s);
			int[] freqs = new int[columns.size()];
			for (String s2 : row.keySet()) {
				if (columns.containsKey(s2)) {
					freqs[columns.get(s2)] = row.get(s2);
				}
			}
			printRow(s, freqs);
		}
		System.out.println("\\hline");
		System.out.println("\\end{tabular}");
	}

	private static HashMap<String, Integer> printTitleRow(Set<String> titles) {
		HashMap<String, Integer> columns = new HashMap<>();
		int columnCounter = 0;
		System.out.print("");
		for (String title : titles) {
			System.out.print(" & ");
			columns.put(title, columnCounter);
			columnCounter++;
			System.out.print(getCell(title + ""));
		}
		System.out.println("\\\\");
		return columns;
	}

	private static void printRow(String title, int[] row) {
		System.out.print(getCell(title));
		for (int i = 0; i < row.length; i++) {
			System.out.print(" & ");
			System.out.print(getCell(row[i] + ""));
		}
		System.out.println("\\\\");
	}

	private static String getCell(String content) {
		return content.replace("$", "\\$").replace("#", "\\#");
	}

}
