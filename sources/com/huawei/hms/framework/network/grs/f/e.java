package com.huawei.hms.framework.network.grs.f;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes4.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private static final String f7615a = "e";

    /* renamed from: b, reason: collision with root package name */
    public static final Set<String> f7616b = Collections.unmodifiableSet(new a(16));

    public static class a extends HashSet<String> {
        public a(int i2) {
            super(i2);
            add("ser_country");
            add("reg_country");
            add("issue_country");
            add("geo_ip");
        }
    }

    private static String a(Context context, com.huawei.hms.framework.network.grs.e.a aVar, String str, GrsBaseInfo grsBaseInfo, boolean z2) {
        String str2;
        StringBuilder sb;
        String str3;
        String serCountry = grsBaseInfo.getSerCountry();
        String regCountry = grsBaseInfo.getRegCountry();
        String issueCountry = grsBaseInfo.getIssueCountry();
        for (String str4 : str.split(">")) {
            if (f7616b.contains(str4.trim())) {
                if (!"ser_country".equals(str4.trim()) || TextUtils.isEmpty(serCountry) || "UNKNOWN".equals(serCountry)) {
                    if ("reg_country".equals(str4.trim()) && !TextUtils.isEmpty(regCountry) && !"UNKNOWN".equals(regCountry)) {
                        Logger.i(f7615a, "current route_by is regCountry and routerCountry is:" + regCountry);
                        return regCountry;
                    }
                    if ("issue_country".equals(str4.trim()) && !TextUtils.isEmpty(issueCountry) && !"UNKNOWN".equals(issueCountry)) {
                        Logger.i(f7615a, "current route_by is issueCountry and routerCountry is:" + issueCountry);
                        return issueCountry;
                    }
                    if ("geo_ip".equals(str4.trim())) {
                        serCountry = new com.huawei.hms.framework.network.grs.g.b(context, aVar, grsBaseInfo).a(z2);
                        str2 = f7615a;
                        sb = new StringBuilder();
                        str3 = "current route_by is geo_ip and routerCountry is: ";
                    }
                } else {
                    str2 = f7615a;
                    sb = new StringBuilder();
                    str3 = "current route_by is serCountry and routerCountry is:";
                }
                sb.append(str3);
                sb.append(serCountry);
                Logger.i(str2, sb.toString());
                return serCountry;
            }
        }
        return "";
    }

    public static String b(Context context, com.huawei.hms.framework.network.grs.e.a aVar, String str, GrsBaseInfo grsBaseInfo, boolean z2) {
        if (TextUtils.isEmpty(str)) {
            Logger.w(f7615a, "routeBy must be not empty string or null.");
            return null;
        }
        if (!"no_route".equals(str) && !"unconditional".equals(str)) {
            return a(context, aVar, str, grsBaseInfo, z2);
        }
        Logger.v(f7615a, "routeBy equals NO_ROUTE_POLICY");
        return "no_route_country";
    }
}
