package com.ilkun.autocomplete;

import com.ilkun.autocomplete.trie.RWayTrie;
import java.util.Iterator;
import static org.junit.Assert.*;
import org.junit.Test;

public class PrefixMatchesTest {
    
    @Test
    public void addTest() {
        PrefixMatches pm = new PrefixMatches(new RWayTrie());
        int expectedSize = 2;
        
        pm.add(new String[]{"testa", "testb"});
    
        assertEquals(expectedSize, pm.size());
    }

    @Test
    public void deleteTest() {
        PrefixMatches pm = new PrefixMatches(new RWayTrie());
        int expectedSize = 0;
        
        pm.add(new String[]{"test"});
        pm.delete("test");
    
        assertEquals(expectedSize, pm.size());
    }

    @Test
    public void containsTest() {
        PrefixMatches pm = new PrefixMatches(new RWayTrie());
        String[] strings = new String[]{"test"};
        
        pm.add(strings);
    
        assertTrue(pm.contains("test"));
    }

    @Test
    public void sizeTest() {
        PrefixMatches pm = new PrefixMatches(new RWayTrie());
        int expectedSize = 0;
        
        assertEquals(expectedSize, pm.size());
    }

    @Test
    public void wordsWithPrefixTest() {
        PrefixMatches pm = new PrefixMatches(new RWayTrie());
        String[] strings = new String[]{"testa", "testb"};
        int expectedCount = 2;
        int actualCount = 0;
        Iterator<String> it;
        
        pm.add(strings);
        it = pm.wordsWithPrefix("test").iterator();
        while (it.hasNext()) {
            it.next();
            actualCount++;
        }
    
        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void wordsWithPrefixAndKTest() {
        PrefixMatches pm = new PrefixMatches(new RWayTrie());
        String[] strings = new String[]{"abc", "abcd", 
            "abce", "abcde", "abcdef"};
        int expectedCount = 3;
        int actualCount = 0;
        Iterator<String> it;
        
        pm.add(strings);
        it = pm.wordsWithPrefix("abc", 2).iterator();
        while (it.hasNext()) {
            it.next();
            actualCount++;
        }
    
        assertEquals(expectedCount, actualCount);
    }
}
