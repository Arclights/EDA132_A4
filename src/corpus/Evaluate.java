package corpus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Evaluate {

	private static void eval(String filePath) throws IOException {
		double totPos = 0;
		double correctPos = 0;
		BufferedReader br = new BufferedReader(new FileReader(
				new File(filePath)));
		String line;
		while ((line = br.readLine()) != null) {
			String[] row = line.split("\\s");
			if (row.length > 1) {
				totPos++;
				if (row[4].equals(row[5])) {
					correctPos++;
				}
			}
		}
		br.close();

		System.out.println("Accuracy: " + (correctPos / totPos));
	}

	public static void main(String[] args) throws IOException {
		eval("CoNLL2009-corpus.txt");
	}
}
