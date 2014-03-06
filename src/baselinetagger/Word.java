package baselinetagger;

public class Word {

	private String lemma;
	private String pos;

	public Word(String lemma, String pos) {
		this.lemma = lemma;
		this.pos = pos;
	}

	public int hashCode() {
		return lemma.hashCode();
	}

}
