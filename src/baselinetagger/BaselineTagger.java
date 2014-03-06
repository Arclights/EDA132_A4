package baselinetagger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class BaselineTagger {

	private static WordStatistics buildStatistics(String filePath)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				new File(filePath)));
		WordStatistics ws = new WordStatistics();
		String line;
		while ((line = br.readLine()) != null) {
			String[] row = line.split("\\s");
			if (row.length > 1) {
				String lemma = row[2];
				String pos = row[4];
				ws.addWord(new Word(lemma, pos));
			}
		}
		br.close();

		return ws;

	}

	private static void tag(WordStatistics ws, String filePath) throws IOException {
		Tagger tag = new Tagger(ws);
		BufferedReader br = new BufferedReader(new FileReader(
				new File(filePath)));
		String line;
		PrintWriter pw = new PrintWriter(new File("data/tagged"));
		while ((line = br.readLine()) != null) {
			String[] row = line.split("\\s");
			if (row.length > 1) {
				String lemma = row[2];
				row[5] = tag.assignPos(lemma);
				
			}
			
			for (String s : row) {
				pw.printf("%s ", s);
			}
			pw.append("\n");
		}
		
		pw.close();
		br.close();

	}

	public static void main(String[] args) throws IOException {
		WordStatistics ws = buildStatistics("data/english_corpus/CoNLL2009-ST-English-train-pos.txt");
		System.out.println("WordStatistics done...");
		tag(ws, "data/english_corpus/CoNLL2009-ST-English-development-pos.txt");
		System.out.println("Tagging done!");
	}

}
