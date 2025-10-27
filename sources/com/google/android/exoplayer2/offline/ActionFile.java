package com.google.android.exoplayer2.offline;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.offline.DownloadRequest;
import com.google.android.exoplayer2.util.AtomicFile;
import com.google.android.exoplayer2.util.Util;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@Deprecated
/* loaded from: classes3.dex */
final class ActionFile {
    private static final String DOWNLOAD_TYPE_DASH = "dash";
    private static final String DOWNLOAD_TYPE_HLS = "hls";
    private static final String DOWNLOAD_TYPE_PROGRESSIVE = "progressive";
    private static final String DOWNLOAD_TYPE_SS = "ss";
    private static final int VERSION = 0;
    private final AtomicFile atomicFile;

    public ActionFile(File file) {
        this.atomicFile = new AtomicFile(file);
    }

    private static String generateDownloadId(Uri uri, @Nullable String str) {
        return str != null ? str : uri.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String inferMimeType(java.lang.String r4) {
        /*
            int r0 = r4.hashCode()
            r1 = 3680(0xe60, float:5.157E-42)
            r2 = 2
            r3 = 1
            if (r0 == r1) goto L38
            r1 = 103407(0x193ef, float:1.44904E-40)
            if (r0 == r1) goto L2e
            r1 = 3075986(0x2eef92, float:4.310374E-39)
            if (r0 == r1) goto L24
            r1 = 1131547531(0x43720b8b, float:242.04509)
            if (r0 == r1) goto L1a
            goto L42
        L1a:
            java.lang.String r0 = "progressive"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L42
            r4 = 3
            goto L43
        L24:
            java.lang.String r0 = "dash"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L42
            r4 = 0
            goto L43
        L2e:
            java.lang.String r0 = "hls"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L42
            r4 = r3
            goto L43
        L38:
            java.lang.String r0 = "ss"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L42
            r4 = r2
            goto L43
        L42:
            r4 = -1
        L43:
            if (r4 == 0) goto L52
            if (r4 == r3) goto L4f
            if (r4 == r2) goto L4c
            java.lang.String r4 = "video/x-unknown"
            return r4
        L4c:
            java.lang.String r4 = "application/vnd.ms-sstr+xml"
            return r4
        L4f:
            java.lang.String r4 = "application/x-mpegURL"
            return r4
        L52:
            java.lang.String r4 = "application/dash+xml"
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.ActionFile.inferMimeType(java.lang.String):java.lang.String");
    }

    private static DownloadRequest readDownloadRequest(DataInputStream dataInputStream) throws IOException {
        byte[] bArr;
        String utf = dataInputStream.readUTF();
        int i2 = dataInputStream.readInt();
        Uri uri = Uri.parse(dataInputStream.readUTF());
        boolean z2 = dataInputStream.readBoolean();
        int i3 = dataInputStream.readInt();
        String utf2 = null;
        if (i3 != 0) {
            bArr = new byte[i3];
            dataInputStream.readFully(bArr);
        } else {
            bArr = null;
        }
        boolean z3 = true;
        boolean z4 = i2 == 0 && DOWNLOAD_TYPE_PROGRESSIVE.equals(utf);
        ArrayList arrayList = new ArrayList();
        if (!z4) {
            int i4 = dataInputStream.readInt();
            for (int i5 = 0; i5 < i4; i5++) {
                arrayList.add(readKey(utf, i2, dataInputStream));
            }
        }
        if (i2 >= 2 || (!DOWNLOAD_TYPE_DASH.equals(utf) && !DOWNLOAD_TYPE_HLS.equals(utf) && !DOWNLOAD_TYPE_SS.equals(utf))) {
            z3 = false;
        }
        if (!z3 && dataInputStream.readBoolean()) {
            utf2 = dataInputStream.readUTF();
        }
        String strGenerateDownloadId = i2 < 3 ? generateDownloadId(uri, utf2) : dataInputStream.readUTF();
        if (z2) {
            throw new DownloadRequest.UnsupportedRequestException();
        }
        return new DownloadRequest.Builder(strGenerateDownloadId, uri).setMimeType(inferMimeType(utf)).setStreamKeys(arrayList).setCustomCacheKey(utf2).setData(bArr).build();
    }

    private static StreamKey readKey(String str, int i2, DataInputStream dataInputStream) throws IOException {
        int i3;
        int i4;
        int i5;
        if ((DOWNLOAD_TYPE_HLS.equals(str) || DOWNLOAD_TYPE_SS.equals(str)) && i2 == 0) {
            i3 = dataInputStream.readInt();
            i4 = dataInputStream.readInt();
            i5 = 0;
        } else {
            int i6 = dataInputStream.readInt();
            int i7 = dataInputStream.readInt();
            int i8 = dataInputStream.readInt();
            i5 = i6;
            i3 = i7;
            i4 = i8;
        }
        return new StreamKey(i5, i3, i4);
    }

    public void delete() {
        this.atomicFile.delete();
    }

    public boolean exists() {
        return this.atomicFile.exists();
    }

    public DownloadRequest[] load() throws IOException {
        if (!exists()) {
            return new DownloadRequest[0];
        }
        try {
            InputStream inputStreamOpenRead = this.atomicFile.openRead();
            DataInputStream dataInputStream = new DataInputStream(inputStreamOpenRead);
            int i2 = dataInputStream.readInt();
            if (i2 > 0) {
                StringBuilder sb = new StringBuilder(44);
                sb.append("Unsupported action file version: ");
                sb.append(i2);
                throw new IOException(sb.toString());
            }
            int i3 = dataInputStream.readInt();
            ArrayList arrayList = new ArrayList();
            for (int i4 = 0; i4 < i3; i4++) {
                try {
                    arrayList.add(readDownloadRequest(dataInputStream));
                } catch (DownloadRequest.UnsupportedRequestException unused) {
                }
            }
            DownloadRequest[] downloadRequestArr = (DownloadRequest[]) arrayList.toArray(new DownloadRequest[0]);
            Util.closeQuietly(inputStreamOpenRead);
            return downloadRequestArr;
        } catch (Throwable th) {
            Util.closeQuietly(null);
            throw th;
        }
    }
}
