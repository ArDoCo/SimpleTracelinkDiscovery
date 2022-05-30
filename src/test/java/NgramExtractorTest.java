import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.NgramExtractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class NgramExtractorTest {
    private static List<String> testArrays;

    @BeforeAll
    public static void setUp() {
        testArrays = new ArrayList<>();
        testArrays.add("");
        testArrays.add("test");
        testArrays.add("This is a test sequence");
    }

    @Test
    void ngrams_emptyString_emptyNgramList(){
        List<String[]> ngrams = NgramExtractor.ngrams(testArrays.get(0), 3);
        Assertions.assertEquals(0, ngrams.size());
    }

    @Test
    void ngrams_emptyStringList_emptyNgramList(){
        ArrayList testList = new ArrayList( Arrays.asList(testArrays.get(0).split(" ")));
        List<String[]> ngrams = NgramExtractor.ngrams(testList, 3);
        Assertions.assertEquals(0, ngrams.size());
    }

    @Test
    void ngrams_notEnoughWordsInString_emptyNgramList(){
        List<String[]> ngrams = NgramExtractor.ngrams(testArrays.get(1), 3);
        Assertions.assertEquals(0, ngrams.size());
    }

    @Test
    void ngrams_notEnoughWordsInList_emptyNgramList(){
        ArrayList testList = new ArrayList( Arrays.asList(testArrays.get(1).split(" ")));
        List<String[]> ngrams = NgramExtractor.ngrams(testList, 3);
        Assertions.assertEquals(0, ngrams.size());
    }

    @Test
    void ngrams_nIsZero_throwIllegalArgumentException(){
        ArrayList testList = new ArrayList( Arrays.asList(testArrays.get(2).split(" ")));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            NgramExtractor.ngrams(testList, 0);
        });

        String expectedMessage = "n must be greater than 0";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void ngrams1_nIsZero_throwIllegalArgumentException(){

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            NgramExtractor.ngrams(testArrays.get(2), 0));

        String expectedMessage = "n must be greater than 0";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}
