import java.util.*;

/**
 * This class provides a tree structure than can hold words and their counts.
 * It also provides methods to access and modify the elements in the tree so that it can be analyzed.
 * This class also holds the tests to demonstrate successful completion of the rubric items.
 * 
 * @author Malachy Guzman
 * @author Duy Nguyen 
 */
public class WordCountTree {
  Node root;
  static int nodeCounter = 0;

  // Node constructor
  private class Node {
    char character;
    int count;
    List<Node> children = new LinkedList<Node>();

    public Node(char ch, int in){
      character = ch;
      count = in;
    }

    public Node() {
      count = 0;
    }
  }
        

  /**
   * Constructs an empty WordCountTree.
   */  
  public WordCountTree(){
    root = new Node('\0', 0);
  }
  
 /**
  * Recursive method to help the incrementCount method
  * It return the Node of the last character of the Word or add new Node if the word
  does not exsist
  * @param word (String) the word we want to increment or add
  * @param index (int) the index of the character in the word
  * @param subRoot (Node) the Node that we are in
  * @return (Node) the Node that last character of the word in, or null if there is no Node 
  */
  public Node incrementCountHelper(String word, int index, Node subRoot){
      if(index == word.length()){
          return subRoot;
      }    
      else{
        if(subRoot.children.size() == 0){//subRoot's children doesn't exist
            if(index == word.length()){ //final letter
              subRoot.children.add(new Node(word.charAt(index), 1));
              return subRoot;
            }
            else{//not final letter
              // System.out.println(word.charAt(index));
              subRoot.children.add(new Node(word.charAt(index), 0));
              return incrementCountHelper(word, index+1, subRoot.children.get(0));
            }
        }

        else{ // word does exist
          boolean letterExists = false;
          for(int i=0; i < subRoot.children.size(); i++){
            if(subRoot.children.get(i).character == word.charAt(index)){
              letterExists = true;
              return incrementCountHelper(word, index+1, subRoot.children.get(i));
            }
          }
          if(!letterExists){ // word does not exist
            subRoot.children.add(new Node(word.charAt(index), 0));
            return incrementCountHelper(word, index+1, subRoot.children.get(subRoot.children.size()-1));
          }
        }
      }
      return null;
  }


  /**
   * Adds 1 to the existing count for word, or adds word to the WordCountTree
   * with a count of 1 if it was not already present.
   * Implementation must be recursive, not iterative.
   * @param word (String) the word to increment or add
   */
  public void incrementCount(String word){
      Node finalChar = incrementCountHelper(word, 0, root);
      finalChar.count++;
  }
  
  /**
   * Recursive method the helpp the contain method
   * @param word (String) the word we want to check for
   * @param index (int) the index of the character in the word
   * @param subRoot (Node) the Node that we are in
   * @return (boolean) true if the tree contains word, false if the tree
   * does not contain the word
   */
  public boolean containsHelper(String word, int index, Node subRoot){
      if(index == word.length() && subRoot.count>0){
        return true;
      }
      else{
        for(int i=0; i<subRoot.children.size(); i++){
          if(index >= word.length()){
            return false;
          }
          else if(subRoot.children.get(i).character == word.charAt(index)){ //next letter exists
             return containsHelper(word, index+1, subRoot.children.get(i));
          }
        }
      }
      return false;
}

/**
 * Returns true if word is stored in this WordCountTree with
 * a count greater than 0, and false otherwise.
 * Implementation must be recursive, not iterative.
 * @param word (String) The word we want to check for
 */
  public boolean contains(String word){
    return containsHelper(word, 0, root);
  }
  

  /**
   * Recursive methods that help the getCount method
   * @param word (String) the word we want to get the count of
   * @param index (int) the index of the character in the word
   * @param subRoot (Node) the Node that we are in
   * @return (Node) the Node that have the last character of the word (whose count variable
   * is number of the word) or null of the word is not in the tree
   */
  public Node getCountHelper(String word, int index, Node subRoot){
    if(index == word.length() && subRoot.count>0){
      return subRoot;
    }
    else{
      for(int i=0; i<subRoot.children.size(); i++){
        if(index >= word.length()){
          return null;
        }
        else if(subRoot.children.get(i).character == word.charAt(index)){ //next letter exists
           return getCountHelper(word, index+1, subRoot.children.get(i));
        }
      }
    }
    return null;
}



/**
 * Returns the count of word, or -1 if word is not in the WordCountTree.
 * Implementation must be recursive, not iterative.
 * @param word (String) the word we want to get the count of
 */
  public int getCount(String word){
    if(getCountHelper(word, 0, root) == null){
      return -1;
    }
    else{
      return getCountHelper(word, 0, root).count;
    }
  }




  /**
   * the Recursive method that helps getNodeCount method
   * @param subRoot (Node) the node we want to start at (make it a root of a subroot)
   * @return (int) number of the Node
   */
  public int getNodeCountHelper(Node subRoot){
    nodeCounter += subRoot.children.size();
    for(int i=0; i<subRoot.children.size(); i++){
      getNodeCountHelper(subRoot.children.get(i)); //add parent and all its children
    }
   return nodeCounter;

  }

/** 
 * Returns a count of the total number of nodes in the tree. 
 * A tree with only a root is a tree with one node; it is an acceptable
 * implementation to have a tree that represents no words have either
 * 1 node (the root) or 0 nodes.
 * Can be recursive or use an instance variable.
 */
  public int getNodeCount(){
    if(root == null){
      return 0;
    }
    else{
      return getNodeCountHelper(root);
    }
  }
  
