/* Licensed under MIT 2022. */
package io.github.ardoco.simpletracelinkdiscovery.eval;

public enum Project {
    MEDIASTORE(//
            "./src/test/resources/mediastore/original_model/ms.repository", //
            "./src/test/resources/mediastore/mediastore.txt", //
            "./src/test/resources/mediastore/goldstandard.csv"),
    TEASTORE(//
            "./src/test/resources/teastore/original_model/teastore.repository", //
            "./src/test/resources/teastore/teastore.txt", //
            "./src/test/resources/teastore/goldstandard.csv"),
    TEAMMATES(//
            "./src/test/resources/teammates/original_model/teammates.repository", //
            "./src/test/resources/teammates/teammates.txt", //
            "./src/test/resources/teammates/goldstandard.csv"),
    BIGBLUEBUTTON(//
            "./src/test/resources/bigbluebutton/original_model/bbb.repository", //
            "./src/test/resources/bigbluebutton/bigbluebutton.txt", //
            "./src/test/resources/bigbluebutton/goldstandard.csv");

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
