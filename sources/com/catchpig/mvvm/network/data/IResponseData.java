package com.catchpig.mvvm.network.data;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlinx.serialization.json.JsonElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003J\u000f\u0010\u0004\u001a\u0004\u0018\u00018\u0000H&¢\u0006\u0002\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H&J\n\u0010\b\u001a\u0004\u0018\u00010\u0007H&J\b\u0010\t\u001a\u00020\u0007H&¨\u0006\n"}, d2 = {"Lcom/catchpig/mvvm/network/data/IResponseData;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/serialization/json/JsonElement;", "", "data", "()Lkotlinx/serialization/json/JsonElement;", "getErrorCode", "", "getErrorMessage", "isSuccess", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface IResponseData<T extends JsonElement> {
    @Nullable
    T data();

    @NotNull
    String getErrorCode();

    @Nullable
    String getErrorMessage();

    @NotNull
    String isSuccess();
}
