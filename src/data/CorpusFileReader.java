package data;

import static data.Constants.CORPUS_DEVELOPMENT;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CorpusFileReader {

	
	public static void main(String[] args) throws IOException {
		Corpus c = createCorpus(CORPUS_DEVELOPMENT);
		for (Word w : c) {
			System.out.println(w);
		}
//		System.out.println(c);
	}

	public static Corpus createCorpus(String filePath) throws IOException {
		Corpus corpus = new Corpus();
		int sentence = 0;
		
		BufferedReader br = new BufferedReader(new FileReader(
				new File(filePath)));
		String line;
		while ((line = br.readLine()) != null) {
			String[] row = line.split("\\s");
			if (row.length > 1) {
				corpus.addWord(sentence, row);
			} else {
				sentence++;
			}
		}
		br.close();
		
		System.out.println(corpus.statistics());
		return corpus;
				
	}
}
