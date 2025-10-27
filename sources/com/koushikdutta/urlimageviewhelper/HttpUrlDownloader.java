package com.koushikdutta.urlimageviewhelper;

import android.content.Context;
import android.os.AsyncTask;
import com.koushikdutta.urlimageviewhelper.UrlDownloader;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.http.NameValuePair;

/* loaded from: classes4.dex */
public class HttpUrlDownloader implements UrlDownloader {
    private UrlImageViewHelper.RequestPropertiesCallback mRequestPropertiesCallback;

    @Override // com.koushikdutta.urlimageviewhelper.UrlDownloader
    public boolean allowCache() {
        return true;
    }

    @Override // com.koushikdutta.urlimageviewhelper.UrlDownloader
    public boolean canDownloadUrl(String str) {
        return str.startsWith("http");
    }

    @Override // com.koushikdutta.urlimageviewhelper.UrlDownloader
    public void download(final Context context, final String str, String str2, final UrlDownloader.UrlDownloaderCallback urlDownloaderCallback, final Runnable runnable) {
        UrlImageViewHelper.executeTask(new AsyncTask<Void, Void, Void>() { // from class: com.koushikdutta.urlimageviewhelper.HttpUrlDownloader.1
            @Override // android.os.AsyncTask
            public Void doInBackground(Void... voidArr) {
                HttpURLConnection httpURLConnection;
                ArrayList<NameValuePair> headersForRequest;
                try {
                    String headerField = str;
                    while (true) {
                        httpURLConnection = (HttpURLConnection) new URL(headerField).openConnection();
                        httpURLConnection.setInstanceFollowRedirects(true);
                        if (HttpUrlDownloader.this.mRequestPropertiesCallback != null && (headersForRequest = HttpUrlDownloader.this.mRequestPropertiesCallback.getHeadersForRequest(context, str)) != null) {
                            Iterator<NameValuePair> it = headersForRequest.iterator();
                            while (it.hasNext()) {
                                NameValuePair next = it.next();
                                httpURLConnection.addRequestProperty(next.getName(), next.getValue());
                            }
                        }
                        if (httpURLConnection.getResponseCode() != 302 && httpURLConnection.getResponseCode() != 301) {
                            break;
                        }
                        headerField = httpURLConnection.getHeaderField("Location");
                    }
                    if (httpURLConnection.getResponseCode() == 200) {
                        urlDownloaderCallback.onDownloadComplete(HttpUrlDownloader.this, httpURLConnection.getInputStream(), null);
                        return null;
                    }
                    UrlImageViewHelper.clog("Response Code: " + httpURLConnection.getResponseCode(), new Object[0]);
                    return null;
                } catch (Throwable th) {
                    th.printStackTrace();
                    return null;
                }
            }

            @Override // android.os.AsyncTask
            public void onPostExecute(Void r12) {
                runnable.run();
            }
        });
    }

    public UrlImageViewHelper.RequestPropertiesCallback getRequestPropertiesCallback() {
        return this.mRequestPropertiesCallback;
    }

    public void setRequestPropertiesCallback(UrlImageViewHelper.RequestPropertiesCallback requestPropertiesCallback) {
        this.mRequestPropertiesCallback = requestPropertiesCallback;
    }
}
