package com.ykb.ebook.common;

import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt__MapsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007¨\u0006\n"}, d2 = {"Lcom/ykb/ebook/common/Data;", "", "()V", "initReadFontSizeMap", "", "", "getInitReadFontSizeMap", "()Ljava/util/Map;", "readFontSizeMap", "getReadFontSizeMap", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class Data {

    @NotNull
    public static final Data INSTANCE = new Data();

    @NotNull
    private static final Map<Integer, Integer> readFontSizeMap = MapsKt__MapsKt.mapOf(TuplesKt.to(16, 16), TuplesKt.to(17, 17), TuplesKt.to(18, 18), TuplesKt.to(19, 19), TuplesKt.to(20, 20), TuplesKt.to(21, 22), TuplesKt.to(22, 24), TuplesKt.to(23, 27), TuplesKt.to(24, 30), TuplesKt.to(25, 33), TuplesKt.to(26, 36), TuplesKt.to(27, 40));

    @NotNull
    private static final Map<Integer, Integer> initReadFontSizeMap = MapsKt__MapsKt.mapOf(TuplesKt.to(16, 16), TuplesKt.to(17, 17), TuplesKt.to(18, 18), TuplesKt.to(19, 19), TuplesKt.to(20, 20), TuplesKt.to(22, 21), TuplesKt.to(24, 22), TuplesKt.to(27, 23), TuplesKt.to(30, 24), TuplesKt.to(33, 25), TuplesKt.to(36, 26), TuplesKt.to(40, 27));

    private Data() {
    }

    @NotNull
    public final Map<Integer, Integer> getInitReadFontSizeMap() {
        return initReadFontSizeMap;
    }

    @NotNull
    public final Map<Integer, Integer> getReadFontSizeMap() {
        return readFontSizeMap;
    }
}
