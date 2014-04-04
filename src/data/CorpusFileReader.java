package data;

import static data.Constants.CORPUS_DEVELOPMENT;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CorpusFileReader {

	
	public static void main(String[] args) throws Exception {
		Corpus c = createCorpus(CORPUS_DEVELOPMENT);
		for (Word w : c) {
			System.out.println(w);
		}
//		System.out.println(c);
	}

	public static Corpus createCorpus(String filePath) throws Exception {
		System.out.printf("Creating corups from '%s'\n",filePath);
		Corpus corpus = new Corpus();
		int sentence = 0;
		boolean print = false;
		BufferedReader br = new BufferedReader(new FileReader(
				new File(filePath)));
		String line;
		while ((line = br.readLine()) != null) {
			if (sentence % 10000 == 0 && print) {
				System.out.printf("Read %d sentences\n",sentence);
				print = false;
			} else if (sentence % 10000 == 1) {
				print = true;
			}
			String[] row = line.split("\\s");
			if (row.length > 1) {
				corpus.addWord(sentence, row);
			} else {
				sentence++;
			}
		}
		br.close();
		System.out.println("All sentences loaded!");
		System.out.println("Calculating bigrams...");
		corpus.calculateBigrams();
		System.out.println("Bigrams calculated!");
		System.out.println(corpus.statistics());
		return corpus;
				
	}
}
