package data.bigram;

import data.Word;

public class WordBigram extends Bigram {

	public WordBigram(Word w) {
<<<<<<< HEAD
		super(w.getForm(),w.getPos());
=======
		super(w.getLemma(),w.getPos());
>>>>>>> d9e8cb0e353691e738aa025b48299ee7564e913e
	}

	public String getLemma() {
		return prob;
	}
	
	public String getPos() {
		return given;
	}
}
