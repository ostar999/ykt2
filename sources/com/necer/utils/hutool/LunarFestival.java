package com.necer.utils.hutool;

import java.util.HashMap;
import kotlin.Metadata;
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\t\u001a\u0004\u0018\u00010\u00072\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006J \u0010\t\u001a\u0004\u0018\u00010\u00072\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006RB\u0010\u0003\u001a6\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0004\u0012\u00020\u00070\u0004j\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/necer/utils/hutool/LunarFestival;", "", "()V", "L_FTV", "Ljava/util/HashMap;", "Lkotlin/Pair;", "", "", "Lkotlin/collections/HashMap;", "getFestivals", "month", "day", "year", "ncalendar_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class LunarFestival {

    @NotNull
    public static final LunarFestival INSTANCE = new LunarFestival();

    @NotNull
    private static final HashMap<Pair<Integer, Integer>, String> L_FTV;

    static {
        HashMap<Pair<Integer, Integer>, String> map = new HashMap<>();
        L_FTV = map;
        map.put(new Pair<>(1, 1), "春节");
        map.put(new Pair<>(1, 15), "元宵节");
        map.put(new Pair<>(5, 5), "端午节");
        map.put(new Pair<>(7, 7), "七夕");
        map.put(new Pair<>(7, 15), "中元节");
        map.put(new Pair<>(8, 15), "中秋节");
        map.put(new Pair<>(9, 9), "重阳节");
        map.put(new Pair<>(10, 15), "下元节");
        map.put(new Pair<>(12, 8), "腊八节");
        map.put(new Pair<>(12, 23), "小年");
        map.put(new Pair<>(12, 30), "除夕");
    }

    private LunarFestival() {
    }

    @Nullable
    public final String getFestivals(int year, int month, int day) {
        if (12 == month && 29 == day && 29 == LunarInfo.INSTANCE.monthDays(year, month)) {
            day++;
        }
        return getFestivals(month, day);
    }

    @Nullable
    public final String getFestivals(int month, int day) {
        return L_FTV.get(new Pair(Integer.valueOf(month), Integer.valueOf(day)));
    }
}
