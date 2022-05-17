import org.apache.logging.log4j.core.util.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import util.NgramExtractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NgramExtractorTest {
    private static List<String> testArrays;

    @BeforeClass
    public static void setUp() {
        testArrays = new ArrayList<>();
        testArrays.add("");
        testArrays.add("test");
        testArrays.add("This is a test sequence");
    }

    @Test
    public void ngrams_emptyString_emptyNgramList(){
        List<String[]> ngrams = NgramExtractor.ngrams(testArrays.get(0), 3);
        Assert.isEmpty(ngrams);
    }

    @Test
    public void ngrams_emptyStringList_emptyNgramList(){
        ArrayList testList = new ArrayList( Arrays.asList(testArrays.get(0).split(" ")));
        List<String[]> ngrams = NgramExtractor.ngrams(testList, 3);
        Assert.isEmpty(ngrams);
    }

    @Test
    public void ngrams_notEnoughWordsInString_emptyNgramList(){
        List<String[]> ngrams = NgramExtractor.ngrams(testArrays.get(1), 3);
        Assert.isEmpty(ngrams);
    }

    @Test
    public void ngrams_notEnoughWordsInList_emptyNgramList(){
        ArrayList testList = new ArrayList( Arrays.asList(testArrays.get(1).split(" ")));
        List<String[]> ngrams = NgramExtractor.ngrams(testList, 3);
        Assert.isEmpty(ngrams);
    }

    @Test
    public void ngrams_nIsZero_emptyNgramList(){
        ArrayList testList = new ArrayList( Arrays.asList(testArrays.get(2).split(" ")));
        List<String[]> ngrams = NgramExtractor.ngrams(testList, 0);
        Assert.isEmpty(ngrams);
    }

    @Test
    public void ngrams1_nIsZero_emptyNgramList(){
        List<String[]> ngrams = NgramExtractor.ngrams(testArrays.get(2), 3);
        Assert.isEmpty(ngrams);
    }


}
