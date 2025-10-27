package androidx.datastore.core;

import androidx.datastore.core.SingleProcessDataStore;
import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ActorScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Add missing generic type declarations: [T] */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\u0003H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ActorScope;", "Landroidx/datastore/core/SingleProcessDataStore$Message;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 1})
@DebugMetadata(c = "androidx.datastore.core.SingleProcessDataStore$actor$1", f = "SingleProcessDataStore.kt", i = {0, 1, 1, 2}, l = {146, 154, 360}, m = "invokeSuspend", n = {"$this$actor", "$this$actor", "msg", "$this$actor"}, s = {"L$0", "L$0", "L$1", "L$0"})
/* loaded from: classes.dex */
public final class SingleProcessDataStore$actor$1<T> extends SuspendLambda implements Function2<ActorScope<SingleProcessDataStore.Message<T>>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ SingleProcessDataStore this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SingleProcessDataStore$actor$1(SingleProcessDataStore singleProcessDataStore, Continuation continuation) {
        super(2, continuation);
        this.this$0 = singleProcessDataStore;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @NotNull
    public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
        Intrinsics.checkNotNullParameter(completion, "completion");
        SingleProcessDataStore$actor$1 singleProcessDataStore$actor$1 = new SingleProcessDataStore$actor$1(this.this$0, completion);
        singleProcessDataStore$actor$1.L$0 = obj;
        return singleProcessDataStore$actor$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Continuation<? super Unit> continuation) {
        return ((SingleProcessDataStore$actor$1) create(obj, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0078 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0084 A[Catch: all -> 0x0139, TRY_LEAVE, TryCatch #5 {all -> 0x0139, blocks: (B:52:0x010d, B:25:0x006a, B:29:0x007c, B:31:0x0084, B:51:0x0100, B:57:0x011c), top: B:77:0x011c }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00b2 A[Catch: all -> 0x0117, TRY_LEAVE, TryCatch #1 {all -> 0x0117, blocks: (B:38:0x00ae, B:40:0x00b2), top: B:69:0x00ae }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0124  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:44:0x00e1 -> B:67:0x00e6). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:51:0x0100 -> B:52:0x010d). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:58:0x0121 -> B:25:0x006a). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r15) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 336
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.SingleProcessDataStore$actor$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
