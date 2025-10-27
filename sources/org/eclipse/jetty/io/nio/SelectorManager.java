package org.eclipse.jetty.io.nio;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.io.AsyncEndPoint;
import org.eclipse.jetty.io.ConnectedEndPoint;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.thread.Timeout;

/* loaded from: classes9.dex */
public abstract class SelectorManager extends AbstractLifeCycle implements Dumpable {
    private long _lowResourcesConnections;
    private int _lowResourcesMaxIdleTime;
    private int _maxIdleTime;
    private SelectSet[] _selectSet;
    public static final Logger LOG = Log.getLogger("org.eclipse.jetty.io.nio");
    private static final int __MONITOR_PERIOD = Integer.getInteger("org.eclipse.jetty.io.nio.MONITOR_PERIOD", 1000).intValue();
    private static final int __MAX_SELECTS = Integer.getInteger("org.eclipse.jetty.io.nio.MAX_SELECTS", 100000).intValue();
    private static final int __BUSY_PAUSE = Integer.getInteger("org.eclipse.jetty.io.nio.BUSY_PAUSE", 50).intValue();
    private static final int __IDLE_TICK = Integer.getInteger("org.eclipse.jetty.io.nio.IDLE_TICK", 400).intValue();
    private int _selectSets = 1;
    private volatile int _set = 0;
    private boolean _deferringInterestedOps0 = true;
    private int _selectorPriorityDelta = 0;

    public interface ChangeTask extends Runnable {
    }

    public static class ChannelAndAttachment {
        final Object _attachment;
        final SelectableChannel _channel;

        public ChannelAndAttachment(SelectableChannel selectableChannel, Object obj) {
            this._channel = selectableChannel;
            this._attachment = obj;
        }
    }

    public class SelectSet implements Dumpable {
        private int _busySelects;
        private final ConcurrentLinkedQueue<Object> _changes = new ConcurrentLinkedQueue<>();
        private ConcurrentMap<SelectChannelEndPoint, Object> _endPoints = new ConcurrentHashMap();
        private volatile long _idleTick = System.currentTimeMillis();
        private long _monitorNext;
        private boolean _paused;
        private boolean _pausing;
        private volatile Thread _selecting;
        private volatile Selector _selector;
        private final int _setID;
        private final Timeout _timeout;

        public SelectSet(int i2) throws Exception {
            this._setID = i2;
            Timeout timeout = new Timeout(this);
            this._timeout = timeout;
            timeout.setDuration(0L);
            this._selector = Selector.open();
            this._monitorNext = System.currentTimeMillis() + SelectorManager.__MONITOR_PERIOD;
        }

