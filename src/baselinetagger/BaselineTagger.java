package baselinetagger;

import java.io.IOException;

import corpus.Evaluation;
import corpus.EvaluationParser;

public class BaselineTagger {
	public static void main(String[] args) throws IOException {
		Evaluation eval = EvaluationParser
				.eval("data/english_corpus/CoNLL2009-ST-English-train-pos.txt");
	}
}
