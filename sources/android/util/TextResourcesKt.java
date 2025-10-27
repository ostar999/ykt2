package android.util;

import android.content.AppCtxKt;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.ArrayRes;
import androidx.annotation.AttrRes;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.fragment.app.Fragment;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.jetbrains.annotations.NotNull;
import splitties.mainthread.MainThreadKt;

@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\u001a\u001b\u0010\u0000\u001a\u00020\u00012\b\b\u0001\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0086\b\u001a8\u0010\u0000\u001a\u00020\u00012\b\b\u0001\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0016\u0010\u0005\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00070\u0006\"\u0004\u0018\u00010\u0007H\u0086\b¢\u0006\u0002\u0010\b\u001a\u001b\u0010\t\u001a\u00020\n2\b\b\u0001\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0086\b\u001a\u0013\u0010\u000b\u001a\u00020\u00012\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a0\u0010\u000b\u001a\u00020\u00012\b\b\u0001\u0010\u0002\u001a\u00020\u00032\u0016\u0010\u0005\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00070\u0006\"\u0004\u0018\u00010\u0007H\u0086\b¢\u0006\u0002\u0010\f\u001a\u001e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b¢\u0006\u0002\u0010\u000e\u001a\u0013\u0010\u000f\u001a\u00020\u00012\b\b\u0001\u0010\u0010\u001a\u00020\u0003H\u0086\b\u001a0\u0010\u000f\u001a\u00020\u00012\b\b\u0001\u0010\u0010\u001a\u00020\u00032\u0016\u0010\u0005\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00070\u0006\"\u0004\u0018\u00010\u0007H\u0086\b¢\u0006\u0002\u0010\f\u001a\u0013\u0010\u0011\u001a\u00020\n2\b\b\u0001\u0010\u0010\u001a\u00020\u0003H\u0086\b\u001a \u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u00062\b\b\u0001\u0010\u0010\u001a\u00020\u0003H\u0086\b¢\u0006\u0002\u0010\u0013\u001a\u0013\u0010\u0014\u001a\u00020\n2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a \u0010\u0015\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u00062\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b¢\u0006\u0002\u0010\u0013\u001a\f\u0010\u0016\u001a\u00020\u0017*\u00020\u0018H\u0002\u001a\u001f\u0010\u0019\u001a\u00020\u0001*\u00020\u001a2\b\b\u0001\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0086\b\u001a<\u0010\u0019\u001a\u00020\u0001*\u00020\u001a2\b\b\u0001\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0016\u0010\u0005\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00070\u0006\"\u0004\u0018\u00010\u0007H\u0086\b¢\u0006\u0002\u0010\u001b\u001a\u001f\u0010\u0019\u001a\u00020\u0001*\u00020\u001c2\b\b\u0001\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0086\b\u001a<\u0010\u0019\u001a\u00020\u0001*\u00020\u001c2\b\b\u0001\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0016\u0010\u0005\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00070\u0006\"\u0004\u0018\u00010\u0007H\u0086\b¢\u0006\u0002\u0010\u001d\u001a\u001f\u0010\u0019\u001a\u00020\u0001*\u00020\u001e2\b\b\u0001\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0086\b\u001a<\u0010\u0019\u001a\u00020\u0001*\u00020\u001e2\b\b\u0001\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0016\u0010\u0005\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00070\u0006\"\u0004\u0018\u00010\u0007H\u0086\b¢\u0006\u0002\u0010\u001f\u001a\u001f\u0010 \u001a\u00020\n*\u00020\u001a2\b\b\u0001\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0086\b\u001a\u001f\u0010 \u001a\u00020\n*\u00020\u001c2\b\b\u0001\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0086\b\u001a\u001f\u0010 \u001a\u00020\n*\u00020\u001e2\b\b\u0001\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010!\u001a\u00020\u0001*\u00020\u001a2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a4\u0010!\u001a\u00020\u0001*\u00020\u001a2\b\b\u0001\u0010\u0002\u001a\u00020\u00032\u0016\u0010\u0005\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00070\u0006\"\u0004\u0018\u00010\u0007H\u0086\b¢\u0006\u0002\u0010\"\u001a\u0017\u0010!\u001a\u00020\u0001*\u00020\u001c2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a4\u0010!\u001a\u00020\u0001*\u00020\u001c2\b\b\u0001\u0010\u0002\u001a\u00020\u00032\u0016\u0010\u0005\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00070\u0006\"\u0004\u0018\u00010\u0007H\u0086\b¢\u0006\u0002\u0010#\u001a\u0017\u0010!\u001a\u00020\u0001*\u00020\u001e2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a4\u0010!\u001a\u00020\u0001*\u00020\u001e2\b\b\u0001\u0010\u0002\u001a\u00020\u00032\u0016\u0010\u0005\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00070\u0006\"\u0004\u0018\u00010\u0007H\u0086\b¢\u0006\u0002\u0010$\u001a\"\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006*\u00020\u001a2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b¢\u0006\u0002\u0010&\u001a\"\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006*\u00020\u001c2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b¢\u0006\u0002\u0010'\u001a\"\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006*\u00020\u001e2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b¢\u0006\u0002\u0010(\u001a\u0014\u0010)\u001a\u00020\u0001*\u00020\u001a2\b\b\u0001\u0010\u0010\u001a\u00020\u0003\u001a1\u0010)\u001a\u00020\u0001*\u00020\u001a2\b\b\u0001\u0010\u0010\u001a\u00020\u00032\u0016\u0010\u0005\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00070\u0006\"\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\"\u001a\u0017\u0010)\u001a\u00020\u0001*\u00020\u001c2\b\b\u0001\u0010\u0010\u001a\u00020\u0003H\u0086\b\u001a4\u0010)\u001a\u00020\u0001*\u00020\u001c2\b\b\u0001\u0010\u0010\u001a\u00020\u00032\u0016\u0010\u0005\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00070\u0006\"\u0004\u0018\u00010\u0007H\u0086\b¢\u0006\u0002\u0010#\u001a\u0017\u0010)\u001a\u00020\u0001*\u00020\u001e2\b\b\u0001\u0010\u0010\u001a\u00020\u0003H\u0086\b\u001a4\u0010)\u001a\u00020\u0001*\u00020\u001e2\b\b\u0001\u0010\u0010\u001a\u00020\u00032\u0016\u0010\u0005\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00070\u0006\"\u0004\u0018\u00010\u0007H\u0086\b¢\u0006\u0002\u0010$\u001a\u0014\u0010*\u001a\u00020\n*\u00020\u001a2\b\b\u0001\u0010\u0010\u001a\u00020\u0003\u001a\u0017\u0010*\u001a\u00020\n*\u00020\u001c2\b\b\u0001\u0010\u0010\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010*\u001a\u00020\n*\u00020\u001e2\b\b\u0001\u0010\u0010\u001a\u00020\u0003H\u0086\b\u001a!\u0010+\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0006*\u00020\u001a2\b\b\u0001\u0010\u0010\u001a\u00020\u0003¢\u0006\u0002\u0010,\u001a$\u0010+\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0006*\u00020\u001c2\b\b\u0001\u0010\u0010\u001a\u00020\u0003H\u0086\b¢\u0006\u0002\u0010-\u001a$\u0010+\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0006*\u00020\u001e2\b\b\u0001\u0010\u0010\u001a\u00020\u0003H\u0086\b¢\u0006\u0002\u0010.\u001a\u0017\u0010/\u001a\u00020\n*\u00020\u001a2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010/\u001a\u00020\n*\u00020\u001c2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a\u0017\u0010/\u001a\u00020\n*\u00020\u001e2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b\u001a$\u00100\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0006*\u00020\u001a2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b¢\u0006\u0002\u0010,\u001a$\u00100\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0006*\u00020\u001c2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b¢\u0006\u0002\u0010-\u001a$\u00100\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0006*\u00020\u001e2\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0086\b¢\u0006\u0002\u0010.¨\u00061"}, d2 = {"appQtyStr", "", "stringResId", "", "quantity", "formatArgs", "", "", "(II[Ljava/lang/Object;)Ljava/lang/String;", "appQtyTxt", "", "appStr", "(I[Ljava/lang/Object;)Ljava/lang/String;", "appStrArray", "(I)[Ljava/lang/String;", "appStyledStr", "attr", "appStyledTxt", "appStyledTxtArray", "(I)[Ljava/lang/CharSequence;", "appTxt", "appTxtArray", "checkOfStringType", "", "Landroid/util/TypedValue;", "qtyStr", "Landroid/content/Context;", "(Landroid/content/Context;II[Ljava/lang/Object;)Ljava/lang/String;", "Landroid/view/View;", "(Landroid/view/View;II[Ljava/lang/Object;)Ljava/lang/String;", "Landroidx/fragment/app/Fragment;", "(Landroidx/fragment/app/Fragment;II[Ljava/lang/Object;)Ljava/lang/String;", "qtyTxt", "str", "(Landroid/content/Context;I[Ljava/lang/Object;)Ljava/lang/String;", "(Landroid/view/View;I[Ljava/lang/Object;)Ljava/lang/String;", "(Landroidx/fragment/app/Fragment;I[Ljava/lang/Object;)Ljava/lang/String;", "strArray", "(Landroid/content/Context;I)[Ljava/lang/String;", "(Landroid/view/View;I)[Ljava/lang/String;", "(Landroidx/fragment/app/Fragment;I)[Ljava/lang/String;", "styledStr", "styledTxt", "styledTxtArray", "(Landroid/content/Context;I)[Ljava/lang/CharSequence;", "(Landroid/view/View;I)[Ljava/lang/CharSequence;", "(Landroidx/fragment/app/Fragment;I)[Ljava/lang/CharSequence;", "txt", "txtArray", "splitties-resources_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class TextResourcesKt {
    @NotNull
    public static final String appQtyStr(@PluralsRes int i2, int i3) throws Resources.NotFoundException {
        String quantityString = AppCtxKt.getAppCtx().getResources().getQuantityString(i2, i3);
        Intrinsics.checkNotNullExpressionValue(quantityString, "resources.getQuantityString(stringResId, quantity)");
        return quantityString;
    }

    @NotNull
    public static final CharSequence appQtyTxt(@PluralsRes int i2, int i3) throws Resources.NotFoundException {
        CharSequence quantityText = AppCtxKt.getAppCtx().getResources().getQuantityText(i2, i3);
        Intrinsics.checkNotNullExpressionValue(quantityText, "resources.getQuantityText(stringResId, quantity)");
        return quantityText;
    }

    @NotNull
    public static final String appStr(@StringRes int i2, @NotNull Object... formatArgs) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(formatArgs, "formatArgs");
        Context appCtx = AppCtxKt.getAppCtx();
        Object[] objArrCopyOf = Arrays.copyOf(formatArgs, formatArgs.length);
        String string = appCtx.getResources().getString(i2, Arrays.copyOf(objArrCopyOf, objArrCopyOf.length));
        Intrinsics.checkNotNullExpressionValue(string, "resources.getString(stringResId, *formatArgs)");
        return string;
    }

    @NotNull
    public static final String[] appStrArray(@ArrayRes int i2) throws Resources.NotFoundException {
        String[] stringArray = AppCtxKt.getAppCtx().getResources().getStringArray(i2);
        Intrinsics.checkNotNullExpressionValue(stringArray, "resources.getStringArray(stringResId)");
        return stringArray;
    }

    @NotNull
    public static final String appStyledStr(@AttrRes int i2) {
        return styledStr(AppCtxKt.getAppCtx(), i2);
    }

    @NotNull
    public static final CharSequence appStyledTxt(@AttrRes int i2) {
        return styledTxt(AppCtxKt.getAppCtx(), i2);
    }

    @NotNull
    public static final CharSequence[] appStyledTxtArray(@AttrRes int i2) {
        return styledTxtArray(AppCtxKt.getAppCtx(), i2);
    }

    @NotNull
    public static final CharSequence appTxt(@StringRes int i2) throws Resources.NotFoundException {
        CharSequence text = AppCtxKt.getAppCtx().getResources().getText(i2);
        Intrinsics.checkNotNullExpressionValue(text, "resources.getText(stringResId)");
        return text;
    }

    @NotNull
    public static final CharSequence[] appTxtArray(@ArrayRes int i2) throws Resources.NotFoundException {
        CharSequence[] textArray = AppCtxKt.getAppCtx().getResources().getTextArray(i2);
        Intrinsics.checkNotNullExpressionValue(textArray, "resources.getTextArray(stringResId)");
        return textArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkOfStringType(TypedValue typedValue) {
        if (!(typedValue.type == 3)) {
            throw new IllegalArgumentException(StyledAttributesKt.unexpectedThemeAttributeTypeErrorMessage(typedValue, TypedValues.Custom.S_STRING).toString());
        }
    }

    @NotNull
    public static final String qtyStr(@NotNull Context context, @PluralsRes int i2, int i3) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(context, "<this>");
        String quantityString = context.getResources().getQuantityString(i2, i3);
        Intrinsics.checkNotNullExpressionValue(quantityString, "resources.getQuantityString(stringResId, quantity)");
        return quantityString;
    }

    @NotNull
    public static final CharSequence qtyTxt(@NotNull Context context, @PluralsRes int i2, int i3) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(context, "<this>");
        CharSequence quantityText = context.getResources().getQuantityText(i2, i3);
        Intrinsics.checkNotNullExpressionValue(quantityText, "resources.getQuantityText(stringResId, quantity)");
        return quantityText;
    }

    @NotNull
    public static final String str(@NotNull Context context, @StringRes int i2, @NotNull Object... formatArgs) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(formatArgs, "formatArgs");
        String string = context.getResources().getString(i2, Arrays.copyOf(formatArgs, formatArgs.length));
        Intrinsics.checkNotNullExpressionValue(string, "resources.getString(stringResId, *formatArgs)");
        return string;
    }

    @NotNull
    public static final String[] strArray(@NotNull Context context, @ArrayRes int i2) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(context, "<this>");
        String[] stringArray = context.getResources().getStringArray(i2);
        Intrinsics.checkNotNullExpressionValue(stringArray, "resources.getStringArray(stringResId)");
        return stringArray;
    }

    @NotNull
    public static final String styledStr(@NotNull Fragment fragment, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        return styledStr(context, i2);
    }

    @NotNull
    public static final CharSequence styledTxt(@NotNull Fragment fragment, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        return styledTxt(context, i2);
    }

    @NotNull
    public static final CharSequence[] styledTxtArray(@NotNull Context context, @AttrRes int i2) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(context, "<this>");
        CharSequence[] textArray = context.getResources().getTextArray(StyledAttributesKt.resolveThemeAttribute$default(context, i2, false, 2, null));
        Intrinsics.checkNotNullExpressionValue(textArray, "resources.getTextArray(stringResId)");
        return textArray;
    }

    @NotNull
    public static final CharSequence txt(@NotNull Context context, @StringRes int i2) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(context, "<this>");
        CharSequence text = context.getResources().getText(i2);
        Intrinsics.checkNotNullExpressionValue(text, "resources.getText(stringResId)");
        return text;
    }

    @NotNull
    public static final CharSequence[] txtArray(@NotNull Context context, @ArrayRes int i2) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(context, "<this>");
        CharSequence[] textArray = context.getResources().getTextArray(i2);
        Intrinsics.checkNotNullExpressionValue(textArray, "resources.getTextArray(stringResId)");
        return textArray;
    }

    @NotNull
    public static final String appStyledStr(@AttrRes int i2, @NotNull Object... formatArgs) {
        Intrinsics.checkNotNullParameter(formatArgs, "formatArgs");
        return styledStr(AppCtxKt.getAppCtx(), i2, Arrays.copyOf(formatArgs, formatArgs.length));
    }

    @NotNull
    public static final String qtyStr(@NotNull Fragment fragment, @PluralsRes int i2, int i3) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        String quantityString = context.getResources().getQuantityString(i2, i3);
        Intrinsics.checkNotNullExpressionValue(quantityString, "resources.getQuantityString(stringResId, quantity)");
        return quantityString;
    }

    @NotNull
    public static final CharSequence qtyTxt(@NotNull Fragment fragment, @PluralsRes int i2, int i3) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        CharSequence quantityText = context.getResources().getQuantityText(i2, i3);
        Intrinsics.checkNotNullExpressionValue(quantityText, "resources.getQuantityText(stringResId, quantity)");
        return quantityText;
    }

    @NotNull
    public static final String str(@NotNull Fragment fragment, @StringRes int i2, @NotNull Object... formatArgs) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Intrinsics.checkNotNullParameter(formatArgs, "formatArgs");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        Object[] objArrCopyOf = Arrays.copyOf(formatArgs, formatArgs.length);
        String string = context.getResources().getString(i2, Arrays.copyOf(objArrCopyOf, objArrCopyOf.length));
        Intrinsics.checkNotNullExpressionValue(string, "resources.getString(stringResId, *formatArgs)");
        return string;
    }

    @NotNull
    public static final String[] strArray(@NotNull Fragment fragment, @ArrayRes int i2) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        String[] stringArray = context.getResources().getStringArray(i2);
        Intrinsics.checkNotNullExpressionValue(stringArray, "resources.getStringArray(stringResId)");
        return stringArray;
    }

    @NotNull
    public static final String styledStr(@NotNull View view, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return styledStr(context, i2);
    }

    @NotNull
    public static final CharSequence styledTxt(@NotNull View view, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return styledTxt(context, i2);
    }

    @NotNull
    public static final CharSequence txt(@NotNull Fragment fragment, @StringRes int i2) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        CharSequence text = context.getResources().getText(i2);
        Intrinsics.checkNotNullExpressionValue(text, "resources.getText(stringResId)");
        return text;
    }

    @NotNull
    public static final CharSequence[] txtArray(@NotNull Fragment fragment, @ArrayRes int i2) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        CharSequence[] textArray = context.getResources().getTextArray(i2);
        Intrinsics.checkNotNullExpressionValue(textArray, "resources.getTextArray(stringResId)");
        return textArray;
    }

    @NotNull
    public static final String appQtyStr(@PluralsRes int i2, int i3, @NotNull Object... formatArgs) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(formatArgs, "formatArgs");
        Context appCtx = AppCtxKt.getAppCtx();
        Object[] objArrCopyOf = Arrays.copyOf(formatArgs, formatArgs.length);
        String quantityString = appCtx.getResources().getQuantityString(i2, i3, Arrays.copyOf(objArrCopyOf, objArrCopyOf.length));
        Intrinsics.checkNotNullExpressionValue(quantityString, "resources.getQuantityString(stringResId, quantity, *formatArgs)");
        return quantityString;
    }

    @NotNull
    public static final String appStr(@StringRes int i2) throws Resources.NotFoundException {
        String string = AppCtxKt.getAppCtx().getResources().getString(i2);
        Intrinsics.checkNotNullExpressionValue(string, "resources.getString(stringResId)");
        return string;
    }

    @NotNull
    public static final String styledStr(@NotNull Fragment fragment, @AttrRes int i2, @NotNull Object... formatArgs) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Intrinsics.checkNotNullParameter(formatArgs, "formatArgs");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        return styledStr(context, i2, Arrays.copyOf(formatArgs, formatArgs.length));
    }

    @NotNull
    public static final CharSequence styledTxt(@NotNull Context context, @AttrRes int i2) {
        CharSequence charSequence;
        Intrinsics.checkNotNullParameter(context, "<this>");
        if (MainThreadKt.mainThread == Thread.currentThread()) {
            Resources.Theme theme = context.getTheme();
            TypedValue typedValue = StyledAttributesKt.uiThreadConfinedCachedTypedValue;
            if (theme.resolveAttribute(i2, typedValue, true)) {
                checkOfStringType(typedValue);
                charSequence = typedValue.string;
            } else {
                throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
            }
        } else {
            TypedValue typedValue2 = StyledAttributesKt.cachedTypeValue;
            synchronized (typedValue2) {
                if (context.getTheme().resolveAttribute(i2, typedValue2, true)) {
                    checkOfStringType(typedValue2);
                    charSequence = typedValue2.string;
                } else {
                    throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
                }
            }
        }
        Intrinsics.checkNotNullExpressionValue(charSequence, "withResolvedThemeAttribute(attr) {\n    checkOfStringType()\n    string\n}");
        return charSequence;
    }

    @NotNull
    public static final CharSequence[] styledTxtArray(@NotNull Fragment fragment, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        return styledTxtArray(context, i2);
    }

    @NotNull
    public static final String qtyStr(@NotNull View view, @PluralsRes int i2, int i3) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        String quantityString = context.getResources().getQuantityString(i2, i3);
        Intrinsics.checkNotNullExpressionValue(quantityString, "resources.getQuantityString(stringResId, quantity)");
        return quantityString;
    }

    @NotNull
    public static final CharSequence qtyTxt(@NotNull View view, @PluralsRes int i2, int i3) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        CharSequence quantityText = context.getResources().getQuantityText(i2, i3);
        Intrinsics.checkNotNullExpressionValue(quantityText, "resources.getQuantityText(stringResId, quantity)");
        return quantityText;
    }

    @NotNull
    public static final String str(@NotNull View view, @StringRes int i2, @NotNull Object... formatArgs) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(formatArgs, "formatArgs");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        Object[] objArrCopyOf = Arrays.copyOf(formatArgs, formatArgs.length);
        String string = context.getResources().getString(i2, Arrays.copyOf(objArrCopyOf, objArrCopyOf.length));
        Intrinsics.checkNotNullExpressionValue(string, "resources.getString(stringResId, *formatArgs)");
        return string;
    }

    @NotNull
    public static final String[] strArray(@NotNull View view, @ArrayRes int i2) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        String[] stringArray = context.getResources().getStringArray(i2);
        Intrinsics.checkNotNullExpressionValue(stringArray, "resources.getStringArray(stringResId)");
        return stringArray;
    }

    @NotNull
    public static final String styledStr(@NotNull View view, @AttrRes int i2, @NotNull Object... formatArgs) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(formatArgs, "formatArgs");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return styledStr(context, i2, Arrays.copyOf(formatArgs, formatArgs.length));
    }

    @NotNull
    public static final CharSequence[] styledTxtArray(@NotNull View view, @AttrRes int i2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return styledTxtArray(context, i2);
    }

    @NotNull
    public static final CharSequence txt(@NotNull View view, @StringRes int i2) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        CharSequence text = context.getResources().getText(i2);
        Intrinsics.checkNotNullExpressionValue(text, "resources.getText(stringResId)");
        return text;
    }

    @NotNull
    public static final CharSequence[] txtArray(@NotNull View view, @ArrayRes int i2) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        CharSequence[] textArray = context.getResources().getTextArray(i2);
        Intrinsics.checkNotNullExpressionValue(textArray, "resources.getTextArray(stringResId)");
        return textArray;
    }

    @NotNull
    public static final String styledStr(@NotNull Context context, @AttrRes int i2) {
        String string;
        Intrinsics.checkNotNullParameter(context, "<this>");
        if (MainThreadKt.mainThread == Thread.currentThread()) {
            Resources.Theme theme = context.getTheme();
            TypedValue typedValue = StyledAttributesKt.uiThreadConfinedCachedTypedValue;
            if (theme.resolveAttribute(i2, typedValue, true)) {
                checkOfStringType(typedValue);
                return typedValue.string.toString();
            }
            throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
        }
        TypedValue typedValue2 = StyledAttributesKt.cachedTypeValue;
        synchronized (typedValue2) {
            if (context.getTheme().resolveAttribute(i2, typedValue2, true)) {
                checkOfStringType(typedValue2);
                string = typedValue2.string.toString();
            } else {
                throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
            }
        }
        return string;
    }

    @NotNull
    public static final String qtyStr(@NotNull Context context, @PluralsRes int i2, int i3, @NotNull Object... formatArgs) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(formatArgs, "formatArgs");
        String quantityString = context.getResources().getQuantityString(i2, i3, Arrays.copyOf(formatArgs, formatArgs.length));
        Intrinsics.checkNotNullExpressionValue(quantityString, "resources.getQuantityString(stringResId, quantity, *formatArgs)");
        return quantityString;
    }

    @NotNull
    public static final String str(@NotNull Context context, @StringRes int i2) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(context, "<this>");
        String string = context.getResources().getString(i2);
        Intrinsics.checkNotNullExpressionValue(string, "resources.getString(stringResId)");
        return string;
    }

    @NotNull
    public static final String qtyStr(@NotNull Fragment fragment, @PluralsRes int i2, int i3, @NotNull Object... formatArgs) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Intrinsics.checkNotNullParameter(formatArgs, "formatArgs");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        Object[] objArrCopyOf = Arrays.copyOf(formatArgs, formatArgs.length);
        String quantityString = context.getResources().getQuantityString(i2, i3, Arrays.copyOf(objArrCopyOf, objArrCopyOf.length));
        Intrinsics.checkNotNullExpressionValue(quantityString, "resources.getQuantityString(stringResId, quantity, *formatArgs)");
        return quantityString;
    }

    @NotNull
    public static final String str(@NotNull Fragment fragment, @StringRes int i2) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(fragment, "<this>");
        Context context = fragment.getContext();
        Intrinsics.checkNotNull(context);
        Intrinsics.checkNotNullExpressionValue(context, "context!!");
        String string = context.getResources().getString(i2);
        Intrinsics.checkNotNullExpressionValue(string, "resources.getString(stringResId)");
        return string;
    }

    @NotNull
    public static final String qtyStr(@NotNull View view, @PluralsRes int i2, int i3, @NotNull Object... formatArgs) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(formatArgs, "formatArgs");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        Object[] objArrCopyOf = Arrays.copyOf(formatArgs, formatArgs.length);
        String quantityString = context.getResources().getQuantityString(i2, i3, Arrays.copyOf(objArrCopyOf, objArrCopyOf.length));
        Intrinsics.checkNotNullExpressionValue(quantityString, "resources.getQuantityString(stringResId, quantity, *formatArgs)");
        return quantityString;
    }

    @NotNull
    public static final String str(@NotNull View view, @StringRes int i2) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        String string = context.getResources().getString(i2);
        Intrinsics.checkNotNullExpressionValue(string, "resources.getString(stringResId)");
        return string;
    }

    @NotNull
    public static final String styledStr(@NotNull Context context, @AttrRes int i2, @NotNull Object... formatArgs) {
        String str;
        Intrinsics.checkNotNullParameter(context, "<this>");
        Intrinsics.checkNotNullParameter(formatArgs, "formatArgs");
        if (MainThreadKt.mainThread == Thread.currentThread()) {
            Resources.Theme theme = context.getTheme();
            TypedValue typedValue = StyledAttributesKt.uiThreadConfinedCachedTypedValue;
            if (theme.resolveAttribute(i2, typedValue, true)) {
                checkOfStringType(typedValue);
                Configuration configuration = context.getResources().getConfiguration();
                Locale locale = Build.VERSION.SDK_INT >= 24 ? configuration.getLocales().get(0) : configuration.locale;
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String string = typedValue.string.toString();
                Object[] objArrCopyOf = Arrays.copyOf(formatArgs, formatArgs.length);
                String str2 = String.format(locale, string, Arrays.copyOf(objArrCopyOf, objArrCopyOf.length));
                Intrinsics.checkNotNullExpressionValue(str2, "java.lang.String.format(locale, format, *args)");
                return str2;
            }
            throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
        }
        TypedValue typedValue2 = StyledAttributesKt.cachedTypeValue;
        synchronized (typedValue2) {
            if (context.getTheme().resolveAttribute(i2, typedValue2, true)) {
                checkOfStringType(typedValue2);
                Configuration configuration2 = context.getResources().getConfiguration();
                Locale locale2 = Build.VERSION.SDK_INT >= 24 ? configuration2.getLocales().get(0) : configuration2.locale;
                StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                String string2 = typedValue2.string.toString();
                Object[] objArrCopyOf2 = Arrays.copyOf(formatArgs, formatArgs.length);
                str = String.format(locale2, string2, Arrays.copyOf(objArrCopyOf2, objArrCopyOf2.length));
                Intrinsics.checkNotNullExpressionValue(str, "java.lang.String.format(locale, format, *args)");
            } else {
                throw new Resources.NotFoundException("Couldn't resolve attribute resource #0x" + ((Object) Integer.toHexString(i2)) + " from the theme of this Context.");
            }
        }
        return str;
    }
}
