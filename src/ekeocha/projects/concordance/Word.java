package ekeocha.projects.concordance;

/**
 * Created by Ekeocha on 9/15/2016.
 */
public class Word {
    private String word;
    private int frequency;
    private StringBuilder sentenceNumbers;
    private long sentenceNumber;

    public Word(String word){
        this.word = word;
        frequency++;
    }

    public void incrementFrequency(){
        frequency++;
    }

    public void setSentenceNumber(long sentenceNumber){ //Using long number keeping mind vast numbers sentences
        this.sentenceNumber = sentenceNumber;
        addSentenceOccurrence(sentenceNumber);
    }

    public int getFrequency() {
        return frequency;
    }

    public StringBuilder getSentenceNumbers() {
        return sentenceNumbers;
    }

    public long getSentenceNumber() {
        return sentenceNumber;
    }

    public void addSentenceOccurrence(long sentenceNumber){
        if(sentenceNumbers != null){
            sentenceNumbers = sentenceNumbers.append("," + sentenceNumber);
        }else {
            sentenceNumbers = new StringBuilder("" + sentenceNumber);
        }
    }

    public String getWord(){
        return this.word;
    }

    @Override
    public String toString() {
        return word + "\t" + "{" + frequency + ":" + sentenceNumbers.toString() + "}";
    }

}
