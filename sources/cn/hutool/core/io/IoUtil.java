package cn.hutool.core.io;

import cn.hutool.core.collection.LineIter;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.io.copy.ReaderWriterCopier;
import cn.hutool.core.io.copy.StreamCopier;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PushbackInputStream;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

/* loaded from: classes.dex */
public class IoUtil extends NioUtil {
    public static Checksum checksum(InputStream inputStream, Checksum checksum) throws Throwable {
        InputStream checkedInputStream;
        Throwable th;
        Assert.notNull(inputStream, "InputStream is null !", new Object[0]);
        if (checksum == null) {
            checksum = new CRC32();
        }
        try {
            checkedInputStream = new CheckedInputStream(inputStream, checksum);
        } catch (Throwable th2) {
            checkedInputStream = inputStream;
            th = th2;
        }
        try {
            copy(checkedInputStream, new NullOutputStream());
            close((Closeable) checkedInputStream);
            return checksum;
        } catch (Throwable th3) {
            th = th3;
            close((Closeable) checkedInputStream);
            throw th;
        }
    }

    public static long checksumCRC32(InputStream inputStream) throws IORuntimeException {
        return checksum(inputStream, new CRC32()).getValue();
    }

    public static long checksumValue(InputStream inputStream, Checksum checksum) {
        return checksum(inputStream, checksum).getValue();
    }

