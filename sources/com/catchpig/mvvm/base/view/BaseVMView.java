package com.catchpig.mvvm.base.view;

import android.view.View;
import androidx.exifinterface.media.ExifInterface;
import com.alipay.sdk.authjs.a;
import com.catchpig.mvvm.base.view.BaseView;
import com.catchpig.mvvm.widget.refresh.RefreshRecyclerView;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0003H&J\\\u0010\u0006\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00070\t2%\b\u0002\u0010\n\u001a\u001f\u0012\u0013\u0012\u00110\f¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u000b2\u0017\u0010\u0010\u001a\u0013\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u00020\u00030\u000b¢\u0006\u0002\b\u0011H'J\\\u0010\u0012\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00070\t2%\b\u0002\u0010\n\u001a\u001f\u0012\u0013\u0012\u00110\f¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u000b2\u0017\u0010\u0010\u001a\u0013\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u00020\u00030\u000b¢\u0006\u0002\b\u0011H'J\\\u0010\u0013\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00070\t2%\b\u0002\u0010\n\u001a\u001f\u0012\u0013\u0012\u00110\f¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u000b2\u0017\u0010\u0010\u001a\u0013\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u00020\u00030\u000b¢\u0006\u0002\b\u0011H'J*\u0010\u0014\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u00072\u0012\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\u00150\t2\u0006\u0010\u0016\u001a\u00020\u0017H'¨\u0006\u0018"}, d2 = {"Lcom/catchpig/mvvm/base/view/BaseVMView;", "Lcom/catchpig/mvvm/base/view/BaseView;", "initFlow", "", "initParam", "initView", "lifecycleFlow", ExifInterface.GPS_DIRECTION_TRUE, "flow", "Lkotlinx/coroutines/flow/Flow;", "errorCallback", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "t", a.f3170c, "Lkotlin/ExtensionFunctionType;", "lifecycleFlowLoadingDialog", "lifecycleFlowLoadingView", "lifecycleFlowRefresh", "", "refresh", "Lcom/catchpig/mvvm/widget/refresh/RefreshRecyclerView;", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface BaseVMView extends BaseView {

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class DefaultImpls {
        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ void lifecycleFlow$default(BaseVMView baseVMView, Flow flow, Function1 function1, Function1 function12, int i2, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: lifecycleFlow");
            }
            if ((i2 & 2) != 0) {
                function1 = null;
            }
            baseVMView.lifecycleFlow(flow, function1, function12);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ void lifecycleFlowLoadingDialog$default(BaseVMView baseVMView, Flow flow, Function1 function1, Function1 function12, int i2, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: lifecycleFlowLoadingDialog");
            }
            if ((i2 & 2) != 0) {
                function1 = null;
            }
            baseVMView.lifecycleFlowLoadingDialog(flow, function1, function12);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ void lifecycleFlowLoadingView$default(BaseVMView baseVMView, Flow flow, Function1 function1, Function1 function12, int i2, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: lifecycleFlowLoadingView");
            }
            if ((i2 & 2) != 0) {
                function1 = null;
            }
            baseVMView.lifecycleFlowLoadingView(flow, function1, function12);
        }

        public static void onFailedReload(@NotNull BaseVMView baseVMView, boolean z2, @NotNull Function1<? super View, Unit> block) {
            Intrinsics.checkNotNullParameter(block, "block");
            BaseView.DefaultImpls.onFailedReload(baseVMView, z2, block);
        }
    }

    void initFlow();

    void initParam();

    void initView();

    @Deprecated(message = "")
    <T> void lifecycleFlow(@NotNull Flow<? extends T> flow, @Nullable Function1<? super Throwable, Unit> errorCallback, @NotNull Function1<? super T, Unit> callback);

    @Deprecated(message = "")
    <T> void lifecycleFlowLoadingDialog(@NotNull Flow<? extends T> flow, @Nullable Function1<? super Throwable, Unit> errorCallback, @NotNull Function1<? super T, Unit> callback);

    @Deprecated(message = "")
    <T> void lifecycleFlowLoadingView(@NotNull Flow<? extends T> flow, @Nullable Function1<? super Throwable, Unit> errorCallback, @NotNull Function1<? super T, Unit> callback);

    @Deprecated(message = "")
    <T> void lifecycleFlowRefresh(@NotNull Flow<? extends List<T>> flow, @NotNull RefreshRecyclerView refresh);
}
