package model.Server;

import model.DictionaryException;

import java.sql.SQLException;
import java.util.HashMap;

public class Dictionary {
    DatabaseOperations dictionaryDatabase;

    public Dictionary() {
        this.dictionaryDatabase = new DatabaseOperations();
    }

    public boolean deleteWord(String word) throws SQLException{
        try{
            if(isWordExist(word)) {
                if(this.dictionaryDatabase.delete(word)){
                    return true;
                } else {
                    throw new DictionaryException("Delete word failed.");
                }
            } else {
                throw new DictionaryException("The word is not in the dictionary.");
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public String searchWord(String word) throws DictionaryException {
        try{
            if(isWordExist(word)) {
                HashMap<String, String> wordAndMeanings = this.dictionaryDatabase.searchWordMeanings(word);
                return wordAndMeanings.get(word);
            } else {
                throw new DictionaryException("The word is not in the dictionary.");
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean addWord(String word, String meanings) {
        try {
            if (!isWordExist(word)) {
                if (this.dictionaryDatabase.insert(word, meanings)) {
                    return true;
                } else {
                    throw new DictionaryException("Add word failed.");
                }
            } else {
                throw new DictionaryException("The word is already in the dictionary.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean isWordExist(String word) {
        return this.dictionaryDatabase.searchIsWordExist(word);
    }

}
