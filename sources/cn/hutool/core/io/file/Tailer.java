package cn.hutool.core.io.file;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.LineHandler;
import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.WatchKind;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.CharsetUtil;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.Stack;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class Tailer implements Serializable {
    public static final LineHandler CONSOLE_HANDLER = new ConsoleLineHandler();
    private static final long serialVersionUID = 1;
    private final Charset charset;
    private final ScheduledExecutorService executorService;
    private WatchMonitor fileDeleteWatchMonitor;
    private final String filePath;
    private final int initReadLine;
    private final LineHandler lineHandler;
    private final long period;
    private final RandomAccessFile randomAccessFile;
    private boolean stopOnDelete;

    public static class ConsoleLineHandler implements LineHandler {
        @Override // cn.hutool.core.io.LineHandler
        public void handle(String str) {
            Console.log(str);
        }
    }

    public Tailer(File file, LineHandler lineHandler) {
        this(file, lineHandler, 0);
    }

    private static void checkFile(File file) {
        if (!file.exists()) {
            throw new UtilException("File [{}] not exist !", file.getAbsolutePath());
        }
        if (!file.isFile()) {
            throw new UtilException("Path [{}] is not a file !", file.getAbsolutePath());
        }
    }

    private void readTail() throws IOException {
        long length = this.randomAccessFile.length();
        if (this.initReadLine > 0) {
            Stack stack = new Stack();
            long filePointer = this.randomAccessFile.getFilePointer();
            long j2 = length - 1;
            if (j2 < 0) {
                j2 = 0;
            }
            this.randomAccessFile.seek(j2);
            int i2 = 0;
            while (true) {
                if (j2 <= filePointer || i2 > this.initReadLine) {
                    break;
                }
                int i3 = this.randomAccessFile.read();
                if (i3 == 10 || i3 == 13) {
                    String line = FileUtil.readLine(this.randomAccessFile, this.charset);
                    if (line != null) {
                        stack.push(line);
                    }
                    i2++;
                    j2--;
                }
                j2--;
                this.randomAccessFile.seek(j2);
                if (j2 == 0) {
                    String line2 = FileUtil.readLine(this.randomAccessFile, this.charset);
                    if (line2 != null) {
                        stack.push(line2);
                    }
                }
            }
            while (!stack.isEmpty()) {
                this.lineHandler.handle((String) stack.pop());
            }
        }
        try {
            this.randomAccessFile.seek(length);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public void setStopOnDelete(boolean z2) {
        this.stopOnDelete = z2;
    }

    public void start() {
        start(false);
    }

    public void stop() throws IOException {
        try {
            this.executorService.shutdown();
        } finally {
            IoUtil.close((Closeable) this.randomAccessFile);
            IoUtil.close((Closeable) this.fileDeleteWatchMonitor);
        }
    }

    public Tailer(File file, LineHandler lineHandler, int i2) {
        this(file, CharsetUtil.CHARSET_UTF_8, lineHandler, i2, DateUnit.SECOND.getMillis());
    }

    public void start(boolean z2) throws ExecutionException, InterruptedException {
        try {
            readTail();
            ScheduledFuture<?> scheduledFutureScheduleAtFixedRate = this.executorService.scheduleAtFixedRate(new LineReadWatcher(this.randomAccessFile, this.charset, this.lineHandler), 0L, this.period, TimeUnit.MILLISECONDS);
            if (this.stopOnDelete) {
                WatchMonitor watchMonitorCreate = WatchMonitor.create(this.filePath, (WatchEvent.Kind<?>[]) new WatchEvent.Kind[]{WatchKind.DELETE.getValue()});
                this.fileDeleteWatchMonitor = watchMonitorCreate;
                watchMonitorCreate.setWatcher(new SimpleWatcher() { // from class: cn.hutool.core.io.file.Tailer.1
                    @Override // cn.hutool.core.io.watch.watchers.IgnoreWatcher, cn.hutool.core.io.watch.Watcher
                    public void onDelete(WatchEvent<?> watchEvent, Path path) throws IOException {
                        super.onDelete(watchEvent, path);
                        Tailer.this.stop();
                        throw new IORuntimeException("{} has been deleted", Tailer.this.filePath);
                    }
                });
                this.fileDeleteWatchMonitor.start();
            }
            if (z2) {
                return;
            }
            try {
                scheduledFutureScheduleAtFixedRate.get();
            } catch (InterruptedException unused) {
            } catch (ExecutionException e2) {
                throw new UtilException(e2);
            }
        } catch (IOException e3) {
            throw new IORuntimeException(e3);
        }
    }

    public Tailer(File file, Charset charset, LineHandler lineHandler) {
        this(file, charset, lineHandler, 0, DateUnit.SECOND.getMillis());
    }

    public Tailer(File file, Charset charset, LineHandler lineHandler, int i2, long j2) {
        checkFile(file);
        this.charset = charset;
        this.lineHandler = lineHandler;
        this.period = j2;
        this.initReadLine = i2;
        this.randomAccessFile = FileUtil.createRandomAccessFile(file, FileMode.r);
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        this.filePath = file.getAbsolutePath();
    }
}
