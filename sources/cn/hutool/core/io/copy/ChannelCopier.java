package cn.hutool.core.io.copy;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.lang.Assert;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/* loaded from: classes.dex */
public class ChannelCopier extends IoCopier<ReadableByteChannel, WritableByteChannel> {
    public ChannelCopier() {
        this(8192);
    }

    private long doCopy(ReadableByteChannel readableByteChannel, WritableByteChannel writableByteChannel, ByteBuffer byteBuffer, StreamProgress streamProgress) throws IOException {
        int i2;
        long j2 = this.count;
        if (j2 <= 0) {
            j2 = Long.MAX_VALUE;
        }
        long j3 = 0;
        while (j2 > 0 && (i2 = readableByteChannel.read(byteBuffer)) >= 0) {
            byteBuffer.flip();
            writableByteChannel.write(byteBuffer);
            byteBuffer.clear();
            long j4 = i2;
            j2 -= j4;
            j3 += j4;
            if (streamProgress != null) {
                streamProgress.progress(this.count, j3);
            }
        }
        return j3;
    }

    public ChannelCopier(int i2) {
        this(i2, -1L);
    }

    @Override // cn.hutool.core.io.copy.IoCopier
    public long copy(ReadableByteChannel readableByteChannel, WritableByteChannel writableByteChannel) throws IllegalArgumentException {
        Assert.notNull(readableByteChannel, "InputStream is null !", new Object[0]);
        Assert.notNull(writableByteChannel, "OutputStream is null !", new Object[0]);
        StreamProgress streamProgress = this.progress;
        if (streamProgress != null) {
            streamProgress.start();
        }
        try {
            long jDoCopy = doCopy(readableByteChannel, writableByteChannel, ByteBuffer.allocate(bufferSize(this.count)), streamProgress);
            if (streamProgress != null) {
                streamProgress.finish();
            }
            return jDoCopy;
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public ChannelCopier(int i2, long j2) {
        this(i2, j2, null);
    }

    public ChannelCopier(int i2, long j2, StreamProgress streamProgress) {
        super(i2, j2, streamProgress);
    }
}
