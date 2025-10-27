package androidx.preference.internal;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.RestrictTo;
import androidx.preference.DialogPreference;
import java.util.Set;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public abstract class AbstractMultiSelectListPreference extends DialogPreference {
    public AbstractMultiSelectListPreference(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
    }

    public abstract CharSequence[] getEntries();

    public abstract CharSequence[] getEntryValues();

    public abstract Set<String> getValues();

    public abstract void setValues(Set<String> set);

    public AbstractMultiSelectListPreference(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }

    public AbstractMultiSelectListPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AbstractMultiSelectListPreference(Context context) {
        super(context);
    }
}
