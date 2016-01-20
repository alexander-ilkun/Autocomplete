package com.ilkun.autocomplete.test;

import com.ilkun.autocomplete.PrefixMatches;
import com.ilkun.autocomplete.Trie;
import com.ilkun.autocomplete.Tuple;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PrefixMatchesTest {
    
    @Mock
    Trie trieMock;
    
    @InjectMocks
    PrefixMatches pm;
    
    @Test
    public void addTest() {
        pm.add(new String[]{"test1", "test2"});
    
        verify(trieMock, atLeastOnce()).add(any(Tuple.class));
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
        pm.wordsWithPrefix("test");
    
        verify(trieMock, times(1)).wordsWithPrefix("test");
    }
}
