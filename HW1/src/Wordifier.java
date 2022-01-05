/* 
 * Wordifier.java
 *
 * Implements methods for iteratively learning words from a 
 * character-segmented text file, and then evaluates how good they are
 *
 * Students may only use functionality provided in the packages
 *     java.lang
 *     java.util 
 *     java.io
 * 
 * Use of any additional Java Class Library components is not permitted 
 * 
 * Andrew Nguyen
 * Sam Tang
 *
 */

import java.io.*;
import java.util.*;

public class Wordifier {

	/**
	 * Loads tokens from the input file into a LinkedList
	 * @param textFilename a plaintext input file
	 * @return Returns a LinkedList form of the data
	 */
	public static LinkedList<String> loadSentences( String textFilename ){
		LinkedList<String> sentences = new LinkedList<>();
		try{
			File textFile = new File(textFilename);
			Scanner input = new Scanner(textFile);
			String aWord;
			while(input.hasNext()){
				aWord = input.next();
				sentences.add(aWord);
			}
		}catch(FileNotFoundException e){
			System.out.println("Error: Unable to open file " + textFilename);
			System.exit(1);
		}
		return sentences;
	}

	/**
	 * Creates and returns a HashSet of all bigrams meeting 3 criteria:
	 * 		1) the bigram is a key in the HashMap bigramCounts
	 * 		2) the count of the bigram is >= countThreshold
	 * 		3) the score of the bigram is >= probabilityThreshold
	 * 	Note: keys in the returned HashSet must include a space
	 * @param bigramCounts HashMap of bigrams and how many times they appear in the data
	 * @param scores HashMap of bigrams and their bigram products
	 * @param countThreshold a threshold on the counts (from bigramCounts)
	 * @param probabilityThreshold a threshold on the bigram products (from scores)
	 * @return Returns a HashSet containing bigrams that fit the above criteria
	 */
	public static HashSet<String> findNewWords( HashMap<String,Integer> bigramCounts, HashMap<String,Double> scores, int countThreshold, double probabilityThreshold ) {

		HashSet<String> findNewWords = new HashSet<>();

		Iterator<String> keySet = bigramCounts.keySet().iterator();

		String key;
		while (keySet.hasNext()) {
			key = keySet.next();
			if (bigramCounts.containsKey(key)) {
				if (bigramCounts.get(key) >= countThreshold) {
					if (scores.get(key) >= probabilityThreshold) {
						findNewWords.add(key);
					}
				}
			}
		}
		return findNewWords;
	}

	/**
	 * Merges tokens together and puts them into a new LinkedList if they exist in newWords,
	 * otherwise non-eligible tokens are simply put into the new list without merging
	 * @param previousData data as a LinkedList before tokens existing in newWords are merged
	 * @param newWords A Set of words. Used to determine what tokens to merge together
	 * @return Returns a new LinkedList with any tokens that existed in newWords merged together
	 */
	public static LinkedList<String> resegment( LinkedList<String> previousData, HashSet<String> newWords ) {

		LinkedList<String> mergedWords = new LinkedList<>();
		int count = 0;
		String biLeft, biRight, aBigram;
		while(count < previousData.size() - 1){

			biLeft = previousData.get(count);
			biRight = previousData.get(count + 1);
			aBigram = biLeft + " " + biRight;

			if(newWords.contains(aBigram)){
				mergedWords.add(aBigram.replace(" ", "")); //Changes "A T" to "AT"
				count++;
			}
			else{
				mergedWords.add(biLeft);
			}
			count++;
		}
		if (count == previousData.size()-1) {
			mergedWords.add(previousData.get(count));
		}
		return mergedWords;
	}

	/**
	 * Maps bigrams appearing in the data to the number of times it appears.
	 * @param data data of the input as a LinkedList
	 * @param bigramCounts Starts empty; maps bigrams to the number of times they occur in data
	 */
	public static void computeCounts(LinkedList<String> data, HashMap<String,Integer> bigramCounts ) {

	    String left, right, bigram;

		for (int i = 1; i < data.size(); i++) {
			left = data.get(i - 1);
			right = data.get(i);

			bigram = (left + " " + right);
			incrementHashMap(bigramCounts, bigram, 1);
		}
	}

