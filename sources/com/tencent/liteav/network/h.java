package com.tencent.liteav.network;

import android.content.Context;
import android.os.Handler;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import java.util.Iterator;
import java.util.Vector;

/* loaded from: classes6.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    private final int f19561a = 5;

    /* renamed from: b, reason: collision with root package name */
    private final int f19562b = 2;

    /* renamed from: c, reason: collision with root package name */
    private String f19563c = "";

    /* renamed from: d, reason: collision with root package name */
    private String f19564d = "";

    /* renamed from: e, reason: collision with root package name */
    private int f19565e = 0;

    /* renamed from: f, reason: collision with root package name */
    private String f19566f = "";

    /* renamed from: g, reason: collision with root package name */
    private Handler f19567g;

    public interface a {
        void a(int i2, String str, Vector<e> vector);
    }

    public h(Context context) {
        if (context != null) {
            this.f19567g = new Handler(context.getMainLooper());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long e() {
        return com.tencent.liteav.basic.c.c.a().a("Network", "AccRetryCountWithoutSecret");
    }

    public String d() {
        return this.f19566f;
    }

    public int c() {
        return this.f19565e;
    }

    public String b() {
        return this.f19564d;
    }

    public String a() {
        return this.f19563c;
    }

    public int a(String str, int i2, final a aVar) {
        this.f19563c = "";
        this.f19564d = "";
        this.f19565e = 0;
        this.f19566f = "";
        if (str != null && !str.isEmpty()) {
            final String streamIDByStreamUrl = TXCCommonUtil.getStreamIDByStreamUrl(str);
            if (streamIDByStreamUrl != null && !streamIDByStreamUrl.isEmpty()) {
                final String strA = a("bizid", str);
                final String strA2 = a("txSecret", str);
                final String strA3 = a("txTime", str);
                if (!a(true, strA, strA3, strA2)) {
                    return -3;
                }
                a(streamIDByStreamUrl, strA, strA2, strA3, i2, new a() { // from class: com.tencent.liteav.network.h.1
                    @Override // com.tencent.liteav.network.h.a
                    public void a(int i3, String str2, Vector<e> vector) {
                        h.this.f19563c = streamIDByStreamUrl;
                        h.this.f19564d = strA;
                        h.this.f19565e = i3;
                        h.this.f19566f = str2;
                        if (vector == null || vector.isEmpty()) {
                            a aVar2 = aVar;
                            if (aVar2 != null) {
                                aVar2.a(i3, str2, null);
                                return;
                            }
                            return;
                        }
                        Vector<e> vector2 = new Vector<>();
                        Iterator<e> it = vector.iterator();
                        while (it.hasNext()) {
                            e next = it.next();
                            String strSubstring = next.f19559a;
                            if (strSubstring.indexOf("?") != -1) {
                                strSubstring = strSubstring.substring(0, strSubstring.indexOf("?"));
                            }
                            vector2.add(new e(strSubstring + "?txSecret=" + strA2 + "&txTime=" + strA3 + "&bizid=" + strA, next.f19560b));
                        }
                        if (aVar != null) {
                            Iterator<e> it2 = vector2.iterator();
                            while (it2.hasNext()) {
                                e next2 = it2.next();
                                TXCLog.e("TXRTMPAccUrlFetcher", "accurl = " + next2.f19559a + " quic = " + next2.f19560b);
                            }
                            aVar.a(i3, str2, vector2);
                        }
                    }
                });
                return 0;
            }
            TXCLog.i("TXRTMPAccUrlFetcher", "getAccerateStreamPlayUrl streamID is empty");
            return -2;
        }
        TXCLog.i("TXRTMPAccUrlFetcher", "getAccerateStreamPlayUrl input playUrl is empty");
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(boolean z2, String str, String str2, String str3) {
        return z2 ? (str == null || str.isEmpty() || str2 == null || str2.isEmpty() || str3 == null || str3.isEmpty()) ? false : true : (str == null || str2 == null || str3 == null) ? false : true;
    }

    private void a(final String str, final String str2, final String str3, final String str4, final int i2, final a aVar) {
        new Thread("getRTMPAccUrl") { // from class: com.tencent.liteav.network.h.2
            /* JADX WARN: Can't wrap try/catch for region: R(8:(9:113|17|(1:19)(1:20)|21|(2:22|(1:24)(1:128))|25|(1:27)|(3:29|(1:31)|32)|33)|(5:35|(1:68)(4:40|(5:43|(10:45|46|109|47|(1:49)(1:50)|51|52|119|53|(1:132)(1:(2:58|133)(2:59|134)))(2:62|130)|63|115|41)|129|64)|69|70|(3:111|72|(3:74|123|75))(7:78|(3:121|80|(3:124|82|83))(5:84|(2:87|85)|135|88|(3:125|90|91))|102|117|103|127|106))(1:96)|97|102|117|103|127|106) */
            /* JADX WARN: Code restructure failed: missing block: B:105:0x0294, code lost:
            
                com.tencent.liteav.basic.log.TXCLog.e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl exception sleep");
             */
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:113:0x0049 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Type inference failed for: r11v0 */
            /* JADX WARN: Type inference failed for: r11v1, types: [boolean, int] */
            /* JADX WARN: Type inference failed for: r11v2 */
            @Override // java.lang.Thread, java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() throws org.json.JSONException, java.lang.InterruptedException, java.io.IOException {
                /*
                    Method dump skipped, instructions count: 689
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.network.h.AnonymousClass2.run():void");
            }
        }.start();
    }

    private String a(String str, String str2) {
        if (str == null || str.length() == 0 || str2 == null || str2.length() == 0) {
            return null;
        }
        String lowerCase = str.toLowerCase();
        for (String str3 : str2.split("[?&]")) {
            if (str3.indexOf("=") != -1) {
                String[] strArrSplit = str3.split("[=]");
                if (strArrSplit.length == 2) {
                    String str4 = strArrSplit[0];
                    String str5 = strArrSplit[1];
                    if (str4 != null && str4.toLowerCase().equalsIgnoreCase(lowerCase)) {
                        return str5;
                    }
                } else {
                    continue;
                }
            }
        }
        return "";
    }
}
