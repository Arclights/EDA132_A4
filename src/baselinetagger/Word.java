package baselinetagger;

public class Word {

	private String lemma;
	private String pos;

	public Word(String lemma, String pos) {
		this.lemma = lemma;
		this.pos = pos;
	}

	public int hashCode() {
		return (lemma + "\n" + pos).hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Word) 
			return ((Word) obj).hashCode() == hashCode();
		return false;
	}

	public String toString() {
		return "[ " + lemma + ", " + pos + "]";
	}

	public String getLemma() {
		return lemma;
	}

	public String getPos() {
		return pos;
	}
}