	/**
	 * Takes a HashMap of bigrams and the number of times they appear in the original data set and fills in three other
	 * HashMaps. One for joint probability (P(x1x2)), one for the marginal probability of the left unigram, and one for
	 * the probability of the right unigram (Pl(x1) and Pr(x2)).
	 * @param bigramCounts Maps bigrams to the number of times they appear in the data
	 * @param bigramProbs Starts empty; Maps bigrams to their joint probability
	 * @param leftUnigramProbs Starts empty; maps left side unigrams to their marginal probability
	 * @param rightUnigramProbs Starts empty; maps right side unigrams to their marginal probability
	 */
	public static void convertCountsToProbabilities(HashMap<String,Integer> bigramCounts, HashMap<String,Double> bigramProbs, HashMap<String,Double> leftUnigramProbs, HashMap<String,Double> rightUnigramProbs ) {

		double totalBigramSize = bigramCounts.size();
		Iterator<String> countIt = bigramCounts.keySet().iterator();

		//bigramCounts will have < "A H", 5 > for example, split key into an array for left and right

		String currentBigram[], key;
		HashMap<String, Integer> preLeft = new HashMap<>();
		HashMap<String, Integer> preRight = new HashMap<>();

		while (countIt.hasNext()) {
			key = countIt.next();

			//Do total bigram probability first
			bigramProbs.put(key, bigramCounts.get(key)/totalBigramSize);

			//Now separate into unigrams
			currentBigram = key.split(" ");

//			incrementHashMap(leftUnigramProbs, currentBigram[0], bigramCounts.get(key));
//			incrementHashMap(rightUnigramProbs, currentBigram[1], bigramCounts.get(key));

			incrementHashMap(preLeft, currentBigram[0], 1);	//Total number of times a unigram appears on the left
			incrementHashMap(preRight, currentBigram[1], 1); //total it appears on the right
		}

		//USE ONE OF THE TWO BELOW BUT NOT BOTH
		//also, be sure to uncomment increment hashmap and recomment the other one above if you switch.

		//Using this way takes longer but also produces more words. Peterpan input file averages around 4% - 9%
		countIt = bigramCounts.keySet().iterator();
		while (countIt.hasNext()) {
		 	key = countIt.next();
		 	currentBigram = key.split(" ");

//			 Time to calculate marginal probabilities of left and right
		 	leftUnigramProbs.put(currentBigram[0], preLeft.get(currentBigram[0])/totalBigramSize);
		 	rightUnigramProbs.put(currentBigram[1], preRight.get(currentBigram[1])/totalBigramSize);
		 }

		//Using this way is faster, exactly the way the sample output peterpan file implements it
//		countIt = leftUnigramProbs.keySet().iterator();
//		while(countIt.hasNext()) {
//			key = countIt.next();
//			leftUnigramProbs.replace(key, leftUnigramProbs.get(key)/totalBigramSize);
//		}
//
//		countIt = rightUnigramProbs.keySet().iterator();
//		while(countIt.hasNext()) {
//			key = countIt.next();
//			rightUnigramProbs.replace(key, rightUnigramProbs.get(key)/totalBigramSize);
//		}
//		System.out.println(leftUnigramProbs);
//		System.out.println(rightUnigramProbs);
	}



	/**
	 * Calculates the bigram product using the joint probability (P(x1x2)), and the marginal probabilities of the
	 * left and right unigrams (Pl(x1) and Pr(x2)).
	 * @param bigramProbs HashMap containing the joint probability of bigrams
	 * @param leftUnigramProbs HashMap containing marginal probability of unigrams on the left
	 * @param rightUnigramProbs HashMap containing marginal probability of unigrams on the right
	 * @return Returns a HashMap of bigrams and their bigram product, which is defined as:
	 * 				P(x1x2) / sqrt(Pl(x1) * Pr(x2))
	 */
	public static HashMap<String,Double> getScores( HashMap<String,Double> bigramProbs, HashMap<String,Double> leftUnigramProbs, HashMap<String,Double> rightUnigramProbs ) {

		HashMap<String,Double> result = new HashMap<>();
		Iterator<String> countIt = bigramProbs.keySet().iterator();
		String key; String currentBigram[]; double joint; double left; double right;

		while (countIt.hasNext()) {
			key = countIt.next(); //in the form of bigrams : "A C" etc
			currentBigram = key.split(" ");

			joint = bigramProbs.get(key);
			left = leftUnigramProbs.get(currentBigram[0]);
			right = rightUnigramProbs.get(currentBigram[1]);

			result.put(key,((joint)/(Math.sqrt(left*right)))); //BP(x1x2) = P(x1x2)/sqrt(Pl(x1)Pr(x2))
		}

		return result;
	}

