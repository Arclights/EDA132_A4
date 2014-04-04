package data;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import data.bigram.Bigrams;
import data.bigram.PosBigram;
import data.bigram.WordBigram;

public class Corpus implements Iterable<Word> {

	private ArrayList<Sentence> sentences;
	private HashMap<PosBigram, Double> posProbs;
	private HashMap<WordBigram, Double> wordProb;
	private String mostFrequentTag;

	public Corpus() {
		sentences = new ArrayList<Sentence>();
		sentences.add(new Sentence());
	}

	void addWord(int sentence, String[] row) throws Exception {
		if (sentence == sentences.size()) {
			Sentence list = new Sentence();
			sentences.add(list);
		}
		try {
			String id = row[0];
			String form = row[1];
			String lemma = row[2];
			String plemma = row[3];
			String pos = row[4];
			String ppos = row[5];
			Word w = new Word(id, form, lemma, plemma, pos, ppos);
			ArrayList<Word> givenSentence = sentences.get(sentence);
			givenSentence.add(w);
		} catch (Exception e) {
			System.out.println(Arrays.toString(row));
			throw e;
		}
	}

	void calculateBigrams() throws FileNotFoundException {
		posProbs = getPosBigramsProbabilities();
		wordProb = getWordBigramsProbabilities();
		mostFrequentTag = mostFrequentTag();
		System.out.println("Most freq tag: " + mostFrequentTag);
	}

	private HashMap<PosBigram, Double> getPosBigramsProbabilities() {

		Bigrams<PosBigram> b = new Bigrams<PosBigram>();
		for (ArrayList<Word> sentence : sentences) {
			for (int i = 1; i < sentence.size(); i++) {
				PosBigram bigram = new PosBigram(sentence.get(i),
						sentence.get(i - 1));
				b.addBigram(bigram);
			}
		}
		return b.getProbabilities();
	}

	private HashMap<WordBigram, Double> getWordBigramsProbabilities() {
		Bigrams<WordBigram> b = new Bigrams<WordBigram>();
		for (ArrayList<Word> sentence : sentences) {
			for (int i = 0; i < sentence.size(); i++) {
				WordBigram bigram = new WordBigram(sentence.get(i));
				b.addBigram(bigram);
			}
		}
		return b.getProbabilities();
	}

	@Override
	public String toString() {
		String nl = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();

		for (Sentence sentence : sentences) {
			sb.append(sentence).append(nl);
		}

		return sb.toString();
	}

	@Override
	public Iterator<Word> iterator() {
		return new CorpusIterator();
	}

	private class CorpusIterator implements Iterator<Word> {

		int sentence = 0;
		int word = 1;

		@Override
		public boolean hasNext() {
			if (sentence == sentences.size())
				return false;
			return true;
		}

		@Override
		public Word next() {
			Word w = sentences.get(sentence).get(word);
			word++;
			if (sentences.get(sentence).size() - 1 == word) {
				word = 1;
				sentence++;
			}
			return w;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();

		}

	}

	public String statistics() {
		int words = 0;
		for (ArrayList<Word> w : sentences)
			words += w.size();

		String s = "Corpus consists of: \n\t" + sentences.size()
				+ " sentences\n\t" + words + " words";
		return s;
	}

	public void tagBaseline(Corpus corpus) {
		for (Word w : corpus) {
			HashMap<String, Double> wordProbs = getWordProbs(w.form);
			double largestWordProb = 0;
			String bestPos = "";
			for (String pos : wordProbs.keySet()) {
				if (wordProbs.get(pos) > largestWordProb) {
					largestWordProb = wordProbs.get(pos);
					bestPos = pos;
				}
			}
			w.setPpos(bestPos);
		}
	}

	public void tagViterbi(Corpus corpus) {
		System.out.println("Tagging...");
		int proc = 0;
		int size = corpus.sentences.size();
		for (int i = 0; i < size; i++) {
			int curr = (100 * i) / size;
			if (curr > proc) {
				proc = curr;
				System.out.printf("%3d%%\t", curr);
				if (curr % 10 == 0)
					System.out.println();
			}
			Sentence sentence = corpus.sentences.get(i);
			Viterbi viterbi = new Viterbi(sentence, posProbs, wordProb,mostFrequentTag);
			viterbi.calculate();
		}
		System.out.printf("100%%\t");
		System.out.println();
	}

	private String mostFrequentTag() {
		HashMap<String, Integer> posFreq = new HashMap<String, Integer>();

		for (Sentence s : sentences) {
			for (Word w : s) {
				String word = w.getPos();
				Integer i = posFreq.get(word);
				if (i == null) {
					i = 0;
				}
				i++;
				posFreq.put(word, i);
			}
		}

		String tag = "";
		int freq = -1;

		for (String word : posFreq.keySet()) {
			int f = posFreq.get(word);
			if (f > freq) {
				freq = f;
				tag = word;
			}
		}
		return tag;
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

	public void printToFile(String fileName) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(fileName);
		pw.append(toString());
		pw.close();
	}

}
