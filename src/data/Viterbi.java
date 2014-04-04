package data;

import java.util.ArrayList;
import java.util.HashMap;

import viterbitagger.ViterbiTagger;
import data.bigram.PosBigram;
import data.bigram.WordBigram;

public class Viterbi {

	private Sentence s;
	private HashMap<PosBigram, Double> posProbs;
	private HashMap<WordBigram, Double> wordProb;
	private String mostCommonTag;

	private HashMap<Integer, ArrayList<Node>> table;

	public Viterbi(Sentence s, HashMap<PosBigram, Double> posProbs,
			HashMap<WordBigram, Double> wordProb, String mostCommonTag) {
		this.s = s;
		this.posProbs = posProbs;
		this.wordProb = wordProb;
		this.mostCommonTag = mostCommonTag;
		table = new HashMap<Integer, ArrayList<Node>>();
		for (int i = 0; i < s.size(); i++) {
			table.put(i, new ArrayList<Node>());
		}
		table.get(0).add(new Node(null, 1, s.get(0).ppos));
	}

	public void calculate() {
		forward();
		backwards();
	}

	private void forward() {
		for (int i = 1; i < s.size() - 1; i++) {
			Word currentWord = s.get(i);

			HashMap<String, Double> possibleTags = getWordProbs(currentWord
					.getForm());

			if (possibleTags.isEmpty())
				possibleTags.put(mostCommonTag, 1.0);

			for (String ppos : possibleTags.keySet()) {
				Node from = null;
				double probFromPpos = -1;

				for (Node n : table.get(i - 1)) {
					double prob = getBigramProb(ppos, n.ppos);
					prob *= n.prob;
					if (prob > probFromPpos) {
						probFromPpos = prob;
						from = n;
					}
				}

				double wordProb = possibleTags.get(ppos);
				double totalProb = wordProb * probFromPpos;
				Node n = new Node(from, totalProb, ppos);
				table.get(i).add(n);
			}

			if (ViterbiTagger.mode != 0) {

				boolean allZero = true;

				for (Node n : table.get(i)) {
					if (n.prob != 0)
						allZero = false;
				}

				if (allZero) {
					for (Node n : table.get(i)) {
						if (ViterbiTagger.mode == 1)
							n.prob = 1;
						else
							n.prob = n.prevNode.prob;
					}
				}
			}

		}

	}

	private double getBigramProb(String pposFrom, String pposTo) {
		PosBigram bi = new PosBigram(new Word(pposFrom), new Word(pposTo));
		Double prob = posProbs.get(bi);
		if (prob == null)
			return 0;
		return prob;
	}

	private void backwards() {
		Node best = null;
		double prob = -1;
		for (Node n : table.get(s.size() - 2)) {
			if (n.prob > prob) {
				best = n;
				prob = n.prob;
			}
		}
		for (int i = s.size() - 2; i > 0; i--) {
			s.get(i).setPpos(best.ppos);
			best = best.prevNode;
		}
	}

	private static class Node {
		Node prevNode;
		double prob;
		String ppos;

		public Node(Node prevNode, double prob, String ppos) {
			this.prevNode = prevNode;
			this.prob = prob;
			this.ppos = ppos;
		}

		@Override
		public String toString() {
			if (prevNode != null)
				return "[" + ppos + ", " + prevNode.ppos + ", " + prob + "]";
			return "[" + ppos + ", " + prob + "]";
		}
	}

	/**
	 * Gets each POS the form can have and returns the probabilities of the form
	 * being that POS mapped to the respective POS
	 * 
	 * @param form
	 * @return
	 */
	private HashMap<String, Double> getWordProbs(String form) {
		HashMap<String, Double> data = new HashMap<String, Double>();
		for (WordBigram wb : wordProb.keySet()) {
			if (wb.getForm().equals(form)) {
				data.put(wb.getPos(), wordProb.get(wb));
			}
		}
		return data;
	}
}
