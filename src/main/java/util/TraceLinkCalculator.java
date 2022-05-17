package util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import entity.DocumentationSection;
import entity.ModelEntity;
import entity.SimilarityMeasure;
import entity.TraceLink;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.apache.commons.text.similarity.LevenshteinDistance;

import static entity.SimilarityMeasure.*;


public class TraceLinkCalculator {

    private TraceLinkCalculator() {
        throw new IllegalStateException("Utility class, no instantiation provided");
    }
    private static double matchNgram(String[] ngram1, String[] ngram2, SimilarityMeasure similarityMeasure,
                                     double similarityThreshold) {
        double match = 0;

        if(ngram1.length != ngram2.length){
            throw new IllegalArgumentException("The ngrams to be matched must have the same length.");
        }

        for (int i = 0; i<ngram1.length; i++){
            if(similarityMeasure.equals(LEVENSHTEIN)) {
                match += isLevenshteinSimilar(ngram1[i].toLowerCase(), ngram2[i].toLowerCase(), similarityThreshold) ? 1 : 0;
            } else {
                match += isJaroWinklerSimilar(ngram1[i].toLowerCase(), ngram2[i].toLowerCase(), similarityThreshold) ? 1 : 0;
            }
        }

        return (match / ngram1.length);
    }
    private static Boolean isLevenshteinSimilar(String str1, String str2, double threshold){
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        int distance = levenshteinDistance.apply(str1, str2);
        double normalizedDistance = (double)distance / (Math.max(str1.length(), str2.length()));

        return (1.0 - normalizedDistance) >= threshold;
    }

    private static Boolean isJaroWinklerSimilar(String str1, String str2, double threshold){
        JaroWinklerSimilarity jaroWinklerSimilarity = new JaroWinklerSimilarity();
        return jaroWinklerSimilarity.apply(str1, str2) >= threshold;
    }
    public static TraceLink calculateTraceLink(ModelEntity modelEntity, DocumentationSection documentationSection,
                                               SimilarityMeasure similarityMeasure, double similarityThreshold) {
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
                if(ngram1.length == ngram2.length &&
                        matchNgram(ngram1, ngram2, similarityMeasure, similarityThreshold)>= 1.0){
                    matches +=1;
                }
            }
        }
        return new TraceLink(modelEntity, documentationSection, matches);
    }
    public static List<TraceLink> calculateTraceLinks(List<DocumentationSection> documentationSections,
                                                      List<ModelEntity> modelEntities, double matchNumberThreshold,
                                                      SimilarityMeasure similarityMeasure, double similarityThreshold){
        List<TraceLink> traceLinks = new ArrayList<>();

        for(DocumentationSection docuSection: documentationSections){
            for(ModelEntity modelEntity: modelEntities){
                TraceLink traceLink = TraceLinkCalculator.calculateTraceLink(modelEntity, docuSection,
                        similarityMeasure, similarityThreshold);
                if(traceLink.getMatches() > matchNumberThreshold){
                    traceLinks.add(traceLink);
                }
            }
        }
        return traceLinks;
    }
}
