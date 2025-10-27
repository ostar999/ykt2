package androidx.preference;

import android.util.SparseArray;
import android.view.View;
import androidx.annotation.IdRes;
import androidx.annotation.RestrictTo;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes.dex */
public class PreferenceViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> mCachedViews;
    private boolean mDividerAllowedAbove;
    private boolean mDividerAllowedBelow;

    public PreferenceViewHolder(View view) {
        super(view);
        SparseArray<View> sparseArray = new SparseArray<>(4);
        this.mCachedViews = sparseArray;
        sparseArray.put(android.R.id.title, view.findViewById(android.R.id.title));
        sparseArray.put(android.R.id.summary, view.findViewById(android.R.id.summary));
        sparseArray.put(android.R.id.icon, view.findViewById(android.R.id.icon));
        int i2 = R.id.icon_frame;
        sparseArray.put(i2, view.findViewById(i2));
        sparseArray.put(16908350, view.findViewById(16908350));
    }

    @RestrictTo({RestrictTo.Scope.TESTS})
    public static PreferenceViewHolder createInstanceForTests(View view) {
        return new PreferenceViewHolder(view);
    }

    public View findViewById(@IdRes int i2) {
        View view = this.mCachedViews.get(i2);
        if (view != null) {
            return view;
        }
        View viewFindViewById = this.itemView.findViewById(i2);
        if (viewFindViewById != null) {
            this.mCachedViews.put(i2, viewFindViewById);
        }
        return viewFindViewById;
    }

    public boolean isDividerAllowedAbove() {
        return this.mDividerAllowedAbove;
    }

    public boolean isDividerAllowedBelow() {
        return this.mDividerAllowedBelow;
    }

    public void setDividerAllowedAbove(boolean z2) {
        this.mDividerAllowedAbove = z2;
    }

    public void setDividerAllowedBelow(boolean z2) {
        this.mDividerAllowedBelow = z2;
    }
}
