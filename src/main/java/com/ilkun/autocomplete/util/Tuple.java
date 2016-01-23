package com.ilkun.autocomplete.util;

public class Tuple<T> {
    
    private final String term;
    private final T weight;
    
    public Tuple(String term, T weight) {
        this.term = term;
        this.weight = weight;
    }

    public String getTerm() {
        return term;
    }

    public T getWeight() {
        return weight;
    }
}
