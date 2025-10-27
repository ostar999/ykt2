package org.eclipse.jetty.util;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

/* loaded from: classes9.dex */
public abstract class PatternMatcher {
    public void match(Pattern pattern, URI[] uriArr, boolean z2) throws Exception {
        if (uriArr != null) {
            String[] strArrSplit = pattern == null ? null : pattern.pattern().split(",");
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; strArrSplit != null && i2 < strArrSplit.length; i2++) {
                arrayList.add(Pattern.compile(strArrSplit[i2]));
            }
            if (arrayList.isEmpty()) {
                arrayList.add(pattern);
            }
            if (arrayList.isEmpty()) {
                matchPatterns(null, uriArr, z2);
                return;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                matchPatterns((Pattern) it.next(), uriArr, z2);
            }
        }
    }

    public void matchPatterns(Pattern pattern, URI[] uriArr, boolean z2) throws Exception {
        for (int i2 = 0; i2 < uriArr.length; i2++) {
            String string = uriArr[i2].toString();
            if ((pattern == null && z2) || (pattern != null && pattern.matcher(string).matches())) {
                matched(uriArr[i2]);
            }
        }
    }

    public abstract void matched(URI uri) throws Exception;
}
