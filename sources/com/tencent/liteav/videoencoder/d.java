package com.tencent.liteav.videoencoder;

import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.g;

/* loaded from: classes6.dex */
public class d {
    public static boolean a(int i2, int i3, int i4) {
        if (2 != com.tencent.liteav.basic.c.c.a().c()) {
            return false;
        }
        if (!g.a().b("enable_hw_hevc_encode", true)) {
            TXCLog.w("TXCVideoEncoderUtils", "local not support hevc encoder");
            return false;
        }
        if (!com.tencent.liteav.basic.c.c.a().h()) {
            TXCLog.w("TXCVideoEncoderUtils", "not support hevc encoder: in blacklist!");
            return false;
        }
        if (!b(i2, i3, i4)) {
            return false;
        }
        TXCLog.i("TXCVideoEncoderUtils", "config hevc switch on!");
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x0065, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean b(int r12, int r13, int r14) {
        /*
            int r0 = com.tencent.liteav.basic.util.TXCBuild.VersionInt()
            r1 = 21
            r2 = 0
            if (r0 >= r1) goto La
            return r2
        La:
            android.media.MediaCodecList r0 = new android.media.MediaCodecList
            r1 = 1
            r0.<init>(r1)
            android.media.MediaCodecInfo[] r0 = r0.getCodecInfos()
            int r3 = r0.length
            r4 = r2
        L16:
            java.lang.String r5 = "TXCVideoEncoderUtils"
            if (r4 >= r3) goto L68
            r6 = r0[r4]
            boolean r7 = r6.isEncoder()
            if (r7 != 0) goto L23
            goto L65
        L23:
            java.lang.String[] r7 = r6.getSupportedTypes()
            int r8 = r7.length
            r9 = r2
        L29:
            if (r9 >= r8) goto L65
            r10 = r7[r9]
            java.lang.String r11 = "video/hevc"
            boolean r11 = r10.contains(r11)
            if (r11 != 0) goto L39
            int r9 = r9 + 1
            goto L29
        L39:
            android.media.MediaCodecInfo$CodecCapabilities r7 = r6.getCapabilitiesForType(r10)
            if (r7 != 0) goto L40
            return r2
        L40:
            android.media.MediaCodecInfo$VideoCapabilities r7 = r7.getVideoCapabilities()
            if (r7 != 0) goto L47
            return r2
        L47:
            double r8 = (double) r14
            boolean r11 = r7.areSizeAndRateSupported(r12, r13, r8)
            if (r11 == 0) goto L65
            boolean r7 = r7.areSizeAndRateSupported(r13, r12, r8)
            if (r7 == 0) goto L65
            r12 = 2
            java.lang.Object[] r12 = new java.lang.Object[r12]
            java.lang.String r13 = r6.getName()
            r12[r2] = r13
            r12[r1] = r10
            java.lang.String r13 = "got hevc encoder:%s, type:%s"
            com.tencent.liteav.basic.log.TXCLog.i(r5, r13, r12)
            return r1
        L65:
            int r4 = r4 + 1
            goto L16
        L68:
            java.lang.String r12 = "not got hevc encoder"
            com.tencent.liteav.basic.log.TXCLog.w(r5, r12)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.videoencoder.d.b(int, int, int):boolean");
    }
}
