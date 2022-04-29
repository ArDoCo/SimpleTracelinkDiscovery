package util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import entity.DocumentationSection;
import entity.ModelEntity;
import entity.TraceLink;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.apache.commons.text.similarity.LevenshteinDistance;


public class TraceLinkCalculator {
    //UserDBAdapter
    private Boolean equalArray(String[] array1, String[] array2){
        return IntStream.range(0, array1.length).allMatch(i -> array1[i].equalsIgnoreCase(array2[i]));
    }
    private static double matchNgram(String[] ngram1, String[] ngram2) {
        double match = 0;

        if(ngram1.length != ngram2.length){
            throw new IllegalArgumentException("The ngrams to be matched must have the same length.");
        }

        for (int i = 0; i<ngram1.length; i++){
                match += isLevenshteinSimilar(ngram1[i], ngram2[i], 0.1) ? 1 : 0;
        }

        return (match / ngram1.length);
    }

    private static Boolean isStringSimilar(String str1, String str2){
        return str1.equalsIgnoreCase(str2);
    }

    public static Boolean isLevenshteinSimilar(String str1, String str2, double threshold){
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        int distance = levenshteinDistance.apply(str1, str2);
        double normalizedDistance = (double)distance / (Math.max(str1.length(), str2.length()));

        return normalizedDistance < threshold;
    }

    private static Boolean isJaroWinklerSimilar(String str1, String str2, double threshold){
        JaroWinklerSimilarity jaroWinklerSimilarity = new JaroWinklerSimilarity();
        return jaroWinklerSimilarity.apply(str1, str2) > threshold;
    }
    public static TraceLink calculateTraceLink(ModelEntity modelEntity, DocumentationSection documentationSection, double threshold) {

        int matches = 0;

        List<String[]> modelEntityNgrams = new ArrayList<>();
        modelEntityNgrams.add(new String[] {modelEntity.getName()});
        List<String[]> documentationSectionNgrams = NgramExtractor.ngrams(documentationSection.getText(), 1);

        for(int i = 2; i<=3; i++){
            modelEntityNgrams.addAll(NgramExtractor.ngrams(modelEntity.getNameParts(), i));
            documentationSectionNgrams.addAll(NgramExtractor.ngrams(documentationSection.getText(), i));
        }

        for(String[] ngram2: modelEntityNgrams) {
            for (String[] ngram1 : documentationSectionNgrams) {
                if(ngram1.length == ngram2.length && matchNgram(ngram1, ngram2)>= threshold){
                    matches +=1;
                }
            }
        }
        return new TraceLink(modelEntity, documentationSection, matches);
    }
}
