package test;

import java.io.IOException;
import java.util.ArrayList;

import data.Corpus;
import data.CorpusFileReader;

public class ManuallyFillViterbi {
	public static void main(String[] args) throws IOException {

		Corpus c = CorpusFileReader.createCorpus(data.Constants.CORPUS_TRAIN);

		ArrayList<String> list = new ArrayList<>();
		for (String s : ("<BOS> the round table might collapse <EOS>").split("\\s")) {
			list.add(s);
		}
		c.tag(list);

	}
}
