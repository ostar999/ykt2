package androidx.preference;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

/* loaded from: classes.dex */
public class PreferenceCategory extends PreferenceGroup {
    private static final String TAG = "PreferenceCategory";

    public PreferenceCategory(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
    }

    @Override // androidx.preference.Preference
    public boolean isEnabled() {
        return false;
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        if (Build.VERSION.SDK_INT >= 28) {
            preferenceViewHolder.itemView.setAccessibilityHeading(true);
        }
    }

    @Override // androidx.preference.Preference
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        AccessibilityNodeInfoCompat.CollectionItemInfoCompat collectionItemInfo;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfoCompat);
        if (Build.VERSION.SDK_INT >= 28 || (collectionItemInfo = accessibilityNodeInfoCompat.getCollectionItemInfo()) == null) {
            return;
        }
        accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(collectionItemInfo.getRowIndex(), collectionItemInfo.getRowSpan(), collectionItemInfo.getColumnIndex(), collectionItemInfo.getColumnSpan(), true, collectionItemInfo.isSelected()));
    }

    @Override // androidx.preference.Preference
    public boolean shouldDisableDependents() {
        return !super.isEnabled();
    }

    public PreferenceCategory(Context context, AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, 0);
    }

    public PreferenceCategory(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, R.attr.preferenceCategoryStyle, android.R.attr.preferenceCategoryStyle));
    }

    public PreferenceCategory(Context context) {
        this(context, null);
    }
}
