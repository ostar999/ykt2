package okio;

import androidx.exifinterface.media.ExifInterface;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.ArrayList;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.reflect.KClasses;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001Bo\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\b\u0012\u0018\b\u0002\u0010\f\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e\u0012\u0004\u0012\u00020\u00010\r¢\u0006\u0002\u0010\u000fJu\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\b2\u0018\b\u0002\u0010\f\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e\u0012\u0004\u0012\u00020\u00010\r¢\u0006\u0002\u0010\u001cJ'\u0010\u001d\u001a\u0004\u0018\u0001H\u001e\"\b\b\u0000\u0010\u001e*\u00020\u00012\u000e\u0010\u001f\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u001e0\u000e¢\u0006\u0002\u0010 J\b\u0010!\u001a\u00020\"H\u0016R\u0015\u0010\t\u001a\u0004\u0018\u00010\b¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R!\u0010\f\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e\u0012\u0004\u0012\u00020\u00010\r¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0015R\u0015\u0010\u000b\u001a\u0004\u0018\u00010\b¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0016\u0010\u0011R\u0015\u0010\n\u001a\u0004\u0018\u00010\b¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0017\u0010\u0011R\u0015\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0018\u0010\u0011R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006#"}, d2 = {"Lokio/FileMetadata;", "", "isRegularFile", "", "isDirectory", "symlinkTarget", "Lokio/Path;", DatabaseManager.SIZE, "", "createdAtMillis", "lastModifiedAtMillis", "lastAccessedAtMillis", "extras", "", "Lkotlin/reflect/KClass;", "(ZZLokio/Path;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;Ljava/util/Map;)V", "getCreatedAtMillis", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getExtras", "()Ljava/util/Map;", "()Z", "getLastAccessedAtMillis", "getLastModifiedAtMillis", "getSize", "getSymlinkTarget", "()Lokio/Path;", "copy", "(ZZLokio/Path;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;Ljava/util/Map;)Lokio/FileMetadata;", PushConstants.EXTRA, ExifInterface.GPS_DIRECTION_TRUE, "type", "(Lkotlin/reflect/KClass;)Ljava/lang/Object;", "toString", "", "okio"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class FileMetadata {

    @Nullable
    private final Long createdAtMillis;

    @NotNull
    private final Map<KClass<?>, Object> extras;
    private final boolean isDirectory;
    private final boolean isRegularFile;

    @Nullable
    private final Long lastAccessedAtMillis;

    @Nullable
    private final Long lastModifiedAtMillis;

    @Nullable
    private final Long size;

    @Nullable
    private final Path symlinkTarget;

    public FileMetadata() {
        this(false, false, null, null, null, null, null, null, 255, null);
    }

    public FileMetadata(boolean z2, boolean z3, @Nullable Path path, @Nullable Long l2, @Nullable Long l3, @Nullable Long l4, @Nullable Long l5, @NotNull Map<KClass<?>, ? extends Object> extras) {
        Intrinsics.checkNotNullParameter(extras, "extras");
        this.isRegularFile = z2;
        this.isDirectory = z3;
        this.symlinkTarget = path;
        this.size = l2;
        this.createdAtMillis = l3;
        this.lastModifiedAtMillis = l4;
        this.lastAccessedAtMillis = l5;
        this.extras = MapsKt__MapsKt.toMap(extras);
    }

    @NotNull
    public final FileMetadata copy(boolean isRegularFile, boolean isDirectory, @Nullable Path symlinkTarget, @Nullable Long size, @Nullable Long createdAtMillis, @Nullable Long lastModifiedAtMillis, @Nullable Long lastAccessedAtMillis, @NotNull Map<KClass<?>, ? extends Object> extras) {
        Intrinsics.checkNotNullParameter(extras, "extras");
        return new FileMetadata(isRegularFile, isDirectory, symlinkTarget, size, createdAtMillis, lastModifiedAtMillis, lastAccessedAtMillis, extras);
    }

    @Nullable
    public final <T> T extra(@NotNull KClass<? extends T> type) {
        Intrinsics.checkNotNullParameter(type, "type");
        Object obj = this.extras.get(type);
        if (obj == null) {
            return null;
        }
        return (T) KClasses.cast(type, obj);
    }

    @Nullable
    public final Long getCreatedAtMillis() {
        return this.createdAtMillis;
    }

    @NotNull
    public final Map<KClass<?>, Object> getExtras() {
        return this.extras;
    }

    @Nullable
    public final Long getLastAccessedAtMillis() {
        return this.lastAccessedAtMillis;
    }

    @Nullable
    public final Long getLastModifiedAtMillis() {
        return this.lastModifiedAtMillis;
    }

    @Nullable
    public final Long getSize() {
        return this.size;
    }

    @Nullable
    public final Path getSymlinkTarget() {
        return this.symlinkTarget;
    }

    /* renamed from: isDirectory, reason: from getter */
    public final boolean getIsDirectory() {
        return this.isDirectory;
    }

    /* renamed from: isRegularFile, reason: from getter */
    public final boolean getIsRegularFile() {
        return this.isRegularFile;
    }

    @NotNull
    public String toString() {
        ArrayList arrayList = new ArrayList();
        if (this.isRegularFile) {
            arrayList.add("isRegularFile");
        }
        if (this.isDirectory) {
            arrayList.add("isDirectory");
        }
        if (this.size != null) {
            arrayList.add("byteCount=" + this.size);
        }
        if (this.createdAtMillis != null) {
            arrayList.add("createdAt=" + this.createdAtMillis);
        }
        if (this.lastModifiedAtMillis != null) {
            arrayList.add("lastModifiedAt=" + this.lastModifiedAtMillis);
        }
        if (this.lastAccessedAtMillis != null) {
            arrayList.add("lastAccessedAt=" + this.lastAccessedAtMillis);
        }
        if (!this.extras.isEmpty()) {
            arrayList.add("extras=" + this.extras);
        }
        return CollectionsKt___CollectionsKt.joinToString$default(arrayList, ", ", "FileMetadata(", ")", 0, null, null, 56, null);
    }

    public /* synthetic */ FileMetadata(boolean z2, boolean z3, Path path, Long l2, Long l3, Long l4, Long l5, Map map, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? false : z2, (i2 & 2) == 0 ? z3 : false, (i2 & 4) != 0 ? null : path, (i2 & 8) != 0 ? null : l2, (i2 & 16) != 0 ? null : l3, (i2 & 32) != 0 ? null : l4, (i2 & 64) == 0 ? l5 : null, (i2 & 128) != 0 ? MapsKt__MapsKt.emptyMap() : map);
    }
}
