package kotlinx.coroutines.selects;

import androidx.concurrent.futures.a;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.umeng.analytics.pro.d;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.CompletedExceptionally;
import kotlinx.coroutines.CompletionStateKt;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobCancellingNode;
import kotlinx.coroutines.internal.AtomicDesc;
import kotlinx.coroutines.internal.AtomicOp;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.OpDescriptor;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000®\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b\u0001\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\u00020Y2\b\u0012\u0004\u0012\u00028\u00000Z2\b\u0012\u0004\u0012\u00028\u00000[2\b\u0012\u0004\u0012\u00028\u00000\u00022\u00060Bj\u0002`C:\u0004TUVWB\u0015\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0006H\u0016¢\u0006\u0004\b\t\u0010\nJ\u000f\u0010\u000b\u001a\u00020\bH\u0002¢\u0006\u0004\b\u000b\u0010\fJ.\u0010\u0011\u001a\u00020\b2\u000e\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\r2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\rH\u0082\b¢\u0006\u0004\b\u0011\u0010\u0012J\u0011\u0010\u0013\u001a\u0004\u0018\u00010\u000eH\u0001¢\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0017\u001a\n\u0018\u00010\u0015j\u0004\u0018\u0001`\u0016H\u0016¢\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u001b\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u0019H\u0001¢\u0006\u0004\b\u001b\u0010\u001cJ\u000f\u0010\u001d\u001a\u00020\bH\u0002¢\u0006\u0004\b\u001d\u0010\fJ8\u0010!\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\u001e2\u001c\u0010\u0010\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u000e0 H\u0016ø\u0001\u0000¢\u0006\u0004\b!\u0010\"J\u0019\u0010%\u001a\u0004\u0018\u00010\u000e2\u0006\u0010$\u001a\u00020#H\u0016¢\u0006\u0004\b%\u0010&J\u0017\u0010(\u001a\u00020\b2\u0006\u0010'\u001a\u00020\u0019H\u0016¢\u0006\u0004\b(\u0010\u001cJ \u0010+\u001a\u00020\b2\f\u0010*\u001a\b\u0012\u0004\u0012\u00028\u00000)H\u0016ø\u0001\u0000¢\u0006\u0004\b+\u0010,J\u000f\u0010.\u001a\u00020-H\u0016¢\u0006\u0004\b.\u0010/J\u000f\u00101\u001a\u000200H\u0016¢\u0006\u0004\b1\u00102J\u001b\u00105\u001a\u0004\u0018\u00010\u000e2\b\u00104\u001a\u0004\u0018\u000103H\u0016¢\u0006\u0004\b5\u00106J5\u00108\u001a\u00020\b*\u0002072\u001c\u0010\u0010\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u000e0 H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b8\u00109JG\u00108\u001a\u00020\b\"\u0004\b\u0001\u0010:*\b\u0012\u0004\u0012\u00028\u00010;2\"\u0010\u0010\u001a\u001e\b\u0001\u0012\u0004\u0012\u00028\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u000e0<H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b8\u0010=J[\u00108\u001a\u00020\b\"\u0004\b\u0001\u0010>\"\u0004\b\u0002\u0010:*\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020?2\u0006\u0010@\u001a\u00028\u00012\"\u0010\u0010\u001a\u001e\b\u0001\u0012\u0004\u0012\u00028\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u000e0<H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b8\u0010AR\u001c\u0010F\u001a\n\u0018\u00010Bj\u0004\u0018\u0001`C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bD\u0010ER\u001a\u0010I\u001a\b\u0012\u0004\u0012\u00028\u00000\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bG\u0010HR\u0014\u0010M\u001a\u00020J8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bK\u0010LR\u0014\u0010N\u001a\u0002008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bN\u00102R(\u0010R\u001a\u0004\u0018\u00010\u00062\b\u0010\u000f\u001a\u0004\u0018\u00010\u00068B@BX\u0082\u000e¢\u0006\f\u001a\u0004\bO\u0010P\"\u0004\bQ\u0010\nR\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010S\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006X"}, d2 = {"Lkotlinx/coroutines/selects/SelectBuilderImpl;", "R", "Lkotlin/coroutines/Continuation;", "uCont", "<init>", "(Lkotlin/coroutines/Continuation;)V", "Lkotlinx/coroutines/DisposableHandle;", "handle", "", "disposeOnSelect", "(Lkotlinx/coroutines/DisposableHandle;)V", "doAfterSelect", "()V", "Lkotlin/Function0;", "", "value", "block", "doResume", "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "getResult", "()Ljava/lang/Object;", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "getStackTraceElement", "()Ljava/lang/StackTraceElement;", "", AliyunLogKey.KEY_EVENT, "handleBuilderException", "(Ljava/lang/Throwable;)V", "initCancellability", "", "timeMillis", "Lkotlin/Function1;", "onTimeout", "(JLkotlin/jvm/functions/Function1;)V", "Lkotlinx/coroutines/internal/AtomicDesc;", "desc", "performAtomicTrySelect", "(Lkotlinx/coroutines/internal/AtomicDesc;)Ljava/lang/Object;", "exception", "resumeSelectWithException", "Lkotlin/Result;", "result", "resumeWith", "(Ljava/lang/Object;)V", "", "toString", "()Ljava/lang/String;", "", "trySelect", "()Z", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "otherOp", "trySelectOther", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)Ljava/lang/Object;", "Lkotlinx/coroutines/selects/SelectClause0;", "invoke", "(Lkotlinx/coroutines/selects/SelectClause0;Lkotlin/jvm/functions/Function1;)V", "Q", "Lkotlinx/coroutines/selects/SelectClause1;", "Lkotlin/Function2;", "(Lkotlinx/coroutines/selects/SelectClause1;Lkotlin/jvm/functions/Function2;)V", "P", "Lkotlinx/coroutines/selects/SelectClause2;", "param", "(Lkotlinx/coroutines/selects/SelectClause2;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "callerFrame", "getCompletion", "()Lkotlin/coroutines/Continuation;", "completion", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", d.R, "isSelected", "getParentHandle", "()Lkotlinx/coroutines/DisposableHandle;", "setParentHandle", "parentHandle", "Lkotlin/coroutines/Continuation;", "AtomicSelectOp", "DisposeNode", "PairSelectOp", "SelectOnCancelling", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "Lkotlinx/coroutines/selects/SelectBuilder;", "Lkotlinx/coroutines/selects/SelectInstance;"}, k = 1, mv = {1, 6, 0}, xi = 48)
@PublishedApi
/* renamed from: kotlinx.coroutines.selects.SelectBuilderImpl, reason: from toString */
/* loaded from: classes8.dex */
public final class SelectInstance<R> extends LockFreeLinkedListHead implements SelectBuilder<R>, kotlinx.coroutines.selects.SelectInstance<R>, Continuation<R>, CoroutineStackFrame {

