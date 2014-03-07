package data.bigram;

import data.Word;

public class WordBigram extends Bigram {

	public WordBigram(Word w) {
		super(w.getLemma(),w.getPos());
	}
}
