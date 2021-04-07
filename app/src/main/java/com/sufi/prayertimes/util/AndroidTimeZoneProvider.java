package com.sufi.prayertimes.util;

import org.joda.time.DateTimeZone;
import org.joda.time.tz.Provider;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Set;
import java.util.TimeZone;

import androidx.collection.ArraySet;
import androidx.collection.SimpleArrayMap;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * Created by Sufwan.Ansari on 13.12.2018.
 */

public class AndroidTimeZoneProvider implements Provider {
    @NonNull
    private SimpleArrayMap<String, WeakReference<DateTimeZone>> cache = new SimpleArrayMap<>();

    public AndroidTimeZoneProvider() {
    }

    @Override
    public DateTimeZone getZone(@NonNull String id) {
        if (id.equals("UTC")) return DateTimeZone.UTC;
        WeakReference<DateTimeZone> wr = cache.get(id);
        if (wr != null) {
            DateTimeZone dtz = wr.get();
            if (dtz != null) return dtz;
        }
        TimeZone tz = TimeZone.getTimeZone(id);
        DateTimeZone dtz = new MyDateTimeZone(id, tz);
        cache.put(id, new WeakReference<>(dtz));
        return dtz;
    }

    @NonNull
    @Override
    public Set<String> getAvailableIDs() {
        ArraySet<String> set = new ArraySet<>();
        Collections.addAll(set, TimeZone.getAvailableIDs());
        return set;

    }


    private class MyDateTimeZone extends DateTimeZone {
        private TimeZone tz;
        @NonNull
        private long[] transitions = new long[0];

        MyDateTimeZone(@NonNull String id, @NonNull TimeZone tz) {
            super(id);
            this.tz = tz;

            try {
                Field f = tz.getClass().getDeclaredField("mTransitions");
                f.setAccessible(true);
                long[] transitions = (long[]) f.get(tz);
                if (transitions == null) return;
                this.transitions = new long[transitions.length];
                System.arraycopy(transitions, 0, this.transitions, 0, transitions.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Nullable
        @Override
        public String getNameKey(long l) {
            return null;
        }

        @Override
        public int getOffset(long l) {
            return tz.getOffset(System.currentTimeMillis());
        }

        @Override
        public int getStandardOffset(long l) {
            return tz.getRawOffset();
        }

        @Override
        public boolean isFixed() {
            return !tz.useDaylightTime();
        }

        @Override
        public long nextTransition(long l) {
            if (transitions.length == 0) return Long.MAX_VALUE;
            int i = 0;
            long time = System.currentTimeMillis() / 1000;
            while (i < transitions.length && transitions[i] < time) {
                i++;
            }
            return i >= transitions.length ? 0 : transitions[i];
        }

        @Override
        public long previousTransition(long l) {
            if (transitions.length == 0) return 0;
            int i = -1;
            long time = System.currentTimeMillis() / 1000;
            while (i + 1 < transitions.length && transitions[i + 1] < time) {
                i++;
            }
            return i >= 0 && i >= transitions.length ? 0 : transitions[i];
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MyDateTimeZone)) return false;
            return ((MyDateTimeZone) o).getID().equals(getID());
        }
    }
}
