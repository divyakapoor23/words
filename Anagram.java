package Assign3;

import java.util.Arrays;
/*
 * Assignment: Programming Assignment 3
 * @author: Divya Kapoor
 * @date: 03/12/2021
 */



/**
 * A model class to hold the word and anagram data.
 * The word is process wssto sanitize the unwanted special characters
 */

class Anagram {
    /**
     * sanitize regex pattern
     */
    public static final String checkAlphanumeric = "[^a-zA-Z0-9]";
    public final String anagram;
    public final String word;

    /**
     * for hashcode memoization
     */
    private int computedHash;

    /**
     * Constructs an anagram object with the given word.
     *
     * @param word - dictionary word to create an anagram.
     */
    Anagram(String word) {
        this.anagram = getAnagram(word);
        this.word = word;
    }

    private String getAnagram(String word) {
        char[] chars = word.replaceAll(checkAlphanumeric, "").toLowerCase().trim().toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    /**
     * Compute a hash value using the from the given word while applying the sanitization.
     * @return - computed hashcode
     */
    public int keyHash(int lowBound) {
        if (computedHash != 0){
            return computedHash;
        }
        int hash = 5;
        for (char ch : anagram.toCharArray()) {
            hash = 87 * hash + ch;
        }
        hash %= lowBound;
        computedHash = (hash < 0) ? (hash + lowBound) : hash;
//		System.out.println("Computed Hash: "+ computedHash);
        return computedHash;
    }

//    /**
//     * Compute a hash value using the from the given word while applying the sanitization.
//     * @return - computed hashcode
//     */
//    public int keyHash(int lowBound) {
//        int hash2 = 5;
//        char[] chars = anagram.toCharArray();
//        int lenMid = chars.length / 2;
//        for (int i = 0, j = chars.length - 1; i < lenMid && j >= lenMid; i++, j--) {
//            hash2 = 41 * ((hash2 + chars[i]) + (41 * (hash2 + chars[j]))); // 698
//        }
//        if ((lenMid / 2.0) > 0) {
//            hash2 = 57 * hash2 + chars[(lenMid / 2)]; // 698
//        }
//        hash2 %= lowBound;
//			System.out.println("Computed Hash: "+ computedHash);
//        return (hash2 < 0) ? (hash2 + lowBound) : hash2;
//    }

    @Override
    public String toString() {
        return "Anagram{" +
                "key='" + anagram + '\'' +
                ", word='" + word + '\'' +
                '}';
    }
}


