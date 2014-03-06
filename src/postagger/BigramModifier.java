package postagger;

import static data.Constants.CORPUS_DEVELOPMENT;

import java.io.IOException;
import java.util.Iterator;

import data.Corpus;
import data.CorpusFileReader;
import data.Word;

public class BigramModifier {

	public static Bigrams extractBigrams(Corpus corp) {
		System.out.println("Extracting bigrams...");

		Bigrams bigrams = new Bigrams();

		Iterator<Word> it = corp.getCompleteIterator();
		Word wPrev = it.next();
		while (it.hasNext()) {
			Word w = it.next();
			bigrams.addBigram(new Bigram(w, wPrev));
			wPrev = w;
		}

		return bigrams;
	}

	public static double P(Bigram bigram) {
		double p = 0;

		return p;
	}

	public static void main(String[] args) throws IOException {
		Corpus corp = CorpusFileReader.createCorpus(CORPUS_DEVELOPMENT);
		Bigrams bigrams = extractBigrams(corp);
		System.out.println(bigrams);
		System.out.println(bigrams.getProbabilities());
	}
}
