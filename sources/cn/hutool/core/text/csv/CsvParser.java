package cn.hutool.core.text.csv;

import cn.hutool.core.collection.ComputeIter;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ObjectUtil;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public final class CsvParser extends ComputeIter<CsvRow> implements Closeable, Serializable {
    private static final int DEFAULT_ROW_CAPACITY = 10;
    private static final long serialVersionUID = 1;
    private final CsvReadConfig config;
    private boolean finished;
    private CsvRow header;
    private boolean inQuotes;
    private long inQuotesLineCount;
    private int maxFieldCount;
    private final Reader reader;
    private final Buffer buf = new Buffer(32768);
    private int preChar = -1;
    private final StrBuilder currentField = new StrBuilder(512);
    private long lineNo = -1;
    private int firstLineFieldCount = -1;

    public static class Buffer implements Serializable {
        private static final long serialVersionUID = 1;
        final char[] buf;
        private int limit;
        private int mark;
        private int position;

        public Buffer(int i2) {
            this.buf = new char[i2];
        }

        public void appendTo(StrBuilder strBuilder, int i2) {
            strBuilder.append(this.buf, this.mark, i2);
        }

        public char get() {
            char[] cArr = this.buf;
            int i2 = this.position;
            this.position = i2 + 1;
            return cArr[i2];
        }

        public final boolean hasRemaining() {
            return this.position < this.limit;
        }

        public void mark() {
            this.mark = this.position;
        }

        public int read(Reader reader) throws IOException {
            try {
                int i2 = reader.read(this.buf);
                this.mark = 0;
                this.position = 0;
                this.limit = i2;
                return i2;
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        }
    }

    public CsvParser(Reader reader, CsvReadConfig csvReadConfig) {
        Objects.requireNonNull(reader, "reader must not be null");
        this.reader = reader;
        this.config = (CsvReadConfig) ObjectUtil.defaultIfNull(csvReadConfig, new b());
    }

    private void addField(List<String> list, String str) {
        char c3 = this.config.textDelimiter;
        String strReplace = CharSequenceUtil.replace(CharSequenceUtil.unWrap(CharSequenceUtil.trim(str, 1, new Predicate() { // from class: cn.hutool.core.text.csv.f
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CsvParser.lambda$addField$0((Character) obj);
            }
        }), c3), "" + c3 + c3, c3 + "");
        if (this.config.trimField) {
            strReplace = CharSequenceUtil.trim(strReplace);
        }
        list.add(strReplace);
    }

    private void initHeader(List<String> list) {
        LinkedHashMap linkedHashMap = new LinkedHashMap(list.size());
        for (int i2 = 0; i2 < list.size(); i2++) {
            String str = list.get(i2);
            if (MapUtil.isNotEmpty(this.config.headerAlias)) {
                str = (String) ObjectUtil.defaultIfNull(this.config.headerAlias.get(str), str);
            }
            if (CharSequenceUtil.isNotEmpty(str) && !linkedHashMap.containsKey(str)) {
                linkedHashMap.put(str, Integer.valueOf(i2));
            }
        }
        this.header = new CsvRow(this.lineNo, Collections.unmodifiableMap(linkedHashMap), Collections.unmodifiableList(list));
    }

    private boolean isLineEnd(char c3, int i2) {
        return (c3 == '\r' || c3 == '\n') && i2 != 13;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$addField$0(Character ch) {
        return ch.charValue() == '\n' || ch.charValue() == '\r';
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x007d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.List<java.lang.String> readLine() throws cn.hutool.core.io.IORuntimeException {
        /*
            Method dump skipped, instructions count: 232
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.hutool.core.text.csv.CsvParser.readLine():java.util.List");
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.reader.close();
    }

    public List<String> getHeader() {
        CsvReadConfig csvReadConfig = this.config;
        if (csvReadConfig.headerLineNo < 0) {
            throw new IllegalStateException("No header available - header parsing is disabled");
        }
        if (this.lineNo >= csvReadConfig.beginLineNo) {
            return this.header.fields;
        }
        throw new IllegalStateException("No header available - call nextRow() first");
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x0093, code lost:
    
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public cn.hutool.core.text.csv.CsvRow nextRow() throws cn.hutool.core.io.IORuntimeException {
        /*
            r9 = this;
        L0:
            boolean r0 = r9.finished
            r1 = 0
            if (r0 != 0) goto L93
            java.util.List r0 = r9.readLine()
            int r2 = r0.size()
            r3 = 1
            if (r2 >= r3) goto L12
            goto L93
        L12:
            long r4 = r9.lineNo
            cn.hutool.core.text.csv.CsvReadConfig r6 = r9.config
            long r7 = r6.beginLineNo
            int r7 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r7 >= 0) goto L1d
            goto L0
        L1d:
            long r7 = r6.endLineNo
            int r4 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r4 <= 0) goto L25
            goto L93
        L25:
            boolean r4 = r6.skipEmptyRows
            r5 = 0
            if (r4 == 0) goto L39
            if (r2 != r3) goto L39
            java.lang.Object r4 = r0.get(r5)
            java.lang.String r4 = (java.lang.String) r4
            boolean r4 = r4.isEmpty()
            if (r4 == 0) goto L39
            goto L0
        L39:
            cn.hutool.core.text.csv.CsvReadConfig r4 = r9.config
            boolean r6 = r4.errorOnDifferentFieldCount
            if (r6 == 0) goto L6f
            int r6 = r9.firstLineFieldCount
            if (r6 >= 0) goto L46
            r9.firstLineFieldCount = r2
            goto L6f
        L46:
            if (r2 != r6) goto L49
            goto L6f
        L49:
            cn.hutool.core.io.IORuntimeException r0 = new cn.hutool.core.io.IORuntimeException
            r1 = 3
            java.lang.Object[] r1 = new java.lang.Object[r1]
            long r6 = r9.lineNo
            java.lang.Long r4 = java.lang.Long.valueOf(r6)
            r1[r5] = r4
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r1[r3] = r2
            int r2 = r9.firstLineFieldCount
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r3 = 2
            r1[r3] = r2
            java.lang.String r2 = "Line %d has %d fields, but first line has %d fields"
            java.lang.String r1 = java.lang.String.format(r2, r1)
            r0.<init>(r1)
            throw r0
        L6f:
            int r3 = r9.maxFieldCount
            if (r2 <= r3) goto L75
            r9.maxFieldCount = r2
        L75:
            long r2 = r9.lineNo
            long r4 = r4.headerLineNo
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 != 0) goto L86
            cn.hutool.core.text.csv.CsvRow r4 = r9.header
            if (r4 != 0) goto L86
            r9.initHeader(r0)
            goto L0
        L86:
            cn.hutool.core.text.csv.CsvRow r4 = new cn.hutool.core.text.csv.CsvRow
            cn.hutool.core.text.csv.CsvRow r5 = r9.header
            if (r5 != 0) goto L8d
            goto L8f
        L8d:
            java.util.Map<java.lang.String, java.lang.Integer> r1 = r5.headerMap
        L8f:
            r4.<init>(r2, r1, r0)
            return r4
        L93:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.hutool.core.text.csv.CsvParser.nextRow():cn.hutool.core.text.csv.CsvRow");
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // cn.hutool.core.collection.ComputeIter
    public CsvRow computeNext() {
        return nextRow();
    }
}
