package com.android.volley;

import java.util.Collections;
import java.util.Map;

/* loaded from: classes2.dex */
public class NetworkResponse {
    public final byte[] data;
    public final Map<String, String> headers;
    public final boolean notModified;
    public final int statusCode;

    public NetworkResponse(int i2, byte[] bArr, Map<String, String> map, boolean z2) {
        this.statusCode = i2;
        this.data = bArr;
        this.headers = map;
        this.notModified = z2;
    }

    public NetworkResponse(byte[] bArr) {
        this(200, bArr, Collections.emptyMap(), false);
    }

    public NetworkResponse(byte[] bArr, Map<String, String> map) {
        this(200, bArr, map, false);
    }
}
