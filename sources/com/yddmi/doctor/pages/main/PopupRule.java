package com.yddmi.doctor.pages.main;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.utils.ext.LogExtKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.lxj.xpopup.impl.FullScreenPopupView;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddPointManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0001\u0017B\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000f\u001a\u00020\u0010H\u0014J\b\u0010\u0011\u001a\u00020\u0012H\u0014J\u000e\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u0006J\u000e\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/yddmi/doctor/pages/main/PopupRule;", "Lcom/lxj/xpopup/impl/FullScreenPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "mData", "", "mListener", "Lcom/yddmi/doctor/pages/main/PopupRule$OnPopupRuleClickListener;", "ruleTv", "Landroid/widget/TextView;", "scrollV", "Landroid/widget/ScrollView;", "webImgv", "Landroid/widget/ImageView;", "getImplLayoutId", "", "onCreate", "", PLVRxEncryptDataFunction.SET_DATA_METHOD, "data", "setOnPopupRuleClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "OnPopupRuleClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupRule extends FullScreenPopupView {

    @NotNull
    private String mData;

    @Nullable
    private OnPopupRuleClickListener mListener;
    private TextView ruleTv;
    private ScrollView scrollV;
    private ImageView webImgv;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/pages/main/PopupRule$OnPopupRuleClickListener;", "", "onClick", "", "view", "Landroid/view/View;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupRuleClickListener {
        void onClick(@Nullable View view);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupRule(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mData = "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopupRule this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        LogExtKt.logd("x 点击监听", YddConfig.TAG);
        YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-FREEGET-RETURN", "免费领取-返回", null, 4, null);
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PopupRule this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
        OnPopupRuleClickListener onPopupRuleClickListener = this$0.mListener;
        if (onPopupRuleClickListener != null) {
            onPopupRuleClickListener.onClick(view);
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.main_popup_rule;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        View viewFindViewById = findViewById(R.id.scrollV);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.scrollV)");
        this.scrollV = (ScrollView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.ruleTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.ruleTv)");
        this.ruleTv = (TextView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.webImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.webImgv)");
        this.webImgv = (ImageView) viewFindViewById3;
        TextView textView = this.ruleTv;
        ScrollView scrollView = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ruleTv");
            textView = null;
        }
        textView.setText(this.mData);
        ScrollView scrollView2 = this.scrollV;
        if (scrollView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scrollV");
        } else {
            scrollView = scrollView2;
        }
        scrollView.setOnTouchListener(new View.OnTouchListener() { // from class: com.yddmi.doctor.pages.main.PopupRule.onCreate.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(@Nullable View v2, @Nullable MotionEvent event) {
                if (event == null) {
                    return false;
                }
                PopupRule popupRule = PopupRule.this;
                if (event.getAction() != 1) {
                    return false;
                }
                ImageView imageView = popupRule.webImgv;
                if (imageView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("webImgv");
                    imageView = null;
                }
                imageView.setVisibility(4);
                return false;
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.xImgv);
        if (imageView != null) {
            ViewExtKt.setDebounceClickListener$default(imageView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.j0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PopupRule.onCreate$lambda$0(this.f25932c, view);
                }
            }, 0L, 2, null);
        }
        TextView textView2 = (TextView) findViewById(R.id.knowTv);
        if (textView2 != null) {
            ViewExtKt.setDebounceClickListener$default(textView2, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.k0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PopupRule.onCreate$lambda$1(this.f25934c, view);
                }
            }, 0L, 2, null);
        }
    }

    @NotNull
    public final PopupRule setData(@NotNull String data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.mData = data;
        return this;
    }

    public final void setOnPopupRuleClickListener(@NotNull OnPopupRuleClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }
}
