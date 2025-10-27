package c;

import android.text.TextUtils;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import cn.hutool.core.lang.RegexPool;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicItemDataBean;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class c {

    /* renamed from: k, reason: collision with root package name */
    public static final String f2217k = "c";

    /* renamed from: l, reason: collision with root package name */
    public static final String f2218l = "sh";

    /* renamed from: m, reason: collision with root package name */
    public static final String f2219m = "\n";

    /* renamed from: n, reason: collision with root package name */
    public static final String f2220n = "exit\n";

    /* renamed from: o, reason: collision with root package name */
    public static c f2221o;

    /* renamed from: c, reason: collision with root package name */
    public Thread f2224c;

    /* renamed from: i, reason: collision with root package name */
    public double f2230i;

    /* renamed from: j, reason: collision with root package name */
    public String f2231j;

    /* renamed from: a, reason: collision with root package name */
    public String f2222a = "rtcdemo";

    /* renamed from: b, reason: collision with root package name */
    public String f2223b = "";

    /* renamed from: d, reason: collision with root package name */
    public boolean f2225d = true;

    /* renamed from: e, reason: collision with root package name */
    public boolean f2226e = false;

    /* renamed from: f, reason: collision with root package name */
    public boolean f2227f = false;

    /* renamed from: g, reason: collision with root package name */
    public int f2228g = 5;

    /* renamed from: h, reason: collision with root package name */
    public int f2229h = 0;

    public static synchronized c d() {
        if (f2221o == null) {
            f2221o = new c();
        }
        return f2221o;
    }

    public static Map<String, String> e() throws IOException {
        String[] strArrSplit;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/stat")));
            String line = bufferedReader.readLine();
            bufferedReader.close();
            strArrSplit = line.split(" ");
        } catch (IOException e2) {
            e2.printStackTrace();
            strArrSplit = null;
        }
        HashMap map = new HashMap();
        map.put(PLVLinkMicManager.USER, strArrSplit[2]);
        map.put("nice", strArrSplit[3]);
        map.put("system", strArrSplit[4]);
        map.put(PLVLinkMicItemDataBean.STATUS_IDLE, strArrSplit[5]);
        map.put("iowait", strArrSplit[6]);
        map.put("irq", strArrSplit[7]);
        map.put("softirq", strArrSplit[8]);
        return map;
    }

    public static float f() throws InterruptedException, IOException, NumberFormatException {
        Map<String, String> mapE = e();
        long j2 = Long.parseLong(mapE.get(PLVLinkMicManager.USER)) + Long.parseLong(mapE.get("nice")) + Long.parseLong(mapE.get("system")) + Long.parseLong(mapE.get(PLVLinkMicItemDataBean.STATUS_IDLE)) + Long.parseLong(mapE.get("iowait")) + Long.parseLong(mapE.get("irq")) + Long.parseLong(mapE.get("softirq"));
        long j3 = Long.parseLong(mapE.get(PLVLinkMicItemDataBean.STATUS_IDLE));
        try {
            Thread.sleep(360L);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        Map<String, String> mapE2 = e();
        long j4 = ((((((Long.parseLong(mapE2.get(PLVLinkMicManager.USER)) + Long.parseLong(mapE2.get("nice"))) + Long.parseLong(mapE2.get("system"))) + Long.parseLong(mapE2.get(PLVLinkMicItemDataBean.STATUS_IDLE))) + Long.parseLong(mapE2.get("iowait"))) + Long.parseLong(mapE2.get("irq"))) + Long.parseLong(mapE2.get("softirq"))) - j2;
        return ((j4 - (Long.parseLong(mapE2.get(PLVLinkMicItemDataBean.STATUS_IDLE)) - j3)) * 100) / j4;
    }

    public String a() {
        return this.f2231j;
    }

    public void b(String str) {
        Log.d(f2217k, "setPkgName: " + str);
        if (str.length() > 15) {
            this.f2222a = str.substring(0, 14);
        } else {
            this.f2222a = str;
        }
    }

    public String c() {
        if (!this.f2226e && this.f2222a != null) {
            this.f2227f = true;
        }
        return this.f2223b;
    }

    public void g() {
        this.f2222a = null;
        this.f2231j = null;
        this.f2225d = false;
        this.f2224c = null;
    }

    public List<String> a(String str) {
        return a(new String[]{str});
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x007e, code lost:
    
        r9 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x007f, code lost:
    
        r9.printStackTrace();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0113 A[Catch: IOException -> 0x010a, TRY_LEAVE, TryCatch #11 {IOException -> 0x010a, blocks: (B:94:0x0106, B:98:0x010e, B:100:0x0113), top: B:112:0x0106 }] */
    /* JADX WARN: Removed duplicated region for block: B:104:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x00e6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0106 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:114:0x00c4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:133:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00cc A[Catch: IOException -> 0x00c8, TryCatch #12 {IOException -> 0x00c8, blocks: (B:60:0x00c4, B:64:0x00cc, B:66:0x00d1), top: B:114:0x00c4 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00d1 A[Catch: IOException -> 0x00c8, TRY_LEAVE, TryCatch #12 {IOException -> 0x00c8, blocks: (B:60:0x00c4, B:64:0x00cc, B:66:0x00d1), top: B:114:0x00c4 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00ee A[Catch: IOException -> 0x00ea, TryCatch #10 {IOException -> 0x00ea, blocks: (B:77:0x00e6, B:81:0x00ee, B:83:0x00f3), top: B:110:0x00e6 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00f3 A[Catch: IOException -> 0x00ea, TRY_LEAVE, TryCatch #10 {IOException -> 0x00ea, blocks: (B:77:0x00e6, B:81:0x00ee, B:83:0x00f3), top: B:110:0x00e6 }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00fc A[PHI: r2 r3 r4 r9
      0x00fc: PHI (r2v15 ??) = (r2v12 ??), (r2v13 ??), (r2v17 ??), (r2v17 ??) binds: [B:69:0x00d8, B:86:0x00fa, B:27:0x007f, B:24:0x0073] A[DONT_GENERATE, DONT_INLINE]
      0x00fc: PHI (r3v15 ??) = (r3v12 ??), (r3v13 ??), (r3v20 ??), (r3v20 ??) binds: [B:69:0x00d8, B:86:0x00fa, B:27:0x007f, B:24:0x0073] A[DONT_GENERATE, DONT_INLINE]
      0x00fc: PHI (r4v13 java.io.BufferedReader) = 
      (r4v10 java.io.BufferedReader)
      (r4v11 java.io.BufferedReader)
      (r4v26 java.io.BufferedReader)
      (r4v26 java.io.BufferedReader)
     binds: [B:69:0x00d8, B:86:0x00fa, B:27:0x007f, B:24:0x0073] A[DONT_GENERATE, DONT_INLINE]
      0x00fc: PHI (r9v23 'e' ??) = (r9v17 'e' ??), (r9v20 'e' ??), (r9v33 'e' ??), (r9v32 'e' ??) binds: [B:69:0x00d8, B:86:0x00fa, B:27:0x007f, B:24:0x0073] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x010e A[Catch: IOException -> 0x010a, TryCatch #11 {IOException -> 0x010a, blocks: (B:94:0x0106, B:98:0x010e, B:100:0x0113), top: B:112:0x0106 }] */
    /* JADX WARN: Type inference failed for: r2v0, types: [int] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15, types: [java.lang.Process] */
    /* JADX WARN: Type inference failed for: r2v17, types: [java.lang.Process] */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v23 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v12, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r3v13, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v20, types: [java.io.DataOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r3v23 */
    /* JADX WARN: Type inference failed for: r3v24 */
    /* JADX WARN: Type inference failed for: r3v25 */
    /* JADX WARN: Type inference failed for: r3v26 */
    /* JADX WARN: Type inference failed for: r3v27 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v13 */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v16, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r9v17 */
    /* JADX WARN: Type inference failed for: r9v18, types: [java.io.IOException, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r9v19, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v20 */
    /* JADX WARN: Type inference failed for: r9v21, types: [java.io.IOException, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r9v22 */
    /* JADX WARN: Type inference failed for: r9v23 */
    /* JADX WARN: Type inference failed for: r9v25 */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v32, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r9v33, types: [java.io.IOException, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r9v34 */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List<java.lang.String> a(java.lang.String[] r9) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 289
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: c.c.a(java.lang.String[]):java.util.List");
    }

    public String b() {
        int size;
        List<String> listA = a("top -n 1");
        ArrayList arrayList = new ArrayList();
        int i2 = -1;
        for (String str : listA) {
            if (str.contains("CPU")) {
                for (String str2 : str.split("\\s+")) {
                    if (Pattern.matches("^[A-Z]+$", str2) || str2.contains("CPU") || str2.contains("MEM")) {
                        if (str2.contains("CPU")) {
                            if (str2.contains(ExifInterface.LATITUDE_SOUTH)) {
                                int iIndexOf = str2.indexOf(ExifInterface.LATITUDE_SOUTH);
                                if (iIndexOf >= 0) {
                                    int i3 = iIndexOf + 1;
                                    arrayList.add(str2.substring(0, i3));
                                    arrayList.add(str2.substring(i3));
                                    size = arrayList.size();
                                }
                            } else {
                                arrayList.add(str2);
                                size = arrayList.size();
                            }
                            i2 = size - 1;
                        } else {
                            arrayList.add(str2);
                        }
                    }
                }
            } else if (str.contains(this.f2222a) && i2 != -1) {
                String[] strArrSplit = str.split("\\s+");
                ArrayList arrayList2 = new ArrayList();
                Collections.addAll(arrayList2, strArrSplit);
                if (arrayList2.size() > arrayList.size()) {
                    Iterator it = arrayList2.iterator();
                    while (it.hasNext()) {
                        if (TextUtils.isEmpty((String) it.next())) {
                            it.remove();
                        }
                    }
                }
                Matcher matcher = Pattern.compile(RegexPool.NUMBERS).matcher((String) arrayList2.get(i2));
                if (matcher.find()) {
                    return matcher.group();
                }
            }
        }
        return "0";
    }

    public final void a(double d3) {
        int i2 = this.f2229h;
        int i3 = this.f2228g;
        if (i2 >= i3) {
            if (i2 == i3) {
                this.f2229h = i2 + 1;
            }
            this.f2231j = String.format("%.2f", Double.valueOf(this.f2230i));
            this.f2229h = 0;
            this.f2230i = 0.0d;
            return;
        }
        int i4 = i2 + 1;
        this.f2229h = i4;
        this.f2230i = (((i4 - 1) * this.f2230i) + d3) / i4;
    }
}
