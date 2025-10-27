package okhttp3.internal.connection;

import java.io.IOException;
import java.lang.ref.Reference;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Address;
import okhttp3.ConnectionPool;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.concurrent.Task;
import okhttp3.internal.concurrent.TaskQueue;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.platform.Platform;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0005*\u0001\u000e\u0018\u0000 (2\u00020\u0001:\u0001(B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ.\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u000e\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u001b2\u0006\u0010\u001d\u001a\u00020\u0015J\u000e\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001f\u001a\u00020\u0007J\u000e\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u0012J\u0006\u0010\"\u001a\u00020\u0005J\u0006\u0010#\u001a\u00020$J\u0006\u0010%\u001a\u00020\u0005J\u0018\u0010&\u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u0007H\u0002J\u000e\u0010'\u001a\u00020$2\u0006\u0010!\u001a\u00020\u0012R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000fR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lokhttp3/internal/connection/RealConnectionPool;", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "maxIdleConnections", "", "keepAliveDuration", "", "timeUnit", "Ljava/util/concurrent/TimeUnit;", "(Lokhttp3/internal/concurrent/TaskRunner;IJLjava/util/concurrent/TimeUnit;)V", "cleanupQueue", "Lokhttp3/internal/concurrent/TaskQueue;", "cleanupTask", "okhttp3/internal/connection/RealConnectionPool$cleanupTask$1", "Lokhttp3/internal/connection/RealConnectionPool$cleanupTask$1;", "connections", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "Lokhttp3/internal/connection/RealConnection;", "keepAliveDurationNs", "callAcquirePooledConnection", "", "address", "Lokhttp3/Address;", "call", "Lokhttp3/internal/connection/RealCall;", "routes", "", "Lokhttp3/Route;", "requireMultiplexed", "cleanup", "now", "connectionBecameIdle", "connection", "connectionCount", "evictAll", "", "idleConnectionCount", "pruneAndGetAllocationCount", "put", "Companion", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class RealConnectionPool {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final TaskQueue cleanupQueue;

    @NotNull
    private final RealConnectionPool$cleanupTask$1 cleanupTask;

    @NotNull
    private final ConcurrentLinkedQueue<RealConnection> connections;
    private final long keepAliveDurationNs;
    private final int maxIdleConnections;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lokhttp3/internal/connection/RealConnectionPool$Companion;", "", "()V", "get", "Lokhttp3/internal/connection/RealConnectionPool;", "connectionPool", "Lokhttp3/ConnectionPool;", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final RealConnectionPool get(@NotNull ConnectionPool connectionPool) {
            Intrinsics.checkNotNullParameter(connectionPool, "connectionPool");
            return connectionPool.getDelegate();
        }
    }

    /* JADX WARN: Type inference failed for: r4v2, types: [okhttp3.internal.connection.RealConnectionPool$cleanupTask$1] */
    public RealConnectionPool(@NotNull TaskRunner taskRunner, int i2, long j2, @NotNull TimeUnit timeUnit) {
        Intrinsics.checkNotNullParameter(taskRunner, "taskRunner");
        Intrinsics.checkNotNullParameter(timeUnit, "timeUnit");
        this.maxIdleConnections = i2;
        this.keepAliveDurationNs = timeUnit.toNanos(j2);
        this.cleanupQueue = taskRunner.newQueue();
        final String strStringPlus = Intrinsics.stringPlus(Util.okHttpName, " ConnectionPool");
        this.cleanupTask = new Task(strStringPlus) { // from class: okhttp3.internal.connection.RealConnectionPool$cleanupTask$1
            @Override // okhttp3.internal.concurrent.Task
            public long runOnce() {
                return this.this$0.cleanup(System.nanoTime());
            }
        };
        this.connections = new ConcurrentLinkedQueue<>();
        if (!(j2 > 0)) {
            throw new IllegalArgumentException(Intrinsics.stringPlus("keepAliveDuration <= 0: ", Long.valueOf(j2)).toString());
        }
    }

    private final int pruneAndGetAllocationCount(RealConnection connection, long now) {
        if (Util.assertionsEnabled && !Thread.holdsLock(connection)) {
            throw new AssertionError("Thread " + ((Object) Thread.currentThread().getName()) + " MUST hold lock on " + connection);
        }
        List<Reference<RealCall>> calls = connection.getCalls();
        int i2 = 0;
        while (i2 < calls.size()) {
            Reference<RealCall> reference = calls.get(i2);
            if (reference.get() != null) {
                i2++;
            } else {
                Platform.INSTANCE.get().logCloseableLeak("A connection to " + connection.getRoute().address().url() + " was leaked. Did you forget to close a response body?", ((RealCall.CallReference) reference).getCallStackTrace());
                calls.remove(i2);
                connection.setNoNewExchanges(true);
                if (calls.isEmpty()) {
                    connection.setIdleAtNs$okhttp(now - this.keepAliveDurationNs);
                    return 0;
                }
            }
        }
        return calls.size();
    }

    public final boolean callAcquirePooledConnection(@NotNull Address address, @NotNull RealCall call, @Nullable List<Route> routes, boolean requireMultiplexed) {
        Intrinsics.checkNotNullParameter(address, "address");
        Intrinsics.checkNotNullParameter(call, "call");
        Iterator<RealConnection> it = this.connections.iterator();
        while (it.hasNext()) {
            RealConnection connection = it.next();
            Intrinsics.checkNotNullExpressionValue(connection, "connection");
            synchronized (connection) {
                if (requireMultiplexed) {
                    if (!connection.isMultiplexed$okhttp()) {
                    }
                    Unit unit = Unit.INSTANCE;
                }
                if (connection.isEligible$okhttp(address, routes)) {
                    call.acquireConnectionNoEvents(connection);
                    return true;
                }
                Unit unit2 = Unit.INSTANCE;
            }
        }
        return false;
    }

    public final long cleanup(long now) throws IOException {
        Iterator<RealConnection> it = this.connections.iterator();
        int i2 = 0;
        long j2 = Long.MIN_VALUE;
        RealConnection realConnection = null;
        int i3 = 0;
        while (it.hasNext()) {
            RealConnection connection = it.next();
            Intrinsics.checkNotNullExpressionValue(connection, "connection");
            synchronized (connection) {
                if (pruneAndGetAllocationCount(connection, now) > 0) {
                    i3++;
                } else {
                    i2++;
                    long idleAtNs = now - connection.getIdleAtNs();
                    if (idleAtNs > j2) {
                        realConnection = connection;
                        j2 = idleAtNs;
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
        }
        long j3 = this.keepAliveDurationNs;
        if (j2 < j3 && i2 <= this.maxIdleConnections) {
            if (i2 > 0) {
                return j3 - j2;
            }
            if (i3 > 0) {
                return j3;
            }
            return -1L;
        }
        Intrinsics.checkNotNull(realConnection);
        synchronized (realConnection) {
            if (!realConnection.getCalls().isEmpty()) {
                return 0L;
            }
            if (realConnection.getIdleAtNs() + j2 != now) {
                return 0L;
            }
            realConnection.setNoNewExchanges(true);
            this.connections.remove(realConnection);
            Util.closeQuietly(realConnection.socket());
            if (this.connections.isEmpty()) {
                this.cleanupQueue.cancelAll();
            }
            return 0L;
        }
    }

    public final boolean connectionBecameIdle(@NotNull RealConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        if (Util.assertionsEnabled && !Thread.holdsLock(connection)) {
            throw new AssertionError("Thread " + ((Object) Thread.currentThread().getName()) + " MUST hold lock on " + connection);
        }
        if (!connection.getNoNewExchanges() && this.maxIdleConnections != 0) {
            TaskQueue.schedule$default(this.cleanupQueue, this.cleanupTask, 0L, 2, null);
            return false;
        }
        connection.setNoNewExchanges(true);
        this.connections.remove(connection);
        if (this.connections.isEmpty()) {
            this.cleanupQueue.cancelAll();
        }
        return true;
    }

    public final int connectionCount() {
        return this.connections.size();
    }

    public final void evictAll() {
        Socket socket;
        Iterator<RealConnection> it = this.connections.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "connections.iterator()");
        while (it.hasNext()) {
            RealConnection connection = it.next();
            Intrinsics.checkNotNullExpressionValue(connection, "connection");
            synchronized (connection) {
                if (connection.getCalls().isEmpty()) {
                    it.remove();
                    connection.setNoNewExchanges(true);
                    socket = connection.socket();
                } else {
                    socket = null;
                }
            }
            if (socket != null) {
                Util.closeQuietly(socket);
            }
        }
        if (this.connections.isEmpty()) {
            this.cleanupQueue.cancelAll();
        }
    }

    public final int idleConnectionCount() {
        boolean zIsEmpty;
        ConcurrentLinkedQueue<RealConnection> concurrentLinkedQueue = this.connections;
        int i2 = 0;
        if (!(concurrentLinkedQueue instanceof Collection) || !concurrentLinkedQueue.isEmpty()) {
            for (RealConnection it : concurrentLinkedQueue) {
                Intrinsics.checkNotNullExpressionValue(it, "it");
                synchronized (it) {
                    zIsEmpty = it.getCalls().isEmpty();
                }
                if (zIsEmpty && (i2 = i2 + 1) < 0) {
                    CollectionsKt__CollectionsKt.throwCountOverflow();
                }
            }
        }
        return i2;
    }

    public final void put(@NotNull RealConnection connection) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        if (!Util.assertionsEnabled || Thread.holdsLock(connection)) {
            this.connections.add(connection);
            TaskQueue.schedule$default(this.cleanupQueue, this.cleanupTask, 0L, 2, null);
            return;
        }
        throw new AssertionError("Thread " + ((Object) Thread.currentThread().getName()) + " MUST hold lock on " + connection);
    }
}
