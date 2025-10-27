package com.catchpig.utils.ext;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u000e\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005H\n¢\u0006\u0002\b\u0007"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Landroid/app/Activity;", "result", "Landroidx/activity/result/ActivityResult;", "kotlin.jvm.PlatformType", "onActivityResult"}, k = 3, mv = {1, 8, 0}, xi = 176)
@SourceDebugExtension({"SMAP\nActivityExt.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ActivityExt.kt\ncom/catchpig/utils/ext/ActivityExtKt$startKtActivity$3$1\n*L\n1#1,92:1\n*E\n"})
/* loaded from: classes.dex */
public final class ActivityExtKt$startKtActivity$3$1<O> implements ActivityResultCallback {
    final /* synthetic */ Function1<ActivityResult, Unit> $callback;

    /* JADX WARN: Multi-variable type inference failed */
    public ActivityExtKt$startKtActivity$3$1(Function1<? super ActivityResult, Unit> function1) {
        this.$callback = function1;
    }

    @Override // androidx.activity.result.ActivityResultCallback
    public final void onActivityResult(ActivityResult result) {
        Function1<ActivityResult, Unit> function1 = this.$callback;
        Intrinsics.checkNotNullExpressionValue(result, "result");
        function1.invoke(result);
    }
}
