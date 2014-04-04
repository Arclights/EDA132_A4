package evaluation;

import static data.Constants.*;

import java.io.IOException;

import data.Corpus;
import data.CorpusFileReader;
import data.Word;

public class EvaluationParser {

	public static Evaluation eval(Corpus corp) throws IOException {

		Evaluation evaluation = new Evaluation();

		for (Word w : corp) {
			evaluation.addRow(w);
		}

		return evaluation;

	}

	public static void main(String[] args) throws IOException {
		Corpus corp = CorpusFileReader.createCorpus("baseline_tagger_tagged_corp.txt");
		Evaluation evaluation = eval(corp);

		evaluation.printAccuracy();
		System.out.println();
		evaluation.printConfusionTable();
		evaluation.printConfusionTableLatex();
	}
}
