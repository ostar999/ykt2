package com.android.volley.toolbox;

import android.os.Bundle;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MultipartRequest extends Request<JSONObject> {
    private static String FILE_PART_NAME = "";
    private MultipartEntity entity;
    private final File mFilePart;
    private final Response.Listener<JSONObject> mListener;

    public MultipartRequest(String str, Response.ErrorListener errorListener, Response.Listener<JSONObject> listener, String str2, File file, Bundle bundle) {
        super(1, str, errorListener);
        this.entity = new MultipartEntity();
        FILE_PART_NAME = str2;
        this.mListener = listener;
        this.mFilePart = file;
        buildMultipartEntity(bundle);
    }

    private void buildMultipartEntity(Bundle bundle) {
        MultipartEntity multipartEntityEncodePOSTUrl = encodePOSTUrl(this.entity, bundle);
        this.entity = multipartEntityEncodePOSTUrl;
        multipartEntityEncodePOSTUrl.addPart(FILE_PART_NAME, new FileBody(this.mFilePart));
    }

    public static MultipartEntity encodePOSTUrl(MultipartEntity multipartEntity, Bundle bundle) {
        if (bundle != null && bundle.size() > 0) {
            boolean z2 = true;
            for (String str : bundle.keySet()) {
                if (str != null) {
                    if (z2) {
                        z2 = false;
                    }
                    Object obj = bundle.get(str);
                    try {
                        multipartEntity.addPart(str, new StringBody(obj != null ? String.valueOf(obj) : ""));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        return multipartEntity;
    }

    @Override // com.android.volley.Request
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            this.entity.writeTo(byteArrayOutputStream);
        } catch (IOException unused) {
            FLog.e("IOException writing to ByteArrayOutputStream", new Object[0]);
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override // com.android.volley.Request
    public String getBodyContentType() {
        return this.entity.getContentType().getValue();
    }

    @Override // com.android.volley.Request
    public Response<JSONObject> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            try {
                return Response.success(new JSONObject(new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers))), HttpHeaderParser.parseCacheHeaders(networkResponse));
            } catch (UnsupportedEncodingException e2) {
                return Response.error(new ParseError(e2));
            } catch (JSONException e3) {
                return Response.error(new ParseError(e3));
            }
        } catch (Exception e4) {
            e4.printStackTrace();
            return null;
        }
    }

    @Override // com.android.volley.Request
    public void deliverResponse(JSONObject jSONObject, String str) {
        try {
            this.mListener.onResponse(jSONObject, str);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
