package data.bigram;

import data.Word;

public class PosBigram extends Bigram {

	public PosBigram(Word currWord, Word prevWord) {
		super(currWord.getPos(), prevWord.getPos());
	}
}
