package com.ykb.ebook.dialog;

import android.text.TextUtils;
import android.widget.Toast;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.ykb.ebook.base.BaseResponse;
import com.ykb.ebook.dialog.NoteListDialog;
import com.ykb.ebook.model.NoteListBean;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\u00020\u00002\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
@DebugMetadata(c = "com.ykb.ebook.dialog.NoteListDialog$Builder$delNote$2", f = "NoteListDialog.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes7.dex */
public final class NoteListDialog$Builder$delNote$2 extends SuspendLambda implements Function3<CoroutineScope, BaseResponse<Object>, Continuation<? super Unit>, Object> {
    final /* synthetic */ NoteListBean $item;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NoteListDialog.Builder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoteListDialog$Builder$delNote$2(NoteListDialog.Builder builder, NoteListBean noteListBean, Continuation<? super NoteListDialog$Builder$delNote$2> continuation) {
        super(3, continuation);
        this.this$0 = builder;
        this.$item = noteListBean;
    }

    @Override // kotlin.jvm.functions.Function3
    @Nullable
    public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull BaseResponse<Object> baseResponse, @Nullable Continuation<? super Unit> continuation) {
        NoteListDialog$Builder$delNote$2 noteListDialog$Builder$delNote$2 = new NoteListDialog$Builder$delNote$2(this.this$0, this.$item, continuation);
        noteListDialog$Builder$delNote$2.L$0 = baseResponse;
        return noteListDialog$Builder$delNote$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        BaseResponse baseResponse = (BaseResponse) this.L$0;
        if (baseResponse.getCode() != 200) {
            if (!TextUtils.isEmpty(baseResponse.getMessage())) {
                Toast.makeText(this.this$0.getContext(), baseResponse.getMessage(), 0).show();
            }
            baseResponse.throwApiException();
        } else {
            this.this$0.mAdapter.remove(this.$item);
            Function0 function0 = this.this$0.onDeleteClick;
            if (function0 != null) {
                function0.invoke();
            }
        }
        return Unit.INSTANCE;
    }
}