	/**
	 * Creates a HashMap containing K,V pairs of words and the number of times they appear in the data
	 * @param data LinkedList representing the data
	 * @return Returns a HashMap that maps words to the number of times they appear in the data
	 */
	public static HashMap<String,Integer> getVocabulary( LinkedList<String> data ) {

        HashMap<String, Integer> map = new HashMap<>();
		Iterator<String> linkedIter = data.iterator();
		String str;

		while (linkedIter.hasNext()) {
			str = linkedIter.next();
			incrementHashMap(map, str, 1);
		}

		return map;
	}

	/**
	 * Loads all the words from the dictionary input file into a HashSet. Data in a Set disregards duplicate entries.
	 * @param dictionaryFilename Self-explanatory. Usually something like xyz.txt
	 * @return Returns a HashSet containing all unique words from the dictionary file
	 */
	public static HashSet<String> loadDictionary( String dictionaryFilename ) {

	    try {
            Scanner sc = new Scanner(new File(dictionaryFilename)); //In the format of xyz.dictionary
            HashSet<String> resultSet = new HashSet<>();

            //loop each sc.nextline --> hash string --> put into HashSet
            while (sc.hasNextLine()) {
                String str = sc.nextLine();
                resultSet.add(str);
            }

            return resultSet;

        } catch (FileNotFoundException err) {
            System.out.println("loadDictionary() received an invalid dictionary file name");
            System.out.println(err.toString());
        }

        return null;
	}

	/**
	 * Simply increments the integer in a String-int HashMap if the key exists. If not, it is inserted.
	 * @param map A non-null HashMap
	 * @param key A String key that may or may not exist in the HashMap
	 * @param amount amount to increment the key's value by. Usually 1.
	 */
	private static void incrementHashMap(HashMap<String,Integer> map,String key,int amount) {

		if( map.containsKey(key) ) {
			map.put(key,map.get(key)+amount);
		} else {
			map.put(key,amount);
		}
		return;
	}

	//overloaded method that takes a String,Double HashMap instead, as well as a double amount
	private static void incrementHashMap(HashMap<String,Double> map,String key,double amount) {

		if( map.containsKey(key) ) {
			map.put(key,map.get(key)+amount);
		} else {
			map.put(key, amount);
		}
		return;
	}

	/**
	 * Prints words in vocab that are also in dictionary (meaning they are recognized as legitimate words).
	 * Additionally prints miscellaneous data about the words such as how many unique words/tokens were found,
	 * how many total words/tokens were found.
	 * @param vocab A HashMap of words paired to the number of times they appear in the original data set.
	 * @param dictionary A HashSet containing all the words in the dictionary input file.
	 */
	public static void printNumWordsDiscovered( HashMap<String,Integer> vocab, HashSet<String> dictionary ) {

		Iterator<String> keys = vocab.keySet().iterator();
		int uniqueWordCount = 0; int uniqueTokenCount = 0;
		int dictSize = dictionary.size(); int totalTokenCount = 0; //non-unique token sum

		String str;
		LinkedList<String> discoveredWords = new LinkedList<>();
		while (keys.hasNext()) {
			str = keys.next();
			totalTokenCount += vocab.get(str);
			if (dictionary.contains(str)) {
				discoveredWords.add(str);	//Words in vocab that are also in dictionary
				uniqueWordCount++;				//Total number of unique words counted
				uniqueTokenCount += vocab.get(str);

			}
		}

		discoveredWords.sort(Comparator.naturalOrder());
		for (String s : discoveredWords) {
			System.out.println("Discovered " + s + " (count " + vocab.get(s) + ")");
		}

		System.out.println();

		System.out.println("Discovered " + uniqueWordCount + " actual (unique) words out of " + dictSize +
				" words (" + String.format("%2f", (100 * (double)uniqueWordCount / dictSize)) + "%)");

		System.out.println("Discovered " + uniqueTokenCount + " actual (unique) tokens out of " + totalTokenCount +
				" tokens (" + String.format("%2f", (100 * (double)uniqueTokenCount / totalTokenCount)) + "%)");

		return;
	}
}