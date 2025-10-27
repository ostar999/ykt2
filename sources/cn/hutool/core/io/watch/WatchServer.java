package cn.hutool.core.io.watch;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.util.ArrayUtil;
import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.AccessDeniedException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class WatchServer extends Thread implements Closeable, Serializable {
    private static final long serialVersionUID = 1;
    protected WatchEvent.Kind<?>[] events;
    protected boolean isClosed;
    private WatchEvent.Modifier[] modifiers;
    private final Map<WatchKey, Path> watchKeyPathMap = new HashMap();
    private WatchService watchService;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$watch$0(Watcher watcher, WatchEvent watchEvent, Path path) {
        WatchEvent.Kind<?> kind = watchEvent.kind();
        if (kind == WatchKind.CREATE.getValue()) {
            watcher.onCreate(watchEvent, path);
            return;
        }
        if (kind == WatchKind.MODIFY.getValue()) {
            watcher.onModify(watchEvent, path);
        } else if (kind == WatchKind.DELETE.getValue()) {
            watcher.onDelete(watchEvent, path);
        } else if (kind == WatchKind.OVERFLOW.getValue()) {
            watcher.onOverflow(watchEvent, path);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.isClosed = true;
        IoUtil.close((Closeable) this.watchService);
    }

    public void init() throws WatchException {
        try {
            this.watchService = FileSystems.getDefault().newWatchService();
            this.isClosed = false;
        } catch (IOException e2) {
            throw new WatchException(e2);
        }
    }

    public void registerPath(Path path, int i2) throws IOException {
        WatchEvent.Kind[] kindArr = (WatchEvent.Kind[]) ArrayUtil.defaultIfEmpty(this.events, WatchKind.ALL);
        try {
            this.watchKeyPathMap.put(ArrayUtil.isEmpty((Object[]) this.modifiers) ? path.register(this.watchService, kindArr) : path.register(this.watchService, kindArr, this.modifiers), path);
            if (i2 > 1) {
                Files.walkFileTree(path, EnumSet.noneOf(FileVisitOption.class), i2, new SimpleFileVisitor<Path>() { // from class: cn.hutool.core.io.watch.WatchServer.1
                    @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                    public FileVisitResult postVisitDirectory(Path path2, IOException iOException) throws IOException {
                        WatchServer.this.registerPath(path2, 0);
                        return super.postVisitDirectory((AnonymousClass1) path2, iOException);
                    }
                });
            }
        } catch (IOException e2) {
            if (!(e2 instanceof AccessDeniedException)) {
                throw new WatchException(e2);
            }
        }
    }

    public void setModifiers(WatchEvent.Modifier[] modifierArr) {
        this.modifiers = modifierArr;
    }

    public void watch(WatchAction watchAction, Filter<WatchEvent<?>> filter) throws InterruptedException, IOException {
        try {
            WatchKey watchKeyTake = this.watchService.take();
            Path path = this.watchKeyPathMap.get(watchKeyTake);
            for (WatchEvent<?> watchEvent : watchKeyTake.pollEvents()) {
                if (filter == null || filter.accept(watchEvent)) {
                    watchAction.doAction(watchEvent, path);
                }
            }
            watchKeyTake.reset();
        } catch (InterruptedException | ClosedWatchServiceException unused) {
            close();
        }
    }

    public void watch(final Watcher watcher, Filter<WatchEvent<?>> filter) throws InterruptedException, IOException {
        watch(new WatchAction() { // from class: cn.hutool.core.io.watch.p
            @Override // cn.hutool.core.io.watch.WatchAction
            public final void doAction(WatchEvent watchEvent, Path path) {
                WatchServer.lambda$watch$0(watcher, watchEvent, path);
            }
        }, filter);
    }
}
