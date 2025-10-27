package cn.hutool.core.io;

import cn.hutool.core.io.copy.ChannelCopier;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class NioUtil {
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final int DEFAULT_LARGE_BUFFER_SIZE = 32768;
    public static final int DEFAULT_MIDDLE_BUFFER_SIZE = 16384;
    public static final int EOF = -1;

    public static void close(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception unused) {
            }
        }
    }

    public static long copy(FileChannel fileChannel, FileChannel fileChannel2) throws IllegalArgumentException, IORuntimeException {
        Assert.notNull(fileChannel, "In channel is null!", new Object[0]);
        Assert.notNull(fileChannel2, "Out channel is null!", new Object[0]);
        try {
            return copySafely(fileChannel, fileChannel2);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static long copyByNIO(InputStream inputStream, OutputStream outputStream, int i2, StreamProgress streamProgress) throws IORuntimeException {
        return copyByNIO(inputStream, outputStream, i2, -1L, streamProgress);
    }

    private static long copySafely(FileChannel fileChannel, FileChannel fileChannel2) throws IOException {
        long size = fileChannel.size();
        long j2 = size;
        long j3 = 0;
        while (j2 > 0) {
            long jTransferTo = fileChannel.transferTo(j3, j2, fileChannel2);
            j3 += jTransferTo;
            j2 -= jTransferTo;
        }
        return size;
    }

    public static String read(ReadableByteChannel readableByteChannel, Charset charset) throws IORuntimeException {
        FastByteArrayOutputStream fastByteArrayOutputStream = read(readableByteChannel);
        return charset == null ? fastByteArrayOutputStream.toString() : fastByteArrayOutputStream.toString(charset);
    }

    public static String readUtf8(FileChannel fileChannel) throws IORuntimeException {
        return read(fileChannel, CharsetUtil.CHARSET_UTF_8);
    }

    public static long copyByNIO(InputStream inputStream, OutputStream outputStream, int i2, long j2, StreamProgress streamProgress) throws IOException, IORuntimeException {
        long jCopy = copy(Channels.newChannel(inputStream), Channels.newChannel(outputStream), i2, j2, streamProgress);
        IoUtil.flush(outputStream);
        return jCopy;
    }

    public static FastByteArrayOutputStream read(ReadableByteChannel readableByteChannel) throws IORuntimeException {
        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream();
        copy(readableByteChannel, Channels.newChannel(fastByteArrayOutputStream));
        return fastByteArrayOutputStream;
    }

    public static long copy(ReadableByteChannel readableByteChannel, WritableByteChannel writableByteChannel) throws IORuntimeException {
        return copy(readableByteChannel, writableByteChannel, 8192);
    }

    public static String read(FileChannel fileChannel, String str) throws IORuntimeException {
        return read(fileChannel, CharsetUtil.charset(str));
    }

    public static long copy(ReadableByteChannel readableByteChannel, WritableByteChannel writableByteChannel, int i2) throws IORuntimeException {
        return copy(readableByteChannel, writableByteChannel, i2, null);
    }

    public static String read(FileChannel fileChannel, Charset charset) throws IORuntimeException {
        try {
            return StrUtil.str((ByteBuffer) fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, fileChannel.size()).load(), charset);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static long copy(ReadableByteChannel readableByteChannel, WritableByteChannel writableByteChannel, int i2, StreamProgress streamProgress) throws IORuntimeException {
        return copy(readableByteChannel, writableByteChannel, i2, -1L, streamProgress);
    }

    public static long copy(ReadableByteChannel readableByteChannel, WritableByteChannel writableByteChannel, int i2, long j2, StreamProgress streamProgress) throws IORuntimeException {
        return new ChannelCopier(i2, j2, streamProgress).copy(readableByteChannel, writableByteChannel);
    }
}
