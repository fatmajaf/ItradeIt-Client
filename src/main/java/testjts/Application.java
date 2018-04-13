package testjts;

import java.util.Scanner;

import tn.esprit.SLTS_server.util.TextToSpeechConvertor;

public class Application {

	public static void main(String[] args) {

		// Defining Scanner Object to read data from console
	/*	Scanner inputScanner = new Scanner(System.in);
		
		TextToSpeechConvertor ttsc = new TextToSpeechConvertor();

		System.out.println("Enter the Text : (type 'exit' to terminate)");

		// Reading the text
		String inputText = inputScanner.nextLine();

		while (true) {

			if("exit".equalsIgnoreCase(inputText)) {
				
				inputText = "Bye, See you later";
				ttsc.speak(inputText);
				break;
			}
			
			ttsc.speak(inputText);
			
			System.out.println("Enter the Text : (type 'exit' to terminate)");
			inputText = inputScanner.nextLine();
		}

		inputScanner.close();*/
		TextToSpeechConvertor ttsc = new TextToSpeechConvertor();
		ttsc.speak("v n d a good impressions 16");
		
	}
}
