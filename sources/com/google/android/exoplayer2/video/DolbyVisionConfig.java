package com.google.android.exoplayer2.video;

import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.util.ParsableByteArray;

/* loaded from: classes3.dex */
public final class DolbyVisionConfig {
    public final String codecs;
    public final int level;
    public final int profile;

    private DolbyVisionConfig(int i2, int i3, String str) {
        this.profile = i2;
        this.level = i3;
        this.codecs = str;
    }

    @Nullable
    public static DolbyVisionConfig parse(ParsableByteArray parsableByteArray) {
        String str;
        parsableByteArray.skipBytes(2);
        int unsignedByte = parsableByteArray.readUnsignedByte();
        int i2 = unsignedByte >> 1;
        int unsignedByte2 = ((parsableByteArray.readUnsignedByte() >> 3) & 31) | ((unsignedByte & 1) << 5);
        if (i2 == 4 || i2 == 5 || i2 == 7) {
            str = "dvhe";
        } else if (i2 == 8) {
            str = "hev1";
        } else {
            if (i2 != 9) {
                return null;
            }
            str = "avc3";
        }
        String str2 = unsignedByte2 < 10 ? ".0" : StrPool.DOT;
        StringBuilder sb = new StringBuilder(str.length() + 24 + str2.length());
        sb.append(str);
        sb.append(".0");
        sb.append(i2);
        sb.append(str2);
        sb.append(unsignedByte2);
        return new DolbyVisionConfig(i2, unsignedByte2, sb.toString());
    }
}
