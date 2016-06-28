package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class ScrabbleCheater {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ScrabbleCheater scrabbleCheater = new ScrabbleCheater();
		scrabbleCheater.readFile();

	}

	public void readFile() {
		File dictionaryFile = new File("/Users/imi/Dropbox/eclipse/info1/ScrabbleCheaterBE/src/words.txt");
		BufferedReader fileReader = null;
		try {
			fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(dictionaryFile)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String currentInput = "";

		try {
			currentInput = fileReader.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (currentInput != null) {
			try {
				currentInput = fileReader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(currentInput);
		}

		try {
			fileReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String normalize(String original){
		return "";
	}

}
