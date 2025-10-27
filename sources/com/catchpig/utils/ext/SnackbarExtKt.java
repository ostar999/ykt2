package com.catchpig.utils.ext;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.google.android.material.R;
import com.google.android.material.snackbar.Snackbar;
import java.lang.reflect.Field;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0002\b\u0004\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0001\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0004\u001a*\u0010\u0007\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004\u001a\u0014\u0010\f\u001a\u00020\u0001*\u00020\u00022\b\b\u0001\u0010\r\u001a\u00020\u0004\u001a&\u0010\u000e\u001a\u00020\u0001*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0001\u0010\u0012\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u0004\u001a(\u0010\u000e\u001a\u00020\u0001*\u00020\u000f2\b\b\u0001\u0010\u0013\u001a\u00020\u00042\b\b\u0001\u0010\u0012\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u0004\u001a\n\u0010\u0014\u001a\u00020\u000f*\u00020\u0002¨\u0006\u0015"}, d2 = {"setBackgroundResource", "", "Lcom/google/android/material/snackbar/Snackbar;", AliyunLogKey.KEY_RESULT, "", "setGravity", "gravity", "setMargin", "startDp", "topDp", "endDp", "bottomDp", "setTextColorRes", "textColor", "showSnackBar", "Landroid/view/View;", "text", "", "background", "textRes", "targetParent", "utils_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nSnackbarExt.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SnackbarExt.kt\ncom/catchpig/utils/ext/SnackbarExtKt\n+ 2 View.kt\nandroidx/core/view/ViewKt\n*L\n1#1,102:1\n315#2:103\n329#2,4:104\n316#2:108\n*S KotlinDebug\n*F\n+ 1 SnackbarExt.kt\ncom/catchpig/utils/ext/SnackbarExtKt\n*L\n71#1:103\n71#1:104,4\n71#1:108\n*E\n"})
/* loaded from: classes.dex */
public final class SnackbarExtKt {
    public static final void setBackgroundResource(@NotNull Snackbar snackbar, @DrawableRes int i2) {
        Intrinsics.checkNotNullParameter(snackbar, "<this>");
        snackbar.getView().setBackgroundResource(i2);
    }

    public static final void setGravity(@NotNull Snackbar snackbar, int i2) {
        Intrinsics.checkNotNullParameter(snackbar, "<this>");
        View view = snackbar.getView();
        Intrinsics.checkNotNullExpressionValue(view, "view");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.view.ViewGroup.LayoutParams");
        }
        if (layoutParams instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) layoutParams).gravity = i2;
        } else if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
            ((CoordinatorLayout.LayoutParams) layoutParams).gravity = i2;
        }
        view.setLayoutParams(layoutParams);
    }

    public static final void setMargin(@NotNull Snackbar snackbar, int i2, int i3, int i4, int i5) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(snackbar, "<this>");
        Field declaredField = snackbar.getClass().getSuperclass().getDeclaredField("originalMargins");
        declaredField.setAccessible(true);
        Context context = snackbar.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        int iDp2px = CommonExtKt.dp2px(context, i2);
        Context context2 = snackbar.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "context");
        int iDp2px2 = CommonExtKt.dp2px(context2, i3);
        Context context3 = snackbar.getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "context");
        int iDp2px3 = CommonExtKt.dp2px(context3, i4);
        Context context4 = snackbar.getContext();
        Intrinsics.checkNotNullExpressionValue(context4, "context");
        declaredField.set(snackbar, new Rect(iDp2px, iDp2px2, iDp2px3, CommonExtKt.dp2px(context4, i5)));
    }

    public static final void setTextColorRes(@NotNull Snackbar snackbar, @ColorRes int i2) {
        Intrinsics.checkNotNullParameter(snackbar, "<this>");
        View viewFindViewById = snackbar.getView().findViewById(R.id.snackbar_text);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "view.findViewById<TextVi…erial.R.id.snackbar_text)");
        TextViewExtKt.setTextColorRes((TextView) viewFindViewById, i2);
    }

    public static final void showSnackBar(@NotNull View view, @NotNull CharSequence text, @DrawableRes int i2, int i3) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(text, "text");
        Snackbar snackbarMake = Snackbar.make(view, text, 0);
        Intrinsics.checkNotNullExpressionValue(snackbarMake, "make(this, text, Snackbar.LENGTH_LONG)");
        setBackgroundResource(snackbarMake, i2);
        setTextColorRes(snackbarMake, com.catchpig.utils.R.color.color_black);
        if (i3 == 80) {
            View viewTargetParent = targetParent(snackbarMake);
            Context context = view.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            setMargin(snackbarMake, 20, 0, 20, CommonExtKt.px2dp(context, viewTargetParent.getBottom() - view.getBottom()) + 20);
        } else {
            setGravity(snackbarMake, i3);
            setMargin(snackbarMake, 20, 0, 20, 0);
        }
        snackbarMake.show();
    }

    public static /* synthetic */ void showSnackBar$default(View view, CharSequence charSequence, int i2, int i3, int i4, Object obj) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if ((i4 & 4) != 0) {
            i3 = 80;
        }
        showSnackBar(view, charSequence, i2, i3);
    }

    @NotNull
    public static final View targetParent(@NotNull Snackbar snackbar) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(snackbar, "<this>");
        Field declaredField = snackbar.getClass().getSuperclass().getDeclaredField("targetParent");
        declaredField.setAccessible(true);
        Object obj = declaredField.get(snackbar);
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type android.view.View");
        return (View) obj;
    }

    public static /* synthetic */ void showSnackBar$default(View view, int i2, int i3, int i4, int i5, Object obj) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if ((i5 & 4) != 0) {
            i4 = 80;
        }
        showSnackBar(view, i2, i3, i4);
    }

    public static final void showSnackBar(@NotNull View view, @StringRes int i2, @DrawableRes int i3, int i4) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Snackbar snackbarMake = Snackbar.make(view, i2, 0);
        Intrinsics.checkNotNullExpressionValue(snackbarMake, "make(this, textRes, Snackbar.LENGTH_LONG)");
        setBackgroundResource(snackbarMake, i3);
        setTextColorRes(snackbarMake, com.catchpig.utils.R.color.color_black);
        if (i4 == 80) {
            View viewTargetParent = targetParent(snackbarMake);
            Context context = view.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            setMargin(snackbarMake, 20, 0, 20, CommonExtKt.px2dp(context, viewTargetParent.getBottom() - view.getBottom()) + 20);
        } else {
            setGravity(snackbarMake, i4);
            setMargin(snackbarMake, 20, 0, 20, 0);
        }
        snackbarMake.show();
    }
}
