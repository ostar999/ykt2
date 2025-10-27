package cn.hutool.core.io.file;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.file.visitor.MoveVisitor;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/* loaded from: classes.dex */
public class PathMover {
    private final CopyOption[] options;
    private final Path src;
    private final Path target;

    public PathMover(Path path, Path path2, CopyOption[] copyOptionArr) throws IllegalArgumentException {
        Assert.notNull(path2, "Src path must be not null !", new Object[0]);
        if (!PathUtil.exists(path, false)) {
            throw new IllegalArgumentException("Src path is not exist!");
        }
        this.src = path;
        this.target = (Path) Assert.notNull(path2, "Target path must be not null !", new Object[0]);
        this.options = (CopyOption[]) ObjectUtil.defaultIfNull(copyOptionArr, new CopyOption[0]);
    }

    public static PathMover of(Path path, Path path2, boolean z2) {
        return of(path, path2, z2 ? new CopyOption[]{StandardCopyOption.REPLACE_EXISTING} : new CopyOption[0]);
    }

    private static void walkMove(Path path, Path path2, CopyOption... copyOptionArr) throws IOException {
        try {
            Files.walkFileTree(path, new MoveVisitor(path, path2, copyOptionArr));
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public Path move() throws IOException, IORuntimeException {
        Path path = this.src;
        Path pathResolve = this.target;
        CopyOption[] copyOptionArr = this.options;
        if (PathUtil.isSub(path, pathResolve)) {
            if (Files.exists(pathResolve, new LinkOption[0]) && PathUtil.equals(path, pathResolve)) {
                return pathResolve;
            }
            throw new IllegalArgumentException(CharSequenceUtil.format("Target [{}] is sub path of src [{}]!", pathResolve, path));
        }
        if (PathUtil.isDirectory(pathResolve)) {
            pathResolve = pathResolve.resolve(path.getFileName());
        }
        PathUtil.mkParentDirs(pathResolve);
        try {
            return Files.move(path, pathResolve, copyOptionArr);
        } catch (IOException e2) {
            if (e2 instanceof FileAlreadyExistsException) {
                throw new IORuntimeException(e2);
            }
            walkMove(path, pathResolve, copyOptionArr);
            PathUtil.del(path);
            return pathResolve;
        }
    }

    public Path moveContent() throws IOException {
        Path path = this.src;
        if (PathUtil.isExistsAndNotDirectory(this.target, false)) {
            return move();
        }
        Path path2 = this.target;
        if (PathUtil.isExistsAndNotDirectory(path2, false)) {
            throw new IllegalArgumentException("Can not move dir content to a file");
        }
        if (PathUtil.equals(path, path2)) {
            return path2;
        }
        CopyOption[] copyOptionArr = this.options;
        PathUtil.mkParentDirs(path2);
        walkMove(path, path2, copyOptionArr);
        return path2;
    }

    public static PathMover of(Path path, Path path2, CopyOption[] copyOptionArr) {
        return new PathMover(path, path2, copyOptionArr);
    }
}
