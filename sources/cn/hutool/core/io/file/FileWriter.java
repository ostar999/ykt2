package cn.hutool.core.io.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.Map;

/* loaded from: classes.dex */
public class FileWriter extends FileWrapper {
    private static final long serialVersionUID = 1;

    public FileWriter(File file, Charset charset) throws IllegalArgumentException, IORuntimeException {
        super(file, charset);
        checkFile();
    }

    private void checkFile() throws IllegalArgumentException, IORuntimeException {
        Assert.notNull(this.file, "File to write content is null !", new Object[0]);
        if (this.file.exists() && !this.file.isFile()) {
            throw new IORuntimeException("File [{}] is not a file !", this.file.getAbsoluteFile());
        }
    }

    public static FileWriter create(File file, Charset charset) {
        return new FileWriter(file, charset);
    }

    private void printNewLine(PrintWriter printWriter, LineSeparator lineSeparator) {
        if (lineSeparator == null) {
            printWriter.println();
        } else {
            printWriter.print(lineSeparator.getValue());
        }
    }

    public File append(String str) throws IORuntimeException {
        return write(str, true);
    }

    public <T> File appendLines(Iterable<T> iterable) throws IORuntimeException {
        return writeLines(iterable, true);
    }

    public BufferedOutputStream getOutputStream() throws IORuntimeException {
        try {
            return new BufferedOutputStream(Files.newOutputStream(FileUtil.touch(this.file).toPath(), new OpenOption[0]));
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public PrintWriter getPrintWriter(boolean z2) throws IORuntimeException {
        return new PrintWriter(getWriter(z2));
    }

    public BufferedWriter getWriter(boolean z2) throws IORuntimeException {
        try {
            return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileUtil.touch(this.file), z2), this.charset));
        } catch (Exception e2) {
            throw new IORuntimeException(e2);
        }
    }

    public File write(String str, boolean z2) throws IOException, IORuntimeException {
        BufferedWriter writer = null;
        try {
            try {
                writer = getWriter(z2);
                writer.write(str);
                writer.flush();
                IoUtil.close((Closeable) writer);
                return this.file;
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        } catch (Throwable th) {
            IoUtil.close((Closeable) writer);
            throw th;
        }
    }

    public File writeFromStream(InputStream inputStream) throws IORuntimeException {
        return writeFromStream(inputStream, true);
    }

    public <T> File writeLines(Iterable<T> iterable) throws IORuntimeException {
        return writeLines(iterable, false);
    }

    public File writeMap(Map<?, ?> map, String str, boolean z2) throws IORuntimeException {
        return writeMap(map, null, str, z2);
    }

    public static FileWriter create(File file) {
        return new FileWriter(file);
    }

    public File append(byte[] bArr, int i2, int i3) throws IORuntimeException {
        return write(bArr, i2, i3, true);
    }

    public File writeFromStream(InputStream inputStream, boolean z2) throws IOException, IORuntimeException {
        OutputStream outputStreamNewOutputStream = null;
        try {
            try {
                outputStreamNewOutputStream = Files.newOutputStream(FileUtil.touch(this.file).toPath(), new OpenOption[0]);
                IoUtil.copy(inputStream, outputStreamNewOutputStream);
                return this.file;
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        } finally {
            IoUtil.close((Closeable) outputStreamNewOutputStream);
            if (z2) {
                IoUtil.close((Closeable) inputStream);
            }
        }
    }

    public <T> File writeLines(Iterable<T> iterable, boolean z2) throws IORuntimeException {
        return writeLines(iterable, null, z2);
    }

    public File writeMap(Map<?, ?> map, LineSeparator lineSeparator, String str, boolean z2) throws IORuntimeException {
        if (str == null) {
            str = " = ";
        }
        PrintWriter printWriter = getPrintWriter(z2);
        try {
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (entry != null) {
                    printWriter.print(CharSequenceUtil.format("{}{}{}", entry.getKey(), str, entry.getValue()));
                    printNewLine(printWriter, lineSeparator);
                    printWriter.flush();
                }
            }
            if (printWriter != null) {
                printWriter.close();
            }
            return this.file;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                if (printWriter != null) {
                    try {
                        printWriter.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                }
                throw th2;
            }
        }
    }

    public FileWriter(File file, String str) {
        this(file, CharsetUtil.charset(str));
    }

    public <T> File writeLines(Iterable<T> iterable, LineSeparator lineSeparator, boolean z2) throws IORuntimeException {
        PrintWriter printWriter = getPrintWriter(z2);
        try {
            boolean z3 = true;
            for (T t2 : iterable) {
                if (t2 != null) {
                    if (z3) {
                        if (z2 && FileUtil.isNotEmpty(this.file)) {
                            printNewLine(printWriter, lineSeparator);
                        }
                        z3 = false;
                    } else {
                        printNewLine(printWriter, lineSeparator);
                    }
                    printWriter.print(t2);
                    printWriter.flush();
                }
            }
            if (printWriter != null) {
                printWriter.close();
            }
            return this.file;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                if (printWriter != null) {
                    try {
                        printWriter.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                }
                throw th2;
            }
        }
    }

    public FileWriter(String str, Charset charset) {
        this(FileUtil.file(str), charset);
    }

    public FileWriter(String str, String str2) {
        this(FileUtil.file(str), CharsetUtil.charset(str2));
    }

    public FileWriter(File file) {
        this(file, FileWrapper.DEFAULT_CHARSET);
    }

    public FileWriter(String str) {
        this(str, FileWrapper.DEFAULT_CHARSET);
    }

    public File write(String str) throws IORuntimeException {
        return write(str, false);
    }

    public File write(byte[] bArr, int i2, int i3) throws IORuntimeException {
        return write(bArr, i2, i3, false);
    }

    public File write(byte[] bArr, int i2, int i3, boolean z2) throws IOException, IORuntimeException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(FileUtil.touch(this.file), z2);
            try {
                fileOutputStream.write(bArr, i2, i3);
                fileOutputStream.flush();
                fileOutputStream.close();
                return this.file;
            } finally {
            }
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }
}
