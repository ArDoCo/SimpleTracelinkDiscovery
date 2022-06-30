/* Licensed under MIT 2022. */
package io.github.ardoco.simpletracelinkdiscovery.eval;

public enum Project {
    MEDIASTORE(//
            "./src/test/resources/benchmark/mediastore/original_model/ms.repository", //
            "./src/test/resources/benchmark/mediastore/mediastore.txt", //
            "./src/test/resources/benchmark/mediastore/goldstandard.csv"),
    TEASTORE(//
            "./src/test/resources/benchmark/teastore/original_model/teastore.repository", //
            "./src/test/resources/benchmark/teastore/teastore.txt", //
            "./src/test/resources/benchmark/teastore/goldstandard.csv"),
    TEAMMATES(//
            "./src/test/resources/benchmark/teammates/original_model/teammates.repository", //
            "./src/test/resources/benchmark/teammates/teammates.txt", //
            "./src/test/resources/benchmark/teammates/goldstandard.csv"),
    BIGBLUEBUTTON(//
            "./src/test/resources/benchmark/bigbluebutton/original_model/bbb.repository", //
            "./src/test/resources/benchmark/bigbluebutton/bigbluebutton_1SentPerLine.txt", //
            "./src/test/resources/benchmark/bigbluebutton/goldstandard.csv");

    private final String model;
    private final String textFile;
    private final String goldStandard;

    Project(String model, String textFile, String goldStandard) {
        this.model = model;
        this.textFile = textFile;
        this.goldStandard = goldStandard;
    }

    public String getModel() {
        return model;
    }

    public String getTextFile() {
        return textFile;
    }

    public String getGoldStandard() {
        return goldStandard;
    }

}
