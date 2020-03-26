package rabin_karp;

import java.util.*;

public class RabinKarpExtended
{
    public static final int NUM_OF_ALPHABET = 10;

    private String text, query;
    private HashMap<Character, Integer> alphabet;
    private StringBuilder numberText;
    private TreeMap<Integer, Integer> number2position;

    public RabinKarpExtended(String text, String query)
    {
        this.text = text;
        this.query = query;
        alphabet = new HashMap<>();
        numberText = new StringBuilder();
        createIndex();
    }

    public Integer search()
    {
        if (alphabet.size() > NUM_OF_ALPHABET) {
            return null;
        }
        int posQuery = 0;
        for (int i = 0; i < numberText.length(); i++) {
            if (Character.toString(numberText.charAt(i)).equals(alphabet.get(query.charAt(posQuery)).toString())) {
                int sizeQuery = query.length() - 1;
                if (posQuery == sizeQuery) {
                    posQuery = i - sizeQuery;
                    break;
                }
                posQuery++;
            } else {
                posQuery = 0;
            }
        }
        return posQuery + 1;
        //ArrayList<Integer> indices = new ArrayList<>();
        //return indices;
    }

    private void createIndex()
    {
        for (int i = 0; i < text.length(); i++) {
            if (!alphabet.containsKey(text.charAt(i))) {
                alphabet.put(text.charAt(i), alphabet.size());
            }
            numberText.append(alphabet.get(text.charAt(i)));
        }
    }
}