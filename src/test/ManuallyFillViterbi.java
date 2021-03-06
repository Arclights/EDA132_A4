package test;

import data.Corpus;
import data.CorpusFileReader;

public class ManuallyFillViterbi {
	public static void main(String[] args) throws Exception {
		Corpus trainCorpus = CorpusFileReader
				.createCorpus(data.Constants.CORPUS_TRAIN);
		Corpus develCorpus = CorpusFileReader
				.createCorpus(data.Constants.CORPUS_DEVELOPMENT);

		trainCorpus.tagViterbi(develCorpus);
		develCorpus.printToFile(data.Constants.TAGGED_DATA);

	}
}
