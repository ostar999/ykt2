package com.app.hubert.guide;

import android.app.Activity;
import android.content.Context;
import androidx.fragment.app.Fragment;
import com.app.hubert.guide.core.Builder;

/* loaded from: classes2.dex */
public class NewbieGuide {
    public static final int FAILED = -1;
    public static final int SUCCESS = 1;
    public static final String TAG = "NewbieGuide";

    public static void resetLabel(Context context, String str) {
        context.getSharedPreferences(TAG, 0).edit().putInt(str, 0).apply();
    }

    public static Builder with(Activity activity) {
        return new Builder(activity);
    }

    public static Builder with(Fragment fragment) {
        return new Builder(fragment);
    }
}
