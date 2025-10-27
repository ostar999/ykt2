package cn.hutool.core.date;

import java.util.Calendar;

/* loaded from: classes.dex */
public class DateModifier {
    private static final int[] IGNORE_FIELDS = {11, 9, 8, 6, 4, 3};

    /* renamed from: cn.hutool.core.date.DateModifier$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$cn$hutool$core$date$DateModifier$ModifyType;

        static {
            int[] iArr = new int[ModifyType.values().length];
            $SwitchMap$cn$hutool$core$date$DateModifier$ModifyType = iArr;
            try {
                iArr[ModifyType.TRUNCATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$cn$hutool$core$date$DateModifier$ModifyType[ModifyType.CEILING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$cn$hutool$core$date$DateModifier$ModifyType[ModifyType.ROUND.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public enum ModifyType {
        TRUNCATE,
        ROUND,
        CEILING
    }

    public static Calendar modify(Calendar calendar, int i2, ModifyType modifyType) {
        return modify(calendar, i2, modifyType, false);
    }

    private static void modifyField(Calendar calendar, int i2, ModifyType modifyType) {
        if (10 == i2) {
            i2 = 11;
        }
        int i3 = AnonymousClass1.$SwitchMap$cn$hutool$core$date$DateModifier$ModifyType[modifyType.ordinal()];
        if (i3 == 1) {
            calendar.set(i2, CalendarUtil.getBeginValue(calendar, i2));
            return;
        }
        if (i3 == 2) {
            calendar.set(i2, CalendarUtil.getEndValue(calendar, i2));
            return;
        }
        if (i3 != 3) {
            return;
        }
        int beginValue = CalendarUtil.getBeginValue(calendar, i2);
        int endValue = CalendarUtil.getEndValue(calendar, i2);
        if (calendar.get(i2) >= (7 == i2 ? (beginValue + 3) % 7 : 1 + ((endValue - beginValue) / 2))) {
            beginValue = endValue;
        }
        calendar.set(i2, beginValue);
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x006f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.Calendar modify(java.util.Calendar r8, int r9, cn.hutool.core.date.DateModifier.ModifyType r10, boolean r11) {
        /*
            r0 = 9
            r1 = 3
            r2 = 0
            if (r0 != r9) goto L4c
            boolean r11 = cn.hutool.core.date.CalendarUtil.isAM(r8)
            int[] r0 = cn.hutool.core.date.DateModifier.AnonymousClass1.$SwitchMap$cn$hutool$core$date$DateModifier$ModifyType
            int r3 = r10.ordinal()
            r0 = r0[r3]
            r3 = 12
            r4 = 1
            r5 = 11
            if (r0 == r4) goto L3f
            r6 = 23
            r7 = 2
            if (r0 == r7) goto L38
            if (r0 == r1) goto L21
            goto L46
        L21:
            if (r11 == 0) goto L24
            goto L25
        L24:
            r2 = r3
        L25:
            if (r11 == 0) goto L28
            r6 = r5
        L28:
            int r11 = r6 - r2
            int r11 = r11 / r7
            int r11 = r11 + r4
            int r0 = r8.get(r5)
            if (r0 >= r11) goto L33
            goto L34
        L33:
            r2 = r6
        L34:
            r8.set(r5, r2)
            goto L46
        L38:
            if (r11 == 0) goto L3b
            r6 = r5
        L3b:
            r8.set(r5, r6)
            goto L46
        L3f:
            if (r11 == 0) goto L42
            goto L43
        L42:
            r2 = r3
        L43:
            r8.set(r5, r2)
        L46:
            int r9 = r9 + r4
            java.util.Calendar r8 = modify(r8, r9, r10)
            return r8
        L4c:
            r0 = 14
            if (r11 == 0) goto L53
            r3 = 13
            goto L54
        L53:
            r3 = r0
        L54:
            int r4 = r9 + 1
        L56:
            if (r4 > r3) goto L75
            int[] r5 = cn.hutool.core.date.DateModifier.IGNORE_FIELDS
            boolean r5 = cn.hutool.core.util.PrimitiveArrayUtil.contains(r5, r4)
            if (r5 == 0) goto L61
            goto L72
        L61:
            r5 = 4
            if (r5 == r9) goto L6b
            if (r1 != r9) goto L67
            goto L6b
        L67:
            r5 = 7
            if (r5 != r4) goto L6f
            goto L72
        L6b:
            r5 = 5
            if (r5 != r4) goto L6f
            goto L72
        L6f:
            modifyField(r8, r4, r10)
        L72:
            int r4 = r4 + 1
            goto L56
        L75:
            if (r11 == 0) goto L7a
            r8.set(r0, r2)
        L7a:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.hutool.core.date.DateModifier.modify(java.util.Calendar, int, cn.hutool.core.date.DateModifier$ModifyType, boolean):java.util.Calendar");
    }
}
