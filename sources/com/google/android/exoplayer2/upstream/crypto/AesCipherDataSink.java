package com.google.android.exoplayer2.upstream.crypto;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes3.dex */
public final class AesCipherDataSink implements DataSink {

    @Nullable
    private AesFlushingCipher cipher;

    @Nullable
    private final byte[] scratch;
    private final byte[] secretKey;
    private final DataSink wrappedDataSink;

    public AesCipherDataSink(byte[] bArr, DataSink dataSink) {
        this(bArr, dataSink, null);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSink
    public void close() throws IOException {
        this.cipher = null;
        this.wrappedDataSink.close();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSink
    public void open(DataSpec dataSpec) throws IOException {
        this.wrappedDataSink.open(dataSpec);
        this.cipher = new AesFlushingCipher(1, this.secretKey, dataSpec.key, dataSpec.uriPositionOffset + dataSpec.position);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSink
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        if (this.scratch == null) {
            ((AesFlushingCipher) Util.castNonNull(this.cipher)).updateInPlace(bArr, i2, i3);
            this.wrappedDataSink.write(bArr, i2, i3);
            return;
        }
        int i4 = 0;
        while (i4 < i3) {
            int iMin = Math.min(i3 - i4, this.scratch.length);
            ((AesFlushingCipher) Util.castNonNull(this.cipher)).update(bArr, i2 + i4, iMin, this.scratch, 0);
            this.wrappedDataSink.write(this.scratch, 0, iMin);
            i4 += iMin;
        }
    }

    public AesCipherDataSink(byte[] bArr, DataSink dataSink, @Nullable byte[] bArr2) {
        this.wrappedDataSink = dataSink;
        this.secretKey = bArr;
        this.scratch = bArr2;
    }
}
