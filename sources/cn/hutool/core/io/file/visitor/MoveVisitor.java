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
import java.nio.file.attribute.FileAttribute;

/* loaded from: classes.dex */
public class MoveVisitor extends SimpleFileVisitor<Path> {
    private final CopyOption[] copyOptions;
    private boolean isTargetCreated;
    private final Path source;
    private final Path target;

    public MoveVisitor(Path path, Path path2, CopyOption... copyOptionArr) {
        if (PathUtil.exists(path2, false) && !PathUtil.isDirectory(path2)) {
            throw new IllegalArgumentException("Target must be a directory");
        }
        this.source = path;
        this.target = path2;
        this.copyOptions = copyOptionArr;
    }

    private void initTarget() throws IOException {
        if (this.isTargetCreated) {
            return;
        }
        PathUtil.mkdir(this.target);
        this.isTargetCreated = true;
    }

    @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
    public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        initTarget();
        Path pathResolve = this.target.resolve(this.source.relativize(path));
        if (!Files.exists(pathResolve, new LinkOption[0])) {
            Files.createDirectories(pathResolve, new FileAttribute[0]);
        } else if (!Files.isDirectory(pathResolve, new LinkOption[0])) {
            throw new FileAlreadyExistsException(pathResolve.toString());
        }
        return FileVisitResult.CONTINUE;
    }

    @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
    public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        initTarget();
        Files.move(path, this.target.resolve(this.source.relativize(path)), this.copyOptions);
        return FileVisitResult.CONTINUE;
    }
}
