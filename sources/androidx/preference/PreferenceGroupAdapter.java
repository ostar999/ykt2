package androidx.preference;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class PreferenceGroupAdapter extends RecyclerView.Adapter<PreferenceViewHolder> implements Preference.OnPreferenceChangeInternalListener, PreferenceGroup.PreferencePositionCallback {
    private Handler mHandler;
    private PreferenceGroup mPreferenceGroup;
    private CollapsiblePreferenceGroupController mPreferenceGroupController;
    private List<PreferenceLayout> mPreferenceLayouts;
    private List<Preference> mPreferenceList;
    private List<Preference> mPreferenceListInternal;
    private Runnable mSyncRunnable;
    private PreferenceLayout mTempPreferenceLayout;

    public static class PreferenceLayout {
        String mName;
        int mResId;
        int mWidgetResId;

        public PreferenceLayout() {
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof PreferenceLayout)) {
                return false;
            }
            PreferenceLayout preferenceLayout = (PreferenceLayout) obj;
            return this.mResId == preferenceLayout.mResId && this.mWidgetResId == preferenceLayout.mWidgetResId && TextUtils.equals(this.mName, preferenceLayout.mName);
        }

        public int hashCode() {
            return ((((R2.attr.bl_checkable_gradient_endColor + this.mResId) * 31) + this.mWidgetResId) * 31) + this.mName.hashCode();
        }

        public PreferenceLayout(PreferenceLayout preferenceLayout) {
            this.mResId = preferenceLayout.mResId;
            this.mWidgetResId = preferenceLayout.mWidgetResId;
            this.mName = preferenceLayout.mName;
        }
    }

    public PreferenceGroupAdapter(PreferenceGroup preferenceGroup) {
        this(preferenceGroup, new Handler());
    }

    private void addPreferenceClassName(Preference preference) {
        PreferenceLayout preferenceLayoutCreatePreferenceLayout = createPreferenceLayout(preference, null);
        if (this.mPreferenceLayouts.contains(preferenceLayoutCreatePreferenceLayout)) {
            return;
        }
        this.mPreferenceLayouts.add(preferenceLayoutCreatePreferenceLayout);
    }

    @VisibleForTesting
    public static PreferenceGroupAdapter createInstanceWithCustomHandler(PreferenceGroup preferenceGroup, Handler handler) {
        return new PreferenceGroupAdapter(preferenceGroup, handler);
    }

    private PreferenceLayout createPreferenceLayout(Preference preference, PreferenceLayout preferenceLayout) {
        if (preferenceLayout == null) {
            preferenceLayout = new PreferenceLayout();
        }
        preferenceLayout.mName = preference.getClass().getName();
        preferenceLayout.mResId = preference.getLayoutResource();
        preferenceLayout.mWidgetResId = preference.getWidgetLayoutResource();
        return preferenceLayout;
    }

    private void flattenPreferenceGroup(List<Preference> list, PreferenceGroup preferenceGroup) {
        preferenceGroup.sortPreferences();
        int preferenceCount = preferenceGroup.getPreferenceCount();
        for (int i2 = 0; i2 < preferenceCount; i2++) {
            Preference preference = preferenceGroup.getPreference(i2);
            list.add(preference);
            addPreferenceClassName(preference);
            if (preference instanceof PreferenceGroup) {
                PreferenceGroup preferenceGroup2 = (PreferenceGroup) preference;
                if (preferenceGroup2.isOnSameScreenAsChildren()) {
                    flattenPreferenceGroup(list, preferenceGroup2);
                }
            }
            preference.setOnPreferenceChangeInternalListener(this);
        }
    }

    public Preference getItem(int i2) {
        if (i2 < 0 || i2 >= getItemCount()) {
            return null;
        }
        return this.mPreferenceList.get(i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mPreferenceList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i2) {
        if (hasStableIds()) {
            return getItem(i2).getId();
        }
        return -1L;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i2) {
        PreferenceLayout preferenceLayoutCreatePreferenceLayout = createPreferenceLayout(getItem(i2), this.mTempPreferenceLayout);
        this.mTempPreferenceLayout = preferenceLayoutCreatePreferenceLayout;
        int iIndexOf = this.mPreferenceLayouts.indexOf(preferenceLayoutCreatePreferenceLayout);
        if (iIndexOf != -1) {
            return iIndexOf;
        }
        int size = this.mPreferenceLayouts.size();
        this.mPreferenceLayouts.add(new PreferenceLayout(this.mTempPreferenceLayout));
        return size;
    }

    @Override // androidx.preference.PreferenceGroup.PreferencePositionCallback
    public int getPreferenceAdapterPosition(String str) {
        int size = this.mPreferenceList.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (TextUtils.equals(str, this.mPreferenceList.get(i2).getKey())) {
                return i2;
            }
        }
        return -1;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeInternalListener
    public void onPreferenceChange(Preference preference) {
        int iIndexOf = this.mPreferenceList.indexOf(preference);
        if (iIndexOf != -1) {
            notifyItemChanged(iIndexOf, preference);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeInternalListener
    public void onPreferenceHierarchyChange(Preference preference) {
        this.mHandler.removeCallbacks(this.mSyncRunnable);
        this.mHandler.post(this.mSyncRunnable);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeInternalListener
    public void onPreferenceVisibilityChange(Preference preference) {
        if (this.mPreferenceListInternal.contains(preference) && !this.mPreferenceGroupController.onPreferenceVisibilityChange(preference)) {
            if (!preference.isVisible()) {
                int size = this.mPreferenceList.size();
                int i2 = 0;
                while (i2 < size && !preference.equals(this.mPreferenceList.get(i2))) {
                    if (i2 == size - 1) {
                        return;
                    } else {
                        i2++;
                    }
                }
                this.mPreferenceList.remove(i2);
                notifyItemRemoved(i2);
                return;
            }
            int i3 = -1;
            for (Preference preference2 : this.mPreferenceListInternal) {
                if (preference.equals(preference2)) {
                    break;
                } else if (preference2.isVisible()) {
                    i3++;
                }
            }
            int i4 = i3 + 1;
            this.mPreferenceList.add(i4, preference);
            notifyItemInserted(i4);
        }
    }

    public void syncMyPreferences() {
        Iterator<Preference> it = this.mPreferenceListInternal.iterator();
        while (it.hasNext()) {
            it.next().setOnPreferenceChangeInternalListener(null);
        }
        ArrayList arrayList = new ArrayList(this.mPreferenceListInternal.size());
        flattenPreferenceGroup(arrayList, this.mPreferenceGroup);
        final List<Preference> listCreateVisiblePreferencesList = this.mPreferenceGroupController.createVisiblePreferencesList(this.mPreferenceGroup);
        final List<Preference> list = this.mPreferenceList;
        this.mPreferenceList = listCreateVisiblePreferencesList;
        this.mPreferenceListInternal = arrayList;
        PreferenceManager preferenceManager = this.mPreferenceGroup.getPreferenceManager();
        if (preferenceManager == null || preferenceManager.getPreferenceComparisonCallback() == null) {
            notifyDataSetChanged();
        } else {
            final PreferenceManager.PreferenceComparisonCallback preferenceComparisonCallback = preferenceManager.getPreferenceComparisonCallback();
            DiffUtil.calculateDiff(new DiffUtil.Callback() { // from class: androidx.preference.PreferenceGroupAdapter.2
                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public boolean areContentsTheSame(int i2, int i3) {
                    return preferenceComparisonCallback.arePreferenceContentsTheSame((Preference) list.get(i2), (Preference) listCreateVisiblePreferencesList.get(i3));
                }

                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public boolean areItemsTheSame(int i2, int i3) {
                    return preferenceComparisonCallback.arePreferenceItemsTheSame((Preference) list.get(i2), (Preference) listCreateVisiblePreferencesList.get(i3));
                }

                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public int getNewListSize() {
                    return listCreateVisiblePreferencesList.size();
                }

                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public int getOldListSize() {
                    return list.size();
                }
            }).dispatchUpdatesTo(this);
        }
        Iterator<Preference> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            it2.next().clearWasDetached();
        }
    }

    private PreferenceGroupAdapter(PreferenceGroup preferenceGroup, Handler handler) {
        this.mTempPreferenceLayout = new PreferenceLayout();
        this.mSyncRunnable = new Runnable() { // from class: androidx.preference.PreferenceGroupAdapter.1
            @Override // java.lang.Runnable
            public void run() {
                PreferenceGroupAdapter.this.syncMyPreferences();
            }
        };
        this.mPreferenceGroup = preferenceGroup;
        this.mHandler = handler;
        this.mPreferenceGroupController = new CollapsiblePreferenceGroupController(preferenceGroup, this);
        this.mPreferenceGroup.setOnPreferenceChangeInternalListener(this);
        this.mPreferenceList = new ArrayList();
        this.mPreferenceListInternal = new ArrayList();
        this.mPreferenceLayouts = new ArrayList();
        PreferenceGroup preferenceGroup2 = this.mPreferenceGroup;
        if (preferenceGroup2 instanceof PreferenceScreen) {
            setHasStableIds(((PreferenceScreen) preferenceGroup2).shouldUseGeneratedIds());
        } else {
            setHasStableIds(true);
        }
        syncMyPreferences();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder, int i2) {
        getItem(i2).onBindViewHolder(preferenceViewHolder);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public PreferenceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
        PreferenceLayout preferenceLayout = this.mPreferenceLayouts.get(i2);
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(viewGroup.getContext());
        TypedArray typedArrayObtainStyledAttributes = viewGroup.getContext().obtainStyledAttributes((AttributeSet) null, R.styleable.BackgroundStyle);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.BackgroundStyle_android_selectableItemBackground);
        if (drawable == null) {
            drawable = ContextCompat.getDrawable(viewGroup.getContext(), android.R.drawable.list_selector_background);
        }
        typedArrayObtainStyledAttributes.recycle();
        View viewInflate = layoutInflaterFrom.inflate(preferenceLayout.mResId, viewGroup, false);
        if (viewInflate.getBackground() == null) {
            ViewCompat.setBackground(viewInflate, drawable);
        }
        ViewGroup viewGroup2 = (ViewGroup) viewInflate.findViewById(android.R.id.widget_frame);
        if (viewGroup2 != null) {
            int i3 = preferenceLayout.mWidgetResId;
            if (i3 != 0) {
                layoutInflaterFrom.inflate(i3, viewGroup2);
            } else {
                viewGroup2.setVisibility(8);
            }
        }
        return new PreferenceViewHolder(viewInflate);
    }

    @Override // androidx.preference.PreferenceGroup.PreferencePositionCallback
    public int getPreferenceAdapterPosition(Preference preference) {
        int size = this.mPreferenceList.size();
        for (int i2 = 0; i2 < size; i2++) {
            Preference preference2 = this.mPreferenceList.get(i2);
            if (preference2 != null && preference2.equals(preference)) {
                return i2;
            }
        }
        return -1;
    }
}
