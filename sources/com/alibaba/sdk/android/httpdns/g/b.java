package com.alibaba.sdk.android.httpdns.g;

import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class b extends Exception {
    private int code;

    private b(int i2, String str) {
        super(str);
        this.code = i2;
    }

    public static b a(int i2, String str) {
        return new b(i2, a(str));
    }

    private static String a(String str) {
        try {
            return new JSONObject(str).getString("code");
        } catch (JSONException unused) {
            return str;
        }
    }

    public boolean e() {
        return this.code == 403 && getMessage().equals("ServiceLevelDeny");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        b bVar = (b) obj;
        return this.code == bVar.code && getMessage().equals(bVar.getMessage());
    }

    public int getCode() {
        return this.code;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.code), getMessage()});
    }
}
