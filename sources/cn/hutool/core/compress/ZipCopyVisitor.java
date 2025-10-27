package cn.hutool.core.compress;

import cn.hutool.core.text.CharSequenceUtil;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/* loaded from: classes.dex */
public class ZipCopyVisitor extends SimpleFileVisitor<Path> {
    private final CopyOption[] copyOptions;
    private final FileSystem fileSystem;
    private final Path source;

    public ZipCopyVisitor(Path path, FileSystem fileSystem, CopyOption... copyOptionArr) {
        this.source = path;
        this.fileSystem = fileSystem;
        this.copyOptions = copyOptionArr;
    }

    private Path resolveTarget(Path path) {
        return this.fileSystem.getPath(this.source.relativize(path).toString(), new String[0]);
    }

    @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
    public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        Path pathResolveTarget = resolveTarget(path);
        if (CharSequenceUtil.isNotEmpty(pathResolveTarget.toString())) {
            try {
                Files.copy(path, pathResolveTarget, this.copyOptions);
            } catch (DirectoryNotEmptyException unused) {
            } catch (FileAlreadyExistsException e2) {
                if (!Files.isDirectory(pathResolveTarget, new LinkOption[0])) {
                    throw e2;
                }
            }
        }
        return FileVisitResult.CONTINUE;
    }

    @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
    public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        Files.copy(path, resolveTarget(path), this.copyOptions);
        return FileVisitResult.CONTINUE;
    }
}
