package com.aliyun.vod.common.utils;

import com.aliyun.vod.common.stream.ByteArrayOutputStream;
import com.aliyun.vod.common.stream.StringBuilderWriter;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes2.dex */
public class IOUtils {
    public static final int CONTINUE_LOADING_PERCENTAGE = 75;
    public static final int DEFAULT_BUFFER_SIZE = 32768;
    public static final char DIR_SEPARATOR = File.separatorChar;
    public static final char DIR_SEPARATOR_UNIX = '/';
    public static final char DIR_SEPARATOR_WINDOWS = '\\';
    private static final int EOF = -1;
    public static final String LINE_SEPARATOR;
    public static final String LINE_SEPARATOR_UNIX = "\n";
    public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
    private static final int SKIP_BUFFER_SIZE = 2048;
    private static byte[] SKIP_BYTE_BUFFER;
    private static char[] SKIP_CHAR_BUFFER;

    public interface CopyListener {
        boolean onBytesCopied(int i2, int i3);
    }

    static {
        StringBuilderWriter stringBuilderWriter = new StringBuilderWriter(4);
        PrintWriter printWriter = new PrintWriter(stringBuilderWriter);
        printWriter.println();
        LINE_SEPARATOR = stringBuilderWriter.toString();
        printWriter.close();
    }

    public static void close(URLConnection uRLConnection) {
        if (uRLConnection instanceof HttpURLConnection) {
            ((HttpURLConnection) uRLConnection).disconnect();
        }
    }

    public static void closeQuietly(Reader reader) throws IOException {
        closeQuietly((Closeable) reader);
    }

    public static void closeSilently(Closeable closeable) throws IOException {
        try {
            closeable.close();
        } catch (Exception unused) {
        }
    }

    public static boolean contentEquals(InputStream inputStream, InputStream inputStream2) throws IOException {
        if (!(inputStream instanceof BufferedInputStream)) {
            inputStream = new BufferedInputStream(inputStream);
        }
        if (!(inputStream2 instanceof BufferedInputStream)) {
            inputStream2 = new BufferedInputStream(inputStream2);
        }
        for (int i2 = inputStream.read(); -1 != i2; i2 = inputStream.read()) {
            if (i2 != inputStream2.read()) {
                return false;
            }
        }
        return inputStream2.read() == -1;
    }

    public static boolean contentEqualsIgnoreEOL(Reader reader, Reader reader2) throws IOException {
        BufferedReader bufferedReader = toBufferedReader(reader);
        BufferedReader bufferedReader2 = toBufferedReader(reader2);
        String line = bufferedReader.readLine();
        String line2 = bufferedReader2.readLine();
        while (line != null && line2 != null && line.equals(line2)) {
            line = bufferedReader.readLine();
            line2 = bufferedReader2.readLine();
        }
        return line == null ? line2 == null : line.equals(line2);
    }

