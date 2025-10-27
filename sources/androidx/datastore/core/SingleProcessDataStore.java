package androidx.datastore.core;

import androidx.datastore.core.handlers.NoOpCorruptionHandler;
import androidx.exifinterface.media.ExifInterface;
import c.i;
import com.tencent.connect.common.Constants;
import com.umeng.socialize.tracker.a;
import com.yikaobang.yixue.R2;
import io.socket.engineio.client.Socket;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.channels.ActorKt;
import kotlinx.coroutines.channels.ConflatedBroadcastChannel;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0003\n\u0002\b\u000f\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0002?@B\u007f\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012?\b\u0002\u0010\b\u001a9\u00125\u00123\b\u0001\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00110\n0\t\u0012\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u0013\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0015ø\u0001\u0000¢\u0006\u0002\u0010\u0016J\u0015\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000#0\"H\u0082\bJ%\u0010*\u001a\u00020\u00102\u0012\u0010+\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000#0\"H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010,J\u0011\u0010-\u001a\u00028\u0000H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010.J\u0011\u0010/\u001a\u00028\u0000H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010.J\u0010\u00100\u001a\u00020\u00102\u0006\u00101\u001a\u000202H\u0002JX\u00103\u001a\u00028\u000021\u00104\u001a-\b\u0001\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(5\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00110\n2\u0012\u00106\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000#0\"H\u0082@ø\u0001\u0000¢\u0006\u0002\u00107JD\u00108\u001a\u00028\u000021\u00104\u001a-\b\u0001\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(5\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00110\nH\u0096@ø\u0001\u0000¢\u0006\u0002\u00109J\u0017\u0010:\u001a\u00020\u00102\u0006\u0010;\u001a\u00028\u0000H\u0000¢\u0006\u0004\b<\u0010=J\f\u0010>\u001a\u00020\u0010*\u00020\u0005H\u0002R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082D¢\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u001b0\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u001dX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR \u0010 \u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000#0\"0!X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010$\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b'\u0010(\u001a\u0004\b%\u0010&RJ\u0010)\u001a;\u00125\u00123\b\u0001\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00110\n\u0018\u00010\tX\u0082\u000eø\u0001\u0000¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006A"}, d2 = {"Landroidx/datastore/core/SingleProcessDataStore;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/datastore/core/DataStore;", "produceFile", "Lkotlin/Function0;", "Ljava/io/File;", "serializer", "Landroidx/datastore/core/Serializer;", "initTasksList", "", "Lkotlin/Function2;", "Landroidx/datastore/core/InitializerApi;", "Lkotlin/ParameterName;", "name", "api", "Lkotlin/coroutines/Continuation;", "", "", "corruptionHandler", "Landroidx/datastore/core/CorruptionHandler;", Constants.PARAM_SCOPE, "Lkotlinx/coroutines/CoroutineScope;", "(Lkotlin/jvm/functions/Function0;Landroidx/datastore/core/Serializer;Ljava/util/List;Landroidx/datastore/core/CorruptionHandler;Lkotlinx/coroutines/CoroutineScope;)V", "SCRATCH_SUFFIX", "", "actor", "Lkotlinx/coroutines/channels/SendChannel;", "Landroidx/datastore/core/SingleProcessDataStore$Message;", "data", "Lkotlinx/coroutines/flow/Flow;", "getData", "()Lkotlinx/coroutines/flow/Flow;", "downstreamChannel", "Ljava/util/concurrent/atomic/AtomicReference;", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;", "Landroidx/datastore/core/DataAndHash;", "file", "getFile", "()Ljava/io/File;", "file$delegate", "Lkotlin/Lazy;", "initTasks", "readAndInitOnce", "dataChannel", "(Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "readData", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "readDataOrHandleCorruption", "resetDataChannel", "ex", "", "transformAndWrite", "transform", "t", "updateDataChannel", "(Lkotlin/jvm/functions/Function2;Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateData", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "writeData", "newData", "writeData$datastore_core", "(Ljava/lang/Object;)V", "createParentDirectories", "Message", "UncloseableOutputStream", "datastore-core"}, k = 1, mv = {1, 4, 1})
/* loaded from: classes.dex */
public final class SingleProcessDataStore<T> implements DataStore<T> {
    private final String SCRATCH_SUFFIX;
    private final SendChannel<Message<T>> actor;
    private final CorruptionHandler<T> corruptionHandler;

