package com.google.android.exoplayer2.offline;

import com.google.android.exoplayer2.util.Assertions;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class Download {
    public static final int FAILURE_REASON_NONE = 0;
    public static final int FAILURE_REASON_UNKNOWN = 1;
    public static final int STATE_COMPLETED = 3;
    public static final int STATE_DOWNLOADING = 2;
    public static final int STATE_FAILED = 4;
    public static final int STATE_QUEUED = 0;
    public static final int STATE_REMOVING = 5;
    public static final int STATE_RESTARTING = 7;
    public static final int STATE_STOPPED = 1;
    public static final int STOP_REASON_NONE = 0;
    public final long contentLength;
    public final int failureReason;
    final DownloadProgress progress;
    public final DownloadRequest request;
    public final long startTimeMs;
    public final int state;
    public final int stopReason;
    public final long updateTimeMs;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface FailureReason {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public Download(DownloadRequest downloadRequest, int i2, long j2, long j3, long j4, int i3, int i4) {
        this(downloadRequest, i2, j2, j3, j4, i3, i4, new DownloadProgress());
    }

    public long getBytesDownloaded() {
        return this.progress.bytesDownloaded;
    }

    public float getPercentDownloaded() {
        return this.progress.percentDownloaded;
    }

    public boolean isTerminalState() {
        int i2 = this.state;
        return i2 == 3 || i2 == 4;
    }

    public Download(DownloadRequest downloadRequest, int i2, long j2, long j3, long j4, int i3, int i4, DownloadProgress downloadProgress) {
        Assertions.checkNotNull(downloadProgress);
        Assertions.checkArgument((i4 == 0) == (i2 != 4));
        if (i3 != 0) {
            Assertions.checkArgument((i2 == 2 || i2 == 0) ? false : true);
        }
        this.request = downloadRequest;
        this.state = i2;
        this.startTimeMs = j2;
        this.updateTimeMs = j3;
        this.contentLength = j4;
        this.stopReason = i3;
        this.failureReason = i4;
        this.progress = downloadProgress;
    }
}
