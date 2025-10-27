package com.android.volley.toolbox;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class JsonObjectPostRequest extends Request<JSONObject> {
    private Response.Listener<JSONObject> mListener;
    private Map<String, String> mMap;

    public JsonObjectPostRequest(String str, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, Map map) {
        super(1, str, errorListener);
        this.mListener = listener;
        this.mMap = map;
    }

    @Override // com.android.volley.Request
    public Map<String, String> getParams() throws AuthFailureError {
        return this.mMap;
    }

    @Override // com.android.volley.Request
    public Response<JSONObject> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            return Response.success(new JSONObject(new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers))), HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e2) {
            return Response.error(new ParseError(e2));
        } catch (JSONException e3) {
            return Response.error(new ParseError(e3));
        }
    }

    @Override // com.android.volley.Request
    public void deliverResponse(JSONObject jSONObject, String str) {
        this.mListener.onResponse(jSONObject, str);
    }
}
