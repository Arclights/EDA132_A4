package corpus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Parser {

	private static void parse(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				new File(filePath)));
		Statistics stat = new Statistics();
		String line;
		while ((line = br.readLine()) != null) {
			String[] row = line.split("\\s");
			if (row.length > 1) {
				stat.addWord(row);
				stat.addPos(row);
			}
		}
		br.close();

		PrintWriter pw = new PrintWriter(new File("word_freq.txt"));
		pw.print(stat.getWordFreqList());
		pw.close();
		pw = new PrintWriter(new File("pos_freq.txt"));
		pw.print(stat.getPosFreqList());
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		parse("CoNLL2009-corpus.txt");
	}
}
