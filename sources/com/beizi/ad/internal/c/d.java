package com.beizi.ad.internal.c;

import android.text.TextUtils;
import cn.hutool.core.text.CharPool;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
class d {

    /* renamed from: d, reason: collision with root package name */
    private static final Pattern f4069d = Pattern.compile("[R,r]ange:[ ]?bytes=(\\d*)-");

    /* renamed from: e, reason: collision with root package name */
    private static final Pattern f4070e = Pattern.compile("GET /(.*) HTTP");

    /* renamed from: a, reason: collision with root package name */
    public final String f4071a;

    /* renamed from: b, reason: collision with root package name */
    public final long f4072b;

    /* renamed from: c, reason: collision with root package name */
    public final boolean f4073c;

    public d(String str) {
        k.a(str);
        long jA = a(str);
        this.f4072b = Math.max(0L, jA);
        this.f4073c = jA >= 0;
        this.f4071a = b(str);
    }

    public static d a(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = bufferedReader.readLine();
            if (TextUtils.isEmpty(line)) {
                return new d(sb.toString());
            }
            sb.append(line);
            sb.append('\n');
        }
    }

    private String b(String str) {
        Matcher matcher = f4070e.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new IllegalArgumentException("Invalid request `" + str + "`: url not found!");
    }

    public String toString() {
        return "GetRequest{rangeOffset=" + this.f4072b + ", partial=" + this.f4073c + ", uri='" + this.f4071a + CharPool.SINGLE_QUOTE + '}';
    }

    private long a(String str) {
        Matcher matcher = f4069d.matcher(str);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }
        return -1L;
    }
}
