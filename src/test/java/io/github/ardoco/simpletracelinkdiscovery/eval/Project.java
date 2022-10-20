/* Licensed under MIT 2022. */
package io.github.ardoco.simpletracelinkdiscovery.eval;

public enum Project {
    MEDIASTORE(//
            "src/test/resources/benchmark/mediastore/model_2016/pcm/ms.repository", //
            "src/test/resources/benchmark/mediastore/text_2016/mediastore.txt", //
            "src/test/resources/benchmark/mediastore/text_2016/goldstandard.csv"
    ), //
    TEASTORE( //
            "src/test/resources/benchmark/teastore/model_2020/pcm/teastore.repository", //
            "src/test/resources/benchmark/teastore/text_2020/teastore.txt", //
            "src/test/resources/benchmark/teastore/text_2020/goldstandard.csv"
    ), //
    TEASTORE_HISTORIC( //
            "src/test/resources/benchmark/teastore/model_2020/pcm/teastore.repository", //
            "src/test/resources/benchmark/teastore/text_2018/teastore_2018_AB.txt", //
            "src/test/resources/benchmark/teastore/text_2018/goldstandard_AB.csv"
    ), //
    TEAMMATES( //
            "src/test/resources/benchmark/teammates/model_2021/pcm/teammates.repository", //
            "src/test/resources/benchmark/teammates/text_2021/teammates.txt", //
            "src/test/resources/benchmark/teammates/text_2021/goldstandard.csv"
    ), //
    TEAMMATES_HISTORIC( //
            "src/test/resources/benchmark/teammates/model_2021/pcm/teammates.repository", //
            "src/test/resources/benchmark/teammates/text_2015/teammates_2015.txt", //
            "src/test/resources/benchmark/teammates/text_2015/goldstandard.csv"
    ), //
    BIGBLUEBUTTON( //
            "src/test/resources/benchmark/bigbluebutton/model_2021/pcm/bbb.repository", //
            "src/test/resources/benchmark/bigbluebutton/text_2021/bigbluebutton.txt", //
            "src/test/resources/benchmark/bigbluebutton/text_2021/goldstandard.csv"
    ), //
    BIGBLUEBUTTON_HISTORIC( //
            "src/test/resources/benchmark/bigbluebutton/model_2021/pcm/bbb.repository", //
            "src/test/resources/benchmark/bigbluebutton/text_2015/bigbluebutton_2015.txt", //
            "src/test/resources/benchmark/bigbluebutton/text_2015/goldstandard.csv"
    ), //
    JABREF( //
            "src/test/resources/benchmark/jabref/model_2021/pcm/jabref.repository", //
            "src/test/resources/benchmark/jabref/text_2021/jabref.txt", //
            "src/test/resources/benchmark/jabref/text_2021/goldstandard.csv"
    ), //
    JABREF_HISTORIC( //
            "src/test/resources/benchmark/jabref/model_2021/pcm/jabref.repository", //
            "src/test/resources/benchmark/jabref/text_2016/jabref_2016.txt", //
            "src/test/resources/benchmark/jabref/text_2016/goldstandard.csv"
    );

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
