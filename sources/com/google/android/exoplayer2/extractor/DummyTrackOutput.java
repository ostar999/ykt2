package com.google.android.exoplayer2.extractor;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.EOFException;
import java.io.IOException;

/* loaded from: classes3.dex */
public final class DummyTrackOutput implements TrackOutput {
    private final byte[] readBuffer = new byte[4096];

    @Override // com.google.android.exoplayer2.extractor.TrackOutput
    public void format(Format format) {
    }

    @Override // com.google.android.exoplayer2.extractor.TrackOutput
    public /* synthetic */ int sampleData(DataReader dataReader, int i2, boolean z2) {
        return d.a(this, dataReader, i2, z2);
    }

    @Override // com.google.android.exoplayer2.extractor.TrackOutput
    public int sampleData(DataReader dataReader, int i2, boolean z2, int i3) throws IOException {
        int i4 = dataReader.read(this.readBuffer, 0, Math.min(this.readBuffer.length, i2));
        if (i4 != -1) {
            return i4;
        }
        if (z2) {
            return -1;
        }
        throw new EOFException();
    }

    @Override // com.google.android.exoplayer2.extractor.TrackOutput
    public /* synthetic */ void sampleData(ParsableByteArray parsableByteArray, int i2) {
        d.b(this, parsableByteArray, i2);
    }

    @Override // com.google.android.exoplayer2.extractor.TrackOutput
    public void sampleMetadata(long j2, int i2, int i3, int i4, @Nullable TrackOutput.CryptoData cryptoData) {
    }

    @Override // com.google.android.exoplayer2.extractor.TrackOutput
    public void sampleData(ParsableByteArray parsableByteArray, int i2, int i3) {
        parsableByteArray.skipBytes(i2);
    }
}
