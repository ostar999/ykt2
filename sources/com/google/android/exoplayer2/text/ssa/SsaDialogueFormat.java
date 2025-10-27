package com.google.android.exoplayer2.text.ssa;

/* loaded from: classes3.dex */
final class SsaDialogueFormat {
    public final int endTimeIndex;
    public final int length;
    public final int startTimeIndex;
    public final int styleIndex;
    public final int textIndex;

    private SsaDialogueFormat(int i2, int i3, int i4, int i5, int i6) {
        this.startTimeIndex = i2;
        this.endTimeIndex = i3;
        this.styleIndex = i4;
        this.textIndex = i5;
        this.length = i6;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0032  */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.android.exoplayer2.text.ssa.SsaDialogueFormat fromFormatLine(java.lang.String r9) {
        /*
            java.lang.String r0 = "Format:"
            boolean r0 = r9.startsWith(r0)
            com.google.android.exoplayer2.util.Assertions.checkArgument(r0)
            r0 = 7
            java.lang.String r9 = r9.substring(r0)
            java.lang.String r0 = ","
            java.lang.String[] r9 = android.text.TextUtils.split(r9, r0)
            r0 = -1
            r1 = 0
            r4 = r0
            r5 = r4
            r6 = r5
            r7 = r6
            r2 = r1
        L1b:
            int r3 = r9.length
            if (r2 >= r3) goto L6d
            r3 = r9[r2]
            java.lang.String r3 = r3.trim()
            java.lang.String r3 = com.google.common.base.Ascii.toLowerCase(r3)
            r3.hashCode()
            int r8 = r3.hashCode()
            switch(r8) {
                case 100571: goto L55;
                case 3556653: goto L4a;
                case 109757538: goto L3f;
                case 109780401: goto L34;
                default: goto L32;
            }
        L32:
            r3 = r0
            goto L5f
        L34:
            java.lang.String r8 = "style"
            boolean r3 = r3.equals(r8)
            if (r3 != 0) goto L3d
            goto L32
        L3d:
            r3 = 3
            goto L5f
        L3f:
            java.lang.String r8 = "start"
            boolean r3 = r3.equals(r8)
            if (r3 != 0) goto L48
            goto L32
        L48:
            r3 = 2
            goto L5f
        L4a:
            java.lang.String r8 = "text"
            boolean r3 = r3.equals(r8)
            if (r3 != 0) goto L53
            goto L32
        L53:
            r3 = 1
            goto L5f
        L55:
            java.lang.String r8 = "end"
            boolean r3 = r3.equals(r8)
            if (r3 != 0) goto L5e
            goto L32
        L5e:
            r3 = r1
        L5f:
            switch(r3) {
                case 0: goto L69;
                case 1: goto L67;
                case 2: goto L65;
                case 3: goto L63;
                default: goto L62;
            }
        L62:
            goto L6a
        L63:
            r6 = r2
            goto L6a
        L65:
            r4 = r2
            goto L6a
        L67:
            r7 = r2
            goto L6a
        L69:
            r5 = r2
        L6a:
            int r2 = r2 + 1
            goto L1b
        L6d:
            if (r4 == r0) goto L7b
            if (r5 == r0) goto L7b
            if (r7 == r0) goto L7b
            com.google.android.exoplayer2.text.ssa.SsaDialogueFormat r0 = new com.google.android.exoplayer2.text.ssa.SsaDialogueFormat
            int r8 = r9.length
            r3 = r0
            r3.<init>(r4, r5, r6, r7, r8)
            goto L7c
        L7b:
            r0 = 0
        L7c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ssa.SsaDialogueFormat.fromFormatLine(java.lang.String):com.google.android.exoplayer2.text.ssa.SsaDialogueFormat");
    }
}
