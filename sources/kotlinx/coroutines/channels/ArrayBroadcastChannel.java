package kotlinx.coroutines.channels;

import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.internal.ConcurrentKt;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000L2\b\u0012\u0004\u0012\u00028\u00000M:\u0001JB\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\u0019\u0010\t\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0017¢\u0006\u0004\b\t\u0010\nJ\u001f\u0010\t\u001a\u00020\r2\u000e\u0010\u0007\u001a\n\u0018\u00010\u000bj\u0004\u0018\u0001`\fH\u0016¢\u0006\u0004\b\t\u0010\u000eJ\u0019\u0010\u000f\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\b\u000f\u0010\nJ\u000f\u0010\u0010\u001a\u00020\rH\u0002¢\u0006\u0004\b\u0010\u0010\u0011J\u0019\u0010\u0012\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0016¢\u0006\u0004\b\u0012\u0010\nJ\u000f\u0010\u0014\u001a\u00020\u0013H\u0002¢\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0017\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00020\u0013H\u0002¢\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u0019\u001a\u00028\u0000H\u0014¢\u0006\u0004\b\u001b\u0010\u001cJ#\u0010\u001f\u001a\u00020\u001a2\u0006\u0010\u0019\u001a\u00028\u00002\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0014¢\u0006\u0004\b\u001f\u0010 J\u0015\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000!H\u0016¢\u0006\u0004\b\"\u0010#J4\u0010'\u001a\u00020\r2\u0010\b\u0002\u0010%\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010$2\u0010\b\u0002\u0010&\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010$H\u0082\u0010¢\u0006\u0004\b'\u0010(R\u001c\u0010*\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001a0)8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b*\u0010+R\u0014\u0010/\u001a\u00020,8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b-\u0010.R\u0018\u00102\u001a\u000600j\u0002`18\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b2\u00103R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u00104\u001a\u0004\b5\u00106R$\u0010;\u001a\u00020\u00132\u0006\u00107\u001a\u00020\u00138B@BX\u0082\u000e¢\u0006\f\u001a\u0004\b8\u0010\u0015\"\u0004\b9\u0010:R\u0014\u0010<\u001a\u00020\b8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R\u0014\u0010>\u001a\u00020\b8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b>\u0010=R$\u0010A\u001a\u00020\u00022\u0006\u00107\u001a\u00020\u00028B@BX\u0082\u000e¢\u0006\f\u001a\u0004\b?\u00106\"\u0004\b@\u0010\u0005R6\u0010D\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000$0Bj\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000$`C8\u0002X\u0082\u0004¢\u0006\f\n\u0004\bD\u0010E\u0012\u0004\bF\u0010\u0011R$\u0010I\u001a\u00020\u00132\u0006\u00107\u001a\u00020\u00138B@BX\u0082\u000e¢\u0006\f\u001a\u0004\bG\u0010\u0015\"\u0004\bH\u0010:¨\u0006K"}, d2 = {"Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", "E", "", "capacity", "<init>", "(I)V", "", "cause", "", "cancel", "(Ljava/lang/Throwable;)Z", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "", "(Ljava/util/concurrent/CancellationException;)V", "cancelInternal", "checkSubOffers", "()V", "close", "", "computeMinHead", "()J", "index", "elementAt", "(J)Ljava/lang/Object;", "element", "", "offerInternal", "(Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "offerSelectInternal", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "openSubscription", "()Lkotlinx/coroutines/channels/ReceiveChannel;", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;", "addSub", "removeSub", "updateHead", "(Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;)V", "", "buffer", "[Ljava/lang/Object;", "", "getBufferDebugString", "()Ljava/lang/String;", "bufferDebugString", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "bufferLock", "Ljava/util/concurrent/locks/ReentrantLock;", "I", "getCapacity", "()I", "value", "getHead", "setHead", "(J)V", TtmlNode.TAG_HEAD, "isBufferAlwaysFull", "()Z", "isBufferFull", "getSize", "setSize", DatabaseManager.SIZE, "", "Lkotlinx/coroutines/internal/SubscribersList;", "subscribers", "Ljava/util/List;", "getSubscribers$annotations", "getTail", "setTail", "tail", "Subscriber", "kotlinx-coroutines-core", "Lkotlinx/coroutines/channels/AbstractSendChannel;", "Lkotlinx/coroutines/channels/BroadcastChannel;"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class ArrayBroadcastChannel<E> extends AbstractSendChannel<E> implements BroadcastChannel<E> {

    @NotNull
    private volatile /* synthetic */ long _head;

    @NotNull
    private volatile /* synthetic */ int _size;

    @NotNull
    private volatile /* synthetic */ long _tail;

    @NotNull
    private final Object[] buffer;

    @NotNull
    private final ReentrantLock bufferLock;
    private final int capacity;

    @NotNull
    private final List<Subscriber<E>> subscribers;

    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u00028\u00010'2\b\u0012\u0004\u0012\u00028\u00010(B\u0015\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\r\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0019\u0010\u000b\u001a\u00020\u00062\b\u0010\n\u001a\u0004\u0018\u00010\tH\u0016¢\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\r\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\r\u0010\bJ\u0011\u0010\u000f\u001a\u0004\u0018\u00010\u000eH\u0002¢\u0006\u0004\b\u000f\u0010\u0010J\u0011\u0010\u0011\u001a\u0004\u0018\u00010\u000eH\u0014¢\u0006\u0004\b\u0011\u0010\u0010J\u001d\u0010\u0014\u001a\u0004\u0018\u00010\u000e2\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u0012H\u0014¢\u0006\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00068TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\bR\u0014\u0010\u0018\u001a\u00020\u00068TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\bR\u0014\u0010\u0019\u001a\u00020\u00068TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\bR\u0014\u0010\u001a\u001a\u00020\u00068TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\bR$\u0010!\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u0018\u0010$\u001a\u00060\"j\u0002`#8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b$\u0010%¨\u0006&"}, d2 = {"Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;", "E", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", "broadcastChannel", "<init>", "(Lkotlinx/coroutines/channels/ArrayBroadcastChannel;)V", "", "checkOffer", "()Z", "", "cause", "close", "(Ljava/lang/Throwable;)Z", "needsToCheckOfferWithoutLock", "", "peekUnderLock", "()Ljava/lang/Object;", "pollInternal", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "pollSelectInternal", "(Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", "isBufferAlwaysEmpty", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "", "value", "getSubHead", "()J", "setSubHead", "(J)V", "subHead", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "subLock", "Ljava/util/concurrent/locks/ReentrantLock;", "kotlinx-coroutines-core", "Lkotlinx/coroutines/channels/AbstractChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Subscriber<E> extends AbstractChannel<E> implements ReceiveChannel<E> {

        @NotNull
        private volatile /* synthetic */ long _subHead;

        @NotNull
        private final ArrayBroadcastChannel<E> broadcastChannel;

        @NotNull
        private final ReentrantLock subLock;

        public Subscriber(@NotNull ArrayBroadcastChannel<E> arrayBroadcastChannel) {
            super(null);
            this.broadcastChannel = arrayBroadcastChannel;
            this.subLock = new ReentrantLock();
            this._subHead = 0L;
        }

        private final boolean needsToCheckOfferWithoutLock() {
            if (getClosedForReceive() != null) {
                return false;
            }
            return (isBufferEmpty() && this.broadcastChannel.getClosedForReceive() == null) ? false : true;
        }

        private final Object peekUnderLock() {
            long j2 = get_subHead();
            Closed<?> closedForReceive = this.broadcastChannel.getClosedForReceive();
            if (j2 < this.broadcastChannel.get_tail()) {
                Object objElementAt = this.broadcastChannel.elementAt(j2);
                Closed<?> closedForReceive2 = getClosedForReceive();
                return closedForReceive2 != null ? closedForReceive2 : objElementAt;
            }
            if (closedForReceive != null) {
                return closedForReceive;
            }
            Closed<?> closedForReceive3 = getClosedForReceive();
            return closedForReceive3 == null ? AbstractChannelKt.POLL_FAILED : closedForReceive3;
        }

        /* JADX WARN: Code restructure failed: missing block: B:12:0x0022, code lost:
        
            r2 = (kotlinx.coroutines.channels.Closed) r1;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean checkOffer() {
            /*
                r8 = this;
                r0 = 0
            L1:
                boolean r1 = r8.needsToCheckOfferWithoutLock()
                r2 = 0
                if (r1 == 0) goto L59
                java.util.concurrent.locks.ReentrantLock r1 = r8.subLock
                boolean r1 = r1.tryLock()
                if (r1 == 0) goto L59
                java.lang.Object r1 = r8.peekUnderLock()     // Catch: java.lang.Throwable -> L52
                kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.channels.AbstractChannelKt.POLL_FAILED     // Catch: java.lang.Throwable -> L52
                if (r1 != r3) goto L1e
            L18:
                java.util.concurrent.locks.ReentrantLock r1 = r8.subLock
                r1.unlock()
                goto L1
            L1e:
                boolean r3 = r1 instanceof kotlinx.coroutines.channels.Closed     // Catch: java.lang.Throwable -> L52
                if (r3 == 0) goto L2b
                r2 = r1
                kotlinx.coroutines.channels.Closed r2 = (kotlinx.coroutines.channels.Closed) r2     // Catch: java.lang.Throwable -> L52
            L25:
                java.util.concurrent.locks.ReentrantLock r1 = r8.subLock
                r1.unlock()
                goto L59
            L2b:
                kotlinx.coroutines.channels.ReceiveOrClosed r3 = r8.takeFirstReceiveOrPeekClosed()     // Catch: java.lang.Throwable -> L52
                if (r3 != 0) goto L32
                goto L25
            L32:
                boolean r4 = r3 instanceof kotlinx.coroutines.channels.Closed     // Catch: java.lang.Throwable -> L52
                if (r4 == 0) goto L37
                goto L25
            L37:
                kotlinx.coroutines.internal.Symbol r2 = r3.tryResumeReceive(r1, r2)     // Catch: java.lang.Throwable -> L52
                if (r2 != 0) goto L3e
                goto L18
            L3e:
                long r4 = r8.get_subHead()     // Catch: java.lang.Throwable -> L52
                r6 = 1
                long r4 = r4 + r6
                r8.setSubHead(r4)     // Catch: java.lang.Throwable -> L52
                java.util.concurrent.locks.ReentrantLock r0 = r8.subLock
                r0.unlock()
                r3.completeResumeReceive(r1)
                r0 = 1
                goto L1
            L52:
                r0 = move-exception
                java.util.concurrent.locks.ReentrantLock r1 = r8.subLock
                r1.unlock()
                throw r0
            L59:
                if (r2 == 0) goto L60
                java.lang.Throwable r1 = r2.closeCause
                r8.close(r1)
            L60:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ArrayBroadcastChannel.Subscriber.checkOffer():boolean");
        }

        @Override // kotlinx.coroutines.channels.AbstractSendChannel, kotlinx.coroutines.channels.SendChannel
        public boolean close(@Nullable Throwable cause) {
            boolean zClose = super.close(cause);
            if (zClose) {
                ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, null, this, 1, null);
                ReentrantLock reentrantLock = this.subLock;
                reentrantLock.lock();
                try {
                    setSubHead(this.broadcastChannel.get_tail());
                    Unit unit = Unit.INSTANCE;
                } finally {
                    reentrantLock.unlock();
                }
            }
            return zClose;
        }

        /* renamed from: getSubHead, reason: from getter */
        public final long get_subHead() {
            return this._subHead;
        }

        @Override // kotlinx.coroutines.channels.AbstractChannel
        public boolean isBufferAlwaysEmpty() {
            return false;
        }

        @Override // kotlinx.coroutines.channels.AbstractSendChannel
        public boolean isBufferAlwaysFull() {
            throw new IllegalStateException("Should not be used".toString());
        }

        @Override // kotlinx.coroutines.channels.AbstractChannel
        public boolean isBufferEmpty() {
            return get_subHead() >= this.broadcastChannel.get_tail();
        }

        @Override // kotlinx.coroutines.channels.AbstractSendChannel
        public boolean isBufferFull() {
            throw new IllegalStateException("Should not be used".toString());
        }

        @Override // kotlinx.coroutines.channels.AbstractChannel
        @Nullable
        public Object pollInternal() {
            boolean z2;
            ReentrantLock reentrantLock = this.subLock;
            reentrantLock.lock();
            try {
                Object objPeekUnderLock = peekUnderLock();
                if ((objPeekUnderLock instanceof Closed) || objPeekUnderLock == AbstractChannelKt.POLL_FAILED) {
                    z2 = false;
                } else {
                    setSubHead(get_subHead() + 1);
                    z2 = true;
                }
                reentrantLock.unlock();
                Closed closed = objPeekUnderLock instanceof Closed ? (Closed) objPeekUnderLock : null;
                if (closed != null) {
                    close(closed.closeCause);
                }
                if (checkOffer() ? true : z2) {
                    ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, null, null, 3, null);
                }
                return objPeekUnderLock;
            } catch (Throwable th) {
                reentrantLock.unlock();
                throw th;
            }
        }

        @Override // kotlinx.coroutines.channels.AbstractChannel
        @Nullable
        public Object pollSelectInternal(@NotNull SelectInstance<?> select) {
            ReentrantLock reentrantLock = this.subLock;
            reentrantLock.lock();
            try {
                Object objPeekUnderLock = peekUnderLock();
                boolean z2 = false;
                if (!(objPeekUnderLock instanceof Closed) && objPeekUnderLock != AbstractChannelKt.POLL_FAILED) {
                    if (select.trySelect()) {
                        setSubHead(get_subHead() + 1);
                        z2 = true;
                    } else {
                        objPeekUnderLock = SelectKt.getALREADY_SELECTED();
                    }
                }
                reentrantLock.unlock();
                Closed closed = objPeekUnderLock instanceof Closed ? (Closed) objPeekUnderLock : null;
                if (closed != null) {
                    close(closed.closeCause);
                }
                if (checkOffer() ? true : z2) {
                    ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, null, null, 3, null);
                }
                return objPeekUnderLock;
            } catch (Throwable th) {
                reentrantLock.unlock();
                throw th;
            }
        }

        public final void setSubHead(long j2) {
            this._subHead = j2;
        }
    }

    public ArrayBroadcastChannel(int i2) {
        super(null);
        this.capacity = i2;
        if (!(i2 >= 1)) {
            throw new IllegalArgumentException(("ArrayBroadcastChannel capacity must be at least 1, but " + i2 + " was specified").toString());
        }
        this.bufferLock = new ReentrantLock();
        this.buffer = new Object[i2];
        this._head = 0L;
        this._tail = 0L;
        this._size = 0;
        this.subscribers = ConcurrentKt.subscriberList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // kotlinx.coroutines.channels.BroadcastChannel
    /* renamed from: cancelInternal, reason: merged with bridge method [inline-methods] */
    public final boolean cancel(Throwable cause) {
        boolean zClose = close(cause);
        Iterator<Subscriber<E>> it = this.subscribers.iterator();
        while (it.hasNext()) {
            it.next().cancel(cause);
        }
        return zClose;
    }

    private final void checkSubOffers() {
        boolean z2;
        Iterator<Subscriber<E>> it = this.subscribers.iterator();
        boolean z3 = false;
        loop0: while (true) {
            z2 = z3;
            while (it.hasNext()) {
                if (it.next().checkOffer()) {
                    break;
                } else {
                    z2 = true;
                }
            }
            z3 = true;
        }
        if (z3 || !z2) {
            updateHead$default(this, null, null, 3, null);
        }
    }

    private final long computeMinHead() {
        Iterator<Subscriber<E>> it = this.subscribers.iterator();
        long jCoerceAtMost = Long.MAX_VALUE;
        while (it.hasNext()) {
            jCoerceAtMost = RangesKt___RangesKt.coerceAtMost(jCoerceAtMost, it.next().get_subHead());
        }
        return jCoerceAtMost;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final E elementAt(long index) {
        return (E) this.buffer[(int) (index % this.capacity)];
    }

    /* renamed from: getHead, reason: from getter */
    private final long get_head() {
        return this._head;
    }

    /* renamed from: getSize, reason: from getter */
    private final int get_size() {
        return this._size;
    }

    private static /* synthetic */ void getSubscribers$annotations() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getTail, reason: from getter */
    public final long get_tail() {
        return this._tail;
    }

    private final void setHead(long j2) {
        this._head = j2;
    }

    private final void setSize(int i2) {
        this._size = i2;
    }

    private final void setTail(long j2) {
        this._tail = j2;
    }

    private final void updateHead(Subscriber<E> addSub, Subscriber<E> removeSub) {
        Send sendTakeFirstSendOrPeekClosed;
        while (true) {
            ReentrantLock reentrantLock = this.bufferLock;
            reentrantLock.lock();
            if (addSub != null) {
                try {
                    addSub.setSubHead(get_tail());
                    boolean zIsEmpty = this.subscribers.isEmpty();
                    this.subscribers.add(addSub);
                    if (!zIsEmpty) {
                        return;
                    }
                } finally {
                    reentrantLock.unlock();
                }
            }
            if (removeSub != null) {
                this.subscribers.remove(removeSub);
                if (get_head() != removeSub.get_subHead()) {
                    return;
                }
            }
            long jComputeMinHead = computeMinHead();
            long j2 = get_tail();
            long j3 = get_head();
            long jCoerceAtMost = RangesKt___RangesKt.coerceAtMost(jComputeMinHead, j2);
            if (jCoerceAtMost <= j3) {
                return;
            }
            int i2 = get_size();
            while (j3 < jCoerceAtMost) {
                Object[] objArr = this.buffer;
                int i3 = this.capacity;
                objArr[(int) (j3 % i3)] = null;
                boolean z2 = i2 >= i3;
                j3++;
                setHead(j3);
                i2--;
                setSize(i2);
                if (z2) {
                    do {
                        sendTakeFirstSendOrPeekClosed = takeFirstSendOrPeekClosed();
                        if (sendTakeFirstSendOrPeekClosed != null && !(sendTakeFirstSendOrPeekClosed instanceof Closed)) {
                            Intrinsics.checkNotNull(sendTakeFirstSendOrPeekClosed);
                        }
                    } while (sendTakeFirstSendOrPeekClosed.tryResumeSend(null) == null);
                    this.buffer[(int) (j2 % this.capacity)] = sendTakeFirstSendOrPeekClosed.getElement();
                    setSize(i2 + 1);
                    setTail(j2 + 1);
                    Unit unit = Unit.INSTANCE;
                    reentrantLock.unlock();
                    sendTakeFirstSendOrPeekClosed.completeResumeSend();
                    checkSubOffers();
                    addSub = null;
                    removeSub = null;
                }
            }
            return;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void updateHead$default(ArrayBroadcastChannel arrayBroadcastChannel, Subscriber subscriber, Subscriber subscriber2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            subscriber = null;
        }
        if ((i2 & 2) != 0) {
            subscriber2 = null;
        }
        arrayBroadcastChannel.updateHead(subscriber, subscriber2);
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel, kotlinx.coroutines.channels.SendChannel
    public boolean close(@Nullable Throwable cause) {
        if (!super.close(cause)) {
            return false;
        }
        checkSubOffers();
        return true;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @NotNull
    public String getBufferDebugString() {
        return "(buffer:capacity=" + this.buffer.length + ",size=" + get_size() + ')';
    }

    public final int getCapacity() {
        return this.capacity;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public boolean isBufferAlwaysFull() {
        return false;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public boolean isBufferFull() {
        return get_size() >= this.capacity;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @NotNull
    public Object offerInternal(E element) {
        ReentrantLock reentrantLock = this.bufferLock;
        reentrantLock.lock();
        try {
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend != null) {
                return closedForSend;
            }
            int i2 = get_size();
            if (i2 >= this.capacity) {
                return AbstractChannelKt.OFFER_FAILED;
            }
            long j2 = get_tail();
            this.buffer[(int) (j2 % this.capacity)] = element;
            setSize(i2 + 1);
            setTail(j2 + 1);
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            checkSubOffers();
            return AbstractChannelKt.OFFER_SUCCESS;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @NotNull
    public Object offerSelectInternal(E element, @NotNull SelectInstance<?> select) {
        ReentrantLock reentrantLock = this.bufferLock;
        reentrantLock.lock();
        try {
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend != null) {
                return closedForSend;
            }
            int i2 = get_size();
            if (i2 >= this.capacity) {
                return AbstractChannelKt.OFFER_FAILED;
            }
            if (!select.trySelect()) {
                return SelectKt.getALREADY_SELECTED();
            }
            long j2 = get_tail();
            this.buffer[(int) (j2 % this.capacity)] = element;
            setSize(i2 + 1);
            setTail(j2 + 1);
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            checkSubOffers();
            return AbstractChannelKt.OFFER_SUCCESS;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // kotlinx.coroutines.channels.BroadcastChannel
    @NotNull
    public ReceiveChannel<E> openSubscription() {
        Subscriber subscriber = new Subscriber(this);
        updateHead$default(this, subscriber, null, 2, null);
        return subscriber;
    }

    @Override // kotlinx.coroutines.channels.BroadcastChannel
    public void cancel(@Nullable CancellationException cause) {
        cancel(cause);
    }
}
