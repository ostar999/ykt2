package com.luck.picture.lib.basic;

import android.app.Activity;
import android.content.Intent;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.luck.picture.lib.PictureOnlyCameraFragment;
import com.luck.picture.lib.PictureSelectorFragment;
import com.luck.picture.lib.PictureSelectorPreviewFragment;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CompressEngine;
import com.luck.picture.lib.engine.CropEngine;
import com.luck.picture.lib.engine.ExtendLoaderEngine;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.engine.SandboxFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnCameraInterceptListener;
import com.luck.picture.lib.interfaces.OnExternalPreviewEventListener;
import com.luck.picture.lib.interfaces.OnInjectLayoutResourceListener;
import com.luck.picture.lib.interfaces.OnMediaEditInterceptListener;
import com.luck.picture.lib.interfaces.OnPermissionsInterceptListener;
import com.luck.picture.lib.interfaces.OnPreviewInterceptListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.interfaces.OnSelectLimitTipsListener;
import com.luck.picture.lib.manager.SelectedManager;
import com.luck.picture.lib.style.PictureSelectorStyle;
import com.luck.picture.lib.utils.ActivityCompatHelper;
import com.luck.picture.lib.utils.DoubleUtils;
import com.luck.picture.lib.utils.SdkVersionUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class PictureSelectionModel {
    private final PictureSelectionConfig selectionConfig;
    private final PictureSelector selector;

    public PictureSelectionModel(PictureSelector pictureSelector) {
        this.selector = pictureSelector;
        this.selectionConfig = PictureSelectionConfig.getCleanInstance();
    }

    public PictureSelectorFragment build() {
        Activity activity = this.selector.getActivity();
        if (activity == null) {
            throw new NullPointerException("Activity cannot be null");
        }
        if (!(activity instanceof IBridgePictureBehavior)) {
            throw new NullPointerException("Use only build PictureSelectorFragment,Activity or Fragment interface needs to be implemented " + IBridgePictureBehavior.class);
        }
        PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
        pictureSelectionConfig.isResultListenerBack = false;
        pictureSelectionConfig.isActivityResultBack = true;
        PictureSelectionConfig.onResultCallListener = null;
        return new PictureSelectorFragment();
    }

    public PictureSelectorFragment buildLaunch(int i2, OnResultCallbackListener<LocalMedia> onResultCallbackListener) {
        Activity activity = this.selector.getActivity();
        if (activity == null) {
            throw new NullPointerException("Activity cannot be null");
        }
        if (onResultCallbackListener == null) {
            throw new NullPointerException("OnResultCallbackListener cannot be null");
        }
        PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
        pictureSelectionConfig.isResultListenerBack = true;
        pictureSelectionConfig.isActivityResultBack = false;
        PictureSelectionConfig.onResultCallListener = onResultCallbackListener;
        FragmentManager supportFragmentManager = activity instanceof AppCompatActivity ? ((AppCompatActivity) activity).getSupportFragmentManager() : activity instanceof FragmentActivity ? ((FragmentActivity) activity).getSupportFragmentManager() : null;
        if (supportFragmentManager == null) {
            throw new NullPointerException("FragmentManager cannot be null");
        }
        PictureSelectorFragment pictureSelectorFragment = new PictureSelectorFragment();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag(pictureSelectorFragment.getFragmentTag());
        if (fragmentFindFragmentByTag != null) {
            supportFragmentManager.beginTransaction().remove(fragmentFindFragmentByTag).commitAllowingStateLoss();
        }
        supportFragmentManager.beginTransaction().add(i2, pictureSelectorFragment, pictureSelectorFragment.getFragmentTag()).addToBackStack(pictureSelectorFragment.getFragmentTag()).commitAllowingStateLoss();
        return pictureSelectorFragment;
    }

    public void forResult(OnResultCallbackListener<LocalMedia> onResultCallbackListener) {
        if (DoubleUtils.isFastDoubleClick()) {
            return;
        }
        Activity activity = this.selector.getActivity();
        if (activity == null) {
            throw new NullPointerException("Activity cannot be null");
        }
        if (onResultCallbackListener == null) {
            throw new NullPointerException("OnResultCallbackListener cannot be null");
        }
        PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
        pictureSelectionConfig.isResultListenerBack = true;
        pictureSelectionConfig.isActivityResultBack = false;
        PictureSelectionConfig.onResultCallListener = onResultCallbackListener;
        if (!pictureSelectionConfig.isOnlyCamera) {
            if (PictureSelectionConfig.imageEngine == null) {
                throw new NullPointerException("imageEngine is null,Please implement ImageEngine");
            }
            activity.startActivity(new Intent(activity, (Class<?>) PictureSelectorSupporterActivity.class));
            activity.overridePendingTransition(PictureSelectionConfig.selectorStyle.getWindowAnimationStyle().activityEnterAnimation, R.anim.ps_anim_fade_in);
            return;
        }
        FragmentManager supportFragmentManager = activity instanceof AppCompatActivity ? ((AppCompatActivity) activity).getSupportFragmentManager() : activity instanceof FragmentActivity ? ((FragmentActivity) activity).getSupportFragmentManager() : null;
        if (supportFragmentManager == null) {
            throw new NullPointerException("FragmentManager cannot be null");
        }
        String str = PictureOnlyCameraFragment.TAG;
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag(str);
        if (fragmentFindFragmentByTag != null) {
            supportFragmentManager.beginTransaction().remove(fragmentFindFragmentByTag).commitAllowingStateLoss();
        }
        FragmentInjectManager.injectSystemRoomFragment(supportFragmentManager, str, PictureOnlyCameraFragment.newInstance());
    }

    public PictureSelectionModel isAutomaticTitleRecyclerTop(boolean z2) {
        this.selectionConfig.isAutomaticTitleRecyclerTop = z2;
        return this;
    }

    public PictureSelectionModel isBmp(boolean z2) {
        this.selectionConfig.isBmp = z2;
        return this;
    }

    public PictureSelectionModel isCameraAroundState(boolean z2) {
        this.selectionConfig.isCameraAroundState = z2;
        return this;
    }

    public PictureSelectionModel isCameraForegroundService(boolean z2) {
        this.selectionConfig.isCameraForegroundService = z2;
        return this;
    }

    public PictureSelectionModel isCameraRotateImage(boolean z2) {
        this.selectionConfig.isCameraRotateImage = z2;
        return this;
    }

    public PictureSelectionModel isDirectReturnSingle(boolean z2) {
        PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
        pictureSelectionConfig.isDirectReturnSingle = pictureSelectionConfig.selectionMode == 1 && z2;
        return this;
    }

    public PictureSelectionModel isDisplayCamera(boolean z2) {
        this.selectionConfig.isDisplayCamera = z2;
        return this;
    }

    public PictureSelectionModel isDisplayTimeAxis(boolean z2) {
        PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
        if (pictureSelectionConfig.isOnlyCamera) {
            pictureSelectionConfig.isDisplayTimeAxis = false;
        } else {
            pictureSelectionConfig.isDisplayTimeAxis = z2;
        }
        return this;
    }

    public PictureSelectionModel isEmptyResultReturn(boolean z2) {
        this.selectionConfig.isEmptyResultReturn = z2;
        return this;
    }

    public PictureSelectionModel isGif(boolean z2) {
        this.selectionConfig.isGif = z2;
        return this;
    }

    public PictureSelectionModel isHidePreviewDownload(boolean z2) {
        this.selectionConfig.isHidePreviewDownload = z2;
        return this;
    }

    public PictureSelectionModel isMaxSelectEnabledMask(boolean z2) {
        this.selectionConfig.isMaxSelectEnabledMask = z2;
        return this;
    }

    public PictureSelectionModel isOnlyObtainSandboxDir(boolean z2) {
        this.selectionConfig.isOnlySandboxDir = z2;
        return this;
    }

    public PictureSelectionModel isOpenClickSound(boolean z2) {
        PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
        pictureSelectionConfig.isOpenClickSound = !pictureSelectionConfig.isOnlyCamera && z2;
        return this;
    }

    public PictureSelectionModel isOriginalControl(boolean z2) {
        PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
        pictureSelectionConfig.isOriginalControl = z2;
        if (pictureSelectionConfig.isOnlyCamera && z2) {
            pictureSelectionConfig.isCheckOriginalImage = true;
        }
        return this;
    }

    public PictureSelectionModel isPageStrategy(boolean z2) {
        this.selectionConfig.isPageStrategy = z2;
        return this;
    }

    public PictureSelectionModel isPreviewAudio(boolean z2) {
        this.selectionConfig.isEnablePreviewAudio = z2;
        return this;
    }

    public PictureSelectionModel isPreviewFullScreenMode(boolean z2) {
        PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
        if (pictureSelectionConfig.isOnlyCamera) {
            pictureSelectionConfig.isPreviewFullScreenMode = false;
        } else {
            pictureSelectionConfig.isPreviewFullScreenMode = z2;
        }
        return this;
    }

    public PictureSelectionModel isPreviewImage(boolean z2) {
        this.selectionConfig.isEnablePreviewImage = z2;
        return this;
    }

    public PictureSelectionModel isPreviewVideo(boolean z2) {
        this.selectionConfig.isEnablePreviewVideo = z2;
        return this;
    }

    public PictureSelectionModel isPreviewZoomEffect(boolean z2) {
        PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
        if (pictureSelectionConfig.isOnlyCamera) {
            pictureSelectionConfig.isPreviewZoomEffect = false;
        } else {
            pictureSelectionConfig.isPreviewZoomEffect = z2;
        }
        return this;
    }

    public PictureSelectionModel isQuickCapture(boolean z2) {
        this.selectionConfig.isQuickCapture = z2;
        return this;
    }

    public PictureSelectionModel isSyncCover(boolean z2) {
        this.selectionConfig.isSyncCover = z2;
        return this;
    }

    public PictureSelectionModel isWebp(boolean z2) {
        this.selectionConfig.isWebp = z2;
        return this;
    }

    public PictureSelectionModel isWithSelectVideoImage(boolean z2) {
        PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
        pictureSelectionConfig.isWithVideoImage = pictureSelectionConfig.selectionMode != 1 && pictureSelectionConfig.chooseMode == SelectMimeType.ofAll() && z2;
        return this;
    }

    public PictureSelectionModel setCameraImageFormat(String str) {
        this.selectionConfig.cameraImageFormat = str;
        return this;
    }

    public PictureSelectionModel setCameraImageFormatForQ(String str) {
        this.selectionConfig.cameraImageFormatForQ = str;
        return this;
    }

    public PictureSelectionModel setCameraInterceptListener(OnCameraInterceptListener onCameraInterceptListener) {
        PictureSelectionConfig.onCameraInterceptListener = onCameraInterceptListener;
        return this;
    }

    public PictureSelectionModel setCameraVideoFormat(String str) {
        this.selectionConfig.cameraVideoFormat = str;
        return this;
    }

    public PictureSelectionModel setCameraVideoFormatForQ(String str) {
        this.selectionConfig.cameraVideoFormatForQ = str;
        return this;
    }

    public PictureSelectionModel setCompressEngine(CompressEngine compressEngine) {
        if (PictureSelectionConfig.compressEngine != compressEngine) {
            PictureSelectionConfig.compressEngine = compressEngine;
            this.selectionConfig.isCompressEngine = true;
        } else {
            this.selectionConfig.isCompressEngine = false;
        }
        return this;
    }

    public PictureSelectionModel setCropEngine(CropEngine cropEngine) {
        if (PictureSelectionConfig.cropEngine != cropEngine) {
            PictureSelectionConfig.cropEngine = cropEngine;
        }
        return this;
    }

    public PictureSelectionModel setEditMediaInterceptListener(OnMediaEditInterceptListener onMediaEditInterceptListener) {
        PictureSelectionConfig.onEditMediaEventListener = onMediaEditInterceptListener;
        return this;
    }

    public PictureSelectionModel setExtendLoaderEngine(ExtendLoaderEngine extendLoaderEngine) {
        if (PictureSelectionConfig.loaderDataEngine != extendLoaderEngine) {
            PictureSelectionConfig.loaderDataEngine = extendLoaderEngine;
            this.selectionConfig.isLoaderDataEngine = true;
        } else {
            this.selectionConfig.isLoaderDataEngine = false;
        }
        return this;
    }

    public PictureSelectionModel setExternalPreviewEventListener(OnExternalPreviewEventListener onExternalPreviewEventListener) {
        PictureSelectionConfig.onExternalPreviewEventListener = onExternalPreviewEventListener;
        return this;
    }

    public PictureSelectionModel setFilterMaxFileSize(long j2) {
        if (j2 >= 1048576) {
            this.selectionConfig.filterMaxFileSize = j2;
        } else {
            this.selectionConfig.filterMaxFileSize = j2 * 1024;
        }
        return this;
    }

    public PictureSelectionModel setFilterMinFileSize(long j2) {
        if (j2 >= 1048576) {
            this.selectionConfig.filterMinFileSize = j2;
        } else {
            this.selectionConfig.filterMinFileSize = j2 * 1024;
        }
        return this;
    }

    public PictureSelectionModel setFilterVideoMaxSecond(int i2) {
        this.selectionConfig.filterVideoMaxSecond = i2 * 1000;
        return this;
    }

    public PictureSelectionModel setFilterVideoMinSecond(int i2) {
        this.selectionConfig.filterVideoMinSecond = i2 * 1000;
        return this;
    }

    public PictureSelectionModel setImageEngine(ImageEngine imageEngine) {
        if (PictureSelectionConfig.imageEngine != imageEngine) {
            PictureSelectionConfig.imageEngine = imageEngine;
        }
        return this;
    }

    public PictureSelectionModel setImageSpanCount(int i2) {
        this.selectionConfig.imageSpanCount = i2;
        return this;
    }

    public PictureSelectionModel setInjectLayoutResourceListener(OnInjectLayoutResourceListener onInjectLayoutResourceListener) {
        this.selectionConfig.isInjectLayoutResource = onInjectLayoutResourceListener != null;
        PictureSelectionConfig.onLayoutResourceListener = onInjectLayoutResourceListener;
        return this;
    }

    public PictureSelectionModel setLanguage(int i2) {
        this.selectionConfig.language = i2;
        return this;
    }

    public PictureSelectionModel setMaxSelectNum(int i2) {
        PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
        if (pictureSelectionConfig.selectionMode == 1) {
            i2 = 1;
        }
        pictureSelectionConfig.maxSelectNum = i2;
        return this;
    }

    public PictureSelectionModel setMaxVideoSelectNum(int i2) {
        PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
        if (pictureSelectionConfig.chooseMode == SelectMimeType.ofVideo()) {
            i2 = 0;
        }
        pictureSelectionConfig.maxVideoSelectNum = i2;
        return this;
    }

    public PictureSelectionModel setMinAudioSelectNum(int i2) {
        this.selectionConfig.minAudioSelectNum = i2;
        return this;
    }

    public PictureSelectionModel setMinSelectNum(int i2) {
        this.selectionConfig.minSelectNum = i2;
        return this;
    }

    public PictureSelectionModel setMinVideoSelectNum(int i2) {
        this.selectionConfig.minVideoSelectNum = i2;
        return this;
    }

    public PictureSelectionModel setOfAllCameraType(int i2) {
        this.selectionConfig.ofAllCameraType = i2;
        return this;
    }

    public PictureSelectionModel setOutputAudioDir(String str) {
        this.selectionConfig.outPutAudioDir = str;
        return this;
    }

    public PictureSelectionModel setOutputAudioFileName(String str) {
        this.selectionConfig.outPutAudioFileName = str;
        return this;
    }

    public PictureSelectionModel setOutputCameraDir(String str) {
        this.selectionConfig.outPutCameraDir = str;
        return this;
    }

    public PictureSelectionModel setOutputCameraImageFileName(String str) {
        this.selectionConfig.outPutCameraImageFileName = str;
        return this;
    }

    public PictureSelectionModel setOutputCameraVideoFileName(String str) {
        this.selectionConfig.outPutCameraVideoFileName = str;
        return this;
    }

    public PictureSelectionModel setPermissionsInterceptListener(OnPermissionsInterceptListener onPermissionsInterceptListener) {
        PictureSelectionConfig.onPermissionsEventListener = onPermissionsInterceptListener;
        return this;
    }

    public PictureSelectionModel setPreviewInterceptListener(OnPreviewInterceptListener onPreviewInterceptListener) {
        PictureSelectionConfig.onPreviewInterceptListener = onPreviewInterceptListener;
        return this;
    }

    public PictureSelectionModel setQueryOnlyMimeType(String... strArr) {
        if (strArr != null && strArr.length > 0) {
            this.selectionConfig.queryOnlyList.addAll(Arrays.asList(strArr));
        }
        return this;
    }

    public PictureSelectionModel setQuerySandboxDir(String str) {
        this.selectionConfig.sandboxDir = str;
        return this;
    }

    public PictureSelectionModel setRecordVideoMaxSecond(int i2) {
        this.selectionConfig.recordVideoMaxSecond = i2;
        return this;
    }

    public PictureSelectionModel setRecordVideoMinSecond(int i2) {
        this.selectionConfig.recordVideoMinSecond = i2;
        return this;
    }

    public PictureSelectionModel setRecyclerAnimationMode(int i2) {
        this.selectionConfig.animationMode = i2;
        return this;
    }

    public PictureSelectionModel setRequestedOrientation(int i2) {
        this.selectionConfig.requestedOrientation = i2;
        return this;
    }

    public PictureSelectionModel setSandboxFileEngine(SandboxFileEngine sandboxFileEngine) {
        if (!SdkVersionUtils.isQ() || PictureSelectionConfig.sandboxFileEngine == sandboxFileEngine) {
            this.selectionConfig.isSandboxFileEngine = false;
        } else {
            PictureSelectionConfig.sandboxFileEngine = sandboxFileEngine;
            this.selectionConfig.isSandboxFileEngine = true;
        }
        return this;
    }

    public PictureSelectionModel setSelectLimitTipsListener(OnSelectLimitTipsListener onSelectLimitTipsListener) {
        PictureSelectionConfig.onSelectLimitTipsListener = onSelectLimitTipsListener;
        return this;
    }

    public PictureSelectionModel setSelectMaxDurationSecond(int i2) {
        this.selectionConfig.selectMaxDurationSecond = i2 * 1000;
        return this;
    }

    public PictureSelectionModel setSelectMaxFileSize(long j2) {
        if (j2 >= 1048576) {
            this.selectionConfig.selectMaxFileSize = j2;
        } else {
            this.selectionConfig.selectMaxFileSize = j2 * 1024;
        }
        return this;
    }

    public PictureSelectionModel setSelectMinDurationSecond(int i2) {
        this.selectionConfig.selectMinDurationSecond = i2 * 1000;
        return this;
    }

    public PictureSelectionModel setSelectMinFileSize(long j2) {
        if (j2 >= 1048576) {
            this.selectionConfig.selectMinFileSize = j2;
        } else {
            this.selectionConfig.selectMinFileSize = j2 * 1024;
        }
        return this;
    }

    public PictureSelectionModel setSelectedData(List<LocalMedia> list) {
        if (list == null) {
            return this;
        }
        PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
        if (pictureSelectionConfig.selectionMode == 1 && pictureSelectionConfig.isDirectReturnSingle) {
            SelectedManager.clear();
        } else {
            SelectedManager.getSelectedResult().addAll(new ArrayList(list));
        }
        return this;
    }

    public PictureSelectionModel setSelectionMode(int i2) {
        PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
        pictureSelectionConfig.selectionMode = i2;
        pictureSelectionConfig.maxSelectNum = i2 != 1 ? pictureSelectionConfig.maxSelectNum : 1;
        return this;
    }

    public PictureSelectionModel setSelectorUIStyle(PictureSelectorStyle pictureSelectorStyle) {
        if (pictureSelectorStyle != null) {
            PictureSelectionConfig.selectorStyle = pictureSelectorStyle;
        }
        return this;
    }

    @Deprecated
    public PictureSelectionModel setVideoQuality(int i2) {
        this.selectionConfig.videoQuality = i2;
        return this;
    }

    public void startActivityPreview(int i2, boolean z2, ArrayList<LocalMedia> arrayList) {
        if (DoubleUtils.isFastDoubleClick()) {
            return;
        }
        Activity activity = this.selector.getActivity();
        if (activity == null) {
            throw new NullPointerException("Activity cannot be null");
        }
        if (PictureSelectionConfig.imageEngine == null) {
            throw new NullPointerException("imageEngine is null,Please implement ImageEngine");
        }
        if (arrayList == null || arrayList.size() == 0) {
            throw new NullPointerException("preview data is null");
        }
        Intent intent = new Intent(activity, (Class<?>) PictureSelectorSupporterActivity.class);
        SelectedManager.addSelectedPreviewResult(arrayList);
        intent.putExtra(PictureConfig.EXTRA_EXTERNAL_PREVIEW, true);
        intent.putExtra(PictureConfig.EXTRA_PREVIEW_CURRENT_POSITION, i2);
        intent.putExtra(PictureConfig.EXTRA_EXTERNAL_PREVIEW_DISPLAY_DELETE, z2);
        activity.startActivity(intent);
        activity.overridePendingTransition(PictureSelectionConfig.selectorStyle.getWindowAnimationStyle().activityEnterAnimation, R.anim.ps_anim_fade_in);
    }

    public void startFragmentPreview(int i2, boolean z2, List<LocalMedia> list) {
        if (DoubleUtils.isFastDoubleClick()) {
            return;
        }
        Activity activity = this.selector.getActivity();
        if (activity == null) {
            throw new NullPointerException("Activity cannot be null");
        }
        if (PictureSelectionConfig.imageEngine == null) {
            throw new NullPointerException("imageEngine is null,Please implement ImageEngine");
        }
        if (list == null || list.size() == 0) {
            throw new NullPointerException("preview data is null");
        }
        FragmentManager supportFragmentManager = activity instanceof AppCompatActivity ? ((AppCompatActivity) activity).getSupportFragmentManager() : activity instanceof FragmentActivity ? ((FragmentActivity) activity).getSupportFragmentManager() : null;
        if (supportFragmentManager == null) {
            throw new NullPointerException("FragmentManager cannot be null");
        }
        String str = PictureSelectorPreviewFragment.TAG;
        if (ActivityCompatHelper.checkFragmentNonExits((FragmentActivity) activity, str)) {
            PictureSelectorPreviewFragment pictureSelectorPreviewFragmentNewInstance = PictureSelectorPreviewFragment.newInstance();
            ArrayList<LocalMedia> arrayList = new ArrayList<>(list);
            pictureSelectorPreviewFragmentNewInstance.setExternalPreviewData(i2, arrayList.size(), arrayList, z2);
            FragmentInjectManager.injectSystemRoomFragment(supportFragmentManager, str, pictureSelectorPreviewFragmentNewInstance);
        }
    }

    public PictureSelectionModel isPageStrategy(boolean z2, int i2) {
        PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
        pictureSelectionConfig.isPageStrategy = z2;
        if (i2 < 10) {
            i2 = 60;
        }
        pictureSelectionConfig.pageSize = i2;
        return this;
    }

    public PictureSelectionModel(PictureSelector pictureSelector, int i2) {
        this.selector = pictureSelector;
        PictureSelectionConfig cleanInstance = PictureSelectionConfig.getCleanInstance();
        this.selectionConfig = cleanInstance;
        cleanInstance.chooseMode = i2;
        setMaxVideoSelectNum(cleanInstance.maxVideoSelectNum);
    }

    public PictureSelectionModel isPageStrategy(boolean z2, boolean z3) {
        PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
        pictureSelectionConfig.isPageStrategy = z2;
        pictureSelectionConfig.isFilterInvalidFile = z3;
        return this;
    }

    public PictureSelectionModel isPageStrategy(boolean z2, int i2, boolean z3) {
        PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
        pictureSelectionConfig.isPageStrategy = z2;
        if (i2 < 10) {
            i2 = 60;
        }
        pictureSelectionConfig.pageSize = i2;
        pictureSelectionConfig.isFilterInvalidFile = z3;
        return this;
    }

    public PictureSelectionModel(PictureSelector pictureSelector, int i2, boolean z2) {
        this.selector = pictureSelector;
        PictureSelectionConfig cleanInstance = PictureSelectionConfig.getCleanInstance();
        this.selectionConfig = cleanInstance;
        cleanInstance.isOnlyCamera = z2;
        cleanInstance.chooseMode = i2;
        cleanInstance.isPreviewFullScreenMode = false;
        cleanInstance.isPreviewZoomEffect = false;
    }

    public void forResult(int i2) {
        FragmentManager supportFragmentManager;
        if (DoubleUtils.isFastDoubleClick()) {
            return;
        }
        Activity activity = this.selector.getActivity();
        if (activity != null) {
            PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
            pictureSelectionConfig.isResultListenerBack = false;
            pictureSelectionConfig.isActivityResultBack = true;
            if (pictureSelectionConfig.isOnlyCamera) {
                if (activity instanceof AppCompatActivity) {
                    supportFragmentManager = ((AppCompatActivity) activity).getSupportFragmentManager();
                } else {
                    supportFragmentManager = activity instanceof FragmentActivity ? ((FragmentActivity) activity).getSupportFragmentManager() : null;
                }
                if (supportFragmentManager != null) {
                    if (activity instanceof IBridgePictureBehavior) {
                        String str = PictureOnlyCameraFragment.TAG;
                        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag(str);
                        if (fragmentFindFragmentByTag != null) {
                            supportFragmentManager.beginTransaction().remove(fragmentFindFragmentByTag).commitAllowingStateLoss();
                        }
                        FragmentInjectManager.injectSystemRoomFragment(supportFragmentManager, str, PictureOnlyCameraFragment.newInstance());
                        return;
                    }
                    throw new NullPointerException("Use only camera openCamera mode,Activity or Fragment interface needs to be implemented " + IBridgePictureBehavior.class);
                }
                throw new NullPointerException("FragmentManager cannot be null");
            }
            if (PictureSelectionConfig.imageEngine != null) {
                activity.startActivityForResult(new Intent(activity, (Class<?>) PictureSelectorSupporterActivity.class), i2);
                activity.overridePendingTransition(PictureSelectionConfig.selectorStyle.getWindowAnimationStyle().activityEnterAnimation, R.anim.ps_anim_fade_in);
                return;
            }
            throw new NullPointerException("imageEngine is null,Please implement ImageEngine");
        }
        throw new NullPointerException("Activity cannot be null");
    }

    public void forResult(ActivityResultLauncher<Intent> activityResultLauncher) {
        FragmentManager supportFragmentManager;
        if (DoubleUtils.isFastDoubleClick()) {
            return;
        }
        Activity activity = this.selector.getActivity();
        if (activity == null) {
            throw new NullPointerException("Activity cannot be null");
        }
        if (activityResultLauncher != null) {
            PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
            pictureSelectionConfig.isResultListenerBack = false;
            pictureSelectionConfig.isActivityResultBack = true;
            if (pictureSelectionConfig.isOnlyCamera) {
                if (activity instanceof AppCompatActivity) {
                    supportFragmentManager = ((AppCompatActivity) activity).getSupportFragmentManager();
                } else {
                    supportFragmentManager = activity instanceof FragmentActivity ? ((FragmentActivity) activity).getSupportFragmentManager() : null;
                }
                if (supportFragmentManager != null) {
                    if (activity instanceof IBridgePictureBehavior) {
                        String str = PictureOnlyCameraFragment.TAG;
                        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag(str);
                        if (fragmentFindFragmentByTag != null) {
                            supportFragmentManager.beginTransaction().remove(fragmentFindFragmentByTag).commitAllowingStateLoss();
                        }
                        FragmentInjectManager.injectSystemRoomFragment(supportFragmentManager, str, PictureOnlyCameraFragment.newInstance());
                        return;
                    }
                    throw new NullPointerException("Use only camera openCamera mode,Activity or Fragment interface needs to be implemented " + IBridgePictureBehavior.class);
                }
                throw new NullPointerException("FragmentManager cannot be null");
            }
            if (PictureSelectionConfig.imageEngine != null) {
                activityResultLauncher.launch(new Intent(activity, (Class<?>) PictureSelectorSupporterActivity.class));
                activity.overridePendingTransition(PictureSelectionConfig.selectorStyle.getWindowAnimationStyle().activityEnterAnimation, R.anim.ps_anim_fade_in);
                return;
            }
            throw new NullPointerException("imageEngine is null,Please implement ImageEngine");
        }
        throw new NullPointerException("ActivityResultLauncher cannot be null");
    }
}
