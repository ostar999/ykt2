package androidx.datastore.core;

import kotlin.Metadata;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.sync.Mutex;

/* JADX INFO: Add missing generic type declarations: [T] */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001JD\u0010\u0002\u001a\u00028\u000021\u0010\u0003\u001a-\b\u0001\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0004H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\n\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000b"}, d2 = {"androidx/datastore/core/SingleProcessDataStore$readAndInitOnce$api$1", "Landroidx/datastore/core/InitializerApi;", "updateData", "transform", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "t", "Lkotlin/coroutines/Continuation;", "", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "datastore-core"}, k = 1, mv = {1, 4, 1})
/* loaded from: classes.dex */
public final class SingleProcessDataStore$readAndInitOnce$api$1<T> implements InitializerApi<T> {
    final /* synthetic */ Ref.ObjectRef $initData;
    final /* synthetic */ Ref.BooleanRef $initializationComplete;
    final /* synthetic */ Mutex $updateLock;
    final /* synthetic */ SingleProcessDataStore this$0;

    public SingleProcessDataStore$readAndInitOnce$api$1(SingleProcessDataStore singleProcessDataStore, Mutex mutex, Ref.BooleanRef booleanRef, Ref.ObjectRef objectRef) {
        this.this$0 = singleProcessDataStore;
        this.$updateLock = mutex;
        this.$initializationComplete = booleanRef;
        this.$initData = objectRef;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0097 A[Catch: all -> 0x0035, TryCatch #1 {all -> 0x0035, blocks: (B:13:0x0031, B:30:0x008c, B:32:0x0097, B:33:0x00a0), top: B:43:0x0031 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.datastore.core.InitializerApi
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object updateData(@org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function2<? super T, ? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> r9, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super T> r10) throws java.lang.Throwable {
        /*
            r8 = this;
            boolean r0 = r10 instanceof androidx.datastore.core.SingleProcessDataStore$readAndInitOnce$api$1$updateData$1
            if (r0 == 0) goto L13
            r0 = r10
            androidx.datastore.core.SingleProcessDataStore$readAndInitOnce$api$1$updateData$1 r0 = (androidx.datastore.core.SingleProcessDataStore$readAndInitOnce$api$1$updateData$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.datastore.core.SingleProcessDataStore$readAndInitOnce$api$1$updateData$1 r0 = new androidx.datastore.core.SingleProcessDataStore$readAndInitOnce$api$1$updateData$1
            r0.<init>(r8, r10)
        L18:
            java.lang.Object r10 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 2
            r4 = 1
            r5 = 0
            if (r2 == 0) goto L52
            if (r2 == r4) goto L40
            if (r2 != r3) goto L38
            java.lang.Object r9 = r0.L$1
            kotlinx.coroutines.sync.Mutex r9 = (kotlinx.coroutines.sync.Mutex) r9
            java.lang.Object r0 = r0.L$0
            androidx.datastore.core.SingleProcessDataStore$readAndInitOnce$api$1 r0 = (androidx.datastore.core.SingleProcessDataStore$readAndInitOnce$api$1) r0
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: java.lang.Throwable -> L35
            goto L8c
        L35:
            r10 = move-exception
            goto Lb4
        L38:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L40:
            java.lang.Object r9 = r0.L$2
            kotlinx.coroutines.sync.Mutex r9 = (kotlinx.coroutines.sync.Mutex) r9
            java.lang.Object r2 = r0.L$1
            kotlin.jvm.functions.Function2 r2 = (kotlin.jvm.functions.Function2) r2
            java.lang.Object r6 = r0.L$0
            androidx.datastore.core.SingleProcessDataStore$readAndInitOnce$api$1 r6 = (androidx.datastore.core.SingleProcessDataStore$readAndInitOnce$api$1) r6
            kotlin.ResultKt.throwOnFailure(r10)
            r10 = r9
            r9 = r2
            goto L67
        L52:
            kotlin.ResultKt.throwOnFailure(r10)
            kotlinx.coroutines.sync.Mutex r10 = r8.$updateLock
            r0.L$0 = r8
            r0.L$1 = r9
            r0.L$2 = r10
            r0.label = r4
            java.lang.Object r2 = r10.lock(r5, r0)
            if (r2 != r1) goto L66
            return r1
        L66:
            r6 = r8
        L67:
            kotlin.jvm.internal.Ref$BooleanRef r2 = r6.$initializationComplete     // Catch: java.lang.Throwable -> Lb0
            boolean r2 = r2.element     // Catch: java.lang.Throwable -> Lb0
            if (r2 != 0) goto La8
            kotlin.jvm.internal.Ref$ObjectRef r2 = r6.$initData     // Catch: java.lang.Throwable -> Lb0
            T r2 = r2.element     // Catch: java.lang.Throwable -> Lb0
            r0.L$0 = r6     // Catch: java.lang.Throwable -> Lb0
            r0.L$1 = r10     // Catch: java.lang.Throwable -> Lb0
            r0.L$2 = r5     // Catch: java.lang.Throwable -> Lb0
            r0.label = r3     // Catch: java.lang.Throwable -> Lb0
            r3 = 6
            kotlin.jvm.internal.InlineMarker.mark(r3)     // Catch: java.lang.Throwable -> Lb0
            java.lang.Object r9 = r9.invoke(r2, r0)     // Catch: java.lang.Throwable -> Lb0
            r0 = 7
            kotlin.jvm.internal.InlineMarker.mark(r0)     // Catch: java.lang.Throwable -> Lb0
            if (r9 != r1) goto L88
            return r1
        L88:
            r0 = r6
            r7 = r10
            r10 = r9
            r9 = r7
        L8c:
            kotlin.jvm.internal.Ref$ObjectRef r1 = r0.$initData     // Catch: java.lang.Throwable -> L35
            T r1 = r1.element     // Catch: java.lang.Throwable -> L35
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r10, r1)     // Catch: java.lang.Throwable -> L35
            r1 = r1 ^ r4
            if (r1 == 0) goto La0
            androidx.datastore.core.SingleProcessDataStore r1 = r0.this$0     // Catch: java.lang.Throwable -> L35
            r1.writeData$datastore_core(r10)     // Catch: java.lang.Throwable -> L35
            kotlin.jvm.internal.Ref$ObjectRef r1 = r0.$initData     // Catch: java.lang.Throwable -> L35
            r1.element = r10     // Catch: java.lang.Throwable -> L35
        La0:
            kotlin.jvm.internal.Ref$ObjectRef r10 = r0.$initData     // Catch: java.lang.Throwable -> L35
            T r10 = r10.element     // Catch: java.lang.Throwable -> L35
            r9.unlock(r5)
            return r10
        La8:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> Lb0
            java.lang.String r0 = "InitializerApi.updateData should not be called after initialization is complete."
            r9.<init>(r0)     // Catch: java.lang.Throwable -> Lb0
            throw r9     // Catch: java.lang.Throwable -> Lb0
        Lb0:
            r9 = move-exception
            r7 = r10
            r10 = r9
            r9 = r7
        Lb4:
            r9.unlock(r5)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.SingleProcessDataStore$readAndInitOnce$api$1.updateData(kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
