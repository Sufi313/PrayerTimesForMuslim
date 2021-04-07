package com.sufi.prayertimes.quran.models;


import io.reactivex.annotations.Nullable;

public class IndexKey {
    public final int mLineIndex;
    public final int mWordIndex;

    public IndexKey(int lineIndex, int wordIndex) {
        this.mLineIndex = lineIndex;
        this.mWordIndex = wordIndex;
    }

    public int hashCode() {
        return ((679 + this.mLineIndex) * 97) + this.mWordIndex;
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        IndexKey indexKey = (IndexKey) obj;
        if (mLineIndex == indexKey.mLineIndex && mWordIndex == indexKey.mWordIndex) {
            z = true;
        }
        return z;
    }
}
