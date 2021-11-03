# Word Counter

## Overview

This project creates a word cloud from a text file. It allows the user to specify how many words they want to see, and then the word cloud scales the each individual word's size relative to how many times it appears in the text.


## Usage

To run the program, do the following:
```java
javac *.java
java WordCount
```
The user will then be prompted and if they follow the instructions:
```java
Hello! Input the name of the file you'd like to process:
$ WutheringHeights.txt
How many words would you like to display?
$ 25
```

An HTML file that contains the word cloud will then be printed to the console, and if run in a viewer or pasted into an HTML file and run, the user will be able to see the word cloud.


## Rubric Items Demonstration
Below is a demonstration with user input and code lines for each of the rubric items:

Run the following, replacing x with 1, 2, 3, or 4 depending on what you want to show:
```java
javac *.java
java WordCountTree x
```


### incrementCount Method
After compiling, run
```java
java WordCountTree 1
```
This will run the incrementCount test, which will show: 
```
incrementCount() Test:

Adding the words cat, car, bag, and anderson to the tree
Printing the count of cat, car, bag, and anderson:
cat count: 1
car count: 1
bag count: 1
anderson count: 1
This shows that incrementCount() can add new words

Now adding another car, another bag, and three more andersons:

Printing the count of cat, car, bag, and anderson:
cat count: 1
car count: 2
bag count: 2
anderson count: 4
This shows that incrementCount() can increase the count of old words
```
Reference lines `48-92` and `242-270` of `WordCountTree.java` to see the methods this test calls and the test code itself. 

### contains Method
After compiling, run
```java
java WordCountTree 2
```
This will display the contains test in the terminal, which will show:

```
contains() Test:

Adding the words cat, car, bag, and anderson to the tree
Printing the whether cat, car, bag, anderson exist, as well as olin (not in tree):

cat? true
car? true
bag? true
anderson? true
olin? false

This shows that contains works for words that both exist and don't exist.
```
Reference lines `102-127` and `272-287` of `WordCountTree.java` to see the methods this test calls and the test code itself. 

### getCount Method
After compiling, run
```java
java WordCountTree 3
```
This will display the getCount test in the terminal, which will show:
```
getCount() Test:

Adding the words cat 3 time and car 1 time to the tree
Printing the count of cat, car, and Duy (Duy not in tree):

cat count: 3
car count: 1
Duy count: -1

This shows that getCount() works for words that both exist and don't exist.
```
Reference lines `138-169` and `289-301` of `WordCountTree.java` to see the methods this test calls and the test code itself. 


### getNodeCount Method
After compiling, run
```java
java WordCountTree 4
```
This will display the getNodeCount test in the terminal, which will show:
```
Adding the words cat, car, bag, and Anya
Printing the total number of nodes (should be 11)

Total word count: 11

This shows that getNodeCount() works.
```
Reference lines `179-202` and `304-316` of `WordCountTree.java` to see the methods this test calls and the test code itself. 

