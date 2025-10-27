package org.eclipse.jetty.io.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import net.lingala.zip4j.util.InternalZipConstants;
import org.eclipse.jetty.io.AbstractBuffer;
import org.eclipse.jetty.io.Buffer;

/* loaded from: classes9.dex */
public class RandomAccessFileBuffer extends AbstractBuffer implements Buffer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    final int _capacity;
    final FileChannel _channel;
    final RandomAccessFile _file;

    public RandomAccessFileBuffer(File file) throws FileNotFoundException {
        super(2, true);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, InternalZipConstants.WRITE_MODE);
        this._file = randomAccessFile;
        this._channel = randomAccessFile.getChannel();
        this._capacity = Integer.MAX_VALUE;
        setGetIndex(0);
        setPutIndex((int) file.length());
    }

    @Override // org.eclipse.jetty.io.Buffer
    public byte[] array() {
        return null;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int capacity() {
        return this._capacity;
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public void clear() {
        try {
            synchronized (this._file) {
                super.clear();
                this._file.setLength(0L);
            }
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public byte peek() {
        byte b3;
        synchronized (this._file) {
            try {
                try {
                    if (this._get != this._file.getFilePointer()) {
                        this._file.seek(this._get);
                    }
                    b3 = this._file.readByte();
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return b3;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public void poke(int i2, byte b3) {
        synchronized (this._file) {
            try {
                try {
                    this._file.seek(i2);
                    this._file.writeByte(b3);
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public int writeTo(WritableByteChannel writableByteChannel, int i2, int i3) throws IOException {
        int iTransferTo;
        synchronized (this._file) {
            iTransferTo = (int) this._channel.transferTo(i2, i3, writableByteChannel);
        }
        return iTransferTo;
    }

    public RandomAccessFileBuffer(File file, int i2) throws FileNotFoundException {
        super(2, true);
        this._capacity = i2;
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, InternalZipConstants.WRITE_MODE);
        this._file = randomAccessFile;
        this._channel = randomAccessFile.getChannel();
        setGetIndex(0);
        setPutIndex((int) file.length());
    }

    @Override // org.eclipse.jetty.io.Buffer
    public byte peek(int i2) {
        byte b3;
        synchronized (this._file) {
            try {
                try {
                    this._file.seek(i2);
                    b3 = this._file.readByte();
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return b3;
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public int poke(int i2, byte[] bArr, int i3, int i4) {
        synchronized (this._file) {
            try {
                try {
                    this._file.seek(i2);
                    this._file.write(bArr, i3, i4);
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i4;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int peek(int i2, byte[] bArr, int i3, int i4) {
        int i5;
        synchronized (this._file) {
            try {
                try {
                    this._file.seek(i2);
                    i5 = this._file.read(bArr, i3, i4);
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i5;
    }

    public RandomAccessFileBuffer(File file, int i2, int i3) throws FileNotFoundException {
        super(i3, true);
        this._capacity = i2;
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, i3 == 2 ? InternalZipConstants.WRITE_MODE : "r");
        this._file = randomAccessFile;
        this._channel = randomAccessFile.getChannel();
        setGetIndex(0);
        setPutIndex((int) file.length());
    }
}
