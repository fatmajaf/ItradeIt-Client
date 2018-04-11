package tn.esprit.sltsclient.main;

import tn.esprit.sltsclient.Utils.BadWordFilter;

public class Testprofinity {
	
	public static void main(String[] args) throws InterruptedException {
		// P.s : sorry for the bad langage .. this is for testing purposes 
		String input="fuck this shit , damn , god damn , asshole , this is awsome , pussy , milk me, bitch  3asba , miboun ass  bass , massive";
		String output = BadWordFilter.getCensoredText(input);
		System.out.println(output);

	}

}

