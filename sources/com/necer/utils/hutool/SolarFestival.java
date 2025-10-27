package com.necer.utils.hutool;

import java.util.HashMap;
import kotlin.Metadata;
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\t\u001a\u0004\u0018\u00010\u00072\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006RB\u0010\u0003\u001a6\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0004\u0012\u00020\u00070\u0004j\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/necer/utils/hutool/SolarFestival;", "", "()V", "S_FTV", "Ljava/util/HashMap;", "Lkotlin/Pair;", "", "", "Lkotlin/collections/HashMap;", "getFestivals", "month", "day", "ncalendar_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class SolarFestival {

    @NotNull
    public static final SolarFestival INSTANCE = new SolarFestival();

    @NotNull
    private static final HashMap<Pair<Integer, Integer>, String> S_FTV;

    static {
        HashMap<Pair<Integer, Integer>, String> map = new HashMap<>();
        S_FTV = map;
        map.put(new Pair<>(1, 1), "元旦");
        map.put(new Pair<>(2, 14), "情人节");
        map.put(new Pair<>(3, 8), "妇女节");
        map.put(new Pair<>(3, 12), "植树节");
        map.put(new Pair<>(3, 15), "消费者");
        map.put(new Pair<>(4, 1), "愚人节");
        map.put(new Pair<>(5, 1), "劳动节");
        map.put(new Pair<>(5, 4), "青年节");
        map.put(new Pair<>(6, 1), "儿童节");
        map.put(new Pair<>(7, 1), "建党节");
        map.put(new Pair<>(8, 1), "建军节");
        map.put(new Pair<>(9, 10), "教师节");
        map.put(new Pair<>(10, 1), "国庆节");
    }

    private SolarFestival() {
    }

    @Nullable
    public final String getFestivals(int month, int day) {
        return S_FTV.get(new Pair(Integer.valueOf(month), Integer.valueOf(day)));
    }
}
