package cn.hutool.core.text.csv;

import java.io.Serializable;

/* loaded from: classes.dex */
public class CsvWriteConfig extends CsvConfig<CsvWriteConfig> implements Serializable {
    private static final long serialVersionUID = 5396453565371560052L;
    protected boolean alwaysDelimitText;
    protected boolean endingLineBreak;
    protected char[] lineDelimiter = {'\r', '\n'};

    public static CsvWriteConfig defaultConfig() {
        return new CsvWriteConfig();
    }

    public CsvWriteConfig setAlwaysDelimitText(boolean z2) {
        this.alwaysDelimitText = z2;
        return this;
    }

    public CsvWriteConfig setEndingLineBreak(boolean z2) {
        this.endingLineBreak = z2;
        return this;
    }

    public CsvWriteConfig setLineDelimiter(char[] cArr) {
        this.lineDelimiter = cArr;
        return this;
    }
}
