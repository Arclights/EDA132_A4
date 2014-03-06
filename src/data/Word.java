package data;

public class Word {

	String id;
	String form;
	String lemma;
	String plemma;
	String pos;
	String ppos;

	public Word(String id, String form, String lemma, String plemma,
			String pos, String ppos) {
		this.id = id;
		this.form = form;
		this.lemma = lemma;
		this.plemma = plemma;
		this.pos = pos;
		this.ppos = ppos;
	}

	public Word(String special) {
		this("-", special, special, special, special, special);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(id).append("\t");
		sb.append(form).append("\t");
		sb.append(lemma).append("\t");
		sb.append(plemma).append("\t");
		sb.append(pos).append("\t");
		sb.append(ppos);

		return sb.toString();
	}

	public String getPos() {
		return pos;
	}

	public String getLemma() {
		return lemma;
	}

	public void setPpos(String ppos) {
		this.ppos = ppos;
	}

	public String getPpos() {
		return ppos;
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
}