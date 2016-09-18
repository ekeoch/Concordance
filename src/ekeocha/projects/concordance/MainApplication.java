package ekeocha.projects.concordance;

import java.io.*;
import java.text.BreakIterator;
import java.util.Comparator;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ekeocha on 9/16/2016.
 */
public class MainApplication {
    private SortedSet<Word> concordance;
    private Comparator<Word> treeSetComparator;
    private Pattern compiledPattern;
    private final static String WORD_PATTERN = "[\\w\\.!\\?]+(?!$)";

    private final static String USAGE = "ekeocha.projects.concordance.MainApplication [file]\n" +
            "       Ex. ekeocha.projects.concordance.MainApplication C:/Users/Ekeocha/IdeaProjects/Concordance/input.txt";

    public MainApplication() {
        this.treeSetComparator = new TreeSetWordComparator();
        this.concordance = new TreeSet<>(treeSetComparator);
        this.compiledPattern = Pattern.compile(WORD_PATTERN, Pattern.CASE_INSENSITIVE);
    }

    private static boolean validateInput(String path){
        File input = new File(path);
        return input.exists() && !input.isDirectory();
    }

    private static void validateArgs(String[] args){
        if(args.length != 1){
            System.out.println(USAGE);
            System.exit(1);
        }

        if(!validateInput(args[0])){
            final String INVALID_PATH = "File " + args[0] + " is directory or doesn't exist.";
            System.out.println(INVALID_PATH);
            System.exit(1);
        }
    }

    private SortedSet<Word> process(String path){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String text;
            long sentenceCounter = 0;
            while((text = bufferedReader.readLine())!= null){ //Processing the file line by line ensures memory conservation
                BreakIterator breakIterator = BreakIterator.getSentenceInstance(Locale.ENGLISH);
                breakIterator.setText(text);
                int start = breakIterator.first();
                for(int end = breakIterator.next(); end != breakIterator.DONE; start = end, end = breakIterator.next()){
                    String sentence = text.substring(start,end);
                    sentence = sentence.trim(); //Bug fix: The Break Iterator seems to keep the whitespace between sentences.
                    sentenceCounter++;
                    Matcher matcher = compiledPattern.matcher(sentence);
                    while (matcher.find()){
                        Word word = new Word(matcher.group());
                        word.setSentenceNumber(sentenceCounter);
                        concordance.add(word);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return concordance;
    }

    public static void main(String[] args) {
        MainApplication.validateArgs(args);
        MainApplication mainApplication = new MainApplication();
        SortedSet<Word> concordance = mainApplication.process(args[0]);

        //Print concordance list into any format desired. For this demo i'll just dump
        //the entire list to stdout
        for(Word word: concordance){
            System.out.printf("%-20s%s%n",word.getWord(),"{" + word.getFrequency() + ":" + word.getSentenceNumbers() + "}");
        }
    }
}
