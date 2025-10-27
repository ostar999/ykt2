package com.koushikdutta.urlimageviewhelper;

import android.content.Context;
import android.os.AsyncTask;
import com.koushikdutta.urlimageviewhelper.UrlDownloader;
import java.io.File;
import java.net.URI;

/* loaded from: classes4.dex */
public class FileUrlDownloader implements UrlDownloader {
    @Override // com.koushikdutta.urlimageviewhelper.UrlDownloader
    public boolean allowCache() {
        return false;
    }

    @Override // com.koushikdutta.urlimageviewhelper.UrlDownloader
    public boolean canDownloadUrl(String str) {
        return str.startsWith("file:/");
    }

    @Override // com.koushikdutta.urlimageviewhelper.UrlDownloader
    public void download(Context context, final String str, String str2, final UrlDownloader.UrlDownloaderCallback urlDownloaderCallback, final Runnable runnable) {
        UrlImageViewHelper.executeTask(new AsyncTask<Void, Void, Void>() { // from class: com.koushikdutta.urlimageviewhelper.FileUrlDownloader.1
            @Override // android.os.AsyncTask
            public Void doInBackground(Void... voidArr) {
                try {
                    urlDownloaderCallback.onDownloadComplete(FileUrlDownloader.this, null, new File(new URI(str)).getAbsolutePath());
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