    @NotNull
    private final Continuation<R> uCont;
    static final /* synthetic */ AtomicReferenceFieldUpdater _state$FU = AtomicReferenceFieldUpdater.newUpdater(SelectInstance.class, Object.class, "_state");
    private static final /* synthetic */ AtomicReferenceFieldUpdater _result$FU = AtomicReferenceFieldUpdater.newUpdater(SelectInstance.class, Object.class, "_result");

    /* renamed from: _state, reason: from toString */
    @NotNull
    volatile /* synthetic */ Object state = SelectKt.getNOT_SELECTED();

    /* renamed from: _result, reason: from toString */
    @NotNull
    private volatile /* synthetic */ Object result = SelectKt.UNDECIDED;

    @NotNull
    private volatile /* synthetic */ Object _parentHandle = null;

    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0019\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u001c\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u00022\b\u0010\u000f\u001a\u0004\u0018\u00010\u0002H\u0016J\u0012\u0010\u0010\u001a\u00020\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0002H\u0002J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u00022\b\u0010\u000e\u001a\u0004\u0018\u00010\u0002H\u0016J\n\u0010\u0012\u001a\u0004\u0018\u00010\u0002H\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\rH\u0002R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lkotlinx/coroutines/selects/SelectBuilderImpl$AtomicSelectOp;", "Lkotlinx/coroutines/internal/AtomicOp;", "", "impl", "Lkotlinx/coroutines/selects/SelectBuilderImpl;", "desc", "Lkotlinx/coroutines/internal/AtomicDesc;", "(Lkotlinx/coroutines/selects/SelectBuilderImpl;Lkotlinx/coroutines/internal/AtomicDesc;)V", "opSequence", "", "getOpSequence", "()J", "complete", "", "affected", "failure", "completeSelect", "prepare", "prepareSelectOp", "toString", "", "undoPrepare", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* renamed from: kotlinx.coroutines.selects.SelectBuilderImpl$AtomicSelectOp, reason: from toString */
    public static final class AtomicSelectOp extends AtomicOp<Object> {

