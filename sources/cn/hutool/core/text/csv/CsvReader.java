package cn.hutool.core.text.csv;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.PathUtil;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/* loaded from: classes.dex */
public class CsvReader extends CsvBaseReader implements Iterable<CsvRow>, Closeable {
    private static final long serialVersionUID = 1;
    private final Reader reader;

    public CsvReader() {
        this(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stream$0() {
        try {
            close();
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        IoUtil.close((Closeable) this.reader);
    }

    @Override // java.lang.Iterable
    public Iterator<CsvRow> iterator() {
        return parse(this.reader);
    }

    public CsvData read() throws IORuntimeException {
        return read(this.reader, false);
    }

    public Stream<CsvRow> stream() {
        return (Stream) StreamSupport.stream(spliterator(), false).onClose(new Runnable() { // from class: cn.hutool.core.text.csv.h
            @Override // java.lang.Runnable
            public final void run() {
                this.f2585c.lambda$stream$0();
            }
        });
    }

    public CsvReader(CsvReadConfig csvReadConfig) {
        this((Reader) null, csvReadConfig);
    }

    public void read(CsvRowHandler csvRowHandler) throws IOException, IORuntimeException {
        read(this.reader, false, csvRowHandler);
    }

    public CsvReader(File file, CsvReadConfig csvReadConfig) {
        this(file, CsvBaseReader.DEFAULT_CHARSET, csvReadConfig);
    }

    public CsvReader(Path path, CsvReadConfig csvReadConfig) {
        this(path, CsvBaseReader.DEFAULT_CHARSET, csvReadConfig);
    }

    public CsvReader(File file, Charset charset, CsvReadConfig csvReadConfig) {
        this(FileUtil.getReader(file, charset), csvReadConfig);
    }

    public CsvReader(Path path, Charset charset, CsvReadConfig csvReadConfig) {
        this(PathUtil.getReader(path, charset), csvReadConfig);
    }

    public CsvReader(Reader reader, CsvReadConfig csvReadConfig) {
        super(csvReadConfig);
        this.reader = reader;
    }
}
