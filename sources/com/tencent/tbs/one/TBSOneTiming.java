package com.tencent.tbs.one;

import com.tencent.tbs.one.impl.a.g;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes6.dex */
public class TBSOneTiming {

    /* renamed from: a, reason: collision with root package name */
    public static final TimingEntry f21691a = new TimingEntry();

    public static class CategoryTiming extends TimingEntry {

        /* renamed from: a, reason: collision with root package name */
        public final String f21692a;

        public CategoryTiming(String str) {
            this.f21692a = str;
        }

        public /* synthetic */ CategoryTiming(String str, byte b3) {
            this(str);
        }

        public ComponentTiming component(String str) {
            return new ComponentTiming(this.f21692a, str, (byte) 0);
        }

        @Override // com.tencent.tbs.one.TBSOneTiming.TimingEntry
        public void end(String str) {
            super.end(TBSOneTiming.b(this.f21692a, str));
        }

        @Override // com.tencent.tbs.one.TBSOneTiming.TimingEntry
        public void start(String str) {
            super.start(TBSOneTiming.b(this.f21692a, str));
        }
    }

    public static class ComponentTiming extends TimingEntry {

        /* renamed from: a, reason: collision with root package name */
        public final String f21693a;

        /* renamed from: b, reason: collision with root package name */
        public final String f21694b;

        public ComponentTiming(String str, String str2) {
            this.f21693a = str;
            this.f21694b = str2;
        }

        public /* synthetic */ ComponentTiming(String str, String str2, byte b3) {
            this(str, str2);
        }

        @Override // com.tencent.tbs.one.TBSOneTiming.TimingEntry
        public void end(String str) {
            super.end(TBSOneTiming.b(this.f21693a, this.f21694b, str));
        }

        @Override // com.tencent.tbs.one.TBSOneTiming.TimingEntry
        public void start(String str) {
            super.start(TBSOneTiming.b(this.f21693a, this.f21694b, str));
        }
    }

    public static final class Timing {

        /* renamed from: a, reason: collision with root package name */
        public final AtomicLong f21695a;

        /* renamed from: b, reason: collision with root package name */
        public final AtomicLong f21696b;

        /* renamed from: c, reason: collision with root package name */
        public final String f21697c;

        public Timing(String str) {
            this.f21695a = new AtomicLong(-1L);
            this.f21696b = new AtomicLong(-1L);
            this.f21697c = str;
        }

        public /* synthetic */ Timing(String str, byte b3) {
            this(str);
        }

        public final void end() {
            if (this.f21695a.get() < 0) {
                g.c("[timing] " + this.f21697c + " has not start yet!", new Object[0]);
                return;
            }
            if (this.f21696b.get() <= 0) {
                this.f21696b.set(new Date().getTime());
                return;
            }
            g.c("[timing] " + this.f21697c + " has ended before, duplicated calling is not permitted!", new Throwable());
        }

        public final void start() {
            if (this.f21695a.get() <= 0) {
                this.f21695a.set(new Date().getTime());
                return;
            }
            g.c("[timing] " + this.f21697c + " has started before, duplicated calling is not permitted!", new Throwable());
        }
    }

    public static class TimingEntry {

        /* renamed from: a, reason: collision with root package name */
        public static final Map<String, Timing> f21698a = new ConcurrentHashMap();

        public static Timing a(String str) {
            Map<String, Timing> map = f21698a;
            byte b3 = 0;
            if (!map.containsKey(str)) {
                map.put(str, new Timing(str, b3));
            }
            if (map.get(str) == null) {
                map.put(str, new Timing(str, b3));
            }
            return map.get(str);
        }

        public void end(String str) {
            a(str).end();
        }

        public void start(String str) {
            a(str).start();
        }
    }

    public static String b(String... strArr) {
        StringBuilder sb = new StringBuilder();
        if (strArr.length == 1) {
            return strArr[0];
        }
        for (int i2 = 0; i2 < strArr.length - 1; i2++) {
            sb.append(strArr[i2]);
            sb.append("-");
        }
        sb.append(strArr[strArr.length - 1]);
        return sb.toString();
    }

    public static CategoryTiming category(String str) {
        return new CategoryTiming(str, (byte) 0);
    }

    public static void end(String str) {
        f21691a.end(str);
    }

    public static void start(String str) {
        f21691a.start(str);
    }

    public static Map<String, Long> stat() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : TimingEntry.f21698a.entrySet()) {
            Timing timing = (Timing) entry.getValue();
            linkedHashMap.put(b((String) entry.getKey(), "start"), Long.valueOf(timing.f21695a.get()));
            linkedHashMap.put(b((String) entry.getKey(), "end"), Long.valueOf(timing.f21696b.get()));
            linkedHashMap.put(b((String) entry.getKey(), "cost"), Long.valueOf(timing.f21696b.get() - timing.f21695a.get()));
        }
        ArrayList<Map.Entry> arrayList = new ArrayList(linkedHashMap.entrySet());
        Collections.sort(arrayList, new Comparator<Map.Entry<String, Long>>() { // from class: com.tencent.tbs.one.TBSOneTiming.1
            @Override // java.util.Comparator
            public final int compare(Map.Entry<String, Long> entry2, Map.Entry<String, Long> entry3) {
                return entry2.getValue().compareTo(entry3.getValue());
            }
        });
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry entry2 : arrayList) {
            linkedHashMap2.put(entry2.getKey(), entry2.getValue());
        }
        return linkedHashMap2;
    }
}
