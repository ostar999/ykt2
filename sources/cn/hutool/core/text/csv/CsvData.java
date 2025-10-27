package cn.hutool.core.text.csv;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class CsvData implements Iterable<CsvRow>, Serializable {
    private static final long serialVersionUID = 1;
    private final List<String> header;
    private final List<CsvRow> rows;

    public CsvData(List<String> list, List<CsvRow> list2) {
        this.header = list;
        this.rows = list2;
    }

    public List<String> getHeader() {
        List<String> list = this.header;
        if (list == null) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }

    public CsvRow getRow(int i2) {
        return this.rows.get(i2);
    }

    public int getRowCount() {
        return this.rows.size();
    }

    public List<CsvRow> getRows() {
        return this.rows;
    }

    @Override // java.lang.Iterable
    public Iterator<CsvRow> iterator() {
        return this.rows.iterator();
    }

    public String toString() {
        return "CsvData{header=" + this.header + ", rows=" + this.rows + '}';
    }
}