  /** 
  * Creates and sorts in decreasing order a list 
  * of WordCount objects, one per word stored in this 
  * WordCountTree.
  *
  * @return a List of WordCount objects in decreasing order
  */
  public List<WordCount> getWordCountsByCount(){
    List<WordCount> wordCountList = new ArrayList<WordCount>();
    getWordCountsHelper(root, "", wordCountList);
    Collections.sort(wordCountList, new SortWordCount());
    return wordCountList;
  }

  private void getWordCountsHelper(Node node, String wordSoFar, List<WordCount> wordCountList) {
    if(node.children.size() == 0) {
      return;
    }
    for(Node child : node.children) {
      String nextWord = wordSoFar + child.character;

      if(child.count != 0) {
        WordCount newWord = new WordCount(nextWord, child.count);
        wordCountList.add(newWord);
      }

      getWordCountsHelper(child, nextWord, wordCountList);
    }
  }

  /**
   * In the main method, we test our methods and allow the user to see individual method tests.
   * @param args
   */
  public static void main(String[] args) {
      WordCountTree tree = new WordCountTree();
      String test = args[0];

      if(test.equals("1")){ //incrementCount
        System.out.println("\nincrementCount() Test:\n");
        System.out.println("Adding the words cat, car, bag, and anderson to the tree");
        tree.incrementCount("cat");
        tree.incrementCount("car");
        tree.incrementCount("bag");
        tree.incrementCount("anderson");

        System.out.println("Printing the count of cat, car, bag, and anderson:");
        System.out.println("cat count: " + tree.getCount("cat"));
        System.out.println("car count: " + tree.getCount("car"));
        System.out.println("bag count: " + tree.getCount("bag"));
        System.out.println("anderson count: " + tree.getCount("anderson"));
        System.out.println("This shows that incrementCount() can add new words\n");

        System.out.println("Now adding another car, another bag, and three more andersons:");
        tree.incrementCount("car");
        tree.incrementCount("bag");
        tree.incrementCount("anderson");
        tree.incrementCount("anderson");
        tree.incrementCount("anderson");

        System.out.println("\nPrinting the count of cat, car, bag, and anderson:");
        System.out.println("cat count: " + tree.getCount("cat"));
        System.out.println("car count: " + tree.getCount("car"));
        System.out.println("bag count: " + tree.getCount("bag"));
        System.out.println("anderson count: " + tree.getCount("anderson"));
        System.out.println("This shows that incrementCount() can increase the count of old words\n");
      }

      else if(test.equals("2")){ //contains
        System.out.println("\ncontains() Test:\n");
        System.out.println("Adding the words cat, car, bag, and anderson to the tree");
        tree.incrementCount("cat");
        tree.incrementCount("car");
        tree.incrementCount("bag");
        tree.incrementCount("anderson");

        System.out.println("Printing the whether cat, car, bag, anderson exist, as well as olin (not in tree):\n");
        System.out.println("cat? " + tree.contains("cat"));
        System.out.println("car? " + tree.contains("car"));
        System.out.println("bag? " + tree.contains("bag"));
        System.out.println("anderson? " + tree.contains("anderson"));
        System.out.println("olin? " + tree.contains("olin"));
        System.out.println("\nThis shows that contains works for words that both exist and don't exist.\n");
      }

      else if(test.equals("3")){  //getCount
        System.out.println("\ngetCount() Test:\n");
        System.out.println("Adding the words cat 3 time and car 1 time to the tree");
        tree.incrementCount("cat");
        tree.incrementCount("cat");
        tree.incrementCount("cat");
        tree.incrementCount("car");

        System.out.println("Printing the count of cat, car, and Duy (Duy not in tree):\n");
        System.out.println("cat count: " + tree.getCount("cat"));
        System.out.println("car count: " + tree.getCount("car"));
        System.out.println("Duy count: " + tree.getCount("Duy"));
        System.out.println("\nThis shows that getCount() works for words that both exist and don't exist.\n");        
      }

      else if(test.equals("4")){  //getNodeCount
        System.out.println("\ngetNodeCount() Test:\n");
        System.out.println("Adding the words cat, car, bag, and Anya");
        tree.incrementCount("cat");
        tree.incrementCount("car");
        tree.incrementCount("bag");
        tree.incrementCount("Anya");

        System.out.println("Printing the total number of nodes (should be 11)\n");
        System.out.println("Total word count: " + tree.getNodeCount());
       
        System.out.println("\nThis shows that getNodeCount() works.\n");
      }
      else{
        System.out.println("Invalid input");
        System.out.println("Run this program again and type 1 to see incrementCount, 2 to see contains, 3 to see getCount, or 4 to get getNodeCount.");
        System.exit(1);
      }
  }
}