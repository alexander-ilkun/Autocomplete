package com.ilkun.autocomplete;

import java.util.Iterator;
import java.util.LinkedList;

public class PrefixMatches {

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
    public Iterable<String> wordsWithPrefix(String pref, int k) {
        LinkedList<String> result = new LinkedList<>();
        Iterator<String> it = trie.wordsWithPrefix(pref).iterator();
        int lastLength = 0;
        if (k >= 1 && pref.length() >= 2) {
            while (it.hasNext()) {
                String curWord = it.next();
                if (curWord.length() > lastLength) {
                    lastLength = curWord.length();
                    k--;
                }
                if (k < 0) {
                    break;
                }
                result.addLast(curWord);
            }
        }

        return result;
    }

    // если введенный pref длиннее или равен 2м символам, то возвращает набор
    // слов k=3 разных длин начиная с минимальной, и начинающихся с данного 
    // префикса pref.
    public Iterable<String> wordsWithPrefix(String pref) {
        return wordsWithPrefix(pref, 3);
    }
}
