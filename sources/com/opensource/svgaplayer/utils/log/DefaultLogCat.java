package com.opensource.svgaplayer.utils.log;

import android.util.Log;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.opensource.svgaplayer.BuildConfig;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J$\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016J\u0018\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\u0018\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\u0018\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016¨\u0006\r"}, d2 = {"Lcom/opensource/svgaplayer/utils/log/DefaultLogCat;", "Lcom/opensource/svgaplayer/utils/log/ILogger;", "()V", "debug", "", "tag", "", "msg", "error", "", AliyunLogCommon.LogLevel.INFO, "verbose", AliyunLogCommon.LogLevel.WARN, BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class DefaultLogCat implements ILogger {
    @Override // com.opensource.svgaplayer.utils.log.ILogger
    public void debug(@NotNull String tag, @NotNull String msg) {
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        Log.d(tag, msg);
    }

    @Override // com.opensource.svgaplayer.utils.log.ILogger
    public void error(@NotNull String tag, @Nullable String msg, @Nullable Throwable error) {
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        Log.e(tag, msg, error);
    }

    @Override // com.opensource.svgaplayer.utils.log.ILogger
    public void info(@NotNull String tag, @NotNull String msg) {
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        Log.i(tag, msg);
    }

    @Override // com.opensource.svgaplayer.utils.log.ILogger
    public void verbose(@NotNull String tag, @NotNull String msg) {
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        Log.v(tag, msg);
    }

    @Override // com.opensource.svgaplayer.utils.log.ILogger
    public void warn(@NotNull String tag, @NotNull String msg) {
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        Log.w(tag, msg);
    }
}
