package com.necer.utils.hutool;

import com.yikaobang.yixue.R2;
import java.time.LocalDate;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0016\n\u0002\b\f\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\bH\u0002J\u000e\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\bJ\u000e\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\bJ\u0016\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\bJ\u000e\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0016"}, d2 = {"Lcom/necer/utils/hutool/LunarInfo;", "", "()V", "BASE_DAY", "", "getBASE_DAY", "()J", "BASE_YEAR", "", "LUNAR_CODE", "", "MAX_YEAR", "getMAX_YEAR", "()I", "getCode", "year", "leapDays", "y", "leapMonth", "monthDays", "m", "yearDays", "ncalendar_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class LunarInfo {
    public static final int BASE_YEAR = 1900;

    @NotNull
    private static final long[] LUNAR_CODE;
    private static final int MAX_YEAR;

    @NotNull
    public static final LunarInfo INSTANCE = new LunarInfo();
    private static final long BASE_DAY = LocalDate.of(1900, 1, 31).toEpochDay();

    static {
        long[] jArr = {19416, 19168, 42352, 21717, 53856, 55632, 91476, 22176, 39632, 21970, 19168, 42422, 42192, 53840, 119381, 46400, 54944, 44450, 38320, 84343, 18800, 42160, 46261, 27216, 27968, 109396, 11104, 38256, 21234, 18800, 25958, 54432, 59984, 92821, 23248, 11104, 100067, 37600, 116951, 51536, 54432, 120998, 46416, 22176, 107956, 9680, 37584, 53938, 43344, 46423, 27808, 46416, 86869, 19872, 42416, 83315, 21168, 43432, 59728, 27296, 44710, 43856, 19296, 43748, 42352, 21088, 62051, 55632, 23383, 22176, 38608, 19925, 19152, 42192, 54484, 53840, 54616, 46400, 46752, 103846, 38320, 18864, 43380, 42160, 45690, 27216, 27968, 44870, 43872, 38256, 19189, 18800, 25776, 29859, 59984, 27480, 23232, 43872, 38613, 37600, 51552, 55636, 54432, 55888, 30034, 22176, 43959, 9680, 37584, 51893, 43344, 46240, 47780, 44368, 21977, 19360, 42416, 86390, 21168, 43312, 31060, 27296, 44368, 23378, 19296, 42726, 42208, 53856, 60005, 54576, 23200, 30371, 38608, 19195, 19152, 42192, 118966, 53840, 54560, 56645, 46496, 22224, 21938, 18864, 42359, 42160, 43600, 111189, 27936, 44448, 84835, 37744, 18936, 18800, 25776, 92326, 59984, 27424, 108228, 43744, 37600, 53987, 51552, 54615, 54432, 55888, 23893, 22176, 42704, 21972, 21200, 43448, 43344, 46240, 46758, 44368, 21920, 43940, 42416, 21168, 45683, 26928, 29495, 27296, 44368, 84821, 19296, 42352, 21732, 53600, 59752, 54560, 55968, 92838, 22224, 19168, 43476, 41680, 53584, 62034};
        LUNAR_CODE = jArr;
        MAX_YEAR = (jArr.length + 1900) - 1;
    }

    private LunarInfo() {
    }

    private final long getCode(int year) {
        return LUNAR_CODE[year - 1900];
    }

    public final long getBASE_DAY() {
        return BASE_DAY;
    }

    public final int getMAX_YEAR() {
        return MAX_YEAR;
    }

    public final int leapDays(int y2) {
        if (leapMonth(y2) != 0) {
            return (getCode(y2) & 65536) != 0 ? 30 : 29;
        }
        return 0;
    }

    public final int leapMonth(int y2) {
        return (int) (getCode(y2) & 15);
    }

    public final int monthDays(int y2, int m2) {
        return (((long) (65536 >> m2)) & getCode(y2)) == 0 ? 29 : 30;
    }

    public final int yearDays(int y2) {
        int i2 = R2.attr.arcEnabledSingle;
        for (int i3 = 32768; i3 > 8; i3 >>= 1) {
            if ((getCode(y2) & i3) != 0) {
                i2++;
            }
        }
        return i2 + leapDays(y2);
    }
}
