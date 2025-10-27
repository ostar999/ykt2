package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Ref;

@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, k = 3, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class FlowKt__LimitKt$take$2$1<T> implements FlowCollector {
    final /* synthetic */ Ref.IntRef $consumed;
    final /* synthetic */ int $count;
    final /* synthetic */ FlowCollector<T> $this_unsafeFlow;

    /* JADX WARN: Multi-variable type inference failed */
    public FlowKt__LimitKt$take$2$1(Ref.IntRef intRef, int i2, FlowCollector<? super T> flowCollector) {
        this.$consumed = intRef;
        this.$count = i2;
        this.$this_unsafeFlow = flowCollector;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object emit(T r6, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof kotlinx.coroutines.flow.FlowKt__LimitKt$take$2$1$emit$1
            if (r0 == 0) goto L13
            r0 = r7
            kotlinx.coroutines.flow.FlowKt__LimitKt$take$2$1$emit$1 r0 = (kotlinx.coroutines.flow.FlowKt__LimitKt$take$2$1$emit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.flow.FlowKt__LimitKt$take$2$1$emit$1 r0 = new kotlinx.coroutines.flow.FlowKt__LimitKt$take$2$1$emit$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L38
            if (r2 == r4) goto L34
            if (r2 != r3) goto L2c
            kotlin.ResultKt.throwOnFailure(r7)
            goto L5f
        L2c:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L34:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L51
        L38:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlin.jvm.internal.Ref$IntRef r7 = r5.$consumed
            int r2 = r7.element
            int r2 = r2 + r4
            r7.element = r2
            int r7 = r5.$count
            if (r2 >= r7) goto L54
            kotlinx.coroutines.flow.FlowCollector<T> r7 = r5.$this_unsafeFlow
            r0.label = r4
            java.lang.Object r6 = r7.emit(r6, r0)
            if (r6 != r1) goto L51
            return r1
        L51:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        L54:
            kotlinx.coroutines.flow.FlowCollector<T> r7 = r5.$this_unsafeFlow
            r0.label = r3
            java.lang.Object r6 = kotlinx.coroutines.flow.FlowKt__LimitKt.access$emitAbort$FlowKt__LimitKt(r7, r6, r0)
            if (r6 != r1) goto L5f
            return r1
        L5f:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__LimitKt$take$2$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
