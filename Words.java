import java.util.*;
import java.io.BufferedReader;  
import java.io.FileReader;  
import java.io.IOException;  
import java.util.ArrayList;  
import java.util.HashSet;  
  
public class Words {  
  
    public static ArrayList<String> loadWords(String filename) {  
        ArrayList<String> words = new ArrayList<>();  
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {  
            String line;  
            while ((line = br.readLine()) != null) {  
                words.add(line.trim().toUpperCase()); // Trim and convert to uppercase if needed  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return words;  
    }  
  
    public static String getTargetWord() {  
        ArrayList<String> targetWords = loadWords("wordle-game-words (1).txt");  
        int randomIndex = (int) (Math.random() * targetWords.size());  
        return targetWords.get(randomIndex);  
    }  
  
    public static boolean isRealWord(String guess) {  
        HashSet<String> realWords = new HashSet<>(loadWords("wordle-guess-words (1).txt"));  
        return realWords.contains(guess.toUpperCase());  
    }  
 
}  