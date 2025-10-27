package kotlinx.coroutines.channels;

import com.yikaobang.yixue.R2;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
@DebugMetadata(c = "kotlinx.coroutines.channels.AbstractChannel", f = "AbstractChannel.kt", i = {}, l = {R2.attr.bl_multi_selector6}, m = "receiveCatching-JP2dKIU", n = {}, s = {})
/* loaded from: classes8.dex */
public final class AbstractChannel$receiveCatching$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ AbstractChannel<E> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractChannel$receiveCatching$1(AbstractChannel<E> abstractChannel, Continuation<? super AbstractChannel$receiveCatching$1> continuation) {
        super(continuation);
        this.this$0 = abstractChannel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        Object objMo2313receiveCatchingJP2dKIU = this.this$0.mo2313receiveCatchingJP2dKIU(this);
        return objMo2313receiveCatchingJP2dKIU == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objMo2313receiveCatchingJP2dKIU : ChannelResult.m2320boximpl(objMo2313receiveCatchingJP2dKIU);
    }
}
