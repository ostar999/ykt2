package com.google.android.exoplayer2.text.ssa;

import android.graphics.Color;
import android.graphics.PointF;
import android.text.TextUtils;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.common.primitives.Ints;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes3.dex */
final class SsaStyle {
    public static final int SSA_ALIGNMENT_BOTTOM_CENTER = 2;
    public static final int SSA_ALIGNMENT_BOTTOM_LEFT = 1;
    public static final int SSA_ALIGNMENT_BOTTOM_RIGHT = 3;
    public static final int SSA_ALIGNMENT_MIDDLE_CENTER = 5;
    public static final int SSA_ALIGNMENT_MIDDLE_LEFT = 4;
    public static final int SSA_ALIGNMENT_MIDDLE_RIGHT = 6;
    public static final int SSA_ALIGNMENT_TOP_CENTER = 8;
    public static final int SSA_ALIGNMENT_TOP_LEFT = 7;
    public static final int SSA_ALIGNMENT_TOP_RIGHT = 9;
    public static final int SSA_ALIGNMENT_UNKNOWN = -1;
    private static final String TAG = "SsaStyle";
    public final int alignment;
    public final boolean bold;
    public final float fontSize;
    public final boolean italic;
    public final String name;

    @Nullable
    @ColorInt
    public final Integer primaryColor;
    public final boolean strikeout;
    public final boolean underline;

    public static final class Format {
        public final int alignmentIndex;
        public final int boldIndex;
        public final int fontSizeIndex;
        public final int italicIndex;
        public final int length;
        public final int nameIndex;
        public final int primaryColorIndex;
        public final int strikeoutIndex;
        public final int underlineIndex;

