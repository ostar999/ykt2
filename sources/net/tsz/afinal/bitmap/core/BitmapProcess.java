package net.tsz.afinal.bitmap.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import net.tsz.afinal.bitmap.download.Downloader;

/* loaded from: classes9.dex */
public class BitmapProcess {
    private static final int BYTESBUFFE_POOL_SIZE = 4;
    private BitmapCache mCache;
    private Downloader mDownloader;
    private static final int BYTESBUFFER_SIZE = 204800;
    private static final BytesBufferPool sMicroThumbBufferPool = new BytesBufferPool(4, BYTESBUFFER_SIZE);

    public BitmapProcess(Downloader downloader, BitmapCache bitmapCache) {
        this.mDownloader = downloader;
        this.mCache = bitmapCache;
    }

    public Bitmap getBitmap(String str, BitmapDisplayConfig bitmapDisplayConfig) {
        byte[] bArrDownload;
        Bitmap fromDisk = getFromDisk(str, bitmapDisplayConfig);
        if (fromDisk != null || (bArrDownload = this.mDownloader.download(str)) == null || bArrDownload.length <= 0) {
            return fromDisk;
        }
        if (bitmapDisplayConfig == null) {
            return BitmapFactory.decodeByteArray(bArrDownload, 0, bArrDownload.length);
        }
        Bitmap bitmapDecodeSampledBitmapFromByteArray = BitmapDecoder.decodeSampledBitmapFromByteArray(bArrDownload, 0, bArrDownload.length, bitmapDisplayConfig.getBitmapWidth(), bitmapDisplayConfig.getBitmapHeight());
        this.mCache.addToDiskCache(str, bArrDownload);
        return bitmapDecodeSampledBitmapFromByteArray;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.graphics.Bitmap getFromDisk(java.lang.String r6, net.tsz.afinal.bitmap.core.BitmapDisplayConfig r7) {
        /*
            r5 = this;
            net.tsz.afinal.bitmap.core.BytesBufferPool r0 = net.tsz.afinal.bitmap.core.BitmapProcess.sMicroThumbBufferPool
            net.tsz.afinal.bitmap.core.BytesBufferPool$BytesBuffer r1 = r0.get()
            net.tsz.afinal.bitmap.core.BitmapCache r2 = r5.mCache     // Catch: java.lang.Throwable -> L33
            boolean r6 = r2.getImageData(r6, r1)     // Catch: java.lang.Throwable -> L33
            if (r6 == 0) goto L2e
            int r6 = r1.length     // Catch: java.lang.Throwable -> L33
            int r2 = r1.offset     // Catch: java.lang.Throwable -> L33
            int r3 = r6 - r2
            if (r3 <= 0) goto L2e
            if (r7 == 0) goto L27
            byte[] r3 = r1.data     // Catch: java.lang.Throwable -> L33
            int r4 = r7.getBitmapWidth()     // Catch: java.lang.Throwable -> L33
            int r7 = r7.getBitmapHeight()     // Catch: java.lang.Throwable -> L33
            android.graphics.Bitmap r6 = net.tsz.afinal.bitmap.core.BitmapDecoder.decodeSampledBitmapFromByteArray(r3, r2, r6, r4, r7)     // Catch: java.lang.Throwable -> L33
            goto L2f
        L27:
            byte[] r7 = r1.data     // Catch: java.lang.Throwable -> L33
            android.graphics.Bitmap r6 = android.graphics.BitmapFactory.decodeByteArray(r7, r2, r6)     // Catch: java.lang.Throwable -> L33
            goto L2f
        L2e:
            r6 = 0
        L2f:
            r0.recycle(r1)
            return r6
        L33:
            r6 = move-exception
            net.tsz.afinal.bitmap.core.BytesBufferPool r7 = net.tsz.afinal.bitmap.core.BitmapProcess.sMicroThumbBufferPool
            r7.recycle(r1)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: net.tsz.afinal.bitmap.core.BitmapProcess.getFromDisk(java.lang.String, net.tsz.afinal.bitmap.core.BitmapDisplayConfig):android.graphics.Bitmap");
    }
}
