package com.yddmi.doctor.pages.exam;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.lxj.xpopup.core.CenterPopupView;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.yddmi.doctor.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0014J\b\u0010\r\u001a\u00020\u000eH\u0014J\u000e\u0010\u000f\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/yddmi/doctor/pages/exam/PopupExamRule;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "closeImgv", "Landroid/widget/ImageView;", "mData", "", "tipTv", "Landroid/widget/TextView;", "getImplLayoutId", "", "onCreate", "", PLVRxEncryptDataFunction.SET_DATA_METHOD, "data", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupExamRule extends CenterPopupView {
    private ImageView closeImgv;

    @NotNull
    private String mData;
    private TextView tipTv;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupExamRule(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mData = "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopupExamRule this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.exam_pop_rule;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        ImageView imageView;
        super.onCreate();
        View viewFindViewById = findViewById(R.id.tipTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.tipTv)");
        this.tipTv = (TextView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.closeImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.closeImgv)");
        ImageView imageView2 = (ImageView) viewFindViewById2;
        this.closeImgv = imageView2;
        TextView textView = null;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("closeImgv");
            imageView = null;
        } else {
            imageView = imageView2;
        }
        ViewExtKt.setDebounceClickListener$default(imageView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.exam.x
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupExamRule.onCreate$lambda$0(this.f25774c, view);
            }
        }, 0L, 2, null);
        if (StringsKt__StringsKt.indexOf$default((CharSequence) this.mData, TtmlNode.TAG_STYLE, 0, false, 6, (Object) null) > 0) {
            TextView textView2 = this.tipTv;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tipTv");
            } else {
                textView = textView2;
            }
            textView.setText(Html.fromHtml(this.mData));
            return;
        }
        TextView textView3 = this.tipTv;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tipTv");
        } else {
            textView = textView3;
        }
        textView.setText(this.mData);
    }

    @NotNull
    public final PopupExamRule setData(@NotNull String data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.mData = data;
        return this;
    }
}