        @JvmField
        @NotNull
        public final AtomicDesc desc;

        @JvmField
        @NotNull
        public final SelectInstance<?> impl;
        private final long opSequence = SelectKt.selectOpSequenceNumber.next();

        public AtomicSelectOp(@NotNull SelectInstance<?> selectInstance, @NotNull AtomicDesc atomicDesc) {
            this.impl = selectInstance;
            this.desc = atomicDesc;
            atomicDesc.setAtomicOp(this);
        }

        private final void completeSelect(Object failure) {
            boolean z2 = failure == null;
            if (a.a(SelectInstance._state$FU, this.impl, this, z2 ? null : SelectKt.getNOT_SELECTED()) && z2) {
                this.impl.doAfterSelect();
            }
        }

        private final Object prepareSelectOp() {
            SelectInstance<?> selectInstance = this.impl;
            while (true) {
                Object obj = selectInstance.state;
                if (obj == this) {
                    return null;
                }
                if (obj instanceof OpDescriptor) {
                    ((OpDescriptor) obj).perform(this.impl);
                } else {
                    if (obj != SelectKt.getNOT_SELECTED()) {
                        return SelectKt.getALREADY_SELECTED();
                    }
                    if (a.a(SelectInstance._state$FU, this.impl, SelectKt.getNOT_SELECTED(), this)) {
                        return null;
                    }
                }
            }
        }

        private final void undoPrepare() {
            a.a(SelectInstance._state$FU, this.impl, this, SelectKt.getNOT_SELECTED());
        }

        @Override // kotlinx.coroutines.internal.AtomicOp
        public void complete(@Nullable Object affected, @Nullable Object failure) {
            completeSelect(failure);
            this.desc.complete(this, failure);
        }

        @Override // kotlinx.coroutines.internal.AtomicOp
        public long getOpSequence() {
            return this.opSequence;
        }

        @Override // kotlinx.coroutines.internal.AtomicOp
        @Nullable
        public Object prepare(@Nullable Object affected) {
            Object objPrepareSelectOp;
            if (affected == null && (objPrepareSelectOp = prepareSelectOp()) != null) {
                return objPrepareSelectOp;
            }
            try {
                return this.desc.prepare(this);
            } catch (Throwable th) {
                if (affected == null) {
                    undoPrepare();
                }
                throw th;
            }
        }

