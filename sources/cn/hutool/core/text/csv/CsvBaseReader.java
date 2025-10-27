package cn.hutool.core.text.csv;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.PathUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes.dex */
public class CsvBaseReader implements Serializable {
    protected static final Charset DEFAULT_CHARSET = CharsetUtil.CHARSET_UTF_8;
    private static final long serialVersionUID = 1;
    private final CsvReadConfig config;

    public CsvBaseReader() {
        this(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$read$1(List list, Class cls, CsvRow csvRow) {
        list.add(csvRow.toBean(cls));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$read$2(List list, Class cls, CsvRow csvRow) {
        list.add(csvRow.toBean(cls));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$readMapList$0(List list, CsvRow csvRow) {
        list.add(csvRow.getFieldMap());
    }

    public CsvParser parse(Reader reader) throws IORuntimeException {
        return new CsvParser(reader, this.config);
    }

    public CsvData read(File file) throws IORuntimeException {
        return read(file, DEFAULT_CHARSET);
    }

    public CsvData readFromStr(String str) {
        return read(new StringReader(str));
    }

    public List<Map<String, String>> readMapList(Reader reader) throws IOException, IORuntimeException {
        this.config.setContainsHeader(true);
        final ArrayList arrayList = new ArrayList();
        read(reader, new CsvRowHandler() { // from class: cn.hutool.core.text.csv.e
            @Override // cn.hutool.core.text.csv.CsvRowHandler
            public final void handle(CsvRow csvRow) {
                CsvBaseReader.lambda$readMapList$0(arrayList, csvRow);
            }
        });
        return arrayList;
    }

    public void setContainsHeader(boolean z2) {
        this.config.setContainsHeader(z2);
    }

    public void setErrorOnDifferentFieldCount(boolean z2) {
        this.config.setErrorOnDifferentFieldCount(z2);
    }

    public void setFieldSeparator(char c3) {
        this.config.setFieldSeparator(c3);
    }

    public void setSkipEmptyRows(boolean z2) {
        this.config.setSkipEmptyRows(z2);
    }

    public void setTextDelimiter(char c3) {
        this.config.setTextDelimiter(c3);
    }

    public CsvBaseReader(CsvReadConfig csvReadConfig) {
        this.config = (CsvReadConfig) ObjectUtil.defaultIfNull(csvReadConfig, new b());
    }

    public CsvData read(File file, Charset charset) throws IORuntimeException {
        Path path = file.toPath();
        Objects.requireNonNull(path, "file must not be null");
        return read(path, charset);
    }

    public void readFromStr(String str, CsvRowHandler csvRowHandler) throws IOException, IORuntimeException {
        read(parse(new StringReader(str)), true, csvRowHandler);
    }

    public CsvData read(Path path) throws IORuntimeException {
        return read(path, DEFAULT_CHARSET);
    }

    public CsvData read(Path path, Charset charset) throws IllegalArgumentException, IORuntimeException {
        Assert.notNull(path, "path must not be null", new Object[0]);
        return read(PathUtil.getReader(path, charset));
    }

    public CsvData read(Reader reader) throws IORuntimeException {
        return read(reader, true);
    }

    public CsvData read(Reader reader, boolean z2) throws IOException, IORuntimeException {
        CsvParser csvParser = parse(reader);
        final ArrayList arrayList = new ArrayList();
        read(csvParser, z2, new CsvRowHandler() { // from class: cn.hutool.core.text.csv.c
            @Override // cn.hutool.core.text.csv.CsvRowHandler
            public final void handle(CsvRow csvRow) {
                arrayList.add(csvRow);
            }
        });
        return new CsvData(this.config.headerLineNo > -1 ? csvParser.getHeader() : null, arrayList);
    }

    public <T> List<T> read(Reader reader, final Class<T> cls) throws IOException, IORuntimeException {
        this.config.setContainsHeader(true);
        final ArrayList arrayList = new ArrayList();
        read(reader, new CsvRowHandler() { // from class: cn.hutool.core.text.csv.d
            @Override // cn.hutool.core.text.csv.CsvRowHandler
            public final void handle(CsvRow csvRow) {
                CsvBaseReader.lambda$read$1(arrayList, cls, csvRow);
            }
        });
        return arrayList;
    }

    public <T> List<T> read(String str, final Class<T> cls) throws IOException, IORuntimeException {
        this.config.setContainsHeader(true);
        final ArrayList arrayList = new ArrayList();
        read(new StringReader(str), new CsvRowHandler() { // from class: cn.hutool.core.text.csv.a
            @Override // cn.hutool.core.text.csv.CsvRowHandler
            public final void handle(CsvRow csvRow) {
                CsvBaseReader.lambda$read$2(arrayList, cls, csvRow);
            }
        });
        return arrayList;
    }

    public void read(Reader reader, CsvRowHandler csvRowHandler) throws IOException, IORuntimeException {
        read(reader, true, csvRowHandler);
    }

    public void read(Reader reader, boolean z2, CsvRowHandler csvRowHandler) throws IOException, IORuntimeException {
        read(parse(reader), z2, csvRowHandler);
    }

    private void read(CsvParser csvParser, boolean z2, CsvRowHandler csvRowHandler) throws IOException, IORuntimeException {
        while (csvParser.hasNext()) {
            try {
                csvRowHandler.handle(csvParser.next());
            } finally {
                if (z2) {
                    IoUtil.close((Closeable) csvParser);
                }
            }
        }
    }
}
