package cn.hutool.core.text.csv;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ArrayIter;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public final class CsvWriter implements Closeable, Flushable, Serializable {
    private static final long serialVersionUID = 1;
    private final CsvWriteConfig config;
    private boolean isFirstLine;
    private boolean newline;
    private final Writer writer;

    public CsvWriter(String str) {
        this(FileUtil.file(str));
    }

    private void appendField(String str) throws IOException {
        boolean z2;
        CsvWriteConfig csvWriteConfig = this.config;
        boolean z3 = csvWriteConfig.alwaysDelimitText;
        char c3 = csvWriteConfig.textDelimiter;
        char c4 = csvWriteConfig.fieldSeparator;
        if (this.newline) {
            this.newline = false;
        } else {
            this.writer.write(c4);
        }
        boolean z4 = true;
        if (str == null) {
            if (z3) {
                this.writer.write(new char[]{c3, c3});
                return;
            }
            return;
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                z4 = z3;
                z2 = false;
                break;
            }
            char c5 = charArray[i2];
            if (c5 == c3) {
                z2 = true;
                break;
            }
            if (c5 == c4 || c5 == '\n' || c5 == '\r') {
                z3 = true;
            }
            i2++;
        }
        if (z4) {
            this.writer.write(c3);
        }
        if (z2) {
            for (char c6 : charArray) {
                if (c6 == c3) {
                    this.writer.write(c3);
                }
                this.writer.write(c6);
            }
        } else {
            this.writer.write(charArray);
        }
        if (z4) {
            this.writer.write(c3);
        }
    }

    private void appendLine(String... strArr) throws IORuntimeException {
        try {
            doAppendLine(strArr);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    private void doAppendLine(String... strArr) throws IOException {
        if (strArr != null) {
            if (this.isFirstLine) {
                this.isFirstLine = false;
            } else {
                this.writer.write(this.config.lineDelimiter);
            }
            for (String str : strArr) {
                appendField(str);
            }
            this.newline = true;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException, IORuntimeException {
        if (this.config.endingLineBreak) {
            writeLine();
        }
        IoUtil.close((Closeable) this.writer);
    }

    @Override // java.io.Flushable
    public void flush() throws IOException, IORuntimeException {
        try {
            this.writer.flush();
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public CsvWriter setAlwaysDelimitText(boolean z2) {
        this.config.setAlwaysDelimitText(z2);
        return this;
    }

    public CsvWriter setLineDelimiter(char[] cArr) {
        this.config.setLineDelimiter(cArr);
        return this;
    }

    public CsvWriter write(String[]... strArr) throws IORuntimeException {
        return write(new ArrayIter((Object[]) strArr));
    }

    public CsvWriter writeBeans(Iterable<?> iterable) throws IOException, IORuntimeException {
        if (CollUtil.isNotEmpty(iterable)) {
            Iterator<?> it = iterable.iterator();
            boolean z2 = true;
            while (it.hasNext()) {
                Map<String, Object> mapBeanToMap = BeanUtil.beanToMap(it.next(), new String[0]);
                if (z2) {
                    writeHeaderLine((String[]) mapBeanToMap.keySet().toArray(new String[0]));
                    z2 = false;
                }
                writeLine(Convert.toStrArray(mapBeanToMap.values()));
            }
            flush();
        }
        return this;
    }

    public CsvWriter writeComment(String str) throws IOException, IllegalArgumentException {
        Assert.notNull(this.config.commentCharacter, "Comment is disable!", new Object[0]);
        try {
            if (this.isFirstLine) {
                this.isFirstLine = false;
            } else {
                this.writer.write(this.config.lineDelimiter);
            }
            this.writer.write(this.config.commentCharacter.charValue());
            this.writer.write(str);
            this.newline = true;
            return this;
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public CsvWriter writeHeaderLine(String... strArr) throws IORuntimeException {
        Map<String, String> map = this.config.headerAlias;
        if (MapUtil.isNotEmpty(map)) {
            for (int i2 = 0; i2 < strArr.length; i2++) {
                String str = map.get(strArr[i2]);
                if (str != null) {
                    strArr[i2] = str;
                }
            }
        }
        return writeLine(strArr);
    }

    public CsvWriter writeLine(String... strArr) throws IORuntimeException {
        if (ArrayUtil.isEmpty((Object[]) strArr)) {
            return writeLine();
        }
        appendLine(strArr);
        return this;
    }

    public CsvWriter(File file) {
        this(file, CharsetUtil.CHARSET_UTF_8);
    }

    public CsvWriter write(Iterable<?> iterable) throws IOException, IORuntimeException {
        if (CollUtil.isNotEmpty(iterable)) {
            Iterator<?> it = iterable.iterator();
            while (it.hasNext()) {
                appendLine(Convert.toStrArray(it.next()));
            }
            flush();
        }
        return this;
    }

    public CsvWriter(String str, Charset charset) {
        this(FileUtil.file(str), charset);
    }

    public CsvWriter(File file, Charset charset) {
        this(file, charset, false);
    }

    public CsvWriter writeLine() throws IOException, IORuntimeException {
        try {
            this.writer.write(this.config.lineDelimiter);
            this.newline = true;
            return this;
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public CsvWriter(String str, Charset charset, boolean z2) {
        this(FileUtil.file(str), charset, z2);
    }

    public CsvWriter(File file, Charset charset, boolean z2) {
        this(file, charset, z2, (CsvWriteConfig) null);
    }

    public CsvWriter write(CsvData csvData) throws IOException, IORuntimeException {
        if (csvData != null) {
            List<String> header = csvData.getHeader();
            if (CollUtil.isNotEmpty((Collection<?>) header)) {
                writeHeaderLine((String[]) header.toArray(new String[0]));
            }
            write(csvData.getRows());
            flush();
        }
        return this;
    }

    public CsvWriter(String str, Charset charset, boolean z2, CsvWriteConfig csvWriteConfig) {
        this(FileUtil.file(str), charset, z2, csvWriteConfig);
    }

    public CsvWriter(File file, Charset charset, boolean z2, CsvWriteConfig csvWriteConfig) {
        this(FileUtil.getWriter(file, charset, z2), z2 ? csvWriteConfig == null ? CsvWriteConfig.defaultConfig().setEndingLineBreak(true) : csvWriteConfig.setEndingLineBreak(true) : csvWriteConfig);
    }

    public CsvWriter(Writer writer) {
        this(writer, (CsvWriteConfig) null);
    }

    public CsvWriter(Writer writer, CsvWriteConfig csvWriteConfig) {
        this.newline = true;
        this.isFirstLine = true;
        this.writer = writer instanceof BufferedWriter ? writer : new BufferedWriter(writer);
        this.config = (CsvWriteConfig) ObjectUtil.defaultIfNull(csvWriteConfig, (Supplier<? extends CsvWriteConfig>) new Supplier() { // from class: cn.hutool.core.text.csv.i
            @Override // java.util.function.Supplier
            public final Object get() {
                return CsvWriteConfig.defaultConfig();
            }
        });
    }
}