    public static int copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        long jCopyLarge = copyLarge(inputStream, outputStream);
        if (jCopyLarge > 2147483647L) {
            return -1;
        }
        return (int) jCopyLarge;
    }

    public static long copyLarge(InputStream inputStream, OutputStream outputStream) throws IOException {
        return copyLarge(inputStream, outputStream, new byte[32768]);
    }

    public static boolean copyStream(InputStream inputStream, RandomAccessFile randomAccessFile, CopyListener copyListener, int i2) throws IOException {
        return copyStream(inputStream, randomAccessFile, copyListener, i2, 32768);
    }

    public static int read(Reader reader, char[] cArr, int i2, int i3) throws IOException {
        if (i3 < 0) {
            throw new IllegalArgumentException("Length must not be negative: " + i3);
        }
        int i4 = i3;
        while (i4 > 0) {
            int i5 = reader.read(cArr, (i3 - i4) + i2, i4);
            if (-1 == i5) {
                break;
            }
            i4 -= i5;
        }
        return i3 - i4;
    }

    public static void readFully(Reader reader, char[] cArr, int i2, int i3) throws IOException {
        int i4 = read(reader, cArr, i2, i3);
        if (i4 == i3) {
            return;
        }
        throw new EOFException("Length to read: " + i3 + " actual: " + i4);
    }

    public static List<String> readLines(InputStream inputStream) throws IOException {
        return readLines(inputStream, Charset.defaultCharset());
    }

    private static boolean shouldStopLoading(CopyListener copyListener, int i2, int i3) {
        return (copyListener == null || copyListener.onBytesCopied(i2, i3) || (i2 * 100) / i3 >= 75) ? false : true;
    }

    public static long skip(InputStream inputStream, long j2) throws IOException {
        if (j2 < 0) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + j2);
        }
        if (SKIP_BYTE_BUFFER == null) {
            SKIP_BYTE_BUFFER = new byte[2048];
        }
        long j3 = j2;
        while (j3 > 0) {
            long j4 = inputStream.read(SKIP_BYTE_BUFFER, 0, (int) Math.min(j3, 2048L));
            if (j4 < 0) {
                break;
            }
            j3 -= j4;
        }
        return j2 - j3;
    }

    public static void skipFully(InputStream inputStream, long j2) throws IOException {
        if (j2 < 0) {
            throw new IllegalArgumentException("Bytes to skip must not be negative: " + j2);
        }
        long jSkip = skip(inputStream, j2);
        if (jSkip == j2) {
            return;
        }
        throw new EOFException("Bytes to skip: " + j2 + " actual: " + jSkip);
    }

    public static InputStream toBufferedInputStream(InputStream inputStream) throws IOException {
        return ByteArrayOutputStream.toBufferedInputStream(inputStream);
    }

    public static BufferedReader toBufferedReader(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copy(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static char[] toCharArray(InputStream inputStream) throws IOException {
        return toCharArray(inputStream, Charset.defaultCharset());
    }

    public static InputStream toInputStream(CharSequence charSequence) {
        return toInputStream(charSequence, Charset.defaultCharset());
    }

    public static String toString(InputStream inputStream) throws IOException {
        return toString(inputStream, Charset.defaultCharset());
    }

    public static void write(byte[] bArr, OutputStream outputStream) throws IOException {
        if (bArr != null) {
            outputStream.write(bArr);
        }
    }

    public static void writeLines(Collection<?> collection, String str, OutputStream outputStream) throws IOException {
        writeLines(collection, str, outputStream, Charset.defaultCharset());
    }

    public static void closeQuietly(Writer writer) throws IOException {
        closeQuietly((Closeable) writer);
    }

    public static void copy(InputStream inputStream, Writer writer) throws IOException {
        copy(inputStream, writer, Charset.defaultCharset());
    }

    public static long copyLarge(InputStream inputStream, OutputStream outputStream, byte[] bArr) throws IOException {
        long j2 = 0;
        while (true) {
            int i2 = inputStream.read(bArr);
            if (-1 == i2) {
                return j2;
            }
            outputStream.write(bArr, 0, i2);
            j2 += i2;
        }
    }

    public static boolean copyStream(InputStream inputStream, RandomAccessFile randomAccessFile, CopyListener copyListener, int i2, int i3) throws IOException {
        int iAvailable = inputStream.available();
        byte[] bArr = new byte[i3];
        if (shouldStopLoading(copyListener, i2, iAvailable)) {
            return false;
        }
        do {
            int i4 = inputStream.read(bArr, 0, i3);
            if (i4 == -1) {
                return true;
            }
            randomAccessFile.write(bArr, 0, i4);
            i2 += i4;
        } while (!shouldStopLoading(copyListener, i2, iAvailable));
        return false;
    }

    public static List<String> readLines(InputStream inputStream, Charset charset) throws IOException {
        return readLines(new InputStreamReader(inputStream, Charsets.toCharset(charset)));
    }

    public static char[] toCharArray(InputStream inputStream, Charset charset) throws IOException {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        copy(inputStream, charArrayWriter, charset);
        return charArrayWriter.toCharArray();
    }

    public static InputStream toInputStream(CharSequence charSequence, Charset charset) {
        return toInputStream(charSequence.toString(), charset);
    }

    public static String toString(InputStream inputStream, Charset charset) throws IOException {
        StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        copy(inputStream, stringBuilderWriter, charset);
        return stringBuilderWriter.toString();
    }

    public static void write(byte[] bArr, Writer writer) throws IOException {
        write(bArr, writer, Charset.defaultCharset());
    }

    public static void writeLines(Collection<?> collection, String str, OutputStream outputStream, Charset charset) throws IOException {
        if (collection == null) {
            return;
        }
        if (str == null) {
            str = LINE_SEPARATOR;
        }
        Charset charset2 = Charsets.toCharset(charset);
        for (Object obj : collection) {
            if (obj != null) {
                outputStream.write(StringCodingUtils.getBytes(obj.toString(), charset2));
            }
            outputStream.write(StringCodingUtils.getBytes(str, charset2));
        }
    }

    public static void closeQuietly(InputStream inputStream) throws IOException {
        closeQuietly((Closeable) inputStream);
    }

    public static void copy(InputStream inputStream, Writer writer, Charset charset) throws IOException {
        copy(new InputStreamReader(inputStream, Charsets.toCharset(charset)), writer);
    }

    public static int read(Reader reader, char[] cArr) throws IOException {
        return read(reader, cArr, 0, cArr.length);
    }

    public static void readFully(Reader reader, char[] cArr) throws IOException {
        readFully(reader, cArr, 0, cArr.length);
    }

    public static InputStream toInputStream(CharSequence charSequence, String str) throws IOException {
        return toInputStream(charSequence, Charsets.toCharset(str));
    }

    public static void write(byte[] bArr, Writer writer, Charset charset) throws IOException {
        if (bArr != null) {
            writer.write(new String(bArr, Charsets.toCharset(charset)));
        }
    }

    public static void closeQuietly(OutputStream outputStream) throws IOException {
        closeQuietly((Closeable) outputStream);
    }

    public static long copyLarge(InputStream inputStream, OutputStream outputStream, long j2, long j3) throws IOException {
        return copyLarge(inputStream, outputStream, j2, j3, new byte[32768]);
    }

    public static int read(InputStream inputStream, byte[] bArr, int i2, int i3) throws IOException {
        if (i3 < 0) {
            throw new IllegalArgumentException("Length must not be negative: " + i3);
        }
        int i4 = i3;
        while (i4 > 0) {
            int i5 = inputStream.read(bArr, (i3 - i4) + i2, i4);
            if (-1 == i5) {
                break;
            }
            i4 -= i5;
        }
        return i3 - i4;
    }

    public static void readFully(InputStream inputStream, byte[] bArr, int i2, int i3) throws IOException {
        int i4 = read(inputStream, bArr, i2, i3);
        if (i4 == i3) {
            return;
        }
        throw new EOFException("Length to read: " + i3 + " actual: " + i4);
    }

    public static List<String> readLines(InputStream inputStream, String str) throws IOException {
        return readLines(inputStream, Charsets.toCharset(str));
    }

    public static void skipFully(Reader reader, long j2) throws IOException {
        long jSkip = skip(reader, j2);
        if (jSkip == j2) {
            return;
        }
        throw new EOFException("Chars to skip: " + j2 + " actual: " + jSkip);
    }

    public static byte[] toByteArray(InputStream inputStream, long j2) throws IOException {
        if (j2 <= 2147483647L) {
            return toByteArray(inputStream, (int) j2);
        }
        throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + j2);
    }

    public static InputStream toInputStream(String str) {
        return toInputStream(str, Charset.defaultCharset());
    }

    public static void write(byte[] bArr, Writer writer, String str) throws IOException {
        write(bArr, writer, Charsets.toCharset(str));
    }

    public static void closeQuietly(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void copy(InputStream inputStream, Writer writer, String str) throws IOException {
        copy(inputStream, writer, Charsets.toCharset(str));
    }

    public static long copyLarge(InputStream inputStream, OutputStream outputStream, long j2, long j3, byte[] bArr) throws IOException {
        long j4 = 0;
        if (j2 > 0) {
            skipFully(inputStream, j2);
        }
        if (j3 == 0) {
            return 0L;
        }
        int length = bArr.length;
        int iMin = (j3 <= 0 || j3 >= ((long) length)) ? length : (int) j3;
        while (iMin > 0) {
            int i2 = inputStream.read(bArr, 0, iMin);
            if (-1 == i2) {
                break;
            }
            outputStream.write(bArr, 0, i2);
            j4 += i2;
            if (j3 > 0) {
                iMin = (int) Math.min(j3 - j4, length);
            }
        }
        return j4;
    }

    public static List<String> readLines(Reader reader) throws IOException {
        BufferedReader bufferedReader = toBufferedReader(reader);
        ArrayList arrayList = new ArrayList();
        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
            arrayList.add(line);
        }
        return arrayList;
    }

    public static long skip(Reader reader, long j2) throws IOException {
        if (j2 >= 0) {
            if (SKIP_CHAR_BUFFER == null) {
                SKIP_CHAR_BUFFER = new char[2048];
            }
            long j3 = j2;
            while (j3 > 0) {
                long j4 = reader.read(SKIP_CHAR_BUFFER, 0, (int) Math.min(j3, 2048L));
                if (j4 < 0) {
                    break;
                }
                j3 -= j4;
            }
            return j2 - j3;
        }
        throw new IllegalArgumentException("Skip count must be non-negative, actual: " + j2);
    }

    public static char[] toCharArray(InputStream inputStream, String str) throws IOException {
        return toCharArray(inputStream, Charsets.toCharset(str));
    }

    public static InputStream toInputStream(String str, Charset charset) {
        return new ByteArrayInputStream(StringCodingUtils.getBytes(str, Charsets.toCharset(charset)));
    }

    public static String toString(InputStream inputStream, String str) throws IOException {
        return toString(inputStream, Charsets.toCharset(str));
    }

    public static void write(char[] cArr, Writer writer) throws IOException {
        if (cArr != null) {
            writer.write(cArr);
        }
    }

    public static void closeQuietly(Socket socket) throws IOException {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException unused) {
            }
        }
    }

    public static int copy(Reader reader, Writer writer) throws IOException {
        long jCopyLarge = copyLarge(reader, writer);
        if (jCopyLarge > 2147483647L) {
            return -1;
        }
        return (int) jCopyLarge;
    }

    public static int read(InputStream inputStream, byte[] bArr) throws IOException {
        return read(inputStream, bArr, 0, bArr.length);
    }

    public static void readFully(InputStream inputStream, byte[] bArr) throws IOException {
        readFully(inputStream, bArr, 0, bArr.length);
    }

    public static byte[] toByteArray(InputStream inputStream, int i2) throws IOException {
        if (i2 < 0) {
            throw new IllegalArgumentException("Size must be equal or greater than zero: " + i2);
        }
        int i3 = 0;
        if (i2 == 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[i2];
        while (i3 < i2) {
            int i4 = inputStream.read(bArr, i3, i2 - i3);
            if (i4 == -1) {
                break;
            }
            i3 += i4;
        }
        if (i3 == i2) {
            return bArr;
        }
        throw new IOException("Unexpected readed size. current: " + i3 + ", excepted: " + i2);
    }

    public static char[] toCharArray(Reader reader) throws IOException {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        copy(reader, charArrayWriter);
        return charArrayWriter.toCharArray();
    }

    public static InputStream toInputStream(String str, String str2) throws IOException {
        return new ByteArrayInputStream(StringCodingUtils.getBytes(str, Charsets.toCharset(str2)));
    }

    public static String toString(Reader reader) throws IOException {
        StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        copy(reader, stringBuilderWriter);
        return stringBuilderWriter.toString();
    }

    public static void write(char[] cArr, OutputStream outputStream) throws IOException {
        write(cArr, outputStream, Charset.defaultCharset());
    }

    public static void closeQuietly(Selector selector) throws IOException {
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void copy(Reader reader, OutputStream outputStream) throws IOException {
        copy(reader, outputStream, Charset.defaultCharset());
    }

    public static void write(char[] cArr, OutputStream outputStream, Charset charset) throws IOException {
        if (cArr != null) {
            outputStream.write(StringCodingUtils.getBytes(new String(cArr), Charsets.toCharset(charset)));
        }
    }

    public static void writeLines(Collection<?> collection, String str, OutputStream outputStream, String str2) throws IOException {
        writeLines(collection, str, outputStream, Charsets.toCharset(str2));
    }

    public static void closeQuietly(ServerSocket serverSocket) throws IOException {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void copy(Reader reader, OutputStream outputStream, Charset charset) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, Charsets.toCharset(charset));
        copy(reader, outputStreamWriter);
        outputStreamWriter.flush();
    }

    public static void write(char[] cArr, OutputStream outputStream, String str) throws IOException {
        write(cArr, outputStream, Charsets.toCharset(str));
    }

    public static void writeLines(Collection<?> collection, String str, Writer writer) throws IOException {
        if (collection == null) {
            return;
        }
        if (str == null) {
            str = LINE_SEPARATOR;
        }
        for (Object obj : collection) {
            if (obj != null) {
                writer.write(obj.toString());
            }
            writer.write(str);
        }
    }

    public static boolean contentEquals(Reader reader, Reader reader2) throws IOException {
        BufferedReader bufferedReader = toBufferedReader(reader);
        BufferedReader bufferedReader2 = toBufferedReader(reader2);
        for (int i2 = bufferedReader.read(); -1 != i2; i2 = bufferedReader.read()) {
            if (i2 != bufferedReader2.read()) {
                return false;
            }
        }
        return bufferedReader2.read() == -1;
    }

    public static String toString(URI uri) throws IOException {
        return toString(uri, Charset.defaultCharset());
    }

    public static void write(CharSequence charSequence, Writer writer) throws IOException {
        if (charSequence != null) {
            write(charSequence.toString(), writer);
        }
    }

    public static long copyLarge(Reader reader, Writer writer) throws IOException {
        return copyLarge(reader, writer, new char[32768]);
    }

    public static byte[] toByteArray(Reader reader) throws IOException {
        return toByteArray(reader, Charset.defaultCharset());
    }

    public static String toString(URI uri, Charset charset) throws IOException {
        return toString(uri.toURL(), Charsets.toCharset(charset));
    }

    public static void write(CharSequence charSequence, OutputStream outputStream) throws IOException {
        write(charSequence, outputStream, Charset.defaultCharset());
    }

    public static void copy(Reader reader, OutputStream outputStream, String str) throws IOException {
        copy(reader, outputStream, Charsets.toCharset(str));
    }

    public static long copyLarge(Reader reader, Writer writer, char[] cArr) throws IOException {
        long j2 = 0;
        while (true) {
            int i2 = reader.read(cArr);
            if (-1 == i2) {
                return j2;
            }
            writer.write(cArr, 0, i2);
            j2 += i2;
        }
    }

    public static byte[] toByteArray(Reader reader, Charset charset) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copy(reader, byteArrayOutputStream, charset);
        return byteArrayOutputStream.toByteArray();
    }

    public static String toString(URI uri, String str) throws IOException {
        return toString(uri, Charsets.toCharset(str));
    }

    public static void write(CharSequence charSequence, OutputStream outputStream, Charset charset) throws IOException {
        if (charSequence != null) {
            write(charSequence.toString(), outputStream, charset);
        }
    }

    public static String toString(URL url) throws IOException {
        return toString(url, Charset.defaultCharset());
    }

    public static void write(CharSequence charSequence, OutputStream outputStream, String str) throws IOException {
        write(charSequence, outputStream, Charsets.toCharset(str));
    }

    public static long copyLarge(Reader reader, Writer writer, long j2, long j3) throws IOException {
        return copyLarge(reader, writer, j2, j3, new char[32768]);
    }

    public static String toString(URL url, Charset charset) throws IOException {
        InputStream inputStreamOpenStream = url.openStream();
        try {
            return toString(inputStreamOpenStream, charset);
        } finally {
            inputStreamOpenStream.close();
        }
    }

    public static void write(String str, Writer writer) throws IOException {
        if (str != null) {
            writer.write(str);
        }
    }

    public static long copyLarge(Reader reader, Writer writer, long j2, long j3, char[] cArr) throws IOException {
        long j4 = 0;
        if (j2 > 0) {
            skipFully(reader, j2);
        }
        if (j3 == 0) {
            return 0L;
        }
        int length = cArr.length;
        if (j3 > 0 && j3 < cArr.length) {
            length = (int) j3;
        }
        while (length > 0) {
            int i2 = reader.read(cArr, 0, length);
            if (-1 == i2) {
                break;
            }
            writer.write(cArr, 0, i2);
            j4 += i2;
            if (j3 > 0) {
                length = (int) Math.min(j3 - j4, cArr.length);
            }
        }
        return j4;
    }

    public static byte[] toByteArray(Reader reader, String str) throws IOException {
        return toByteArray(reader, Charsets.toCharset(str));
    }

    public static void write(String str, OutputStream outputStream) throws IOException {
        write(str, outputStream, Charset.defaultCharset());
    }

    @Deprecated
    public static byte[] toByteArray(String str) throws IOException {
        return str.getBytes();
    }

    public static void write(String str, OutputStream outputStream, Charset charset) throws IOException {
        if (str != null) {
            outputStream.write(StringCodingUtils.getBytes(str, Charsets.toCharset(charset)));
        }
    }

    public static byte[] toByteArray(URI uri) throws IOException {
        return toByteArray(uri.toURL());
    }

    public static String toString(URL url, String str) throws IOException {
        return toString(url, Charsets.toCharset(str));
    }

    public static void write(String str, OutputStream outputStream, String str2) throws IOException {
        write(str, outputStream, Charsets.toCharset(str2));
    }

    public static byte[] toByteArray(URL url) throws IOException {
        URLConnection uRLConnectionOpenConnection = url.openConnection();
        try {
            return toByteArray(uRLConnectionOpenConnection);
        } finally {
            close(uRLConnectionOpenConnection);
        }
    }

    @Deprecated
    public static String toString(byte[] bArr) throws IOException {
        return new String(bArr);
    }

    @Deprecated
    public static void write(StringBuffer stringBuffer, Writer writer) throws IOException {
        if (stringBuffer != null) {
            writer.write(stringBuffer.toString());
        }
    }

    public static String toString(byte[] bArr, String str) throws IOException {
        return new String(bArr, str);
    }

    @Deprecated
    public static void write(StringBuffer stringBuffer, OutputStream outputStream) throws IOException {
        write(stringBuffer, outputStream, (String) null);
    }

    @Deprecated
    public static void write(StringBuffer stringBuffer, OutputStream outputStream, String str) throws IOException {
        if (stringBuffer != null) {
            outputStream.write(StringCodingUtils.getBytes(stringBuffer.toString(), Charsets.toCharset(str)));
        }
    }

    public static byte[] toByteArray(URLConnection uRLConnection) throws IOException {
        InputStream inputStream = uRLConnection.getInputStream();
        try {
            return toByteArray(inputStream);
        } finally {
            inputStream.close();
        }
    }
}
