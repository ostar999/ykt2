package com.huawei.hms.framework.network.grs.g.j;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.common.StringUtils;
import com.huawei.hms.framework.network.grs.GrsApp;
import com.huawei.hms.framework.network.grs.g.k.d;
import com.huawei.hms.framework.network.grs.h.c;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f7683a = "a";

    /* renamed from: b, reason: collision with root package name */
    private static d f7684b;

    public static synchronized d a(Context context) {
        d dVar = f7684b;
        if (dVar != null) {
            return dVar;
        }
        String strA = c.a(GrsApp.getInstance().getBrand("/") + "grs_sdk_server_config.json", context);
        ArrayList arrayList = null;
        if (TextUtils.isEmpty(strA)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(strA).getJSONObject("grs_server");
            JSONArray jSONArray = jSONObject.getJSONArray("grs_base_url");
            if (jSONArray != null && jSONArray.length() > 0) {
                arrayList = new ArrayList();
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    arrayList.add(jSONArray.get(i2).toString());
                }
            }
            d dVar2 = new d();
            f7684b = dVar2;
            dVar2.a(arrayList);
            f7684b.b(jSONObject.getString("grs_query_endpoint_1.0"));
            f7684b.a(jSONObject.getString("grs_query_endpoint_2.0"));
            f7684b.a(jSONObject.getInt("grs_query_timeout"));
        } catch (JSONException e2) {
            Logger.w(f7683a, "getGrsServerBean catch JSONException: %s", StringUtils.anonymizeMessage(e2.getMessage()));
        }
        return f7684b;
    }
}
