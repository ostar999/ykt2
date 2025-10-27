package org.eclipse.jetty.server;

import cn.hutool.core.text.CharPool;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class InclusiveByteRange {
    private static final Logger LOG = Log.getLogger((Class<?>) InclusiveByteRange.class);
    long first;
    long last;

    public InclusiveByteRange(long j2, long j3) {
        this.first = j2;
        this.last = j3;
    }

    public static List satisfiableRanges(Enumeration enumeration, long j2) throws NumberFormatException {
        int iIndexOf;
        long j3;
        long j4;
        Object obj = null;
        while (enumeration.hasMoreElements()) {
            StringTokenizer stringTokenizer = new StringTokenizer((String) enumeration.nextElement(), "=,", false);
            Object objAdd = obj;
            String strTrim = null;
            while (true) {
                try {
                    if (stringTokenizer.hasMoreTokens()) {
                        try {
                            strTrim = stringTokenizer.nextToken().trim();
                            iIndexOf = strTrim.indexOf(45);
                        } catch (NumberFormatException e2) {
                            Logger logger = LOG;
                            logger.warn("Bad range format: {}", strTrim);
                            logger.ignore(e2);
                        }
                        if (iIndexOf >= 0) {
                            int i2 = iIndexOf + 1;
                            if (strTrim.indexOf("-", i2) < 0) {
                                if (iIndexOf == 0) {
                                    if (i2 < strTrim.length()) {
                                        j4 = Long.parseLong(strTrim.substring(i2).trim());
                                        j3 = -1;
                                    } else {
                                        LOG.warn("Bad range format: {}", strTrim);
                                    }
                                } else if (i2 < strTrim.length()) {
                                    j3 = Long.parseLong(strTrim.substring(0, iIndexOf).trim());
                                    j4 = Long.parseLong(strTrim.substring(i2).trim());
                                } else {
                                    j3 = Long.parseLong(strTrim.substring(0, iIndexOf).trim());
                                    j4 = -1;
                                }
                                if ((j3 != -1 || j4 != -1) && (j3 == -1 || j4 == -1 || j3 <= j4)) {
                                    if (j3 < j2) {
                                        objAdd = LazyList.add(objAdd, new InclusiveByteRange(j3, j4));
                                    }
                                }
                            }
                        }
                        if (!HttpHeaderValues.BYTES.equals(strTrim)) {
                            LOG.warn("Bad range format: {}", strTrim);
                            break;
                        }
                    }
                } catch (Exception e3) {
                    Logger logger2 = LOG;
                    logger2.warn("Bad range format: {}", strTrim);
                    logger2.ignore(e3);
                }
            }
            obj = objAdd;
        }
        return LazyList.getList(obj, true);
    }

    public static String to416HeaderRangeString(long j2) {
        StringBuilder sb = new StringBuilder(40);
        sb.append("bytes */");
        sb.append(j2);
        return sb.toString();
    }

    public long getFirst() {
        return this.first;
    }

    public long getLast() {
        return this.last;
    }

    public long getSize(long j2) {
        return (getLast(j2) - getFirst(j2)) + 1;
    }

    public String toHeaderRangeString(long j2) {
        StringBuilder sb = new StringBuilder(40);
        sb.append("bytes ");
        sb.append(getFirst(j2));
        sb.append(CharPool.DASHED);
        sb.append(getLast(j2));
        sb.append("/");
        sb.append(j2);
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(60);
        sb.append(Long.toString(this.first));
        sb.append(":");
        sb.append(Long.toString(this.last));
        return sb.toString();
    }

    public long getFirst(long j2) {
        long j3 = this.first;
        if (j3 >= 0) {
            return j3;
        }
        long j4 = j2 - this.last;
        if (j4 < 0) {
            return 0L;
        }
        return j4;
    }

    public long getLast(long j2) {
        if (this.first < 0) {
            return j2 - 1;
        }
        long j3 = this.last;
        return (j3 < 0 || j3 >= j2) ? j2 - 1 : j3;
    }
}
