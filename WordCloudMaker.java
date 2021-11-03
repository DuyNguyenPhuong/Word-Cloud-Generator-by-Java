import java.util.Random;
import java.util.List;

/**
 * WordCloudMaker.java
 * Sherri Goings, 12 Feb 2013
 * Modified by Jeff Ondich, 14 Feb 2014
 * Modified by Anna Rafferty, 22 February 2015
 */
public class WordCloudMaker {
    private static final String[] HTML_COLORS = new String[] {"FF99CC", "FF6666", "CC0000", "880000",
                                                              "66FFCC", "33CC99", "009966", "006633",
                                                              "00FFFF", "0099FF", "0033FF", "003388",
                                                              "CC99FF", "9933FF", "6600CC", "330066"};

    private static final String HTML_TEMPLATE = "<!DOCTYPE html>\n"
                                                + "<head>\n<title>TITLE</title>\n</head>\n"
                                                + "<body>\n<h1>TITLE</h1>\n"
                                                + "<div style=\"\n width: 800px;\n background-color: rgb(250,250,250);\n"
                                                + " border: 5px grey solid;\ntext-align: center\">\n"
                                                + "WORDS\n"
                                                + "</div>\n"
                                                + "</body>\n</html>\n";

    private static int colorIndex = 0;

    /**
     * Creates a word cloud based on the (word, frequency) pairs in wordCountList.
     * Words are sized (small to large) and colored (light to dark) proportionately to
     * frequency (least frequent to most). Word positions are randomized.
     *
     * NOTE: This method reorders wordCountList.
     *
     * @return a string consisting of HTML that will draw the word cloud.
     * @param title the desired title for the word cloud.
     * @param wordCountList the list of (word, frequency) pairs for which a word cloud is desired.
     */
    public static String getWordCloudHTML(String title, List<WordCount> wordCountList) {
        // Get the maximum and minimum frequencies for the words in the wordCountList.
        int maximumFrequency = 0;
        int minimumFrequency = Integer.MAX_VALUE;
        for (WordCount wordCount : wordCountList) {
            if (wordCount.getCount() > maximumFrequency) {
                maximumFrequency = wordCount.getCount();
            }
            if (wordCount.getCount() < minimumFrequency) {
                minimumFrequency = wordCount.getCount();
            }
        }

        // Shuffle wordCountList.
        Random randomGenerator = new Random();
        for (int k = wordCountList.size(); k > 1; k--) {
            int indexOfItemToSwap = randomGenerator.nextInt(k);
            WordCount tempWordCount = wordCountList.get(k - 1);
            wordCountList.set(k - 1, wordCountList.get(indexOfItemToSwap));
            wordCountList.set(indexOfItemToSwap, tempWordCount);
        }

        // Create a String consisting of HTML encoding of the words in wordCountList,
        // sized and colored appropriately for their frequencies.
        String wordsInHTML = "";
        for (WordCount wordCount : wordCountList) {
            wordsInHTML += makeHTMLWord(wordCount, maximumFrequency, minimumFrequency);
        }

        // Add the HTML code to surround the words with a box
        String document = HTML_TEMPLATE.replaceAll("TITLE", title);
        document = document.replaceAll("WORDS", wordsInHTML);

        return document;
    }

    /**
     * Returns an HTML version of the specified WordCount.
     */
    private static String makeHTMLWord(WordCount wordCount, int maximumFrequency, int minimumFrequency) {
        final int maximumFontSize = 96;
        final int minimumFontSize = 14;
        double ratio = (double)(wordCount.getCount() - minimumFrequency)/(double)(maximumFrequency - minimumFrequency);
        int fontSize = (int)(maximumFontSize * ratio + (1 - ratio) * minimumFontSize);
        String color = HTML_COLORS[(int)((ratio / 1.01) * 4) + colorIndex];
        colorIndex = (colorIndex + 4) % 16;
        String wordString = "<span style=\"color:#" + color + ";font-size:";
        wordString += fontSize + "px;\">&nbsp";
        wordString += wordCount.getWord() + "&nbsp</span>\n";
        return wordString;
    }
}

