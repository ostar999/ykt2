package cn.hutool.core.io;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.file.FileCopier;
import cn.hutool.core.io.file.FileMode;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.io.file.LineSeparator;
import cn.hutool.core.io.file.PathUtil;
import cn.hutool.core.io.file.Tailer;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.io.unit.DataSizeUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.core.util.ZipUtil;
import com.luck.picture.lib.config.PictureMimeType;
import com.psychiatrygarden.utils.MimeTypes;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/* loaded from: classes.dex */
public class FileUtil extends PathUtil {
    public static final String CLASS_EXT = ".class";
    public static final String JAR_FILE_EXT = ".jar";
    public static final String JAR_PATH_EXT = ".jar!";
    public static final String PATH_FILE_PRE = "file:";
    public static final String FILE_SEPARATOR = File.separator;
    public static final String PATH_SEPARATOR = File.pathSeparator;
    private static final Pattern PATTERN_PATH_ABSOLUTE = Pattern.compile("^[a-zA-Z]:([/\\\\].*)?");

    public static <T> File appendLines(Collection<T> collection, String str, String str2) throws IORuntimeException {
        return writeLines((Collection) collection, str, str2, true);
    }

    public static File appendString(String str, String str2, String str3) throws IORuntimeException {
        return appendString(str, touch(str2), str3);
    }

    public static <T> File appendUtf8Lines(Collection<T> collection, File file) throws IORuntimeException {
        return appendLines(collection, file, CharsetUtil.CHARSET_UTF_8);
    }

    public static File appendUtf8String(String str, String str2) throws IORuntimeException {
        return appendString(str, str2, CharsetUtil.CHARSET_UTF_8);
    }

    private static File buildFile(File file, String str) {
        String strReplace = str.replace('\\', '/');
        if (!isWindows() && strReplace.lastIndexOf(47, strReplace.length() - 2) > 0) {
            int i2 = 0;
            List<String> listSplit = CharSequenceUtil.split((CharSequence) strReplace, '/', false, true);
            int size = listSplit.size() - 1;
            while (i2 < size) {
                File file2 = new File(file, listSplit.get(i2));
                i2++;
                file = file2;
            }
            file.mkdirs();
            strReplace = listSplit.get(size);
        }
        return new File(file, strReplace);
    }

    public static File checkSlip(File file, File file2) throws IllegalArgumentException {
        if (file == null || file2 == null || isSub(file, file2)) {
            return file2;
        }
        throw new IllegalArgumentException("New file is outside of the parent dir: " + file2.getName());
    }

