package com.ilkun.autocomplete.trie;

import com.ilkun.autocomplete.util.Tuple;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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
        int expectedSize = 1;
        
        trie.add(new Tuple("test", 4));

        assertEquals(expectedSize, trie.size());
    }

    @Test
    public void sizeAddingExsitingElementTest() {
        Trie trie = new RWayTrie();
        int expectedSize = 1;
        
        trie.add(new Tuple("test", 4));
        trie.add(new Tuple("test", 4));

        assertEquals(expectedSize, trie.size());
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
        int actualSize = 0;
        int expectedSize = 5;
        
        for (String word : words) {
            trie.add(new Tuple(word, word.length()));
        }
        Iterator<String> it = trie.wordsWithPrefix("abc").iterator();
        while (it.hasNext()) {
            it.next();
            actualSize++;
        }
        
        assertEquals(expectedSize, actualSize);
    }
}
