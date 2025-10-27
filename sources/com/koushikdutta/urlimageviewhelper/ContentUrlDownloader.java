package com.koushikdutta.urlimageviewhelper;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import com.koushikdutta.urlimageviewhelper.UrlDownloader;

/* loaded from: classes4.dex */
public class ContentUrlDownloader implements UrlDownloader {
    @Override // com.koushikdutta.urlimageviewhelper.UrlDownloader
    public boolean allowCache() {
        return false;
    }

    @Override // com.koushikdutta.urlimageviewhelper.UrlDownloader
    public boolean canDownloadUrl(String str) {
        return str.startsWith("content");
    }

    @Override // com.koushikdutta.urlimageviewhelper.UrlDownloader
    public void download(final Context context, final String str, String str2, final UrlDownloader.UrlDownloaderCallback urlDownloaderCallback, final Runnable runnable) {
        UrlImageViewHelper.executeTask(new AsyncTask<Void, Void, Void>() { // from class: com.koushikdutta.urlimageviewhelper.ContentUrlDownloader.1
            @Override // android.os.AsyncTask
            public Void doInBackground(Void... voidArr) {
                try {
                    urlDownloaderCallback.onDownloadComplete(ContentUrlDownloader.this, context.getContentResolver().openInputStream(Uri.parse(str)), null);
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
}
