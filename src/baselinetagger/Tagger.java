package baselinetagger;

import java.util.HashMap;

public class Tagger {

	
	
	private HashMap<String,String> ws;

	public Tagger(WordStatistics ws) {
		this.ws = ws.getMostFrequentPos();
	}
	
	public String assignPos(String lemma) {
		return ws.get(lemma);
	}
}
