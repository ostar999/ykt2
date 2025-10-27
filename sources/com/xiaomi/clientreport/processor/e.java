package com.xiaomi.clientreport.processor;

import android.text.TextUtils;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.xiaomi.clientreport.data.PerfClientReport;
import com.xiaomi.push.y;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.HashMap;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes6.dex */
public class e {
    private static PerfClientReport a(PerfClientReport perfClientReport, String str) {
        long[] jArrM124a;
        if (perfClientReport == null || (jArrM124a = m124a(str)) == null) {
            return null;
        }
        perfClientReport.perfCounts = jArrM124a[0];
        perfClientReport.perfLatencies = jArrM124a[1];
        return perfClientReport;
    }

    private static PerfClientReport a(String str) {
        PerfClientReport blankInstance = null;
        try {
            String[] strArrM125a = m125a(str);
            if (strArrM125a == null || strArrM125a.length < 4 || TextUtils.isEmpty(strArrM125a[0]) || TextUtils.isEmpty(strArrM125a[1]) || TextUtils.isEmpty(strArrM125a[2]) || TextUtils.isEmpty(strArrM125a[3])) {
                return null;
            }
            blankInstance = PerfClientReport.getBlankInstance();
            blankInstance.production = Integer.parseInt(strArrM125a[0]);
            blankInstance.clientInterfaceId = strArrM125a[1];
            blankInstance.reportType = Integer.parseInt(strArrM125a[2]);
            blankInstance.code = Integer.parseInt(strArrM125a[3]);
            return blankInstance;
        } catch (Exception unused) {
            com.xiaomi.channel.commonutils.logger.b.c("parse per key error");
            return blankInstance;
        }
    }

