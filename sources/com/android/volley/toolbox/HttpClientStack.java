package com.android.volley.toolbox;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/* loaded from: classes2.dex */
public class HttpClientStack implements HttpStack {
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    protected final HttpClient mClient;

    public HttpClientStack(HttpClient httpClient) {
        this.mClient = httpClient;
    }

    private static void addHeaders(HttpUriRequest httpUriRequest, Map<String, String> map) {
        for (String str : map.keySet()) {
            httpUriRequest.setHeader(str, map.get(str));
        }
    }

    public static HttpUriRequest createHttpRequest(Request<?> request, Map<String, String> map) throws AuthFailureError {
        int method = request.getMethod();
        if (method == -1) {
            byte[] postBody = request.getPostBody();
            if (postBody == null) {
                return new HttpGet(request.getUrl());
            }
            HttpPost httpPost = new HttpPost(request.getUrl());
            httpPost.addHeader("Content-Type", request.getPostBodyContentType());
            httpPost.setEntity(new ByteArrayEntity(postBody));
            return httpPost;
        }
        if (method == 0) {
            return new HttpGet(request.getUrl());
        }
        if (method == 1) {
            HttpPost httpPost2 = new HttpPost(request.getUrl());
            httpPost2.addHeader("Content-Type", request.getBodyContentType());
            setEntityIfNonEmptyBody(httpPost2, request);
            return httpPost2;
        }
        if (method != 2) {
            if (method == 3) {
                return new HttpDelete(request.getUrl());
            }
            throw new IllegalStateException("Unknown request method.");
        }
        HttpPut httpPut = new HttpPut(request.getUrl());
        httpPut.addHeader("Content-Type", request.getBodyContentType());
        setEntityIfNonEmptyBody(httpPut, request);
        return httpPut;
    }

    private static List<NameValuePair> getPostParameterPairs(Map<String, String> map) {
        ArrayList arrayList = new ArrayList(map.size());
        for (String str : map.keySet()) {
            arrayList.add(new BasicNameValuePair(str, map.get(str)));
        }
        return arrayList;
    }

    private static void setEntityIfNonEmptyBody(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, Request<?> request) throws AuthFailureError {
        byte[] body = request.getBody();
        if (body != null) {
            httpEntityEnclosingRequestBase.setEntity(new ByteArrayEntity(body));
        }
    }

    public void onPrepareRequest(HttpUriRequest httpUriRequest) throws IOException {
    }

    @Override // com.android.volley.toolbox.HttpStack
    public HttpResponse performRequest(Request<?> request, Map<String, String> map) throws IOException, AuthFailureError {
        HttpUriRequest httpUriRequestCreateHttpRequest = createHttpRequest(request, map);
        addHeaders(httpUriRequestCreateHttpRequest, map);
        addHeaders(httpUriRequestCreateHttpRequest, request.getHeaders());
        onPrepareRequest(httpUriRequestCreateHttpRequest);
        HttpParams params = httpUriRequestCreateHttpRequest.getParams();
        int timeoutMs = request.getTimeoutMs();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, timeoutMs);
        return this.mClient.execute(httpUriRequestCreateHttpRequest);
    }
}
