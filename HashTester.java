package Assign3;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
/*
 * There are two versions of hashing function developed in the Anagram.java class to compute the hash of the given key.
 * Method-1
 * Linear hashing with complexity effectively amortized O(1)
 * The current approach of hashing function is borrowed from the Java hashing technique i.e. hash computation
 * using the ASCII values of the anagram's characters by multiplying with some prime numbers. This concepts is taken from
 * the book Effective Java by Joshua Bloch. To further optimize this, a concept of memoization is used to retain the
 * already computed hash for future use and the complexity is effectively amortized O(1).
 *
 * Method-2
 * This method is an attempt improve the previously used linear hashing technique by traversing the anagram from both
 * left and right while computing the hash. This reduce the time complexity which can further combined with the concept
 * of memoization. As compared to method-1, this method is not performant in terms of unique hashes so can be optimized further.
 *
 * To avoid the indexOutOfBound exception with hash and bucket selection, the size of the bucket list is used to limit
 * the upperbound of the generated hashcodes. The hashcode collions are handled with quadratic probing hashing to find a
 * stable hashcode for a vacant or suitable bucket index.
 * Big O Complexities
 *              BigO                        Collision complexity
 * Hashcode-1:  effectively amortized O(1)          O(6)
 * Hashcode-2:  effectively amortized O(1)          O(26)
 *
 * Average runtime: 600ms
 *
 * if (computedHash != 0)
 *      return computedHash;
 * int hash = 5;
 * for (char ch : anagram.toCharArray())
 *      hash = 87 * hash + ch;
 *
 *
 *  References:
 *  Effective Java Second Edition. Chapter 3, page 48, by Joshua Bloch
 *  https://fpl.cs.depaul.edu/jriely/450/extras/Chapter3.pdf
 *
 *  Note: The words with special characters are sanitize and included into the dictionary
 */

/*
 * Assignment: Programming Assignment 3
 *
 * @author: Divya Kapoor
 * @date: 03/12/2021
 */
public class HashTester {
	

    public static void main(String[] args) {
    	if (args.length < 1) {
			System.err.println("One argument is needed to run this program.");
			return;
		}
    	System.out.println(Arrays.toString(args));
    	
        WordHashTable words = getWordTable(200000, "words.txt");
        readTestFileAndSaveAnagrams(words, args[0], args[1]);
        System.out.println("Word Count: " + words.getWordCount());
        System.out.println("Collision Count: " + words.getCollisionCount());
    	System.out.println();
        
    }

    /**
     * Helper method to construct a hashtable of words from a given file
     *
     * @param size - to construct size of buckets in the table
     * @param wordFile - name of the word file to read from
     */
    private static WordHashTable getWordTable(int size, String wordFile) {
        WordHashTable words = new WordHashTable(size);
        try (Scanner scanner = new Scanner(new FileInputStream(wordFile))) {
            while (scanner.hasNextLine()) {
                words.add(new Anagram(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return words;
    }

    /**
     * Helper method to read the list from the given file and
     * save it result into an output file
     *
     * @param table - list of dictionary words
     * @param inputFile - file name to read input from
     * @param outputFile - a string of anagrams:file name to write the result to
     */
    private static void readTestFileAndSaveAnagrams(WordHashTable table, String inputFile, String outputFile) {
        File file = new File(outputFile);
        try {
           boolean flag = file.createNewFile();
//       	   System.out.println("create "+ flag+" "+ outputFile);
           
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Scanner scanner = new Scanner(new FileInputStream(inputFile));
             PrintWriter writer = new PrintWriter(new FileOutputStream(outputFile))) {
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                writer.println(getWritableString(table.getMatchingAnagram(word), word));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Helper method to format the list into a string
     *
     * @param words - list of anagrams for a word
     * @param word - input word
     * @return a string of anagrams
     */
    private static String getWritableString(List<String> words, String word) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(word);
        stringBuffer.append(" ");
        stringBuffer.append(words.size());
        for (String w : words) {
            stringBuffer.append(" " + w);
        }
        return stringBuffer.toString();
    }

}
