package cn.hutool.core.io.watch.watchers;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.lang.Chain;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class WatcherChain implements Watcher, Chain<Watcher, WatcherChain> {
    private final List<Watcher> chain;

    public WatcherChain(Watcher... watcherArr) {
        this.chain = CollUtil.newArrayList(watcherArr);
    }

    public static WatcherChain create(Watcher... watcherArr) {
        return new WatcherChain(watcherArr);
    }

    @Override // java.lang.Iterable
    public Iterator<Watcher> iterator() {
        return this.chain.iterator();
    }

    @Override // cn.hutool.core.io.watch.Watcher
    public void onCreate(WatchEvent<?> watchEvent, Path path) {
        Iterator<Watcher> it = this.chain.iterator();
        while (it.hasNext()) {
            it.next().onCreate(watchEvent, path);
        }
    }

    @Override // cn.hutool.core.io.watch.Watcher
    public void onDelete(WatchEvent<?> watchEvent, Path path) {
        Iterator<Watcher> it = this.chain.iterator();
        while (it.hasNext()) {
            it.next().onDelete(watchEvent, path);
        }
    }

    @Override // cn.hutool.core.io.watch.Watcher
    public void onModify(WatchEvent<?> watchEvent, Path path) {
        Iterator<Watcher> it = this.chain.iterator();
        while (it.hasNext()) {
            it.next().onModify(watchEvent, path);
        }
    }

    @Override // cn.hutool.core.io.watch.Watcher
    public void onOverflow(WatchEvent<?> watchEvent, Path path) {
        Iterator<Watcher> it = this.chain.iterator();
        while (it.hasNext()) {
            it.next().onOverflow(watchEvent, path);
        }
    }

    @Override // cn.hutool.core.lang.Chain
    public WatcherChain addChain(Watcher watcher) {
        this.chain.add(watcher);
        return this;
    }
}
