package cn.hutool.core.text.csv;

import com.google.android.exoplayer2.util.TimestampAdjuster;
import java.io.Serializable;

/* loaded from: classes.dex */
public class CsvReadConfig extends CsvConfig<CsvReadConfig> implements Serializable {
    private static final long serialVersionUID = 5396453565371560052L;
    protected long beginLineNo;
    protected boolean errorOnDifferentFieldCount;
    protected boolean trimField;
    protected long headerLineNo = -1;
    protected boolean skipEmptyRows = true;
    protected long endLineNo = TimestampAdjuster.MODE_SHARED;

    public static CsvReadConfig defaultConfig() {
        return new CsvReadConfig();
    }

    public CsvReadConfig setBeginLineNo(long j2) {
        this.beginLineNo = j2;
        return this;
    }

    public CsvReadConfig setContainsHeader(boolean z2) {
        return setHeaderLineNo(z2 ? this.beginLineNo : -1L);
    }

    public CsvReadConfig setEndLineNo(long j2) {
        this.endLineNo = j2;
        return this;
    }

    public CsvReadConfig setErrorOnDifferentFieldCount(boolean z2) {
        this.errorOnDifferentFieldCount = z2;
        return this;
    }

    public CsvReadConfig setHeaderLineNo(long j2) {
        this.headerLineNo = j2;
        return this;
    }

    public CsvReadConfig setSkipEmptyRows(boolean z2) {
        this.skipEmptyRows = z2;
        return this;
    }

    public CsvReadConfig setTrimField(boolean z2) {
        this.trimField = z2;
        return this;
    }
}
