package com.xiaomi.push;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class ef extends ei {
    public ef(Context context, int i2) {
        super(context, i2);
    }

    private String b() throws JSONException {
        Bundle extras;
        StringBuilder sb = new StringBuilder();
        try {
            Intent intentRegisterReceiver = ((ei) this).f339a.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            if (intentRegisterReceiver != null && (extras = intentRegisterReceiver.getExtras()) != null) {
                Set<String> setKeySet = extras.keySet();
                JSONObject jSONObject = new JSONObject();
                if (setKeySet != null && setKeySet.size() > 0) {
                    for (String str : setKeySet) {
                        if (!TextUtils.isEmpty(str)) {
                            try {
                                jSONObject.put(str, String.valueOf(extras.get(str)));
                            } catch (Exception unused) {
                            }
                        }
                    }
                    sb.append(jSONObject);
                }
            }
        } catch (Exception unused2) {
        }
        return sb.toString();
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return 20;
    }

    @Override // com.xiaomi.push.ei
    public hz a() {
        return hz.Battery;
    }

    @Override // com.xiaomi.push.ei
    /* renamed from: a */
    public String mo336a() {
        return b();
    }
}
