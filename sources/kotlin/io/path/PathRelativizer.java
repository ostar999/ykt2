package kotlin.io.path;

import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import java.nio.file.Path;
import java.nio.file.Paths;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt___StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lkotlin/io/path/PathRelativizer;", "", "()V", "emptyPath", "Ljava/nio/file/Path;", "kotlin.jvm.PlatformType", "parentPath", "tryRelativeTo", "path", TtmlNode.RUBY_BASE, "kotlin-stdlib-jdk7"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
final class PathRelativizer {

    @NotNull
    public static final PathRelativizer INSTANCE = new PathRelativizer();
    private static final Path emptyPath = Paths.get("", new String[0]);
    private static final Path parentPath = Paths.get(StrPool.DOUBLE_DOT, new String[0]);

    private PathRelativizer() {
    }

    @NotNull
    public final Path tryRelativeTo(@NotNull Path path, @NotNull Path base) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(base, "base");
        Path pathNormalize = base.normalize();
        Path r2 = path.normalize();
        Path pathRelativize = pathNormalize.relativize(r2);
        int iMin = Math.min(pathNormalize.getNameCount(), r2.getNameCount());
        for (int i2 = 0; i2 < iMin; i2++) {
            Path name = pathNormalize.getName(i2);
            Path path2 = parentPath;
            if (!Intrinsics.areEqual(name, path2)) {
                break;
            }
            if (!Intrinsics.areEqual(r2.getName(i2), path2)) {
                throw new IllegalArgumentException("Unable to compute relative path");
            }
        }
        if (Intrinsics.areEqual(r2, pathNormalize) || !Intrinsics.areEqual(pathNormalize, emptyPath)) {
            String string = pathRelativize.toString();
            String separator = pathRelativize.getFileSystem().getSeparator();
            Intrinsics.checkNotNullExpressionValue(separator, "rn.fileSystem.separator");
            r2 = StringsKt__StringsJVMKt.endsWith$default(string, separator, false, 2, null) ? pathRelativize.getFileSystem().getPath(StringsKt___StringsKt.dropLast(string, pathRelativize.getFileSystem().getSeparator().length()), new String[0]) : pathRelativize;
        }
        Intrinsics.checkNotNullExpressionValue(r2, "r");
        return r2;
    }
}
