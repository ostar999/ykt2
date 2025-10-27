package com.ykb.ebook.base;

import androidx.annotation.Keep;
import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u001d\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004HÆ\u0003J\t\u0010\r\u001a\u00020\u0006HÆ\u0003J)\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u000e\b\u0002\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0006HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lcom/ykb/ebook/base/BaseListResponse;", ExifInterface.GPS_DIRECTION_TRUE, "", "list", "", "count", "", "(Ljava/util/List;Ljava/lang/String;)V", "getCount", "()Ljava/lang/String;", "getList", "()Ljava/util/List;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final /* data */ class BaseListResponse<T> {

    @NotNull
    private final String count;

    @NotNull
    private final List<T> list;

    /* JADX WARN: Multi-variable type inference failed */
    public BaseListResponse(@NotNull List<? extends T> list, @NotNull String count) {
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(count, "count");
        this.list = list;
        this.count = count;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ BaseListResponse copy$default(BaseListResponse baseListResponse, List list, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            list = baseListResponse.list;
        }
        if ((i2 & 2) != 0) {
            str = baseListResponse.count;
        }
        return baseListResponse.copy(list, str);
    }

    @NotNull
    public final List<T> component1() {
        return this.list;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getCount() {
        return this.count;
    }

    @NotNull
    public final BaseListResponse<T> copy(@NotNull List<? extends T> list, @NotNull String count) {
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(count, "count");
        return new BaseListResponse<>(list, count);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BaseListResponse)) {
            return false;
        }
        BaseListResponse baseListResponse = (BaseListResponse) other;
        return Intrinsics.areEqual(this.list, baseListResponse.list) && Intrinsics.areEqual(this.count, baseListResponse.count);
    }

    @NotNull
    public final String getCount() {
        return this.count;
    }

    @NotNull
    public final List<T> getList() {
        return this.list;
    }

    public int hashCode() {
        return (this.list.hashCode() * 31) + this.count.hashCode();
    }

    @NotNull
    public String toString() {
        return "BaseListResponse(list=" + this.list + ", count=" + this.count + ')';
    }

    public /* synthetic */ BaseListResponse(List list, String str, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, (i2 & 2) != 0 ? "0" : str);
    }
}
