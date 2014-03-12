package test;

import java.io.IOException;
import java.util.ArrayList;

import data.Corpus;
import data.CorpusFileReader;

public class ManuallyFillViterbi {
	public static void main(String[] args) throws IOException {

		Corpus c = CorpusFileReader.createCorpus(data.Constants.CORPUS_TRAIN);

		ArrayList<String> list = new ArrayList<>();
//		String sentence="That round table might collapse";
		String sentence="So much for anticipating the market by a fraction of a second .";
		for (String s : ("<BOS> "
				+ sentence.toLowerCase() + " <EOS>")
				.split("\\s")) {
			list.add(s);
		}
		c.tag(list);

	}
}
