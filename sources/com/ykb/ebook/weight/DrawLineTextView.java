package com.ykb.ebook.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.ykb.ebook.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B\u001f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u001a\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u0010\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0015R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/ykb/ebook/weight/DrawLineTextView;", "Landroidx/appcompat/widget/AppCompatTextView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "drawType", "lineColor", "textBackground", "init", "", "onDraw", "canvas", "Landroid/graphics/Canvas;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class DrawLineTextView extends AppCompatTextView {
    private int drawType;
    private int lineColor;
    private int textBackground;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DrawLineTextView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.lineColor = Color.parseColor("#78aeff");
        this.textBackground = Color.parseColor("#2678AEFF");
        init(context, null);
    }

    private final void init(Context context, AttributeSet attrs) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.DrawLineTextView);
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "context.obtainStyledAttr…yleable.DrawLineTextView)");
        this.lineColor = typedArrayObtainStyledAttributes.getColor(R.styleable.DrawLineTextView_line_color, this.lineColor);
        this.textBackground = typedArrayObtainStyledAttributes.getColor(R.styleable.DrawLineTextView_text_background, this.textBackground);
        this.drawType = typedArrayObtainStyledAttributes.getInteger(R.styleable.DrawLineTextView_draw_type, this.drawType);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.widget.TextView, android.view.View
    @SuppressLint({"DrawAllocation"})
    public void onDraw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        int i2 = this.drawType;
        if (i2 != 0) {
            if (i2 != 1) {
                return;
            }
            canvas.drawColor(this.textBackground);
        } else {
            Paint paint = new Paint();
            paint.setColor(this.lineColor);
            paint.setFlags(8);
            canvas.drawLine(0.0f, getHeight() - getPaddingBottom(), getWidth(), getHeight() - getPaddingBottom(), paint);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DrawLineTextView(@NotNull Context context, @NotNull AttributeSet attrs) {
        super(context, attrs);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attrs, "attrs");
        this.lineColor = Color.parseColor("#78aeff");
        this.textBackground = Color.parseColor("#2678AEFF");
        init(context, attrs);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DrawLineTextView(@NotNull Context context, @NotNull AttributeSet attrs, int i2) {
        super(context, attrs, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attrs, "attrs");
        this.lineColor = Color.parseColor("#78aeff");
        this.textBackground = Color.parseColor("#2678AEFF");
        init(context, attrs);
    }
}
