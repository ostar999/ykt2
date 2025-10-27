package com.google.android.exoplayer2.offline;

/* loaded from: classes3.dex */
public final /* synthetic */ class b {
    public static boolean a(DownloadCursor downloadCursor) {
        return downloadCursor.getCount() == 0 || downloadCursor.getPosition() == downloadCursor.getCount();
    }

    public static boolean b(DownloadCursor downloadCursor) {
        return downloadCursor.getCount() == 0 || downloadCursor.getPosition() == -1;
    }

    public static boolean c(DownloadCursor downloadCursor) {
        return downloadCursor.getPosition() == 0 && downloadCursor.getCount() != 0;
    }

    public static boolean d(DownloadCursor downloadCursor) {
        int count = downloadCursor.getCount();
        return downloadCursor.getPosition() == count + (-1) && count != 0;
    }

    public static boolean e(DownloadCursor downloadCursor) {
        return downloadCursor.moveToPosition(0);
    }

    public static boolean f(DownloadCursor downloadCursor) {
        return downloadCursor.moveToPosition(downloadCursor.getCount() - 1);
    }

    public static boolean g(DownloadCursor downloadCursor) {
        return downloadCursor.moveToPosition(downloadCursor.getPosition() + 1);
    }

    public static boolean h(DownloadCursor downloadCursor) {
        return downloadCursor.moveToPosition(downloadCursor.getPosition() - 1);
    }
}
