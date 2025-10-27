package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;

/* loaded from: classes3.dex */
public final class ByteArrayDataSource extends BaseDataSource {
    private int bytesRemaining;
    private final byte[] data;
    private boolean opened;
    private int readPosition;

    @Nullable
    private Uri uri;

    public ByteArrayDataSource(byte[] bArr) {
        super(false);
        Assertions.checkNotNull(bArr);
        Assertions.checkArgument(bArr.length > 0);
        this.data = bArr;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() {
        if (this.opened) {
            this.opened = false;
            transferEnded();
        }
        this.uri = null;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    @Nullable
    public Uri getUri() {
        return this.uri;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws IOException {
        this.uri = dataSpec.uri;
        transferInitializing(dataSpec);
        long j2 = dataSpec.position;
        byte[] bArr = this.data;
        if (j2 > bArr.length) {
            throw new DataSourceException(2008);
        }
        this.readPosition = (int) j2;
        int length = bArr.length - ((int) j2);
        this.bytesRemaining = length;
        long j3 = dataSpec.length;
        if (j3 != -1) {
            this.bytesRemaining = (int) Math.min(length, j3);
        }
        this.opened = true;
        transferStarted(dataSpec);
        long j4 = dataSpec.length;
        return j4 != -1 ? j4 : this.bytesRemaining;
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i2, int i3) {
        if (i3 == 0) {
            return 0;
        }
        int i4 = this.bytesRemaining;
        if (i4 == 0) {
            return -1;
        }
        int iMin = Math.min(i3, i4);
        System.arraycopy(this.data, this.readPosition, bArr, i2, iMin);
        this.readPosition += iMin;
        this.bytesRemaining -= iMin;
        bytesTransferred(iMin);
        return iMin;
    }
}
