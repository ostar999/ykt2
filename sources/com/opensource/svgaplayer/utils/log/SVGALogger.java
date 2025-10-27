package com.opensource.svgaplayer.utils.log;

import com.opensource.svgaplayer.BuildConfig;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006J\u000e\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0006J\u0006\u0010\u0003\u001a\u00020\u0004J\u000e\u0010\n\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/opensource/svgaplayer/utils/log/SVGALogger;", "", "()V", "isLogEnabled", "", "mLogger", "Lcom/opensource/svgaplayer/utils/log/ILogger;", "getSVGALogger", "injectSVGALoggerImp", "logImp", "setLogEnabled", "isEnabled", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class SVGALogger {
    private static boolean isLogEnabled;
    public static final SVGALogger INSTANCE = new SVGALogger();
    private static ILogger mLogger = new DefaultLogCat();

    private SVGALogger() {
    }

    @Nullable
    public final ILogger getSVGALogger() {
        return mLogger;
    }

    @NotNull
    public final SVGALogger injectSVGALoggerImp(@NotNull ILogger logImp) {
        Intrinsics.checkParameterIsNotNull(logImp, "logImp");
        mLogger = logImp;
        return this;
    }

    public final boolean isLogEnabled() {
        return isLogEnabled;
    }

    @NotNull
    public final SVGALogger setLogEnabled(boolean isEnabled) {
        isLogEnabled = isEnabled;
        return this;
    }
}