    public static void close(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    public static void closeIfPosible(Object obj) {
        if (obj instanceof AutoCloseable) {
            NioUtil.close((AutoCloseable) obj);
        }
    }

    public static boolean contentEquals(InputStream inputStream, InputStream inputStream2) throws IOException, IORuntimeException {
        if (!(inputStream instanceof BufferedInputStream)) {
            inputStream = new BufferedInputStream(inputStream);
        }
        if (!(inputStream2 instanceof BufferedInputStream)) {
            inputStream2 = new BufferedInputStream(inputStream2);
        }
        try {
            for (int i2 = inputStream.read(); -1 != i2; i2 = inputStream.read()) {
                if (i2 != inputStream2.read()) {
                    return false;
                }
            }
            return inputStream2.read() == -1;
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static boolean contentEqualsIgnoreEOL(Reader reader, Reader reader2) throws IOException, IORuntimeException {
        BufferedReader reader3 = getReader(reader);
        BufferedReader reader4 = getReader(reader2);
        try {
            String line = reader3.readLine();
            String line2 = reader4.readLine();
            while (line != null && line.equals(line2)) {
                line = reader3.readLine();
                line2 = reader4.readLine();
            }
            return Objects.equals(line, line2);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static long copy(Reader reader, Writer writer) throws IORuntimeException {
        return copy(reader, writer, 8192);
    }

    public static void flush(Flushable flushable) throws IOException {
        if (flushable != null) {
            try {
                flushable.flush();
            } catch (Exception unused) {
            }
        }
    }

    public static BomReader getBomReader(InputStream inputStream) {
        return new BomReader(inputStream);
    }

    public static PushbackReader getPushBackReader(Reader reader, int i2) {
        return reader instanceof PushbackReader ? (PushbackReader) reader : new PushbackReader(reader, i2);
    }

    @Deprecated
    public static BufferedReader getReader(InputStream inputStream, String str) {
        return getReader(inputStream, Charset.forName(str));
    }

    public static BufferedReader getUtf8Reader(InputStream inputStream) {
        return getReader(inputStream, CharsetUtil.CHARSET_UTF_8);
    }

    public static OutputStreamWriter getUtf8Writer(OutputStream outputStream) {
        return getWriter(outputStream, CharsetUtil.CHARSET_UTF_8);
    }

    @Deprecated
    public static OutputStreamWriter getWriter(OutputStream outputStream, String str) {
        return getWriter(outputStream, Charset.forName(str));
    }

    public static LineIter lineIter(Reader reader) {
        return new LineIter(reader);
    }

    @Deprecated
    public static String read(InputStream inputStream, String str) throws IORuntimeException {
        FastByteArrayOutputStream fastByteArrayOutputStream = read(inputStream);
        return CharSequenceUtil.isBlank(str) ? fastByteArrayOutputStream.toString() : fastByteArrayOutputStream.toString(str);
    }

    public static byte[] readBytes(InputStream inputStream) throws IORuntimeException {
        return readBytes(inputStream, true);
    }

    public static String readHex(InputStream inputStream, int i2, boolean z2) throws IORuntimeException {
        return HexUtil.encodeHexStr(readBytes(inputStream, i2), z2);
    }

    public static String readHex64Lower(InputStream inputStream) throws IORuntimeException {
        return readHex(inputStream, 64, true);
    }

    public static String readHex64Upper(InputStream inputStream) throws IORuntimeException {
        return readHex(inputStream, 64, false);
    }

    public static String readHex8192Upper(InputStream inputStream) throws IOException, IORuntimeException {
        try {
            inputStream.available();
            return readHex(inputStream, Math.min(8192, inputStream.available()), false);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    @Deprecated
    public static <T extends Collection<String>> T readLines(InputStream inputStream, String str, T t2) throws IORuntimeException {
        return (T) readLines(inputStream, CharsetUtil.charset(str), t2);
    }

    public static <T> T readObj(InputStream inputStream) throws UtilException, IORuntimeException {
        return (T) readObj(inputStream, (Class) null);
    }

    public static String readUtf8(InputStream inputStream) throws IORuntimeException {
        return read(inputStream, CharsetUtil.CHARSET_UTF_8);
    }

    public static <T extends Collection<String>> T readUtf8Lines(InputStream inputStream, T t2) throws IORuntimeException {
        return (T) readLines(inputStream, CharsetUtil.CHARSET_UTF_8, t2);
    }

    public static InputStream toAvailableStream(InputStream inputStream) throws IOException {
        if (inputStream instanceof FileInputStream) {
            return inputStream;
        }
        PushbackInputStream pushbackStream = toPushbackStream(inputStream, 1);
        try {
            if (pushbackStream.available() <= 0) {
                pushbackStream.unread(pushbackStream.read());
            }
            return pushbackStream;
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static BufferedInputStream toBuffered(InputStream inputStream) throws IllegalArgumentException {
        Assert.notNull(inputStream, "InputStream must be not null!", new Object[0]);
        return inputStream instanceof BufferedInputStream ? (BufferedInputStream) inputStream : new BufferedInputStream(inputStream);
    }

    public static InputStream toMarkSupportStream(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        return !inputStream.markSupported() ? new BufferedInputStream(inputStream) : inputStream;
    }

    public static PushbackInputStream toPushbackStream(InputStream inputStream, int i2) {
        return inputStream instanceof PushbackInputStream ? (PushbackInputStream) inputStream : new PushbackInputStream(inputStream, i2);
    }

    public static String toStr(ByteArrayOutputStream byteArrayOutputStream, Charset charset) {
        try {
            return byteArrayOutputStream.toString(charset.name());
        } catch (UnsupportedEncodingException e2) {
            throw new IORuntimeException(e2);
        }
    }

    @Deprecated
    public static ByteArrayInputStream toStream(String str, String str2) {
        return toStream(str, CharsetUtil.charset(str2));
    }

    public static ByteArrayInputStream toUtf8Stream(String str) {
        return toStream(str, CharsetUtil.CHARSET_UTF_8);
    }

    public static void write(OutputStream outputStream, boolean z2, byte[] bArr) throws IOException, IORuntimeException {
        try {
            try {
                outputStream.write(bArr);
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        } finally {
            if (z2) {
                close((Closeable) outputStream);
            }
        }
    }

    public static void writeObj(OutputStream outputStream, boolean z2, Serializable serializable) throws IOException, IORuntimeException {
        writeObjects(outputStream, z2, serializable);
    }

    public static void writeObjects(OutputStream outputStream, boolean z2, Serializable... serializableArr) throws IOException, IORuntimeException {
        ObjectOutputStream objectOutputStream = null;
        try {
            try {
                objectOutputStream = outputStream instanceof ObjectOutputStream ? (ObjectOutputStream) outputStream : new ObjectOutputStream(outputStream);
                for (Serializable serializable : serializableArr) {
                    if (serializable != null) {
                        objectOutputStream.writeObject(serializable);
                    }
                }
                objectOutputStream.flush();
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        } finally {
            if (z2) {
                close((Closeable) objectOutputStream);
            }
        }
    }

    public static void writeUtf8(OutputStream outputStream, boolean z2, Object... objArr) throws IOException, IORuntimeException {
        write(outputStream, CharsetUtil.CHARSET_UTF_8, z2, objArr);
    }

    public static long copy(Reader reader, Writer writer, int i2) throws IORuntimeException {
        return copy(reader, writer, i2, (StreamProgress) null);
    }

    public static BufferedReader getReader(BOMInputStream bOMInputStream) {
        return getReader(bOMInputStream, bOMInputStream.getCharset());
    }

    public static OutputStreamWriter getWriter(OutputStream outputStream, Charset charset) {
        if (outputStream == null) {
            return null;
        }
        return charset == null ? new OutputStreamWriter(outputStream) : new OutputStreamWriter(outputStream, charset);
    }

    public static LineIter lineIter(InputStream inputStream, Charset charset) {
        return new LineIter(inputStream, charset);
    }

    public static byte[] readBytes(InputStream inputStream, boolean z2) throws IORuntimeException {
        return read(inputStream, z2).toByteArray();
    }

    public static <T extends Collection<String>> T readLines(InputStream inputStream, Charset charset, T t2) throws IORuntimeException {
        return (T) readLines(getReader(inputStream, charset), t2);
    }

    public static <T> T readObj(InputStream inputStream, Class<T> cls) throws UtilException, IORuntimeException {
        try {
            return (T) readObj(inputStream instanceof ValidateObjectInputStream ? (ValidateObjectInputStream) inputStream : new ValidateObjectInputStream(inputStream, new Class[0]), (Class) cls);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static void readUtf8Lines(InputStream inputStream, LineHandler lineHandler) throws IllegalArgumentException, IORuntimeException {
        readLines(inputStream, CharsetUtil.CHARSET_UTF_8, lineHandler);
    }

    public static ByteArrayInputStream toStream(String str, Charset charset) {
        if (str == null) {
            return null;
        }
        return toStream(CharSequenceUtil.bytes(str, charset));
    }

    public static long copy(Reader reader, Writer writer, int i2, StreamProgress streamProgress) throws IORuntimeException {
        return copy(reader, writer, i2, -1L, streamProgress);
    }

    public static BufferedReader getReader(InputStream inputStream, Charset charset) {
        InputStreamReader inputStreamReader;
        if (inputStream == null) {
            return null;
        }
        if (charset == null) {
            inputStreamReader = new InputStreamReader(inputStream);
        } else {
            inputStreamReader = new InputStreamReader(inputStream, charset);
        }
        return new BufferedReader(inputStreamReader);
    }

    public static String read(InputStream inputStream, Charset charset) throws IORuntimeException {
        return StrUtil.str(readBytes(inputStream), charset);
    }

    public static byte[] readBytes(InputStream inputStream, int i2) throws IORuntimeException {
        if (inputStream == null) {
            return null;
        }
        if (i2 <= 0) {
            return new byte[0];
        }
        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream(i2);
        copy(inputStream, fastByteArrayOutputStream, 8192, i2, (StreamProgress) null);
        return fastByteArrayOutputStream.toByteArray();
    }

    public static <T extends Collection<String>> T readLines(Reader reader, final T t2) throws IllegalArgumentException, IORuntimeException {
        t2.getClass();
        readLines(reader, new LineHandler() { // from class: cn.hutool.core.io.c
            @Override // cn.hutool.core.io.LineHandler
            public final void handle(String str) {
                t2.add(str);
            }
        });
        return t2;
    }

    public static BufferedInputStream toBuffered(InputStream inputStream, int i2) throws IllegalArgumentException {
        Assert.notNull(inputStream, "InputStream must be not null!", new Object[0]);
        return inputStream instanceof BufferedInputStream ? (BufferedInputStream) inputStream : new BufferedInputStream(inputStream, i2);
    }

    public static FileInputStream toStream(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static long copy(Reader reader, Writer writer, int i2, long j2, StreamProgress streamProgress) throws IORuntimeException {
        return new ReaderWriterCopier(i2, j2, streamProgress).copy(reader, writer);
    }

    public static FastByteArrayOutputStream read(InputStream inputStream) throws IORuntimeException {
        return read(inputStream, true);
    }

    public static void readLines(InputStream inputStream, Charset charset, LineHandler lineHandler) throws IllegalArgumentException, IORuntimeException {
        readLines(getReader(inputStream, charset), lineHandler);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T readObj(ValidateObjectInputStream validateObjectInputStream, Class<T> cls) throws UtilException, IORuntimeException {
        if (validateObjectInputStream != 0) {
            if (cls != null) {
                validateObjectInputStream.accept(cls);
            }
            try {
                return (T) validateObjectInputStream.readObject();
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            } catch (ClassNotFoundException e3) {
                throw new UtilException(e3);
            }
        }
        throw new IllegalArgumentException("The InputStream must not be null");
    }

    public static long copy(InputStream inputStream, OutputStream outputStream) throws IORuntimeException {
        return copy(inputStream, outputStream, 8192);
    }

    public static FastByteArrayOutputStream read(InputStream inputStream, boolean z2) throws IOException, IORuntimeException {
        FastByteArrayOutputStream fastByteArrayOutputStream;
        if (inputStream instanceof FileInputStream) {
            try {
                fastByteArrayOutputStream = new FastByteArrayOutputStream(inputStream.available());
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        } else {
            fastByteArrayOutputStream = new FastByteArrayOutputStream();
        }
        try {
            copy(inputStream, fastByteArrayOutputStream);
            return fastByteArrayOutputStream;
        } finally {
            if (z2) {
                close((Closeable) inputStream);
            }
        }
    }

    public static void readLines(Reader reader, LineHandler lineHandler) throws IllegalArgumentException, IORuntimeException {
        Assert.notNull(reader);
        Assert.notNull(lineHandler);
        Iterator it = lineIter(reader).iterator();
        while (it.hasNext()) {
            lineHandler.handle((String) it.next());
        }
    }

    public static BufferedOutputStream toBuffered(OutputStream outputStream) throws IllegalArgumentException {
        Assert.notNull(outputStream, "OutputStream must be not null!", new Object[0]);
        return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream) outputStream : new BufferedOutputStream(outputStream);
    }

    public static ByteArrayInputStream toStream(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return new ByteArrayInputStream(bArr);
    }

    public static long copy(InputStream inputStream, OutputStream outputStream, int i2) throws IORuntimeException {
        return copy(inputStream, outputStream, i2, (StreamProgress) null);
    }

    public static BufferedReader getReader(Reader reader) {
        if (reader == null) {
            return null;
        }
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    public static ByteArrayInputStream toStream(ByteArrayOutputStream byteArrayOutputStream) {
        if (byteArrayOutputStream == null) {
            return null;
        }
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    @Deprecated
    public static void write(OutputStream outputStream, String str, boolean z2, Object... objArr) throws IOException, IORuntimeException {
        write(outputStream, CharsetUtil.charset(str), z2, objArr);
    }

    public static long copy(InputStream inputStream, OutputStream outputStream, int i2, StreamProgress streamProgress) throws IORuntimeException {
        return copy(inputStream, outputStream, i2, -1L, streamProgress);
    }

    public static BufferedOutputStream toBuffered(OutputStream outputStream, int i2) throws IllegalArgumentException {
        Assert.notNull(outputStream, "OutputStream must be not null!", new Object[0]);
        return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream) outputStream : new BufferedOutputStream(outputStream, i2);
    }

    public static void write(OutputStream outputStream, Charset charset, boolean z2, Object... objArr) throws IOException, IORuntimeException {
        OutputStreamWriter writer = null;
        try {
            try {
                writer = getWriter(outputStream, charset);
                for (Object obj : objArr) {
                    if (obj != null) {
                        writer.write(Convert.toStr(obj, ""));
                    }
                }
                writer.flush();
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        } finally {
            if (z2) {
                close((Closeable) writer);
            }
        }
    }

    public static long copy(InputStream inputStream, OutputStream outputStream, int i2, long j2, StreamProgress streamProgress) throws IORuntimeException {
        return new StreamCopier(i2, j2, streamProgress).copy(inputStream, outputStream);
    }

    public static long copy(FileInputStream fileInputStream, FileOutputStream fileOutputStream) throws Throwable {
        FileChannel channel;
        Assert.notNull(fileInputStream, "FileInputStream is null!", new Object[0]);
        Assert.notNull(fileOutputStream, "FileOutputStream is null!", new Object[0]);
        FileChannel channel2 = null;
        try {
            channel = fileInputStream.getChannel();
        } catch (Throwable th) {
            th = th;
            channel = null;
        }
        try {
            channel2 = fileOutputStream.getChannel();
            long jCopy = NioUtil.copy(channel, channel2);
            close((Closeable) channel2);
            close((Closeable) channel);
            return jCopy;
        } catch (Throwable th2) {
            th = th2;
            close((Closeable) channel2);
            close((Closeable) channel);
            throw th;
        }
    }

    public static BufferedReader toBuffered(Reader reader) throws IllegalArgumentException {
        Assert.notNull(reader, "Reader must be not null!", new Object[0]);
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    public static boolean contentEquals(Reader reader, Reader reader2) throws IOException, IORuntimeException {
        BufferedReader reader3 = getReader(reader);
        BufferedReader reader4 = getReader(reader2);
        try {
            for (int i2 = reader3.read(); -1 != i2; i2 = reader3.read()) {
                if (i2 != reader4.read()) {
                    return false;
                }
            }
            return reader4.read() == -1;
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static BufferedReader toBuffered(Reader reader, int i2) throws IllegalArgumentException {
        Assert.notNull(reader, "Reader must be not null!", new Object[0]);
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader, i2);
    }

    public static String read(Reader reader) throws IORuntimeException {
        return read(reader, true);
    }

    public static String read(Reader reader, boolean z2) throws IOException, IORuntimeException {
        StringBuilder sbBuilder = StrUtil.builder();
        CharBuffer charBufferAllocate = CharBuffer.allocate(8192);
        while (-1 != reader.read(charBufferAllocate)) {
            try {
                try {
                    sbBuilder.append(charBufferAllocate.flip());
                } catch (IOException e2) {
                    throw new IORuntimeException(e2);
                }
            } finally {
                if (z2) {
                    close((Closeable) reader);
                }
            }
        }
        return sbBuilder.toString();
    }

    public static BufferedWriter toBuffered(Writer writer) throws IllegalArgumentException {
        Assert.notNull(writer, "Writer must be not null!", new Object[0]);
        return writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer);
    }

    public static BufferedWriter toBuffered(Writer writer, int i2) throws IllegalArgumentException {
        Assert.notNull(writer, "Writer must be not null!", new Object[0]);
        return writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer, i2);
    }
}
