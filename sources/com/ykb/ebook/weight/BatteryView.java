package com.ykb.ebook.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.style.LineHeightSpan;
import android.util.AttributeSet;
import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatTextView;
import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import com.umeng.analytics.pro.am;
import com.ykb.ebook.extensions.ConvertExtensionsKt;
import com.ykb.ebook.page.canvans_recorder.CanvasRecorder;
import com.ykb.ebook.page.canvans_recorder.CanvasRecorderFactory;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002J\u0014\u0010#\u001a\u0004\u0018\u00010$2\b\u0010%\u001a\u0004\u0018\u00010&H\u0002J\b\u0010'\u001a\u00020 H\u0016J\u0010\u0010(\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0014J0\u0010)\u001a\u00020 2\u0006\u0010*\u001a\u00020\u00172\u0006\u0010+\u001a\u00020\b2\u0006\u0010,\u001a\u00020\b2\u0006\u0010-\u001a\u00020\b2\u0006\u0010.\u001a\u00020\bH\u0014J\u001c\u0010\u001a\u001a\u00020 2\u0006\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010%\u001a\u0004\u0018\u00010/H\u0007J\u0010\u00100\u001a\u00020 2\b\b\u0001\u00101\u001a\u00020\bJ\u0012\u00102\u001a\u00020 2\b\u00103\u001a\u0004\u0018\u00010\u000eH\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R#\u0010\r\u001a\n \u000f*\u0004\u0018\u00010\u000e0\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u0017@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00064"}, d2 = {"Lcom/ykb/ebook/weight/BatteryView;", "Landroidx/appcompat/widget/AppCompatTextView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", am.Z, "", "batteryPaint", "Landroid/graphics/Paint;", "batterySpan", "Landroid/text/style/LineHeightSpan;", "batteryTypeface", "Landroid/graphics/Typeface;", "kotlin.jvm.PlatformType", "getBatteryTypeface", "()Landroid/graphics/Typeface;", "batteryTypeface$delegate", "Lkotlin/Lazy;", "canvasRecorder", "Lcom/ykb/ebook/page/canvans_recorder/CanvasRecorder;", "value", "", "isBattery", "()Z", "setBattery", "(Z)V", "outFrame", "Landroid/graphics/Rect;", "polar", "drawBattery", "", "canvas", "Landroid/graphics/Canvas;", "getBatteryText", "Landroid/text/SpannableStringBuilder;", "text", "", "invalidate", "onDraw", "onLayout", "changed", "left", PLVDanmakuInfo.FONTMODE_TOP, "right", PLVDanmakuInfo.FONTMODE_BOTTOM, "", "setColor", "color", "setTypeface", "tf", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nBatteryView.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BatteryView.kt\ncom/ykb/ebook/weight/BatteryView\n+ 2 CanvasRecorderExtensions.kt\ncom/ykb/ebook/extensions/CanvasRecorderExtensionsKt\n+ 3 Canvas.kt\nandroidx/core/graphics/CanvasKt\n*L\n1#1,138:1\n34#2:139\n12#2,9:140\n21#2,2:152\n24#2,3:157\n14#2:160\n35#2,2:161\n30#3,3:149\n34#3,3:154\n*S KotlinDebug\n*F\n+ 1 BatteryView.kt\ncom/ykb/ebook/weight/BatteryView\n*L\n88#1:139\n88#1:140,9\n88#1:152,2\n88#1:157,3\n88#1:160\n88#1:161,2\n88#1:149,3\n88#1:154,3\n*E\n"})
/* loaded from: classes8.dex */
public final class BatteryView extends AppCompatTextView {
    private int battery;

    @NotNull
    private final Paint batteryPaint;

    @NotNull
    private final LineHeightSpan batterySpan;