        private SelectChannelEndPoint createEndPoint(SocketChannel socketChannel, SelectionKey selectionKey) throws IOException {
            SelectChannelEndPoint selectChannelEndPointNewEndPoint = SelectorManager.this.newEndPoint(socketChannel, this, selectionKey);
            SelectorManager.LOG.debug("created {}", selectChannelEndPointNewEndPoint);
            SelectorManager.this.endPointOpened(selectChannelEndPointNewEndPoint);
            this._endPoints.put(selectChannelEndPointNewEndPoint, this);
            return selectChannelEndPointNewEndPoint;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void renewSelector() {
            try {
                synchronized (this) {
                    Selector selector = this._selector;
                    if (selector == null) {
                        return;
                    }
                    Selector selectorOpen = Selector.open();
                    for (SelectionKey selectionKey : selector.keys()) {
                        if (selectionKey.isValid() && selectionKey.interestOps() != 0) {
                            SelectableChannel selectableChannelChannel = selectionKey.channel();
                            Object objAttachment = selectionKey.attachment();
                            if (objAttachment == null) {
                                addChange(selectableChannelChannel);
                            } else {
                                addChange(selectableChannelChannel, objAttachment);
                            }
                        }
                    }
                    this._selector.close();
                    this._selector = selectorOpen;
                }
            } catch (IOException e2) {
                throw new RuntimeException("recreating selector", e2);
            }
        }

        public void addChange(Object obj) {
            this._changes.add(obj);
        }

        public void cancelTimeout(Timeout.Task task) {
            task.cancel();
        }

        public void destroyEndPoint(SelectChannelEndPoint selectChannelEndPoint) {
            SelectorManager.LOG.debug("destroyEndPoint {}", selectChannelEndPoint);
            this._endPoints.remove(selectChannelEndPoint);
            SelectorManager.this.endPointClosed(selectChannelEndPoint);
        }

        /* JADX WARN: Code restructure failed: missing block: B:62:0x00d2, code lost:
        
            r2 = r1.selectNow();
            r5 = java.lang.System.currentTimeMillis();
         */
        /* JADX WARN: Code restructure failed: missing block: B:63:0x00dd, code lost:
        
            if (r2 != 0) goto L96;
         */
        /* JADX WARN: Code restructure failed: missing block: B:65:0x00e7, code lost:
        
            if (r1.selectedKeys().isEmpty() == false) goto L96;
         */
        /* JADX WARN: Code restructure failed: missing block: B:67:0x00eb, code lost:
        
            if (r14._pausing == false) goto L73;
         */
        /* JADX WARN: Code restructure failed: missing block: B:68:0x00ed, code lost:
        
            java.lang.Thread.sleep(org.eclipse.jetty.io.nio.SelectorManager.__BUSY_PAUSE);
         */
        /* JADX WARN: Code restructure failed: missing block: B:70:0x00f6, code lost:
        
            r2 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:71:0x00f7, code lost:
        
            org.eclipse.jetty.io.nio.SelectorManager.LOG.ignore(r2);
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:104:0x017d A[Catch: all -> 0x02ca, CancelledKeyException -> 0x02cc, ClosedSelectorException -> 0x02d5, TRY_LEAVE, TryCatch #16 {CancelledKeyException -> 0x02cc, ClosedSelectorException -> 0x02d5, blocks: (B:3:0x0001, B:7:0x000e, B:8:0x0014, B:10:0x0019, B:50:0x00a9, B:52:0x00b1, B:55:0x00be, B:58:0x00c3, B:53:0x00b7, B:60:0x00ca, B:62:0x00d2, B:64:0x00df, B:66:0x00e9, B:68:0x00ed, B:72:0x00fc, B:73:0x0100, B:75:0x0113, B:87:0x012c, B:89:0x013e, B:91:0x0145, B:93:0x0150, B:95:0x0156, B:71:0x00f7, B:96:0x0163, B:98:0x0167, B:101:0x016f, B:102:0x0177, B:104:0x017d, B:141:0x0200, B:143:0x0208, B:146:0x0215, B:151:0x0221, B:153:0x0229, B:155:0x022f, B:149:0x021a, B:144:0x020e, B:157:0x0235, B:158:0x023c, B:160:0x0254, B:162:0x0258, B:163:0x025f, B:164:0x0266, B:166:0x0273, B:168:0x027f, B:170:0x0292, B:172:0x02a4, B:173:0x02ae, B:175:0x02b4, B:177:0x02ba), top: B:206:0x0001, outer: #8 }] */
        /* JADX WARN: Removed duplicated region for block: B:160:0x0254 A[Catch: all -> 0x02ca, CancelledKeyException -> 0x02cc, ClosedSelectorException -> 0x02d5, TryCatch #16 {CancelledKeyException -> 0x02cc, ClosedSelectorException -> 0x02d5, blocks: (B:3:0x0001, B:7:0x000e, B:8:0x0014, B:10:0x0019, B:50:0x00a9, B:52:0x00b1, B:55:0x00be, B:58:0x00c3, B:53:0x00b7, B:60:0x00ca, B:62:0x00d2, B:64:0x00df, B:66:0x00e9, B:68:0x00ed, B:72:0x00fc, B:73:0x0100, B:75:0x0113, B:87:0x012c, B:89:0x013e, B:91:0x0145, B:93:0x0150, B:95:0x0156, B:71:0x00f7, B:96:0x0163, B:98:0x0167, B:101:0x016f, B:102:0x0177, B:104:0x017d, B:141:0x0200, B:143:0x0208, B:146:0x0215, B:151:0x0221, B:153:0x0229, B:155:0x022f, B:149:0x021a, B:144:0x020e, B:157:0x0235, B:158:0x023c, B:160:0x0254, B:162:0x0258, B:163:0x025f, B:164:0x0266, B:166:0x0273, B:168:0x027f, B:170:0x0292, B:172:0x02a4, B:173:0x02ae, B:175:0x02b4, B:177:0x02ba), top: B:206:0x0001, outer: #8 }] */
        /* JADX WARN: Removed duplicated region for block: B:166:0x0273 A[Catch: all -> 0x02ca, CancelledKeyException -> 0x02cc, ClosedSelectorException -> 0x02d5, TryCatch #16 {CancelledKeyException -> 0x02cc, ClosedSelectorException -> 0x02d5, blocks: (B:3:0x0001, B:7:0x000e, B:8:0x0014, B:10:0x0019, B:50:0x00a9, B:52:0x00b1, B:55:0x00be, B:58:0x00c3, B:53:0x00b7, B:60:0x00ca, B:62:0x00d2, B:64:0x00df, B:66:0x00e9, B:68:0x00ed, B:72:0x00fc, B:73:0x0100, B:75:0x0113, B:87:0x012c, B:89:0x013e, B:91:0x0145, B:93:0x0150, B:95:0x0156, B:71:0x00f7, B:96:0x0163, B:98:0x0167, B:101:0x016f, B:102:0x0177, B:104:0x017d, B:141:0x0200, B:143:0x0208, B:146:0x0215, B:151:0x0221, B:153:0x0229, B:155:0x022f, B:149:0x021a, B:144:0x020e, B:157:0x0235, B:158:0x023c, B:160:0x0254, B:162:0x0258, B:163:0x025f, B:164:0x0266, B:166:0x0273, B:168:0x027f, B:170:0x0292, B:172:0x02a4, B:173:0x02ae, B:175:0x02b4, B:177:0x02ba), top: B:206:0x0001, outer: #8 }] */
        /* JADX WARN: Removed duplicated region for block: B:216:0x00be A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:227:0x00cf A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:52:0x00b1 A[Catch: all -> 0x02ca, CancelledKeyException -> 0x02cc, ClosedSelectorException -> 0x02d5, TryCatch #16 {CancelledKeyException -> 0x02cc, ClosedSelectorException -> 0x02d5, blocks: (B:3:0x0001, B:7:0x000e, B:8:0x0014, B:10:0x0019, B:50:0x00a9, B:52:0x00b1, B:55:0x00be, B:58:0x00c3, B:53:0x00b7, B:60:0x00ca, B:62:0x00d2, B:64:0x00df, B:66:0x00e9, B:68:0x00ed, B:72:0x00fc, B:73:0x0100, B:75:0x0113, B:87:0x012c, B:89:0x013e, B:91:0x0145, B:93:0x0150, B:95:0x0156, B:71:0x00f7, B:96:0x0163, B:98:0x0167, B:101:0x016f, B:102:0x0177, B:104:0x017d, B:141:0x0200, B:143:0x0208, B:146:0x0215, B:151:0x0221, B:153:0x0229, B:155:0x022f, B:149:0x021a, B:144:0x020e, B:157:0x0235, B:158:0x023c, B:160:0x0254, B:162:0x0258, B:163:0x025f, B:164:0x0266, B:166:0x0273, B:168:0x027f, B:170:0x0292, B:172:0x02a4, B:173:0x02ae, B:175:0x02b4, B:177:0x02ba), top: B:206:0x0001, outer: #8 }] */
        /* JADX WARN: Removed duplicated region for block: B:53:0x00b7 A[Catch: all -> 0x02ca, CancelledKeyException -> 0x02cc, ClosedSelectorException -> 0x02d5, TRY_LEAVE, TryCatch #16 {CancelledKeyException -> 0x02cc, ClosedSelectorException -> 0x02d5, blocks: (B:3:0x0001, B:7:0x000e, B:8:0x0014, B:10:0x0019, B:50:0x00a9, B:52:0x00b1, B:55:0x00be, B:58:0x00c3, B:53:0x00b7, B:60:0x00ca, B:62:0x00d2, B:64:0x00df, B:66:0x00e9, B:68:0x00ed, B:72:0x00fc, B:73:0x0100, B:75:0x0113, B:87:0x012c, B:89:0x013e, B:91:0x0145, B:93:0x0150, B:95:0x0156, B:71:0x00f7, B:96:0x0163, B:98:0x0167, B:101:0x016f, B:102:0x0177, B:104:0x017d, B:141:0x0200, B:143:0x0208, B:146:0x0215, B:151:0x0221, B:153:0x0229, B:155:0x022f, B:149:0x021a, B:144:0x020e, B:157:0x0235, B:158:0x023c, B:160:0x0254, B:162:0x0258, B:163:0x025f, B:164:0x0266, B:166:0x0273, B:168:0x027f, B:170:0x0292, B:172:0x02a4, B:173:0x02ae, B:175:0x02b4, B:177:0x02ba), top: B:206:0x0001, outer: #8 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void doSelect() throws java.io.IOException {
            /*
                Method dump skipped, instructions count: 750
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.io.nio.SelectorManager.SelectSet.doSelect():void");
        }

        @Override // org.eclipse.jetty.util.component.Dumpable
        public String dump() {
            return AggregateLifeCycle.dump(this);
        }

        public void dumpKeyState(List<Object> list) {
            Selector selector = this._selector;
            Set<SelectionKey> setKeys = selector.keys();
            list.add(selector + " keys=" + setKeys.size());
            for (SelectionKey selectionKey : setKeys) {
                if (selectionKey.isValid()) {
                    list.add(selectionKey.attachment() + " iOps=" + selectionKey.interestOps() + " rOps=" + selectionKey.readyOps());
                } else {
                    list.add(selectionKey.attachment() + " iOps=-1 rOps=-1");
                }
            }
        }

        public SelectorManager getManager() {
            return SelectorManager.this;
        }

        public long getNow() {
            return this._timeout.getNow();
        }

        public Selector getSelector() {
            return this._selector;
        }

        public void scheduleTimeout(Timeout.Task task, long j2) {
            if (!(task instanceof Runnable)) {
                throw new IllegalArgumentException("!Runnable");
            }
            this._timeout.schedule(task, j2);
        }

        public void stop() throws Exception {
            Selector selector;
            for (int i2 = 0; i2 < 100; i2++) {
                try {
                    if (this._selecting == null) {
                        break;
                    }
                    wakeup();
                    Thread.sleep(10L);
                } catch (Exception e2) {
                    SelectorManager.LOG.ignore(e2);
                }
            }
            synchronized (this) {
                for (SelectionKey selectionKey : this._selector.keys()) {
                    if (selectionKey != null) {
                        Object objAttachment = selectionKey.attachment();
                        if (objAttachment instanceof EndPoint) {
                            try {
                                ((EndPoint) objAttachment).close();
                            } catch (IOException e3) {
                                SelectorManager.LOG.ignore(e3);
                            }
                        }
                    }
                }
                this._timeout.cancelAll();
                try {
                    selector = this._selector;
                } catch (IOException e4) {
                    SelectorManager.LOG.ignore(e4);
                }
                if (selector != null) {
                    selector.close();
                    this._selector = null;
                } else {
                    this._selector = null;
                }
            }
        }

        public String toString() {
            Selector selector = this._selector;
            Object[] objArr = new Object[3];
            objArr[0] = super.toString();
            int size = -1;
            objArr[1] = Integer.valueOf((selector == null || !selector.isOpen()) ? -1 : selector.keys().size());
            if (selector != null && selector.isOpen()) {
                size = selector.selectedKeys().size();
            }
            objArr[2] = Integer.valueOf(size);
            return String.format("%s keys=%d selected=%d", objArr);
        }

        public void wakeup() {
            try {
                Selector selector = this._selector;
                if (selector != null) {
                    selector.wakeup();
                }
            } catch (Exception unused) {
                addChange(new ChangeTask() { // from class: org.eclipse.jetty.io.nio.SelectorManager.SelectSet.2
                    @Override // java.lang.Runnable
                    public void run() {
                        SelectSet.this.renewSelector();
                    }
                });
                renewSelector();
            }
        }

        public void addChange(SelectableChannel selectableChannel, Object obj) {
            if (obj == null) {
                addChange(selectableChannel);
            } else if (obj instanceof EndPoint) {
                addChange(obj);
            } else {
                addChange(new ChannelAndAttachment(selectableChannel, obj));
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v14 */
        /* JADX WARN: Type inference failed for: r0v15 */
        /* JADX WARN: Type inference failed for: r0v6 */
        /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r4v1, types: [java.lang.Object] */
        /* JADX WARN: Type inference failed for: r4v5, types: [java.lang.StackTraceElement] */
        @Override // org.eclipse.jetty.util.component.Dumpable
        public void dump(Appendable appendable, String str) throws InterruptedException, IOException {
            ?? r4;
            appendable.append(String.valueOf(this)).append(" id=").append(String.valueOf(this._setID)).append("\n");
            Thread thread = this._selecting;
            ?? stackTrace = thread == null ? 0 : thread.getStackTrace();
            if (stackTrace != 0) {
                int length = stackTrace.length;
                for (int i2 = 0; i2 < length; i2++) {
                    r4 = stackTrace[i2];
                    if (r4.getClassName().startsWith("org.eclipse.jetty.")) {
                        break;
                    }
                }
                r4 = "not selecting";
            } else {
                r4 = "not selecting";
            }
            Selector selector = this._selector;
            if (selector != null) {
                final ArrayList arrayList = new ArrayList(selector.keys().size() * 2);
                arrayList.add(r4);
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                addChange(new ChangeTask() { // from class: org.eclipse.jetty.io.nio.SelectorManager.SelectSet.3
                    @Override // java.lang.Runnable
                    public void run() {
                        SelectSet.this.dumpKeyState(arrayList);
                        countDownLatch.countDown();
                    }
                });
                try {
                    countDownLatch.await(5L, TimeUnit.SECONDS);
                } catch (InterruptedException e2) {
                    SelectorManager.LOG.ignore(e2);
                }
                AggregateLifeCycle.dump(appendable, str, arrayList);
            }
        }
    }

