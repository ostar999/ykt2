package com.google.android.exoplayer2.source.mediaparser;

import android.annotation.SuppressLint;
import android.media.MediaParser;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

@RequiresApi(30)
@SuppressLint({"Override"})
/* loaded from: classes3.dex */
public final class InputReaderAdapterV30 implements MediaParser.SeekableInputReader {
    private long currentPosition;

    @Nullable
    private DataReader dataReader;
    private long lastSeekPosition;
    private long resourceLength;

    public long getAndResetSeekPosition() {
        long j2 = this.lastSeekPosition;
        this.lastSeekPosition = -1L;
        return j2;
    }

    @Override // android.media.MediaParser.InputReader
    public long getLength() {
        return this.resourceLength;
    }

    @Override // android.media.MediaParser.InputReader
    public long getPosition() {
        return this.currentPosition;
    }

    @Override // android.media.MediaParser.InputReader
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        int i4 = ((DataReader) Util.castNonNull(this.dataReader)).read(bArr, i2, i3);
        this.currentPosition += i4;
        return i4;
    }

    @Override // android.media.MediaParser.SeekableInputReader
    public void seekToPosition(long j2) {
        this.lastSeekPosition = j2;
    }

    public void setCurrentPosition(long j2) {
        this.currentPosition = j2;
    }

    public void setDataReader(DataReader dataReader, long j2) {
        this.dataReader = dataReader;
        this.resourceLength = j2;
        this.lastSeekPosition = -1L;
    }
}
