//
// This class is responsible for loading and saving the dictionary. It can read the dictionary from the JSON file and
// also serialize it back. The saving function is optional and currently disabled.
// Written by: Michael Yixiao Wu | StuID: 1388097
//

package server;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static server.DictionaryServer.filename;

public class DictionaryManager {
    private Dictionary dictionary;

    public DictionaryManager() {
        this.loadDictionary();
    }

    // Read from dictionary JSON file.
    private void loadDictionary() {
        // Read from a JSON file using GSON.
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filename)) {
            Type type = new TypeToken<ConcurrentHashMap<String, List<String>>>() {}.getType();
            ConcurrentHashMap<String, List<String>> tempDictionary = gson.fromJson(reader, type);

            // Create dictionary.
            if (tempDictionary == null) {
                tempDictionary = new ConcurrentHashMap<>();
            }
            dictionary = new Dictionary(tempDictionary);
        } catch (IOException e) {
            System.out.println("An error occurred while loading the dictionary: " + e.getMessage());
            System.out.println("Creating a new dictionary...");
            dictionary = new Dictionary(new ConcurrentHashMap<>());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while loading the dictionary: " + e.getMessage());
            System.out.println("Creating a new dictionary...");

            dictionary = new Dictionary(new ConcurrentHashMap<>());
        }
    }

    // Save dictionary to JSON
    private synchronized void saveDictionary() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("Data/dictionary.json")) {
            gson.toJson(dictionary.getDictionaryMap(), writer);
        } catch (IOException e) {
            System.err.println("An error occurred while saving the dictionary: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while saving the dictionary: " + e.getMessage());
        }
    }

    // Getter for Dictionary.
    public Dictionary getDictionary() {
        return dictionary;
    }
}