package com.google.android.material.badge;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.internal.ParcelableSparseArray;
import com.google.android.material.internal.ToolbarUtils;

@ExperimentalBadgeUtils
/* loaded from: classes3.dex */
public class BadgeUtils {
    private static final String LOG_TAG = "BadgeUtils";
    public static final boolean USE_COMPAT_PARENT = false;

    private BadgeUtils() {
    }

    public static void attachBadgeDrawable(@NonNull BadgeDrawable badgeDrawable, @NonNull View view) throws Resources.NotFoundException {
        attachBadgeDrawable(badgeDrawable, view, (FrameLayout) null);
    }

    @NonNull
    public static SparseArray<BadgeDrawable> createBadgeDrawablesFromSavedStates(Context context, @NonNull ParcelableSparseArray parcelableSparseArray) {
        SparseArray<BadgeDrawable> sparseArray = new SparseArray<>(parcelableSparseArray.size());
        for (int i2 = 0; i2 < parcelableSparseArray.size(); i2++) {
            int iKeyAt = parcelableSparseArray.keyAt(i2);
            BadgeDrawable.SavedState savedState = (BadgeDrawable.SavedState) parcelableSparseArray.valueAt(i2);
            if (savedState == null) {
                throw new IllegalArgumentException("BadgeDrawable's savedState cannot be null");
            }
            sparseArray.put(iKeyAt, BadgeDrawable.createFromSavedState(context, savedState));
        }
        return sparseArray;
    }

    @NonNull
    public static ParcelableSparseArray createParcelableBadgeStates(@NonNull SparseArray<BadgeDrawable> sparseArray) {
        ParcelableSparseArray parcelableSparseArray = new ParcelableSparseArray();
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            int iKeyAt = sparseArray.keyAt(i2);
            BadgeDrawable badgeDrawableValueAt = sparseArray.valueAt(i2);
            if (badgeDrawableValueAt == null) {
                throw new IllegalArgumentException("badgeDrawable cannot be null");
            }
            parcelableSparseArray.put(iKeyAt, badgeDrawableValueAt.getSavedState());
        }
        return parcelableSparseArray;
    }

    public static void detachBadgeDrawable(@Nullable BadgeDrawable badgeDrawable, @NonNull View view) {
        if (badgeDrawable == null) {
            return;
        }
        if (USE_COMPAT_PARENT || badgeDrawable.getCustomBadgeParent() != null) {
            badgeDrawable.getCustomBadgeParent().setForeground(null);
        } else {
            view.getOverlay().remove(badgeDrawable);
        }
    }

    @VisibleForTesting
    public static void removeToolbarOffset(BadgeDrawable badgeDrawable) throws Resources.NotFoundException {
        badgeDrawable.setAdditionalHorizontalOffset(0);
        badgeDrawable.setAdditionalVerticalOffset(0);
    }

    public static void setBadgeDrawableBounds(@NonNull BadgeDrawable badgeDrawable, @NonNull View view, @Nullable FrameLayout frameLayout) throws Resources.NotFoundException {
        Rect rect = new Rect();
        view.getDrawingRect(rect);
        badgeDrawable.setBounds(rect);
        badgeDrawable.updateBadgeCoordinates(view, frameLayout);
    }

    @VisibleForTesting
    public static void setToolbarOffset(BadgeDrawable badgeDrawable, Resources resources) throws Resources.NotFoundException {
        badgeDrawable.setAdditionalHorizontalOffset(resources.getDimensionPixelOffset(R.dimen.mtrl_badge_toolbar_action_menu_item_horizontal_offset));
        badgeDrawable.setAdditionalVerticalOffset(resources.getDimensionPixelOffset(R.dimen.mtrl_badge_toolbar_action_menu_item_vertical_offset));
    }

    public static void updateBadgeBounds(@NonNull Rect rect, float f2, float f3, float f4, float f5) {
        rect.set((int) (f2 - f4), (int) (f3 - f5), (int) (f2 + f4), (int) (f3 + f5));
    }

    public static void attachBadgeDrawable(@NonNull BadgeDrawable badgeDrawable, @NonNull View view, @Nullable FrameLayout frameLayout) throws Resources.NotFoundException {
        setBadgeDrawableBounds(badgeDrawable, view, frameLayout);
        if (badgeDrawable.getCustomBadgeParent() != null) {
            badgeDrawable.getCustomBadgeParent().setForeground(badgeDrawable);
        } else {
            if (USE_COMPAT_PARENT) {
                throw new IllegalArgumentException("Trying to reference null customBadgeParent");
            }
            view.getOverlay().add(badgeDrawable);
        }
    }

    public static void detachBadgeDrawable(@Nullable BadgeDrawable badgeDrawable, @NonNull Toolbar toolbar, @IdRes int i2) throws Resources.NotFoundException {
        if (badgeDrawable == null) {
            return;
        }
        ActionMenuItemView actionMenuItemView = ToolbarUtils.getActionMenuItemView(toolbar, i2);
        if (actionMenuItemView != null) {
            removeToolbarOffset(badgeDrawable);
            detachBadgeDrawable(badgeDrawable, actionMenuItemView);
        } else {
            Log.w(LOG_TAG, "Trying to remove badge from a null menuItemView: " + i2);
        }
    }

    public static void attachBadgeDrawable(@NonNull BadgeDrawable badgeDrawable, @NonNull Toolbar toolbar, @IdRes int i2) {
        attachBadgeDrawable(badgeDrawable, toolbar, i2, null);
    }

    public static void attachBadgeDrawable(@NonNull final BadgeDrawable badgeDrawable, @NonNull final Toolbar toolbar, @IdRes final int i2, @Nullable final FrameLayout frameLayout) {
        toolbar.post(new Runnable() { // from class: com.google.android.material.badge.BadgeUtils.1
            @Override // java.lang.Runnable
            public void run() throws Resources.NotFoundException {
                ActionMenuItemView actionMenuItemView = ToolbarUtils.getActionMenuItemView(toolbar, i2);
                if (actionMenuItemView != null) {
                    BadgeUtils.setToolbarOffset(badgeDrawable, toolbar.getResources());
                    BadgeUtils.attachBadgeDrawable(badgeDrawable, actionMenuItemView, frameLayout);
                }
            }
        });
    }
}
