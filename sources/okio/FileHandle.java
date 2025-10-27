package okio;

import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.tencent.open.SocialConstants;
import io.socket.engineio.client.Socket;
import java.io.Closeable;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u00002\u00060\u0001j\u0002`\u0002:\u0002()B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\r\u001a\u00020\u000eJ\u0006\u0010\u000f\u001a\u00020\u000eJ\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\fJ\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014J\b\u0010\u0015\u001a\u00020\u000eH$J\b\u0010\u0016\u001a\u00020\u000eH$J(\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\bH$J\u0010\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\u0011H$J\b\u0010\u001f\u001a\u00020\u0011H$J(\u0010 \u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\bH$J&\u0010!\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\bJ\u001e\u0010!\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\"2\u0006\u0010\u001c\u001a\u00020\u0011J \u0010#\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\"2\u0006\u0010\u001c\u001a\u00020\u0011H\u0002J\u0016\u0010$\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0016\u0010$\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010%\u001a\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\u0011J\u0010\u0010\u0012\u001a\u00020\f2\b\b\u0002\u0010\u0018\u001a\u00020\u0011J\u0006\u0010\u001e\u001a\u00020\u0011J\u0010\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0018\u001a\u00020\u0011J&\u0010&\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\bJ\u001e\u0010&\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\"2\u0006\u0010\u001c\u001a\u00020\u0011J \u0010'\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\"2\u0006\u0010\u001c\u001a\u00020\u0011H\u0002R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006*"}, d2 = {"Lokio/FileHandle;", "Ljava/io/Closeable;", "Lokio/Closeable;", "readWrite", "", "(Z)V", "closed", "openStreamCount", "", "getReadWrite", "()Z", "appendingSink", "Lokio/Sink;", "close", "", Socket.EVENT_FLUSH, "position", "", "sink", SocialConstants.PARAM_SOURCE, "Lokio/Source;", "protectedClose", "protectedFlush", "protectedRead", "fileOffset", "array", "", "arrayOffset", "byteCount", "protectedResize", DatabaseManager.SIZE, "protectedSize", "protectedWrite", "read", "Lokio/Buffer;", "readNoCloseCheck", "reposition", "resize", "write", "writeNoCloseCheck", "FileHandleSink", "FileHandleSource", "okio"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes9.dex */
public abstract class FileHandle implements Closeable {
    private boolean closed;
    private int openStreamCount;
    private final boolean readWrite;

    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0014H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0005H\u0016R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u001c"}, d2 = {"Lokio/FileHandle$FileHandleSink;", "Lokio/Sink;", "fileHandle", "Lokio/FileHandle;", "position", "", "(Lokio/FileHandle;J)V", "closed", "", "getClosed", "()Z", "setClosed", "(Z)V", "getFileHandle", "()Lokio/FileHandle;", "getPosition", "()J", "setPosition", "(J)V", "close", "", Socket.EVENT_FLUSH, "timeout", "Lokio/Timeout;", "write", SocialConstants.PARAM_SOURCE, "Lokio/Buffer;", "byteCount", "okio"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class FileHandleSink implements Sink {
        private boolean closed;

        @NotNull
        private final FileHandle fileHandle;
        private long position;

        public FileHandleSink(@NotNull FileHandle fileHandle, long j2) {
            Intrinsics.checkNotNullParameter(fileHandle, "fileHandle");
            this.fileHandle = fileHandle;
            this.position = j2;
        }

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            this.closed = true;
            synchronized (this.fileHandle) {
                FileHandle fileHandle = this.fileHandle;
                fileHandle.openStreamCount--;
                if (this.fileHandle.openStreamCount == 0 && this.fileHandle.closed) {
                    Unit unit = Unit.INSTANCE;
                    this.fileHandle.protectedClose();
                }
            }
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            if (!(!this.closed)) {
                throw new IllegalStateException("closed".toString());
            }
            this.fileHandle.protectedFlush();
        }

        public final boolean getClosed() {
            return this.closed;
        }

        @NotNull
        public final FileHandle getFileHandle() {
            return this.fileHandle;
        }

        public final long getPosition() {
            return this.position;
        }

        public final void setClosed(boolean z2) {
            this.closed = z2;
        }

        public final void setPosition(long j2) {
            this.position = j2;
        }

        @Override // okio.Sink
        @NotNull
        public Timeout timeout() {
            return Timeout.NONE;
        }

        @Override // okio.Sink
        public void write(@NotNull Buffer source, long byteCount) throws IOException {
            Intrinsics.checkNotNullParameter(source, "source");
            if (!(!this.closed)) {
                throw new IllegalStateException("closed".toString());
            }
            this.fileHandle.writeNoCloseCheck(this.position, source, byteCount);
            this.position += byteCount;
        }
    }

    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0005H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u001b"}, d2 = {"Lokio/FileHandle$FileHandleSource;", "Lokio/Source;", "fileHandle", "Lokio/FileHandle;", "position", "", "(Lokio/FileHandle;J)V", "closed", "", "getClosed", "()Z", "setClosed", "(Z)V", "getFileHandle", "()Lokio/FileHandle;", "getPosition", "()J", "setPosition", "(J)V", "close", "", "read", "sink", "Lokio/Buffer;", "byteCount", "timeout", "Lokio/Timeout;", "okio"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class FileHandleSource implements Source {
        private boolean closed;

        @NotNull
        private final FileHandle fileHandle;
        private long position;

        public FileHandleSource(@NotNull FileHandle fileHandle, long j2) {
            Intrinsics.checkNotNullParameter(fileHandle, "fileHandle");
            this.fileHandle = fileHandle;
            this.position = j2;
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            this.closed = true;
            synchronized (this.fileHandle) {
                FileHandle fileHandle = this.fileHandle;
                fileHandle.openStreamCount--;
                if (this.fileHandle.openStreamCount == 0 && this.fileHandle.closed) {
                    Unit unit = Unit.INSTANCE;
                    this.fileHandle.protectedClose();
                }
            }
        }

        public final boolean getClosed() {
            return this.closed;
        }

        @NotNull
        public final FileHandle getFileHandle() {
            return this.fileHandle;
        }

        public final long getPosition() {
            return this.position;
        }

        @Override // okio.Source
        public long read(@NotNull Buffer sink, long byteCount) throws IOException {
            Intrinsics.checkNotNullParameter(sink, "sink");
            if (!(!this.closed)) {
                throw new IllegalStateException("closed".toString());
            }
            long noCloseCheck = this.fileHandle.readNoCloseCheck(this.position, sink, byteCount);
            if (noCloseCheck != -1) {
                this.position += noCloseCheck;
            }
            return noCloseCheck;
        }

        public final void setClosed(boolean z2) {
            this.closed = z2;
        }

        public final void setPosition(long j2) {
            this.position = j2;
        }

        @Override // okio.Source
        @NotNull
        public Timeout timeout() {
            return Timeout.NONE;
        }
    }

    public FileHandle(boolean z2) {
        this.readWrite = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final long readNoCloseCheck(long fileOffset, Buffer sink, long byteCount) throws IOException {
        if (!(byteCount >= 0)) {
            throw new IllegalArgumentException(("byteCount < 0: " + byteCount).toString());
        }
        long j2 = fileOffset + byteCount;
        long j3 = fileOffset;
        while (true) {
            if (j3 >= j2) {
                break;
            }
            Segment segmentWritableSegment$okio = sink.writableSegment$okio(1);
            int iProtectedRead = protectedRead(j3, segmentWritableSegment$okio.data, segmentWritableSegment$okio.limit, (int) Math.min(j2 - j3, 8192 - r9));
            if (iProtectedRead == -1) {
                if (segmentWritableSegment$okio.pos == segmentWritableSegment$okio.limit) {
                    sink.head = segmentWritableSegment$okio.pop();
                    SegmentPool.recycle(segmentWritableSegment$okio);
                }
                if (fileOffset == j3) {
                    return -1L;
                }
            } else {
                segmentWritableSegment$okio.limit += iProtectedRead;
                long j4 = iProtectedRead;
                j3 += j4;
                sink.setSize$okio(sink.size() + j4);
            }
        }
        return j3 - fileOffset;
    }

    public static /* synthetic */ Sink sink$default(FileHandle fileHandle, long j2, int i2, Object obj) throws IOException {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: sink");
        }
        if ((i2 & 1) != 0) {
            j2 = 0;
        }
        return fileHandle.sink(j2);
    }

    public static /* synthetic */ Source source$default(FileHandle fileHandle, long j2, int i2, Object obj) throws IOException {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: source");
        }
        if ((i2 & 1) != 0) {
            j2 = 0;
        }
        return fileHandle.source(j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void writeNoCloseCheck(long fileOffset, Buffer source, long byteCount) throws IOException {
        _UtilKt.checkOffsetAndCount(source.size(), 0L, byteCount);
        long j2 = byteCount + fileOffset;
        while (fileOffset < j2) {
            Segment segment = source.head;
            Intrinsics.checkNotNull(segment);
            int iMin = (int) Math.min(j2 - fileOffset, segment.limit - segment.pos);
            protectedWrite(fileOffset, segment.data, segment.pos, iMin);
            segment.pos += iMin;
            long j3 = iMin;
            fileOffset += j3;
            source.setSize$okio(source.size() - j3);
            if (segment.pos == segment.limit) {
                source.head = segment.pop();
                SegmentPool.recycle(segment);
            }
        }
    }

    @NotNull
    public final Sink appendingSink() throws IOException {
        return sink(size());
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        synchronized (this) {
            if (this.closed) {
                return;
            }
            this.closed = true;
            if (this.openStreamCount != 0) {
                return;
            }
            Unit unit = Unit.INSTANCE;
            protectedClose();
        }
    }

    public final void flush() throws IOException {
        if (!this.readWrite) {
            throw new IllegalStateException("file handle is read-only".toString());
        }
        synchronized (this) {
            if (!(!this.closed)) {
                throw new IllegalStateException("closed".toString());
            }
            Unit unit = Unit.INSTANCE;
        }
        protectedFlush();
    }

    public final boolean getReadWrite() {
        return this.readWrite;
    }

    public final long position(@NotNull Source source) throws IOException {
        long size;
        Intrinsics.checkNotNullParameter(source, "source");
        if (source instanceof buffer) {
            buffer bufferVar = (buffer) source;
            size = bufferVar.bufferField.size();
            source = bufferVar.source;
        } else {
            size = 0;
        }
        if (!((source instanceof FileHandleSource) && ((FileHandleSource) source).getFileHandle() == this)) {
            throw new IllegalArgumentException("source was not created by this FileHandle".toString());
        }
        FileHandleSource fileHandleSource = (FileHandleSource) source;
        if (!fileHandleSource.getClosed()) {
            return fileHandleSource.getPosition() - size;
        }
        throw new IllegalStateException("closed".toString());
    }

    public abstract void protectedClose() throws IOException;

    public abstract void protectedFlush() throws IOException;

    public abstract int protectedRead(long fileOffset, @NotNull byte[] array, int arrayOffset, int byteCount) throws IOException;

    public abstract void protectedResize(long size) throws IOException;

    public abstract long protectedSize() throws IOException;

    public abstract void protectedWrite(long fileOffset, @NotNull byte[] array, int arrayOffset, int byteCount) throws IOException;

    public final int read(long fileOffset, @NotNull byte[] array, int arrayOffset, int byteCount) throws IOException {
        Intrinsics.checkNotNullParameter(array, "array");
        synchronized (this) {
            if (!(!this.closed)) {
                throw new IllegalStateException("closed".toString());
            }
            Unit unit = Unit.INSTANCE;
        }
        return protectedRead(fileOffset, array, arrayOffset, byteCount);
    }

    public final void reposition(@NotNull Source source, long position) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        boolean z2 = false;
        if (!(source instanceof buffer)) {
            if ((source instanceof FileHandleSource) && ((FileHandleSource) source).getFileHandle() == this) {
                z2 = true;
            }
            if (!z2) {
                throw new IllegalArgumentException("source was not created by this FileHandle".toString());
            }
            FileHandleSource fileHandleSource = (FileHandleSource) source;
            if (!(!fileHandleSource.getClosed())) {
                throw new IllegalStateException("closed".toString());
            }
            fileHandleSource.setPosition(position);
            return;
        }
        buffer bufferVar = (buffer) source;
        Source source2 = bufferVar.source;
        if (!((source2 instanceof FileHandleSource) && ((FileHandleSource) source2).getFileHandle() == this)) {
            throw new IllegalArgumentException("source was not created by this FileHandle".toString());
        }
        FileHandleSource fileHandleSource2 = (FileHandleSource) source2;
        if (!(!fileHandleSource2.getClosed())) {
            throw new IllegalStateException("closed".toString());
        }
        long size = bufferVar.bufferField.size();
        long position2 = position - (fileHandleSource2.getPosition() - size);
        if (0 <= position2 && position2 < size) {
            z2 = true;
        }
        if (z2) {
            bufferVar.skip(position2);
        } else {
            bufferVar.bufferField.clear();
            fileHandleSource2.setPosition(position);
        }
    }

    public final void resize(long size) throws IOException {
        if (!this.readWrite) {
            throw new IllegalStateException("file handle is read-only".toString());
        }
        synchronized (this) {
            if (!(!this.closed)) {
                throw new IllegalStateException("closed".toString());
            }
            Unit unit = Unit.INSTANCE;
        }
        protectedResize(size);
    }

    @NotNull
    public final Sink sink(long fileOffset) throws IOException {
        if (!this.readWrite) {
            throw new IllegalStateException("file handle is read-only".toString());
        }
        synchronized (this) {
            if (!(!this.closed)) {
                throw new IllegalStateException("closed".toString());
            }
            this.openStreamCount++;
        }
        return new FileHandleSink(this, fileOffset);
    }

    public final long size() throws IOException {
        synchronized (this) {
            if (!(!this.closed)) {
                throw new IllegalStateException("closed".toString());
            }
            Unit unit = Unit.INSTANCE;
        }
        return protectedSize();
    }

    @NotNull
    public final Source source(long fileOffset) throws IOException {
        synchronized (this) {
            if (!(!this.closed)) {
                throw new IllegalStateException("closed".toString());
            }
            this.openStreamCount++;
        }
        return new FileHandleSource(this, fileOffset);
    }

    public final void write(long fileOffset, @NotNull byte[] array, int arrayOffset, int byteCount) throws IOException {
        Intrinsics.checkNotNullParameter(array, "array");
        if (!this.readWrite) {
            throw new IllegalStateException("file handle is read-only".toString());
        }
        synchronized (this) {
            if (!(!this.closed)) {
                throw new IllegalStateException("closed".toString());
            }
            Unit unit = Unit.INSTANCE;
        }
        protectedWrite(fileOffset, array, arrayOffset, byteCount);
    }

    public final long read(long fileOffset, @NotNull Buffer sink, long byteCount) throws IOException {
        Intrinsics.checkNotNullParameter(sink, "sink");
        synchronized (this) {
            if (!this.closed) {
                Unit unit = Unit.INSTANCE;
            } else {
                throw new IllegalStateException("closed".toString());
            }
        }
        return readNoCloseCheck(fileOffset, sink, byteCount);
    }

    public final void write(long fileOffset, @NotNull Buffer source, long byteCount) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        if (this.readWrite) {
            synchronized (this) {
                if (!this.closed) {
                    Unit unit = Unit.INSTANCE;
                } else {
                    throw new IllegalStateException("closed".toString());
                }
            }
            writeNoCloseCheck(fileOffset, source, byteCount);
            return;
        }
        throw new IllegalStateException("file handle is read-only".toString());
    }

    public final long position(@NotNull Sink sink) throws IOException {
        long size;
        Intrinsics.checkNotNullParameter(sink, "sink");
        if (sink instanceof buffer) {
            buffer bufferVar = (buffer) sink;
            size = bufferVar.bufferField.size();
            sink = bufferVar.sink;
        } else {
            size = 0;
        }
        if ((sink instanceof FileHandleSink) && ((FileHandleSink) sink).getFileHandle() == this) {
            FileHandleSink fileHandleSink = (FileHandleSink) sink;
            if (!fileHandleSink.getClosed()) {
                return fileHandleSink.getPosition() + size;
            }
            throw new IllegalStateException("closed".toString());
        }
        throw new IllegalArgumentException("sink was not created by this FileHandle".toString());
    }

    public final void reposition(@NotNull Sink sink, long position) throws IOException {
        Intrinsics.checkNotNullParameter(sink, "sink");
        boolean z2 = false;
        if (sink instanceof buffer) {
            buffer bufferVar = (buffer) sink;
            Sink sink2 = bufferVar.sink;
            if ((sink2 instanceof FileHandleSink) && ((FileHandleSink) sink2).getFileHandle() == this) {
                z2 = true;
            }
            if (z2) {
                FileHandleSink fileHandleSink = (FileHandleSink) sink2;
                if (!fileHandleSink.getClosed()) {
                    bufferVar.emit();
                    fileHandleSink.setPosition(position);
                    return;
                }
                throw new IllegalStateException("closed".toString());
            }
            throw new IllegalArgumentException("sink was not created by this FileHandle".toString());
        }
        if ((sink instanceof FileHandleSink) && ((FileHandleSink) sink).getFileHandle() == this) {
            z2 = true;
        }
        if (z2) {
            FileHandleSink fileHandleSink2 = (FileHandleSink) sink;
            if (!fileHandleSink2.getClosed()) {
                fileHandleSink2.setPosition(position);
                return;
            }
            throw new IllegalStateException("closed".toString());
        }
        throw new IllegalArgumentException("sink was not created by this FileHandle".toString());
    }
}
