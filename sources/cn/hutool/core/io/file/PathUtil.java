package cn.hutool.core.io.file;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.visitor.CopyVisitor;
import cn.hutool.core.io.file.visitor.DelVisitor;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.CharsetUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.AccessDeniedException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/* loaded from: classes.dex */
public class PathUtil {
    public static Path copy(Path path, Path path2, CopyOption... copyOptionArr) throws IllegalArgumentException, IORuntimeException {
        Assert.notNull(path, "Src path must be not null !", new Object[0]);
        Assert.notNull(path2, "Target path must be not null !", new Object[0]);
        return isDirectory(path) ? copyContent(path, path2.resolve(path.getFileName()), copyOptionArr) : copyFile(path, path2, copyOptionArr);
    }

    public static Path copyContent(Path path, Path path2, CopyOption... copyOptionArr) throws IOException, IllegalArgumentException, IORuntimeException {
        Assert.notNull(path, "Src path must be not null !", new Object[0]);
        Assert.notNull(path2, "Target path must be not null !", new Object[0]);
        try {
            Files.walkFileTree(path, new CopyVisitor(path, path2, copyOptionArr));
            return path2;
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static Path copyFile(Path path, Path path2, StandardCopyOption... standardCopyOptionArr) throws IORuntimeException {
        return copyFile(path, path2, (CopyOption[]) standardCopyOptionArr);
    }

    public static Path createTempFile(String str, String str2, Path path) throws IORuntimeException {
        int i2 = 0;
        do {
            try {
                return path == null ? Files.createTempFile(str, str2, new FileAttribute[0]) : Files.createTempFile(mkdir(path), str, str2, new FileAttribute[0]);
            } catch (IOException e2) {
                i2++;
            }
        } while (i2 < 50);
        throw new IORuntimeException(e2);
    }

    public static boolean del(Path path) throws IORuntimeException {
        if (Files.notExists(path, new LinkOption[0])) {
            return true;
        }
        try {
            if (isDirectory(path)) {
                Files.walkFileTree(path, DelVisitor.INSTANCE);
            } else {
                delFile(path);
            }
            return true;
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static void delFile(Path path) throws IOException {
        try {
            Files.delete(path);
        } catch (AccessDeniedException e2) {
            if (!path.toFile().delete()) {
                throw e2;
            }
        }
    }

    public static boolean equals(Path path, Path path2) throws IORuntimeException {
        try {
            return Files.isSameFile(path, path2);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static boolean exists(Path path, boolean z2) {
        return Files.exists(path, z2 ? new LinkOption[0] : new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
    }

    public static BasicFileAttributes getAttributes(Path path, boolean z2) throws IORuntimeException {
        if (path == null) {
            return null;
        }
        try {
            return Files.readAttributes(path, BasicFileAttributes.class, z2 ? new LinkOption[0] : new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static BufferedInputStream getInputStream(Path path) throws IORuntimeException {
        try {
            return IoUtil.toBuffered(Files.newInputStream(path, new OpenOption[0]));
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static Path getLastPathEle(Path path) {
        return getPathEle(path, path.getNameCount() - 1);
    }

    public static String getMimeType(Path path) {
        try {
            return Files.probeContentType(path);
        } catch (IOException unused) {
            return null;
        }
    }

    public static String getName(Path path) {
        if (path == null) {
            return null;
        }
        return path.getFileName().toString();
    }

    public static BufferedOutputStream getOutputStream(Path path) throws IORuntimeException {
        try {
            return IoUtil.toBuffered(Files.newOutputStream(path, new OpenOption[0]));
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static Path getPathEle(Path path, int i2) {
        return subPath(path, i2, i2 == -1 ? path.getNameCount() : i2 + 1);
    }

    public static BufferedReader getReader(Path path, Charset charset) throws IORuntimeException {
        return IoUtil.getReader(getInputStream(path), charset);
    }

    public static BufferedReader getUtf8Reader(Path path) throws IORuntimeException {
        return getReader(path, CharsetUtil.CHARSET_UTF_8);
    }

    public static boolean isDirEmpty(Path path) throws IOException {
        try {
            DirectoryStream directoryStreamNewDirectoryStream = Files.newDirectoryStream(path);
            try {
                boolean z2 = !directoryStreamNewDirectoryStream.iterator().hasNext();
                if (directoryStreamNewDirectoryStream != null) {
                    directoryStreamNewDirectoryStream.close();
                }
                return z2;
            } finally {
            }
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static boolean isDirectory(Path path) {
        return isDirectory(path, false);
    }

    public static boolean isExistsAndNotDirectory(Path path, boolean z2) {
        return exists(path, z2) && !isDirectory(path, z2);
    }

    public static boolean isFile(Path path, boolean z2) {
        if (path == null) {
            return false;
        }
        return Files.isRegularFile(path, z2 ? new LinkOption[0] : new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
    }

    public static boolean isSub(Path path, Path path2) {
        return toAbsNormal(path2).startsWith(toAbsNormal(path));
    }

    public static boolean isSymlink(Path path) {
        return Files.isSymbolicLink(path);
    }

    public static List<File> loopFiles(Path path, FileFilter fileFilter) {
        return loopFiles(path, -1, fileFilter);
    }

    public static Path mkParentDirs(Path path) {
        return mkdir(path.getParent());
    }

    public static Path mkdir(Path path) throws IOException {
        if (path != null && !exists(path, false)) {
            try {
                Files.createDirectories(path, new FileAttribute[0]);
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        }
        return path;
    }

    public static Path move(Path path, Path path2, boolean z2) {
        return PathMover.of(path, path2, z2).move();
    }

    public static Path moveContent(Path path, Path path2, boolean z2) {
        return PathMover.of(path, path2, z2).moveContent();
    }

    public static byte[] readBytes(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static Path rename(Path path, String str, boolean z2) {
        return move(path, path.resolveSibling(str), z2);
    }

    public static Path subPath(Path path, int i2, int i3) {
        if (path == null) {
            return null;
        }
        int nameCount = path.getNameCount();
        if (i2 < 0) {
            i2 += nameCount;
            if (i2 < 0) {
                i2 = 0;
            }
        } else if (i2 > nameCount) {
            i2 = nameCount;
        }
        if (i3 >= 0 ? i3 > nameCount : (i3 = i3 + nameCount) < 0) {
            i3 = nameCount;
        }
        if (i3 < i2) {
            int i4 = i3;
            i3 = i2;
            i2 = i4;
        }
        if (i2 == i3) {
            return null;
        }
        return path.subpath(i2, i3);
    }

    public static Path toAbsNormal(Path path) throws IllegalArgumentException {
        Assert.notNull(path);
        return path.toAbsolutePath().normalize();
    }

    public static void walkFiles(Path path, FileVisitor<? super Path> fileVisitor) throws IOException {
        walkFiles(path, -1, fileVisitor);
    }

    public static Path copyFile(Path path, Path path2, CopyOption... copyOptionArr) throws IllegalArgumentException, IORuntimeException {
        Assert.notNull(path, "Source File is null !", new Object[0]);
        Assert.notNull(path2, "Destination File or directory is null !", new Object[0]);
        if (isDirectory(path2)) {
            path2 = path2.resolve(path.getFileName());
        }
        mkParentDirs(path2);
        try {
            return Files.copy(path, path2, copyOptionArr);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static boolean isDirectory(Path path, boolean z2) {
        if (path == null) {
            return false;
        }
        return Files.isDirectory(path, z2 ? new LinkOption[0] : new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
    }

    public static List<File> loopFiles(Path path, int i2, final FileFilter fileFilter) throws IOException {
        final ArrayList arrayList = new ArrayList();
        if (path != null && Files.exists(path, new LinkOption[0])) {
            if (!isDirectory(path)) {
                File file = path.toFile();
                if (fileFilter == null || fileFilter.accept(file)) {
                    arrayList.add(file);
                }
                return arrayList;
            }
            walkFiles(path, i2, new SimpleFileVisitor<Path>() { // from class: cn.hutool.core.io.file.PathUtil.1
                @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                public FileVisitResult visitFile(Path path2, BasicFileAttributes basicFileAttributes) {
                    File file2 = path2.toFile();
                    FileFilter fileFilter2 = fileFilter;
                    if (fileFilter2 == null || fileFilter2.accept(file2)) {
                        arrayList.add(file2);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        return arrayList;
    }

    public static void walkFiles(Path path, int i2, FileVisitor<? super Path> fileVisitor) throws IOException {
        if (i2 < 0) {
            i2 = Integer.MAX_VALUE;
        }
        try {
            Files.walkFileTree(path, EnumSet.noneOf(FileVisitOption.class), i2, fileVisitor);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }
}
