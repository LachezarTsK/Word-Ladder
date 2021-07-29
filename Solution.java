import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Solution {

    TrieNode root = new TrieNode();

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        addAllWords(wordList);
        if (!wordIsInDictionary(endWord)) {
            return 0;
        }
        if (beginWord.length() == 1) {
            return 2;
        }

        return searchShortestPath(beginWord.toCharArray(), endWord.toCharArray());
    }

    public int searchShortestPath(char[] beginWord, char[] endWord) {
        LinkedList<char[]> queueWords = new LinkedList<>();
        queueWords.add(beginWord);

        int minSteps = 1;
        while (!queueWords.isEmpty()) {
            int wordsOfNextStep = queueWords.size();

            while (wordsOfNextStep-- > 0) {
                char[] currentWord = queueWords.removeFirst();
                if (Arrays.equals(currentWord, endWord)) {
                    return minSteps;
                }
                searchForNeighbours(currentWord, queueWords);
            }
            minSteps++;
        }
        return 0;
    }

    public void searchForNeighbours(char[] word, LinkedList<char[]> queueWords) {

        for (int i = 0; i < word.length; i++) {
            char ch = word[i];
            for (char j = 'a'; j <= 'z'; j++) {

                if (ch != j) {
                    word[i] = j;
                    TrieNode lastCharWord = wordIsInDictionary(word);

                    if (lastCharWord != null && lastCharWord.isEndOfWord && !lastCharWord.isVisited) {
                        lastCharWord.isVisited = true;
                        queueWords.add(word.clone());
                    }
                }
            }
            //Rrestore original word, in order to check the other possible combinations.
            word[i] = ch;
        }

    }

    public boolean wordsDifferInExactlyOneChar(String wordOne, String wordTwo) {
        int difference = 0;
        for (int i = 0; i < wordOne.length(); i++) {
            if (wordOne.charAt(i) != wordTwo.charAt(i)) {
                if (++difference > 1) {
                    return false;
                }
            }
        }
        return difference == 1;
    }

    public void addAllWords(List<String> wordList) {
        for (String word : wordList) {
            addWord(word);
        }
    }

    public void addWord(String word) {
        TrieNode current = root;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);

            if (current.branches[ch - 'a'] == null) {
                current.branches[ch - 'a'] = new TrieNode();
            }
            current = current.branches[ch - 'a'];
        }
        current.isEndOfWord = true;
    }

    //Overloaded method of 'public boolean wordIsInDictionary(String word)'
    public TrieNode wordIsInDictionary(char[] word) {
        TrieNode current = root;

        for (int i = 0; i < word.length; i++) {
            char ch = word[i];

            if (current.branches[ch - 'a'] == null) {
                return null;
            }
            current = current.branches[ch - 'a'];
        }
        return current;
    }

    public boolean wordIsInDictionary(String word) {
        TrieNode current = root;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);

            if (current.branches[ch - 'a'] == null) {
                return false;
            }
            current = current.branches[ch - 'a'];
        }
        return current.isEndOfWord;
    }
}

class TrieNode {

    TrieNode[] branches;
    boolean isEndOfWord;
    boolean isVisited;
    int CHARS_IN_ALPHABET = 26;

    public TrieNode() {
        branches = new TrieNode[CHARS_IN_ALPHABET];
    }
}
