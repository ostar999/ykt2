package com.sina.weibo.sdk.net;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/* loaded from: classes6.dex */
public final class h {

    /* renamed from: r, reason: collision with root package name */
    private HashMap<String, String> f17193r = new HashMap<>();

    public final String i() {
        StringBuilder sb = new StringBuilder();
        boolean z2 = true;
        for (String str : this.f17193r.keySet()) {
            if (z2) {
                z2 = false;
            } else {
                sb.append("&");
            }
            String str2 = this.f17193r.get(str);
            if (!TextUtils.isEmpty(str2)) {
                try {
                    sb.append(URLEncoder.encode(str, "UTF-8"));
                    sb.append("=");
                    sb.append(URLEncoder.encode(str2, "UTF-8"));
                } catch (UnsupportedEncodingException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public final void put(String str, String str2) {
        this.f17193r.put(str, str2);
    }
}
