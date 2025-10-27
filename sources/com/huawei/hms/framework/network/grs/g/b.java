package com.huawei.hms.framework.network.grs.g;

import android.content.Context;
import android.text.TextUtils;
import cn.hutool.core.lang.RegexPool;
import com.huawei.hms.framework.common.StringUtils;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import org.json.JSONException;
import org.slf4j.Logger;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private final Context f7629a;

    /* renamed from: b, reason: collision with root package name */
    private final GrsBaseInfo f7630b;

    /* renamed from: c, reason: collision with root package name */
    private final com.huawei.hms.framework.network.grs.e.a f7631c;

    public b(Context context, com.huawei.hms.framework.network.grs.e.a aVar, GrsBaseInfo grsBaseInfo) {
        this.f7629a = context;
        this.f7630b = grsBaseInfo;
        this.f7631c = aVar;
    }

    public String a(boolean z2) {
        String strA;
        String str = com.huawei.hms.framework.network.grs.a.a(this.f7631c.a().a("geoipCountryCode", ""), "geoip.countrycode").get(Logger.ROOT_LOGGER_NAME);
        com.huawei.hms.framework.common.Logger.i("GeoipCountry", "geoIpCountry is: " + str);
        String strA2 = this.f7631c.a().a("geoipCountryCodetime", "0");
        long j2 = 0;
        if (!TextUtils.isEmpty(strA2) && strA2.matches(RegexPool.NUMBERS)) {
            try {
                j2 = Long.parseLong(strA2);
            } catch (NumberFormatException e2) {
                com.huawei.hms.framework.common.Logger.w("GeoipCountry", "convert urlParamKey from String to Long catch NumberFormatException.", e2);
            }
        }
        if (TextUtils.isEmpty(str) || com.huawei.hms.framework.network.grs.h.e.a(Long.valueOf(j2))) {
            com.huawei.hms.framework.network.grs.g.k.c cVar = new com.huawei.hms.framework.network.grs.g.k.c(this.f7630b, this.f7629a);
            cVar.a("geoip.countrycode");
            com.huawei.hms.framework.network.grs.e.c cVarC = this.f7631c.c();
            if (cVarC != null) {
                try {
                    strA = i.a(cVarC.a("services", ""), cVar.c());
                } catch (JSONException e3) {
                    com.huawei.hms.framework.common.Logger.w("GeoipCountry", "getGeoipCountry merge services occure jsonException. %s", StringUtils.anonymizeMessage(e3.getMessage()));
                    strA = null;
                }
                if (!TextUtils.isEmpty(strA)) {
                    cVarC.b("services", strA);
                }
            }
            if (z2) {
                d dVarA = this.f7631c.b().a(cVar, "geoip.countrycode", cVarC);
                if (dVarA != null) {
                    str = com.huawei.hms.framework.network.grs.a.a(dVarA.j(), "geoip.countrycode").get(Logger.ROOT_LOGGER_NAME);
                }
                com.huawei.hms.framework.common.Logger.i("GeoipCountry", "sync request to query geoip.countrycode is:" + str);
            } else {
                com.huawei.hms.framework.common.Logger.i("GeoipCountry", "async request to query geoip.countrycode");
                this.f7631c.b().a(cVar, null, "geoip.countrycode", cVarC);
            }
        }
        return str;
    }
}
