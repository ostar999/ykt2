package com.yddmi.doctor.pages.exam;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.lxj.xpopup.core.CenterPopupView;
import com.yddmi.doctor.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u0011B\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0014J\b\u0010\r\u001a\u00020\u000eH\u0014J\u000e\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/yddmi/doctor/pages/exam/PopupExamMax;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "closeImgv", "Landroid/widget/ImageView;", "mListener", "Lcom/yddmi/doctor/pages/exam/PopupExamMax$OnPopupMaxClickListener;", "unlockTv", "Landroid/widget/TextView;", "getImplLayoutId", "", "onCreate", "", "setOnPopupMaxClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "OnPopupMaxClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupExamMax extends CenterPopupView {
    private ImageView closeImgv;

    @Nullable
    private OnPopupMaxClickListener mListener;
    private TextView unlockTv;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/pages/exam/PopupExamMax$OnPopupMaxClickListener;", "", "onViewClick", "", "view", "Landroid/view/View;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupMaxClickListener {
        void onViewClick(@Nullable View view);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupExamMax(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopupExamMax this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PopupExamMax this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupMaxClickListener onPopupMaxClickListener = this$0.mListener;
        if (onPopupMaxClickListener != null) {
            TextView textView = this$0.unlockTv;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("unlockTv");
                textView = null;
            }
            onPopupMaxClickListener.onViewClick(textView);
        }
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.exam_pop_max;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        ImageView imageView;
        TextView textView;
        super.onCreate();
        View viewFindViewById = findViewById(R.id.closeImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.closeImgv)");
        this.closeImgv = (ImageView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.unlockTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.unlockTv)");
        this.unlockTv = (TextView) viewFindViewById2;
        ImageView imageView2 = this.closeImgv;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("closeImgv");
            imageView = null;
        } else {
            imageView = imageView2;
        }
        ViewExtKt.setDebounceClickListener$default(imageView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.exam.v
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupExamMax.onCreate$lambda$0(this.f25772c, view);
            }
        }, 0L, 2, null);
        TextView textView2 = this.unlockTv;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("unlockTv");
            textView = null;
        } else {
            textView = textView2;
        }
        ViewExtKt.setDebounceClickListener$default(textView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.exam.w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupExamMax.onCreate$lambda$1(this.f25773c, view);
            }
        }, 0L, 2, null);
    }

    public final void setOnPopupMaxClickListener(@NotNull OnPopupMaxClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }
}
