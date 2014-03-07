package data.bigram;


public abstract class Bigram {
	String a;
	String b;

	public Bigram(String a, String b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public String toString() {
		return "(" + a + "|" + b + ")";
	}

	public int hashCode() {
		return (a + "\n" + b).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Bigram) 
			return ((Bigram) obj).hashCode() == hashCode();
		return false;
	}
}
