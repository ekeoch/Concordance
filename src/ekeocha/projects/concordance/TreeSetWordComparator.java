package ekeocha.projects.concordance;

import java.util.Comparator;

/**
 * Created by Ekeocha on 9/15/2016.
 */
public class TreeSetWordComparator implements Comparator<Word> {
    @Override
    public int compare(Word word1, Word word2) {
        int compare = word1.getWord().compareToIgnoreCase(word2.getWord());
        //On the event that the TreeSet spots an equivalent ekeocha.projects.concordance.Word entry we increment the frequency and occurrence
        if(compare == 0){
            word2.incrementFrequency();
            word2.addSentenceOccurrence(word1.getSentenceNumber());
        }
        return compare;
    }
}
