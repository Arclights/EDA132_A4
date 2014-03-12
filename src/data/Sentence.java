package data;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Sentence extends ArrayList<Word> {

	public Sentence() {
		super.add(new Word("<BOS>"));
		super.add(new Word("<EOS>"));
	}
		
	@Override
	public boolean add(Word e) {
		add(size()-1,e);		
		return true;
	}
	
	
	@Override
	public String toString() {
		String nl = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		for(Word w : this) {
			sb.append(w).append(nl);
		}
		
		
		return sb.toString();
	}
	
	@Override
	public Sentence clone() {
		Sentence s = new Sentence();
		s.clear();
		for (Word w : this) {
			s.add(w.clone());
		}
		return s;
	}
}
