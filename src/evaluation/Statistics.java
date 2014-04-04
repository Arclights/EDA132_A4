package evaluation;

import java.util.HashMap;

import data.Word;

public class Statistics {
	private HashMap<String, Integer> wordFreq;
	private HashMap<String, Integer> posFreq;

	public Statistics() {
		wordFreq = new HashMap<String, Integer>();
		posFreq = new HashMap<String, Integer>();
	}

	public void addWord(Word w) {
		String lemma = w.getForm();
		int count = wordFreq.containsKey(lemma) ? wordFreq.get(lemma) : 0;
		wordFreq.put(lemma, count + 1);
	}

	public void addPos(Word w) {
		String pos = w.getPos();
		int count = posFreq.containsKey(pos) ? posFreq.get(pos) : 0;
		posFreq.put(pos, count + 1);
	}

	public String getWordFreqList() {
		StringBuilder sb = new StringBuilder();
		for (String key : wordFreq.keySet()) {
			sb.append(key).append("\t").append(wordFreq.get(key)).append("\n");
		}
		return sb.toString();
	}

	public String getPosFreqList() {
		StringBuilder sb = new StringBuilder();
		for (String key : posFreq.keySet()) {
			sb.append(key).append("\t").append(posFreq.get(key)).append("\n");
		}
		return sb.toString();
	}

}
