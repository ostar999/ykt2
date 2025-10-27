package com.google.common.io;

import cn.hutool.core.text.StrPool;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.TreeTraverser;
import com.google.common.graph.SuccessorsFunction;
import com.google.common.graph.Traverser;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.yikaobang.yixue.R2;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.lingala.zip4j.util.InternalZipConstants;

@GwtIncompatible
/* loaded from: classes4.dex */
public final class Files {
    private static final int TEMP_DIR_ATTEMPTS = 10000;
    private static final TreeTraverser<File> FILE_TREE_TRAVERSER = new TreeTraverser<File>() { // from class: com.google.common.io.Files.2
        public String toString() {
            return "Files.fileTreeTraverser()";
        }

        @Override // com.google.common.collect.TreeTraverser
        public Iterable<File> children(File file) {
            return Files.fileTreeChildren(file);
        }
    };
    private static final SuccessorsFunction<File> FILE_TREE = new SuccessorsFunction<File>() { // from class: com.google.common.io.Files.3
        @Override // com.google.common.graph.SuccessorsFunction
        public Iterable<File> successors(File file) {
            return Files.fileTreeChildren(file);
        }
    };

    public static final class FileByteSink extends ByteSink {
        private final File file;
        private final ImmutableSet<FileWriteMode> modes;

        public String toString() {
            return "Files.asByteSink(" + this.file + ", " + this.modes + ")";
        }

        private FileByteSink(File file, FileWriteMode... fileWriteModeArr) {
            this.file = (File) Preconditions.checkNotNull(file);
            this.modes = ImmutableSet.copyOf(fileWriteModeArr);
        }

        @Override // com.google.common.io.ByteSink
        public FileOutputStream openStream() throws IOException {
            return new FileOutputStream(this.file, this.modes.contains(FileWriteMode.APPEND));
        }
    }

    public static final class FileByteSource extends ByteSource {
        private final File file;

        @Override // com.google.common.io.ByteSource
        public byte[] read() throws Throwable {
            try {
                FileInputStream fileInputStream = (FileInputStream) Closer.create().register(openStream());
                return ByteStreams.toByteArray(fileInputStream, fileInputStream.getChannel().size());
            } finally {
            }
        }

        @Override // com.google.common.io.ByteSource
        public long size() throws IOException {
            if (this.file.isFile()) {
                return this.file.length();
            }
            throw new FileNotFoundException(this.file.toString());
        }

        @Override // com.google.common.io.ByteSource
        public Optional<Long> sizeIfKnown() {
            return this.file.isFile() ? Optional.of(Long.valueOf(this.file.length())) : Optional.absent();
        }

        public String toString() {
            return "Files.asByteSource(" + this.file + ")";
        }

        private FileByteSource(File file) {
            this.file = (File) Preconditions.checkNotNull(file);
        }

        @Override // com.google.common.io.ByteSource
        public FileInputStream openStream() throws IOException {
            return new FileInputStream(this.file);
        }
    }

    public enum FilePredicate implements Predicate<File> {
        IS_DIRECTORY { // from class: com.google.common.io.Files.FilePredicate.1
            @Override // java.lang.Enum
            public String toString() {
                return "Files.isDirectory()";
            }

            @Override // com.google.common.base.Predicate
            public boolean apply(File file) {
                return file.isDirectory();
            }
        },
        IS_FILE { // from class: com.google.common.io.Files.FilePredicate.2
            @Override // java.lang.Enum
            public String toString() {
                return "Files.isFile()";
            }

            @Override // com.google.common.base.Predicate
            public boolean apply(File file) {
                return file.isFile();
            }
        }
    }

    private Files() {
    }

    @Beta
    @Deprecated
    public static void append(CharSequence charSequence, File file, Charset charset) throws Throwable {
        asCharSink(file, charset, FileWriteMode.APPEND).write(charSequence);
    }

    public static ByteSink asByteSink(File file, FileWriteMode... fileWriteModeArr) {
        return new FileByteSink(file, fileWriteModeArr);
    }

    public static ByteSource asByteSource(File file) {
        return new FileByteSource(file);
    }

    public static CharSink asCharSink(File file, Charset charset, FileWriteMode... fileWriteModeArr) {
        return asByteSink(file, fileWriteModeArr).asCharSink(charset);
    }

    public static CharSource asCharSource(File file, Charset charset) {
        return asByteSource(file).asCharSource(charset);
    }

