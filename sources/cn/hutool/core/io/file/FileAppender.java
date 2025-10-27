package cn.hutool.core.io.file;

import cn.hutool.core.thread.lock.LockUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class FileAppender implements Serializable {
    private static final long serialVersionUID = 1;
    private final int capacity;
    private final boolean isNewLineMode;
    private final List<String> list;
    private final Lock lock;
    private final FileWriter writer;

    public FileAppender(File file, int i2, boolean z2) {
        this(file, CharsetUtil.CHARSET_UTF_8, i2, z2);
    }

    public FileAppender append(String str) {
        if (this.list.size() >= this.capacity) {
            flush();
        }
        this.lock.lock();
        try {
            this.list.add(str);
            return this;
        } finally {
            this.lock.unlock();
        }
    }

    public FileAppender flush() {
        this.lock.lock();
        try {
            PrintWriter printWriter = this.writer.getPrintWriter(true);
            try {
                Iterator<String> it = this.list.iterator();
                while (it.hasNext()) {
                    printWriter.print(it.next());
                    if (this.isNewLineMode) {
                        printWriter.println();
                    }
                }
                if (printWriter != null) {
                    printWriter.close();
                }
                this.list.clear();
                return this;
            } finally {
            }
        } finally {
            this.lock.unlock();
        }
    }

    public FileAppender(File file, Charset charset, int i2, boolean z2) {
        this(file, charset, i2, z2, null);
    }

    public FileAppender(File file, Charset charset, int i2, boolean z2, Lock lock) {
        this.capacity = i2;
        this.list = new ArrayList(i2);
        this.isNewLineMode = z2;
        this.writer = FileWriter.create(file, charset);
        this.lock = (Lock) ObjectUtil.defaultIfNull(lock, (Supplier<? extends Lock>) new Supplier() { // from class: cn.hutool.core.io.file.a
            @Override // java.util.function.Supplier
            public final Object get() {
                return LockUtil.getNoLock();
            }
        });
    }
}
