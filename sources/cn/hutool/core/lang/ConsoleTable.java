package cn.hutool.core.lang;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class ConsoleTable {
    private static final char COLUMN_LINE = '|';
    private static final char CORNER = '+';
    private static final char LF = '\n';
    private static final char ROW_LINE = 65293;
    private static final char SPACE = 12288;
    private List<Integer> columnCharNumber;
    private boolean isSBCMode = true;
    private final List<List<String>> headerList = new ArrayList();
    private final List<List<String>> bodyList = new ArrayList();

    public static ConsoleTable create() {
        return new ConsoleTable();
    }

    private void fillBorder(StringBuilder sb) {
        sb.append(CORNER);
        Iterator<Integer> it = this.columnCharNumber.iterator();
        while (it.hasNext()) {
            sb.append(CharSequenceUtil.repeat(ROW_LINE, it.next().intValue() + 2));
            sb.append(CORNER);
        }
        sb.append('\n');
    }

    private void fillColumns(List<String> list, String[] strArr) {
        for (int i2 = 0; i2 < strArr.length; i2++) {
            String string = StrUtil.toString(strArr[i2]);
            if (this.isSBCMode) {
                string = Convert.toSBC(string);
            }
            list.add(string);
            int length = string.length();
            if (length > this.columnCharNumber.get(i2).intValue()) {
                this.columnCharNumber.set(i2, Integer.valueOf(length));
            }
        }
    }

    private void fillRow(StringBuilder sb, List<String> list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            String str = list.get(i2);
            sb.append(SPACE);
            sb.append(str);
            int length = str.length();
            int iSbcCount = sbcCount(str);
            if (iSbcCount % 2 == 1) {
                sb.append(' ');
            }
            sb.append(SPACE);
            int iIntValue = this.columnCharNumber.get(i2).intValue();
            for (int i3 = 0; i3 < (iIntValue - length) + (iSbcCount / 2); i3++) {
                sb.append(SPACE);
            }
            sb.append(COLUMN_LINE);
        }
    }

    private void fillRows(StringBuilder sb, List<List<String>> list) {
        for (List<String> list2 : list) {
            sb.append(COLUMN_LINE);
            fillRow(sb, list2);
            sb.append('\n');
        }
    }

    private int sbcCount(String str) {
        int i2 = 0;
        for (int i3 = 0; i3 < str.length(); i3++) {
            if (str.charAt(i3) < 127) {
                i2++;
            }
        }
        return i2;
    }

    public ConsoleTable addBody(String... strArr) {
        ArrayList arrayList = new ArrayList();
        this.bodyList.add(arrayList);
        fillColumns(arrayList, strArr);
        return this;
    }

    public ConsoleTable addHeader(String... strArr) {
        if (this.columnCharNumber == null) {
            this.columnCharNumber = new ArrayList(Collections.nCopies(strArr.length, 0));
        }
        ArrayList arrayList = new ArrayList();
        fillColumns(arrayList, strArr);
        this.headerList.add(arrayList);
        return this;
    }

    public void print() {
        Console.print(toString());
    }

    public ConsoleTable setSBCMode(boolean z2) {
        this.isSBCMode = z2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        fillBorder(sb);
        fillRows(sb, this.headerList);
        fillBorder(sb);
        fillRows(sb, this.bodyList);
        fillBorder(sb);
        return sb.toString();
    }
}