    @Beta
    public static void copy(File file, OutputStream outputStream) throws Throwable {
        asByteSource(file).copyTo(outputStream);
    }

    @Beta
    public static void createParentDirs(File file) throws IOException {
        Preconditions.checkNotNull(file);
        File parentFile = file.getCanonicalFile().getParentFile();
        if (parentFile == null) {
            return;
        }
        parentFile.mkdirs();
        if (parentFile.isDirectory()) {
            return;
        }
        throw new IOException("Unable to create parent directories of " + file);
    }

    @Beta
    public static File createTempDir() {
        File file = new File(System.getProperty("java.io.tmpdir"));
        String str = System.currentTimeMillis() + "-";
        for (int i2 = 0; i2 < 10000; i2++) {
            File file2 = new File(file, str + i2);
            if (file2.mkdir()) {
                return file2;
            }
        }
        throw new IllegalStateException("Failed to create directory within 10000 attempts (tried " + str + "0 to " + str + R2.drawable.homepage_shangcheng_press + ')');
    }

    @Beta
    public static boolean equal(File file, File file2) throws IOException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(file2);
        if (file == file2 || file.equals(file2)) {
            return true;
        }
        long length = file.length();
        long length2 = file2.length();
        if (length == 0 || length2 == 0 || length == length2) {
            return asByteSource(file).contentEquals(asByteSource(file2));
        }
        return false;
    }

    @Beta
    public static Traverser<File> fileTraverser() {
        return Traverser.forTree(FILE_TREE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Iterable<File> fileTreeChildren(File file) {
        File[] fileArrListFiles;
        return (!file.isDirectory() || (fileArrListFiles = file.listFiles()) == null) ? Collections.emptyList() : Collections.unmodifiableList(Arrays.asList(fileArrListFiles));
    }

    @Deprecated
    public static TreeTraverser<File> fileTreeTraverser() {
        return FILE_TREE_TRAVERSER;
    }

    @Beta
    public static String getFileExtension(String str) {
        Preconditions.checkNotNull(str);
        String name = new File(str).getName();
        int iLastIndexOf = name.lastIndexOf(46);
        return iLastIndexOf == -1 ? "" : name.substring(iLastIndexOf + 1);
    }

    @Beta
    public static String getNameWithoutExtension(String str) {
        Preconditions.checkNotNull(str);
        String name = new File(str).getName();
        int iLastIndexOf = name.lastIndexOf(46);
        return iLastIndexOf == -1 ? name : name.substring(0, iLastIndexOf);
    }

    @Beta
    @Deprecated
    public static HashCode hash(File file, HashFunction hashFunction) throws IOException {
        return asByteSource(file).hash(hashFunction);
    }

    @Beta
    public static Predicate<File> isDirectory() {
        return FilePredicate.IS_DIRECTORY;
    }

    @Beta
    public static Predicate<File> isFile() {
        return FilePredicate.IS_FILE;
    }

    @Beta
    public static MappedByteBuffer map(File file) throws IOException {
        Preconditions.checkNotNull(file);
        return map(file, FileChannel.MapMode.READ_ONLY);
    }

    private static MappedByteBuffer mapInternal(File file, FileChannel.MapMode mapMode, long j2) throws Throwable {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(mapMode);
        Closer closerCreate = Closer.create();
        try {
            FileChannel fileChannel = (FileChannel) closerCreate.register(((RandomAccessFile) closerCreate.register(new RandomAccessFile(file, mapMode == FileChannel.MapMode.READ_ONLY ? "r" : InternalZipConstants.WRITE_MODE))).getChannel());
            if (j2 == -1) {
                j2 = fileChannel.size();
            }
            return fileChannel.map(mapMode, 0L, j2);
        } finally {
        }
    }

    @Beta
    public static void move(File file, File file2) throws Throwable {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(file2);
        Preconditions.checkArgument(!file.equals(file2), "Source %s and destination %s must be different", file, file2);
        if (file.renameTo(file2)) {
            return;
        }
        copy(file, file2);
        if (file.delete()) {
            return;
        }
        if (file2.delete()) {
            throw new IOException("Unable to delete " + file);
        }
        throw new IOException("Unable to delete " + file2);
    }

    @Beta
    public static BufferedReader newReader(File file, Charset charset) throws FileNotFoundException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(charset);
        return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
    }

    @Beta
    public static BufferedWriter newWriter(File file, Charset charset) throws FileNotFoundException {
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(charset);
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
    }

    @Beta
    @Deprecated
    @CanIgnoreReturnValue
    public static <T> T readBytes(File file, ByteProcessor<T> byteProcessor) throws IOException {
        return (T) asByteSource(file).read(byteProcessor);
    }

    @Beta
    @Deprecated
    public static String readFirstLine(File file, Charset charset) throws IOException {
        return asCharSource(file, charset).readFirstLine();
    }

    @Beta
    public static List<String> readLines(File file, Charset charset) throws IOException {
        return (List) asCharSource(file, charset).readLines(new LineProcessor<List<String>>() { // from class: com.google.common.io.Files.1
            final List<String> result = Lists.newArrayList();

            @Override // com.google.common.io.LineProcessor
            public boolean processLine(String str) {
                this.result.add(str);
                return true;
            }

            @Override // com.google.common.io.LineProcessor
            public List<String> getResult() {
                return this.result;
            }
        });
    }

    @Beta
    public static String simplifyPath(String str) {
        Preconditions.checkNotNull(str);
        if (str.length() == 0) {
            return StrPool.DOT;
        }
        Iterable<String> iterableSplit = Splitter.on('/').omitEmptyStrings().split(str);
        ArrayList arrayList = new ArrayList();
        for (String str2 : iterableSplit) {
            str2.hashCode();
            if (!str2.equals(StrPool.DOT)) {
                if (!str2.equals(StrPool.DOUBLE_DOT)) {
                    arrayList.add(str2);
                } else if (arrayList.size() <= 0 || ((String) arrayList.get(arrayList.size() - 1)).equals(StrPool.DOUBLE_DOT)) {
                    arrayList.add(StrPool.DOUBLE_DOT);
                } else {
                    arrayList.remove(arrayList.size() - 1);
                }
            }
        }
        String strJoin = Joiner.on('/').join(arrayList);
        if (str.charAt(0) == '/') {
            strJoin = "/" + strJoin;
        }
        while (strJoin.startsWith("/../")) {
            strJoin = strJoin.substring(3);
        }
        return strJoin.equals("/..") ? "/" : "".equals(strJoin) ? StrPool.DOT : strJoin;
    }

    @Beta
    public static byte[] toByteArray(File file) throws IOException {
        return asByteSource(file).read();
    }

    @Beta
    @Deprecated
    public static String toString(File file, Charset charset) throws IOException {
        return asCharSource(file, charset).read();
    }

    @Beta
    public static void touch(File file) throws IOException {
        Preconditions.checkNotNull(file);
        if (file.createNewFile() || file.setLastModified(System.currentTimeMillis())) {
            return;
        }
        throw new IOException("Unable to update modification time of " + file);
    }

    @Beta
    public static void write(byte[] bArr, File file) throws Throwable {
        asByteSink(file, new FileWriteMode[0]).write(bArr);
    }

    @Beta
    public static void copy(File file, File file2) throws Throwable {
        Preconditions.checkArgument(!file.equals(file2), "Source %s and destination %s must be different", file, file2);
        asByteSource(file).copyTo(asByteSink(file2, new FileWriteMode[0]));
    }

    @Beta
    @Deprecated
    public static void write(CharSequence charSequence, File file, Charset charset) throws Throwable {
        asCharSink(file, charset, new FileWriteMode[0]).write(charSequence);
    }

    @Beta
    public static MappedByteBuffer map(File file, FileChannel.MapMode mapMode) throws IOException {
        return mapInternal(file, mapMode, -1L);
    }

    @Beta
    @Deprecated
    @CanIgnoreReturnValue
    public static <T> T readLines(File file, Charset charset, LineProcessor<T> lineProcessor) throws IOException {
        return (T) asCharSource(file, charset).readLines(lineProcessor);
    }

    @Beta
    @Deprecated
    public static void copy(File file, Charset charset, Appendable appendable) throws Throwable {
        asCharSource(file, charset).copyTo(appendable);
    }

    @Beta
    public static MappedByteBuffer map(File file, FileChannel.MapMode mapMode, long j2) throws IOException {
        Preconditions.checkArgument(j2 >= 0, "size (%s) may not be negative", j2);
        return mapInternal(file, mapMode, j2);
    }
}
