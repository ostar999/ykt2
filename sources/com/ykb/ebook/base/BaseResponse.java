package com.ykb.ebook.base;

import androidx.annotation.Keep;
import androidx.exifinterface.media.ExifInterface;
import com.ykb.ebook.network.ApiException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010\bJ\t\u0010\u0010\u001a\u00020\u0004HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0006HÆ\u0003J\u0010\u0010\u0012\u001a\u0004\u0018\u00018\u0000HÆ\u0003¢\u0006\u0002\u0010\fJ4\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00018\u0000HÆ\u0001¢\u0006\u0002\u0010\u0014J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0004HÖ\u0001J\u0006\u0010\u0019\u001a\u00020\u001aJ\t\u0010\u001b\u001a\u00020\u0006HÖ\u0001R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0015\u0010\u0007\u001a\u0004\u0018\u00018\u0000¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001c"}, d2 = {"Lcom/ykb/ebook/base/BaseResponse;", ExifInterface.GPS_DIRECTION_TRUE, "", "code", "", "message", "", "data", "(ILjava/lang/String;Ljava/lang/Object;)V", "getCode", "()I", "getData", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getMessage", "()Ljava/lang/String;", "component1", "component2", "component3", "copy", "(ILjava/lang/String;Ljava/lang/Object;)Lcom/ykb/ebook/base/BaseResponse;", "equals", "", "other", "hashCode", "throwApiException", "", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final /* data */ class BaseResponse<T> {
    private final int code;

    @Nullable
    private final T data;

    @NotNull
    private final String message;

    public BaseResponse(int i2, @NotNull String message, @Nullable T t2) {
        Intrinsics.checkNotNullParameter(message, "message");
        this.code = i2;
        this.message = message;
        this.data = t2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ BaseResponse copy$default(BaseResponse baseResponse, int i2, String str, Object obj, int i3, Object obj2) {
        if ((i3 & 1) != 0) {
            i2 = baseResponse.code;
        }
        if ((i3 & 2) != 0) {
            str = baseResponse.message;
        }
        if ((i3 & 4) != 0) {
            obj = baseResponse.data;
        }
        return baseResponse.copy(i2, str, obj);
    }

    /* renamed from: component1, reason: from getter */
    public final int getCode() {
        return this.code;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getMessage() {
        return this.message;
    }

    @Nullable
    public final T component3() {
        return this.data;
    }

    @NotNull
    public final BaseResponse<T> copy(int code, @NotNull String message, @Nullable T data) {
        Intrinsics.checkNotNullParameter(message, "message");
        return new BaseResponse<>(code, message, data);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BaseResponse)) {
            return false;
        }
        BaseResponse baseResponse = (BaseResponse) other;
        return this.code == baseResponse.code && Intrinsics.areEqual(this.message, baseResponse.message) && Intrinsics.areEqual(this.data, baseResponse.data);
    }

    public final int getCode() {
        return this.code;
    }

    @Nullable
    public final T getData() {
        return this.data;
    }

    @NotNull
    public final String getMessage() {
        return this.message;
    }

    public int hashCode() {
        int iHashCode = ((this.code * 31) + this.message.hashCode()) * 31;
        T t2 = this.data;
        return iHashCode + (t2 == null ? 0 : t2.hashCode());
    }

    public final void throwApiException() {
        if (this.code != 200) {
            throw new ApiException(this.code, this.message);
        }
        if (this.data == null) {
            throw new ApiException(700, "数据非法");
        }
    }

    @NotNull
    public String toString() {
        return "BaseResponse(code=" + this.code + ", message=" + this.message + ", data=" + this.data + ')';
    }
}