        private Format(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
            this.nameIndex = i2;
            this.alignmentIndex = i3;
            this.primaryColorIndex = i4;
            this.fontSizeIndex = i5;
            this.boldIndex = i6;
            this.italicIndex = i7;
            this.underlineIndex = i8;
            this.strikeoutIndex = i9;
            this.length = i10;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:7:0x002d  */
        @androidx.annotation.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static com.google.android.exoplayer2.text.ssa.SsaStyle.Format fromFormatLine(java.lang.String r14) {
            /*
                r0 = 7
                java.lang.String r14 = r14.substring(r0)
                java.lang.String r1 = ","
                java.lang.String[] r14 = android.text.TextUtils.split(r14, r1)
                r1 = -1
                r2 = 0
                r5 = r1
                r6 = r5
                r7 = r6
                r8 = r7
                r9 = r8
                r10 = r9
                r11 = r10
                r12 = r11
                r3 = r2
            L16:
                int r4 = r14.length
                if (r3 >= r4) goto L9e
                r4 = r14[r3]
                java.lang.String r4 = r4.trim()
                java.lang.String r4 = com.google.common.base.Ascii.toLowerCase(r4)
                r4.hashCode()
                int r13 = r4.hashCode()
                switch(r13) {
                    case -1178781136: goto L7d;
                    case -1026963764: goto L72;
                    case -192095652: goto L67;
                    case -70925746: goto L5c;
                    case 3029637: goto L51;
                    case 3373707: goto L46;
                    case 366554320: goto L3b;
                    case 1767875043: goto L30;
                    default: goto L2d;
                }
            L2d:
                r4 = r1
                goto L87
            L30:
                java.lang.String r13 = "alignment"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L39
                goto L2d
            L39:
                r4 = r0
                goto L87
            L3b:
                java.lang.String r13 = "fontsize"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L44
                goto L2d
            L44:
                r4 = 6
                goto L87
            L46:
                java.lang.String r13 = "name"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L4f
                goto L2d
            L4f:
                r4 = 5
                goto L87
            L51:
                java.lang.String r13 = "bold"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L5a
                goto L2d
            L5a:
                r4 = 4
                goto L87
            L5c:
                java.lang.String r13 = "primarycolour"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L65
                goto L2d
            L65:
                r4 = 3
                goto L87
            L67:
                java.lang.String r13 = "strikeout"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L70
                goto L2d
            L70:
                r4 = 2
                goto L87
            L72:
                java.lang.String r13 = "underline"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L7b
                goto L2d
            L7b:
                r4 = 1
                goto L87
            L7d:
                java.lang.String r13 = "italic"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L86
                goto L2d
            L86:
                r4 = r2
            L87:
                switch(r4) {
                    case 0: goto L99;
                    case 1: goto L97;
                    case 2: goto L95;
                    case 3: goto L93;
                    case 4: goto L91;
                    case 5: goto L8f;
                    case 6: goto L8d;
                    case 7: goto L8b;
                    default: goto L8a;
                }
            L8a:
                goto L9a
            L8b:
                r6 = r3
                goto L9a
            L8d:
                r8 = r3
                goto L9a
            L8f:
                r5 = r3
                goto L9a
            L91:
                r9 = r3
                goto L9a
            L93:
                r7 = r3
                goto L9a
            L95:
                r12 = r3
                goto L9a
            L97:
                r11 = r3
                goto L9a
            L99:
                r10 = r3
            L9a:
                int r3 = r3 + 1
                goto L16
            L9e:
                if (r5 == r1) goto La8
                com.google.android.exoplayer2.text.ssa.SsaStyle$Format r0 = new com.google.android.exoplayer2.text.ssa.SsaStyle$Format
                int r13 = r14.length
                r4 = r0
                r4.<init>(r5, r6, r7, r8, r9, r10, r11, r12, r13)
                goto La9
            La8:
                r0 = 0
            La9:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ssa.SsaStyle.Format.fromFormatLine(java.lang.String):com.google.android.exoplayer2.text.ssa.SsaStyle$Format");
        }
    }

    public static final class Overrides {
        private static final String TAG = "SsaStyle.Overrides";
        public final int alignment;

        @Nullable
        public final PointF position;
        private static final Pattern BRACES_PATTERN = Pattern.compile("\\{([^}]*)\\}");
        private static final String PADDED_DECIMAL_PATTERN = "\\s*\\d+(?:\\.\\d+)?\\s*";
        private static final Pattern POSITION_PATTERN = Pattern.compile(Util.formatInvariant("\\\\pos\\((%1$s),(%1$s)\\)", PADDED_DECIMAL_PATTERN));
        private static final Pattern MOVE_PATTERN = Pattern.compile(Util.formatInvariant("\\\\move\\(%1$s,%1$s,(%1$s),(%1$s)(?:,%1$s,%1$s)?\\)", PADDED_DECIMAL_PATTERN));
        private static final Pattern ALIGNMENT_OVERRIDE_PATTERN = Pattern.compile("\\\\an(\\d+)");

        private Overrides(int i2, @Nullable PointF pointF) {
            this.alignment = i2;
            this.position = pointF;
        }

        private static int parseAlignmentOverride(String str) {
            Matcher matcher = ALIGNMENT_OVERRIDE_PATTERN.matcher(str);
            if (matcher.find()) {
                return SsaStyle.parseAlignment((String) Assertions.checkNotNull(matcher.group(1)));
            }
            return -1;
        }

        public static Overrides parseFromDialogue(String str) {
            Matcher matcher = BRACES_PATTERN.matcher(str);
            PointF pointF = null;
            int i2 = -1;
            while (matcher.find()) {
                String str2 = (String) Assertions.checkNotNull(matcher.group(1));
                try {
                    PointF position = parsePosition(str2);
                    if (position != null) {
                        pointF = position;
                    }
                } catch (RuntimeException unused) {
                }
                try {
                    int alignmentOverride = parseAlignmentOverride(str2);
                    if (alignmentOverride != -1) {
                        i2 = alignmentOverride;
                    }
                } catch (RuntimeException unused2) {
                }
            }
            return new Overrides(i2, pointF);
        }

        @Nullable
        private static PointF parsePosition(String str) {
            String strGroup;
            String strGroup2;
            Matcher matcher = POSITION_PATTERN.matcher(str);
            Matcher matcher2 = MOVE_PATTERN.matcher(str);
            boolean zFind = matcher.find();
            boolean zFind2 = matcher2.find();
            if (zFind) {
                if (zFind2) {
                    StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 82);
                    sb.append("Override has both \\pos(x,y) and \\move(x1,y1,x2,y2); using \\pos values. override='");
                    sb.append(str);
                    sb.append("'");
                    Log.i(TAG, sb.toString());
                }
                strGroup = matcher.group(1);
                strGroup2 = matcher.group(2);
            } else {
                if (!zFind2) {
                    return null;
                }
                strGroup = matcher2.group(1);
                strGroup2 = matcher2.group(2);
            }
            return new PointF(Float.parseFloat(((String) Assertions.checkNotNull(strGroup)).trim()), Float.parseFloat(((String) Assertions.checkNotNull(strGroup2)).trim()));
        }

        public static String stripStyleOverrides(String str) {
            return BRACES_PATTERN.matcher(str).replaceAll("");
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface SsaAlignment {
    }

    private SsaStyle(String str, int i2, @Nullable @ColorInt Integer num, float f2, boolean z2, boolean z3, boolean z4, boolean z5) {
        this.name = str;
        this.alignment = i2;
        this.primaryColor = num;
        this.fontSize = f2;
        this.bold = z2;
        this.italic = z3;
        this.underline = z4;
        this.strikeout = z5;
    }

    @Nullable
    public static SsaStyle fromStyleLine(String str, Format format) {
        Assertions.checkArgument(str.startsWith("Style:"));
        String[] strArrSplit = TextUtils.split(str.substring(6), ",");
        int length = strArrSplit.length;
        int i2 = format.length;
        if (length != i2) {
            Log.w(TAG, Util.formatInvariant("Skipping malformed 'Style:' line (expected %s values, found %s): '%s'", Integer.valueOf(i2), Integer.valueOf(strArrSplit.length), str));
            return null;
        }
        try {
            String strTrim = strArrSplit[format.nameIndex].trim();
            int i3 = format.alignmentIndex;
            int alignment = i3 != -1 ? parseAlignment(strArrSplit[i3].trim()) : -1;
            int i4 = format.primaryColorIndex;
            Integer color = i4 != -1 ? parseColor(strArrSplit[i4].trim()) : null;
            int i5 = format.fontSizeIndex;
            float fontSize = i5 != -1 ? parseFontSize(strArrSplit[i5].trim()) : -3.4028235E38f;
            int i6 = format.boldIndex;
            boolean z2 = i6 != -1 && parseBooleanValue(strArrSplit[i6].trim());
            int i7 = format.italicIndex;
            boolean z3 = i7 != -1 && parseBooleanValue(strArrSplit[i7].trim());
            int i8 = format.underlineIndex;
            boolean z4 = i8 != -1 && parseBooleanValue(strArrSplit[i8].trim());
            int i9 = format.strikeoutIndex;
            return new SsaStyle(strTrim, alignment, color, fontSize, z2, z3, z4, i9 != -1 && parseBooleanValue(strArrSplit[i9].trim()));
        } catch (RuntimeException e2) {
            StringBuilder sb = new StringBuilder(str.length() + 36);
            sb.append("Skipping malformed 'Style:' line: '");
            sb.append(str);
            sb.append("'");
            Log.w(TAG, sb.toString(), e2);
            return null;
        }
    }

    private static boolean isValidAlignment(int i2) {
        switch (i2) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return true;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int parseAlignment(String str) throws NumberFormatException {
        try {
            int i2 = Integer.parseInt(str.trim());
            if (isValidAlignment(i2)) {
                return i2;
            }
        } catch (NumberFormatException unused) {
        }
        String strValueOf = String.valueOf(str);
        Log.w(TAG, strValueOf.length() != 0 ? "Ignoring unknown alignment: ".concat(strValueOf) : new String("Ignoring unknown alignment: "));
        return -1;
    }

    private static boolean parseBooleanValue(String str) throws NumberFormatException {
        try {
            int i2 = Integer.parseInt(str);
            return i2 == 1 || i2 == -1;
        } catch (NumberFormatException e2) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 33);
            sb.append("Failed to parse boolean value: '");
            sb.append(str);
            sb.append("'");
            Log.w(TAG, sb.toString(), e2);
            return false;
        }
    }

    @Nullable
    @ColorInt
    public static Integer parseColor(String str) {
        try {
            long j2 = str.startsWith("&H") ? Long.parseLong(str.substring(2), 16) : Long.parseLong(str);
            Assertions.checkArgument(j2 <= InternalZipConstants.ZIP_64_LIMIT);
            return Integer.valueOf(Color.argb(Ints.checkedCast(((j2 >> 24) & 255) ^ 255), Ints.checkedCast(j2 & 255), Ints.checkedCast((j2 >> 8) & 255), Ints.checkedCast((j2 >> 16) & 255)));
        } catch (IllegalArgumentException e2) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 36);
            sb.append("Failed to parse color expression: '");
            sb.append(str);
            sb.append("'");
            Log.w(TAG, sb.toString(), e2);
            return null;
        }
    }

    private static float parseFontSize(String str) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e2) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 29);
            sb.append("Failed to parse font size: '");
            sb.append(str);
            sb.append("'");
            Log.w(TAG, sb.toString(), e2);
            return -3.4028235E38f;
        }
    }
}
