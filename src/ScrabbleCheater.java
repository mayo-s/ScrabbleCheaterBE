package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

public class ScrabbleCheater {
	private final int hashTableSize = 7200;
	private LinkedList<String>[] hashTable;
	private int maxColisioncounter = 0;
	private int maxColisionposition = 0;

	@SuppressWarnings("unchecked")
	public ScrabbleCheater() {
		hashTable = new LinkedList[hashTableSize];
		for (int i = 0; i < hashTable.length; i++) {
			hashTable[i] = new LinkedList<String>();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ScrabbleCheater scrabbleCheater = new ScrabbleCheater();
		scrabbleCheater.readFile();
		scrabbleCheater.run();

	}

	public void run() {
		String input = "";
		BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
		while (!input.equals("quit")) {
			try {
				input = userInput.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ArrayList<String> result = getWords(input);
			for(String s: result){
				System.out.println(s);
			}
		}

	}

	public ArrayList<String> getWords(String input) {
		ArrayList<String> output = new ArrayList<>();
		String normalized = normalize(input);
		int hash = generateHash(normalized);
		LinkedList<String> listOfPotentialWords = hashTable[hash];
		ListIterator<String> itr = listOfPotentialWords.listIterator();
		String current = listOfPotentialWords.getFirst();
		if (normalize(current).equals(normalize(input))) {
			output.add(current);
		}
		while (itr.hasNext()) {
			current = itr.next();
			if (normalize(current).equals(normalize(input))) {
				output.add(current);
			}
			
		}
		return output;

	}

	public void readFile() {
		File dictionaryFile = new File("/Users/imi/Dropbox/eclipse/info1/ScrabbleCheaterBE/src/wordslong.txt");
		BufferedReader fileReader = null;
		int count = 0;
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
			if (currentInput != null) {
				String normalized = normalize(currentInput);
				int hash = generateHash(normalized);
				putInHashTable(currentInput, hash);
			}

			System.out.println(currentInput);
			count++;
		}

		try {
			fileReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Words total: " + count);
		System.out.println("Max colision: " + maxColisioncounter);
		System.out.println("Max colision position" + maxColisionposition);
	}

	private String normalize(String original) {
		char[] originalArray = original.toCharArray();
		Arrays.sort(originalArray);
		String sorted = new String(originalArray);
		return sorted;
	}

	private void putInHashTable(String word, int hash) {
		if (hash > 0 && hash < hashTable.length) {
			hashTable[hash].add(word);
			int colisioncounter = hashTable[hash].size();
			if (colisioncounter > maxColisioncounter) {
				maxColisioncounter = colisioncounter;
				maxColisionposition = hash;
			}
		}
	}

	private int generateHash(String word) {
		char[] chars = word.toCharArray();
		long polynom = 0;
		for (int i = 0; i < chars.length; i++) {
			int asciiPosition = chars[i] - 96; // a maps to 1
			polynom += Math.pow(asciiPosition, i + 1);
		}
		return (int) (polynom % hashTableSize);
	}

}
