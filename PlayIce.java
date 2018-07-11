import java.util.*;
import java.util.regex.Pattern;

public class PlayIce{
	
	private static ArrayList<Rule> rules = new ArrayList<Rule>();
	private static ArrayList<String> exceptions;
	private static ArrayList<Line> lines = new ArrayList<Line>();

	public static void main(String [] args){
		int mode = 0; 
		String alphabet = "";
		Scanner sc = new Scanner(System.in);

		while(sc.hasNextLine()){
			String line = sc.nextLine();
			/* For the first line i.e. the alphabet. */
			if(mode == 0){
				alphabet = line;
				mode++;
			} 
			/* Creates each instance of the rules and saves to list. */
			else if(mode == 1 && (!line.isEmpty())){
				String[] substrings = line.split(" ");
				String forbidden = substrings[0];
				exceptions = new ArrayList<String>();
				for(int i = 1; i < substrings.length; i++){
					exceptions.add(substrings[i]);
				}
				Rule r = new Rule(forbidden, exceptions);				
				rules.add(r);

			/* For the blank line. */
			} else if(line.isEmpty()){
				mode++;
			} 
			/* Generates an instance of our input, starts the validation process.*/
			else if(mode == 2){
				Line l = new Line(line, alphabet);
				lines.add(l);
				checkType(l);
			}
		}
	}

	/**
	 * Checks if a input is a word or a number.
	 * @param line object.
	 */
	private static void checkType(Line line){
		boolean hasLetters = line.toString().matches(".*[a-zA-Z]+.*");
		if(hasLetters){
			validateWord(line);
		} else {
			int number = Integer.parseInt(line.toString());
			countCombinations(line, number);

		} 
	}

	/**
	 * First validates if a line breaks any rules, then checks if the string
	 * contains the correct alphabet. 
	 * @param line object
	 */
	private static void validateWord(Line line){
		for(Rule r : rules){
			searchExceptions(line, r, line.toString());
		}
		/* Checks alphabet. */
		String s = String.valueOf(line.getAlphabet());
		String regEx = "^[" + s + "]+$"; 
		boolean correctAlphabet = line.toString().matches(regEx);
		if(!correctAlphabet){
			line.setValidity(false);
		}
		print(line);
	}

	/**
	 * Looks for forbidden strings recursively.
	 * @param line object
	 * @param This particular rule.
	 * @param string input.
	 */
	private static void searchExceptions(Line line, Rule r, String str){
		ArrayList<String> ex = r.getExceptions();
		String forbidden = r.getForbidden();
		int forbiddenIndex = str.indexOf(forbidden);

		/* A couple checks for simple cases. */
		boolean checkOne = forbiddenIndex >= 0 && ex.size() == 0;
		boolean checkTwo = forbiddenIndex >= 0 && forbidden.length() == str.length();
		if(checkOne || checkTwo){
			line.setValidity(false);
			return;
		}
		if(forbiddenIndex < 0){
			return;
		}
		/* Makes sure we don't go out of bounds, flag is set to true if
		 * a forbidden string is within the rules.
		 */
		boolean flag = false;
		for(String exception : ex){
			int start = forbiddenIndex - exception.length();
			int end = forbiddenIndex + forbidden.length();

			if (start < 1) start = 0;
			if (end > str.length()) end = str.length();
			String combined = r.createException(exception, forbidden);
			String prefix = str.substring(start, end);
			//System.out.println("Comb: " + combined);
			//System.out.println("Pre: " +prefix);			
			if(combined.matches(prefix)){
				flag = true;
			} 
		}
		/* If the flag is true, we continue our search for the next forbidden string.
		 * If false, word violates a rule and its validity field is set to false.
		 */
		if(flag){ 
			searchExceptions(line, r, str.substring(forbiddenIndex+1));
		} else {
			line.setValidity(false);
			return;
		}
	}

	/**
	 * 
	 * @param line object
	 */
	private static void countCombinations(Line line, int num){
		int max = num;
		int stringLength = 0;
		ArrayList<String> strings = new ArrayList<String>();
		for(char letter : line.getAlphabet()){
			String s = "";
			strings.add(s+letter);
		}
		int maximum = 0;
		for(Rule r : rules){
			int x = r.getMaxExceptionLength();
			if(x > maximum){
				maximum = x;
			}
		}

		recurse(line.getAlphabet(), strings, max, stringLength+1, maximum);
		//System.out.println(x);
	}

	private static void recurse(char[] alphabet, ArrayList<String> strings, int max, int min, int maxLength){
		if(min == max){
			System.out.println(strings.size());
			return;
		}
		for(String s : strings){
			System.out.print(s + " ");
		}
		System.out.println();

		ArrayList<String> strings2 = new ArrayList<String>();

		for(char letter : alphabet){
			for(int i = 0; i < strings.size(); i++){
				String s = strings.get(i);
				s = s + letter;
				Line line = new Line(s, alphabet.toString());
				for(Rule r : rules){
					//int maxLength = r.getMaxExceptionLength();
					System.out.println(maxLength);

					if(s.length() > maxLength+s.length()){
						s = s.substring((s.length() - maxLength), s.length());
						//searchExceptions(line, r, shortenedString);
						//System.out.println(s);
					} 
					//System.out.println(s);

					searchExceptions(line, r, s);

					
				}

				if(line.getValidity()){
					strings2.add(s);
				}
			}
		}

		//System.out.println(strings2.size());
		recurse(alphabet, strings2, max, min+1, maxLength); 
	}
	


	private static void print(Line line){
		if(line.getValidity()){
			System.out.println("Valid");
		} else {
			System.out.println("Invalid");
		}		
	}
}