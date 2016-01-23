package com.ilkun.autocomplete.trie;

import com.ilkun.autocomplete.PrefixMatchesRWayTrieTest;
import com.ilkun.autocomplete.trie.RWayTrie;
import com.ilkun.autocomplete.trie.Trie;
import com.ilkun.autocomplete.util.Tuple;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import static org.junit.Assert.*;
import org.junit.Test;

public class RWayTriePerformanceTest {
    
    @Test
    public void performanceTest() throws FileNotFoundException {
        Trie trie = new RWayTrie();
        ClassLoader cLoader = PrefixMatchesRWayTrieTest.class.getClassLoader();
        File file = new File(cLoader.getResource("test_data.txt").getFile());
        Scanner sc = new Scanner(file);
        int count = sc.nextInt();
        int expectedSize = 0;
        String wordToAdd;
        
        for (int i = 0; i < count; i++) {
            sc.nextLong();
            wordToAdd = sc.next();
            trie.add(new Tuple(wordToAdd, wordToAdd.length()));
        }
        sc.close();
        Iterable<String> wordsInTrie = trie.words();
        for (String wordToDelete : wordsInTrie) {
            trie.delete(wordToDelete);
        }
    
        assertEquals(expectedSize, trie.size());
    }
}
