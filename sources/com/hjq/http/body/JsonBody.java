package com.hjq.http.body;

import androidx.annotation.NonNull;
import com.hjq.http.model.ContentType;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class JsonBody extends RequestBody {
    private final byte[] mBytes;
    private final String mJson;

    public JsonBody(Map<?, ?> map) {
        this(new JSONObject(map));
    }

    @Override // okhttp3.RequestBody
    public long contentLength() {
        return this.mBytes.length;
    }

    @Override // okhttp3.RequestBody
    /* renamed from: contentType */
    public MediaType get$contentType() {
        return ContentType.JSON;
    }

    public String getJson() {
        return this.mJson;
    }

    @NonNull
    public String toString() {
        return this.mJson;
    }

    @Override // okhttp3.RequestBody
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        byte[] bArr = this.mBytes;
        bufferedSink.write(bArr, 0, bArr.length);
    }

    public JsonBody(List<?> list) {
        this(new JSONArray((Collection) list));
    }

    public JsonBody(JSONObject jSONObject) {
        String string = jSONObject.toString();
        this.mJson = string;
        this.mBytes = string.getBytes();
    }

    public JsonBody(JSONArray jSONArray) {
        String string = jSONArray.toString();
        this.mJson = string;
        this.mBytes = string.getBytes();
    }

    public JsonBody(String str) {
        this.mJson = str;
        this.mBytes = str.getBytes();
    }
}
