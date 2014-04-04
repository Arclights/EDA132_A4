package viterbitagger;

import java.io.IOException;

import data.Corpus;
import data.CorpusFileReader;
import data.Word;
import evaluation.Evaluation;
public class ViterbiTagger {

	public static int mode = 0;
	
	public static void main(String[] args) {
		if (args.length < 3 || args.length > 4) {
			err(null);
		}
		if (args.length == 4) {
			try {
				int imp = Integer.parseInt(args[3]);
				if (imp > 2 || imp < 0)
					err("Illegal improvement selected! You bad boy!");
				mode = imp;
			} catch (NumberFormatException e) {
				err("Illegal improvement selected! You bad boy!");
			}
		}
		String train = args[0];
		String test = args[1];
		String destination = args[2];

		try {
			Corpus trainingCorpus = null;
			Corpus testCorpus = null;
			trainingCorpus = CorpusFileReader.createCorpus(train);
			testCorpus = CorpusFileReader.createCorpus(test);
			trainingCorpus.tagViterbi(testCorpus);

			testCorpus.printToFile(destination);

			System.out.println("Tagging done, calculating accuracy...");

			Evaluation eval;
			eval = eval(testCorpus);

			eval.printAccuracy();

			System.out.println("Thank you and have a nice day!");
		} catch (Exception e) {
			e.printStackTrace();
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
				.println("java -jar <jarfile> <trainCorpus> <testCorpus> <destinationFile> [improvement]");
		System.out.println("As improvement you can choose the following:");
		System.out.println("0: no improvement");
		System.out.println("1: if a word has 0 probability on its predecessors, set it to 100%");
		System.out.println("2: if a word has 0 probability on its predecessors, set it to its predecessors probability");
		
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
