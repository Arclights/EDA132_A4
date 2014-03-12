package data.bigram;

import data.Word;

public class WordBigram extends Bigram {

	public WordBigram(Word w) {
		super(w.getForm(),w.getPos());
	}

	public String getLemma() {
		return prob;
	}
	
	public String getPos() {
		return given;
	}
}
