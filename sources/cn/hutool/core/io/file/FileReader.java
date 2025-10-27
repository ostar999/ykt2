package cn.hutool.core.io.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.LineHandler;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes.dex */
public class FileReader extends FileWrapper {
    private static final long serialVersionUID = 1;

    public interface ReaderHandler<T> {
        T handle(BufferedReader bufferedReader) throws IOException;
    }

    public FileReader(File file, Charset charset) throws IORuntimeException {
        super(file, charset);
        checkFile();
    }

    private void checkFile() throws IORuntimeException {
        if (!this.file.exists()) {
            throw new IORuntimeException("File not exist: " + this.file);
        }
        if (this.file.isFile()) {
            return;
        }
        throw new IORuntimeException("Not a file:" + this.file);
    }

    public static FileReader create(File file, Charset charset) {
        return new FileReader(file, charset);
    }

    public BufferedInputStream getInputStream() throws IORuntimeException {
        try {
            return new BufferedInputStream(new FileInputStream(this.file));
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public BufferedReader getReader() throws IORuntimeException {
        return IoUtil.getReader(getInputStream(), this.charset);
    }

    public <T> T read(ReaderHandler<T> readerHandler) throws IOException, IORuntimeException {
        BufferedReader reader = null;
        try {
            try {
                reader = FileUtil.getReader(this.file, this.charset);
                return readerHandler.handle(reader);
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        } finally {
            IoUtil.close((Closeable) reader);
        }
    }

    public byte[] readBytes() throws Throwable {
        long length = this.file.length();
        if (length >= 2147483647L) {
            throw new IORuntimeException("File is larger then max array size");
        }
        byte[] bArr = new byte[(int) length];
        FileInputStream fileInputStream = null;
        try {
            try {
                FileInputStream fileInputStream2 = new FileInputStream(this.file);
                try {
                    int i2 = fileInputStream2.read(bArr);
                    if (i2 < length) {
                        throw new IOException(CharSequenceUtil.format("File length is [{}] but read [{}]!", Long.valueOf(length), Integer.valueOf(i2)));
                    }
                    IoUtil.close((Closeable) fileInputStream2);
                    return bArr;
                } catch (Exception e2) {
                    e = e2;
                    fileInputStream = fileInputStream2;
                    throw new IORuntimeException(e);
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = fileInputStream2;
                    IoUtil.close((Closeable) fileInputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    public <T extends Collection<String>> T readLines(T t2) throws IOException, IORuntimeException {
        BufferedReader reader = null;
        try {
            try {
                reader = FileUtil.getReader(this.file, this.charset);
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        return t2;
                    }
                    t2.add(line);
                }
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        } finally {
            IoUtil.close((Closeable) reader);
        }
    }

    public String readString() throws IORuntimeException {
        return new String(readBytes(), this.charset);
    }

    public long writeToStream(OutputStream outputStream) throws IORuntimeException {
        return writeToStream(outputStream, false);
    }

    public static FileReader create(File file) {
        return new FileReader(file);
    }

    public long writeToStream(OutputStream outputStream, boolean z2) throws IOException, IORuntimeException {
        try {
            try {
                FileInputStream fileInputStream = new FileInputStream(this.file);
                try {
                    long jCopy = IoUtil.copy(fileInputStream, outputStream);
                    fileInputStream.close();
                    return jCopy;
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        try {
                            fileInputStream.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                        throw th2;
                    }
                }
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        } finally {
            if (z2) {
                IoUtil.close((Closeable) outputStream);
            }
        }
    }

    public FileReader(File file, String str) {
        this(file, CharsetUtil.charset(str));
    }

    public FileReader(String str, Charset charset) {
        this(FileUtil.file(str), charset);
    }

    public FileReader(String str, String str2) {
        this(FileUtil.file(str), CharsetUtil.charset(str2));
    }

    public FileReader(File file) {
        this(file, FileWrapper.DEFAULT_CHARSET);
    }

    public FileReader(String str) {
        this(str, FileWrapper.DEFAULT_CHARSET);
    }

    public void readLines(LineHandler lineHandler) throws IORuntimeException {
        BufferedReader reader = null;
        try {
            reader = FileUtil.getReader(this.file, this.charset);
            IoUtil.readLines(reader, lineHandler);
        } finally {
            IoUtil.close((Closeable) reader);
        }
    }

    public List<String> readLines() throws IORuntimeException {
        return (List) readLines((FileReader) new ArrayList());
    }
}
