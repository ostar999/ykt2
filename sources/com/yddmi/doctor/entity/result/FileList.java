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
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 &2\u00020\u0001:\u0002%&BY\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fBM\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\u0002\u0010\rJ\u0011\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0003J\u0011\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0003J\u0011\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0003J\u0011\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0003JQ\u0010\u0017\u001a\u00020\u00002\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00052\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00052\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00052\u0010\b\u0002\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001J!\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00002\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$HÇ\u0001R\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0019\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0019\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0019\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000f¨\u0006'"}, d2 = {"Lcom/yddmi/doctor/entity/result/FileList;", "", "seen1", "", "audio", "", "Lcom/yddmi/doctor/entity/result/AnnexItem;", "document", "image", "video", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getAudio", "()Ljava/util/List;", "getDocument", "getImage", "getVideo", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Serializable
/* loaded from: classes6.dex */
public final /* data */ class FileList {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private final List<AnnexItem> audio;

    @Nullable
    private final List<AnnexItem> document;

    @Nullable
    private final List<AnnexItem> image;

    @Nullable
    private final List<AnnexItem> video;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004HÆ\u0001¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/entity/result/FileList$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/yddmi/doctor/entity/result/FileList;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final KSerializer<FileList> serializer() {
            return FileList$$serializer.INSTANCE;
        }
    }

    public FileList() {
        this((List) null, (List) null, (List) null, (List) null, 15, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly", replaceWith = @ReplaceWith(expression = "", imports = {}))
    public /* synthetic */ FileList(int i2, List list, List list2, List list3, List list4, SerializationConstructorMarker serializationConstructorMarker) {
        if ((i2 & 0) != 0) {
            PluginExceptionsKt.throwMissingFieldException(i2, 0, FileList$$serializer.INSTANCE.getDescriptor());
        }
        if ((i2 & 1) == 0) {
            this.audio = null;
        } else {
            this.audio = list;
        }
        if ((i2 & 2) == 0) {
            this.document = null;
        } else {
            this.document = list2;
        }
        if ((i2 & 4) == 0) {
            this.image = null;
        } else {
            this.image = list3;
        }
        if ((i2 & 8) == 0) {
            this.video = null;
        } else {
            this.video = list4;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ FileList copy$default(FileList fileList, List list, List list2, List list3, List list4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            list = fileList.audio;
        }
        if ((i2 & 2) != 0) {
            list2 = fileList.document;
        }
        if ((i2 & 4) != 0) {
            list3 = fileList.image;
        }
        if ((i2 & 8) != 0) {
            list4 = fileList.video;
        }
        return fileList.copy(list, list2, list3, list4);
    }

    @JvmStatic
    public static final void write$Self(@NotNull FileList self, @NotNull CompositeEncoder output, @NotNull SerialDescriptor serialDesc) {
        Intrinsics.checkNotNullParameter(self, "self");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(serialDesc, "serialDesc");
        if (output.shouldEncodeElementDefault(serialDesc, 0) || self.audio != null) {
            output.encodeNullableSerializableElement(serialDesc, 0, new ArrayListSerializer(AnnexItem$$serializer.INSTANCE), self.audio);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 1) || self.document != null) {
            output.encodeNullableSerializableElement(serialDesc, 1, new ArrayListSerializer(AnnexItem$$serializer.INSTANCE), self.document);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 2) || self.image != null) {
            output.encodeNullableSerializableElement(serialDesc, 2, new ArrayListSerializer(AnnexItem$$serializer.INSTANCE), self.image);
        }
        if (output.shouldEncodeElementDefault(serialDesc, 3) || self.video != null) {
            output.encodeNullableSerializableElement(serialDesc, 3, new ArrayListSerializer(AnnexItem$$serializer.INSTANCE), self.video);
        }
    }

    @Nullable
    public final List<AnnexItem> component1() {
        return this.audio;
    }

    @Nullable
    public final List<AnnexItem> component2() {
        return this.document;
    }

    @Nullable
    public final List<AnnexItem> component3() {
        return this.image;
    }

    @Nullable
    public final List<AnnexItem> component4() {
        return this.video;
    }

    @NotNull
    public final FileList copy(@Nullable List<AnnexItem> audio, @Nullable List<AnnexItem> document, @Nullable List<AnnexItem> image, @Nullable List<AnnexItem> video) {
        return new FileList(audio, document, image, video);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FileList)) {
            return false;
        }
        FileList fileList = (FileList) other;
        return Intrinsics.areEqual(this.audio, fileList.audio) && Intrinsics.areEqual(this.document, fileList.document) && Intrinsics.areEqual(this.image, fileList.image) && Intrinsics.areEqual(this.video, fileList.video);
    }

    @Nullable
    public final List<AnnexItem> getAudio() {
        return this.audio;
    }

    @Nullable
    public final List<AnnexItem> getDocument() {
        return this.document;
    }

    @Nullable
    public final List<AnnexItem> getImage() {
        return this.image;
    }

    @Nullable
    public final List<AnnexItem> getVideo() {
        return this.video;
    }

    public int hashCode() {
        List<AnnexItem> list = this.audio;
        int iHashCode = (list == null ? 0 : list.hashCode()) * 31;
        List<AnnexItem> list2 = this.document;
        int iHashCode2 = (iHashCode + (list2 == null ? 0 : list2.hashCode())) * 31;
        List<AnnexItem> list3 = this.image;
        int iHashCode3 = (iHashCode2 + (list3 == null ? 0 : list3.hashCode())) * 31;
        List<AnnexItem> list4 = this.video;
        return iHashCode3 + (list4 != null ? list4.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "FileList(audio=" + this.audio + ", document=" + this.document + ", image=" + this.image + ", video=" + this.video + ")";
    }

    public FileList(@Nullable List<AnnexItem> list, @Nullable List<AnnexItem> list2, @Nullable List<AnnexItem> list3, @Nullable List<AnnexItem> list4) {
        this.audio = list;
        this.document = list2;
        this.image = list3;
        this.video = list4;
    }

    public /* synthetic */ FileList(List list, List list2, List list3, List list4, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : list, (i2 & 2) != 0 ? null : list2, (i2 & 4) != 0 ? null : list3, (i2 & 8) != 0 ? null : list4);
    }
}
