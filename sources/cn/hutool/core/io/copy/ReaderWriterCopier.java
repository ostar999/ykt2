package cn.hutool.core.io.copy;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.lang.Assert;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/* loaded from: classes.dex */
public class ReaderWriterCopier extends IoCopier<Reader, Writer> {
    public ReaderWriterCopier() {
        this(8192);
    }

    private long doCopy(Reader reader, Writer writer, char[] cArr, StreamProgress streamProgress) throws IOException {
        int i2;
        long j2 = this.count;
        if (j2 <= 0) {
            j2 = Long.MAX_VALUE;
        }
        long j3 = 0;
        while (j2 > 0 && (i2 = reader.read(cArr, 0, bufferSize(j2))) >= 0) {
            writer.write(cArr, 0, i2);
            if (this.flushEveryBuffer) {
                writer.flush();
            }
            long j4 = i2;
            j2 -= j4;
            j3 += j4;
            if (streamProgress != null) {
                streamProgress.progress(this.count, j3);
            }
        }
        return j3;
    }

    public ReaderWriterCopier(int i2) {
        this(i2, -1L);
    }

    @Override // cn.hutool.core.io.copy.IoCopier
    public long copy(Reader reader, Writer writer) throws IOException, IllegalArgumentException {
        Assert.notNull(reader, "InputStream is null !", new Object[0]);
        Assert.notNull(writer, "OutputStream is null !", new Object[0]);
        StreamProgress streamProgress = this.progress;
        if (streamProgress != null) {
            streamProgress.start();
        }
        try {
            long jDoCopy = doCopy(reader, writer, new char[bufferSize(this.count)], streamProgress);
            writer.flush();
            if (streamProgress != null) {
                streamProgress.finish();
            }
            return jDoCopy;
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public ReaderWriterCopier(int i2, long j2) {
        this(i2, j2, null);
    }

    public ReaderWriterCopier(int i2, long j2, StreamProgress streamProgress) {
        super(i2, j2, streamProgress);
    }
}
