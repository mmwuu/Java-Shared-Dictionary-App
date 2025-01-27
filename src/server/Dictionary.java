//
// This class represents the dictionary data structure. It provides methods to add, remove, query, and
// update words in the dictionary.
// Written by Michael Yixiao Wu | StuID: 1388097
//

package server;


import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Dictionary {

    // The dictionary will use a concurrent hashmap data structure.
    private ConcurrentHashMap<String, List<String>> dictionary;

    // Initialise the dictionary map.
    public Dictionary(ConcurrentHashMap<String, List<String>> dictionary) {
        this.dictionary = dictionary;
    }

    // Add a new word to the dictionary.
    public synchronized String addWord(String word, List<String> meanings) {
        try {
            if (dictionary.containsKey(word)) {
                // print error to console and return error message.
                System.out.println("Error: Word already exists in the dictionary.");
                return "Word not added: Word already exists in the dictionary.";
            } else {
                // Add word and meanings to the dictionary.
                dictionary.put(word, new CopyOnWriteArrayList<>(meanings));
                return "Word added successfully";
            }
        } catch (Exception e) {
            System.out.println("An error occurred while adding the word: " + e.getMessage());
            return "An error occurred while adding the word: " + e.getMessage();
        }
    }

    // Remove a word from the dictionary.
    public synchronized String removeWord(String word) {
        try {
            if (dictionary.remove(word) != null) {
                System.out.println("Word removed successfully");
                return "Word removed successfully";
            } else {
                System.out.println("Word not removed: Word does not exist in the dictionary");
                return "Word not removed: Word does not exist in the dictionary";
            }
        } catch (Exception e) {
            System.out.println("An error occurred while removing the word: " + e.getMessage());
            return "An error occurred while removing the word: " + e.getMessage();
        }
    }

    // Query the meaning(s) of a given word
    public List<String> queryWord(String word) {
        try {
            // return word if it exists in the dictionary.
            if (dictionary.get(word) != null) {
                return dictionary.get(word);
            } else {
                // return error messages as a list.
                System.out.println("Error: Word does not exist in the dictionary.");
                List<String> errorMessageList = new CopyOnWriteArrayList<>();
                errorMessageList.add("Error: Word does not exist in the dictionary.");
                return errorMessageList;
            }
        } catch (Exception e) {
            System.out.println("An error occurred while querying the word: " + e.getMessage());
            List<String> errorMessageList = new CopyOnWriteArrayList<>();
            errorMessageList.add("An error occurred while querying the word: " + e.getMessage());
            return errorMessageList;
        }
    }

    // Update the meaning of a word.
    public synchronized String updateWord(String word, List<String> newMeanings) {
        // Check if the word exists
        try {
            if (dictionary.containsKey(word)) {
                // Get the existing meanings
                List<String> existingMeanings = dictionary.get(word);

                // Add the new meanings if they don't already exist
                for (String newMeaning : newMeanings) {
                    if (!existingMeanings.contains(newMeaning)) {
                        existingMeanings.add(newMeaning);
                    } else {
                        System.out.println("Error: Meaning already exists for the word.");
                        return "Error: Meaning already exists for the word.";

                    }
                }

                // return a confirmation that the word has been updated.
                System.out.println("Successfully updated the meaning(s) of the word.");
                return "Successfully updated the meaning(s) of the word.";
            } else {
                System.out.println("Error: Word does not exist in the dictionary.");
                return "Error: Word does not exist in the dictionary.";
            }
        } catch (Exception e) {
            System.out.println("An error occurred while updating the word: " + e.getMessage());
            return "An error occurred while updating the word: " + e.getMessage();
        }
    }

    // Getter for the ConcurrentHashMap dictionary.
    public ConcurrentHashMap<String, List<String>> getDictionaryMap() {
        return dictionary;
    }
}