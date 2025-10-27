package cn.hutool.core.io.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.LineHandler;
import cn.hutool.core.io.watch.SimpleWatcher;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

/* loaded from: classes.dex */
public class LineReadWatcher extends SimpleWatcher implements Runnable {
    private final Charset charset;
    private final LineHandler lineHandler;
    private final RandomAccessFile randomAccessFile;

    public LineReadWatcher(RandomAccessFile randomAccessFile, Charset charset, LineHandler lineHandler) {
        this.randomAccessFile = randomAccessFile;
        this.charset = charset;
        this.lineHandler = lineHandler;
    }

    @Override // cn.hutool.core.io.watch.watchers.IgnoreWatcher, cn.hutool.core.io.watch.Watcher
    public void onModify(WatchEvent<?> watchEvent, Path path) throws IOException {
        RandomAccessFile randomAccessFile = this.randomAccessFile;
        Charset charset = this.charset;
        LineHandler lineHandler = this.lineHandler;
        try {
            long length = randomAccessFile.length();
            long filePointer = randomAccessFile.getFilePointer();
            if (filePointer == length) {
                return;
            }
            if (length < filePointer) {
                randomAccessFile.seek(length);
            } else {
                FileUtil.readLines(randomAccessFile, charset, lineHandler);
                randomAccessFile.seek(length);
            }
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    @Override // java.lang.Runnable
    public void run() throws IOException {
        onModify(null, null);
    }
}
