package baselinetagger;

import static data.Constants.CORPUS_TRAIN;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import data.Corpus;
import data.CorpusFileReader;
import data.Word;

public class BaselineTagger {

	private static WordStatistics buildStatistics(Corpus corp)
			throws IOException {
		WordStatistics ws = new WordStatistics();

		for (Word w : corp) {
			ws.addWord(w);
		}

		return ws;

	}

	private static void tag(WordStatistics ws, Corpus corp) throws IOException {
		Tagger tag = new Tagger(ws);
		PrintWriter pw = new PrintWriter(new File("data/tagged"));

		for (Word w : corp) {
			w.setPpos(tag.assignPos(w));
			pw.append(w.toString()).append("\n");
		}

		pw.close();

	}

	public static void main(String[] args) throws IOException {
		Corpus corp = CorpusFileReader.createCorpus(CORPUS_TRAIN);
		WordStatistics ws = buildStatistics(corp);
		System.out.println("WordStatistics done...");
		tag(ws, corp);
		System.out.println("Tagging done!");
	}
}
