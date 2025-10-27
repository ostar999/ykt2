package com.google.android.exoplayer2.extractor.mp4;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;

/* loaded from: classes3.dex */
public final class TrackEncryptionBox {
    private static final String TAG = "TrackEncryptionBox";
    public final TrackOutput.CryptoData cryptoData;

    @Nullable
    public final byte[] defaultInitializationVector;
    public final boolean isEncrypted;
    public final int perSampleIvSize;

    @Nullable
    public final String schemeType;

    public TrackEncryptionBox(boolean z2, @Nullable String str, int i2, byte[] bArr, int i3, int i4, @Nullable byte[] bArr2) {
        Assertions.checkArgument((bArr2 == null) ^ (i2 == 0));
        this.isEncrypted = z2;
        this.schemeType = str;
        this.perSampleIvSize = i2;
        this.defaultInitializationVector = bArr2;
        this.cryptoData = new TrackOutput.CryptoData(schemeToCryptoMode(str), bArr, i3, i4);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static int schemeToCryptoMode(@Nullable String str) {
        if (str == null) {
            return 1;
        }
        char c3 = 65535;
        switch (str.hashCode()) {
            case 3046605:
                if (str.equals(C.CENC_TYPE_cbc1)) {
                    c3 = 0;
                    break;
                }
                break;
            case 3046671:
                if (str.equals(C.CENC_TYPE_cbcs)) {
                    c3 = 1;
                    break;
                }
                break;
            case 3049879:
                if (str.equals(C.CENC_TYPE_cenc)) {
                    c3 = 2;
                    break;
                }
                break;
            case 3049895:
                if (str.equals(C.CENC_TYPE_cens)) {
                    c3 = 3;
                    break;
                }
                break;
        }
        switch (c3) {
            case 0:
            case 1:
                return 2;
            default:
                StringBuilder sb = new StringBuilder(str.length() + 68);
                sb.append("Unsupported protection scheme type '");
                sb.append(str);
                sb.append("'. Assuming AES-CTR crypto mode.");
                Log.w(TAG, sb.toString());
            case 2:
            case 3:
                return 1;
        }
    }
}