    public void connectionFailed(SocketChannel socketChannel, Throwable th, Object obj) {
        Logger logger = LOG;
        logger.warn(th + "," + socketChannel + "," + obj, new Object[0]);
        logger.debug(th);
    }

    public abstract boolean dispatch(Runnable runnable);

    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        this._selectSet = new SelectSet[this._selectSets];
        int i2 = 0;
        while (true) {
            SelectSet[] selectSetArr = this._selectSet;
            if (i2 >= selectSetArr.length) {
                break;
            }
            selectSetArr[i2] = new SelectSet(i2);
            i2++;
        }
        super.doStart();
        for (final int i3 = 0; i3 < getSelectSets(); i3++) {
            if (!dispatch(new Runnable() { // from class: org.eclipse.jetty.io.nio.SelectorManager.1
                @Override // java.lang.Runnable
                public void run() {
                    String name = Thread.currentThread().getName();
                    int priority = Thread.currentThread().getPriority();
                    try {
                        SelectSet[] selectSetArr2 = SelectorManager.this._selectSet;
                        if (selectSetArr2 == null) {
                            SelectorManager.LOG.debug("Stopped {} on {}", Thread.currentThread(), this);
                            Thread.currentThread().setName(name);
                            if (SelectorManager.this.getSelectorPriorityDelta() != 0) {
                                Thread.currentThread().setPriority(priority);
                                return;
                            }
                            return;
                        }
                        SelectSet selectSet = selectSetArr2[i3];
                        Thread.currentThread().setName(name + " Selector" + i3);
                        if (SelectorManager.this.getSelectorPriorityDelta() != 0) {
                            Thread.currentThread().setPriority(Thread.currentThread().getPriority() + SelectorManager.this.getSelectorPriorityDelta());
                        }
                        SelectorManager.LOG.debug("Starting {} on {}", Thread.currentThread(), this);
                        while (SelectorManager.this.isRunning()) {
                            try {
                                selectSet.doSelect();
                            } catch (IOException e2) {
                                SelectorManager.LOG.ignore(e2);
                            } catch (Exception e3) {
                                SelectorManager.LOG.warn(e3);
                            }
                        }
                        SelectorManager.LOG.debug("Stopped {} on {}", Thread.currentThread(), this);
                        Thread.currentThread().setName(name);
                        if (SelectorManager.this.getSelectorPriorityDelta() != 0) {
                            Thread.currentThread().setPriority(priority);
                        }
                    } catch (Throwable th) {
                        SelectorManager.LOG.debug("Stopped {} on {}", Thread.currentThread(), this);
                        Thread.currentThread().setName(name);
                        if (SelectorManager.this.getSelectorPriorityDelta() != 0) {
                            Thread.currentThread().setPriority(priority);
                        }
                        throw th;
                    }
                }
            })) {
                throw new IllegalStateException("!Selecting");
            }
        }
    }

    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        SelectSet[] selectSetArr = this._selectSet;
        this._selectSet = null;
        if (selectSetArr != null) {
            for (SelectSet selectSet : selectSetArr) {
                if (selectSet != null) {
                    selectSet.stop();
                }
            }
        }
        super.doStop();
    }

    @Override // org.eclipse.jetty.util.component.Dumpable
    public String dump() {
        return AggregateLifeCycle.dump(this);
    }

    public abstract void endPointClosed(SelectChannelEndPoint selectChannelEndPoint);

    public abstract void endPointOpened(SelectChannelEndPoint selectChannelEndPoint);

    public abstract void endPointUpgraded(ConnectedEndPoint connectedEndPoint, Connection connection);

    public long getLowResourcesConnections() {
        return this._lowResourcesConnections * this._selectSets;
    }

    public long getLowResourcesMaxIdleTime() {
        return this._lowResourcesMaxIdleTime;
    }

    public long getMaxIdleTime() {
        return this._maxIdleTime;
    }

    public SelectSet getSelectSet(int i2) {
        return this._selectSet[i2];
    }

    public int getSelectSets() {
        return this._selectSets;
    }

    public int getSelectorPriorityDelta() {
        return this._selectorPriorityDelta;
    }

    public boolean isDeferringInterestedOps0() {
        return this._deferringInterestedOps0;
    }

    public abstract AsyncConnection newConnection(SocketChannel socketChannel, AsyncEndPoint asyncEndPoint, Object obj);

    public abstract SelectChannelEndPoint newEndPoint(SocketChannel socketChannel, SelectSet selectSet, SelectionKey selectionKey) throws IOException;

    public void register(SocketChannel socketChannel, Object obj) {
        int i2 = this._set;
        this._set = i2 + 1;
        if (i2 < 0) {
            i2 = -i2;
        }
        int i3 = i2 % this._selectSets;
        SelectSet[] selectSetArr = this._selectSet;
        if (selectSetArr != null) {
            SelectSet selectSet = selectSetArr[i3];
            selectSet.addChange(socketChannel, obj);
            selectSet.wakeup();
        }
    }

    public void setDeferringInterestedOps0(boolean z2) {
        this._deferringInterestedOps0 = z2;
    }

    public void setLowResourcesConnections(long j2) {
        int i2 = this._selectSets;
        this._lowResourcesConnections = ((j2 + i2) - 1) / i2;
    }

    public void setLowResourcesMaxIdleTime(long j2) {
        this._lowResourcesMaxIdleTime = (int) j2;
    }

    public void setMaxIdleTime(long j2) {
        this._maxIdleTime = (int) j2;
    }

    public void setSelectSets(int i2) {
        long j2 = this._lowResourcesConnections * this._selectSets;
        this._selectSets = i2;
        this._lowResourcesConnections = j2 / i2;
    }

    public void setSelectorPriorityDelta(int i2) {
        this._selectorPriorityDelta = i2;
    }

    @Override // org.eclipse.jetty.util.component.Dumpable
    public void dump(Appendable appendable, String str) throws IOException {
        AggregateLifeCycle.dumpObject(appendable, this);
        AggregateLifeCycle.dump(appendable, str, TypeUtil.asList(this._selectSet));
    }

    public void register(SocketChannel socketChannel) {
        int i2 = this._set;
        this._set = i2 + 1;
        if (i2 < 0) {
            i2 = -i2;
        }
        int i3 = i2 % this._selectSets;
        SelectSet[] selectSetArr = this._selectSet;
        if (selectSetArr != null) {
            SelectSet selectSet = selectSetArr[i3];
            selectSet.addChange(socketChannel);
            selectSet.wakeup();
        }
    }

    public void register(ServerSocketChannel serverSocketChannel) {
        int i2 = this._set;
        this._set = i2 + 1;
        if (i2 < 0) {
            i2 = -i2;
        }
        SelectSet selectSet = this._selectSet[i2 % this._selectSets];
        selectSet.addChange(serverSocketChannel);
        selectSet.wakeup();
    }
}
