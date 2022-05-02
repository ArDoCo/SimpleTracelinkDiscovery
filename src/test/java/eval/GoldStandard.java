package eval;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public class GoldStandard {
    private File goldStandard;

    private MutableList<MutableList<String>> sentence2instance = Lists.mutable.empty();

    public GoldStandard(File goldStanard) {
        goldStandard = goldStanard;
        load();
    }

    private void load() {
        try (Scanner scan = new Scanner(goldStandard)) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (line == null || line.isBlank() || line.contains("modelElementID")) {
                    // continue if line is empty, null, or is the header (that starts with "modelElementID")
                    continue;
                }

                String[] idXline = line.strip().split(",");
                String instance = idXline[0];
                int sentence = Integer.parseInt(idXline[1]);
                while (sentence2instance.size() <= sentence) {
                    sentence2instance.add(Lists.mutable.empty());
                }
                sentence2instance.get(sentence).add(instance);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ImmutableList<String> getModelInstances(int sentenceNo) {
        // Index starts at 1
        return sentence2instance.get(sentenceNo).toImmutable();
    }

    public ImmutableList<Integer> getSentencesWithElement(String elem) {
        MutableList<Integer> sentences = Lists.mutable.empty();
        for (int i = 0; i < sentence2instance.size(); i++) {
            var instances = sentence2instance.get(i);
            if (instances.anySatisfy(e -> e.equals(elem))) {
                sentences.add(i);
            }
        }
        return sentences.toImmutable();
    }

    public int getTotalNumberOfLinks(){
        int n = 0;
        for(MutableList<String> instances: sentence2instance){
            n+=instances.size();
        }
        return n;
    }
}


