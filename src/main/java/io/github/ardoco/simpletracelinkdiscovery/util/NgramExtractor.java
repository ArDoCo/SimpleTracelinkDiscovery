/* Licensed under MIT 2022. */
package io.github.ardoco.simpletracelinkdiscovery.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NgramExtractor {

    private NgramExtractor() {
        throw new IllegalStateException("Utility class, no instantiation provided");
    }

    public static List<String[]> ngrams(String string, int n) {
        if (n == 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }

        List<String[]> ngramList = new ArrayList<>();
        List<String> words = new ArrayList<>(Arrays.asList(string.split(" ")));

        for (int i = 0; i <= (words.size() - n); i++) {
            ngramList.add(words.subList(i, i + n).toArray(new String[0]));
        }
        return ngramList;
    }

    public static List<String[]> ngrams(List<String> strings, int n) {
        if (n == 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        List<String[]> ngramList = new ArrayList<>();

        for (int i = 0; i <= (strings.size() - n); i++) {
            ngramList.add(strings.subList(i, i + n).toArray(new String[0]));
        }
        return ngramList;
    }

}
