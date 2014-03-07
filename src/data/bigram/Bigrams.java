package data.bigram;

import java.util.HashMap;

public class Bigrams {

	HashMap<Bigram, Integer> bigrams;
	double totalBigrams;

	public Bigrams() {
		bigrams = new HashMap<>();
		totalBigrams = 0;
	}

	public void addBigram(Bigram bigram) {
		int count = bigrams.containsKey(bigram) ? bigrams.get(bigram) : 0;
		bigrams.put(bigram, count + 1);
		totalBigrams++;
	}

	public HashMap<Bigram, Double> getProbabilities() {
		HashMap<Bigram, Double> out = new HashMap<>();
		for (Bigram b : bigrams.keySet()) {
			out.put(b, bigrams.get(b) / totalBigrams);
		}
		return out;
	}

	@Override
	public String toString() {
		return bigrams.toString();
	}

}
