package com.ilkun.autocomplete;

import com.ilkun.autocomplete.util.Tuple;
import com.ilkun.autocomplete.trie.Trie;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PrefixMatchesRWayTrieTest {
    
    @Mock
    Trie trieMock;
    
    @InjectMocks
    PrefixMatches pm;
    
    @Test
    public void addTest() {
        pm.add(new String[]{"testa", "testb"});
    
        verify(trieMock, atLeastOnce()).add(any(Tuple.class));
    }

    @Test
    public void addCallsSizeTwoTimesTest() {
        pm.add(new String[]{"testa", "testb"});
    
        verify(trieMock, times(2)).size();
    }
    
    @Test
    public void deleteTest() {
        pm.delete("test");
    
        verify(trieMock).delete(any(String.class));
    }

    @Test
    public void containsTest() {
        pm.contains("test");
    
        verify(trieMock).contains(any(String.class));
    }

    @Test
    public void sizeTest() {
        pm.size();
    
        verify(trieMock).size();
    }

    @Test
    public void wordsWithPrefixTest() {
        pm.wordsWithPrefix("test").iterator();
    
        verify(trieMock, times(1)).wordsWithPrefix("test");
    }

    @Test
    public void wordsWithPrefixAndKTest() {
        pm.wordsWithPrefix("test", 4).iterator();
    
        verify(trieMock, times(1)).wordsWithPrefix("test");
    }
}
