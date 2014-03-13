package evaluation;

import static data.CorpusFileReader.createCorpus;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import data.Corpus;
import data.Word;

public class StatisticsParser {

	private static void parse(Corpus corp) throws IOException {
		Statistics stat = new Statistics();

		for (Word w : corp) {
			stat.addPos(w);
			stat.addWord(w);
		}
		System.out.println("Statistics built...");
		PrintWriter pw = new PrintWriter(new File("word_freq.txt"));
		pw.print(stat.getWordFreqList());
		pw.close();
		pw = new PrintWriter(new File("pos_freq.txt"));
		pw.print(stat.getPosFreqList());
		pw.close();
		System.out.println("Files stored!");
	}

	public static void main(String[] args) throws IOException {
		Corpus corp = createCorpus(data.Constants.CORPUS_TRAIN);
		parse(corp);
	}
}
