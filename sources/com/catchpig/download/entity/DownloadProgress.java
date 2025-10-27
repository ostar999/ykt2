package com.catchpig.download.entity;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import z.a;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0006HÆ\u0003J1\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u0006HÖ\u0001J\t\u0010\u001e\u001a\u00020\u001fHÖ\u0001R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0010R\u001a\u0010\u0007\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\n\"\u0004\b\u0014\u0010\f¨\u0006 "}, d2 = {"Lcom/catchpig/download/entity/DownloadProgress;", "", "readLength", "", "countLength", "completeCount", "", "totalCount", "(JJII)V", "getCompleteCount", "()I", "setCompleteCount", "(I)V", "getCountLength", "()J", "setCountLength", "(J)V", "getReadLength", "setReadLength", "getTotalCount", "setTotalCount", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "download_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class DownloadProgress {
    private int completeCount;
    private long countLength;
    private long readLength;
    private int totalCount;

    public DownloadProgress(long j2, long j3, int i2, int i3) {
        this.readLength = j2;
        this.countLength = j3;
        this.completeCount = i2;
        this.totalCount = i3;
    }

    public static /* synthetic */ DownloadProgress copy$default(DownloadProgress downloadProgress, long j2, long j3, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            j2 = downloadProgress.readLength;
        }
        long j4 = j2;
        if ((i4 & 2) != 0) {
            j3 = downloadProgress.countLength;
        }
        long j5 = j3;
        if ((i4 & 4) != 0) {
            i2 = downloadProgress.completeCount;
        }
        int i5 = i2;
        if ((i4 & 8) != 0) {
            i3 = downloadProgress.totalCount;
        }
        return downloadProgress.copy(j4, j5, i5, i3);
    }

    /* renamed from: component1, reason: from getter */
    public final long getReadLength() {
        return this.readLength;
    }

    /* renamed from: component2, reason: from getter */
    public final long getCountLength() {
        return this.countLength;
    }

    /* renamed from: component3, reason: from getter */
    public final int getCompleteCount() {
        return this.completeCount;
    }

    /* renamed from: component4, reason: from getter */
    public final int getTotalCount() {
        return this.totalCount;
    }

    @NotNull
    public final DownloadProgress copy(long readLength, long countLength, int completeCount, int totalCount) {
        return new DownloadProgress(readLength, countLength, completeCount, totalCount);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DownloadProgress)) {
            return false;
        }
        DownloadProgress downloadProgress = (DownloadProgress) other;
        return this.readLength == downloadProgress.readLength && this.countLength == downloadProgress.countLength && this.completeCount == downloadProgress.completeCount && this.totalCount == downloadProgress.totalCount;
    }

    public final int getCompleteCount() {
        return this.completeCount;
    }

    public final long getCountLength() {
        return this.countLength;
    }

    public final long getReadLength() {
        return this.readLength;
    }

    public final int getTotalCount() {
        return this.totalCount;
    }

    public int hashCode() {
        return (((((a.a(this.readLength) * 31) + a.a(this.countLength)) * 31) + this.completeCount) * 31) + this.totalCount;
    }

    public final void setCompleteCount(int i2) {
        this.completeCount = i2;
    }

    public final void setCountLength(long j2) {
        this.countLength = j2;
    }

    public final void setReadLength(long j2) {
        this.readLength = j2;
    }

    public final void setTotalCount(int i2) {
        this.totalCount = i2;
    }

    @NotNull
    public String toString() {
        return "DownloadProgress(readLength=" + this.readLength + ", countLength=" + this.countLength + ", completeCount=" + this.completeCount + ", totalCount=" + this.totalCount + ")";
    }
}
