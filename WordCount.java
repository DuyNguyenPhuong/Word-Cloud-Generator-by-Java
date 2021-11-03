import java.util.*;

public class WordCount{

  public String word;
  public int count;

  public WordCount(String myWord, int myCount) {
    word = myWord;
    count = myCount;
  }
  
/**
 * Gets the word stored by this WordCount
 */
public String getWord(){
  return word;
}
 
/** 
 * Gets the count stored by this WordCount
 */
 public int getCount() {
   return count;
 }  

 public String toString() {
   return word + ": " + count;
 }

}

/* A class that allows for sorting WordCounts */
class SortWordCount implements Comparator<WordCount> {

  public int compare(WordCount a, WordCount b)
  {
    return b.count - a.count;
  }

}