package com.arialyy.aria.http;

import com.arialyy.aria.util.ALog;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

@Deprecated
/* loaded from: classes2.dex */
public class ChunkedInputStream extends InputStream {
    private static final String TAG = "ChunkedInputStream";
    private DataInputStream din;
    private String id;
    private int unreadBytes = 0;
    private byte[] singleByte = new byte[1];
    private boolean endOfData = false;

    public ChunkedInputStream(InputStream inputStream, String str) {
        this.din = new DataInputStream(inputStream);
        this.id = str;
        ALog.d(TAG, String.format("Creating chunked input for %s", str));
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        ALog.d(TAG, String.format("%s: Closing chunked input.", this.id));
        this.din.close();
    }

    public boolean isEndOfData() {
        return this.endOfData;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (read(this.singleByte, 0, 1) == -1) {
            return -1;
        }
        return this.singleByte[0];
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        if (i3 < 0) {
            throw new IllegalArgumentException(this.id + ": Negative read length");
        }
        if (i3 == 0) {
            return 0;
        }
        if (this.unreadBytes == 0) {
            try {
                int i4 = this.din.readInt();
                this.unreadBytes = i4;
                ALog.d(TAG, String.format("%s: Chunk size %s", this.id, Integer.valueOf(i4)));
                if (this.unreadBytes == 0) {
                    ALog.d(TAG, String.format("%s: Hit end of data", this.id));
                    this.endOfData = true;
                    return -1;
                }
            } catch (IOException e2) {
                throw new IOException(this.id + ": Error while attempting to read chunk length", e2);
            }
        }
        int iMin = Math.min(i3, this.unreadBytes);
        try {
            this.din.readFully(bArr, i2, iMin);
            this.unreadBytes -= iMin;
            return 0 + iMin;
        } catch (IOException e3) {
            throw new IOException(this.id + ": Error while attempting to read " + iMin + " bytes from current chunk", e3);
        }
    }
}