    /* renamed from: batteryTypeface$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy batteryTypeface;

    @NotNull
    private final CanvasRecorder canvasRecorder;
    private boolean isBattery;

    @NotNull
    private final Rect outFrame;

    @NotNull
    private final Rect polar;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public BatteryView(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ BatteryView(Context context, AttributeSet attributeSet, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void batterySpan$lambda$0(CharSequence charSequence, int i2, int i3, int i4, int i5, Paint.FontMetricsInt fontMetricsInt) {
        fontMetricsInt.top = -22;
        fontMetricsInt.ascent = -28;
        fontMetricsInt.descent = 7;
        fontMetricsInt.bottom = 1;
        fontMetricsInt.leading = 0;
    }

    private final void drawBattery(Canvas canvas) {
        getLayout().getLineBounds(0, this.outFrame);
        int primaryHorizontal = ((int) getLayout().getPrimaryHorizontal(getText().length() - String.valueOf(this.battery).length())) + ConvertExtensionsKt.dpToPx(2);
        int desiredWidth = ((int) Layout.getDesiredWidth(String.valueOf(this.battery), getPaint())) + primaryHorizontal + ConvertExtensionsKt.dpToPx(4);
        this.outFrame.set(primaryHorizontal, ConvertExtensionsKt.dpToPx(2), desiredWidth, getHeight() - ConvertExtensionsKt.dpToPx(2));
        Rect rect = this.outFrame;
        int i2 = rect.bottom;
        int i3 = rect.top;
        int i4 = (i2 - i3) / 3;
        this.polar.set(desiredWidth, i3 + i4, ConvertExtensionsKt.dpToPx(2) + desiredWidth, this.outFrame.bottom - i4);
        this.batteryPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(this.outFrame, this.batteryPaint);
        this.batteryPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(this.polar, this.batteryPaint);
    }

    private final SpannableStringBuilder getBatteryText(CharSequence text) {
        if (text == null) {
            return null;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        spannableStringBuilder.setSpan(this.batterySpan, 0, text.length(), 33);
        return spannableStringBuilder;
    }

    private final Typeface getBatteryTypeface() {
        return (Typeface) this.batteryTypeface.getValue();
    }

    public static /* synthetic */ void setBattery$default(BatteryView batteryView, int i2, String str, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            str = null;
        }
        batteryView.setBattery(i2, str);
    }

    @Override // android.view.View
    public void invalidate() {
        super.invalidate();
        try {
            Result.Companion companion = Result.INSTANCE;
            this.canvasRecorder.invalidate();
            Result.m783constructorimpl(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            Result.m783constructorimpl(ResultKt.createFailure(th));
        }
    }

    /* renamed from: isBattery, reason: from getter */
    public final boolean getIsBattery() {
        return this.isBattery;
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        CanvasRecorder canvasRecorder = this.canvasRecorder;
        int width = getWidth();
        int height = getHeight();
        if (canvasRecorder.needRecord()) {
            try {
                Canvas canvasBeginRecording = canvasRecorder.beginRecording(width, height);
                int iSave = canvasBeginRecording.save();
                try {
                    super.onDraw(canvasBeginRecording);
                    if (this.isBattery) {
                        drawBattery(canvasBeginRecording);
                    }
                } finally {
                    canvasBeginRecording.restoreToCount(iSave);
                }
            } finally {
                canvasRecorder.endRecording();
            }
        }
        canvasRecorder.draw(canvas);
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void onLayout(boolean changed, int left, int top2, int right, int bottom) {
        super.onLayout(changed, left, top2, right, bottom);
        this.canvasRecorder.invalidate();
    }

    public final void setBattery(boolean z2) {
        this.isBattery = z2;
        if (!z2 || isInEditMode()) {
            return;
        }
        super.setTypeface(getBatteryTypeface());
        postInvalidate();
    }

    public final void setColor(@ColorInt int color) {
        setTextColor(color);
        this.batteryPaint.setColor(color);
        invalidate();
    }

    @Override // android.widget.TextView
    public void setTypeface(@Nullable Typeface tf) {
        if (this.isBattery) {
            return;
        }
        super.setTypeface(tf);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public BatteryView(@NotNull final Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.batteryTypeface = LazyKt__LazyJVMKt.lazy(new Function0<Typeface>() { // from class: com.ykb.ebook.weight.BatteryView$batteryTypeface$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Typeface invoke() {
                return Typeface.createFromAsset(context.getAssets(), "font/number.ttf");
            }
        });
        Paint paint = new Paint();
        this.batteryPaint = paint;
        this.outFrame = new Rect();
        this.polar = new Rect();
        this.canvasRecorder = CanvasRecorderFactory.create$default(CanvasRecorderFactory.INSTANCE, false, 1, null);
        this.batterySpan = new LineHeightSpan() { // from class: com.ykb.ebook.weight.a
            @Override // android.text.style.LineHeightSpan
            public final void chooseHeight(CharSequence charSequence, int i2, int i3, int i4, int i5, Paint.FontMetricsInt fontMetricsInt) {
                BatteryView.batterySpan$lambda$0(charSequence, i2, i3, i4, i5, fontMetricsInt);
            }
        };
        setPadding(0, ConvertExtensionsKt.dpToPx(3), ConvertExtensionsKt.dpToPx(6), ConvertExtensionsKt.dpToPx(3));
        paint.setStrokeWidth(ConvertExtensionsKt.dpToPx(1.0f));
        paint.setAntiAlias(true);
        paint.setColor(getPaint().getColor());
    }

    @SuppressLint({"SetTextI18n"})
    public final void setBattery(int battery, @Nullable String text) {
        this.battery = battery;
        if (text == null || text.length() == 0) {
            setText(getBatteryText(String.valueOf(battery)));
            return;
        }
        setText(getBatteryText(text + "  " + battery));
    }
}
