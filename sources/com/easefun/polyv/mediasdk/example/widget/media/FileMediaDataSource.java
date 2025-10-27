package com.easefun.polyv.mediasdk.example.widget.media;

import com.easefun.polyv.mediasdk.player.misc.IMediaDataSource;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/* loaded from: classes3.dex */
public class FileMediaDataSource implements IMediaDataSource {
    private RandomAccessFile mFile;
    private long mFileSize;

    public FileMediaDataSource(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        this.mFile = randomAccessFile;
        this.mFileSize = randomAccessFile.length();
    }

    @Override // com.easefun.polyv.mediasdk.player.misc.IMediaDataSource
    public void close() throws IOException {
        this.mFileSize = 0L;
        this.mFile.close();
        this.mFile = null;
    }

    @Override // com.easefun.polyv.mediasdk.player.misc.IMediaDataSource
    public long getSize() throws IOException {
        return this.mFileSize;
    }

    @Override // com.easefun.polyv.mediasdk.player.misc.IMediaDataSource
    public int readAt(long j2, byte[] bArr, int i2, int i3) throws IOException {
        if (this.mFile.getFilePointer() != j2) {
            this.mFile.seek(j2);
        }
        if (i3 == 0) {
            return 0;
        }
        return this.mFile.read(bArr, 0, i3);
    }
}
