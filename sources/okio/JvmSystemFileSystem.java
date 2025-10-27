package okio;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.tencent.open.SocialConstants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.jvm.internal.Intrinsics;
import net.lingala.zip4j.util.InternalZipConstants;
import okio.Path;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0010\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006H\u0016J\u0010\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0006H\u0016J\u0018\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\bH\u0016J\u0018\u0010\u0012\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006H\u0016J\u0018\u0010\u0013\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u00152\u0006\u0010\u0010\u001a\u00020\u0006H\u0016J \u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00152\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\bH\u0002J\u0018\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00152\u0006\u0010\u0010\u001a\u00020\u0006H\u0016J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u000e\u001a\u00020\u0006H\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J \u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\bH\u0016J\u0010\u0010\u000b\u001a\u00020\u001e2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u001f\u001a\u00020 H\u0016J\f\u0010!\u001a\u00020\n*\u00020\u0006H\u0002J\f\u0010\"\u001a\u00020\n*\u00020\u0006H\u0002¨\u0006#"}, d2 = {"Lokio/JvmSystemFileSystem;", "Lokio/FileSystem;", "()V", "appendingSink", "Lokio/Sink;", "file", "Lokio/Path;", "mustExist", "", "atomicMove", "", SocialConstants.PARAM_SOURCE, TypedValues.AttributesType.S_TARGET, "canonicalize", "path", "createDirectory", "dir", "mustCreate", "createSymlink", RequestParameters.SUBRESOURCE_DELETE, "list", "", "throwOnFailure", "listOrNull", "metadataOrNull", "Lokio/FileMetadata;", "openReadOnly", "Lokio/FileHandle;", "openReadWrite", "sink", "Lokio/Source;", "toString", "", "requireCreate", "requireExist", "okio"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes9.dex */
public class JvmSystemFileSystem extends FileSystem {
    private final void requireCreate(Path path) throws IOException {
        if (exists(path)) {
            throw new IOException(path + " already exists.");
        }
    }

    private final void requireExist(Path path) throws IOException {
        if (exists(path)) {
            return;
        }
        throw new IOException(path + " doesn't exist.");
    }

    @Override // okio.FileSystem
    @NotNull
    public Sink appendingSink(@NotNull Path file, boolean mustExist) throws IOException {
        Intrinsics.checkNotNullParameter(file, "file");
        if (mustExist) {
            requireExist(file);
        }
        return Okio.sink(file.toFile(), true);
    }

    @Override // okio.FileSystem
    public void atomicMove(@NotNull Path source, @NotNull Path target) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(target, "target");
        if (source.toFile().renameTo(target.toFile())) {
            return;
        }
        throw new IOException("failed to move " + source + " to " + target);
    }

    @Override // okio.FileSystem
    @NotNull
    public Path canonicalize(@NotNull Path path) throws IOException {
        Intrinsics.checkNotNullParameter(path, "path");
        File canonicalFile = path.toFile().getCanonicalFile();
        if (!canonicalFile.exists()) {
            throw new FileNotFoundException("no such file");
        }
        Path.Companion companion = Path.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(canonicalFile, "canonicalFile");
        return Path.Companion.get$default(companion, canonicalFile, false, 1, (Object) null);
    }

    @Override // okio.FileSystem
    public void createDirectory(@NotNull Path dir, boolean mustCreate) throws IOException {
        Intrinsics.checkNotNullParameter(dir, "dir");
        if (dir.toFile().mkdir()) {
            return;
        }
        FileMetadata fileMetadataMetadataOrNull = metadataOrNull(dir);
        boolean z2 = false;
        if (fileMetadataMetadataOrNull != null && fileMetadataMetadataOrNull.getIsDirectory()) {
            z2 = true;
        }
        if (!z2) {
            throw new IOException("failed to create directory: " + dir);
        }
        if (mustCreate) {
            throw new IOException(dir + " already exist.");
        }
    }

    @Override // okio.FileSystem
    public void createSymlink(@NotNull Path source, @NotNull Path target) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(target, "target");
        throw new IOException("unsupported");
    }

    @Override // okio.FileSystem
    public void delete(@NotNull Path path, boolean mustExist) throws IOException {
        Intrinsics.checkNotNullParameter(path, "path");
        File file = path.toFile();
        if (file.delete()) {
            return;
        }
        if (file.exists()) {
            throw new IOException("failed to delete " + path);
        }
        if (mustExist) {
            throw new FileNotFoundException("no such file: " + path);
        }
    }

    @Override // okio.FileSystem
    @NotNull
    public List<Path> list(@NotNull Path dir) throws IOException {
        Intrinsics.checkNotNullParameter(dir, "dir");
        List<Path> list = list(dir, true);
        Intrinsics.checkNotNull(list);
        return list;
    }

    @Override // okio.FileSystem
    @Nullable
    public List<Path> listOrNull(@NotNull Path dir) {
        Intrinsics.checkNotNullParameter(dir, "dir");
        return list(dir, false);
    }

    @Override // okio.FileSystem
    @Nullable
    public FileMetadata metadataOrNull(@NotNull Path path) {
        Intrinsics.checkNotNullParameter(path, "path");
        File file = path.toFile();
        boolean zIsFile = file.isFile();
        boolean zIsDirectory = file.isDirectory();
        long jLastModified = file.lastModified();
        long length = file.length();
        if (zIsFile || zIsDirectory || jLastModified != 0 || length != 0 || file.exists()) {
            return new FileMetadata(zIsFile, zIsDirectory, null, Long.valueOf(length), null, Long.valueOf(jLastModified), null, null, 128, null);
        }
        return null;
    }

    @Override // okio.FileSystem
    @NotNull
    public FileHandle openReadOnly(@NotNull Path file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return new JvmFileHandle(false, new RandomAccessFile(file.toFile(), "r"));
    }

    @Override // okio.FileSystem
    @NotNull
    public FileHandle openReadWrite(@NotNull Path file, boolean mustCreate, boolean mustExist) throws IOException {
        Intrinsics.checkNotNullParameter(file, "file");
        if (!((mustCreate && mustExist) ? false : true)) {
            throw new IllegalArgumentException("Cannot require mustCreate and mustExist at the same time.".toString());
        }
        if (mustCreate) {
            requireCreate(file);
        }
        if (mustExist) {
            requireExist(file);
        }
        return new JvmFileHandle(true, new RandomAccessFile(file.toFile(), InternalZipConstants.WRITE_MODE));
    }

    @Override // okio.FileSystem
    @NotNull
    public Sink sink(@NotNull Path file, boolean mustCreate) throws IOException {
        Intrinsics.checkNotNullParameter(file, "file");
        if (mustCreate) {
            requireCreate(file);
        }
        return Okio__JvmOkioKt.sink$default(file.toFile(), false, 1, null);
    }

    @Override // okio.FileSystem
    @NotNull
    public Source source(@NotNull Path file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return Okio.source(file.toFile());
    }

    @NotNull
    public String toString() {
        return "JvmSystemFileSystem";
    }

    private final List<Path> list(Path dir, boolean throwOnFailure) throws IOException {
        File file = dir.toFile();
        String[] list = file.list();
        if (list != null) {
            ArrayList arrayList = new ArrayList();
            for (String it : list) {
                Intrinsics.checkNotNullExpressionValue(it, "it");
                arrayList.add(dir.resolve(it));
            }
            CollectionsKt__MutableCollectionsJVMKt.sort(arrayList);
            return arrayList;
        }
        if (!throwOnFailure) {
            return null;
        }
        if (file.exists()) {
            throw new IOException("failed to list " + dir);
        }
        throw new FileNotFoundException("no such file: " + dir);
    }
}
