package cn.hutool.core.text.csv;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class CsvUtil {
    public static CsvReader getReader(CsvReadConfig csvReadConfig) {
        return new CsvReader(csvReadConfig);
    }

    public static CsvWriter getWriter(String str, Charset charset) {
        return new CsvWriter(str, charset);
    }

    public static CsvReader getReader() {
        return new CsvReader();
    }

    public static CsvWriter getWriter(File file, Charset charset) {
        return new CsvWriter(file, charset);
    }

    public static CsvReader getReader(Reader reader, CsvReadConfig csvReadConfig) {
        return new CsvReader(reader, csvReadConfig);
    }

    public static CsvWriter getWriter(String str, Charset charset, boolean z2) {
        return new CsvWriter(str, charset, z2);
    }

    public static CsvReader getReader(Reader reader) {
        return getReader(reader, null);
    }

    public static CsvWriter getWriter(File file, Charset charset, boolean z2) {
        return new CsvWriter(file, charset, z2);
    }

    public static CsvWriter getWriter(File file, Charset charset, boolean z2, CsvWriteConfig csvWriteConfig) {
        return new CsvWriter(file, charset, z2, csvWriteConfig);
    }

    public static CsvWriter getWriter(Writer writer) {
        return new CsvWriter(writer);
    }

    public static CsvWriter getWriter(Writer writer, CsvWriteConfig csvWriteConfig) {
        return new CsvWriter(writer, csvWriteConfig);
    }
}
