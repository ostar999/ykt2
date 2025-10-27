package com.catchpig.utils.ext;

import androidx.exifinterface.media.ExifInterface;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\u001a \u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\b\b\u0000\u0010\u0004*\u00020\u0005*\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"TAG", "", "io2main", "Lio/reactivex/rxjava3/core/Flowable;", ExifInterface.GPS_DIRECTION_TRUE, "", "utils_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class RxJavaExtKt {

    @NotNull
    private static final String TAG = "RxJavaExt";

    @NotNull
    public static final <T> Flowable<T> io2main(@NotNull Flowable<T> flowable) {
        Intrinsics.checkNotNullParameter(flowable, "<this>");
        Flowable<T> flowableObserveOn = flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Intrinsics.checkNotNullExpressionValue(flowableObserveOn, "this.subscribeOn(Schedul…dSchedulers.mainThread())");
        return flowableObserveOn;
    }
}
