package android.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import androidx.annotation.AnyRes;
import androidx.annotation.AttrRes;
import com.alipay.sdk.sys.a;
import com.umeng.analytics.pro.am;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt__CharJVMKt;
import kotlin.text.StringsKt__StringsKt;
import kotlin.text.StringsKt___StringsKt;
import org.jetbrains.annotations.NotNull;
import splitties.experimental.InternalSplittiesApi;
import splitties.mainthread.MainThreadKt;

@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a \u0010\u0006\u001a\u00020\u0007*\u00020\b2\b\b\u0001\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007\u001a\u0014\u0010\f\u001a\u00020\r*\u00020\u00012\u0006\u0010\u000e\u001a\u00020\rH\u0000\u001aJ\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010*\u00020\b2\b\b\u0001\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u000b2\u0019\b\u0004\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u0002H\u00100\u0012¢\u0006\u0002\b\u0013H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0014\"\u0016\u0010\u0000\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0002\u0010\u0003\"\u0016\u0010\u0004\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0003\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0015"}, d2 = {"cachedTypeValue", "Landroid/util/TypedValue;", "getCachedTypeValue$annotations", "()V", "uiThreadConfinedCachedTypedValue", "getUiThreadConfinedCachedTypedValue$annotations", "resolveThemeAttribute", "", "Landroid/content/Context;", "attrRes", "resolveRefs", "", "unexpectedThemeAttributeTypeErrorMessage", "", "expectedKind", "withResolvedThemeAttribute", "R", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Landroid/content/Context;IZLkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "splitties-resources_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class StyledAttributesKt {

    @JvmField
    @NotNull
    public static final TypedValue uiThreadConfinedCachedTypedValue = new TypedValue();

    @JvmField
    @NotNull
    public static final TypedValue cachedTypeValue = new TypedValue();

    @PublishedApi
    public static /* synthetic */ void getCachedTypeValue$annotations() {
    }

    @PublishedApi
    public static /* synthetic */ void getUiThreadConfinedCachedTypedValue$annotations() {
    }

    @AnyRes
    public static final int resolveThemeAttribute(@NotNull Context context, @AttrRes int i2, boolean z2) {
        int i3;
        Intrinsics.checkNotNullParameter(context, "<this>");
        if (MainThreadKt.mainThread == Thread.currentThread()) {
            Resources.Theme theme = context.getTheme();
            TypedValue typedValue = uiThreadConfinedCachedTypedValue;
            if (theme.resolveAttribute(i2, typedValue, z2)) {
                return typedValue.resourceId;
            }
            throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
        }
        TypedValue typedValue2 = cachedTypeValue;
        synchronized (typedValue2) {
            if (!context.getTheme().resolveAttribute(i2, typedValue2, z2)) {
                throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
            }
            i3 = typedValue2.resourceId;
        }
        return i3;
    }

    public static /* synthetic */ int resolveThemeAttribute$default(Context context, int i2, boolean z2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            z2 = true;
        }
        return resolveThemeAttribute(context, i2, z2);
    }

    @NotNull
    public static final String unexpectedThemeAttributeTypeErrorMessage(@NotNull TypedValue typedValue, @NotNull String expectedKind) {
        Intrinsics.checkNotNullParameter(typedValue, "<this>");
        Intrinsics.checkNotNullParameter(expectedKind, "expectedKind");
        Character chFirstOrNull = StringsKt___StringsKt.firstOrNull(expectedKind);
        String str = StringsKt__StringsKt.contains$default((CharSequence) "aeio", chFirstOrNull == null ? ' ' : chFirstOrNull.charValue(), false, 2, (Object) null) ? a.f3324i : am.av;
        StringBuilder sb = new StringBuilder();
        sb.append("Expected ");
        sb.append(str);
        sb.append(' ');
        sb.append(expectedKind);
        sb.append(" theme attribute but got type 0x");
        String string = Integer.toString(typedValue.type, CharsKt__CharJVMKt.checkRadix(16));
        Intrinsics.checkNotNullExpressionValue(string, "java.lang.Integer.toStri…(this, checkRadix(radix))");
        sb.append(string);
        sb.append(" (see what it corresponds to in android.util.TypedValue constants)");
        return sb.toString();
    }

    @InternalSplittiesApi
    public static final <R> R withResolvedThemeAttribute(@NotNull Context context, @AttrRes int i2, boolean z2, @NotNull Function1<? super TypedValue, ? extends R> block) {
        R rInvoke;
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        if (MainThreadKt.mainThread == Thread.currentThread()) {
            Resources.Theme theme = context.getTheme();
            TypedValue typedValue = uiThreadConfinedCachedTypedValue;
            if (theme.resolveAttribute(i2, typedValue, z2)) {
                return block.invoke(typedValue);
            }
            throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
        }
        TypedValue typedValue2 = cachedTypeValue;
        synchronized (typedValue2) {
            try {
                if (!context.getTheme().resolveAttribute(i2, typedValue2, z2)) {
                    throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
                }
                rInvoke = block.invoke(typedValue2);
                InlineMarker.finallyStart(1);
            } catch (Throwable th) {
                InlineMarker.finallyStart(1);
                InlineMarker.finallyEnd(1);
                throw th;
            }
        }
        InlineMarker.finallyEnd(1);
        return rInvoke;
    }

    public static /* synthetic */ Object withResolvedThemeAttribute$default(Context context, int i2, boolean z2, Function1 block, int i3, Object obj) {
        Object objInvoke;
        if ((i3 & 2) != 0) {
            z2 = true;
        }
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        if (MainThreadKt.mainThread == Thread.currentThread()) {
            Resources.Theme theme = context.getTheme();
            TypedValue typedValue = uiThreadConfinedCachedTypedValue;
            if (theme.resolveAttribute(i2, typedValue, z2)) {
                return block.invoke(typedValue);
            }
            throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
        }
        TypedValue typedValue2 = cachedTypeValue;
        synchronized (typedValue2) {
            try {
                if (!context.getTheme().resolveAttribute(i2, typedValue2, z2)) {
                    throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
                }
                objInvoke = block.invoke(typedValue2);
                InlineMarker.finallyStart(1);
            } catch (Throwable th) {
                InlineMarker.finallyStart(1);
                InlineMarker.finallyEnd(1);
                throw th;
            }
        }
        InlineMarker.finallyEnd(1);
        return objInvoke;
    }
}
