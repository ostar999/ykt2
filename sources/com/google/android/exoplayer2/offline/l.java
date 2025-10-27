package com.google.android.exoplayer2.offline;

import com.google.android.exoplayer2.offline.DownloadManager;
import java.util.Comparator;

/* loaded from: classes3.dex */
public final /* synthetic */ class l implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return DownloadManager.InternalHandler.compareStartTimes((Download) obj, (Download) obj2);
    }
}
