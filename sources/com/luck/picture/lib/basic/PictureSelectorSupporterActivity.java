package com.luck.picture.lib.basic;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.luck.picture.lib.PictureSelectorFragment;
import com.luck.picture.lib.PictureSelectorPreviewFragment;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.immersive.ImmersiveManager;
import com.luck.picture.lib.language.PictureLanguageUtils;
import com.luck.picture.lib.manager.SelectedManager;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.utils.StyleUtils;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class PictureSelectorSupporterActivity extends AppCompatActivity {
    private void immersive() {
        SelectMainStyle selectMainStyle = PictureSelectionConfig.selectorStyle.getSelectMainStyle();
        int statusBarColor = selectMainStyle.getStatusBarColor();
        int navigationBarColor = selectMainStyle.getNavigationBarColor();
        boolean zIsDarkStatusBarBlack = selectMainStyle.isDarkStatusBarBlack();
        if (!StyleUtils.checkStyleValidity(statusBarColor)) {
            statusBarColor = ContextCompat.getColor(this, R.color.ps_color_grey);
        }
        if (!StyleUtils.checkStyleValidity(navigationBarColor)) {
            navigationBarColor = ContextCompat.getColor(this, R.color.ps_color_grey);
        }
        ImmersiveManager.immersiveAboveAPI23(this, statusBarColor, navigationBarColor, zIsDarkStatusBarBlack);
    }

    private void setupFragment() {
        if (!getIntent().hasExtra(PictureConfig.EXTRA_EXTERNAL_PREVIEW) || !getIntent().getBooleanExtra(PictureConfig.EXTRA_EXTERNAL_PREVIEW, false)) {
            FragmentInjectManager.injectFragment(this, PictureSelectorFragment.TAG, PictureSelectorFragment.newInstance());
            return;
        }
        int intExtra = getIntent().getIntExtra(PictureConfig.EXTRA_PREVIEW_CURRENT_POSITION, 0);
        PictureSelectorPreviewFragment pictureSelectorPreviewFragmentNewInstance = PictureSelectorPreviewFragment.newInstance();
        ArrayList<LocalMedia> arrayList = new ArrayList<>(SelectedManager.getSelectedPreviewResult());
        pictureSelectorPreviewFragmentNewInstance.setExternalPreviewData(intExtra, arrayList.size(), arrayList, getIntent().getBooleanExtra(PictureConfig.EXTRA_EXTERNAL_PREVIEW_DISPLAY_DELETE, false));
        FragmentInjectManager.injectFragment(this, PictureSelectorPreviewFragment.TAG, pictureSelectorPreviewFragmentNewInstance);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    public void attachBaseContext(Context context) {
        super.attachBaseContext(PictureContextWrapper.wrap(context, PictureSelectionConfig.getInstance().language));
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        overridePendingTransition(0, PictureSelectionConfig.selectorStyle.getWindowAnimationStyle().activityExitAnimation);
    }

    public void initAppLanguage() {
        PictureSelectionConfig pictureSelectionConfig = PictureSelectionConfig.getInstance();
        int i2 = pictureSelectionConfig.language;
        if (i2 == -2 || pictureSelectionConfig.isOnlyCamera) {
            return;
        }
        PictureLanguageUtils.setAppLanguage(this, i2);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        super.onConfigurationChanged(configuration);
        initAppLanguage();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        immersive();
        setContentView(R.layout.ps_activity_container);
        setupFragment();
    }
}
