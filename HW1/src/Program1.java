/* 
 * Program1.java
 *
 * The driver program for CSCI 241's Program1
 *
 * Reads a text file and calls Wordifier to learn new words
 * 
 * Do not modify this file.  
 *
 *
 * ----------------------------------------------------------------------------
 *
 * usage:
 *
 * java Program1 inputFilename countThreshold probabilityThreshold dictionary
 *
 * where the arguments are
 * 
 *   inputFileName         a plaintext file from which your program will estimate counts
 *   countThreshold        two tokens will only be merged if they appear in sequence at least this many times
 *   probabilityThreshold  two tokens will only be merged if their bigram product is at least this high
 *   dictionaryFilename    a dictionary against which to check whether your learned words are actually words
 *
*/

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;

public class Program1 {
	public static void main( String[] args ) {
		if( args.length != 4 ) {	
			System.err.println("Error: Wrong number of arguments");
			System.exit(1);
		}
		
		String inputFileName        = args[0];
		int countThreshold          = Integer.parseInt(args[1]);
		double probabilityThreshold = Double.parseDouble(args[2]);
		String dictionaryFilename   = args[3];

		// Read input files
		LinkedList<String> data = Wordifier.loadSentences(inputFileName);
		HashSet<String> dictionary = Wordifier.loadDictionary(dictionaryFilename);

		// Iteratively merge tokens and resegment the text
		boolean done = false;
		int round = 0;
		while( !done ) {
			// Init round
			round++;
			HashMap<String,Integer> bigramCounts = new HashMap<String,Integer>();
			HashMap<String,Double> bigramProbs   = new HashMap<String,Double>();
			HashMap<String,Double> leftUnigramProbs   = new HashMap<String,Double>();
			HashMap<String,Double> rightUnigramProbs  = new HashMap<String,Double>();
			System.out.format("= Round %d:%n",round);

			// Get bigram product scores
			Wordifier.computeCounts(data,bigramCounts);
			Wordifier.convertCountsToProbabilities(bigramCounts,bigramProbs,leftUnigramProbs,rightUnigramProbs);
			HashMap<String,Double> scores = Wordifier.getScores(bigramProbs,leftUnigramProbs,rightUnigramProbs);

			// Identify the new words (bigrams exceeding the thresholds)	
			HashSet<String> newWords = Wordifier.findNewWords(bigramCounts,scores,countThreshold,probabilityThreshold);
			System.out.println("\tAdded " + newWords.size() + " new tokens");

			if( newWords.size() == 0 ) {
				// No tokens identified to merge; our job here is done
				done = true;
			} else {
				// Otherwise resegment the data and go to the next round
				data = Wordifier.resegment(data,newWords);
				System.out.println(data.size() + " tokens");
//				if (round == 10) {done = true;}
			}
		}
		
		// Evaluate whether the learned words are actual words
		HashMap<String,Integer> vocab = Wordifier.getVocabulary(data);
		Wordifier.printNumWordsDiscovered(vocab,dictionary);

		return;
	}
}
