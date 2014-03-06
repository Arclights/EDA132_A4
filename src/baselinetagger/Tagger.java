package baselinetagger;

import java.util.HashMap;

import data.Word;

public class Tagger {

	private HashMap<String, String> ws;

	public Tagger(WordStatistics ws) {
		this.ws = ws.getMostFrequentPos();
	}

	public String assignPos(Word w) {
		return ws.get(w.getLemma());
	}
}
