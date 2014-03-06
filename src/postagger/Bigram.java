package postagger;

import data.Word;

public class Bigram {
	String pos;
	String prevPos;

	public Bigram(Word currWord, Word prevWord) {
		this.pos = currWord.getPos();
		this.prevPos = prevWord.getPos();
	}

	@Override
	public String toString() {
		return "(" + pos + "|" + prevPos + ")";
	}

	public int hashCode() {
		return (pos + "\n" + prevPos).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Bigram) 
			return ((Bigram) obj).hashCode() == hashCode();
		return false;
	}
}
