package okio.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.tencent.open.SocialConstants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import okio.FileHandle;
import okio.FileMetadata;
import okio.FileSystem;
import okio.Path;
import okio.Sink;
import okio.Source;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0000\u0018\u0000 *2\u00020\u0001:\u0001*B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0005H\u0016J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\nH\u0016J\u0010\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\nH\u0016J\u0010\u0010\u0019\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\nH\u0002J\u0018\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u0005H\u0016J\u0018\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\nH\u0016J\u0018\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0005H\u0016J\u0016\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\n0\b2\u0006\u0010\u001b\u001a\u00020\nH\u0016J\u0018\u0010 \u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\b2\u0006\u0010\u001b\u001a\u00020\nH\u0016J\u0012\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010\u0018\u001a\u00020\nH\u0016J\u0010\u0010#\u001a\u00020$2\u0006\u0010\u0011\u001a\u00020\nH\u0016J \u0010%\u001a\u00020$2\u0006\u0010\u0011\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0005H\u0016J\u0018\u0010&\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u0005H\u0016J\u0010\u0010\u0015\u001a\u00020'2\u0006\u0010\u0011\u001a\u00020\nH\u0016J\f\u0010(\u001a\u00020)*\u00020\nH\u0002R-\u0010\u0007\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\n0\t0\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\f¨\u0006+"}, d2 = {"Lokio/internal/ResourceFileSystem;", "Lokio/FileSystem;", "classLoader", "Ljava/lang/ClassLoader;", "indexEagerly", "", "(Ljava/lang/ClassLoader;Z)V", "roots", "", "Lkotlin/Pair;", "Lokio/Path;", "getRoots", "()Ljava/util/List;", "roots$delegate", "Lkotlin/Lazy;", "appendingSink", "Lokio/Sink;", "file", "mustExist", "atomicMove", "", SocialConstants.PARAM_SOURCE, TypedValues.AttributesType.S_TARGET, "canonicalize", "path", "canonicalizeInternal", "createDirectory", "dir", "mustCreate", "createSymlink", RequestParameters.SUBRESOURCE_DELETE, "list", "listOrNull", "metadataOrNull", "Lokio/FileMetadata;", "openReadOnly", "Lokio/FileHandle;", "openReadWrite", "sink", "Lokio/Source;", "toRelativePath", "", "Companion", "okio"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class ResourceFileSystem extends FileSystem {

    @NotNull
    private static final Companion Companion = new Companion(null);

    @Deprecated
    @NotNull
    private static final Path ROOT = Path.Companion.get$default(Path.INSTANCE, "/", false, 1, (Object) null);

    /* renamed from: roots$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy roots;

    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0002J\u0012\u0010\n\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u001c\u0010\f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00040\u000e0\r*\u00020\u0010J\u0018\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u000e*\u00020\u0012J\u0018\u0010\u0013\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u000e*\u00020\u0012R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0014"}, d2 = {"Lokio/internal/ResourceFileSystem$Companion;", "", "()V", Logger.ROOT_LOGGER_NAME, "Lokio/Path;", "getROOT", "()Lokio/Path;", "keepPath", "", "path", "removeBase", TtmlNode.RUBY_BASE, "toClasspathRoots", "", "Lkotlin/Pair;", "Lokio/FileSystem;", "Ljava/lang/ClassLoader;", "toFileRoot", "Ljava/net/URL;", "toJarRoot", "okio"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean keepPath(Path path) {
            return !StringsKt__StringsJVMKt.endsWith(path.name(), ".class", true);
        }

        @NotNull
        public final Path getROOT() {
            return ResourceFileSystem.ROOT;
        }

        @NotNull
        public final Path removeBase(@NotNull Path path, @NotNull Path base) {
            Intrinsics.checkNotNullParameter(path, "<this>");
            Intrinsics.checkNotNullParameter(base, "base");
            return getROOT().resolve(StringsKt__StringsJVMKt.replace$default(StringsKt__StringsKt.removePrefix(path.toString(), (CharSequence) base.toString()), '\\', '/', false, 4, (Object) null));
        }

        @NotNull
        public final List<Pair<FileSystem, Path>> toClasspathRoots(@NotNull ClassLoader classLoader) throws IOException {
            Intrinsics.checkNotNullParameter(classLoader, "<this>");
            Enumeration<URL> resources = classLoader.getResources("");
            Intrinsics.checkNotNullExpressionValue(resources, "getResources(\"\")");
            ArrayList<URL> list = Collections.list(resources);
            Intrinsics.checkNotNullExpressionValue(list, "list(this)");
            ArrayList arrayList = new ArrayList();
            for (URL it : list) {
                Companion companion = ResourceFileSystem.Companion;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                Pair<FileSystem, Path> fileRoot = companion.toFileRoot(it);
                if (fileRoot != null) {
                    arrayList.add(fileRoot);
                }
            }
            Enumeration<URL> resources2 = classLoader.getResources("META-INF/MANIFEST.MF");
            Intrinsics.checkNotNullExpressionValue(resources2, "getResources(\"META-INF/MANIFEST.MF\")");
            ArrayList<URL> list2 = Collections.list(resources2);
            Intrinsics.checkNotNullExpressionValue(list2, "list(this)");
            ArrayList arrayList2 = new ArrayList();
            for (URL it2 : list2) {
                Companion companion2 = ResourceFileSystem.Companion;
                Intrinsics.checkNotNullExpressionValue(it2, "it");
                Pair<FileSystem, Path> jarRoot = companion2.toJarRoot(it2);
                if (jarRoot != null) {
                    arrayList2.add(jarRoot);
                }
            }
            return CollectionsKt___CollectionsKt.plus((Collection) arrayList, (Iterable) arrayList2);
        }

        @Nullable
        public final Pair<FileSystem, Path> toFileRoot(@NotNull URL url) {
            Intrinsics.checkNotNullParameter(url, "<this>");
            if (Intrinsics.areEqual(url.getProtocol(), "file")) {
                return TuplesKt.to(FileSystem.SYSTEM, Path.Companion.get$default(Path.INSTANCE, new File(url.toURI()), false, 1, (Object) null));
            }
            return null;
        }

        @Nullable
        public final Pair<FileSystem, Path> toJarRoot(@NotNull URL url) {
            int iLastIndexOf$default;
            Intrinsics.checkNotNullParameter(url, "<this>");
            String string = url.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString()");
            if (!StringsKt__StringsJVMKt.startsWith$default(string, "jar:file:", false, 2, null) || (iLastIndexOf$default = StringsKt__StringsKt.lastIndexOf$default((CharSequence) string, "!", 0, false, 6, (Object) null)) == -1) {
                return null;
            }
            Path.Companion companion = Path.INSTANCE;
            String strSubstring = string.substring(4, iLastIndexOf$default);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
            return TuplesKt.to(ZipKt.openZip(Path.Companion.get$default(companion, new File(URI.create(strSubstring)), false, 1, (Object) null), FileSystem.SYSTEM, new Function1<ZipEntry, Boolean>() { // from class: okio.internal.ResourceFileSystem$Companion$toJarRoot$zip$1
                @Override // kotlin.jvm.functions.Function1
                @NotNull
                public final Boolean invoke(@NotNull ZipEntry entry) {
                    Intrinsics.checkNotNullParameter(entry, "entry");
                    return Boolean.valueOf(ResourceFileSystem.Companion.keepPath(entry.getCanonicalPath()));
                }
            }), getROOT());
        }
    }

    public ResourceFileSystem(@NotNull final ClassLoader classLoader, boolean z2) {
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        this.roots = LazyKt__LazyJVMKt.lazy(new Function0<List<? extends Pair<? extends FileSystem, ? extends Path>>>() { // from class: okio.internal.ResourceFileSystem$roots$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final List<? extends Pair<? extends FileSystem, ? extends Path>> invoke() {
                return ResourceFileSystem.Companion.toClasspathRoots(classLoader);
            }
        });
        if (z2) {
            getRoots().size();
        }
    }

    private final Path canonicalizeInternal(Path path) {
        return ROOT.resolve(path, true);
    }

    private final List<Pair<FileSystem, Path>> getRoots() {
        return (List) this.roots.getValue();
    }

    private final String toRelativePath(Path path) {
        return canonicalizeInternal(path).relativeTo(ROOT).toString();
    }

    @Override // okio.FileSystem
    @NotNull
    public Sink appendingSink(@NotNull Path file, boolean mustExist) throws IOException {
        Intrinsics.checkNotNullParameter(file, "file");
        throw new IOException(this + " is read-only");
    }

    @Override // okio.FileSystem
    public void atomicMove(@NotNull Path source, @NotNull Path target) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(target, "target");
        throw new IOException(this + " is read-only");
    }

    @Override // okio.FileSystem
    @NotNull
    public Path canonicalize(@NotNull Path path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return canonicalizeInternal(path);
    }

    @Override // okio.FileSystem
    public void createDirectory(@NotNull Path dir, boolean mustCreate) throws IOException {
        Intrinsics.checkNotNullParameter(dir, "dir");
        throw new IOException(this + " is read-only");
    }

    @Override // okio.FileSystem
    public void createSymlink(@NotNull Path source, @NotNull Path target) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(target, "target");
        throw new IOException(this + " is read-only");
    }

    @Override // okio.FileSystem
    public void delete(@NotNull Path path, boolean mustExist) throws IOException {
        Intrinsics.checkNotNullParameter(path, "path");
        throw new IOException(this + " is read-only");
    }

    @Override // okio.FileSystem
    @NotNull
    public List<Path> list(@NotNull Path dir) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter(dir, "dir");
        String relativePath = toRelativePath(dir);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        boolean z2 = false;
        for (Pair<FileSystem, Path> pair : getRoots()) {
            FileSystem fileSystemComponent1 = pair.component1();
            Path pathComponent2 = pair.component2();
            try {
                List<Path> list = fileSystemComponent1.list(pathComponent2.resolve(relativePath));
                ArrayList arrayList = new ArrayList();
                for (Object obj : list) {
                    if (Companion.keepPath((Path) obj)) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    arrayList2.add(Companion.removeBase((Path) it.next(), pathComponent2));
                }
                CollectionsKt__MutableCollectionsKt.addAll(linkedHashSet, arrayList2);
                z2 = true;
            } catch (IOException unused) {
            }
        }
        if (z2) {
            return CollectionsKt___CollectionsKt.toList(linkedHashSet);
        }
        throw new FileNotFoundException("file not found: " + dir);
    }

    @Override // okio.FileSystem
    @Nullable
    public List<Path> listOrNull(@NotNull Path dir) {
        Intrinsics.checkNotNullParameter(dir, "dir");
        String relativePath = toRelativePath(dir);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<Pair<FileSystem, Path>> it = getRoots().iterator();
        boolean z2 = false;
        while (true) {
            ArrayList arrayList = null;
            if (!it.hasNext()) {
                break;
            }
            Pair<FileSystem, Path> next = it.next();
            FileSystem fileSystemComponent1 = next.component1();
            Path pathComponent2 = next.component2();
            List<Path> listListOrNull = fileSystemComponent1.listOrNull(pathComponent2.resolve(relativePath));
            if (listListOrNull != null) {
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : listListOrNull) {
                    if (Companion.keepPath((Path) obj)) {
                        arrayList2.add(obj);
                    }
                }
                ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    arrayList3.add(Companion.removeBase((Path) it2.next(), pathComponent2));
                }
                arrayList = arrayList3;
            }
            if (arrayList != null) {
                CollectionsKt__MutableCollectionsKt.addAll(linkedHashSet, arrayList);
                z2 = true;
            }
        }
        if (z2) {
            return CollectionsKt___CollectionsKt.toList(linkedHashSet);
        }
        return null;
    }

    @Override // okio.FileSystem
    @Nullable
    public FileMetadata metadataOrNull(@NotNull Path path) throws IOException {
        Intrinsics.checkNotNullParameter(path, "path");
        if (!Companion.keepPath(path)) {
            return null;
        }
        String relativePath = toRelativePath(path);
        for (Pair<FileSystem, Path> pair : getRoots()) {
            FileMetadata fileMetadataMetadataOrNull = pair.component1().metadataOrNull(pair.component2().resolve(relativePath));
            if (fileMetadataMetadataOrNull != null) {
                return fileMetadataMetadataOrNull;
            }
        }
        return null;
    }

    @Override // okio.FileSystem
    @NotNull
    public FileHandle openReadOnly(@NotNull Path file) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter(file, "file");
        if (!Companion.keepPath(file)) {
            throw new FileNotFoundException("file not found: " + file);
        }
        String relativePath = toRelativePath(file);
        for (Pair<FileSystem, Path> pair : getRoots()) {
            try {
                return pair.component1().openReadOnly(pair.component2().resolve(relativePath));
            } catch (FileNotFoundException unused) {
            }
        }
        throw new FileNotFoundException("file not found: " + file);
    }

    @Override // okio.FileSystem
    @NotNull
    public FileHandle openReadWrite(@NotNull Path file, boolean mustCreate, boolean mustExist) throws IOException {
        Intrinsics.checkNotNullParameter(file, "file");
        throw new IOException("resources are not writable");
    }

    @Override // okio.FileSystem
    @NotNull
    public Sink sink(@NotNull Path file, boolean mustCreate) throws IOException {
        Intrinsics.checkNotNullParameter(file, "file");
        throw new IOException(this + " is read-only");
    }

    @Override // okio.FileSystem
    @NotNull
    public Source source(@NotNull Path file) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter(file, "file");
        if (!Companion.keepPath(file)) {
            throw new FileNotFoundException("file not found: " + file);
        }
        String relativePath = toRelativePath(file);
        for (Pair<FileSystem, Path> pair : getRoots()) {
            try {
                return pair.component1().source(pair.component2().resolve(relativePath));
            } catch (FileNotFoundException unused) {
            }
        }
        throw new FileNotFoundException("file not found: " + file);
    }
}
