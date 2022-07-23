package Assign3;

import java.util.*;

/*
 * Anagram.java
 * Assignment: Programming Assignment 3
 * @author: Divya Kapoor
 * @date: 03/12/2021
 */

public class WordHashTable {
    /**
     * Number of words added
     */
    private int count = 0;
    /**
     * Size of the hashtable
     */
    private int size;
    /**
     * Array of buckets to store the sub list hashed index based sub lists
     */
    private List<Anagram>[] bucket;
    /**
     * Number of collision count
     */
    private int collision;

    /**
     * Number of collision count
     * @return count of word added in the table
     */
    public int getWordCount() {
        return count;
    }

    /**
     * Initial size of the table
     * @return initial size of the table
     */
    public int getSize() {
        return size;
    }

    /**
     * Return a the total count of collisions for all words
     * @return count of collisions
     */
    public int getCollisionCount() {
        return collision;
    }


    /**
     * Construct a hashtable with the given size
     * @param size - to create a bucket with given size
     */
    public WordHashTable(int size) {
        this.size = size;
        this.bucket = new LinkedList[size];
    }

    /**
     * Add new word into the table while handling coalition
     * @param anagram - to be added in the table
     */
    public void add(Anagram anagram) {
        count++;
        int anagramHash = anagram.keyHash(size);
        initSubBucket(anagramHash);
        if (bucket[anagramHash].size() == 0) {
            bucket[anagramHash].add(anagram);
        } else if (bucket[anagramHash].get(0).anagram.equals(anagram.anagram)) {
            bucket[anagramHash].add(anagram);
        } else {
            int computedHash = getComputedHash(anagramHash, anagram);
            add(anagram, computedHash);
            collision++;
//            System.out.println(collision+" : "+anagram);
        }
    }

    private void add(Anagram anagram, int computeHash) {
        initSubBucket(computeHash);
        bucket[computeHash].add(anagram);
    }

    private int getComputedHash(int hash, Anagram anagram) {
        int computeHash = hash;
        int i = 0;
        int j=0;

        while (bucket[computeHash] != null) {
            j++;
            if (bucket[computeHash].get(0).anagram.equals(anagram.anagram)) {
                break;
            }
            computeHash += Math.pow(i++, 2);
            computeHash %= size;
        }
        return computeHash;
    }

    private void initSubBucket(int anagramHash) {
        if (bucket[anagramHash] == null) {
            bucket[anagramHash] = new LinkedList<>();
        }
    }

    private int getWordHash(String word) {
//   	String[] arr = new String[10];
//    	List<String> list = new ArrayList<String>();

    	Anagram anagram = new Anagram(word);
        int anagramHash = anagram.keyHash(size);
        if (bucket[anagramHash] != null) {
            int computeHash = anagramHash;
            int i = 0;

            while (!bucket[computeHash].get(0).anagram.equals(anagram.anagram)) {
                computeHash += Math.pow(i++, 2);
                computeHash %= size;
                if (bucket[computeHash] == null) {
                    computeHash = -1;
                    break;
                }
            }
            return computeHash;
        } else {
            return -1;
        }
    }

    /**
     * Find and return the matching list of anagram based on hash from the bucket
     * @return list of same anagrams
     */
    public List<String> getMatchingAnagram(String word) {
        List<String> words = new ArrayList<>();
        int hash = getWordHash(word);
        if (hash != -1) {
            for (int i = 0; i < bucket[hash].size(); i++) {
                String temp = bucket[hash].get(i).word;
                if (!word.equals(temp))
                    words.add(temp);
               
            }
            
            return words;
        }
        
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return "WordHashTable{" +
                "bucket=" + (Arrays.toString(bucket).replace(" null,", "")) +
                '}';
    }
}

