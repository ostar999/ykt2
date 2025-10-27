package com.catchpig.download.callback;

import com.plv.socket.event.linkmic.PLVUpdateMicSiteEvent;
import kotlin.Metadata;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH&¨\u0006\t"}, d2 = {"Lcom/catchpig/download/callback/DownloadProgressListener;", "", PLVUpdateMicSiteEvent.EVENT_NAME, "", "read", "", "count", "done", "", "download_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface DownloadProgressListener {
    void update(long read, long count, boolean done);
}
