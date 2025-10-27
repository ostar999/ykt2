package com.ykb.ebook.extensions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a!\u0010\u000e\u001a\u00020\u000f\"\n\b\u0000\u0010\u0010\u0018\u0001*\u00020\u0011*\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0005H\u0086\b\u001a\u0019\u0010\u0014\u001a\u00020\u000f\"\n\b\u0000\u0010\u0010\u0018\u0001*\u00020\u0015*\u00020\u0012H\u0086\b\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0017\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\"\u0015\u0010\b\u001a\u00020\t*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\"\u0015\u0010\f\u001a\u00020\t*\u00020\u00028G¢\u0006\u0006\u001a\u0004\b\r\u0010\u000b¨\u0006\u0016"}, d2 = {"isNavigationBarExist", "", "Landroid/app/Activity;", "(Landroid/app/Activity;)Z", "navigationBar", "Landroid/view/View;", "getNavigationBar", "(Landroid/app/Activity;)Landroid/view/View;", "navigationBarGravity", "", "getNavigationBarGravity", "(Landroid/app/Activity;)I", "navigationBarHeight", "getNavigationBarHeight", "showAsDropDown", "", ExifInterface.GPS_DIRECTION_TRUE, "Landroid/widget/PopupWindow;", "Landroidx/appcompat/app/AppCompatActivity;", "anchor", "showDialog", "Landroidx/appcompat/app/AppCompatDialog;", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ActivityExtensionsKt {
    @Nullable
    public static final View getNavigationBar(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "<this>");
        View decorView = activity.getWindow().getDecorView();
        ViewGroup viewGroup = decorView instanceof ViewGroup ? (ViewGroup) decorView : null;
        if (viewGroup == null) {
            return null;
        }
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            int id = childAt.getId();
            if (id != -1 && Intrinsics.areEqual(activity.getResources().getResourceEntryName(id), "navigationBarBackground")) {
                return childAt;
            }
        }
        return null;
    }

    public static final int getNavigationBarGravity(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "<this>");
        View navigationBar = getNavigationBar(activity);
        ViewGroup.LayoutParams layoutParams = navigationBar != null ? navigationBar.getLayoutParams() : null;
        FrameLayout.LayoutParams layoutParams2 = layoutParams instanceof FrameLayout.LayoutParams ? (FrameLayout.LayoutParams) layoutParams : null;
        Integer numValueOf = layoutParams2 != null ? Integer.valueOf(layoutParams2.gravity) : null;
        if (numValueOf != null) {
            return numValueOf.intValue();
        }
        return 80;
    }

    @SuppressLint({"InternalInsetResource", "DiscouragedApi"})
    public static final int getNavigationBarHeight(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "<this>");
        if (!isNavigationBarExist(activity)) {
            return 0;
        }
        return activity.getResources().getDimensionPixelSize(activity.getResources().getIdentifier("navigation_bar_height", "dimen", "android"));
    }

    public static final boolean isNavigationBarExist(@NotNull Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "<this>");
        return getNavigationBar(activity) != null;
    }

    public static final /* synthetic */ <T extends PopupWindow> void showAsDropDown(AppCompatActivity appCompatActivity, View anchor) {
        Intrinsics.checkNotNullParameter(appCompatActivity, "<this>");
        Intrinsics.checkNotNullParameter(anchor, "anchor");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        ((PopupWindow) PopupWindow.class.newInstance()).showAsDropDown(anchor);
    }

    public static final /* synthetic */ <T extends AppCompatDialog> void showDialog(AppCompatActivity appCompatActivity) {
        Intrinsics.checkNotNullParameter(appCompatActivity, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        ((AppCompatDialog) AppCompatDialog.class.newInstance()).show();
    }
}
