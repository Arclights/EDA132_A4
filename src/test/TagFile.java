package test;

import java.io.IOException;

import corpus.Evaluation;
import data.Corpus;
import data.CorpusFileReader;
import data.Word;

public class TagFile {

	public static void main(String[] args) {
		if (args.length != 3) {
			err(null);
		}

		String train = args[0];
		String test = args[1];
		String destination = args[2];

		try {
			Corpus trainingCorpus = null;
			Corpus testCorpus = null;
			trainingCorpus = CorpusFileReader.createCorpus(train);
			testCorpus = CorpusFileReader.createCorpus(test);
			trainingCorpus.tag(testCorpus);

			testCorpus.printToFile(destination);

			System.out.println("Tagging done, calculating accuracy...");

			Evaluation eval;
			eval = eval(testCorpus);

			eval.printAccuracy();

			System.out.println("Thank you and have a nice day!");
		} catch (Exception e) {
			err(e.getMessage());
		}
	}

	private static void err(String reason) {
		if (reason != null) {
			System.out.println("Error in running the program...");
			System.out.println(reason);
			System.out.println();
		}
		System.out.println("To run the program, use the following syntax");
		System.out
				.println("java -jar <jarfile> <trainCorpus> <testCorpus> <destinationFile>");
		System.exit(0);
	}

	public static Evaluation eval(Corpus corp) throws IOException {

		Evaluation evaluation = new Evaluation();

		for (Word w : corp) {
			evaluation.addRow(w);
		}

		return evaluation;

	}
}
