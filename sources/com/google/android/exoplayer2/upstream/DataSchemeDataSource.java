package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import android.util.Base64;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Charsets;
import java.io.IOException;
import java.net.URLDecoder;

/* loaded from: classes3.dex */
public final class DataSchemeDataSource extends BaseDataSource {
    public static final String SCHEME_DATA = "data";
    private int bytesRemaining;

    @Nullable
    private byte[] data;

    @Nullable
    private DataSpec dataSpec;
    private int readPosition;

    public DataSchemeDataSource() {
        super(false);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() {
        if (this.data != null) {
            this.data = null;
            transferEnded();
        }
        this.dataSpec = null;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    @Nullable
    public Uri getUri() {
        DataSpec dataSpec = this.dataSpec;
        if (dataSpec != null) {
            return dataSpec.uri;
        }
        return null;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws IOException {
        transferInitializing(dataSpec);
        this.dataSpec = dataSpec;
        Uri uri = dataSpec.uri;
        String scheme = uri.getScheme();
        boolean zEquals = "data".equals(scheme);
        String strValueOf = String.valueOf(scheme);
        Assertions.checkArgument(zEquals, strValueOf.length() != 0 ? "Unsupported scheme: ".concat(strValueOf) : new String("Unsupported scheme: "));
        String[] strArrSplit = Util.split(uri.getSchemeSpecificPart(), ",");
        if (strArrSplit.length != 2) {
            String strValueOf2 = String.valueOf(uri);
            StringBuilder sb = new StringBuilder(strValueOf2.length() + 23);
            sb.append("Unexpected URI format: ");
            sb.append(strValueOf2);
            throw ParserException.createForMalformedDataOfUnknownType(sb.toString(), null);
        }
        String str = strArrSplit[1];
        if (strArrSplit[0].contains(";base64")) {
            try {
                this.data = Base64.decode(str, 0);
            } catch (IllegalArgumentException e2) {
                String strValueOf3 = String.valueOf(str);
                throw ParserException.createForMalformedDataOfUnknownType(strValueOf3.length() != 0 ? "Error while parsing Base64 encoded string: ".concat(strValueOf3) : new String("Error while parsing Base64 encoded string: "), e2);
            }
        } else {
            this.data = Util.getUtf8Bytes(URLDecoder.decode(str, Charsets.US_ASCII.name()));
        }
        long j2 = dataSpec.position;
        byte[] bArr = this.data;
        if (j2 > bArr.length) {
            this.data = null;
            throw new DataSourceException(2008);
        }
        int i2 = (int) j2;
        this.readPosition = i2;
        int length = bArr.length - i2;
        this.bytesRemaining = length;
        long j3 = dataSpec.length;
        if (j3 != -1) {
            this.bytesRemaining = (int) Math.min(length, j3);
        }
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
        System.arraycopy(Util.castNonNull(this.data), this.readPosition, bArr, i2, iMin);
        this.readPosition += iMin;
        this.bytesRemaining -= iMin;
        bytesTransferred(iMin);
        return iMin;
    }
}
