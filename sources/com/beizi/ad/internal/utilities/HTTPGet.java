package com.beizi.ad.internal.utilities;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import com.beizi.ad.R;
import com.beizi.ad.a.a.i;
import com.beizi.ad.internal.g;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/* loaded from: classes2.dex */
public abstract class HTTPGet extends AsyncTask<Void, Void, HTTPResponse> {
    private static String TAG = "HTTPGet";
    private boolean mBinaryStream;

    public HTTPGet(boolean z2) {
        this.mBinaryStream = z2;
    }

    private HttpURLConnection createConnection(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(false);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestMethod("GET");
        return httpURLConnection;
    }

    private void setConnectionParams(HttpURLConnection httpURLConnection) throws ProtocolException {
        httpURLConnection.setRequestProperty("User-Agent", g.a().f4182d);
    }

    public abstract String getUrl();

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.AsyncTask
    public abstract void onPostExecute(HTTPResponse hTTPResponse);

    @Override // android.os.AsyncTask
    public HTTPResponse doInBackground(Void... voidArr) {
        URL url;
        HTTPResponse hTTPResponse = new HTTPResponse();
        HttpURLConnection httpURLConnectionCreateConnection = null;
        try {
            try {
                url = new URL(getUrl());
            } catch (MalformedURLException unused) {
                hTTPResponse.setSucceeded(false);
                hTTPResponse.setErrorCode(HttpErrorCode.URL_FORMAT_ERROR);
                HaoboLog.e(HaoboLog.httpReqLogTag, HaoboLog.getString(R.string.http_url_malformed));
                if (0 != 0) {
                }
            } catch (IOException unused2) {
                hTTPResponse.setSucceeded(false);
                hTTPResponse.setErrorCode(HttpErrorCode.TRANSPORT_ERROR);
                HaoboLog.e(HaoboLog.httpReqLogTag, HaoboLog.getString(R.string.http_io));
                if (0 != 0) {
                }
            } catch (Exception unused3) {
                hTTPResponse.setSucceeded(false);
                hTTPResponse.setErrorCode(HttpErrorCode.TRANSPORT_ERROR);
                HaoboLog.e(HaoboLog.httpReqLogTag, HaoboLog.getString(R.string.http_io));
                if (0 != 0) {
                }
            }
            if (url.getHost() == null) {
                HaoboLog.w(HaoboLog.httpReqLogTag, "An HTTP request with an invalid URL was attempted.", new IllegalStateException("An HTTP request with an invalid URL was attempted."));
                hTTPResponse.setSucceeded(false);
                return hTTPResponse;
            }
            httpURLConnectionCreateConnection = createConnection(url);
            setConnectionParams(httpURLConnectionCreateConnection);
            httpURLConnectionCreateConnection.connect();
            int responseCode = httpURLConnectionCreateConnection.getResponseCode();
            i.a(TAG, "HTTPGet code:" + responseCode);
            InputStream inputStream = httpURLConnectionCreateConnection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = inputStream.read(bArr);
                if (i2 == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, i2);
            }
            inputStream.close();
            hTTPResponse.setHeaders(httpURLConnectionCreateConnection.getHeaderFields());
            hTTPResponse.setSucceeded(responseCode == 200 || responseCode == 302);
            if (responseCode == 302) {
                hTTPResponse.setCode(responseCode);
                hTTPResponse.setLocationUrl(httpURLConnectionCreateConnection.getHeaderField("Location"));
            }
            if (this.mBinaryStream) {
                hTTPResponse.setResponseBinaryBody(byteArrayOutputStream);
            } else {
                hTTPResponse.setResponseBody(byteArrayOutputStream.toString("UTF-8"));
            }
            httpURLConnectionCreateConnection.disconnect();
            return hTTPResponse;
        } catch (Throwable th) {
            if (0 != 0) {
                httpURLConnectionCreateConnection.disconnect();
            }
            throw th;
        }
    }

    @Override // android.os.AsyncTask
    @TargetApi(11)
    public void onCancelled(HTTPResponse hTTPResponse) {
        super.onCancelled((HTTPGet) null);
    }
}
