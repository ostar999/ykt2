package com.ykb.ebook.dialog;

import com.ykb.ebook.api.ApiService;
import com.ykb.ebook.api.ApiServiceKt;
import com.ykb.ebook.base.BaseResponse;
import com.ykb.ebook.model.UploadImgBean;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import okhttp3.MultipartBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/model/UploadImgBean;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
@DebugMetadata(c = "com.ykb.ebook.dialog.WriteSpDialog$Builder$uploadFileComment$1", f = "WriteSpDialog.kt", i = {}, l = {314}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes7.dex */
public final class WriteSpDialog$Builder$uploadFileComment$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super BaseResponse<UploadImgBean>>, Object> {
    final /* synthetic */ MultipartBody.Part $body;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WriteSpDialog$Builder$uploadFileComment$1(MultipartBody.Part part, Continuation<? super WriteSpDialog$Builder$uploadFileComment$1> continuation) {
        super(2, continuation);
        this.$body = part;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @NotNull
    public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
        return new WriteSpDialog$Builder$uploadFileComment$1(this.$body, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    @Nullable
    public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super BaseResponse<UploadImgBean>> continuation) {
        return ((WriteSpDialog$Builder$uploadFileComment$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ApiService api = ApiServiceKt.getAPI();
            MultipartBody.Part part = this.$body;
            this.label = 1;
            obj = api.postImage(part, this);
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
