package data.bigram;

import java.util.HashMap;

public class Bigrams<E extends Bigram> {

	HashMap<E, Integer> bigrams;
	HashMap<String, Integer> n;

	public Bigrams() {
		bigrams = new HashMap<E, Integer>();
		n = new HashMap<String, Integer>();
	}

	public void addBigram(E bigram) {
		int count = bigrams.containsKey(bigram) ? bigrams.get(bigram) : 0;
		bigrams.put(bigram, count + 1);

		count = n.containsKey(bigram.given) ? n.get(bigram.given) : 0;
		n.put(bigram.given, count + 1);
	}

	public HashMap<E, Double> getProbabilities() {
		HashMap<E, Double> out = new HashMap<E, Double>();
		for (E b : bigrams.keySet()) {
			out.put(b, bigrams.get(b) / (double)n.get(b.given));
		}
		return out;
	}

	@Override
	public String toString() {
		return bigrams.toString();
	}

}
