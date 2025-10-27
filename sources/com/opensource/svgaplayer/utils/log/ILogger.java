package com.opensource.svgaplayer.utils.log;

import com.aliyun.vod.log.core.AliyunLogCommon;
import com.opensource.svgaplayer.BuildConfig;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&J$\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&J\u0018\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&J\u0018\u0010\n\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&J\u0018\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&¨\u0006\f"}, d2 = {"Lcom/opensource/svgaplayer/utils/log/ILogger;", "", "debug", "", "tag", "", "msg", "error", "", AliyunLogCommon.LogLevel.INFO, "verbose", AliyunLogCommon.LogLevel.WARN, BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public interface ILogger {
    void debug(@NotNull String tag, @NotNull String msg);

    void error(@NotNull String tag, @Nullable String msg, @Nullable Throwable error);

    void info(@NotNull String tag, @NotNull String msg);

    void verbose(@NotNull String tag, @NotNull String msg);

    void warn(@NotNull String tag, @NotNull String msg);
}
