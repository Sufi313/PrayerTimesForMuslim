package com.sufi.prayertimes.quran.models;

import java.util.Vector;

public class SearchResultObj {
    private Vector<Integer> ayaNumbers;
    private Vector<Integer> suraNumbers;
    private Vector<String> texts;
    private Vector<Integer> wordIndexes;

    public SearchResultObj(Vector<String> vector, Vector<Integer> vector2, Vector<Integer> vector3, Vector<Integer> vector4) {
        this.texts = vector;
        this.wordIndexes = vector2;
        this.ayaNumbers = vector3;
        this.suraNumbers = vector4;
    }

    public Vector<String> getTexts() {
        return this.texts;
    }

    public Vector<Integer> getWordIndexes() {
        return this.wordIndexes;
    }

    public Vector<Integer> getAyaNumbers() {
        return this.ayaNumbers;
    }

    public Vector<Integer> getSuraNumbers() {
        return this.suraNumbers;
    }
}
