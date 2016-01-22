package com.ilkun.autocomplete;

import com.ilkun.autocomplete.util.Tuple;
import com.ilkun.autocomplete.trie.Trie;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PrefixMatches {

    private static final int DEFAULT_NUM_OF_SEQ = 3;
    private static final int MIN_PREF_LENGTH = 2;
    private static final int MIN_K = 1;
    private final Trie trie;
    
    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    // Формирует in-memory словарь слов. Метод принимает слово, строку, массив
    // слов/строк. Если приходит строка, то она разбивается на слова по пробелам.
    // В словарь должны добавляться слова длиннее 2х символов. Предполагается
    // что знаки пунктуации отсутствуют.
    public int add(String... strings) {
        int oldSize = trie.size();
        for (String curString : strings) {
            for (String word : curString.split(" ")) {
                if (word.length() > 2) {
                    trie.add(new Tuple(word.toLowerCase(), word.length()));
                }
            }
        }
        return trie.size() - oldSize;
    }

    // есть ли слово в словаре
    public boolean contains(String word) {
        return trie.contains(word);
    }

    // удаляет слово из словаря
    public boolean delete(String word) {
        return trie.delete(word);
    }

    // к-во слов в словаре
    public int size() {
        return trie.size();
    }

    // если введенный pref длиннее или равен 2м символам, то возвращает набор
    // слов k разных длин начиная с минимальной, и начинающихся 
    // с данного префикса pref.
    // Пример, даны слова следующей длины и pref='abc':
    // abc 3
    // abcd 4
    // abce 4
    // abcde 5
    // abcdef 6
    // - при k=1 возвращаются 'abc'
    // - при k=2 возвращаются 'abc', 'abcd', 'abce'
    // - при k=3 возвращаются 'abc', 'abcd', 'abce', 'abcde'
    // - при k=4 возвращаются 'abc', 'abcd', 'abce', 'abcde', 'abcdef'
    public Iterable<String> wordsWithPrefix(final String pref, final int k) {
        return new Iterable<String>() {

            @Override
            public Iterator<String> iterator() {
                return new Iterator<String>() {

                    Iterator<String> it;
                    String next;
                    int changes = 1;

                    {
                        if (pref.length() < MIN_PREF_LENGTH || k < MIN_K) {
                            it = new Iterator<String>() {

                                @Override
                                public boolean hasNext() {
                                    return false;
                                }

                                @Override
                                public String next() {
                                    throw new NoSuchElementException();
                                }

                            };
                        } else {
                            it = trie.wordsWithPrefix(pref).iterator();
                            if (it.hasNext()) {
                                next = it.next();
                            }
                        }
                    }

                    @Override
                    public boolean hasNext() {
                        return next != null;
                    }

                    @Override
                    public String next() {
                        if (next == null) {
                            throw new NoSuchElementException();
                        }
                        String current = next;
                        if (it.hasNext()) {
                            next = it.next();
                            if (next.length() > current.length()) {
                                changes++;
                                if (changes > k) {
                                    next = null;
                                }
                            }
                        } else {
                            next = null;
                        }
                        return current;
                    }
                };
            }
        };

    }

    // если введенный pref длиннее или равен 2м символам, то возвращает набор
    // слов k=3 разных длин начиная с минимальной, и начинающихся с данного 
    // префикса pref.
    public Iterable<String> wordsWithPrefix(String pref) {
        return wordsWithPrefix(pref, DEFAULT_NUM_OF_SEQ);
    }
}