    @NotNull
    private final Flow<T> data;
    private final AtomicReference<ConflatedBroadcastChannel<DataAndHash<T>>> downstreamChannel;

    /* renamed from: file$delegate, reason: from kotlin metadata */
    private final Lazy file;
    private List<? extends Function2<? super InitializerApi<T>, ? super Continuation<? super Unit>, ? extends Object>> initTasks;
    private final Function0<File> produceFile;
    private final CoroutineScope scope;
    private final Serializer<T> serializer;

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u0000*\u0004\b\u0001\u0010\u00012\u00020\u0002:\u0002\t\nB\u0007\b\u0002¢\u0006\u0002\u0010\u0003R\u001e\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00060\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u0082\u0001\u0002\u000b\f¨\u0006\r"}, d2 = {"Landroidx/datastore/core/SingleProcessDataStore$Message;", ExifInterface.GPS_DIRECTION_TRUE, "", "()V", "dataChannel", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;", "Landroidx/datastore/core/DataAndHash;", "getDataChannel", "()Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;", "Read", "Update", "Landroidx/datastore/core/SingleProcessDataStore$Message$Read;", "Landroidx/datastore/core/SingleProcessDataStore$Message$Update;", "datastore-core"}, k = 1, mv = {1, 4, 1})
    public static abstract class Message<T> {

        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000*\u0004\b\u0002\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0019\u0012\u0012\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006R \u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Landroidx/datastore/core/SingleProcessDataStore$Message$Read;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/datastore/core/SingleProcessDataStore$Message;", "dataChannel", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;", "Landroidx/datastore/core/DataAndHash;", "(Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;)V", "getDataChannel", "()Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;", "datastore-core"}, k = 1, mv = {1, 4, 1})
        public static final class Read<T> extends Message<T> {

            @NotNull
            private final ConflatedBroadcastChannel<DataAndHash<T>> dataChannel;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Read(@NotNull ConflatedBroadcastChannel<DataAndHash<T>> dataChannel) {
                super(null);
                Intrinsics.checkNotNullParameter(dataChannel, "dataChannel");
                this.dataChannel = dataChannel;
            }

            @Override // androidx.datastore.core.SingleProcessDataStore.Message
            @NotNull
            public ConflatedBroadcastChannel<DataAndHash<T>> getDataChannel() {
                return this.dataChannel;
            }
        }

        @Metadata(bv = {1, 0, 3}, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000*\u0004\b\u0002\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B]\u00121\u0010\u0003\u001a-\b\u0001\u0012\u0013\u0012\u00118\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0004\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00020\u000b\u0012\u0012\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\u000e0\rø\u0001\u0000¢\u0006\u0002\u0010\u000fR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R \u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\u000e0\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013RA\u0010\u0003\u001a-\b\u0001\u0012\u0013\u0012\u00118\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0004ø\u0001\u0000¢\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0014\u0010\u0015\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0017"}, d2 = {"Landroidx/datastore/core/SingleProcessDataStore$Message$Update;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/datastore/core/SingleProcessDataStore$Message;", "transform", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "t", "Lkotlin/coroutines/Continuation;", "", i.f2276g, "Lkotlinx/coroutines/CompletableDeferred;", "dataChannel", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;", "Landroidx/datastore/core/DataAndHash;", "(Lkotlin/jvm/functions/Function2;Lkotlinx/coroutines/CompletableDeferred;Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;)V", "getAck", "()Lkotlinx/coroutines/CompletableDeferred;", "getDataChannel", "()Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;", "getTransform", "()Lkotlin/jvm/functions/Function2;", "Lkotlin/jvm/functions/Function2;", "datastore-core"}, k = 1, mv = {1, 4, 1})
        public static final class Update<T> extends Message<T> {

            @NotNull
            private final CompletableDeferred<T> ack;

            @NotNull
            private final ConflatedBroadcastChannel<DataAndHash<T>> dataChannel;

            @NotNull
            private final Function2<T, Continuation<? super T>, Object> transform;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public Update(@NotNull Function2<? super T, ? super Continuation<? super T>, ? extends Object> transform, @NotNull CompletableDeferred<T> ack, @NotNull ConflatedBroadcastChannel<DataAndHash<T>> dataChannel) {
                super(null);
                Intrinsics.checkNotNullParameter(transform, "transform");
                Intrinsics.checkNotNullParameter(ack, "ack");
                Intrinsics.checkNotNullParameter(dataChannel, "dataChannel");
                this.transform = transform;
                this.ack = ack;
                this.dataChannel = dataChannel;
            }

            @NotNull
            public final CompletableDeferred<T> getAck() {
                return this.ack;
            }

            @Override // androidx.datastore.core.SingleProcessDataStore.Message
            @NotNull
            public ConflatedBroadcastChannel<DataAndHash<T>> getDataChannel() {
                return this.dataChannel;
            }

            @NotNull
            public final Function2<T, Continuation<? super T>, Object> getTransform() {
                return this.transform;
            }
        }

        private Message() {
        }

        @NotNull
        public abstract ConflatedBroadcastChannel<DataAndHash<T>> getDataChannel();

        public /* synthetic */ Message(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u0016J \u0010\n\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u000fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Landroidx/datastore/core/SingleProcessDataStore$UncloseableOutputStream;", "Ljava/io/OutputStream;", "fileOutputStream", "Ljava/io/FileOutputStream;", "(Ljava/io/FileOutputStream;)V", "getFileOutputStream", "()Ljava/io/FileOutputStream;", "close", "", Socket.EVENT_FLUSH, "write", "b", "", HttpHeaderValues.BYTES, DebugKt.DEBUG_PROPERTY_VALUE_OFF, "", "len", "datastore-core"}, k = 1, mv = {1, 4, 1})
    public static final class UncloseableOutputStream extends OutputStream {

        @NotNull
        private final FileOutputStream fileOutputStream;

        public UncloseableOutputStream(@NotNull FileOutputStream fileOutputStream) {
            Intrinsics.checkNotNullParameter(fileOutputStream, "fileOutputStream");
            this.fileOutputStream = fileOutputStream;
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() throws IOException {
            this.fileOutputStream.flush();
        }

        @NotNull
        public final FileOutputStream getFileOutputStream() {
            return this.fileOutputStream;
        }

        @Override // java.io.OutputStream
        public void write(int b3) throws IOException {
            this.fileOutputStream.write(b3);
        }

        @Override // java.io.OutputStream
        public void write(@NotNull byte[] b3) throws IOException {
            Intrinsics.checkNotNullParameter(b3, "b");
            this.fileOutputStream.write(b3);
        }

        @Override // java.io.OutputStream
        public void write(@NotNull byte[] bytes, int off, int len) throws IOException {
            Intrinsics.checkNotNullParameter(bytes, "bytes");
            this.fileOutputStream.write(bytes, off, len);
        }
    }

    @Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\u0010\b\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u00002\u0012\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u00012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0082@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;", "Landroidx/datastore/core/DataAndHash;", "dataChannel", "Lkotlin/coroutines/Continuation;", "", "continuation", "", "readAndInitOnce"}, k = 3, mv = {1, 4, 1})
    @DebugMetadata(c = "androidx.datastore.core.SingleProcessDataStore", f = "SingleProcessDataStore.kt", i = {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2}, l = {R2.array.ease_excel_file_suffix, 216, R2.attr.arcStrokeCap}, m = "readAndInitOnce", n = {"this", "dataChannel", "updateLock", a.f23806c, "this", "dataChannel", "updateLock", a.f23806c, "initializationComplete", "api", "dataChannel", a.f23806c, "initializationComplete", "$this$withLock$iv"}, s = {"L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$0", "L$1", "L$2", "L$3"})
    /* renamed from: androidx.datastore.core.SingleProcessDataStore$readAndInitOnce$1, reason: invalid class name */
    public static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SingleProcessDataStore.this.readAndInitOnce(null, this);
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\u0010\u0004\u001a\u0004\u0018\u00010\u0003\"\u0004\b\u0000\u0010\u00002\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0001H\u0082@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/coroutines/Continuation;", "continuation", "", "readDataOrHandleCorruption"}, k = 3, mv = {1, 4, 1})
    @DebugMetadata(c = "androidx.datastore.core.SingleProcessDataStore", f = "SingleProcessDataStore.kt", i = {0, 1, 1}, l = {227, 230}, m = "readDataOrHandleCorruption", n = {"this", "this", "ex"}, s = {"L$0", "L$0", "L$1"})
    /* renamed from: androidx.datastore.core.SingleProcessDataStore$readDataOrHandleCorruption$1, reason: invalid class name and case insensitive filesystem */
    public static final class C04901 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C04901(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SingleProcessDataStore.this.readDataOrHandleCorruption(this);
        }
    }

    @Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\f\u001a\u0004\u0018\u00010\u0006\"\u0004\b\u0000\u0010\u000021\u0010\u0007\u001a-\b\u0001\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\u0002\u0012\b\b\u0003\u0012\u0004\b\b(\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00012\u0012\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\t0\b2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H\u0082@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "t", "Lkotlin/coroutines/Continuation;", "", "transform", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;", "Landroidx/datastore/core/DataAndHash;", "updateDataChannel", "continuation", "transformAndWrite"}, k = 3, mv = {1, 4, 1})
    @DebugMetadata(c = "androidx.datastore.core.SingleProcessDataStore", f = "SingleProcessDataStore.kt", i = {0, 0, 0, 0}, l = {R2.attr.adScopeTextColor}, m = "transformAndWrite", n = {"this", "updateDataChannel", "curDataAndHash", "curData"}, s = {"L$0", "L$1", "L$2", "L$3"})
    /* renamed from: androidx.datastore.core.SingleProcessDataStore$transformAndWrite$1, reason: invalid class name and case insensitive filesystem */
    public static final class C04911 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        public C04911(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SingleProcessDataStore.this.transformAndWrite(null, null, this);
        }
    }

    @Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0010\t\u001a\u0004\u0018\u00010\u0006\"\u0004\b\u0000\u0010\u000021\u0010\u0007\u001a-\b\u0001\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\u0002\u0012\b\b\u0003\u0012\u0004\b\b(\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H\u0096@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "t", "Lkotlin/coroutines/Continuation;", "", "transform", "continuation", "updateData"}, k = 3, mv = {1, 4, 1})
    @DebugMetadata(c = "androidx.datastore.core.SingleProcessDataStore", f = "SingleProcessDataStore.kt", i = {0, 0, 0, 1, 1}, l = {87, 92, 96}, m = "updateData", n = {"this", i.f2276g, "dataChannel", "this", i.f2276g}, s = {"L$0", "L$1", "L$2", "L$0", "L$1"})
    /* renamed from: androidx.datastore.core.SingleProcessDataStore$updateData$1, reason: invalid class name and case insensitive filesystem */
    public static final class C04921 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public C04921(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SingleProcessDataStore.this.updateData(null, this);
        }
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 1})
    @DebugMetadata(c = "androidx.datastore.core.SingleProcessDataStore$updateData$2", f = "SingleProcessDataStore.kt", i = {}, l = {96}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: androidx.datastore.core.SingleProcessDataStore$updateData$2, reason: invalid class name */
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super T>, Object> {
        final /* synthetic */ Ref.ObjectRef $ack;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Ref.ObjectRef objectRef, Continuation continuation) {
            super(2, continuation);
            this.$ack = objectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
            Intrinsics.checkNotNullParameter(completion, "completion");
            return new AnonymousClass2(this.$ack, completion);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Object obj) {
            return ((AnonymousClass2) create(coroutineScope, (Continuation) obj)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                CompletableDeferred completableDeferred = (CompletableDeferred) this.$ack.element;
                this.label = 1;
                obj = completableDeferred.await(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SingleProcessDataStore(@NotNull Function0<? extends File> produceFile, @NotNull Serializer<T> serializer, @NotNull List<? extends Function2<? super InitializerApi<T>, ? super Continuation<? super Unit>, ? extends Object>> initTasksList, @NotNull CorruptionHandler<T> corruptionHandler, @NotNull CoroutineScope scope) {
        Intrinsics.checkNotNullParameter(produceFile, "produceFile");
        Intrinsics.checkNotNullParameter(serializer, "serializer");
        Intrinsics.checkNotNullParameter(initTasksList, "initTasksList");
        Intrinsics.checkNotNullParameter(corruptionHandler, "corruptionHandler");
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.produceFile = produceFile;
        this.serializer = serializer;
        this.corruptionHandler = corruptionHandler;
        this.scope = scope;
        this.data = FlowKt.flow(new SingleProcessDataStore$data$1(this, null));
        this.SCRATCH_SUFFIX = ".tmp";
        this.file = LazyKt__LazyJVMKt.lazy(new Function0<File>() { // from class: androidx.datastore.core.SingleProcessDataStore$file$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final File invoke() {
                return (File) this.this$0.produceFile.invoke();
            }
        });
        this.downstreamChannel = new AtomicReference<>(new ConflatedBroadcastChannel());
        this.initTasks = CollectionsKt___CollectionsKt.toList(initTasksList);
        this.actor = ActorKt.actor$default(scope, null, Integer.MAX_VALUE, null, null, new SingleProcessDataStore$actor$1(this, null), 13, null);
    }

    private final void createParentDirectories(File file) throws IOException {
        File canonicalFile = file.getCanonicalFile();
        Intrinsics.checkNotNullExpressionValue(canonicalFile, "canonicalFile");
        File parentFile = canonicalFile.getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
            if (parentFile.isDirectory()) {
                return;
            }
            throw new IOException("Unable to create parent directories of " + file);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ConflatedBroadcastChannel<DataAndHash<T>> downstreamChannel() {
        Object obj = this.downstreamChannel.get();
        Intrinsics.checkNotNullExpressionValue(obj, "downstreamChannel.get()");
        return (ConflatedBroadcastChannel) obj;
    }

    private final File getFile() {
        return (File) this.file.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void resetDataChannel(Throwable ex) {
        this.downstreamChannel.getAndSet(new ConflatedBroadcastChannel<>()).cancel(ex);
    }

    @Override // androidx.datastore.core.DataStore
    @NotNull
    public Flow<T> getData() {
        return this.data;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0114 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final /* synthetic */ java.lang.Object readAndInitOnce(kotlinx.coroutines.channels.ConflatedBroadcastChannel<androidx.datastore.core.DataAndHash<T>> r14, kotlin.coroutines.Continuation<? super kotlin.Unit> r15) throws androidx.datastore.core.CorruptionException, java.io.FileNotFoundException {
        /*
            Method dump skipped, instructions count: 307
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.SingleProcessDataStore.readAndInitOnce(kotlinx.coroutines.channels.ConflatedBroadcastChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final /* synthetic */ Object readData(Continuation<? super T> continuation) throws FileNotFoundException {
        try {
            FileInputStream fileInputStream = new FileInputStream(getFile());
            try {
                T from = this.serializer.readFrom(fileInputStream);
                CloseableKt.closeFinally(fileInputStream, null);
                return from;
            } finally {
            }
        } catch (FileNotFoundException e2) {
            if (getFile().exists()) {
                throw e2;
            }
            return this.serializer.getDefaultValue();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0065 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final /* synthetic */ java.lang.Object readDataOrHandleCorruption(kotlin.coroutines.Continuation<? super T> r6) throws androidx.datastore.core.CorruptionException, java.io.FileNotFoundException {
        /*
            r5 = this;
            boolean r0 = r6 instanceof androidx.datastore.core.SingleProcessDataStore.C04901
            if (r0 == 0) goto L13
            r0 = r6
            androidx.datastore.core.SingleProcessDataStore$readDataOrHandleCorruption$1 r0 = (androidx.datastore.core.SingleProcessDataStore.C04901) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.datastore.core.SingleProcessDataStore$readDataOrHandleCorruption$1 r0 = new androidx.datastore.core.SingleProcessDataStore$readDataOrHandleCorruption$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L46
            if (r2 == r4) goto L3c
            if (r2 != r3) goto L34
            java.lang.Object r1 = r0.L$1
            androidx.datastore.core.CorruptionException r1 = (androidx.datastore.core.CorruptionException) r1
            java.lang.Object r0 = r0.L$0
            androidx.datastore.core.SingleProcessDataStore r0 = (androidx.datastore.core.SingleProcessDataStore) r0
            kotlin.ResultKt.throwOnFailure(r6)
            goto L69
        L34:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r0)
            throw r6
        L3c:
            java.lang.Object r2 = r0.L$0
            androidx.datastore.core.SingleProcessDataStore r2 = (androidx.datastore.core.SingleProcessDataStore) r2
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: androidx.datastore.core.CorruptionException -> L44
            goto L54
        L44:
            r6 = move-exception
            goto L57
        L46:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.L$0 = r5     // Catch: androidx.datastore.core.CorruptionException -> L55
            r0.label = r4     // Catch: androidx.datastore.core.CorruptionException -> L55
            java.lang.Object r6 = r5.readData(r0)     // Catch: androidx.datastore.core.CorruptionException -> L55
            if (r6 != r1) goto L54
            return r1
        L54:
            return r6
        L55:
            r6 = move-exception
            r2 = r5
        L57:
            androidx.datastore.core.CorruptionHandler<T> r4 = r2.corruptionHandler
            r0.L$0 = r2
            r0.L$1 = r6
            r0.label = r3
            java.lang.Object r0 = r4.handleCorruption(r6, r0)
            if (r0 != r1) goto L66
            return r1
        L66:
            r1 = r6
            r6 = r0
            r0 = r2
        L69:
            r0.writeData$datastore_core(r6)     // Catch: java.io.IOException -> L6d
            return r6
        L6d:
            r6 = move-exception
            kotlin.ExceptionsKt.addSuppressed(r1, r6)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.SingleProcessDataStore.readDataOrHandleCorruption(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final /* synthetic */ java.lang.Object transformAndWrite(kotlin.jvm.functions.Function2<? super T, ? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> r5, kotlinx.coroutines.channels.ConflatedBroadcastChannel<androidx.datastore.core.DataAndHash<T>> r6, kotlin.coroutines.Continuation<? super T> r7) throws java.io.IOException {
        /*
            r4 = this;
            boolean r0 = r7 instanceof androidx.datastore.core.SingleProcessDataStore.C04911
            if (r0 == 0) goto L13
            r0 = r7
            androidx.datastore.core.SingleProcessDataStore$transformAndWrite$1 r0 = (androidx.datastore.core.SingleProcessDataStore.C04911) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.datastore.core.SingleProcessDataStore$transformAndWrite$1 r0 = new androidx.datastore.core.SingleProcessDataStore$transformAndWrite$1
            r0.<init>(r7)
        L18:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3f
            if (r2 != r3) goto L37
            java.lang.Object r5 = r0.L$3
            java.lang.Object r6 = r0.L$2
            androidx.datastore.core.DataAndHash r6 = (androidx.datastore.core.DataAndHash) r6
            java.lang.Object r1 = r0.L$1
            kotlinx.coroutines.channels.ConflatedBroadcastChannel r1 = (kotlinx.coroutines.channels.ConflatedBroadcastChannel) r1
            java.lang.Object r0 = r0.L$0
            androidx.datastore.core.SingleProcessDataStore r0 = (androidx.datastore.core.SingleProcessDataStore) r0
            kotlin.ResultKt.throwOnFailure(r7)
            goto L65
        L37:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L3f:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.getValue()
            androidx.datastore.core.DataAndHash r7 = (androidx.datastore.core.DataAndHash) r7
            r7.checkHashCode()
            java.lang.Object r2 = r7.getValue()
            r0.L$0 = r4
            r0.L$1 = r6
            r0.L$2 = r7
            r0.L$3 = r2
            r0.label = r3
            java.lang.Object r5 = r5.invoke(r2, r0)
            if (r5 != r1) goto L60
            return r1
        L60:
            r0 = r4
            r1 = r6
            r6 = r7
            r7 = r5
            r5 = r2
        L65:
            r6.checkHashCode()
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r7)
            if (r6 == 0) goto L6f
            goto L83
        L6f:
            r0.writeData$datastore_core(r7)
            androidx.datastore.core.DataAndHash r5 = new androidx.datastore.core.DataAndHash
            if (r7 == 0) goto L7b
            int r6 = r7.hashCode()
            goto L7c
        L7b:
            r6 = 0
        L7c:
            r5.<init>(r7, r6)
            r1.offer(r5)
            r5 = r7
        L83:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.SingleProcessDataStore.transformAndWrite(kotlin.jvm.functions.Function2, kotlinx.coroutines.channels.ConflatedBroadcastChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00c4 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00c5 A[PHI: r11
      0x00c5: PHI (r11v7 java.lang.Object) = (r11v5 java.lang.Object), (r11v1 java.lang.Object) binds: [B:30:0x00c2, B:13:0x002c] A[DONT_GENERATE, DONT_INLINE], RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.datastore.core.DataStore
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object updateData(@org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function2<? super T, ? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> r10, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super T> r11) throws java.lang.Throwable {
        /*
            r9 = this;
            boolean r0 = r11 instanceof androidx.datastore.core.SingleProcessDataStore.C04921
            if (r0 == 0) goto L13
            r0 = r11
            androidx.datastore.core.SingleProcessDataStore$updateData$1 r0 = (androidx.datastore.core.SingleProcessDataStore.C04921) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.datastore.core.SingleProcessDataStore$updateData$1 r0 = new androidx.datastore.core.SingleProcessDataStore$updateData$1
            r0.<init>(r11)
        L18:
            java.lang.Object r11 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 3
            r4 = 2
            r5 = 1
            r6 = 0
            if (r2 == 0) goto L57
            if (r2 == r5) goto L45
            if (r2 == r4) goto L39
            if (r2 != r3) goto L31
            kotlin.ResultKt.throwOnFailure(r11)
            goto Lc5
        L31:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L39:
            java.lang.Object r10 = r0.L$1
            kotlin.jvm.internal.Ref$ObjectRef r10 = (kotlin.jvm.internal.Ref.ObjectRef) r10
            java.lang.Object r2 = r0.L$0
            androidx.datastore.core.SingleProcessDataStore r2 = (androidx.datastore.core.SingleProcessDataStore) r2
            kotlin.ResultKt.throwOnFailure(r11)
            goto Laa
        L45:
            java.lang.Object r10 = r0.L$2
            kotlinx.coroutines.channels.ConflatedBroadcastChannel r10 = (kotlinx.coroutines.channels.ConflatedBroadcastChannel) r10
            java.lang.Object r2 = r0.L$1
            kotlin.jvm.internal.Ref$ObjectRef r2 = (kotlin.jvm.internal.Ref.ObjectRef) r2
            java.lang.Object r5 = r0.L$0
            androidx.datastore.core.SingleProcessDataStore r5 = (androidx.datastore.core.SingleProcessDataStore) r5
            kotlin.ResultKt.throwOnFailure(r11)
            r11 = r2
            r2 = r5
            goto L90
        L57:
            kotlin.ResultKt.throwOnFailure(r11)
            kotlin.jvm.internal.Ref$ObjectRef r11 = new kotlin.jvm.internal.Ref$ObjectRef
            r11.<init>()
            kotlinx.coroutines.CompletableDeferred r2 = kotlinx.coroutines.CompletableDeferredKt.CompletableDeferred$default(r6, r5, r6)
            r11.element = r2
            java.util.concurrent.atomic.AtomicReference r2 = access$getDownstreamChannel$p(r9)
            java.lang.Object r2 = r2.get()
            java.lang.String r7 = "downstreamChannel.get()"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r7)
            kotlinx.coroutines.channels.ConflatedBroadcastChannel r2 = (kotlinx.coroutines.channels.ConflatedBroadcastChannel) r2
            androidx.datastore.core.SingleProcessDataStore$Message$Update r7 = new androidx.datastore.core.SingleProcessDataStore$Message$Update
            T r8 = r11.element
            kotlinx.coroutines.CompletableDeferred r8 = (kotlinx.coroutines.CompletableDeferred) r8
            r7.<init>(r10, r8, r2)
            kotlinx.coroutines.channels.SendChannel<androidx.datastore.core.SingleProcessDataStore$Message<T>> r10 = r9.actor
            r0.L$0 = r9
            r0.L$1 = r11
            r0.L$2 = r2
            r0.label = r5
            java.lang.Object r10 = r10.send(r7, r0)
            if (r10 != r1) goto L8e
            return r1
        L8e:
            r10 = r2
            r2 = r9
        L90:
            java.lang.Object r5 = r10.getValueOrNull()
            if (r5 != 0) goto Lab
            kotlinx.coroutines.flow.Flow r10 = kotlinx.coroutines.flow.FlowKt.asFlow(r10)
            r0.L$0 = r2
            r0.L$1 = r11
            r0.L$2 = r6
            r0.label = r4
            java.lang.Object r10 = kotlinx.coroutines.flow.FlowKt.first(r10, r0)
            if (r10 != r1) goto La9
            return r1
        La9:
            r10 = r11
        Laa:
            r11 = r10
        Lab:
            kotlinx.coroutines.CoroutineScope r10 = r2.scope
            kotlin.coroutines.CoroutineContext r10 = r10.getCoroutineContext()
            androidx.datastore.core.SingleProcessDataStore$updateData$2 r2 = new androidx.datastore.core.SingleProcessDataStore$updateData$2
            r2.<init>(r11, r6)
            r0.L$0 = r6
            r0.L$1 = r6
            r0.L$2 = r6
            r0.label = r3
            java.lang.Object r11 = kotlinx.coroutines.BuildersKt.withContext(r10, r2, r0)
            if (r11 != r1) goto Lc5
            return r1
        Lc5:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.SingleProcessDataStore.updateData(kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void writeData$datastore_core(T newData) throws IOException {
        createParentDirectories(getFile());
        File file = new File(getFile().getAbsolutePath() + this.SCRATCH_SUFFIX);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                this.serializer.writeTo(newData, new UncloseableOutputStream(fileOutputStream));
                fileOutputStream.getFD().sync();
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(fileOutputStream, null);
                if (file.renameTo(getFile())) {
                    return;
                }
                throw new IOException("Unable to rename " + file + ".This likely means that there are multiple instances of DataStore for this file. Ensure that you are only creating a single instance of datastore for this file.");
            } finally {
            }
        } catch (IOException e2) {
            if (file.exists()) {
                file.delete();
            }
            throw e2;
        }
    }

    public /* synthetic */ SingleProcessDataStore(Function0 function0, Serializer serializer, List list, CorruptionHandler corruptionHandler, CoroutineScope coroutineScope, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(function0, serializer, (i2 & 4) != 0 ? CollectionsKt__CollectionsKt.emptyList() : list, (i2 & 8) != 0 ? new NoOpCorruptionHandler() : corruptionHandler, (i2 & 16) != 0 ? CoroutineScopeKt.CoroutineScope(Dispatchers.getIO().plus(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null))) : coroutineScope);
    }
}
