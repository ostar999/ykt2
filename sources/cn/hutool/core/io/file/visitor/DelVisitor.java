package cn.hutool.core.io.file.visitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/* loaded from: classes.dex */
public class DelVisitor extends SimpleFileVisitor<Path> {
    public static DelVisitor INSTANCE = new DelVisitor();

    @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
    public FileVisitResult postVisitDirectory(Path path, IOException iOException) throws IOException {
        if (iOException != null) {
            throw iOException;
        }
        Files.delete(path);
        return FileVisitResult.CONTINUE;
    }

    @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
    public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        Files.delete(path);
        return FileVisitResult.CONTINUE;
    }
}
