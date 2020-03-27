package rabin_karp;

import java.util.*;

public class RabinKarpExtended
{
    public static final int NUM_OF_ALPHABET = 10;

    private String text;
    private String query;
    private String numberQuery;
    private String numberText;
    private boolean isAlphabetCorrect;
    private TreeMap<String, List<Integer>> number2position;

    public RabinKarpExtended(String text, String query)
    {
        this.text = text;
        this.query = query;
        this.numberQuery = "";
        this.numberText = "";
        this.isAlphabetCorrect = true;
        number2position = new TreeMap<>();
        createIndex();
    }

    public List<Integer> search()
    {
        if (!isAlphabetCorrect) {
            return null;
        }
        int numOfSymbols = 1;
        while (numOfSymbols < numberText.length()) {
            int i = 0;
            while (i + numOfSymbols <= numberText.length()) {
                //if ((i + 1) * numOfSymbols <= numberText.length()) {
                    if (number2position.containsKey(numberText.substring(i, i + numOfSymbols))) {
                        number2position.get(numberText.substring(i, i + numOfSymbols))
                                .add(i);
                    } else {
                        ArrayList<Integer> l = new ArrayList<>();
                        l.add(i);
                        number2position.put(numberText.substring(i, i + numOfSymbols), l);
                    }
                //}
                i++;
            }
            numOfSymbols++;
        }
        return number2position.get(numberQuery);
    }

    private void createIndex()
    {
        HashMap<Character, Integer> alphabet = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            if (!alphabet.containsKey(text.charAt(i))) {
                alphabet.put(text.charAt(i), alphabet.size());
            }
            if (alphabet.size() > NUM_OF_ALPHABET) {
                isAlphabetCorrect = false;
                break;
            }
            numberText = numberText + alphabet.get(text.charAt(i));
        }
        for (int i = 0; i < query.length(); i++) {
            numberQuery = numberQuery + alphabet.get(query.charAt(i));
        }
        //System.out.println(numberText);
    }
}