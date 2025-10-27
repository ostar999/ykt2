package com.azhon.appupdate.base.bean;

import com.aliyun.vod.log.struct.AliyunLogKey;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0005\u0003\u0004\u0005\u0006\u0007B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0005\b\t\n\u000b\f¨\u0006\r"}, d2 = {"Lcom/azhon/appupdate/base/bean/DownloadStatus;", "", "()V", "Cancel", "Done", "Downloading", "Error", "Start", "Lcom/azhon/appupdate/base/bean/DownloadStatus$Cancel;", "Lcom/azhon/appupdate/base/bean/DownloadStatus$Done;", "Lcom/azhon/appupdate/base/bean/DownloadStatus$Downloading;", "Lcom/azhon/appupdate/base/bean/DownloadStatus$Error;", "Lcom/azhon/appupdate/base/bean/DownloadStatus$Start;", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public abstract class DownloadStatus {

    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/azhon/appupdate/base/bean/DownloadStatus$Cancel;", "Lcom/azhon/appupdate/base/bean/DownloadStatus;", "()V", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Cancel extends DownloadStatus {

        @NotNull
        public static final Cancel INSTANCE = new Cancel();

        private Cancel() {
            super(null);
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/azhon/appupdate/base/bean/DownloadStatus$Done;", "Lcom/azhon/appupdate/base/bean/DownloadStatus;", "apk", "Ljava/io/File;", "(Ljava/io/File;)V", "getApk", "()Ljava/io/File;", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Done extends DownloadStatus {

        @NotNull
        private final File apk;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Done(@NotNull File apk) {
            super(null);
            Intrinsics.checkNotNullParameter(apk, "apk");
            this.apk = apk;
        }

        @NotNull
        public final File getApk() {
            return this.apk;
        }
    }

    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0013"}, d2 = {"Lcom/azhon/appupdate/base/bean/DownloadStatus$Downloading;", "Lcom/azhon/appupdate/base/bean/DownloadStatus;", "max", "", "progress", "(II)V", "getMax", "()I", "getProgress", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "toString", "", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final /* data */ class Downloading extends DownloadStatus {
        private final int max;
        private final int progress;

        public Downloading(int i2, int i3) {
            super(null);
            this.max = i2;
            this.progress = i3;
        }

        public static /* synthetic */ Downloading copy$default(Downloading downloading, int i2, int i3, int i4, Object obj) {
            if ((i4 & 1) != 0) {
                i2 = downloading.max;
            }
            if ((i4 & 2) != 0) {
                i3 = downloading.progress;
            }
            return downloading.copy(i2, i3);
        }

        /* renamed from: component1, reason: from getter */
        public final int getMax() {
            return this.max;
        }

        /* renamed from: component2, reason: from getter */
        public final int getProgress() {
            return this.progress;
        }

        @NotNull
        public final Downloading copy(int max, int progress) {
            return new Downloading(max, progress);
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Downloading)) {
                return false;
            }
            Downloading downloading = (Downloading) other;
            return this.max == downloading.max && this.progress == downloading.progress;
        }

        public final int getMax() {
            return this.max;
        }

        public final int getProgress() {
            return this.progress;
        }

        public int hashCode() {
            return (this.max * 31) + this.progress;
        }

        @NotNull
        public String toString() {
            return "Downloading(max=" + this.max + ", progress=" + this.progress + ')';
        }
    }

    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lcom/azhon/appupdate/base/bean/DownloadStatus$Error;", "Lcom/azhon/appupdate/base/bean/DownloadStatus;", AliyunLogKey.KEY_EVENT, "", "(Ljava/lang/Throwable;)V", "getE", "()Ljava/lang/Throwable;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final /* data */ class Error extends DownloadStatus {

        @NotNull
        private final Throwable e;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Error(@NotNull Throwable e2) {
            super(null);
            Intrinsics.checkNotNullParameter(e2, "e");
            this.e = e2;
        }

        public static /* synthetic */ Error copy$default(Error error, Throwable th, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                th = error.e;
            }
            return error.copy(th);
        }

        @NotNull
        /* renamed from: component1, reason: from getter */
        public final Throwable getE() {
            return this.e;
        }

        @NotNull
        public final Error copy(@NotNull Throwable e2) {
            Intrinsics.checkNotNullParameter(e2, "e");
            return new Error(e2);
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Error) && Intrinsics.areEqual(this.e, ((Error) other).e);
        }

        @NotNull
        public final Throwable getE() {
            return this.e;
        }

        public int hashCode() {
            return this.e.hashCode();
        }

        @NotNull
        public String toString() {
            return "Error(e=" + this.e + ')';
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/azhon/appupdate/base/bean/DownloadStatus$Start;", "Lcom/azhon/appupdate/base/bean/DownloadStatus;", "()V", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Start extends DownloadStatus {

        @NotNull
        public static final Start INSTANCE = new Start();

        private Start() {
            super(null);
        }
    }

    private DownloadStatus() {
    }

    public /* synthetic */ DownloadStatus(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
