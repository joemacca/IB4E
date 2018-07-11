import java.util.*;

public class Rule{

	String forbidden;
	ArrayList<String> exceptions;

	public Rule (String forbidden, ArrayList<String> exceptions){
		this.forbidden = forbidden;
		this.exceptions = exceptions;
	}

	public String getForbidden(){
		return forbidden;
	}

	public ArrayList<String> getExceptions(){
		return exceptions;
	}

	public String exceptionsToString(){
		StringBuilder sb = new StringBuilder("");
		for(String s : exceptions){
			sb.append(s + " ");
		}
		return sb.toString();
	}

	public String createException(String prefix, String forbidden){
	 	prefix = prefix + forbidden;
	 	return prefix;
	}	

	public int getMaxExceptionLength(){
	 	int max = 0;

	 	for(String s : exceptions){
	 		if(s.length() > max){
	 			max = s.length();
	 		}
	 	}
	 	return max+forbidden.length();
	}	



}