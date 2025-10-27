package cn.hutool.core.io.file.visitor;

import cn.hutool.core.io.file.PathUtil;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/* loaded from: classes.dex */
public class CopyVisitor extends SimpleFileVisitor<Path> {
    private final CopyOption[] copyOptions;
    private boolean isTargetCreated;
    private final Path source;
    private final Path target;

    public CopyVisitor(Path path, Path path2, CopyOption... copyOptionArr) {
        if (PathUtil.exists(path2, false) && !PathUtil.isDirectory(path2)) {
            throw new IllegalArgumentException("Target must be a directory");
        }
        this.source = path;
        this.target = path2;
        this.copyOptions = copyOptionArr;
    }

    private void initTargetDir() throws IOException {
        if (this.isTargetCreated) {
            return;
        }
        PathUtil.mkdir(this.target);
        this.isTargetCreated = true;
    }

    private Path resolveTarget(Path path) {
        return this.target.resolve(this.source.relativize(path));
    }

    @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
    public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        initTargetDir();
        Path pathResolveTarget = resolveTarget(path);
        try {
            Files.copy(path, pathResolveTarget, this.copyOptions);
        } catch (FileAlreadyExistsException e2) {
            if (!Files.isDirectory(pathResolveTarget, new LinkOption[0])) {
                throw e2;
            }
        }
        return FileVisitResult.CONTINUE;
    }

    @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
    public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        initTargetDir();
        Files.copy(path, resolveTarget(path), this.copyOptions);
        return FileVisitResult.CONTINUE;
    }
}
