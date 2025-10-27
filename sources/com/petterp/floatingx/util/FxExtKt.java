package com.petterp.floatingx.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.MotionEvent;
import androidx.exifinterface.media.ExifInterface;
import com.petterp.floatingx.assist.helper.ScopeHelper;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\\\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u0004\n\u0000\u001a3\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0011\"\u0004\b\u0000\u0010\u00122\u0019\b\u0004\u0010\u0013\u001a\u0013\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u0002H\u00120\u0014¢\u0006\u0002\b\u0016H\u0086\bø\u0001\u0000\u001a8\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0011\"\n\b\u0000\u0010\u0012\u0018\u0001*\u00020\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u001a2\u000e\b\u0004\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00120\u001bH\u0080\bø\u0001\u0000\u001a\u001c\u0010\u001c\u001a\u00020\n*\u00020\n2\u0006\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\nH\u0000\u001a\u000e\u0010\u001f\u001a\u0004\u0018\u00010 *\u00020!H\u0000\u001a\u001c\u0010\"\u001a\u00020#*\u00020\n2\u0006\u0010\u001d\u001a\u00020$2\u0006\u0010\u001e\u001a\u00020$H\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0003X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0003X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0003X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0003X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0003X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\nX\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000b\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u0018\u0010\f\u001a\u00020\u0003*\u00020\r8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006%"}, d2 = {"DEFAULT_MOVE_ANIMATOR_DURATION", "", "FX_GRAVITY_BOTTOM", "", "FX_GRAVITY_CENTER", "FX_GRAVITY_TOP", "INVALID_LAYOUT_ID", "INVALID_TOUCH_ID", "INVALID_TOUCH_IDX", "TOUCH_CLICK_OFFSET", "", "TOUCH_TIME_THRESHOLD", "pointerId", "Landroid/view/MotionEvent;", "getPointerId", "(Landroid/view/MotionEvent;)I", "createFx", "Lkotlin/Lazy;", ExifInterface.GPS_DIRECTION_TRUE, "obj", "Lkotlin/Function1;", "Lcom/petterp/floatingx/assist/helper/ScopeHelper$Builder;", "Lkotlin/ExtensionFunctionType;", "lazyLoad", "", "mode", "Lkotlin/LazyThreadSafetyMode;", "Lkotlin/Function0;", "coerceInFx", "min", "max", "findActivity", "Landroid/app/Activity;", "Landroid/content/Context;", "withIn", "", "", "floatingx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public final class FxExtKt {
    public static final long DEFAULT_MOVE_ANIMATOR_DURATION = 200;
    public static final /* synthetic */ int FX_GRAVITY_BOTTOM = 3;
    public static final /* synthetic */ int FX_GRAVITY_CENTER = 2;
    public static final /* synthetic */ int FX_GRAVITY_TOP = 1;
    public static final int INVALID_LAYOUT_ID = 0;
    public static final int INVALID_TOUCH_ID = -1;
    public static final int INVALID_TOUCH_IDX = -1;
    public static final float TOUCH_CLICK_OFFSET = 2.0f;
    public static final long TOUCH_TIME_THRESHOLD = 150;

    /* JADX INFO: Add missing generic type declarations: [T] */
    @Metadata(d1 = {"\u0000\b\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0002\u001a\u00028\u0000\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0000H\n"}, d2 = {"", ExifInterface.GPS_DIRECTION_TRUE, "<anonymous>"}, k = 3, mv = {1, 5, 1})
    /* renamed from: com.petterp.floatingx.util.FxExtKt$lazyLoad$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05581<T> extends Lambda implements Function0<T> {
        final /* synthetic */ Function0<T> $obj;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public C05581(Function0<? extends T> function0) {
            super(0);
            this.$obj = function0;
        }

        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final T invoke() {
            return this.$obj.invoke();
        }
    }

    public static final /* synthetic */ float coerceInFx(float f2, float f3, float f4) {
        return f2 < f3 ? f3 : f2 > f4 ? f4 : f2;
    }

    public static final /* synthetic */ <T> Lazy<T> createFx(final Function1<? super ScopeHelper.Builder, ? extends T> obj) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        return LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<T>() { // from class: com.petterp.floatingx.util.FxExtKt.createFx.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final T invoke() {
                return obj.invoke(new ScopeHelper.Builder());
            }
        });
    }

    public static final /* synthetic */ Activity findActivity(Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (!(context instanceof ContextWrapper)) {
            return null;
        }
        Context baseContext = ((ContextWrapper) context).getBaseContext();
        Intrinsics.checkNotNullExpressionValue(baseContext, "baseContext");
        return findActivity(baseContext);
    }

    public static final int getPointerId(@NotNull MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(motionEvent, "<this>");
        try {
            return motionEvent.getPointerId(motionEvent.getActionIndex());
        } catch (Exception unused) {
            return -1;
        }
    }

    public static final /* synthetic */ <T> Lazy<T> lazyLoad(LazyThreadSafetyMode mode, Function0<? extends T> obj) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.needClassReification();
        return LazyKt__LazyJVMKt.lazy(mode, (Function0) new C05581(obj));
    }

    public static /* synthetic */ Lazy lazyLoad$default(LazyThreadSafetyMode mode, Function0 obj, int i2, Object obj2) {
        if ((i2 & 1) != 0) {
            mode = LazyThreadSafetyMode.NONE;
        }
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.needClassReification();
        return LazyKt__LazyJVMKt.lazy(mode, (Function0) new C05581(obj));
    }

    public static final boolean withIn(float f2, @NotNull Number min, @NotNull Number max) {
        Intrinsics.checkNotNullParameter(min, "min");
        Intrinsics.checkNotNullParameter(max, "max");
        return f2 <= max.floatValue() && min.floatValue() <= f2;
    }
}
