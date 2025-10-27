package org.eclipse.jetty.io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import org.eclipse.jetty.io.AbstractBuffer;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class DirectNIOBuffer extends AbstractBuffer implements NIOBuffer {
    private static final Logger LOG = Log.getLogger((Class<?>) DirectNIOBuffer.class);
    protected final ByteBuffer _buf;
    private ReadableByteChannel _in;
    private InputStream _inStream;
    private WritableByteChannel _out;
    private OutputStream _outStream;

    public DirectNIOBuffer(int i2) {
        super(2, false);
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(i2);
        this._buf = byteBufferAllocateDirect;
        byteBufferAllocateDirect.position(0);
        byteBufferAllocateDirect.limit(byteBufferAllocateDirect.capacity());
    }

    @Override // org.eclipse.jetty.io.Buffer
    public byte[] array() {
        return null;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int capacity() {
        return this._buf.capacity();
    }

    @Override // org.eclipse.jetty.io.nio.NIOBuffer
    public ByteBuffer getByteBuffer() {
        return this._buf;
    }

    @Override // org.eclipse.jetty.io.nio.NIOBuffer
    public boolean isDirect() {
        return true;
    }

    @Override // org.eclipse.jetty.io.Buffer
    public byte peek(int i2) {
        return this._buf.get(i2);
    }

    @Override // org.eclipse.jetty.io.Buffer
    public void poke(int i2, byte b3) {
        if (isReadOnly()) {
            throw new IllegalStateException("READONLY");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("index<0: " + i2 + "<0");
        }
        if (i2 <= capacity()) {
            this._buf.put(i2, b3);
            return;
        }
        throw new IllegalArgumentException("index>capacity(): " + i2 + ">" + capacity());
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0044, code lost:
    
        r9._in = null;
        r9._inStream = r10;
     */
    /* JADX WARN: Finally extract failed */
    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int readFrom(java.io.InputStream r10, int r11) throws java.io.IOException {
        /*
            r9 = this;
            java.nio.channels.ReadableByteChannel r0 = r9._in
            if (r0 == 0) goto Le
            boolean r0 = r0.isOpen()
            if (r0 == 0) goto Le
            java.io.InputStream r0 = r9._inStream
            if (r10 == r0) goto L16
        Le:
            java.nio.channels.ReadableByteChannel r0 = java.nio.channels.Channels.newChannel(r10)
            r9._in = r0
            r9._inStream = r10
        L16:
            if (r11 < 0) goto L1e
            int r0 = r9.space()
            if (r11 <= r0) goto L22
        L1e:
            int r11 = r9.space()
        L22:
            int r0 = r9.putIndex()
            r1 = 0
            r3 = r11
            r2 = r1
            r4 = r2
            r5 = r4
        L2b:
            r6 = 0
            if (r2 >= r11) goto L86
            java.nio.ByteBuffer r5 = r9._buf     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L63
            r5.position(r0)     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L63
            java.nio.ByteBuffer r5 = r9._buf     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L63
            int r7 = r0 + r3
            r5.limit(r7)     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L63
            java.nio.channels.ReadableByteChannel r5 = r9._in     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L63
            java.nio.ByteBuffer r7 = r9._buf     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L63
            int r5 = r5.read(r7)     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L63
            if (r5 >= 0) goto L49
            r9._in = r6     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L63
            r9._inStream = r10     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L63
            goto L86
        L49:
            if (r5 <= 0) goto L53
            int r0 = r0 + r5
            int r2 = r2 + r5
            int r3 = r3 - r5
            r9.setPutIndex(r0)     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L63
            r4 = r1
            goto L5a
        L53:
            int r7 = r4 + 1
            r8 = 1
            if (r4 <= r8) goto L59
            goto L86
        L59:
            r4 = r7
        L5a:
            int r7 = r10.available()     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L63
            if (r7 > 0) goto L2b
            goto L86
        L61:
            r11 = move-exception
            goto L69
        L63:
            r11 = move-exception
            r9._in = r6     // Catch: java.lang.Throwable -> L61
            r9._inStream = r10     // Catch: java.lang.Throwable -> L61
            throw r11     // Catch: java.lang.Throwable -> L61
        L69:
            java.nio.channels.ReadableByteChannel r0 = r9._in
            if (r0 == 0) goto L77
            boolean r0 = r0.isOpen()
            if (r0 != 0) goto L77
            r9._in = r6
            r9._inStream = r10
        L77:
            java.nio.ByteBuffer r10 = r9._buf
            r10.position(r1)
            java.nio.ByteBuffer r10 = r9._buf
            int r0 = r10.capacity()
            r10.limit(r0)
            throw r11
        L86:
            if (r5 >= 0) goto La8
            if (r2 != 0) goto La8
            java.nio.channels.ReadableByteChannel r11 = r9._in
            if (r11 == 0) goto L98
            boolean r11 = r11.isOpen()
            if (r11 != 0) goto L98
            r9._in = r6
            r9._inStream = r10
        L98:
            java.nio.ByteBuffer r10 = r9._buf
            r10.position(r1)
            java.nio.ByteBuffer r10 = r9._buf
            int r11 = r10.capacity()
            r10.limit(r11)
            r10 = -1
            return r10
        La8:
            java.nio.channels.ReadableByteChannel r11 = r9._in
            if (r11 == 0) goto Lb6
            boolean r11 = r11.isOpen()
            if (r11 != 0) goto Lb6
            r9._in = r6
            r9._inStream = r10
        Lb6:
            java.nio.ByteBuffer r10 = r9._buf
            r10.position(r1)
            java.nio.ByteBuffer r10 = r9._buf
            int r11 = r10.capacity()
            r10.limit(r11)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.io.nio.DirectNIOBuffer.readFrom(java.io.InputStream, int):int");
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public void writeTo(OutputStream outputStream) throws IOException {
        int iWrite;
        WritableByteChannel writableByteChannel = this._out;
        if (writableByteChannel == null || !writableByteChannel.isOpen() || outputStream != this._outStream) {
            this._out = Channels.newChannel(outputStream);
            this._outStream = outputStream;
        }
        synchronized (this._buf) {
            loop0: while (true) {
                int i2 = 0;
                while (true) {
                    try {
                        try {
                            if (!hasContent() || !this._out.isOpen()) {
                                break loop0;
                            }
                            this._buf.position(getIndex());
                            this._buf.limit(putIndex());
                            iWrite = this._out.write(this._buf);
                            if (iWrite < 0) {
                                break loop0;
                            }
                            if (iWrite > 0) {
                                break;
                            }
                            int i3 = i2 + 1;
                            if (i2 > 1) {
                                break loop0;
                            } else {
                                i2 = i3;
                            }
                        } catch (IOException e2) {
                            this._out = null;
                            this._outStream = null;
                            throw e2;
                        }
                    } finally {
                        WritableByteChannel writableByteChannel2 = this._out;
                        if (writableByteChannel2 != null && !writableByteChannel2.isOpen()) {
                            this._out = null;
                            this._outStream = null;
                        }
                        this._buf.position(0);
                        ByteBuffer byteBuffer = this._buf;
                        byteBuffer.limit(byteBuffer.capacity());
                    }
                }
                skip(iWrite);
            }
        }
    }

    @Override // org.eclipse.jetty.io.Buffer
    public int peek(int i2, byte[] bArr, int i3, int i4) {
        if ((i2 + i4 > capacity() && (i4 = capacity() - i2) == 0) || i4 < 0) {
            return -1;
        }
        try {
            this._buf.position(i2);
            this._buf.get(bArr, i3, i4);
            return i4;
        } finally {
            this._buf.position(0);
        }
    }

    public DirectNIOBuffer(ByteBuffer byteBuffer, boolean z2) {
        super(z2 ? 0 : 2, false);
        if (byteBuffer.isDirect()) {
            this._buf = byteBuffer;
            setGetIndex(byteBuffer.position());
            setPutIndex(byteBuffer.limit());
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public int poke(int i2, Buffer buffer) {
        if (!isReadOnly()) {
            byte[] bArrArray = buffer.array();
            if (bArrArray != null) {
                return poke(i2, bArrArray, buffer.getIndex(), buffer.length());
            }
            Buffer buffer2 = buffer.buffer();
            if (buffer2 instanceof DirectNIOBuffer) {
                ByteBuffer byteBufferDuplicate = ((DirectNIOBuffer) buffer2)._buf;
                ByteBuffer byteBuffer = this._buf;
                if (byteBufferDuplicate == byteBuffer) {
                    byteBufferDuplicate = byteBuffer.duplicate();
                }
                try {
                    this._buf.position(i2);
                    int iRemaining = this._buf.remaining();
                    int length = buffer.length();
                    if (length <= iRemaining) {
                        iRemaining = length;
                    }
                    byteBufferDuplicate.position(buffer.getIndex());
                    byteBufferDuplicate.limit(buffer.getIndex() + iRemaining);
                    this._buf.put(byteBufferDuplicate);
                    return iRemaining;
                } finally {
                    this._buf.position(0);
                    byteBufferDuplicate.limit(byteBufferDuplicate.capacity());
                    byteBufferDuplicate.position(0);
                }
            }
            return super.poke(i2, buffer);
        }
        throw new IllegalStateException("READONLY");
    }

    public DirectNIOBuffer(File file) throws Throwable {
        FileInputStream fileInputStream;
        super(1, false);
        FileChannel channel = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (Throwable th) {
            th = th;
            fileInputStream = null;
        }
        try {
            channel = fileInputStream.getChannel();
            this._buf = channel.map(FileChannel.MapMode.READ_ONLY, 0L, file.length());
            setGetIndex(0);
            setPutIndex((int) file.length());
            this._access = 0;
            try {
                channel.close();
            } catch (IOException e2) {
                LOG.ignore(e2);
            }
            IO.close(fileInputStream);
        } catch (Throwable th2) {
            th = th2;
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e3) {
                    LOG.ignore(e3);
                }
            }
            IO.close(fileInputStream);
            throw th;
        }
    }

    @Override // org.eclipse.jetty.io.AbstractBuffer, org.eclipse.jetty.io.Buffer
    public int poke(int i2, byte[] bArr, int i3, int i4) {
        if (isReadOnly()) {
            throw new IllegalStateException("READONLY");
        }
        if (i2 >= 0) {
            if (i2 + i4 > capacity() && (i4 = capacity() - i2) < 0) {
                throw new IllegalArgumentException("index>capacity(): " + i2 + ">" + capacity());
            }
            try {
                this._buf.position(i2);
                int iRemaining = this._buf.remaining();
                if (i4 > iRemaining) {
                    i4 = iRemaining;
                }
                if (i4 > 0) {
                    this._buf.put(bArr, i3, i4);
                }
                return i4;
            } finally {
                this._buf.position(0);
            }
        }
        throw new IllegalArgumentException("index<0: " + i2 + "<0");
    }
}
