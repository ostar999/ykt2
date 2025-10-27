package com.ykb.ebook.dialog;

import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.ykb.ebook.base.BaseResponse;
import com.ykb.ebook.dialog.PermissionDialog;
import com.ykb.ebook.model.PermissionInfo;
import com.ykb.ebook.model.Ways;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\u00020\u00002\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/model/PermissionInfo;", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
@DebugMetadata(c = "com.ykb.ebook.dialog.PermissionDialog$Builder$loadData$2", f = "PermissionDialog.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes7.dex */
public final class PermissionDialog$Builder$loadData$2 extends SuspendLambda implements Function3<CoroutineScope, BaseResponse<PermissionInfo>, Continuation<? super Unit>, Object> {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ PermissionDialog.Builder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PermissionDialog$Builder$loadData$2(PermissionDialog.Builder builder, Continuation<? super PermissionDialog$Builder$loadData$2> continuation) {
        super(3, continuation);
        this.this$0 = builder;
    }

    @Override // kotlin.jvm.functions.Function3
    @Nullable
    public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull BaseResponse<PermissionInfo> baseResponse, @Nullable Continuation<? super Unit> continuation) {
        PermissionDialog$Builder$loadData$2 permissionDialog$Builder$loadData$2 = new PermissionDialog$Builder$loadData$2(this.this$0, continuation);
        permissionDialog$Builder$loadData$2.L$0 = baseResponse;
        return permissionDialog$Builder$loadData$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        PermissionInfo permissionInfo = (PermissionInfo) ((BaseResponse) this.L$0).getData();
        if (permissionInfo != null) {
            PermissionDialog.Builder builder = this.this$0;
            List<Ways> ways = permissionInfo.getWays();
            if (!(ways == null || ways.isEmpty())) {
                builder.mAdapter.submitList(permissionInfo.getWays());
            }
        }
        return Unit.INSTANCE;
    }
}
