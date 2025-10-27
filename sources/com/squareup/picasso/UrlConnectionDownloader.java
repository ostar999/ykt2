package com.squareup.picasso;

import android.content.Context;
import android.net.Uri;
import android.net.http.HttpResponseCache;
import com.squareup.picasso.Downloader;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/* loaded from: classes6.dex */
public class UrlConnectionDownloader implements Downloader {
    static final String RESPONSE_SOURCE = "X-Android-Response-Source";
    static volatile Object cache;
    private static final Object lock = new Object();
    private final Context context;

    public static class ResponseCacheIcs {
        private ResponseCacheIcs() {
        }

        public static void close(Object obj) throws IOException {
            try {
                ((HttpResponseCache) obj).close();
            } catch (IOException unused) {
            }
        }

        public static Object install(Context context) throws IOException {
            File fileCreateDefaultCacheDir = Utils.createDefaultCacheDir(context);
            HttpResponseCache installed = HttpResponseCache.getInstalled();
            return installed == null ? HttpResponseCache.install(fileCreateDefaultCacheDir, Utils.calculateDiskCacheSize(fileCreateDefaultCacheDir)) : installed;
        }
    }

    public UrlConnectionDownloader(Context context) {
        this.context = context.getApplicationContext();
    }

    private static void installCacheIfNeeded(Context context) {
        if (cache == null) {
            try {
                synchronized (lock) {
                    if (cache == null) {
                        cache = ResponseCacheIcs.install(context);
                    }
                }
            } catch (IOException unused) {
            }
        }
    }

    @Override // com.squareup.picasso.Downloader
    public Downloader.Response load(Uri uri, boolean z2) throws IOException {
        installCacheIfNeeded(this.context);
        HttpURLConnection httpURLConnectionOpenConnection = openConnection(uri);
        httpURLConnectionOpenConnection.setUseCaches(true);
        if (z2) {
            httpURLConnectionOpenConnection.setRequestProperty("Cache-Control", "only-if-cached,max-age=2147483647");
        }
        int responseCode = httpURLConnectionOpenConnection.getResponseCode();
        if (responseCode < 300) {
            return new Downloader.Response(httpURLConnectionOpenConnection.getInputStream(), Utils.parseResponseSourceHeader(httpURLConnectionOpenConnection.getHeaderField(RESPONSE_SOURCE)), httpURLConnectionOpenConnection.getHeaderFieldInt("Content-Length", -1));
        }
        httpURLConnectionOpenConnection.disconnect();
        throw new Downloader.ResponseException(responseCode + " " + httpURLConnectionOpenConnection.getResponseMessage());
    }

    public HttpURLConnection openConnection(Uri uri) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(uri.toString()).openConnection();
        httpURLConnection.setConnectTimeout(15000);
        httpURLConnection.setReadTimeout(20000);
        return httpURLConnection;
    }

    @Override // com.squareup.picasso.Downloader
    public void shutdown() throws IOException {
        if (cache != null) {
            ResponseCacheIcs.close(cache);
        }
    }
}
