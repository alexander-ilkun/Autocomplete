package com.ilkun.autocomplete.test;

import com.ilkun.autocomplete.RWayTrie;
import com.ilkun.autocomplete.Trie;
import com.ilkun.autocomplete.Tuple;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import org.junit.Test;
import static org.junit.Assert.*;

public class RWayTrieTest {

    @Test
    public void containsExsitingElementTest() {
        Trie trie = new RWayTrie();
        trie.add(new Tuple("test", 4));

        assertTrue(trie.contains("test"));
    }

    @Test
    public void containsUnexsitingElementTest() {
        Trie trie = new RWayTrie();
        trie.add(new Tuple("test", 4));

        assertFalse(trie.contains("anothertest"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addWrongWordTest() {
        Trie trie = new RWayTrie();
        trie.add(new Tuple("_1*/+.", 6));
    }

    @Test
    public void deleteExsitingElementTest() {
        Trie trie = new RWayTrie();
        trie.add(new Tuple("test", 4));

        assertTrue(trie.delete("test"));
    }

    @Test
    public void deleteUnexsitingElementTest() {
        Trie trie = new RWayTrie();
        trie.add(new Tuple("test", 4));

        assertFalse(trie.delete("anothertest"));
    }

    @Test
    public void sizeAddingUnexsitingElementTest() {
        Trie trie = new RWayTrie();
        trie.add(new Tuple("test", 4));

        assertEquals(1, trie.size());
    }

    @Test
    public void sizeAddingExsitingElementTest() {
        Trie trie = new RWayTrie();
        trie.add(new Tuple("test", 4));
        trie.add(new Tuple("test", 4));

        assertEquals(1, trie.size());
    }

    @Test
    public void wordsTest() {
        Trie trie = new RWayTrie();
        List<String> words = Arrays.asList(new String[]{"abc", "abcd"});
        for (String word : words) {
            trie.add(new Tuple(word, word.length()));
        }

        Iterable<String> wordsInTrie = trie.words();
        for (String word : wordsInTrie) {
            assertTrue(words.contains(word));
        }
    }

    @Test
    public void wordsWithPrefTest() {
        Trie trie = new RWayTrie();
        List<String> words = Arrays.asList(new String[]{"abc", "abcd",
        "abce", "abcde", "abcdef"});
        for (String word : words) {
            trie.add(new Tuple(word, word.length()));
        }

        int counter = 0;
        Iterator<String> it = trie.wordsWithPrefix("abc").iterator();
        while (it.hasNext()) {
            it.next();
            counter++;
        }
        assertEquals(5, counter);
    }

    @Test
    public void performanceTest() throws FileNotFoundException {
        Trie trie = new RWayTrie();
        ClassLoader cLoader = PrefixMatchesTest.class.getClassLoader();
        File file = new File(cLoader.getResource("test_data.txt").getFile());
        Scanner sc = new Scanner(file);
        int count = sc.nextInt();
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
    }
}
