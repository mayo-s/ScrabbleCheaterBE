package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

public class ScrabbleCheater {
	private final int hashTableSize = 9000;
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
		ScrabbleCheater scrabbleCheater = new ScrabbleCheater();
		scrabbleCheater.readFile();
		for(int i=0; i< 100; i++)
		scrabbleCheater.printResult(scrabbleCheater.englishDistributionSevenLetters());
		scrabbleCheater.run();
	}

	public void run() {
		String input = "";
		BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
		while (!input.equals("quit")) {
			try {
				input = userInput.readLine();
			} catch (IOException e) {
			}
			printResult(input);
		}
	}

	private void printResult(String input) {
		ArrayList<String> result = getWords(input);
		for (String s : result) {
			System.out.println(s);
		}
	}

	public ArrayList<String> getWords(String input) {
		ArrayList<String> output = new ArrayList<>();
		String normalizedInput = normalize(input);
		int hash = generateHash(normalizedInput);
		String current = "";
		LinkedList<String> listOfPotentialWords = hashTable[hash];
		ListIterator<String> itr = listOfPotentialWords.listIterator();
		while (itr.hasNext()) {
			current = itr.next();
			if (normalize(current).equals(normalizedInput)) {
				output.add(current);
			}
		}
		return output;
	}
	
	//private HashSet<String> getSubstrings(String input){
		
	//}

	private String englishDistributionSevenLetters() {
		char[] distributionTable = new char[100];
		int counter = 0;
		String outputString = "";
		int[] frequencies = { 9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1 };
		for (int i = 0; i < frequencies.length; i++) {
			for (int j = 0; j < frequencies[i]; j++) {
				distributionTable[counter] = (char) (i + 97);
				counter++;
			}
		}
		Random rnd = new Random();
		for (int i = 0; i < 7; i++) {
			int randomIndex = rnd.nextInt(100);
			while (distributionTable[randomIndex] == 64) {
				randomIndex = rnd.nextInt(100);
			}
			outputString += distributionTable[randomIndex];
			distributionTable[randomIndex] = 64;
		}
		return outputString;

	}

	private String randomSevenLetters() {
		Random rnd = new Random();
		String returnString = "";
		for (int i = 0; i <= 7; i++) {
			char c = (char) (rnd.nextInt(26) + 97);
			returnString += c;
		}
		return returnString;
	}

	private boolean isPermutation(String word1, String word2) {
		if (normalize(word1).equals(normalize(word2))) {
			return true;
		} else
			return false;
	}

	public void readFile() {
		File dictionaryFile = new File("/Users/imi/Dropbox/eclipse/info1/ScrabbleCheaterBE/src/wordslong.txt");
		String currentInput = "";
		BufferedReader fileReader = null;
		int count = 0;
		try {
			fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(dictionaryFile)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			currentInput = fileReader.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		while (currentInput != null) {
			try {
				currentInput = fileReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (currentInput != null) {
				String normalized = normalize(currentInput);
				int hash = generateHash(normalized);
				putInHashTable(currentInput, hash);
			}
			// System.out.println(currentInput);
			count++;
		}

		try {
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Words total: " + count);
		System.out.println("Max colision: " + maxColisioncounter);
		System.out.println("Max colision position " + maxColisionposition);
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
			polynom += Math.pow(asciiPosition, i);
		}
		return (int) (polynom % hashTableSize);
	}
}

