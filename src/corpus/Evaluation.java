package corpus;

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
		if (confTable.containsKey(w.getPos())) {
			HashMap<String, Integer> tabelRow = confTable.get(w.getPos());
			int count = tabelRow.containsKey(w.getPpos()) ? tabelRow.get(w
					.getPpos()) : 0;
			tabelRow.put(w.getPpos(), count + 1);
		} else {
			HashMap<String, Integer> tableRow = new HashMap<>();
			tableRow.put(w.getPpos(), 1);
			confTable.put(w.getPos(), tableRow);
		}

		totPos++;
		if (w.getPos().equals(w.getPpos())) {
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