        @Override // kotlinx.coroutines.internal.OpDescriptor
        @NotNull
        public String toString() {
            return "AtomicSelectOp(sequence=" + getOpSequence() + ')';
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lkotlinx/coroutines/selects/SelectBuilderImpl$DisposeNode;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "handle", "Lkotlinx/coroutines/DisposableHandle;", "(Lkotlinx/coroutines/DisposableHandle;)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* renamed from: kotlinx.coroutines.selects.SelectBuilderImpl$DisposeNode */
    public static final class DisposeNode extends LockFreeLinkedListNode {

        @JvmField
        @NotNull
        public final DisposableHandle handle;

        public DisposeNode(@NotNull DisposableHandle disposableHandle) {
            this.handle = disposableHandle;
        }
    }

    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\nH\u0016R\u0018\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lkotlinx/coroutines/selects/SelectBuilderImpl$PairSelectOp;", "Lkotlinx/coroutines/internal/OpDescriptor;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)V", "atomicOp", "Lkotlinx/coroutines/internal/AtomicOp;", "getAtomicOp", "()Lkotlinx/coroutines/internal/AtomicOp;", "perform", "", "affected", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* renamed from: kotlinx.coroutines.selects.SelectBuilderImpl$PairSelectOp */
    public static final class PairSelectOp extends OpDescriptor {

        @JvmField
        @NotNull
        public final LockFreeLinkedListNode.PrepareOp otherOp;

        public PairSelectOp(@NotNull LockFreeLinkedListNode.PrepareOp prepareOp) {
            this.otherOp = prepareOp;
        }

        @Override // kotlinx.coroutines.internal.OpDescriptor
        @NotNull
        public AtomicOp<?> getAtomicOp() {
            return this.otherOp.getAtomicOp();
        }