    public static String a(PerfClientReport perfClientReport) {
        return perfClientReport.production + DictionaryFactory.SHARP + perfClientReport.clientInterfaceId + DictionaryFactory.SHARP + perfClientReport.reportType + DictionaryFactory.SHARP + perfClientReport.code;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v12, types: [int] */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r3v2, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r5v8, types: [java.lang.String[]] */
    /* JADX WARN: Type inference failed for: r5v9, types: [java.lang.Object] */
    /* renamed from: a, reason: collision with other method in class */
    private static HashMap<String, String> m123a(String str) throws Throwable {
        HashMap map = new HashMap();
        if (TextUtils.isEmpty(str) || !new File(str).exists()) {
            return map;
        }
        BufferedReader bufferedReader = null;
        ?? length = 0;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                BufferedReader bufferedReader3 = new BufferedReader(new FileReader(str));
                while (true) {
                    try {
                        String line = bufferedReader3.readLine();
                        if (line == null) {
                            break;
                        }
                        ?? Split = line.split("%%%");
                        length = Split.length;
                        if (length >= 2) {
                            length = 0;
                            length = 0;
                            if (!TextUtils.isEmpty(Split[0]) && !TextUtils.isEmpty(Split[1])) {
                                length = Split[0];
                                map.put(length, Split[1]);
                            }
                        }
                    } catch (Exception e2) {
                        e = e2;
                        bufferedReader2 = bufferedReader3;
                        com.xiaomi.channel.commonutils.logger.b.a(e);
                        y.a(bufferedReader2);
                        bufferedReader = bufferedReader2;
                        return map;
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader3;
                        y.a(bufferedReader);
                        throw th;
                    }
                }
                y.a(bufferedReader3);
                bufferedReader = length;
            } catch (Exception e3) {
                e = e3;
            }
            return map;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:112:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00d8 A[PHI: r1
      0x00d8: PHI (r1v6 java.io.File) = (r1v5 java.io.File), (r1v7 java.io.File) binds: [B:66:0x00d6, B:32:0x0095] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00f5  */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10, types: [java.io.BufferedReader, java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v2, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List<java.lang.String> a(android.content.Context r7, java.lang.String r8) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.clientreport.processor.e.a(android.content.Context, java.lang.String):java.util.List");
    }

    private static void a(String str, HashMap<String, String> map) throws Throwable {
        BufferedWriter bufferedWriter;
        Throwable th;
        Exception e2;
        if (TextUtils.isEmpty(str) || map == null || map.size() == 0) {
            return;
        }
        File file = new File(str);
        if (file.exists()) {
            file.delete();
        }
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
        } catch (Exception e3) {
            bufferedWriter = null;
            e2 = e3;
        } catch (Throwable th2) {
            bufferedWriter = null;
            th = th2;
            y.a(bufferedWriter);
            throw th;
        }
        try {
            try {
                for (String str2 : map.keySet()) {
                    bufferedWriter.write(str2 + "%%%" + map.get(str2));
                    bufferedWriter.newLine();
                }
            } catch (Throwable th3) {
                th = th3;
                y.a(bufferedWriter);
                throw th;
            }
        } catch (Exception e4) {
            e2 = e4;
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            y.a(bufferedWriter);
        }
        y.a(bufferedWriter);
    }

    public static void a(String str, com.xiaomi.clientreport.data.a[] aVarArr) throws IOException {
        RandomAccessFile randomAccessFile;
        if (aVarArr == null || aVarArr.length <= 0 || TextUtils.isEmpty(str)) {
            return;
        }
        FileLock fileLockLock = null;
        try {
            File file = new File(str + ".lock");
            y.m774a(file);
            randomAccessFile = new RandomAccessFile(file, InternalZipConstants.WRITE_MODE);
            try {
                fileLockLock = randomAccessFile.getChannel().lock();
                HashMap<String, String> mapM123a = m123a(str);
                for (com.xiaomi.clientreport.data.a aVar : aVarArr) {
                    if (aVar != null) {
                        String strA = a((PerfClientReport) aVar);
                        long j2 = ((PerfClientReport) aVar).perfCounts;
                        long j3 = ((PerfClientReport) aVar).perfLatencies;
                        if (!TextUtils.isEmpty(strA) && j2 > 0 && j3 >= 0) {
                            a(mapM123a, strA, j2, j3);
                        }
                    }
                }
                a(str, mapM123a);
                if (fileLockLock != null && fileLockLock.isValid()) {
                    try {
                        fileLockLock.release();
                    } catch (IOException e2) {
                        e = e2;
                        com.xiaomi.channel.commonutils.logger.b.a(e);
                        y.a(randomAccessFile);
                    }
                }
            } catch (Throwable unused) {
                try {
                    com.xiaomi.channel.commonutils.logger.b.c("failed to write perf to file ");
                    if (fileLockLock != null && fileLockLock.isValid()) {
                        try {
                            fileLockLock.release();
                        } catch (IOException e3) {
                            e = e3;
                            com.xiaomi.channel.commonutils.logger.b.a(e);
                            y.a(randomAccessFile);
                        }
                    }
                    y.a(randomAccessFile);
                } catch (Throwable th) {
                    if (fileLockLock != null && fileLockLock.isValid()) {
                        try {
                            fileLockLock.release();
                        } catch (IOException e4) {
                            com.xiaomi.channel.commonutils.logger.b.a(e4);
                        }
                    }
                    y.a(randomAccessFile);
                    throw th;
                }
            }
        } catch (Throwable unused2) {
            randomAccessFile = null;
        }
        y.a(randomAccessFile);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void a(java.util.HashMap<java.lang.String, java.lang.String> r9, java.lang.String r10, long r11, long r13) {
        /*
            java.lang.Object r0 = r9.get(r10)
            java.lang.String r0 = (java.lang.String) r0
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            java.lang.String r2 = "#"
            if (r1 == 0) goto L24
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
        L13:
            r0.append(r11)
            r0.append(r2)
            r0.append(r13)
            java.lang.String r11 = r0.toString()
            r9.put(r10, r11)
            goto L49
        L24:
            long[] r0 = m124a(r0)
            if (r0 == 0) goto L43
            r1 = 0
            r3 = r0[r1]
            r5 = 0
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 <= 0) goto L43
            r1 = 1
            r7 = r0[r1]
            int r0 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r0 >= 0) goto L3b
            goto L43
        L3b:
            long r11 = r11 + r3
            long r13 = r13 + r7
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            goto L13
        L43:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            goto L13
        L49:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.clientreport.processor.e.a(java.util.HashMap, java.lang.String, long, long):void");
    }

    /* renamed from: a, reason: collision with other method in class */
    public static long[] m124a(String str) {
        long[] jArr = new long[2];
        try {
            String[] strArrSplit = str.split(DictionaryFactory.SHARP);
            if (strArrSplit.length >= 2) {
                jArr[0] = Long.parseLong(strArrSplit[0].trim());
                jArr[1] = Long.parseLong(strArrSplit[1].trim());
            }
            return jArr;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return null;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private static String[] m125a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return str.split(DictionaryFactory.SHARP);
    }
}
