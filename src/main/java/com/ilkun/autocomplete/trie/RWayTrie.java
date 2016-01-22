package com.ilkun.autocomplete.trie;

import com.ilkun.autocomplete.util.Tuple;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class RWayTrie<T> implements Trie<T> {

    private static final int DIMENSION = 26;
    private Node<T> root;
    private int size;

    private static class Node<E> {

        E value;
        Node<E>[] next = new Node[DIMENSION];
    }

    @Override
    public void add(Tuple<T> tuple) {
        root = add(root, tuple.getTerm(), tuple.getWeight(), 0);
    }

    @Override
    public boolean contains(String word) {
        return get(word) != null;
    }

    @Override
    public boolean delete(String word) {
        int oldSize = size;
        root = delete(root, word, 0);
        return oldSize != size;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(final String pref) {
        return new Iterable<String>() {

            @Override
            public Iterator<String> iterator() {
                return new Iterator<String>() {

                    Queue<Tuple<Node<T>>> pairs = new ArrayDeque<>();
                    Queue<String> words = new ArrayDeque<>();
                    Queue<Node<T>> nodes = new ArrayDeque<>();
                    String last;

                    {
                        Node<T> prefNode = get(root, pref, 0);
                        if (prefNode != null) {
                            pairs.add(new Tuple<>(pref, prefNode));
                        }
                    }

                    @Override
                    public boolean hasNext() {
                        return !pairs.isEmpty();
                    }

                    @Override
                    public String next() {
                        while (!pairs.isEmpty()) {
                            String curWord = pairs.element().getTerm();
                            Node<T> curNode = pairs.remove().getWeight();
                            for (char c = 0; c < DIMENSION; c++) {
                                if (curNode.next[c] != null) {
                                    pairs.add(new Tuple<>(curWord + toLetter(c),
                                            curNode.next[c]));
                                }
                            }
                            if (curNode.value != null) {
                                return curWord;
                            }
                        }
                        throw new NoSuchElementException();
                    }
                };
            }
        };
    }

    @Override
    public int size() {
        return size;
    }

    private Node<T> add(Node<T> curNode, String word, T value, int depth) { // Change value associated with key if in subtrie rooted at x.
        if (curNode == null) {
            curNode = new Node();
        }
        if (depth == word.length()) {
            if (curNode.value == null) {
                size++;
            }
            curNode.value = value;
            return curNode;
        }
        int ch = toIndex(word.charAt(depth)); // Use dth key char to identify subtrie.
        curNode.next[ch] = add(curNode.next[ch], word, value, depth + 1);
        return curNode;
    }

    private T get(String word) {
        Node<T> resultNode = get(root, word, 0);
        if (resultNode == null) {
            return null;
        }
        return resultNode.value;
    }

    private Node<T> get(Node<T> curNode, String word, int depth) {
        if (curNode == null) {
            return null;
        }
        if (depth == word.length()) {
            return curNode;
        }
        int ch = toIndex(word.charAt(depth)); // Use dth key char to identify subtrie.
        return get(curNode.next[ch], word, depth + 1);
    }

    private Node<T> delete(Node<T> curNode, String word, int depth) {
        if (curNode == null) {
            return null;
        }
        if (depth == word.length()) {
            curNode.value = null;
            size--;
        } else {
            int ch = toIndex(word.charAt(depth));
            curNode.next[ch] = delete(curNode.next[ch], word, depth + 1);
        }
        if (curNode.value != null) {
            return curNode;
        }
        for (char ch = 0; ch < DIMENSION; ch++) {
            if (curNode.next[ch] != null) {
                return curNode;
            }
        }
        return null;
    }

    private int toIndex(char c) {
        return c - 'a';
    }

    private char toLetter(int i) {
        return (char) (i + 'a');
    }
}
