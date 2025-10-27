package cn.hutool.core.swing.clipboard;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ObjectUtil;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.io.Closeable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/* loaded from: classes.dex */
public enum ClipboardMonitor implements ClipboardOwner, Runnable, Closeable {
    INSTANCE;

    public static final long DEFAULT_DELAY = 100;
    public static final int DEFAULT_TRY_COUNT = 10;
    private final Clipboard clipboard;
    private long delay;
    private boolean isRunning;
    private final Set<ClipboardListener> listenerSet;
    private int tryCount;

    ClipboardMonitor() {
        this(10, 100L);
    }

    private Transferable tryGetContent(Clipboard clipboard) throws InterruptedException {
        Transferable contents = null;
        for (int i2 = 0; i2 < this.tryCount; i2++) {
            long j2 = this.delay;
            if (j2 > 0 && i2 > 0) {
                Thread.sleep(j2);
            }
            try {
                contents = clipboard.getContents((Object) null);
            } catch (IllegalStateException unused) {
            }
            if (contents != null) {
                return contents;
            }
        }
        return null;
    }

    public ClipboardMonitor addListener(ClipboardListener clipboardListener) {
        this.listenerSet.add(clipboardListener);
        return this;
    }

    public ClipboardMonitor clearListener() {
        this.listenerSet.clear();
        return this;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.isRunning = false;
    }

    public void listen(boolean z2) {
        run();
        if (z2) {
            ThreadUtil.sync(this);
        }
    }

    public void lostOwnership(Clipboard clipboard, Transferable transferable) {
        try {
            Transferable transferableTryGetContent = tryGetContent(clipboard);
            Iterator<ClipboardListener> it = this.listenerSet.iterator();
            Transferable transferableOnChange = null;
            while (it.hasNext()) {
                try {
                    transferableOnChange = it.next().onChange(clipboard, (Transferable) ObjectUtil.defaultIfNull(transferableOnChange, transferableTryGetContent));
                } catch (Throwable unused) {
                }
            }
            if (this.isRunning) {
                clipboard.setContents((Transferable) ObjectUtil.defaultIfNull(transferableOnChange, ObjectUtil.defaultIfNull(transferableTryGetContent, transferable)), this);
            }
        } catch (InterruptedException unused2) {
        }
    }

    public ClipboardMonitor removeListener(ClipboardListener clipboardListener) {
        this.listenerSet.remove(clipboardListener);
        return this;
    }

    @Override // java.lang.Runnable
    public synchronized void run() {
        if (!this.isRunning) {
            Clipboard clipboard = this.clipboard;
            clipboard.setContents(clipboard.getContents((Object) null), this);
            this.isRunning = true;
        }
    }

    public ClipboardMonitor setDelay(long j2) {
        this.delay = j2;
        return this;
    }

    public ClipboardMonitor setTryCount(int i2) {
        this.tryCount = i2;
        return this;
    }

    ClipboardMonitor(int i2, long j2) {
        this(i2, j2, ClipboardUtil.getClipboard());
    }

    ClipboardMonitor(int i2, long j2, Clipboard clipboard) {
        this.listenerSet = new LinkedHashSet();
        this.tryCount = i2;
        this.delay = j2;
        this.clipboard = clipboard;
    }
}
