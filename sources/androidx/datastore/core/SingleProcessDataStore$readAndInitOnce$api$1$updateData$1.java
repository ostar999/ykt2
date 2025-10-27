package androidx.datastore.core;

import androidx.exifinterface.media.ExifInterface;
import com.yikaobang.yixue.R2;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0010\t\u001a\u0004\u0018\u00010\u0006\"\u0004\b\u0000\u0010\u000021\u0010\u0007\u001a-\b\u0001\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\u0002\u0012\b\b\u0003\u0012\u0004\b\b(\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H\u0096@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "t", "Lkotlin/coroutines/Continuation;", "", "transform", "continuation", "updateData"}, k = 3, mv = {1, 4, 1})
@DebugMetadata(c = "androidx.datastore.core.SingleProcessDataStore$readAndInitOnce$api$1", f = "SingleProcessDataStore.kt", i = {0, 0, 0, 1, 1}, l = {R2.attr.arcShowThumb, 205}, m = "updateData", n = {"this", "transform", "$this$withLock$iv", "this", "$this$withLock$iv"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1"})
/* loaded from: classes.dex */
public final class SingleProcessDataStore$readAndInitOnce$api$1$updateData$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ SingleProcessDataStore$readAndInitOnce$api$1 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SingleProcessDataStore$readAndInitOnce$api$1$updateData$1(SingleProcessDataStore$readAndInitOnce$api$1 singleProcessDataStore$readAndInitOnce$api$1, Continuation continuation) {
        super(continuation);
        this.this$0 = singleProcessDataStore$readAndInitOnce$api$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.updateData(null, this);
    }
}
