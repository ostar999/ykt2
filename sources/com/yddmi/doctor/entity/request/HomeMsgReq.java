package com.yddmi.doctor.entity.request;

import androidx.annotation.Keep;
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
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 %2\u00020\u0001:\u0002$%B9\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nB)\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\u000bJ\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J1\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J!\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00002\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#HÇ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0007\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\r\"\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\r¨\u0006&"}, d2 = {"Lcom/yddmi/doctor/entity/request/HomeMsgReq;", "", "seen1", "", "id", "userId", "isRead", "isDelete", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IIIIILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(IIII)V", "getId", "()I", "setDelete", "(I)V", "setRead", "getUserId", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class HomeMsgReq {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private final int id;
    private int isDelete;
    private int isRead;
    private final int userId;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/request/HomeMsgReq$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/request/HomeMsgReq;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<HomeMsgReq> serializer() {
            return HomeMsgReq$$serializer.INSTANCE;
        }
    }

    public HomeMsgReq(int i2, int i3, int i4, int i5) {
        this.id = i2;
        this.userId = i3;
        this.isRead = i4;
        this.isDelete = i5;
    }

    public static /* synthetic */ HomeMsgReq copy$default(HomeMsgReq homeMsgReq, int i2, int i3, int i4, int i5, int i6, Object obj) {
        if ((i6 & 1) != 0) {
            i2 = homeMsgReq.id;
        }
        if ((i6 & 2) != 0) {
            i3 = homeMsgReq.userId;
        }
        if ((i6 & 4) != 0) {
            i4 = homeMsgReq.isRead;
        }
        if ((i6 & 8) != 0) {
            i5 = homeMsgReq.isDelete;
        }
        return homeMsgReq.copy(i2, i3, i4, i5);
    }

    @JvmStatic
    public static final void write$Self(@NotNull HomeMsgReq self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        output.encodeIntElement(serialDesc, 0, self.id);
        output.encodeIntElement(serialDesc, 1, self.userId);
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.isRead != -1) {
            output.encodeIntElement(serialDesc, 2, self.isRead);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.isDelete != -1) {
            output.encodeIntElement(serialDesc, 3, self.isDelete);
        }
    }

    /* renamed from: component1, reason: from getter */
    public final int getId() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final int getUserId() {
        return this.userId;
    }

    /* renamed from: component3, reason: from getter */
    public final int getIsRead() {
        return this.isRead;
    }

    /* renamed from: component4, reason: from getter */
    public final int getIsDelete() {
        return this.isDelete;
    }

    @NotNull
    public final HomeMsgReq copy(int id, int userId, int isRead, int isDelete) {
        return new HomeMsgReq(id, userId, isRead, isDelete);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeMsgReq)) {
            return false;
        }
        HomeMsgReq homeMsgReq = (HomeMsgReq) other;
        return this.id == homeMsgReq.id && this.userId == homeMsgReq.userId && this.isRead == homeMsgReq.isRead && this.isDelete == homeMsgReq.isDelete;
    }

    public final int getId() {
        return this.id;
    }

    public final int getUserId() {
        return this.userId;
    }

    public int hashCode() {
        return (((((this.id * 31) + this.userId) * 31) + this.isRead) * 31) + this.isDelete;
    }

    public final int isDelete() {
        return this.isDelete;
    }

    public final int isRead() {
        return this.isRead;
    }

    public final void setDelete(int i2) {
        this.isDelete = i2;
    }

    public final void setRead(int i2) {
        this.isRead = i2;
    }

    @NotNull
    public String toString() {
        return "HomeMsgReq(id=" + this.id + ", userId=" + this.userId + ", isRead=" + this.isRead + ", isDelete=" + this.isDelete + ")";
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ HomeMsgReq(int i2, int i3, int i4, int i5, int i6, SerializationConstructorMarker serializationConstructorMarker) {
        if (3 != (i2 & 3)) {
            PluginExceptionsKt.throwMissingFieldException(i2, 3, HomeMsgReq$$serializer.INSTANCE.getDescriptor());
        }
        this.id = i3;
        this.userId = i4;
        if ((i2 & 4) == 0) {
            this.isRead = -1;
        } else {
            this.isRead = i5;
        }
        if ((i2 & 8) == 0) {
            this.isDelete = -1;
        } else {
            this.isDelete = i6;
        }
    }

    public /* synthetic */ HomeMsgReq(int i2, int i3, int i4, int i5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this(i2, i3, (i6 & 4) != 0 ? -1 : i4, (i6 & 8) != 0 ? -1 : i5);
    }
}
