package com.squareup.picasso;

import android.content.Context;
import android.net.Uri;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;
import com.squareup.picasso.Downloader;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/* loaded from: classes6.dex */
public class OkHttpDownloader implements Downloader {
    static final String RESPONSE_SOURCE_ANDROID = "X-Android-Response-Source";
    static final String RESPONSE_SOURCE_OKHTTP = "OkHttp-Response-Source";
    private final OkUrlFactory urlFactory;

    public OkHttpDownloader(Context context) {
        this(Utils.createDefaultCacheDir(context));
    }

    public OkHttpClient getClient() {
        return this.urlFactory.client();
    }

    @Override // com.squareup.picasso.Downloader
    public Downloader.Response load(Uri uri, boolean z2) throws IOException {
        HttpURLConnection httpURLConnectionOpenConnection = openConnection(uri);
        httpURLConnectionOpenConnection.setUseCaches(true);
        if (z2) {
            httpURLConnectionOpenConnection.setRequestProperty("Cache-Control", "only-if-cached,max-age=2147483647");
        }
        int responseCode = httpURLConnectionOpenConnection.getResponseCode();
        if (responseCode < 300) {
            String headerField = httpURLConnectionOpenConnection.getHeaderField(RESPONSE_SOURCE_OKHTTP);
            if (headerField == null) {
                headerField = httpURLConnectionOpenConnection.getHeaderField(RESPONSE_SOURCE_ANDROID);
            }
            return new Downloader.Response(httpURLConnectionOpenConnection.getInputStream(), Utils.parseResponseSourceHeader(headerField), httpURLConnectionOpenConnection.getHeaderFieldInt("Content-Length", -1));
        }
        httpURLConnectionOpenConnection.disconnect();
        throw new Downloader.ResponseException(responseCode + " " + httpURLConnectionOpenConnection.getResponseMessage());
    }

    public HttpURLConnection openConnection(Uri uri) throws IOException {
        HttpURLConnection httpURLConnectionOpen = this.urlFactory.open(new URL(uri.toString()));
        httpURLConnectionOpen.setConnectTimeout(15000);
        httpURLConnectionOpen.setReadTimeout(20000);
        return httpURLConnectionOpen;
    }

    @Override // com.squareup.picasso.Downloader
    public void shutdown() {
        com.squareup.okhttp.Cache cache = this.urlFactory.client().getCache();
        if (cache != null) {
            try {
                cache.close();
            } catch (IOException unused) {
            }
        }
    }

    public OkHttpDownloader(File file) {
        this(file, Utils.calculateDiskCacheSize(file));
    }

    public OkHttpDownloader(Context context, long j2) {
        this(Utils.createDefaultCacheDir(context), j2);
    }

    public OkHttpDownloader(File file, long j2) {
        this(new OkHttpClient());
        try {
            this.urlFactory.client().setCache(new com.squareup.okhttp.Cache(file, j2));
        } catch (IOException unused) {
        }
    }

    public OkHttpDownloader(OkHttpClient okHttpClient) {
        this.urlFactory = new OkUrlFactory(okHttpClient);
    }
}
