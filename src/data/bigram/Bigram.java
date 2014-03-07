package data.bigram;


public abstract class Bigram {
	 String prob;
	 String given;

	public Bigram(String prob, String given) {
		this.prob = prob;
		this.given = given;
	}

	@Override
	public String toString() {
		return "(" + prob + "|" + given + ")";
	}

	public int hashCode() {
		return (prob + "\n" + given).hashCode();
	}

	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Bigram) 
			return ((Bigram) obj).hashCode() == hashCode();
		return false;
	}
}
