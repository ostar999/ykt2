package cn.hutool.core.io.watch;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.file.PathUtil;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.URLUtil;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.attribute.FileAttribute;

/* loaded from: classes.dex */
public class WatchMonitor extends WatchServer {
    private static final long serialVersionUID = 1;
    private Path filePath;
    private int maxDepth;
    private Path path;
    private Watcher watcher;
    public static final WatchEvent.Kind<?> OVERFLOW = WatchKind.OVERFLOW.getValue();
    public static final WatchEvent.Kind<?> ENTRY_MODIFY = WatchKind.MODIFY.getValue();
    public static final WatchEvent.Kind<?> ENTRY_CREATE = WatchKind.CREATE.getValue();
    public static final WatchEvent.Kind<?> ENTRY_DELETE = WatchKind.DELETE.getValue();
    public static final WatchEvent.Kind<?>[] EVENTS_ALL = WatchKind.ALL;

    public WatchMonitor(File file, WatchEvent.Kind<?>... kindArr) {
        this(file.toPath(), kindArr);
    }

    public static WatchMonitor create(URL url, WatchEvent.Kind<?>... kindArr) {
        return create(url, 0, kindArr);
    }

    public static WatchMonitor createAll(URI uri, Watcher watcher) {
        return createAll(Paths.get(uri), watcher);
    }

    private void doTakeAndWatch(Watcher watcher) throws InterruptedException, IOException {
        super.watch(watcher, new Filter() { // from class: cn.hutool.core.io.watch.g
            @Override // cn.hutool.core.lang.Filter
            public final boolean accept(Object obj) {
                return this.f2469a.lambda$doTakeAndWatch$0((WatchEvent) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$doTakeAndWatch$0(WatchEvent watchEvent) {
        Path path = this.filePath;
        return path == null || path.endsWith(watchEvent.context().toString());
    }

    private void registerPath() throws IOException {
        registerPath(this.path, this.filePath != null ? 0 : this.maxDepth);
    }

    @Override // cn.hutool.core.io.watch.WatchServer
    public void init() throws WatchException, IOException {
        if (!Files.exists(this.path, LinkOption.NOFOLLOW_LINKS)) {
            Path lastPathEle = PathUtil.getLastPathEle(this.path);
            if (lastPathEle != null) {
                String string = lastPathEle.toString();
                if (CharSequenceUtil.contains((CharSequence) string, '.') && !CharSequenceUtil.endWithIgnoreCase(string, ".d")) {
                    Path path = this.path;
                    this.filePath = path;
                    this.path = path.getParent();
                }
            }
            try {
                Files.createDirectories(this.path, new FileAttribute[0]);
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        } else if (Files.isRegularFile(this.path, LinkOption.NOFOLLOW_LINKS)) {
            Path path2 = this.path;
            this.filePath = path2;
            this.path = path2.getParent();
        }
        super.init();
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws InterruptedException, WatchException, IOException {
        watch();
    }

    public WatchMonitor setMaxDepth(int i2) {
        this.maxDepth = i2;
        return this;
    }

    public WatchMonitor setWatcher(Watcher watcher) {
        this.watcher = watcher;
        return this;
    }

    public void watch() throws InterruptedException, WatchException, IOException {
        watch(this.watcher);
    }

    public WatchMonitor(String str, WatchEvent.Kind<?>... kindArr) {
        this(Paths.get(str, new String[0]), kindArr);
    }

    public static WatchMonitor create(URL url, int i2, WatchEvent.Kind<?>... kindArr) {
        return create(URLUtil.toURI(url), i2, kindArr);
    }

    public static WatchMonitor createAll(URL url, Watcher watcher) {
        try {
            return createAll(Paths.get(url.toURI()), watcher);
        } catch (URISyntaxException e2) {
            throw new WatchException(e2);
        }
    }

    public void watch(Watcher watcher) throws InterruptedException, WatchException, IOException {
        if (this.isClosed) {
            throw new WatchException("Watch Monitor is closed !");
        }
        registerPath();
        while (!this.isClosed) {
            doTakeAndWatch(watcher);
        }
    }

    public WatchMonitor(Path path, WatchEvent.Kind<?>... kindArr) {
        this(path, 0, kindArr);
    }

    public static WatchMonitor create(URI uri, WatchEvent.Kind<?>... kindArr) {
        return create(uri, 0, kindArr);
    }

    public WatchMonitor(Path path, int i2, WatchEvent.Kind<?>... kindArr) throws WatchException, IOException {
        this.path = path;
        this.maxDepth = i2;
        this.events = kindArr;
        init();
    }

    public static WatchMonitor create(URI uri, int i2, WatchEvent.Kind<?>... kindArr) {
        return create(Paths.get(uri), i2, kindArr);
    }

    public static WatchMonitor createAll(File file, Watcher watcher) {
        return createAll(file.toPath(), watcher);
    }

    public static WatchMonitor create(File file, WatchEvent.Kind<?>... kindArr) {
        return create(file, 0, kindArr);
    }

    public static WatchMonitor createAll(String str, Watcher watcher) {
        return createAll(Paths.get(str, new String[0]), watcher);
    }

    public static WatchMonitor create(File file, int i2, WatchEvent.Kind<?>... kindArr) {
        return create(file.toPath(), i2, kindArr);
    }

    public static WatchMonitor createAll(Path path, Watcher watcher) {
        WatchMonitor watchMonitorCreate = create(path, EVENTS_ALL);
        watchMonitorCreate.setWatcher(watcher);
        return watchMonitorCreate;
    }

    public static WatchMonitor create(String str, WatchEvent.Kind<?>... kindArr) {
        return create(str, 0, kindArr);
    }

    public static WatchMonitor create(String str, int i2, WatchEvent.Kind<?>... kindArr) {
        return create(Paths.get(str, new String[0]), i2, kindArr);
    }

    public static WatchMonitor create(Path path, WatchEvent.Kind<?>... kindArr) {
        return create(path, 0, kindArr);
    }

    public static WatchMonitor create(Path path, int i2, WatchEvent.Kind<?>... kindArr) {
        return new WatchMonitor(path, i2, kindArr);
    }
}
