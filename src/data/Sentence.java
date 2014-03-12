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
		for (int i = 1; i < size()-1; i++) {
			sb.append(get(i)).append(nl);
		}
		return sb.toString();
	}
	

}
