package com.yddmi.doctor.entity.result;

import androidx.annotation.Keep;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.Serializable;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.internal.ArrayListSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 !2\u00020\u0001:\u0002 !B9\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bB)\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0005¢\u0006\u0002\u0010\fJ\u0011\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0003J\u0011\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0005HÆ\u0003J-\u0010\u0012\u001a\u00020\u00002\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00052\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J!\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fHÇ\u0001R\u0019\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000e¨\u0006\""}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeStudyResult;", "", "seen1", "", "homeActionJxzy", "", "Lcom/yddmi/doctor/entity/result/HomeTeachingClassItem;", "homeActionBljx", "Lcom/yddmi/doctor/entity/result/RowCase;", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/util/List;Ljava/util/List;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/util/List;Ljava/util/List;)V", "getHomeActionBljx", "()Ljava/util/List;", "getHomeActionJxzy", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeStudyResult {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final List<RowCase> homeActionBljx;

    @Nullable
    private final List<HomeTeachingClassItem> homeActionJxzy;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/HomeStudyResult$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/HomeStudyResult;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeStudyResult> serializer() {
            return HomeStudyResult$$serializer.INSTANCE;
        }
    }

    public HomeStudyResult() {
        this((List) null, (List) (0 == true ? 1 : 0), 3, (DefaultConstructorMarker) (0 == true ? 1 : 0));
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeStudyResult(int i2, List list, List list2, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, HomeStudyResult$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.homeActionJxzy = null;
        } else {
            this.homeActionJxzy = list;
        }
        if ((i2 & 2) == 0) {
            this.homeActionBljx = null;
        } else {
            this.homeActionBljx = list2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ HomeStudyResult copy$default(HomeStudyResult homeStudyResult, List list, List list2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            list = homeStudyResult.homeActionJxzy;
        }
        if ((i2 & 2) != 0) {
            list2 = homeStudyResult.homeActionBljx;
        }
        return homeStudyResult.copy(list, list2);
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeStudyResult self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.homeActionJxzy != null) {
            output.encodeNullableSerializableElement(serialDesc, 0, new ArrayListSerializer(HomeTeachingClassItem$$serializer.INSTANCE), self.homeActionJxzy);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.homeActionBljx != null) {
            output.encodeNullableSerializableElement(serialDesc, 1, new ArrayListSerializer(RowCase$$serializer.INSTANCE), self.homeActionBljx);
        }
    }

    @Nullable
    public final List<HomeTeachingClassItem> component1() {
        return this.homeActionJxzy;
    }

    @Nullable
    public final List<RowCase> component2() {
        return this.homeActionBljx;
    }

    @NotNull
    public final HomeStudyResult copy(@Nullable List<HomeTeachingClassItem> homeActionJxzy, @Nullable List<RowCase> homeActionBljx) {
        return new HomeStudyResult(homeActionJxzy, homeActionBljx);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeStudyResult)) {
            return false;
        }
        HomeStudyResult homeStudyResult = (HomeStudyResult) other;
        return Intrinsics.areEqual(this.homeActionJxzy, homeStudyResult.homeActionJxzy) && Intrinsics.areEqual(this.homeActionBljx, homeStudyResult.homeActionBljx);
    }

    @Nullable
    public final List<RowCase> getHomeActionBljx() {
        return this.homeActionBljx;
    }

    @Nullable
    public final List<HomeTeachingClassItem> getHomeActionJxzy() {
        return this.homeActionJxzy;
    }

    public int hashCode() {
        List<HomeTeachingClassItem> list = this.homeActionJxzy;
        int iHashCode = (list == null ? 0 : list.hashCode()) * 31;
        List<RowCase> list2 = this.homeActionBljx;
        return iHashCode + (list2 != null ? list2.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "HomeStudyResult(homeActionJxzy=" + this.homeActionJxzy + ", homeActionBljx=" + this.homeActionBljx + ")";
    }

    public HomeStudyResult(@Nullable List<HomeTeachingClassItem> list, @Nullable List<RowCase> list2) {
        this.homeActionJxzy = list;
        this.homeActionBljx = list2;
    }

    public /* synthetic */ HomeStudyResult(List list, List list2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : list, (i2 & 2) != 0 ? null : list2);
    }
}
