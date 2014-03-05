package corpus;

import java.io.FileNotFoundException;
import java.util.HashMap;

import printers.ConfusionPrinterDefault;
import printers.ConfusionPrinterLatex;

public class Evaluation {

	double totPos = 0;
	double correctPos = 0;

	HashMap<String, HashMap<String, Integer>> confTable;

	public Evaluation() {
		confTable = new HashMap<>();
	}

	public void addRow(String[] row) {
		if (confTable.containsKey(row[4])) {
			HashMap<String, Integer> tabelRow = confTable.get(row[4]);
			int count = tabelRow.containsKey(row[5]) ? tabelRow.get(row[5]) : 0;
			tabelRow.put(row[5], count + 1);
		} else {
			HashMap<String, Integer> tableRow = new HashMap<>();
			tableRow.put(row[5], 1);
			confTable.put(row[4], tableRow);
		}

		totPos++;
		if (row[4].equals(row[5])) {
			correctPos++;
		}
	}

	public void printAccuracy() {
		System.out.println("Accuracy: " + (correctPos / totPos));
	}

	public void printConfusionTable() throws FileNotFoundException{
		ConfusionPrinterDefault.printConfusionTable(confTable);
	}
	
	public void printConfusionTableLatex() throws FileNotFoundException {
		ConfusionPrinterLatex.printConfusionTable(confTable);
	}

}
