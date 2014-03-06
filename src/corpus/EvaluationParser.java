package corpus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class EvaluationParser {

	public static Evaluation eval(String filePath) throws IOException {

		Evaluation evaluation = new Evaluation();
		BufferedReader br = new BufferedReader(new FileReader(
				new File(filePath)));
		String line;
		while ((line = br.readLine()) != null) {
			String[] row = line.split("\\s");
			if (row.length > 1) {
				evaluation.addRow(row);
			}
		}
		br.close();

		return evaluation;

	}

	public static void main(String[] args) throws IOException {
		Evaluation evaluation = eval("data/tagged");

		evaluation.printAccuracy();
		System.out.println();
		evaluation.printConfusionTable();
		evaluation.printConfusionTableLatex();
	}
}
