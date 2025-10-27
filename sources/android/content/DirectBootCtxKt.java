package android.content;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\"*\u0010\u0000\u001a\u0010\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u00020\u00018\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\"\u0012\u0010\b\u001a\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"deviceProtectedStorageCtx", "Lkotlin/Lazy;", "Landroid/content/Context;", "kotlin.jvm.PlatformType", "getDeviceProtectedStorageCtx$annotations", "()V", "getDeviceProtectedStorageCtx", "()Lkotlin/Lazy;", "directBootCtx", "getDirectBootCtx", "()Landroid/content/Context;", "splitties-appctx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class DirectBootCtxKt {

    @RequiresApi(24)
    @NotNull
    private static final Lazy<Context> deviceProtectedStorageCtx = LazyKt__LazyJVMKt.lazy(new Function0<Context>() { // from class: splitties.init.DirectBootCtxKt$deviceProtectedStorageCtx$1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final android.content.Context invoke() {
            return AppCtxKt.getAppCtx().createDeviceProtectedStorageContext();
        }
    });

    @NotNull
    public static final Lazy<Context> getDeviceProtectedStorageCtx() {
        return deviceProtectedStorageCtx;
    }

    @PublishedApi
    public static /* synthetic */ void getDeviceProtectedStorageCtx$annotations() {
    }

    @NotNull
    public static final Context getDirectBootCtx() {
        if (Build.VERSION.SDK_INT < 24) {
            return AppCtxKt.getAppCtx();
        }
        Context value = getDeviceProtectedStorageCtx().getValue();
        Intrinsics.checkNotNullExpressionValue(value, "deviceProtectedStorageCtx.value");
        return value;
    }
}