    public static Checksum checksum(File file, Checksum checksum) throws IllegalArgumentException, IORuntimeException {
        Assert.notNull(file, "File is null !", new Object[0]);
        if (file.isDirectory()) {
            throw new IllegalArgumentException("Checksums can't be computed on directories");
        }
        try {
            return IoUtil.checksum(Files.newInputStream(file.toPath(), new OpenOption[0]), checksum);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static long checksumCRC32(File file) throws IORuntimeException {
        return checksum(file, new CRC32()).getValue();
    }

    public static boolean clean(String str) throws IORuntimeException {
        return clean(file(str));
    }

    public static boolean cleanEmpty(File file) throws IORuntimeException {
        if (file != null && file.exists() && file.isDirectory()) {
            File[] fileArrListFiles = file.listFiles();
            if (ArrayUtil.isEmpty((Object[]) fileArrListFiles)) {
                return file.delete();
            }
            for (File file2 : fileArrListFiles) {
                cleanEmpty(file2);
            }
        }
        return true;
    }

    public static String cleanInvalid(String str) {
        return FileNameUtil.cleanInvalid(str);
    }

    public static boolean containsInvalid(String str) {
        return FileNameUtil.containsInvalid(str);
    }

    public static boolean contentEquals(File file, File file2) throws Throwable {
        BufferedInputStream bufferedInputStream;
        BufferedInputStream inputStream;
        boolean zExists = file.exists();
        if (zExists != file2.exists()) {
            return false;
        }
        if (!zExists) {
            return true;
        }
        if (file.isDirectory() || file2.isDirectory()) {
            throw new IORuntimeException("Can't compare directories, only files");
        }
        if (file.length() != file2.length()) {
            return false;
        }
        if (equals(file, file2)) {
            return true;
        }
        BufferedInputStream inputStream2 = null;
        try {
            inputStream = getInputStream(file);
        } catch (Throwable th) {
            th = th;
            bufferedInputStream = null;
        }
        try {
            inputStream2 = getInputStream(file2);
            boolean zContentEquals = IoUtil.contentEquals(inputStream, inputStream2);
            IoUtil.close((Closeable) inputStream);
            IoUtil.close((Closeable) inputStream2);
            return zContentEquals;
        } catch (Throwable th2) {
            th = th2;
            BufferedInputStream bufferedInputStream2 = inputStream2;
            inputStream2 = inputStream;
            bufferedInputStream = bufferedInputStream2;
            IoUtil.close((Closeable) inputStream2);
            IoUtil.close((Closeable) bufferedInputStream);
            throw th;
        }
    }

    public static boolean contentEqualsIgnoreEOL(File file, File file2, Charset charset) throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader reader;
        boolean zExists = file.exists();
        if (zExists != file2.exists()) {
            return false;
        }
        if (!zExists) {
            return true;
        }
        if (file.isDirectory() || file2.isDirectory()) {
            throw new IORuntimeException("Can't compare directories, only files");
        }
        if (equals(file, file2)) {
            return true;
        }
        BufferedReader reader2 = null;
        try {
            reader = getReader(file, charset);
        } catch (Throwable th) {
            th = th;
            bufferedReader = null;
        }
        try {
            reader2 = getReader(file2, charset);
            boolean zContentEqualsIgnoreEOL = IoUtil.contentEqualsIgnoreEOL(reader, reader2);
            IoUtil.close((Closeable) reader);
            IoUtil.close((Closeable) reader2);
            return zContentEqualsIgnoreEOL;
        } catch (Throwable th2) {
            th = th2;
            BufferedReader bufferedReader2 = reader2;
            reader2 = reader;
            bufferedReader = bufferedReader2;
            IoUtil.close((Closeable) reader2);
            IoUtil.close((Closeable) bufferedReader);
            throw th;
        }
    }

    public static File convertCharset(File file, Charset charset, Charset charset2) {
        return CharsetUtil.convert(file, charset, charset2);
    }

    public static File convertLineSeparator(File file, Charset charset, LineSeparator lineSeparator) throws IORuntimeException {
        return FileWriter.create(file, charset).writeLines(readLines(file, charset), lineSeparator, false);
    }

    public static File copy(String str, String str2, boolean z2) throws IORuntimeException {
        return copy(file(str), file(str2), z2);
    }

    public static File copyContent(File file, File file2, boolean z2) throws IORuntimeException {
        return FileCopier.create(file, file2).setCopyContentIfDir(true).setOverride(z2).copy();
    }

    public static File copyFile(String str, String str2, StandardCopyOption... standardCopyOptionArr) throws IllegalArgumentException, IORuntimeException {
        Assert.notBlank(str, "Source File path is blank !", new Object[0]);
        Assert.notBlank(str2, "Destination File path is blank !", new Object[0]);
        return PathUtil.copyFile(Paths.get(str, new String[0]), Paths.get(str2, new String[0]), standardCopyOptionArr).toFile();
    }

    public static File copyFilesFromDir(File file, File file2, boolean z2) throws IORuntimeException {
        return FileCopier.create(file, file2).setCopyContentIfDir(true).setOnlyCopyFile(true).setOverride(z2).copy();
    }

    public static RandomAccessFile createRandomAccessFile(Path path, FileMode fileMode) {
        return createRandomAccessFile(path.toFile(), fileMode);
    }

    public static File createTempFile(File file) throws IORuntimeException {
        return createTempFile("hutool", null, file, true);
    }

    public static boolean del(String str) throws IORuntimeException {
        return del(file(str));
    }

    public static boolean equals(File file, File file2) throws IllegalArgumentException, IORuntimeException {
        Assert.notNull(file);
        Assert.notNull(file2);
        return (file.exists() && file2.exists()) ? PathUtil.equals(file.toPath(), file2.toPath()) : (file.exists() || file2.exists() || !pathEquals(file, file2)) ? false : true;
    }

    public static boolean exist(String str) {
        return str != null && file(str).exists();
    }

    public static String extName(File file) {
        return FileNameUtil.extName(file);
    }

    public static File file(String str) {
        if (str == null) {
            return null;
        }
        return new File(getAbsolutePath(str));
    }

    public static String getAbsolutePath(String str, Class<?> cls) {
        String strNormalize;
        if (str == null) {
            strNormalize = "";
        } else {
            strNormalize = normalize(str);
            if (isAbsolutePath(strNormalize)) {
                return strNormalize;
            }
        }
        URL resource = ResourceUtil.getResource(strNormalize, cls);
        if (resource != null) {
            return normalize(URLUtil.getDecodedPath(resource));
        }
        String classPath = ClassUtil.getClassPath();
        if (classPath == null) {
            return str;
        }
        Objects.requireNonNull(str);
        return normalize(classPath.concat(str));
    }

    public static BOMInputStream getBOMInputStream(File file) throws IORuntimeException {
        try {
            return new BOMInputStream(Files.newInputStream(file.toPath(), new OpenOption[0]));
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static BufferedReader getBOMReader(File file) {
        return IoUtil.getReader(getBOMInputStream(file));
    }

    public static String getCanonicalPath(File file) {
        if (file == null) {
            return null;
        }
        try {
            return file.getCanonicalPath();
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static BufferedInputStream getInputStream(File file) throws IORuntimeException {
        return IoUtil.toBuffered(IoUtil.toStream(file));
    }

    public static String getLineSeparator() {
        return System.lineSeparator();
    }

    public static String getMimeType(String str) {
        if (CharSequenceUtil.isBlank(str)) {
            return null;
        }
        if (CharSequenceUtil.endWithIgnoreCase(str, ".css")) {
            return MimeTypes.TEXT_CSS;
        }
        if (CharSequenceUtil.endWithIgnoreCase(str, ".js")) {
            return "application/x-javascript";
        }
        if (CharSequenceUtil.endWithIgnoreCase(str, ".rar")) {
            return "application/x-rar-compressed";
        }
        if (CharSequenceUtil.endWithIgnoreCase(str, ".7z")) {
            return "application/x-7z-compressed";
        }
        if (CharSequenceUtil.endWithIgnoreCase(str, ".wgt")) {
            return "application/widget";
        }
        if (CharSequenceUtil.endWithIgnoreCase(str, PictureMimeType.WEBP)) {
            return MimeTypes.IMAGE_WEBP;
        }
        String contentTypeFor = URLConnection.getFileNameMap().getContentTypeFor(str);
        return contentTypeFor == null ? PathUtil.getMimeType(Paths.get(str, new String[0])) : contentTypeFor;
    }

    public static String getName(File file) {
        return FileNameUtil.getName(file);
    }

    public static BufferedOutputStream getOutputStream(File file) throws IORuntimeException {
        try {
            return IoUtil.toBuffered(Files.newOutputStream(touch(file).toPath(), new OpenOption[0]));
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static String getParent(String str, int i2) {
        File parent = getParent(file(str), i2);
        if (parent == null) {
            return null;
        }
        try {
            return parent.getCanonicalPath();
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static String getPrefix(File file) {
        return FileNameUtil.getPrefix(file);
    }

    public static PrintWriter getPrintWriter(String str, String str2, boolean z2) throws IORuntimeException {
        return new PrintWriter(getWriter(str, str2, z2));
    }

    @Deprecated
    public static BufferedReader getReader(File file, String str) throws IORuntimeException {
        return IoUtil.getReader(getInputStream(file), CharsetUtil.charset(str));
    }

    public static String getSuffix(File file) {
        return FileNameUtil.getSuffix(file);
    }

    public static File getTmpDir() {
        return file(getTmpDirPath());
    }

    public static String getTmpDirPath() {
        return System.getProperty("java.io.tmpdir");
    }

    public static int getTotalLines(File file) throws IOException {
        if (!isFile(file)) {
            throw new IORuntimeException("Input must be a File");
        }
        try {
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
            try {
                lineNumberReader.setLineNumber(1);
                lineNumberReader.skip(Long.MAX_VALUE);
                int lineNumber = lineNumberReader.getLineNumber();
                lineNumberReader.close();
                return lineNumber;
            } finally {
            }
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static String getType(File file) throws IORuntimeException {
        return FileTypeUtil.getType(file);
    }

    public static File getUserHomeDir() {
        return file(getUserHomePath());
    }

    public static String getUserHomePath() {
        return System.getProperty("user.home");
    }

    public static BufferedReader getUtf8Reader(File file) throws IORuntimeException {
        return getReader(file, CharsetUtil.CHARSET_UTF_8);
    }

    public static File getWebRoot() {
        String classPath = ClassUtil.getClassPath();
        if (CharSequenceUtil.isNotBlank(classPath)) {
            return getParent(file(classPath), 2);
        }
        return null;
    }

    @Deprecated
    public static BufferedWriter getWriter(String str, String str2, boolean z2) throws IORuntimeException {
        return getWriter(str, Charset.forName(str2), z2);
    }

    public static boolean isAbsolutePath(String str) {
        if (CharSequenceUtil.isEmpty(str)) {
            return false;
        }
        return '/' == str.charAt(0) || ReUtil.isMatch(PATTERN_PATH_ABSOLUTE, str);
    }

    public static boolean isDirEmpty(File file) {
        return PathUtil.isDirEmpty(file.toPath());
    }

    public static boolean isDirectory(String str) {
        return str != null && file(str).isDirectory();
    }

    public static boolean isEmpty(File file) {
        if (file == null || !file.exists()) {
            return true;
        }
        return file.isDirectory() ? ArrayUtil.isEmpty((Object[]) file.list()) : file.isFile() && file.length() <= 0;
    }

    public static boolean isFile(String str) {
        return str != null && file(str).isFile();
    }

    @Deprecated
    public static boolean isModifed(File file, long j2) {
        return isModified(file, j2);
    }

    public static boolean isModified(File file, long j2) {
        return (file != null && file.exists() && file.lastModified() == j2) ? false : true;
    }

    public static boolean isNotEmpty(File file) {
        return !isEmpty(file);
    }

    public static boolean isSub(File file, File file2) throws IllegalArgumentException {
        Assert.notNull(file);
        Assert.notNull(file2);
        return PathUtil.isSub(file.toPath(), file2.toPath());
    }

    public static boolean isSymlink(File file) {
        return PathUtil.isSymlink(file.toPath());
    }

    public static boolean isWindows() {
        return '\\' == File.separatorChar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$readLines$0(Predicate predicate, List list, String str) {
        if (predicate.test(str)) {
            list.add(str);
        }
    }

    public static int lastIndexOfSeparator(String str) {
        if (CharSequenceUtil.isNotEmpty(str)) {
            int length = str.length();
            do {
                length--;
                if (length >= 0) {
                }
            } while (!CharUtil.isFileSeparator(str.charAt(length)));
            return length;
        }
        return -1;
    }

    public static Date lastModifiedTime(File file) {
        if (exist(file)) {
            return new Date(file.lastModified());
        }
        return null;
    }

    public static List<String> listFileNames(String str) throws Throwable {
        if (str == null) {
            return new ArrayList(0);
        }
        int iLastIndexOf = str.lastIndexOf(JAR_PATH_EXT);
        if (iLastIndexOf < 0) {
            ArrayList arrayList = new ArrayList();
            for (File file : ls(str)) {
                if (file.isFile()) {
                    arrayList.add(file.getName());
                }
            }
            return arrayList;
        }
        String absolutePath = getAbsolutePath(str);
        int i2 = iLastIndexOf + 4;
        JarFile jarFile = null;
        try {
            try {
                JarFile jarFile2 = new JarFile(absolutePath.substring(0, i2));
                try {
                    List<String> listListFileNames = ZipUtil.listFileNames(jarFile2, CharSequenceUtil.removePrefix(absolutePath.substring(i2 + 1), "/"));
                    IoUtil.close((Closeable) jarFile2);
                    return listListFileNames;
                } catch (IOException e2) {
                    e = e2;
                    throw new IORuntimeException(CharSequenceUtil.format("Can not read file path of [{}]", absolutePath), e);
                } catch (Throwable th) {
                    th = th;
                    jarFile = jarFile2;
                    IoUtil.close((Closeable) jarFile);
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static <T> T load(String str, String str2, FileReader.ReaderHandler<T> readerHandler) throws IORuntimeException {
        return (T) cn.hutool.core.io.file.FileReader.create(file(str), CharsetUtil.charset(str2)).read(readerHandler);
    }

    public static <T> T loadUtf8(String str, FileReader.ReaderHandler<T> readerHandler) throws IORuntimeException {
        return (T) load(str, CharsetUtil.CHARSET_UTF_8, readerHandler);
    }

    public static List<File> loopFiles(String str, FileFilter fileFilter) {
        return loopFiles(file(str), fileFilter);
    }

    public static File[] ls(String str) {
        if (str == null) {
            return null;
        }
        File file = file(str);
        if (file.isDirectory()) {
            return file.listFiles();
        }
        throw new IORuntimeException(CharSequenceUtil.format("Path [{}] is not directory!", str));
    }

    public static String mainName(File file) {
        return FileNameUtil.mainName(file);
    }

    public static File mkParentDirs(File file) {
        if (file == null) {
            return null;
        }
        return mkdir(getParent(file, 1));
    }

    public static File mkdir(String str) {
        if (str == null) {
            return null;
        }
        return mkdir(file(str));
    }

    public static boolean mkdirsSafely(File file, int i2, long j2) {
        if (file == null) {
            return false;
        }
        if (file.isDirectory()) {
            return true;
        }
        for (int i3 = 1; i3 <= i2; i3++) {
            file.mkdirs();
            if (file.exists()) {
                return true;
            }
            ThreadUtil.sleep(j2);
        }
        return file.exists();
    }

    public static void move(File file, File file2, boolean z2) throws IllegalArgumentException, IORuntimeException {
        Assert.notNull(file, "Src file must be not null!", new Object[0]);
        Assert.notNull(file2, "target file must be not null!", new Object[0]);
        PathUtil.move(file.toPath(), file2.toPath(), z2);
    }

    public static void moveContent(File file, File file2, boolean z2) throws IllegalArgumentException, IORuntimeException {
        Assert.notNull(file, "Src file must be not null!", new Object[0]);
        Assert.notNull(file2, "target file must be not null!", new Object[0]);
        PathUtil.moveContent(file.toPath(), file2.toPath(), z2);
    }

    public static File newFile(String str) {
        return new File(str);
    }

    public static boolean newerThan(File file, File file2) {
        if (file2 == null || !file2.exists()) {
            return true;
        }
        return newerThan(file, file2.lastModified());
    }

    public static String normalize(String str) {
        if (str == null) {
            return null;
        }
        if (str.startsWith("\\\\")) {
            return str;
        }
        String strRemovePrefixIgnoreCase = CharSequenceUtil.removePrefixIgnoreCase(CharSequenceUtil.removePrefixIgnoreCase(str, URLUtil.CLASSPATH_URL_PREFIX), "file:");
        if (CharSequenceUtil.startWith((CharSequence) strRemovePrefixIgnoreCase, '~')) {
            strRemovePrefixIgnoreCase = getUserHomePath() + strRemovePrefixIgnoreCase.substring(1);
        }
        String strTrimStart = CharSequenceUtil.trimStart(strRemovePrefixIgnoreCase.replaceAll("[/\\\\]+", "/"));
        int iIndexOf = strTrimStart.indexOf(":");
        String str2 = "";
        if (iIndexOf > -1) {
            int i2 = iIndexOf + 1;
            String strSubstring = strTrimStart.substring(0, i2);
            if (CharSequenceUtil.startWith((CharSequence) strSubstring, '/')) {
                strSubstring = strSubstring.substring(1);
            }
            if (!strSubstring.contains("/")) {
                strTrimStart = strTrimStart.substring(i2);
                str2 = strSubstring;
            }
        }
        if (strTrimStart.startsWith("/")) {
            str2 = str2 + "/";
            strTrimStart = strTrimStart.substring(1);
        }
        List<String> listSplit = CharSequenceUtil.split((CharSequence) strTrimStart, '/');
        LinkedList linkedList = new LinkedList();
        int i3 = 0;
        for (int size = listSplit.size() - 1; size >= 0; size--) {
            String str3 = listSplit.get(size);
            if (!StrPool.DOT.equals(str3)) {
                if (StrPool.DOUBLE_DOT.equals(str3)) {
                    i3++;
                } else if (i3 > 0) {
                    i3--;
                } else {
                    linkedList.add(0, str3);
                }
            }
        }
        if (i3 > 0 && CharSequenceUtil.isEmpty(str2)) {
            while (true) {
                int i4 = i3 - 1;
                if (i3 <= 0) {
                    break;
                }
                linkedList.add(0, StrPool.DOUBLE_DOT);
                i3 = i4;
            }
        }
        return str2 + CollUtil.join(linkedList, "/");
    }

    public static boolean pathEndsWith(File file, String str) {
        return file.getPath().toLowerCase().endsWith(str);
    }

    public static boolean pathEquals(File file, File file2) {
        if (isWindows()) {
            try {
                return CharSequenceUtil.equalsIgnoreCase(file.getCanonicalPath(), file2.getCanonicalPath());
            } catch (Exception unused) {
                return CharSequenceUtil.equalsIgnoreCase(file.getAbsolutePath(), file2.getAbsolutePath());
            }
        }
        try {
            return CharSequenceUtil.equals(file.getCanonicalPath(), file2.getCanonicalPath());
        } catch (Exception unused2) {
            return CharSequenceUtil.equals(file.getAbsolutePath(), file2.getAbsolutePath());
        }
    }

    public static byte[] readBytes(File file) throws IORuntimeException {
        return cn.hutool.core.io.file.FileReader.create(file).readBytes();
    }

    public static void readLine(RandomAccessFile randomAccessFile, Charset charset, LineHandler lineHandler) throws IOException {
        String line = readLine(randomAccessFile, charset);
        if (line != null) {
            lineHandler.handle(line);
        }
    }

    public static <T extends Collection<String>> T readLines(String str, String str2, T t2) throws IORuntimeException {
        return (T) readLines(file(str), str2, t2);
    }

    @Deprecated
    public static String readString(File file, String str) throws IORuntimeException {
        return readString(file, CharsetUtil.charset(str));
    }

    public static <T extends Collection<String>> T readUtf8Lines(String str, T t2) throws IORuntimeException {
        return (T) readLines(str, CharsetUtil.CHARSET_UTF_8, t2);
    }

    public static String readUtf8String(File file) throws IORuntimeException {
        return readString(file, CharsetUtil.CHARSET_UTF_8);
    }

    public static String readableFileSize(File file) {
        return readableFileSize(file.length());
    }

    public static File rename(File file, String str, boolean z2) {
        return rename(file, str, false, z2);
    }

    public static long size(File file) {
        return size(file, false);
    }

    public static String subPath(String str, File file) {
        try {
            return subPath(str, file.getCanonicalPath());
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static void tail(File file, LineHandler lineHandler) {
        tail(file, CharsetUtil.CHARSET_UTF_8, lineHandler);
    }

    public static File touch(String str) throws IORuntimeException {
        if (str == null) {
            return null;
        }
        return touch(file(str));
    }

    public static void walkFiles(File file, Consumer<File> consumer) {
        if (!file.isDirectory()) {
            consumer.accept(file);
            return;
        }
        File[] fileArrListFiles = file.listFiles();
        if (ArrayUtil.isNotEmpty((Object[]) fileArrListFiles)) {
            for (File file2 : fileArrListFiles) {
                walkFiles(file2, consumer);
            }
        }
    }

    public static File writeBytes(byte[] bArr, String str) throws IORuntimeException {
        return writeBytes(bArr, touch(str));
    }

    public static File writeFromStream(InputStream inputStream, File file) throws IORuntimeException {
        return writeFromStream(inputStream, file, true);
    }

    public static <T> File writeLines(Collection<T> collection, String str, String str2) throws IORuntimeException {
        return writeLines((Collection) collection, str, str2, false);
    }

    public static File writeMap(Map<?, ?> map, File file, Charset charset, String str, boolean z2) throws IORuntimeException {
        return FileWriter.create(file, charset).writeMap(map, str, z2);
    }

    public static File writeString(String str, String str2, String str3) throws IORuntimeException {
        return writeString(str, touch(str2), str3);
    }

    public static long writeToStream(File file, OutputStream outputStream) throws IORuntimeException {
        return cn.hutool.core.io.file.FileReader.create(file).writeToStream(outputStream);
    }

    public static <T> File writeUtf8Lines(Collection<T> collection, String str) throws IORuntimeException {
        return writeLines(collection, str, CharsetUtil.CHARSET_UTF_8);
    }

    public static File writeUtf8Map(Map<?, ?> map, File file, String str, boolean z2) throws IORuntimeException {
        return FileWriter.create(file, CharsetUtil.CHARSET_UTF_8).writeMap(map, str, z2);
    }

    public static File writeUtf8String(String str, String str2) throws IORuntimeException {
        return writeString(str, str2, CharsetUtil.CHARSET_UTF_8);
    }

    public static <T> File appendLines(Collection<T> collection, File file, String str) throws IORuntimeException {
        return writeLines((Collection) collection, file, str, true);
    }

    public static File appendString(String str, String str2, Charset charset) throws IORuntimeException {
        return appendString(str, touch(str2), charset);
    }

    public static <T> File appendUtf8Lines(Collection<T> collection, String str) throws IORuntimeException {
        return appendLines(collection, str, CharsetUtil.CHARSET_UTF_8);
    }

    public static File appendUtf8String(String str, File file) throws IORuntimeException {
        return appendString(str, file, CharsetUtil.CHARSET_UTF_8);
    }

    public static boolean clean(File file) throws IORuntimeException {
        File[] fileArrListFiles;
        if (file != null && file.exists() && file.isDirectory() && (fileArrListFiles = file.listFiles()) != null) {
            for (File file2 : fileArrListFiles) {
                if (!del(file2)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static File copy(File file, File file2, boolean z2) throws IORuntimeException {
        return FileCopier.create(file, file2).setOverride(z2).copy();
    }

    public static RandomAccessFile createRandomAccessFile(File file, FileMode fileMode) {
        try {
            return new RandomAccessFile(file, fileMode.name());
        } catch (FileNotFoundException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static File createTempFile() throws IORuntimeException {
        return createTempFile("hutool", null, null, true);
    }

    public static boolean del(File file) throws IORuntimeException {
        if (file != null && file.exists()) {
            if (file.isDirectory() && !clean(file)) {
                return false;
            }
            Path path = file.toPath();
            try {
                PathUtil.delFile(path);
            } catch (DirectoryNotEmptyException unused) {
                PathUtil.del(path);
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        }
        return true;
    }

    public static boolean exist(File file) {
        return file != null && file.exists();
    }

    public static String extName(String str) {
        return FileNameUtil.extName(str);
    }

    public static File file(String str, String str2) {
        return file(new File(str), str2);
    }

    public static BufferedInputStream getInputStream(String str) throws IORuntimeException {
        return getInputStream(file(str));
    }

    public static String getName(String str) {
        return FileNameUtil.getName(str);
    }

    public static String getPrefix(String str) {
        return FileNameUtil.getPrefix(str);
    }

    public static PrintWriter getPrintWriter(String str, Charset charset, boolean z2) throws IORuntimeException {
        return new PrintWriter(getWriter(str, charset, z2));
    }

    public static BufferedReader getReader(File file, Charset charset) throws IORuntimeException {
        return IoUtil.getReader(getInputStream(file), charset);
    }

    public static String getSuffix(String str) {
        return FileNameUtil.getSuffix(str);
    }

    public static BufferedReader getUtf8Reader(String str) throws IORuntimeException {
        return getReader(str, CharsetUtil.CHARSET_UTF_8);
    }

    public static BufferedWriter getWriter(String str, Charset charset, boolean z2) throws IORuntimeException {
        return getWriter(touch(str), charset, z2);
    }

    public static boolean isDirectory(File file) {
        return file != null && file.isDirectory();
    }

    public static boolean isFile(File file) {
        return file != null && file.isFile();
    }

    public static <T> T load(String str, Charset charset, FileReader.ReaderHandler<T> readerHandler) throws IORuntimeException {
        return (T) cn.hutool.core.io.file.FileReader.create(file(str), charset).read(readerHandler);
    }

    public static <T> T loadUtf8(File file, FileReader.ReaderHandler<T> readerHandler) throws IORuntimeException {
        return (T) load(file, CharsetUtil.CHARSET_UTF_8, readerHandler);
    }

    public static List<File> loopFiles(File file, FileFilter fileFilter) {
        return loopFiles(file, -1, fileFilter);
    }

    public static String mainName(String str) {
        return FileNameUtil.mainName(str);
    }

    public static File mkParentDirs(String str) {
        if (str == null) {
            return null;
        }
        return mkParentDirs(file(str));
    }

    public static byte[] readBytes(String str) throws IORuntimeException {
        return readBytes(file(str));
    }

    public static <T extends Collection<String>> T readLines(String str, Charset charset, T t2) throws IORuntimeException {
        return (T) readLines(file(str), charset, t2);
    }

    public static String readString(File file, Charset charset) throws IORuntimeException {
        return cn.hutool.core.io.file.FileReader.create(file, charset).readString();
    }

    public static <T extends Collection<String>> T readUtf8Lines(File file, T t2) throws IORuntimeException {
        return (T) readLines(file, CharsetUtil.CHARSET_UTF_8, t2);
    }

    public static String readUtf8String(String str) throws IORuntimeException {
        return readString(str, CharsetUtil.CHARSET_UTF_8);
    }

    public static String readableFileSize(long j2) {
        return DataSizeUtil.format(j2);
    }

    public static File rename(File file, String str, boolean z2, boolean z3) {
        if (z2) {
            String strExtName = extName(file);
            if (CharSequenceUtil.isNotBlank(strExtName)) {
                str = str.concat(StrPool.DOT).concat(strExtName);
            }
        }
        return PathUtil.rename(file.toPath(), str, z3).toFile();
    }

    public static long size(File file, boolean z2) {
        if (file == null || !file.exists() || isSymlink(file)) {
            return 0L;
        }
        if (!file.isDirectory()) {
            return file.length();
        }
        long length = z2 ? file.length() : 0L;
        File[] fileArrListFiles = file.listFiles();
        if (ArrayUtil.isEmpty((Object[]) fileArrListFiles)) {
            return 0L;
        }
        for (File file2 : fileArrListFiles) {
            length += size(file2, z2);
        }
        return length;
    }

    public static void tail(File file, Charset charset, LineHandler lineHandler) {
        new Tailer(file, charset, lineHandler).start();
    }

    public static File touch(File file) throws IOException, IORuntimeException {
        if (file == null) {
            return null;
        }
        if (!file.exists()) {
            mkParentDirs(file);
            try {
                file.createNewFile();
            } catch (Exception e2) {
                throw new IORuntimeException(e2);
            }
        }
        return file;
    }

    public static File writeBytes(byte[] bArr, File file) throws IORuntimeException {
        return writeBytes(bArr, file, 0, bArr.length, false);
    }

    public static File writeFromStream(InputStream inputStream, File file, boolean z2) throws IORuntimeException {
        return FileWriter.create(file).writeFromStream(inputStream, z2);
    }

    public static <T> File writeLines(Collection<T> collection, String str, Charset charset) throws IORuntimeException {
        return writeLines((Collection) collection, str, charset, false);
    }

    public static File writeString(String str, String str2, Charset charset) throws IORuntimeException {
        return writeString(str, touch(str2), charset);
    }

    public static long writeToStream(String str, OutputStream outputStream) throws IORuntimeException {
        return writeToStream(touch(str), outputStream);
    }

    public static <T> File writeUtf8Lines(Collection<T> collection, File file) throws IORuntimeException {
        return writeLines(collection, file, CharsetUtil.CHARSET_UTF_8);
    }

    public static File writeUtf8String(String str, File file) throws IORuntimeException {
        return writeString(str, file, CharsetUtil.CHARSET_UTF_8);
    }

    public static <T> File appendLines(Collection<T> collection, String str, Charset charset) throws IORuntimeException {
        return writeLines((Collection) collection, str, charset, true);
    }

    public static File appendString(String str, File file, String str2) throws IORuntimeException {
        return FileWriter.create(file, CharsetUtil.charset(str2)).append(str);
    }

    public static File createTempFile(String str, boolean z2) throws IORuntimeException {
        return createTempFile("hutool", str, null, z2);
    }

    public static boolean exist(String str, String str2) {
        String[] list;
        File file = new File(str);
        if (!file.exists() || (list = file.list()) == null) {
            return false;
        }
        for (String str3 : list) {
            if (str3.matches(str2)) {
                return true;
            }
        }
        return false;
    }

    public static File file(File file, String str) {
        if (!CharSequenceUtil.isBlank(str)) {
            return checkSlip(file, buildFile(file, str));
        }
        throw new NullPointerException("File path is blank!");
    }

    public static PrintWriter getPrintWriter(File file, String str, boolean z2) throws IORuntimeException {
        return new PrintWriter(getWriter(file, str, z2));
    }

    @Deprecated
    public static BufferedReader getReader(String str, String str2) throws IORuntimeException {
        return getReader(str, CharsetUtil.charset(str2));
    }

    @Deprecated
    public static BufferedWriter getWriter(File file, String str, boolean z2) throws IORuntimeException {
        return getWriter(file, Charset.forName(str), z2);
    }

    public static Date lastModifiedTime(String str) {
        return lastModifiedTime(new File(str));
    }

    public static <T> T load(File file, Charset charset, FileReader.ReaderHandler<T> readerHandler) throws IORuntimeException {
        return (T) cn.hutool.core.io.file.FileReader.create(file, charset).read(readerHandler);
    }

    public static List<File> loopFiles(File file, int i2, FileFilter fileFilter) {
        return PathUtil.loopFiles(file.toPath(), i2, fileFilter);
    }

    public static File mkdir(File file) {
        if (file == null) {
            return null;
        }
        if (!file.exists()) {
            mkdirsSafely(file, 5, 1L);
        }
        return file;
    }

    public static boolean newerThan(File file, long j2) {
        return file != null && file.exists() && file.lastModified() > j2;
    }

    public static String readLine(RandomAccessFile randomAccessFile, Charset charset) throws IOException {
        try {
            String line = randomAccessFile.readLine();
            if (line != null) {
                return CharsetUtil.convert(line, CharsetUtil.CHARSET_ISO_8859_1, charset);
            }
            return null;
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static <T extends Collection<String>> T readLines(File file, String str, T t2) throws IORuntimeException {
        return (T) cn.hutool.core.io.file.FileReader.create(file, CharsetUtil.charset(str)).readLines((cn.hutool.core.io.file.FileReader) t2);
    }

    @Deprecated
    public static String readString(String str, String str2) throws IORuntimeException {
        return readString(str, CharsetUtil.charset(str2));
    }

    public static <T extends Collection<String>> T readUtf8Lines(URL url, T t2) throws IORuntimeException {
        return (T) readLines(url, CharsetUtil.CHARSET_UTF_8, t2);
    }

    public static String subPath(String str, String str2) {
        if (!CharSequenceUtil.isNotEmpty(str) || !CharSequenceUtil.isNotEmpty(str2)) {
            return str2;
        }
        return CharSequenceUtil.removePrefix(CharSequenceUtil.removePrefixIgnoreCase(normalize(str2), CharSequenceUtil.removeSuffix(normalize(str), "/")), "/");
    }

    public static void tail(File file, Charset charset) {
        tail(file, charset, Tailer.CONSOLE_HANDLER);
    }

    public static File writeBytes(byte[] bArr, File file, int i2, int i3, boolean z2) throws IORuntimeException {
        return FileWriter.create(file).write(bArr, i2, i3, z2);
    }

    public static File writeFromStream(InputStream inputStream, String str) throws IORuntimeException {
        return writeFromStream(inputStream, touch(str));
    }

    public static <T> File writeLines(Collection<T> collection, File file, String str) throws IORuntimeException {
        return writeLines((Collection) collection, file, str, false);
    }

    public static File writeString(String str, File file, String str2) throws IORuntimeException {
        return FileWriter.create(file, CharsetUtil.charset(str2)).write(str);
    }

    public static <T> File appendLines(Collection<T> collection, File file, Charset charset) throws IORuntimeException {
        return writeLines((Collection) collection, file, charset, true);
    }

    public static File appendString(String str, File file, Charset charset) throws IORuntimeException {
        return FileWriter.create(file, charset).append(str);
    }

    public static File copyFile(File file, File file2, StandardCopyOption... standardCopyOptionArr) throws IllegalArgumentException, IORuntimeException {
        Assert.notNull(file, "Source File is null !", new Object[0]);
        if (file.exists()) {
            Assert.notNull(file2, "Destination File or directiory is null !", new Object[0]);
            if (!equals(file, file2)) {
                return PathUtil.copyFile(file.toPath(), file2.toPath(), standardCopyOptionArr).toFile();
            }
            throw new IORuntimeException("Files '{}' and '{}' are equal", file, file2);
        }
        throw new IORuntimeException("File not exist: " + file);
    }

    public static File createTempFile(String str, String str2, boolean z2) throws IORuntimeException {
        return createTempFile(str, str2, null, z2);
    }

    public static BufferedOutputStream getOutputStream(String str) throws IORuntimeException {
        return getOutputStream(touch(str));
    }

    public static File getParent(File file, int i2) {
        if (i2 < 1 || file == null) {
            return file;
        }
        try {
            File parentFile = file.getCanonicalFile().getParentFile();
            return 1 == i2 ? parentFile : getParent(parentFile, i2 - 1);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static PrintWriter getPrintWriter(File file, Charset charset, boolean z2) throws IORuntimeException {
        return new PrintWriter(getWriter(file, charset, z2));
    }

    public static BufferedReader getReader(String str, Charset charset) throws IORuntimeException {
        return getReader(file(str), charset);
    }

    public static BufferedWriter getWriter(File file, Charset charset, boolean z2) throws IORuntimeException {
        return FileWriter.create(file, charset).getWriter(z2);
    }

    public static List<File> loopFiles(String str) {
        return loopFiles(file(str));
    }

    public static <T extends Collection<String>> T readLines(File file, Charset charset, T t2) throws IORuntimeException {
        return (T) cn.hutool.core.io.file.FileReader.create(file, charset).readLines((cn.hutool.core.io.file.FileReader) t2);
    }

    public static String readString(String str, Charset charset) throws IORuntimeException {
        return readString(file(str), charset);
    }

    public static List<String> readUtf8Lines(URL url) throws IORuntimeException {
        return readLines(url, CharsetUtil.CHARSET_UTF_8);
    }

    public static <T> File writeLines(Collection<T> collection, File file, Charset charset) throws IORuntimeException {
        return writeLines((Collection) collection, file, charset, false);
    }

    public static File writeString(String str, File file, Charset charset) throws IORuntimeException {
        return FileWriter.create(file, charset).write(str);
    }

    public static File createTempFile(File file, boolean z2) throws IORuntimeException {
        return createTempFile("hutool", null, file, z2);
    }

    public static List<File> loopFiles(File file) {
        return loopFiles(file, (FileFilter) null);
    }

    @Deprecated
    public static <T extends Collection<String>> T readLines(URL url, String str, T t2) throws IORuntimeException {
        return (T) readLines(url, CharsetUtil.charset(str), t2);
    }

    @Deprecated
    public static String readString(URL url, String str) throws IORuntimeException {
        return readString(url, CharsetUtil.charset(str));
    }

    public static List<String> readUtf8Lines(String str) throws IORuntimeException {
        return readLines(str, CharsetUtil.CHARSET_UTF_8);
    }

    public static <T> File writeLines(Collection<T> collection, String str, String str2, boolean z2) throws IORuntimeException {
        return writeLines(collection, file(str), str2, z2);
    }

    public static File createTempFile(String str, String str2, File file, boolean z2) throws IOException, IORuntimeException {
        Path path;
        int i2 = 0;
        do {
            if (file == null) {
                path = null;
            } else {
                try {
                    path = file.toPath();
                } catch (IOException e2) {
                    i2++;
                }
            }
            File canonicalFile = PathUtil.createTempFile(str, str2, path).toFile().getCanonicalFile();
            if (z2) {
                canonicalFile.delete();
                canonicalFile.createNewFile();
            }
            return canonicalFile;
        } while (i2 < 50);
        throw new IORuntimeException(e2);
    }

    public static File file(File file, String... strArr) throws IllegalArgumentException {
        Assert.notNull(file, "directory must not be null", new Object[0]);
        if (ArrayUtil.isEmpty((Object[]) strArr)) {
            return file;
        }
        for (String str : strArr) {
            if (str != null) {
                file = file(file, str);
            }
        }
        return file;
    }

    public static <T extends Collection<String>> T readLines(URL url, Charset charset, T t2) throws IOException, IORuntimeException {
        InputStream inputStreamOpenStream = null;
        try {
            try {
                inputStreamOpenStream = url.openStream();
                return (T) IoUtil.readLines(inputStreamOpenStream, charset, t2);
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        } finally {
            IoUtil.close((Closeable) inputStreamOpenStream);
        }
    }

    public static String readString(URL url, Charset charset) throws IOException, IORuntimeException {
        if (url != null) {
            InputStream inputStreamOpenStream = null;
            try {
                try {
                    inputStreamOpenStream = url.openStream();
                    return IoUtil.read(inputStreamOpenStream, charset);
                } catch (IOException e2) {
                    throw new IORuntimeException(e2);
                }
            } finally {
                IoUtil.close((Closeable) inputStreamOpenStream);
            }
        }
        throw new NullPointerException("Empty url provided!");
    }

    public static List<String> readUtf8Lines(File file) throws IORuntimeException {
        return readLines(file, CharsetUtil.CHARSET_UTF_8);
    }

    public static File touch(File file, String str) throws IORuntimeException {
        return touch(file(file, str));
    }

    public static <T> File writeLines(Collection<T> collection, String str, Charset charset, boolean z2) throws IORuntimeException {
        return writeLines(collection, file(str), charset, z2);
    }

    public static String getAbsolutePath(String str) {
        return getAbsolutePath(str, null);
    }

    public static List<String> readUtf8Lines(File file, Predicate<String> predicate) throws IORuntimeException {
        return readLines(file, CharsetUtil.CHARSET_UTF_8, predicate);
    }

    public static File touch(String str, String str2) throws IORuntimeException {
        return touch(file(str, str2));
    }

    public static <T> File writeLines(Collection<T> collection, File file, String str, boolean z2) throws IORuntimeException {
        return FileWriter.create(file, CharsetUtil.charset(str)).writeLines(collection, z2);
    }

    public static String getAbsolutePath(File file) {
        if (file == null) {
            return null;
        }
        try {
            return file.getCanonicalPath();
        } catch (IOException unused) {
            return file.getAbsolutePath();
        }
    }

    public static void readUtf8Lines(File file, LineHandler lineHandler) throws IORuntimeException {
        readLines(file, CharsetUtil.CHARSET_UTF_8, lineHandler);
    }

    public static <T> File writeLines(Collection<T> collection, File file, Charset charset, boolean z2) throws IORuntimeException {
        return FileWriter.create(file, charset).writeLines(collection, z2);
    }

    public static File file(String... strArr) {
        File file = null;
        if (ArrayUtil.isEmpty((Object[]) strArr)) {
            return null;
        }
        for (String str : strArr) {
            if (file == null) {
                file = file(str);
            } else {
                file = file(file, str);
            }
        }
        return file;
    }

    @Deprecated
    public static List<String> readLines(URL url, String str) throws IORuntimeException {
        return readLines(url, CharsetUtil.charset(str));
    }

    public static List<String> readLines(URL url, Charset charset) throws IORuntimeException {
        return (List) readLines(url, charset, new ArrayList());
    }

    public static File file(URI uri) {
        if (uri != null) {
            return new File(uri);
        }
        throw new NullPointerException("File uri is null!");
    }

    public static List<String> readLines(String str, String str2) throws IORuntimeException {
        return (List) readLines(str, str2, new ArrayList());
    }

    public static List<String> readLines(String str, Charset charset) throws IORuntimeException {
        return (List) readLines(str, charset, new ArrayList());
    }

    public static File file(URL url) {
        return new File(URLUtil.toURI(url));
    }

    public static List<String> readLines(File file, String str) throws IORuntimeException {
        return (List) readLines(file, str, new ArrayList());
    }

    public static List<String> readLines(File file, Charset charset) throws IORuntimeException {
        return (List) readLines(file, charset, new ArrayList());
    }

    public static List<String> readLines(File file, Charset charset, final Predicate<String> predicate) throws IORuntimeException {
        final ArrayList arrayList = new ArrayList();
        readLines(file, charset, new LineHandler() { // from class: cn.hutool.core.io.b
            @Override // cn.hutool.core.io.LineHandler
            public final void handle(String str) {
                FileUtil.lambda$readLines$0(predicate, arrayList, str);
            }
        });
        return arrayList;
    }

    public static void readLines(File file, Charset charset, LineHandler lineHandler) throws IORuntimeException {
        cn.hutool.core.io.file.FileReader.create(file, charset).readLines(lineHandler);
    }

    public static void readLines(RandomAccessFile randomAccessFile, Charset charset, LineHandler lineHandler) throws IOException {
        while (true) {
            try {
                String line = randomAccessFile.readLine();
                if (line == null) {
                    return;
                } else {
                    lineHandler.handle(CharsetUtil.convert(line, CharsetUtil.CHARSET_ISO_8859_1, charset));
                }
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        }
    }
}
