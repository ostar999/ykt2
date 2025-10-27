package org.apache.http.impl.cookie;

import cn.hutool.core.text.StrPool;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import org.apache.http.annotation.Immutable;

@Immutable
/* loaded from: classes9.dex */
public class PublicSuffixListParser {
    private static final int MAX_LINE_LEN = 256;
    private final PublicSuffixFilter filter;

    public PublicSuffixListParser(PublicSuffixFilter publicSuffixFilter) {
        this.filter = publicSuffixFilter;
    }

    private boolean readLine(Reader reader, StringBuilder sb) throws IOException {
        char c3;
        sb.setLength(0);
        boolean z2 = false;
        do {
            int i2 = reader.read();
            if (i2 == -1 || (c3 = (char) i2) == '\n') {
                return i2 != -1;
            }
            if (Character.isWhitespace(c3)) {
                z2 = true;
            }
            if (!z2) {
                sb.append(c3);
            }
        } while (sb.length() <= 256);
        throw new IOException("Line too long");
    }

    public void parse(Reader reader) throws IOException {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder(256);
        boolean line = true;
        while (line) {
            line = readLine(bufferedReader, sb);
            String string = sb.toString();
            if (string.length() != 0 && !string.startsWith("//")) {
                if (string.startsWith(StrPool.DOT)) {
                    string = string.substring(1);
                }
                boolean zStartsWith = string.startsWith("!");
                if (zStartsWith) {
                    string = string.substring(1);
                }
                if (zStartsWith) {
                    arrayList2.add(string);
                } else {
                    arrayList.add(string);
                }
            }
        }
        this.filter.setPublicSuffixes(arrayList);
        this.filter.setExceptions(arrayList2);
    }
}
