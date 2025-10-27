package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.android.gms.common.R;
import com.google.android.gms.common.annotation.KeepForSdk;
import javax.annotation.Nullable;

@KeepForSdk
/* loaded from: classes3.dex */
public class StringResourceValueReader {
    private final Resources zzeu;
    private final String zzev;

    public StringResourceValueReader(Context context) {
        Preconditions.checkNotNull(context);
        Resources resources = context.getResources();
        this.zzeu = resources;
        this.zzev = resources.getResourcePackageName(R.string.common_google_play_services_unknown_issue);
    }

    @KeepForSdk
    @Nullable
    public String getString(String str) {
        int identifier = this.zzeu.getIdentifier(str, TypedValues.Custom.S_STRING, this.zzev);
        if (identifier == 0) {
            return null;
        }
        return this.zzeu.getString(identifier);
    }
}
