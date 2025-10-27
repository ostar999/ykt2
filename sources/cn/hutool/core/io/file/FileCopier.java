package cn.hutool.core.io.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.lang.copier.SrcToDestCopier;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class FileCopier extends SrcToDestCopier<File, FileCopier> {
    private static final long serialVersionUID = 1;
    private boolean isCopyAttributes;
    private boolean isCopyContentIfDir;
    private boolean isOnlyCopyFile;
    private boolean isOverride;

    /* JADX WARN: Multi-variable type inference failed */
    public FileCopier(File file, File file2) {
        this.src = file;
        this.dest = file2;
    }

    public static FileCopier create(String str, String str2) {
        return new FileCopier(FileUtil.file(str), FileUtil.file(str2));
    }

    private void internalCopyDirContent(File file, File file2) throws IOException, IORuntimeException {
        Filter<T> filter = this.copyFilter;
        if (filter == 0 || filter.accept(file)) {
            if (!file2.exists()) {
                file2.mkdirs();
            } else if (!file2.isDirectory()) {
                throw new IORuntimeException(CharSequenceUtil.format("Src [{}] is a directory but dest [{}] is a file!", file.getPath(), file2.getPath()));
            }
            String[] list = file.list();
            if (ArrayUtil.isNotEmpty((Object[]) list)) {
                for (String str : list) {
                    File file3 = new File(file, str);
                    File file4 = this.isOnlyCopyFile ? file2 : new File(file2, str);
                    if (file3.isDirectory()) {
                        internalCopyDirContent(file3, file4);
                    } else {
                        internalCopyFile(file3, file4);
                    }
                }
            }
        }
    }

    private File internalCopyFile(File file, File file2) throws IOException, IORuntimeException {
        Filter<T> filter = this.copyFilter;
        if (filter != 0 && !filter.accept(file)) {
            return file;
        }
        if (file2.exists()) {
            if (file2.isDirectory()) {
                file2 = new File(file2, file.getName());
            }
            if (file2.exists() && !this.isOverride) {
                return file;
            }
        } else {
            FileUtil.mkParentDirs(file2);
        }
        ArrayList arrayList = new ArrayList(2);
        if (this.isOverride) {
            arrayList.add(StandardCopyOption.REPLACE_EXISTING);
        }
        if (this.isCopyAttributes) {
            arrayList.add(StandardCopyOption.COPY_ATTRIBUTES);
        }
        try {
            Files.copy(file.toPath(), file2.toPath(), (CopyOption[]) arrayList.toArray(new CopyOption[0]));
            return file2;
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public boolean isCopyAttributes() {
        return this.isCopyAttributes;
    }

    public boolean isCopyContentIfDir() {
        return this.isCopyContentIfDir;
    }

    public boolean isOnlyCopyFile() {
        return this.isOnlyCopyFile;
    }

    public boolean isOverride() {
        return this.isOverride;
    }

    public FileCopier setCopyAttributes(boolean z2) {
        this.isCopyAttributes = z2;
        return this;
    }

    public FileCopier setCopyContentIfDir(boolean z2) {
        this.isCopyContentIfDir = z2;
        return this;
    }

    public FileCopier setOnlyCopyFile(boolean z2) {
        this.isOnlyCopyFile = z2;
        return this;
    }

    public FileCopier setOverride(boolean z2) {
        this.isOverride = z2;
        return this;
    }

    public static FileCopier create(File file, File file2) {
        return new FileCopier(file, file2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // cn.hutool.core.lang.copier.Copier
    public File copy() throws IOException, IllegalArgumentException, IORuntimeException {
        File file = (File) this.src;
        File file2 = (File) this.dest;
        Assert.notNull(file, "Source File is null !", new Object[0]);
        if (!file.exists()) {
            throw new IORuntimeException("File not exist: " + file);
        }
        Assert.notNull(file2, "Destination File or directiory is null !", new Object[0]);
        if (FileUtil.equals(file, file2)) {
            throw new IORuntimeException("Files '{}' and '{}' are equal", file, file2);
        }
        if (!file.isDirectory()) {
            return internalCopyFile(file, file2);
        }
        if (file2.exists() && !file2.isDirectory()) {
            throw new IORuntimeException("Src is a directory but dest is a file!");
        }
        if (FileUtil.isSub(file, file2)) {
            throw new IORuntimeException("Dest is a sub directory of src !");
        }
        internalCopyDirContent(file, this.isCopyContentIfDir ? file2 : FileUtil.mkdir(FileUtil.file(file2, file.getName())));
        return file2;
    }
}
