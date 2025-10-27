package com.catchpig.utils.ext;

import com.catchpig.utils.LogUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002\u001a\u0012\u0010\u0004\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002\u001a\u0012\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002\u001a\u0012\u0010\u0007\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002¨\u0006\b"}, d2 = {"logd", "", "", "tag", "loge", "logi", "logv", "logw", "utils_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class LogExtKt {
    public static final void logd(@NotNull String str, @NotNull String tag) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(tag, "tag");
        LogUtils.INSTANCE.getInstance().dExt$utils_release(tag, str);
    }

    public static final void loge(@NotNull String str, @NotNull String tag) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(tag, "tag");
        LogUtils.INSTANCE.getInstance().eExt$utils_release(tag, str);
    }

    public static final void logi(@NotNull String str, @NotNull String tag) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(tag, "tag");
        LogUtils.INSTANCE.getInstance().iExt$utils_release(tag, str);
    }

    public static final void logv(@NotNull String str, @NotNull String tag) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(tag, "tag");
        LogUtils.INSTANCE.getInstance().vExt$utils_release(tag, str);
    }

    public static final void logw(@NotNull String str, @NotNull String tag) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(tag, "tag");
        LogUtils.INSTANCE.getInstance().wExt$utils_release(tag, str);
    }
}
