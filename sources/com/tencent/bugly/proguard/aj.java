package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import android.util.Pair;
import com.hyphenate.easeui.constants.EaseConstant;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes6.dex */
public final class aj implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    protected int f17489a;

    /* renamed from: b, reason: collision with root package name */
    protected long f17490b;

    /* renamed from: c, reason: collision with root package name */
    protected long f17491c;

    /* renamed from: d, reason: collision with root package name */
    private int f17492d;

    /* renamed from: e, reason: collision with root package name */
    private int f17493e;

    /* renamed from: f, reason: collision with root package name */
    private final Context f17494f;

    /* renamed from: g, reason: collision with root package name */
    private final int f17495g;

    /* renamed from: h, reason: collision with root package name */
    private final byte[] f17496h;

    /* renamed from: i, reason: collision with root package name */
    private final aa f17497i;

    /* renamed from: j, reason: collision with root package name */
    private final ac f17498j;

    /* renamed from: k, reason: collision with root package name */
    private final af f17499k;

    /* renamed from: l, reason: collision with root package name */
    private final ai f17500l;

    /* renamed from: m, reason: collision with root package name */
    private final int f17501m;

    /* renamed from: n, reason: collision with root package name */
    private final ah f17502n;

    /* renamed from: o, reason: collision with root package name */
    private final ah f17503o;

    /* renamed from: p, reason: collision with root package name */
    private String f17504p;

    /* renamed from: q, reason: collision with root package name */
    private final String f17505q;

    /* renamed from: r, reason: collision with root package name */
    private final Map<String, String> f17506r;

    /* renamed from: s, reason: collision with root package name */
    private boolean f17507s;

    public aj(Context context, int i2, int i3, byte[] bArr, String str, String str2, ah ahVar, boolean z2) {
        this(context, i2, i3, bArr, str, str2, ahVar, 2, 30000, z2);
    }

    private static void a(String str) {
        al.e("[Upload] Failed to upload(%d): %s", 1, str);
    }

    public final void b(long j2) {
        this.f17491c += j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String str;
        ac acVar;
        Pair pair;
        boolean zBooleanValue;
        try {
            this.f17489a = 0;
            this.f17490b = 0L;
            this.f17491c = 0L;
            if (ab.c(this.f17494f) == null) {
                str = "network is not available";
            } else {
                byte[] bArr = this.f17496h;
                str = (bArr == null || bArr.length == 0) ? "request package is empty!" : (this.f17494f == null || this.f17497i == null || (acVar = this.f17498j) == null || this.f17499k == null) ? "illegal access error" : acVar.c() == null ? "illegal local strategy" : null;
            }
            if (str != null) {
                a(false, 0, str);
                return;
            }
            byte[] bArrA = ap.a(this.f17496h);
            if (bArrA == null) {
                a(false, 0, "failed to zip request body");
                return;
            }
            HashMap map = new HashMap(10);
            map.put("tls", "1");
            map.put("prodId", this.f17497i.e());
            map.put("bundleId", this.f17497i.f17417c);
            map.put("appVer", this.f17497i.f17429o);
            Map<String, String> map2 = this.f17506r;
            if (map2 != null) {
                map.putAll(map2);
            }
            map.put(EaseConstant.MESSAGE_TYPE_CMD, Integer.toString(this.f17495g));
            map.put(ConstantsAPI.Token.WX_TOKEN_PLATFORMID_KEY, Byte.toString((byte) 1));
            map.put("sdkVer", this.f17497i.f17422h);
            map.put("strategylastUpdateTime", Long.toString(this.f17498j.c().f17350o));
            this.f17500l.a(this.f17501m, System.currentTimeMillis());
            String strB = this.f17504p;
            this.f17498j.c();
            int i2 = 0;
            int i3 = 0;
            while (true) {
                int i4 = i2 + 1;
                if (i2 >= this.f17492d) {
                    a(false, i3, "failed after many attempts");
                    return;
                }
                if (i4 > 1) {
                    al.d("[Upload] Failed to upload last time, wait and try(%d) again.", Integer.valueOf(i4));
                    ap.b(this.f17493e);
                    if (i4 == this.f17492d) {
                        al.d("[Upload] Use the back-up url at the last time: %s", this.f17505q);
                        strB = this.f17505q;
                    }
                }
                al.c("[Upload] Send %d bytes", Integer.valueOf(bArrA.length));
                strB = b(strB);
                al.c("[Upload] Upload to %s with cmd %d (pid=%d | tid=%d).", strB, Integer.valueOf(this.f17495g), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                byte[] bArrA2 = this.f17499k.a(strB, bArrA, this, map);
                Map<String, String> map3 = this.f17499k.f17456c;
                Pair<Boolean, Boolean> pairA = a(bArrA2, map3);
                if (((Boolean) pairA.first).booleanValue()) {
                    Pair<Boolean, Boolean> pairA2 = a(map3);
                    if (((Boolean) pairA2.first).booleanValue()) {
                        byte[] bArrB = ap.b(bArrA2);
                        if (bArrB != null) {
                            bArrA2 = bArrB;
                        }
                        br brVarA = ae.a(bArrA2);
                        if (brVarA == null) {
                            a(false, 1, "failed to decode response package");
                            Boolean bool = Boolean.FALSE;
                            pair = new Pair(bool, bool);
                        } else {
                            Object[] objArr = new Object[2];
                            objArr[0] = Integer.valueOf(brVarA.f17772b);
                            byte[] bArr2 = brVarA.f17773c;
                            objArr[1] = Integer.valueOf(bArr2 == null ? 0 : bArr2.length);
                            al.c("[Upload] Response cmd is: %d, length of sBuffer is: %d", objArr);
                            if (a(brVarA, this.f17497i, this.f17498j)) {
                                a(true, 2, "successfully uploaded");
                                Boolean bool2 = Boolean.TRUE;
                                pair = new Pair(bool2, bool2);
                            } else {
                                a(false, 2, "failed to process response package");
                                Boolean bool3 = Boolean.FALSE;
                                pair = new Pair(bool3, bool3);
                            }
                        }
                        zBooleanValue = !((Boolean) pair.first).booleanValue() ? ((Boolean) pair.second).booleanValue() : false;
                    } else {
                        zBooleanValue = ((Boolean) pairA2.second).booleanValue();
                    }
                } else {
                    zBooleanValue = ((Boolean) pairA.second).booleanValue();
                }
                if (!zBooleanValue) {
                    return;
                }
                i3 = 1;
                i2 = i4;
            }
        } catch (Throwable th) {
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    public aj(Context context, int i2, int i3, byte[] bArr, String str, String str2, ah ahVar, int i4, int i5, boolean z2) {
        this.f17492d = 2;
        this.f17493e = 30000;
        this.f17504p = null;
        this.f17489a = 0;
        this.f17490b = 0L;
        this.f17491c = 0L;
        this.f17507s = false;
        this.f17494f = context;
        this.f17497i = aa.a(context);
        this.f17496h = bArr;
        this.f17498j = ac.a();
        if (af.f17454a == null) {
            af.f17454a = new af(context);
        }
        this.f17499k = af.f17454a;
        ai aiVarA = ai.a();
        this.f17500l = aiVarA;
        this.f17501m = i2;
        this.f17504p = str;
        this.f17505q = str2;
        this.f17502n = ahVar;
        this.f17503o = aiVarA.f17473a;
        this.f17495g = i3;
        if (i4 > 0) {
            this.f17492d = i4;
        }
        if (i5 > 0) {
            this.f17493e = i5;
        }
        this.f17507s = z2;
        this.f17506r = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x001b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(boolean r5, int r6, java.lang.String r7) {
        /*
            r4 = this;
            int r0 = r4.f17495g
            r1 = 630(0x276, float:8.83E-43)
            if (r0 == r1) goto L1b
            r1 = 640(0x280, float:8.97E-43)
            if (r0 == r1) goto L17
            r1 = 830(0x33e, float:1.163E-42)
            if (r0 == r1) goto L1b
            r1 = 840(0x348, float:1.177E-42)
            if (r0 == r1) goto L17
            java.lang.String r0 = java.lang.String.valueOf(r0)
            goto L1d
        L17:
            java.lang.String r0 = "userinfo"
            goto L1d
        L1b:
            java.lang.String r0 = "crash"
        L1d:
            r1 = 1
            r2 = 0
            if (r5 == 0) goto L2b
            java.lang.Object[] r6 = new java.lang.Object[r1]
            r6[r2] = r0
            java.lang.String r0 = "[Upload] Success: %s"
            com.tencent.bugly.proguard.al.a(r0, r6)
            goto L3e
        L2b:
            r3 = 3
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r3[r2] = r6
            r3[r1] = r0
            r6 = 2
            r3[r6] = r7
            java.lang.String r6 = "[Upload] Failed to upload(%d) %s: %s"
            com.tencent.bugly.proguard.al.e(r6, r3)
        L3e:
            long r0 = r4.f17490b
            long r2 = r4.f17491c
            long r0 = r0 + r2
            r2 = 0
            int r6 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r6 <= 0) goto L5e
            com.tencent.bugly.proguard.ai r6 = r4.f17500l
            boolean r0 = r4.f17507s
            long r0 = r6.a(r0)
            long r2 = r4.f17490b
            long r0 = r0 + r2
            long r2 = r4.f17491c
            long r0 = r0 + r2
            com.tencent.bugly.proguard.ai r6 = r4.f17500l
            boolean r2 = r4.f17507s
            r6.a(r0, r2)
        L5e:
            com.tencent.bugly.proguard.ah r6 = r4.f17502n
            if (r6 == 0) goto L65
            r6.a(r5, r7)
        L65:
            com.tencent.bugly.proguard.ah r6 = r4.f17503o
            if (r6 == 0) goto L6c
            r6.a(r5, r7)
        L6c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.aj.a(boolean, int, java.lang.String):void");
    }

    private static String b(String str) {
        if (ap.b(str)) {
            return str;
        }
        try {
            return String.format("%s?aid=%s", str, UUID.randomUUID().toString());
        } catch (Throwable th) {
            al.a(th);
            return str;
        }
    }

    private static boolean a(br brVar, aa aaVar, ac acVar) throws NumberFormatException {
        if (brVar == null) {
            al.d("resp == null!", new Object[0]);
            return false;
        }
        byte b3 = brVar.f17771a;
        if (b3 != 0) {
            al.e("resp result error %d", Byte.valueOf(b3));
            return false;
        }
        try {
            if (!ap.b(brVar.f17777g) && !aa.b().i().equals(brVar.f17777g)) {
                w.a().a(ac.f17444a, com.alipay.sdk.packet.d.f3298n, brVar.f17777g.getBytes("UTF-8"), true);
                aaVar.d(brVar.f17777g);
            }
        } catch (Throwable th) {
            al.a(th);
        }
        aaVar.f17427m = brVar.f17775e;
        int i2 = brVar.f17772b;
        if (i2 == 510) {
            byte[] bArr = brVar.f17773c;
            if (bArr == null) {
                al.e("[Upload] Strategy data is null. Response cmd: %d", Integer.valueOf(i2));
                return false;
            }
            bt btVar = (bt) ae.a(bArr, bt.class);
            if (btVar == null) {
                al.e("[Upload] Failed to decode strategy from server. Response cmd: %d", Integer.valueOf(brVar.f17772b));
                return false;
            }
            acVar.a(btVar);
        }
        return true;
    }

    private Pair<Boolean, Boolean> a(byte[] bArr, Map<String, String> map) {
        if (bArr == null) {
            a("Failed to upload for no response!");
            return new Pair<>(Boolean.FALSE, Boolean.TRUE);
        }
        al.c("[Upload] Received %d bytes", Integer.valueOf(bArr.length));
        if (bArr.length == 0) {
            a(false, 1, "response data from server is empty");
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    al.c("[Upload] HTTP headers from server: key = %s, value = %s", entry.getKey(), entry.getValue());
                }
            }
            Boolean bool = Boolean.FALSE;
            return new Pair<>(bool, bool);
        }
        Boolean bool2 = Boolean.TRUE;
        return new Pair<>(bool2, bool2);
    }

    public final void a(long j2) {
        this.f17489a++;
        this.f17490b += j2;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00bb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.util.Pair<java.lang.Boolean, java.lang.Boolean> a(java.util.Map<java.lang.String, java.lang.String> r8) {
        /*
            Method dump skipped, instructions count: 295
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.aj.a(java.util.Map):android.util.Pair");
    }
}
