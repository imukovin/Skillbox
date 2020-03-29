package rabin_karp;

import java.util.*;

public class RabinKarpExtended
{
    public static final int NUM_OF_ALPHABET = 256;
    public static final int SIMPLE_NUM = 101;

    private String text;
    private String query;
    private int m, n, p, t, h;
    private TreeMap<Integer, List<Integer>> number2position;

    public RabinKarpExtended(String text)
    {
        this.text = text;
        n = text.length();
        number2position = new TreeMap<>();
        //createIndex();
    }

    public void create(String query) {
        this.query = query;
        p = 0;
        t = 0;
        h = 1;
        m = query.length();

        for (int i = 0; i < m - 1; i++) {
            h = (h * NUM_OF_ALPHABET) % SIMPLE_NUM;
        }

        for (int i = 0; i < m; i++) {
            p = (NUM_OF_ALPHABET * p + query.charAt(i)) % SIMPLE_NUM;
            t = (NUM_OF_ALPHABET * t + text.charAt(i)) % SIMPLE_NUM;
        }
    }

    public List<Integer> search()
    {
        if (number2position.get(p) == null) {
            for (int i = 0; i <= n - m; i++) {
                if (p == t) {
                    int j = 0;
                    for (j = 0; j < m; j++) {
                        if (text.charAt(i + j) != query.charAt(j)) {
                            break;
                        }
                    }
                    if (j == m) {
                        if (!number2position.containsKey(p)) {
                            ArrayList<Integer> l = new ArrayList<>();
                            l.add(i);
                            number2position.put(p, l);
                        } else {
                            number2position.get(p).add(i);
                        }
                    }
                }
                if (i < n - m) {
                    t = (NUM_OF_ALPHABET * (t - text.charAt(i) * h) + text.charAt(i + m)) % SIMPLE_NUM;
                    if (t < 0) {
                        t = t + SIMPLE_NUM;
                    }
                }
            }
        }
        return number2position.get(p);
    }

    /*private void createIndex()
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
    }*/
}