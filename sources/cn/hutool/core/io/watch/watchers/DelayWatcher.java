package cn.hutool.core.io.watch.watchers;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.thread.ThreadUtil;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.util.Set;

/* loaded from: classes.dex */
public class DelayWatcher implements Watcher {
    private final long delay;
    private final Set<Path> eventSet = new ConcurrentHashSet();
    private final Watcher watcher;

    public DelayWatcher(Watcher watcher, long j2) throws IllegalArgumentException {
        Assert.notNull(watcher);
        if (watcher instanceof DelayWatcher) {
            throw new IllegalArgumentException("Watcher must not be a DelayWatcher");
        }
        this.watcher = watcher;
        this.delay = j2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startHandleModifyThread$0(Path path, WatchEvent watchEvent) {
        ThreadUtil.sleep(this.delay);
        this.eventSet.remove(Paths.get(path.toString(), watchEvent.context().toString()));
        this.watcher.onModify(watchEvent, path);
    }

    private void onDelayModify(WatchEvent<?> watchEvent, Path path) {
        Path path2 = Paths.get(path.toString(), watchEvent.context().toString());
        if (this.eventSet.contains(path2)) {
            return;
        }
        this.eventSet.add(path2);
        startHandleModifyThread(watchEvent, path);
    }

    private void startHandleModifyThread(final WatchEvent<?> watchEvent, final Path path) {
        ThreadUtil.execute(new Runnable() { // from class: a0.a
            @Override // java.lang.Runnable
            public final void run() {
                this.f1206c.lambda$startHandleModifyThread$0(path, watchEvent);
            }
        });
    }

    @Override // cn.hutool.core.io.watch.Watcher
    public void onCreate(WatchEvent<?> watchEvent, Path path) {
        this.watcher.onCreate(watchEvent, path);
    }

    @Override // cn.hutool.core.io.watch.Watcher
    public void onDelete(WatchEvent<?> watchEvent, Path path) {
        this.watcher.onDelete(watchEvent, path);
    }

    @Override // cn.hutool.core.io.watch.Watcher
    public void onModify(WatchEvent<?> watchEvent, Path path) {
        if (this.delay < 1) {
            this.watcher.onModify(watchEvent, path);
        } else {
            onDelayModify(watchEvent, path);
        }
    }

    @Override // cn.hutool.core.io.watch.Watcher
    public void onOverflow(WatchEvent<?> watchEvent, Path path) {
        this.watcher.onOverflow(watchEvent, path);
    }
}
