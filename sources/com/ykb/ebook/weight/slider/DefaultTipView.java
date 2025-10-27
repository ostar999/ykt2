package com.ykb.ebook.weight.slider;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.ykb.ebook.R;
import com.ykb.ebook.extensions.ConvertExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\r\n\u0002\b\u0002\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\u000e2\b\b\u0001\u0010\u0010\u001a\u00020\u0007J\u000e\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013J\u0010\u0010\u0014\u001a\u00020\u000e2\b\b\u0001\u0010\u0010\u001a\u00020\u0007R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/ykb/ebook/weight/slider/DefaultTipView;", "Landroidx/constraintlayout/widget/ConstraintLayout;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "arrowView", "Lcom/ykb/ebook/weight/slider/ArrowView;", "tipTextView", "Landroid/widget/TextView;", "initView", "", "setTipBackground", "color", "setTipText", "text", "", "setTipTextColor", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class DefaultTipView extends ConstraintLayout {
    private ArrowView arrowView;
    private TextView tipTextView;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public DefaultTipView(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public DefaultTipView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ DefaultTipView(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }

    private final void initView() {
        View viewFindViewById = findViewById(R.id.tip_text);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.tip_text)");
        this.tipTextView = (TextView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.arrow_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.arrow_view)");
        this.arrowView = (ArrowView) viewFindViewById2;
        TextView textView = this.tipTextView;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tipTextView");
            textView = null;
        }
        setTipBackground(-1);
        textView.setTextColor(-16777216);
    }

    public final void setTipBackground(@ColorInt int color) {
        TextView textView = this.tipTextView;
        ArrowView arrowView = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tipTextView");
            textView = null;
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(ConvertExtensionsKt.dpToPx(18));
        gradientDrawable.setColor(color);
        textView.setBackground(gradientDrawable);
        ArrowView arrowView2 = this.arrowView;
        if (arrowView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("arrowView");
        } else {
            arrowView = arrowView2;
        }
        arrowView.setArrowColor(color);
    }

    public final void setTipText(@NotNull CharSequence text) {
        Intrinsics.checkNotNullParameter(text, "text");
        TextView textView = this.tipTextView;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tipTextView");
            textView = null;
        }
        textView.setText(text);
    }

    public final void setTipTextColor(@ColorInt int color) {
        TextView textView = this.tipTextView;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tipTextView");
            textView = null;
        }
        textView.setTextColor(color);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public DefaultTipView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        View.inflate(context, R.layout.layout_default_tip_view, this);
        initView();
    }
}
