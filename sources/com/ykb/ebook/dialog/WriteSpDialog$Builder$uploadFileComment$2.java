package com.ykb.ebook.dialog;

import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.ykb.ebook.base.BaseResponse;
import com.ykb.ebook.dialog.WriteSpDialog;
import com.ykb.ebook.model.UploadImgBean;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\u00020\u00002\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/model/UploadImgBean;", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
@DebugMetadata(c = "com.ykb.ebook.dialog.WriteSpDialog$Builder$uploadFileComment$2", f = "WriteSpDialog.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes7.dex */
public final class WriteSpDialog$Builder$uploadFileComment$2 extends SuspendLambda implements Function3<CoroutineScope, BaseResponse<UploadImgBean>, Continuation<? super Unit>, Object> {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ WriteSpDialog.Builder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WriteSpDialog$Builder$uploadFileComment$2(WriteSpDialog.Builder builder, Continuation<? super WriteSpDialog$Builder$uploadFileComment$2> continuation) {
        super(3, continuation);
        this.this$0 = builder;
    }

    @Override // kotlin.jvm.functions.Function3
    @Nullable
    public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull BaseResponse<UploadImgBean> baseResponse, @Nullable Continuation<? super Unit> continuation) {
        WriteSpDialog$Builder$uploadFileComment$2 writeSpDialog$Builder$uploadFileComment$2 = new WriteSpDialog$Builder$uploadFileComment$2(this.this$0, continuation);
        writeSpDialog$Builder$uploadFileComment$2.L$0 = baseResponse;
        return writeSpDialog$Builder$uploadFileComment$2.invokeSuspend(Unit.INSTANCE);
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
        if (baseResponse.getCode() == 200) {
            WriteSpDialog.Builder builder = this.this$0;
            Object data = baseResponse.getData();
            Intrinsics.checkNotNull(data);
            builder.localImagePath = ((UploadImgBean) data).getB_img();
            ProgressBar processView = this.this$0.getProcessView();
            if (processView != null) {
                processView.setVisibility(8);
            }
        } else if (!TextUtils.isEmpty(baseResponse.getMessage())) {
            Toast.makeText(this.this$0.getContext(), "上传失败！", 0).show();
        }
        this.this$0.uploadingImg = false;
        return Unit.INSTANCE;
    }
}
