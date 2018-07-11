
import java.util.*;

public class Line{

	private String input;
	private char[] alphabet;
	private boolean valid;

	public Line(String input, String alpha){
		this.input = input;
		this.alphabet = alpha.toCharArray();
		this.valid = true;
	}
	public void setValidity(boolean b){
		valid = b;
	}
	public boolean getValidity(){
		return valid;
	}
	public char[] getAlphabet(){
		return alphabet;
	}		

	public String toString(){
		return input;
	
	}
}