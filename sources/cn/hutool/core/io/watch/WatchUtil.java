package cn.hutool.core.io.watch;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.URLUtil;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.Watchable;

/* loaded from: classes.dex */
public class WatchUtil {
    public static WatchMonitor create(URL url, WatchEvent.Kind<?>... kindArr) {
        return create(url, 0, kindArr);
    }

    public static WatchMonitor createAll(URL url, Watcher watcher) {
        return createAll(url, 0, watcher);
    }

    public static WatchMonitor createModify(URL url, Watcher watcher) {
        return createModify(url, 0, watcher);
    }

    public static WatchKey register(Watchable watchable, WatchService watchService, WatchEvent.Kind<?>... kindArr) {
        try {
            return watchable.register(watchService, kindArr);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static WatchMonitor create(URL url, int i2, WatchEvent.Kind<?>... kindArr) {
        return create(URLUtil.toURI(url), i2, kindArr);
    }

    public static WatchMonitor createAll(URL url, int i2, Watcher watcher) {
        return createAll(URLUtil.toURI(url), i2, watcher);
    }

    public static WatchMonitor createModify(URL url, int i2, Watcher watcher) {
        return createModify(URLUtil.toURI(url), i2, watcher);
    }

    public static WatchMonitor create(URI uri, WatchEvent.Kind<?>... kindArr) {
        return create(uri, 0, kindArr);
    }

    public static WatchMonitor createAll(URI uri, Watcher watcher) {
        return createAll(uri, 0, watcher);
    }

    public static WatchMonitor createModify(URI uri, Watcher watcher) {
        return createModify(uri, 0, watcher);
    }

    public static WatchMonitor create(URI uri, int i2, WatchEvent.Kind<?>... kindArr) {
        return create(Paths.get(uri), i2, kindArr);
    }

    public static WatchMonitor createAll(URI uri, int i2, Watcher watcher) {
        return createAll(Paths.get(uri), i2, watcher);
    }

    public static WatchMonitor createModify(URI uri, int i2, Watcher watcher) {
        return createModify(Paths.get(uri), i2, watcher);
    }

    public static WatchMonitor create(File file, WatchEvent.Kind<?>... kindArr) {
        return create(file, 0, kindArr);
    }

    public static WatchMonitor createAll(File file, Watcher watcher) {
        return createAll(file, 0, watcher);
    }

    public static WatchMonitor createModify(File file, Watcher watcher) {
        return createModify(file, 0, watcher);
    }

    public static WatchMonitor create(File file, int i2, WatchEvent.Kind<?>... kindArr) {
        return create(file.toPath(), i2, kindArr);
    }

    public static WatchMonitor createAll(File file, int i2, Watcher watcher) {
        return createAll(file.toPath(), i2, watcher);
    }

    public static WatchMonitor createModify(File file, int i2, Watcher watcher) {
        return createModify(file.toPath(), i2, watcher);
    }

    public static WatchMonitor create(String str, WatchEvent.Kind<?>... kindArr) {
        return create(str, 0, kindArr);
    }

    public static WatchMonitor createAll(String str, Watcher watcher) {
        return createAll(str, 0, watcher);
    }

    public static WatchMonitor createModify(String str, Watcher watcher) {
        return createModify(str, 0, watcher);
    }

    public static WatchMonitor create(String str, int i2, WatchEvent.Kind<?>... kindArr) {
        return create(Paths.get(str, new String[0]), i2, kindArr);
    }

    public static WatchMonitor createAll(String str, int i2, Watcher watcher) {
        return createAll(Paths.get(str, new String[0]), i2, watcher);
    }

    public static WatchMonitor createModify(String str, int i2, Watcher watcher) {
        return createModify(Paths.get(str, new String[0]), i2, watcher);
    }

    public static WatchMonitor create(Path path, WatchEvent.Kind<?>... kindArr) {
        return create(path, 0, kindArr);
    }

    public static WatchMonitor createAll(Path path, Watcher watcher) {
        return createAll(path, 0, watcher);
    }

    public static WatchMonitor createModify(Path path, Watcher watcher) {
        return createModify(path, 0, watcher);
    }

    public static WatchMonitor create(Path path, int i2, WatchEvent.Kind<?>... kindArr) {
        return new WatchMonitor(path, i2, kindArr);
    }

    public static WatchMonitor createAll(Path path, int i2, Watcher watcher) {
        WatchMonitor watchMonitorCreate = create(path, i2, WatchMonitor.EVENTS_ALL);
        watchMonitorCreate.setWatcher(watcher);
        return watchMonitorCreate;
    }

    public static WatchMonitor createModify(Path path, int i2, Watcher watcher) {
        WatchMonitor watchMonitorCreate = create(path, i2, (WatchEvent.Kind<?>[]) new WatchEvent.Kind[]{WatchMonitor.ENTRY_MODIFY});
        watchMonitorCreate.setWatcher(watcher);
        return watchMonitorCreate;
    }
}
