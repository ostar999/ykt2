package com.android.volley.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import java.io.UnsupportedEncodingException;

/* loaded from: classes2.dex */
public class StringRequest extends Request<String> {
    private final Response.Listener<String> mListener;

    public StringRequest(int i2, String str, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(i2, str, errorListener);
        this.mListener = listener;
    }

    @Override // com.android.volley.Request
    public Response<String> parseNetworkResponse(NetworkResponse networkResponse) {
        String str;
        try {
            str = new String(networkResponse.data, "utf-8");
        } catch (UnsupportedEncodingException unused) {
            str = new String(networkResponse.data);
        }
        return Response.success(str, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    @Override // com.android.volley.Request
    public void deliverResponse(String str, String str2) {
        this.mListener.onResponse(str, str2);
    }

    public StringRequest(String str, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(0, str, listener, errorListener);
    }
}
