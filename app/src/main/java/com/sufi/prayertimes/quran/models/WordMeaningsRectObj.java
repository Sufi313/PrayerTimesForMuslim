package com.sufi.prayertimes.quran.models;

public class WordMeaningsRectObj {
    private int mHeight;
    private int mLineIndex;
    private int mWidth;
    private int mWordIndexInThisLine;
    private int mWordMeaningIndex;
    private int x;
    private int y;

    public WordMeaningsRectObj(int x_axis, int y_axis, int width, int height, int wordMeaningIndex, int lineIndex, int wordIndexInThisLine) {
        this.x = x_axis;
        this.y = y_axis;
        this.mWidth = width;
        this.mHeight = height;
        this.mWordMeaningIndex = wordMeaningIndex;
        this.mLineIndex = lineIndex;
        this.mWordIndexInThisLine = wordIndexInThisLine;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x_axis) {
        this.x = x_axis;
    }

    public int getY() {
        return y;
    }

    public void setY(int y_axis) {
        this.y = y_axis;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        this.mHeight = height;
    }

    public int getWordMeaningIndex() {
        return mWordMeaningIndex;
    }

    public void setWordMeaningIndex(int wordMeaningIndex) {
        this.mWordMeaningIndex = wordMeaningIndex;
    }

    public int getLineIndex() {
        return mLineIndex;
    }

    public void setLineIndex(int lineIndex) {
        this.mLineIndex = lineIndex;
    }

    public int getWordIndexInThisLine() {
        return mWordIndexInThisLine;
    }

    public void setWordIndexInThisLine(int wordIndexInThisLine) {
        this.mWordIndexInThisLine = wordIndexInThisLine;
    }
}
