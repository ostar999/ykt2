package com.luck.picture.lib.basic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public final class PictureSelector {
    private final WeakReference<Activity> mActivity;
    private final WeakReference<Fragment> mFragment;

    private PictureSelector(Activity activity) {
        this(activity, null);
    }

    public static PictureSelector create(Context context) {
        return new PictureSelector((Activity) context);
    }

    public static ArrayList<LocalMedia> obtainSelectorList(Intent intent) {
        if (intent == null) {
            return new ArrayList<>();
        }
        ArrayList<LocalMedia> parcelableArrayListExtra = intent.getParcelableArrayListExtra(PictureConfig.EXTRA_RESULT_SELECTION);
        return parcelableArrayListExtra != null ? parcelableArrayListExtra : new ArrayList<>();
    }

    public static Intent putIntentResult(ArrayList<LocalMedia> arrayList) {
        return new Intent().putParcelableArrayListExtra(PictureConfig.EXTRA_RESULT_SELECTION, arrayList);
    }

    @Nullable
    public Activity getActivity() {
        return this.mActivity.get();
    }

    @Nullable
    public Fragment getFragment() {
        WeakReference<Fragment> weakReference = this.mFragment;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public PictureSelectionModel openCamera(int i2) {
        return new PictureSelectionModel(this, i2, true);
    }

    public PictureSelectionModel openGallery(int i2) {
        return new PictureSelectionModel(this, i2);
    }

    public PictureSelectionModel openPreview() {
        return new PictureSelectionModel(this);
    }

    private PictureSelector(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    public static PictureSelector create(Activity activity) {
        return new PictureSelector(activity);
    }

    private PictureSelector(Activity activity, Fragment fragment) {
        this.mActivity = new WeakReference<>(activity);
        this.mFragment = new WeakReference<>(fragment);
    }

    public static PictureSelector create(Fragment fragment) {
        return new PictureSelector(fragment);
    }
}
