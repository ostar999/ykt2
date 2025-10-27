package kotlinx.coroutines.flow;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.d;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.JvmField;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.ChannelFlowOperatorImpl;
import kotlinx.coroutines.internal.Symbol;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000L\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u001a0\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u000b\u001a6\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00060\r\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0000\u001a#\u0010\u0012\u001a\u0004\u0018\u00010\u0013*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002¢\u0006\u0002\u0010\u0017\u001a+\u0010\u0018\u001a\u00020\u0019*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u00142\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u001a\u001a\u0004\u0018\u00010\u0013H\u0002¢\u0006\u0002\u0010\u001b\"\u0016\u0010\u0000\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0002\u0010\u0003¨\u0006\u001c"}, d2 = {"NO_VALUE", "Lkotlinx/coroutines/internal/Symbol;", "getNO_VALUE$annotations", "()V", "MutableSharedFlow", "Lkotlinx/coroutines/flow/MutableSharedFlow;", ExifInterface.GPS_DIRECTION_TRUE, "replay", "", "extraBufferCapacity", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "fuseSharedFlow", "Lkotlinx/coroutines/flow/Flow;", "Lkotlinx/coroutines/flow/SharedFlow;", d.R, "Lkotlin/coroutines/CoroutineContext;", "capacity", "getBufferAt", "", "", "index", "", "([Ljava/lang/Object;J)Ljava/lang/Object;", "setBufferAt", "", "item", "([Ljava/lang/Object;JLjava/lang/Object;)V", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class SharedFlowKt {

    @JvmField
    @NotNull
    public static final Symbol NO_VALUE = new Symbol("NO_VALUE");

    @NotNull
    public static final <T> MutableSharedFlow<T> MutableSharedFlow(int i2, int i3, @NotNull BufferOverflow bufferOverflow) {
        boolean z2 = true;
        if (!(i2 >= 0)) {
            throw new IllegalArgumentException(("replay cannot be negative, but was " + i2).toString());
        }
        if (!(i3 >= 0)) {
            throw new IllegalArgumentException(("extraBufferCapacity cannot be negative, but was " + i3).toString());
        }
        if (i2 <= 0 && i3 <= 0 && bufferOverflow != BufferOverflow.SUSPEND) {
            z2 = false;
        }
        if (z2) {
            int i4 = i3 + i2;
            if (i4 < 0) {
                i4 = Integer.MAX_VALUE;
            }
            return new SharedFlowImpl(i2, i4, bufferOverflow);
        }
        throw new IllegalArgumentException(("replay or extraBufferCapacity must be positive with non-default onBufferOverflow strategy " + bufferOverflow).toString());
    }

    public static /* synthetic */ MutableSharedFlow MutableSharedFlow$default(int i2, int i3, BufferOverflow bufferOverflow, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = 0;
        }
        if ((i4 & 2) != 0) {
            i3 = 0;
        }
        if ((i4 & 4) != 0) {
            bufferOverflow = BufferOverflow.SUSPEND;
        }
        return MutableSharedFlow(i2, i3, bufferOverflow);
    }

    @NotNull
    public static final <T> Flow<T> fuseSharedFlow(@NotNull SharedFlow<? extends T> sharedFlow, @NotNull CoroutineContext coroutineContext, int i2, @NotNull BufferOverflow bufferOverflow) {
        return ((i2 == 0 || i2 == -3) && bufferOverflow == BufferOverflow.SUSPEND) ? sharedFlow : new ChannelFlowOperatorImpl(sharedFlow, coroutineContext, i2, bufferOverflow);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object getBufferAt(Object[] objArr, long j2) {
        return objArr[((int) j2) & (objArr.length - 1)];
    }

    public static /* synthetic */ void getNO_VALUE$annotations() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setBufferAt(Object[] objArr, long j2, Object obj) {
        objArr[((int) j2) & (objArr.length - 1)] = obj;
    }
}
