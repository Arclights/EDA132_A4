package test;

import java.io.IOException;
import java.util.ArrayList;

import data.Corpus;
import data.CorpusFileReader;
import data.Sentence;
import data.Word;

public class ManuallyFillViterbi {
	public static void main(String[] args) throws IOException {
		Corpus trainCorpus = CorpusFileReader
				.createCorpus(data.Constants.CORPUS_TRAIN);
		Corpus develCorpus = CorpusFileReader
				.createCorpus(data.Constants.CORPUS_DEVELOPMENT);

		trainCorpus.tag(develCorpus);
		develCorpus.printToFile("ViterbiTest.txt");

		// Sentence sentence = new Sentence();
		// int i = 1;
		// for (String s : ("That round table might collapse").split("\\s")) {
		// sentence.add(new Word(i++, s));
		// }
		// trainCorpus.tag(sentence);

		// ArrayList<String> list = new ArrayList<>();
		// // String sentence="That round table might collapse";
		// String sentence =
		// "So much for anticipating the market by a fraction of a second .";
		// for (String s : ("<BOS> " + sentence.toLowerCase() + " <EOS>")
		// .split("\\s")) {
		// list.add(s);
		// }
		// c.tag(list);

	}
}
