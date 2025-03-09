import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to my word counter program!");

        System.out.println("Please enter a block of text: ");
        String text = scanner.nextLine().trim();


        String[] words = text.replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase().split("\\s+");

        Map<String, Integer> wordCounts = new HashMap<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
            }
        }

        Map<String, Integer> wordCountTree = new TreeMap<>(wordCounts);
        System.out.println("Word Frequencies:");
        for (Map.Entry<String, Integer> entry : wordCountTree.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

    }
}
