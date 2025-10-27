package com.ykb.ebook.dialog;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.ykb.ebook.dialog.ReportReasonChooseDialog;
import com.ykb.ebook.model.ReportReason;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/model/ReportReason;", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
@DebugMetadata(c = "com.ykb.ebook.dialog.ReportReasonChooseDialog$Builder$commentReportReson$2", f = "ReportReasonChooseDialog.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
@SourceDebugExtension({"SMAP\nReportReasonChooseDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReportReasonChooseDialog.kt\ncom/ykb/ebook/dialog/ReportReasonChooseDialog$Builder$commentReportReson$2\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,210:1\n1855#2,2:211\n*S KotlinDebug\n*F\n+ 1 ReportReasonChooseDialog.kt\ncom/ykb/ebook/dialog/ReportReasonChooseDialog$Builder$commentReportReson$2\n*L\n98#1:211,2\n*E\n"})
/* loaded from: classes7.dex */
public final class ReportReasonChooseDialog$Builder$commentReportReson$2 extends SuspendLambda implements Function3<CoroutineScope, ReportReason, Continuation<? super Unit>, Object> {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ReportReasonChooseDialog.Builder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReportReasonChooseDialog$Builder$commentReportReson$2(ReportReasonChooseDialog.Builder builder, Continuation<? super ReportReasonChooseDialog$Builder$commentReportReson$2> continuation) {
        super(3, continuation);
        this.this$0 = builder;
    }

    @Override // kotlin.jvm.functions.Function3
    @Nullable
    public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull ReportReason reportReason, @Nullable Continuation<? super Unit> continuation) {
        ReportReasonChooseDialog$Builder$commentReportReson$2 reportReasonChooseDialog$Builder$commentReportReson$2 = new ReportReasonChooseDialog$Builder$commentReportReson$2(this.this$0, continuation);
        reportReasonChooseDialog$Builder$commentReportReson$2.L$0 = reportReason;
        return reportReasonChooseDialog$Builder$commentReportReson$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [T, java.util.ArrayList] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ReportReason reportReason = (ReportReason) this.L$0;
        if (!Intrinsics.areEqual(reportReason.getCode(), "200") || reportReason.getData() == null) {
            Toast.makeText(this.this$0.getContext(), reportReason.getMessage(), 0).show();
        } else {
            Ref.ObjectRef objectRef = new Ref.ObjectRef();
            objectRef.element = new ArrayList();
            ReportReasonChooseDialog.Builder builder = this.this$0;
            ArrayList<ReportReason.DataBean> data = reportReason.getData();
            Intrinsics.checkNotNullExpressionValue(data, "it.data");
            builder.resonList = data;
            ArrayList<ReportReason.DataBean> data2 = reportReason.getData();
            Intrinsics.checkNotNullExpressionValue(data2, "it.data");
            Iterator<T> it = data2.iterator();
            while (it.hasNext()) {
                ((ArrayList) objectRef.element).add(((ReportReason.DataBean) it.next()).getTitle());
            }
            this.this$0.mAdapter.submitList((List) objectRef.element);
            LinearLayout reportLl = this.this$0.getReportLl();
            if (reportLl != null) {
                reportLl.setVisibility(0);
            }
            TextView tvTitlte = this.this$0.getTvTitlte();
            if (tvTitlte != null) {
                tvTitlte.setVisibility(8);
            }
        }
        return Unit.INSTANCE;
    }
}
