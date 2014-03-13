package evaluation;

import java.io.FileNotFoundException;
import java.util.HashMap;

import printers.ConfusionPrinterDefault;
import printers.ConfusionPrinterLatex;
import data.Word;

public class Evaluation {

	double totPos = 0;
	double correctPos = 0;

	HashMap<String, HashMap<String, Integer>> confTable;

	public Evaluation() {
		confTable = new HashMap<>();
	}

	public void addRow(Word w) {
		String pos = w.getPos();
		String ppos = w.getPpos();
		if (confTable.containsKey(pos)) {
			HashMap<String, Integer> tabelRow = confTable.get(pos);
			int count = tabelRow.containsKey(ppos) ? tabelRow.get(ppos) : 0;
			tabelRow.put(ppos, count + 1);
		} else {
			HashMap<String, Integer> tableRow = new HashMap<>();
			tableRow.put(ppos, 1);
			confTable.put(ppos, tableRow);
		}

		totPos++;
		if (pos.equals(ppos)) {
			correctPos++;
		}
	}

	public void printAccuracy() {
		System.out.println("Accuracy: " + (correctPos / totPos));
	}

	public void printConfusionTable() throws FileNotFoundException {
		ConfusionPrinterDefault.printConfusionTable(confTable);
	}

	public void printConfusionTableLatex() throws FileNotFoundException {
		ConfusionPrinterLatex.printConfusionTable(confTable);
	}

}
