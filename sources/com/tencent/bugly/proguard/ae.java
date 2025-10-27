package com.tencent.bugly.proguard;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.bugly.crashreport.biz.UserInfoBean;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public final class ae {
    public static bu a(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return null;
        }
        bu buVar = new bu();
        buVar.f17797a = userInfoBean.f17318e;
        buVar.f17801e = userInfoBean.f17323j;
        buVar.f17800d = userInfoBean.f17316c;
        buVar.f17799c = userInfoBean.f17317d;
        buVar.f17804h = userInfoBean.f17328o == 1;
        int i2 = userInfoBean.f17315b;
        if (i2 == 1) {
            buVar.f17798b = (byte) 1;
        } else if (i2 == 2) {
            buVar.f17798b = (byte) 4;
        } else if (i2 == 3) {
            buVar.f17798b = (byte) 2;
        } else if (i2 == 4) {
            buVar.f17798b = (byte) 3;
        } else if (i2 == 8) {
            buVar.f17798b = (byte) 8;
        } else {
            if (i2 < 10 || i2 >= 20) {
                al.e("unknown uinfo type %d ", Integer.valueOf(i2));
                return null;
            }
            buVar.f17798b = (byte) i2;
        }
        HashMap map = new HashMap();
        buVar.f17802f = map;
        if (userInfoBean.f17329p >= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(userInfoBean.f17329p);
            map.put("C01", sb.toString());
        }
        if (userInfoBean.f17330q >= 0) {
            Map<String, String> map2 = buVar.f17802f;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(userInfoBean.f17330q);
            map2.put("C02", sb2.toString());
        }
        Map<String, String> map3 = userInfoBean.f17331r;
        if (map3 != null && map3.size() > 0) {
            for (Map.Entry<String, String> entry : userInfoBean.f17331r.entrySet()) {
                buVar.f17802f.put("C03_" + entry.getKey(), entry.getValue());
            }
        }
        Map<String, String> map4 = userInfoBean.f17332s;
        if (map4 != null && map4.size() > 0) {
            for (Map.Entry<String, String> entry2 : userInfoBean.f17332s.entrySet()) {
                buVar.f17802f.put("C04_" + entry2.getKey(), entry2.getValue());
            }
        }
        Map<String, String> map5 = buVar.f17802f;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(!userInfoBean.f17325l);
        map5.put("A36", sb3.toString());
        Map<String, String> map6 = buVar.f17802f;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(userInfoBean.f17320g);
        map6.put("F02", sb4.toString());
        Map<String, String> map7 = buVar.f17802f;
        StringBuilder sb5 = new StringBuilder();
        sb5.append(userInfoBean.f17321h);
        map7.put("F03", sb5.toString());
        buVar.f17802f.put("F04", userInfoBean.f17323j);
        Map<String, String> map8 = buVar.f17802f;
        StringBuilder sb6 = new StringBuilder();
        sb6.append(userInfoBean.f17322i);
        map8.put("F05", sb6.toString());
        buVar.f17802f.put("F06", userInfoBean.f17326m);
        Map<String, String> map9 = buVar.f17802f;
        StringBuilder sb7 = new StringBuilder();
        sb7.append(userInfoBean.f17324k);
        map9.put("F10", sb7.toString());
        al.c("summary type %d vm:%d", Byte.valueOf(buVar.f17798b), Integer.valueOf(buVar.f17802f.size()));
        return buVar;
    }

    public static <T extends m> T a(byte[] bArr, Class<T> cls) {
        if (bArr != null && bArr.length > 0) {
            try {
                T tNewInstance = cls.newInstance();
                k kVar = new k(bArr);
                kVar.a("utf-8");
                tNewInstance.a(kVar);
                return tNewInstance;
            } catch (Throwable th) {
                if (!al.b(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    public static bq a(Context context, int i2, byte[] bArr) {
        String str;
        aa aaVarB = aa.b();
        StrategyBean strategyBeanC = ac.a().c();
        if (aaVarB != null && strategyBeanC != null) {
            try {
                bq bqVar = new bq();
                synchronized (aaVarB) {
                    bqVar.f17745a = aaVarB.f17416b;
                    bqVar.f17746b = aaVarB.e();
                    bqVar.f17747c = aaVarB.f17417c;
                    bqVar.f17748d = aaVarB.f17429o;
                    bqVar.f17749e = aaVarB.f17433s;
                    bqVar.f17750f = aaVarB.f17422h;
                    bqVar.f17751g = i2;
                    if (bArr == null) {
                        bArr = "".getBytes();
                    }
                    bqVar.f17752h = bArr;
                    bqVar.f17753i = aaVarB.h();
                    bqVar.f17754j = aaVarB.f17425k;
                    bqVar.f17755k = new HashMap();
                    bqVar.f17756l = aaVarB.d();
                    bqVar.f17757m = strategyBeanC.f17350o;
                    bqVar.f17759o = aaVarB.g();
                    bqVar.f17760p = ab.c(context);
                    bqVar.f17761q = System.currentTimeMillis();
                    bqVar.f17763s = aaVarB.i();
                    bqVar.f17766v = aaVarB.g();
                    bqVar.f17767w = bqVar.f17760p;
                    bqVar.f17758n = "com.tencent.bugly";
                    bqVar.f17755k.put("A26", aaVarB.s());
                    Map<String, String> map = bqVar.f17755k;
                    StringBuilder sb = new StringBuilder();
                    sb.append(aa.C());
                    map.put("A62", sb.toString());
                    Map<String, String> map2 = bqVar.f17755k;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(aa.D());
                    map2.put("A63", sb2.toString());
                    Map<String, String> map3 = bqVar.f17755k;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(aaVarB.J);
                    map3.put("F11", sb3.toString());
                    Map<String, String> map4 = bqVar.f17755k;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(aaVarB.I);
                    map4.put("F12", sb4.toString());
                    bqVar.f17755k.put("D3", aaVarB.f17431q);
                    List<o> list = p.f17850b;
                    if (list != null) {
                        for (o oVar : list) {
                            String str2 = oVar.versionKey;
                            if (str2 != null && (str = oVar.version) != null) {
                                bqVar.f17755k.put(str2, str);
                            }
                        }
                    }
                    bqVar.f17755k.put("G15", ap.d("G15", ""));
                    bqVar.f17755k.put("G10", ap.d("G10", ""));
                    bqVar.f17755k.put("D4", ap.d("D4", "0"));
                }
                Map<String, String> mapX = aaVarB.x();
                if (mapX != null) {
                    for (Map.Entry<String, String> entry : mapX.entrySet()) {
                        if (!TextUtils.isEmpty(entry.getValue())) {
                            bqVar.f17755k.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
                return bqVar;
            } catch (Throwable th) {
                if (!al.b(th)) {
                    th.printStackTrace();
                }
                return null;
            }
        }
        al.e("Can not create request pkg for parameters is invalid.", new Object[0]);
        return null;
    }

    public static byte[] a(Object obj) {
        try {
            e eVar = new e();
            eVar.b();
            eVar.a("utf-8");
            eVar.c();
            eVar.b("RqdServer");
            eVar.c("sync");
            eVar.a("detail", (String) obj);
            return eVar.a();
        } catch (Throwable th) {
            if (al.b(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    public static br a(byte[] bArr) {
        if (bArr != null) {
            try {
                e eVar = new e();
                eVar.b();
                eVar.a("utf-8");
                eVar.a(bArr);
                Object objB = eVar.b("detail", new br());
                if (br.class.isInstance(objB)) {
                    return (br) br.class.cast(objB);
                }
                return null;
            } catch (Throwable th) {
                if (!al.b(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    public static byte[] a(m mVar) {
        try {
            l lVar = new l();
            lVar.a("utf-8");
            mVar.a(lVar);
            byte[] bArr = new byte[lVar.f17845a.position()];
            System.arraycopy(lVar.f17845a.array(), 0, bArr, 0, lVar.f17845a.position());
            return bArr;
        } catch (Throwable th) {
            if (al.b(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }
}