        @Override // kotlinx.coroutines.internal.OpDescriptor
        @Nullable
        public Object perform(@Nullable Object affected) {
            if (affected == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.selects.SelectBuilderImpl<*>");
            }
            SelectInstance selectInstance = (SelectInstance) affected;
            this.otherOp.finishPrepare();
            Object objDecide = this.otherOp.getAtomicOp().decide(null);
            a.a(SelectInstance._state$FU, selectInstance, this, objDecide == null ? this.otherOp.desc : SelectKt.getNOT_SELECTED());
            return objDecide;
        }
    }

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0096\u0002¨\u0006\u0007"}, d2 = {"Lkotlinx/coroutines/selects/SelectBuilderImpl$SelectOnCancelling;", "Lkotlinx/coroutines/JobCancellingNode;", "(Lkotlinx/coroutines/selects/SelectBuilderImpl;)V", "invoke", "", "cause", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* renamed from: kotlinx.coroutines.selects.SelectBuilderImpl$SelectOnCancelling */
    public final class SelectOnCancelling extends JobCancellingNode {
        public SelectOnCancelling() {
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        @Override // kotlinx.coroutines.CompletionHandlerBase
        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public void invoke2(@Nullable Throwable cause) {
            if (SelectInstance.this.trySelect()) {
                SelectInstance.this.resumeSelectWithException(getJob().getCancellationException());
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SelectInstance(@NotNull Continuation<? super R> continuation) {
        this.uCont = continuation;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void doAfterSelect() {
        DisposableHandle parentHandle = getParentHandle();
        if (parentHandle != null) {
            parentHandle.dispose();
        }
        for (LockFreeLinkedListNode nextNode = (LockFreeLinkedListNode) getNext(); !Intrinsics.areEqual(nextNode, this); nextNode = nextNode.getNextNode()) {
            if (nextNode instanceof DisposeNode) {
                ((DisposeNode) nextNode).handle.dispose();
            }
        }
    }

    private final void doResume(Function0<? extends Object> value, Function0<Unit> block) {
        while (true) {
            Object obj = this.result;
            if (obj == SelectKt.UNDECIDED) {
                if (a.a(_result$FU, this, SelectKt.UNDECIDED, value.invoke())) {
                    return;
                }
            } else {
                if (obj != IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    throw new IllegalStateException("Already resumed");
                }
                if (a.a(_result$FU, this, IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED(), SelectKt.RESUMED)) {
                    block.invoke();
                    return;
                }
            }
        }
    }

    private final DisposableHandle getParentHandle() {
        return (DisposableHandle) this._parentHandle;
    }

    private final void initCancellability() {
        Job job = (Job) getContext().get(Job.INSTANCE);
        if (job == null) {
            return;
        }
        DisposableHandle disposableHandleInvokeOnCompletion$default = Job.DefaultImpls.invokeOnCompletion$default(job, true, false, new SelectOnCancelling(), 2, null);
        setParentHandle(disposableHandleInvokeOnCompletion$default);
        if (isSelected()) {
            disposableHandleInvokeOnCompletion$default.dispose();
        }
    }

    private final void setParentHandle(DisposableHandle disposableHandle) {
        this._parentHandle = disposableHandle;
    }

    @Override // kotlinx.coroutines.selects.SelectInstance
    public void disposeOnSelect(@NotNull DisposableHandle handle) {
        DisposeNode disposeNode = new DisposeNode(handle);
        if (!isSelected()) {
            addLast(disposeNode);
            if (!isSelected()) {
                return;
            }
        }
        handle.dispose();
    }

    @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
    @Nullable
    public CoroutineStackFrame getCallerFrame() {
        Continuation<R> continuation = this.uCont;
        if (continuation instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation;
        }
        return null;
    }

    @Override // kotlinx.coroutines.selects.SelectInstance
    @NotNull
    public Continuation<R> getCompletion() {
        return this;
    }

    @Override // kotlin.coroutines.Continuation
    @NotNull
    public CoroutineContext getContext() {
        return this.uCont.getContext();
    }

    @PublishedApi
    @Nullable
    public final Object getResult() {
        if (!isSelected()) {
            initCancellability();
        }
        Object obj = this.result;
        if (obj == SelectKt.UNDECIDED) {
            if (a.a(_result$FU, this, SelectKt.UNDECIDED, IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED())) {
                return IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            }
            obj = this.result;
        }
        if (obj == SelectKt.RESUMED) {
            throw new IllegalStateException("Already resumed");
        }
        if (obj instanceof CompletedExceptionally) {
            throw ((CompletedExceptionally) obj).cause;
        }
        return obj;
    }

    @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
    @Nullable
    public StackTraceElement getStackTraceElement() {
        return null;
    }

    @PublishedApi
    public final void handleBuilderException(@NotNull Throwable e2) {
        if (trySelect()) {
            Result.Companion companion = Result.INSTANCE;
            resumeWith(Result.m783constructorimpl(ResultKt.createFailure(e2)));
        } else {
            if (e2 instanceof CancellationException) {
                return;
            }
            Object result = getResult();
            if ((result instanceof CompletedExceptionally) && ((CompletedExceptionally) result).cause == e2) {
                return;
            }
            CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), e2);
        }
    }

    @Override // kotlinx.coroutines.selects.SelectBuilder
    public <P, Q> void invoke(@NotNull SelectClause2<? super P, ? extends Q> selectClause2, @NotNull Function2<? super Q, ? super Continuation<? super R>, ? extends Object> function2) {
        SelectBuilder.DefaultImpls.invoke(this, selectClause2, function2);
    }

    @Override // kotlinx.coroutines.selects.SelectInstance
    public boolean isSelected() {
        while (true) {
            Object obj = this.state;
            if (obj == SelectKt.getNOT_SELECTED()) {
                return false;
            }
            if (!(obj instanceof OpDescriptor)) {
                return true;
            }
            ((OpDescriptor) obj).perform(this);
        }
    }

    @Override // kotlinx.coroutines.selects.SelectBuilder
    public void onTimeout(long timeMillis, @NotNull final Function1<? super Continuation<? super R>, ? extends Object> block) {
        if (timeMillis > 0) {
            disposeOnSelect(DelayKt.getDelay(getContext()).invokeOnTimeout(timeMillis, new Runnable() { // from class: kotlinx.coroutines.selects.SelectBuilderImpl$onTimeout$$inlined$Runnable$1
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    if (this.this$0.trySelect()) {
                        CancellableKt.startCoroutineCancellable(block, this.this$0.getCompletion());
                    }
                }
            }, getContext()));
        } else if (trySelect()) {
            UndispatchedKt.startCoroutineUnintercepted(block, getCompletion());
        }
    }

    @Override // kotlinx.coroutines.selects.SelectInstance
    @Nullable
    public Object performAtomicTrySelect(@NotNull AtomicDesc desc) {
        return new AtomicSelectOp(this, desc).perform(null);
    }

    @Override // kotlinx.coroutines.selects.SelectInstance
    public void resumeSelectWithException(@NotNull Throwable exception) {
        while (true) {
            Object obj = this.result;
            if (obj == SelectKt.UNDECIDED) {
                if (a.a(_result$FU, this, SelectKt.UNDECIDED, new CompletedExceptionally(exception, false, 2, null))) {
                    return;
                }
            } else {
                if (obj != IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    throw new IllegalStateException("Already resumed");
                }
                if (a.a(_result$FU, this, IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED(), SelectKt.RESUMED)) {
                    Continuation continuationIntercepted = IntrinsicsKt__IntrinsicsJvmKt.intercepted(this.uCont);
                    Result.Companion companion = Result.INSTANCE;
                    continuationIntercepted.resumeWith(Result.m783constructorimpl(ResultKt.createFailure(exception)));
                    return;
                }
            }
        }
    }

    @Override // kotlin.coroutines.Continuation
    public void resumeWith(@NotNull Object result) {
        while (true) {
            Object obj = this.result;
            if (obj == SelectKt.UNDECIDED) {
                if (a.a(_result$FU, this, SelectKt.UNDECIDED, CompletionStateKt.toState$default(result, null, 1, null))) {
                    return;
                }
            } else {
                if (obj != IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    throw new IllegalStateException("Already resumed");
                }
                if (a.a(_result$FU, this, IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED(), SelectKt.RESUMED)) {
                    if (!Result.m789isFailureimpl(result)) {
                        this.uCont.resumeWith(result);
                        return;
                    }
                    Continuation<R> continuation = this.uCont;
                    Throwable thM786exceptionOrNullimpl = Result.m786exceptionOrNullimpl(result);
                    Intrinsics.checkNotNull(thM786exceptionOrNullimpl);
                    Result.Companion companion = Result.INSTANCE;
                    continuation.resumeWith(Result.m783constructorimpl(ResultKt.createFailure(thM786exceptionOrNullimpl)));
                    return;
                }
            }
        }
    }

    @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
    @NotNull
    public String toString() {
        return "SelectInstance(state=" + this.state + ", result=" + this.result + ')';
    }

    @Override // kotlinx.coroutines.selects.SelectInstance
    public boolean trySelect() {
        Object objTrySelectOther = trySelectOther(null);
        if (objTrySelectOther == CancellableContinuationImplKt.RESUME_TOKEN) {
            return true;
        }
        if (objTrySelectOther == null) {
            return false;
        }
        throw new IllegalStateException(("Unexpected trySelectIdempotent result " + objTrySelectOther).toString());
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0030, code lost:
    
        doAfterSelect();
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0035, code lost:
    
        return kotlinx.coroutines.CancellableContinuationImplKt.RESUME_TOKEN;
     */
    @Override // kotlinx.coroutines.selects.SelectInstance
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object trySelectOther(@org.jetbrains.annotations.Nullable kotlinx.coroutines.internal.LockFreeLinkedListNode.PrepareOp r4) {
        /*
            r3 = this;
        L0:
            java.lang.Object r0 = r3.state
            java.lang.Object r1 = kotlinx.coroutines.selects.SelectKt.getNOT_SELECTED()
            r2 = 0
            if (r0 != r1) goto L36
            if (r4 != 0) goto L18
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = kotlinx.coroutines.selects.SelectInstance._state$FU
            java.lang.Object r1 = kotlinx.coroutines.selects.SelectKt.getNOT_SELECTED()
            boolean r0 = androidx.concurrent.futures.a.a(r0, r3, r1, r2)
            if (r0 != 0) goto L30
            goto L0
        L18:
            kotlinx.coroutines.selects.SelectBuilderImpl$PairSelectOp r0 = new kotlinx.coroutines.selects.SelectBuilderImpl$PairSelectOp
            r0.<init>(r4)
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r1 = kotlinx.coroutines.selects.SelectInstance._state$FU
            java.lang.Object r2 = kotlinx.coroutines.selects.SelectKt.getNOT_SELECTED()
            boolean r1 = androidx.concurrent.futures.a.a(r1, r3, r2, r0)
            if (r1 == 0) goto L0
            java.lang.Object r4 = r0.perform(r3)
            if (r4 == 0) goto L30
            return r4
        L30:
            r3.doAfterSelect()
            kotlinx.coroutines.internal.Symbol r4 = kotlinx.coroutines.CancellableContinuationImplKt.RESUME_TOKEN
            return r4
        L36:
            boolean r1 = r0 instanceof kotlinx.coroutines.internal.OpDescriptor
            if (r1 == 0) goto L6a
            if (r4 == 0) goto L64
            kotlinx.coroutines.internal.AtomicOp r1 = r4.getAtomicOp()
            boolean r2 = r1 instanceof kotlinx.coroutines.selects.SelectInstance.AtomicSelectOp
            if (r2 == 0) goto L58
            r2 = r1
            kotlinx.coroutines.selects.SelectBuilderImpl$AtomicSelectOp r2 = (kotlinx.coroutines.selects.SelectInstance.AtomicSelectOp) r2
            kotlinx.coroutines.selects.SelectBuilderImpl<?> r2 = r2.impl
            if (r2 == r3) goto L4c
            goto L58
        L4c:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r0 = "Cannot use matching select clauses on the same object"
            java.lang.String r0 = r0.toString()
            r4.<init>(r0)
            throw r4
        L58:
            r2 = r0
            kotlinx.coroutines.internal.OpDescriptor r2 = (kotlinx.coroutines.internal.OpDescriptor) r2
            boolean r1 = r1.isEarlierThan(r2)
            if (r1 == 0) goto L64
            java.lang.Object r4 = kotlinx.coroutines.internal.AtomicKt.RETRY_ATOMIC
            return r4
        L64:
            kotlinx.coroutines.internal.OpDescriptor r0 = (kotlinx.coroutines.internal.OpDescriptor) r0
            r0.perform(r3)
            goto L0
        L6a:
            if (r4 != 0) goto L6d
            return r2
        L6d:
            kotlinx.coroutines.internal.LockFreeLinkedListNode$AbstractAtomicDesc r4 = r4.desc
            if (r0 != r4) goto L74
            kotlinx.coroutines.internal.Symbol r4 = kotlinx.coroutines.CancellableContinuationImplKt.RESUME_TOKEN
            return r4
        L74:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.selects.SelectInstance.trySelectOther(kotlinx.coroutines.internal.LockFreeLinkedListNode$PrepareOp):java.lang.Object");
    }

    @Override // kotlinx.coroutines.selects.SelectBuilder
    public void invoke(@NotNull SelectClause0 selectClause0, @NotNull Function1<? super Continuation<? super R>, ? extends Object> function1) {
        selectClause0.registerSelectClause0(this, function1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.selects.SelectBuilder
    public <Q> void invoke(@NotNull SelectClause1<? extends Q> selectClause1, @NotNull Function2<? super Q, ? super Continuation<? super R>, ? extends Object> function2) {
        selectClause1.registerSelectClause1(this, function2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.selects.SelectBuilder
    public <P, Q> void invoke(@NotNull SelectClause2<? super P, ? extends Q> selectClause2, P p2, @NotNull Function2<? super Q, ? super Continuation<? super R>, ? extends Object> function2) {
        selectClause2.registerSelectClause2(this, p2, function2);
    }
}
