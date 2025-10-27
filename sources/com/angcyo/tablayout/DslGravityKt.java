package com.angcyo.tablayout;

import android.graphics.Rect;
import android.graphics.RectF;
import android.view.Gravity;
import com.luck.picture.lib.config.CustomIntentKey;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\u001a\u0089\u0001\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052K\u0010\u000b\u001aG\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00010\fH\u0002\u001a\u0087\u0001\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052K\u0010\u000b\u001aG\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00010\f\u001a\u0087\u0001\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00142\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052K\u0010\u000b\u001aG\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00010\f\u001a\n\u0010\u0015\u001a\u00020\u0016*\u00020\u0005\u001a\n\u0010\u0017\u001a\u00020\u0016*\u00020\u0005\u001a\n\u0010\u0018\u001a\u00020\u0016*\u00020\u0005\u001a\n\u0010\u0019\u001a\u00020\u0016*\u00020\u0005\u001a\n\u0010\u001a\u001a\u00020\u0016*\u00020\u0005¨\u0006\u001b"}, d2 = {"_config", "", "_dslGravity", "Lcom/angcyo/tablayout/DslGravity;", "gravity", "", "width", "", "height", CustomIntentKey.EXTRA_OFFSET_X, CustomIntentKey.EXTRA_OFFSET_Y, com.alipay.sdk.authjs.a.f3170c, "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "dslGravity", "centerX", "centerY", "rect", "Landroid/graphics/Rect;", "Landroid/graphics/RectF;", "isBottom", "", "isCenter", "isLeft", "isRight", "isTop", "TabLayout_release"}, k = 2, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class DslGravityKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final void _config(final DslGravity dslGravity, int i2, float f2, float f3, int i3, int i4, final Function3<? super DslGravity, ? super Integer, ? super Integer, Unit> function3) {
        dslGravity.setGravity(i2);
        dslGravity.setGravityOffsetX(i3);
        dslGravity.setGravityOffsetY(i4);
        dslGravity.applyGravity(f2, f3, new Function2<Integer, Integer, Unit>() { // from class: com.angcyo.tablayout.DslGravityKt._config.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(Integer num, Integer num2) {
                invoke(num.intValue(), num2.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i5, int i6) {
                function3.invoke(dslGravity, Integer.valueOf(i5), Integer.valueOf(i6));
            }
        });
    }

    @NotNull
    public static final DslGravity dslGravity(@NotNull RectF rect, int i2, float f2, float f3, int i3, int i4, @NotNull Function3<? super DslGravity, ? super Integer, ? super Integer, Unit> callback) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        Intrinsics.checkNotNullParameter(callback, "callback");
        DslGravity dslGravity = new DslGravity();
        dslGravity.setGravityBounds(rect);
        _config(dslGravity, i2, f2, f3, i3, i4, callback);
        return dslGravity;
    }

    public static final boolean isBottom(int i2) {
        return (i2 & 112) == 80;
    }

    public static final boolean isCenter(int i2) {
        return (i2 & 112) == 16 && (Gravity.getAbsoluteGravity(i2, 0) & 7) == 1;
    }

    public static final boolean isLeft(int i2) {
        return (Gravity.getAbsoluteGravity(i2, 0) & 7) == 3;
    }

    public static final boolean isRight(int i2) {
        return (Gravity.getAbsoluteGravity(i2, 0) & 7) == 5;
    }

    public static final boolean isTop(int i2) {
        return (i2 & 112) == 48;
    }

    @NotNull
    public static final DslGravity dslGravity(@NotNull Rect rect, int i2, float f2, float f3, int i3, int i4, @NotNull Function3<? super DslGravity, ? super Integer, ? super Integer, Unit> callback) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        Intrinsics.checkNotNullParameter(callback, "callback");
        DslGravity dslGravity = new DslGravity();
        dslGravity.setGravityBounds(rect);
        _config(dslGravity, i2, f2, f3, i3, i4, callback);
        return dslGravity;
    }
}
