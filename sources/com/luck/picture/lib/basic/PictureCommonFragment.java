package com.luck.picture.lib.basic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import cn.hutool.core.text.StrPool;
import com.hjq.permissions.Permission;
import com.luck.picture.lib.R;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.config.Crop;
import com.luck.picture.lib.config.CustomIntentKey;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.dialog.PhotoItemSelectedDialog;
import com.luck.picture.lib.dialog.PictureLoadingDialog;
import com.luck.picture.lib.dialog.RemindDialog;
import com.luck.picture.lib.engine.PictureSelectorEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.immersive.ImmersiveManager;
import com.luck.picture.lib.interfaces.OnCallbackIndexListener;
import com.luck.picture.lib.interfaces.OnCallbackListener;
import com.luck.picture.lib.interfaces.OnItemClickListener;
import com.luck.picture.lib.interfaces.OnPermissionsInterceptListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.interfaces.OnSelectLimitTipsListener;
import com.luck.picture.lib.language.PictureLanguageUtils;
import com.luck.picture.lib.loader.IBridgeMediaLoader;
import com.luck.picture.lib.manager.SelectedManager;
import com.luck.picture.lib.permissions.PermissionChecker;
import com.luck.picture.lib.permissions.PermissionConfig;
import com.luck.picture.lib.permissions.PermissionResultCallback;
import com.luck.picture.lib.permissions.PermissionUtil;
import com.luck.picture.lib.service.ForegroundService;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
import com.luck.picture.lib.thread.PictureThreadUtils;
import com.luck.picture.lib.utils.ActivityCompatHelper;
import com.luck.picture.lib.utils.BitmapUtils;
import com.luck.picture.lib.utils.MediaStoreUtils;
import com.luck.picture.lib.utils.MediaUtils;
import com.luck.picture.lib.utils.PictureFileUtils;
import com.luck.picture.lib.utils.SdkVersionUtils;
import com.luck.picture.lib.utils.ToastUtils;
import com.luck.picture.lib.utils.ValueOf;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public abstract class PictureCommonFragment extends Fragment implements IPictureSelectorCommonEvent {
    public static final String TAG = "PictureCommonFragment";
    protected PictureSelectionConfig config;
    private long enterAnimDuration;
    protected IBridgePictureBehavior iBridgePictureBehavior;
    protected IBridgeMediaLoader mLoader;
    private PictureLoadingDialog mLoadingDialog;
    protected int mPage = 1;
    private PermissionResultCallback mPermissionResultCallback;
    private int soundID;
    private SoundPool soundPool;

    public static class SelectorResult {
        public int mResultCode;
        public Intent mResultData;

        public SelectorResult(int i2, Intent intent) {
            this.mResultCode = i2;
            this.mResultData = intent;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LocalMedia buildLocalMedia() throws Throwable {
        File file;
        String mimeTypeFromMediaUrl;
        long jCurrentTimeMillis;
        long jGenerateSoundsBucketId;
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return null;
        }
        if (PictureMimeType.isContent(this.config.cameraPath)) {
            file = new File(PictureFileUtils.getPath(getActivity(), Uri.parse(this.config.cameraPath)));
            mimeTypeFromMediaUrl = MediaUtils.getMimeTypeFromMediaUrl(file.getAbsolutePath());
            int iLastIndexOf = this.config.cameraPath.lastIndexOf("/") + 1;
            jCurrentTimeMillis = iLastIndexOf > 0 ? ValueOf.toLong(this.config.cameraPath.substring(iLastIndexOf)) : System.currentTimeMillis();
            jGenerateSoundsBucketId = PictureMimeType.isHasAudio(mimeTypeFromMediaUrl) ? MediaUtils.generateSoundsBucketId(getContext(), file, "") : MediaUtils.generateCameraBucketId(getContext(), file, "");
        } else {
            file = new File(this.config.cameraPath);
            mimeTypeFromMediaUrl = MediaUtils.getMimeTypeFromMediaUrl(file.getAbsolutePath());
            jCurrentTimeMillis = System.currentTimeMillis();
            jGenerateSoundsBucketId = PictureMimeType.isHasAudio(mimeTypeFromMediaUrl) ? MediaUtils.generateSoundsBucketId(getContext(), file, this.config.outPutCameraDir) : MediaUtils.generateCameraBucketId(getContext(), file, this.config.outPutCameraDir);
        }
        String str = mimeTypeFromMediaUrl;
        long j2 = jCurrentTimeMillis;
        long j3 = jGenerateSoundsBucketId;
        if (this.config.isCameraRotateImage && PictureMimeType.isHasImage(str) && !PictureMimeType.isContent(this.config.cameraPath)) {
            BitmapUtils.rotateImage(getContext(), this.config.cameraPath);
        }
        MediaExtraInfo videoSize = PictureMimeType.isHasVideo(str) ? MediaUtils.getVideoSize(getContext(), this.config.cameraPath) : PictureMimeType.isHasAudio(str) ? MediaUtils.getAudioSize(getContext(), this.config.cameraPath) : MediaUtils.getImageSize(getContext(), this.config.cameraPath);
        LocalMedia localMedia = LocalMedia.parseLocalMedia(j2, this.config.cameraPath, file.getAbsolutePath(), file.getName(), MediaUtils.generateCameraFolderName(file.getAbsolutePath()), videoSize.getDuration(), this.config.chooseMode, str, videoSize.getWidth(), videoSize.getHeight(), file.length(), j3, file.lastModified() / 1000);
        if (SdkVersionUtils.isQ()) {
            localMedia.setSandboxPath(PictureMimeType.isContent(this.config.cameraPath) ? null : this.config.cameraPath);
        }
        return localMedia;
    }

    private boolean checkCompleteSelectLimit() {
        PictureSelectionConfig pictureSelectionConfig = this.config;
        if (pictureSelectionConfig.selectionMode == 2 && !pictureSelectionConfig.isOnlyCamera) {
            if (pictureSelectionConfig.isWithVideoImage) {
                ArrayList<LocalMedia> selectedResult = SelectedManager.getSelectedResult();
                int i2 = 0;
                int i3 = 0;
                for (int i4 = 0; i4 < selectedResult.size(); i4++) {
                    if (PictureMimeType.isHasVideo(selectedResult.get(i4).getMimeType())) {
                        i3++;
                    } else {
                        i2++;
                    }
                }
                PictureSelectionConfig pictureSelectionConfig2 = this.config;
                int i5 = pictureSelectionConfig2.minSelectNum;
                if (i5 > 0 && i2 < i5) {
                    if (PictureSelectionConfig.onSelectLimitTipsListener.onSelectLimitTips(getContext(), this.config, 5)) {
                        return true;
                    }
                    RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_min_img_num, String.valueOf(this.config.minSelectNum)));
                    return true;
                }
                int i6 = pictureSelectionConfig2.minVideoSelectNum;
                if (i6 > 0 && i3 < i6) {
                    if (PictureSelectionConfig.onSelectLimitTipsListener.onSelectLimitTips(getContext(), this.config, 7)) {
                        return true;
                    }
                    RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_min_video_num, String.valueOf(this.config.minVideoSelectNum)));
                    return true;
                }
            } else {
                String topResultMimeType = SelectedManager.getTopResultMimeType();
                if (PictureMimeType.isHasImage(topResultMimeType) && this.config.minSelectNum > 0 && SelectedManager.getCount() < this.config.minSelectNum) {
                    OnSelectLimitTipsListener onSelectLimitTipsListener = PictureSelectionConfig.onSelectLimitTipsListener;
                    if (onSelectLimitTipsListener != null && onSelectLimitTipsListener.onSelectLimitTips(getContext(), this.config, 5)) {
                        return true;
                    }
                    RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_min_img_num, String.valueOf(this.config.minSelectNum)));
                    return true;
                }
                if (PictureMimeType.isHasVideo(topResultMimeType) && this.config.minVideoSelectNum > 0 && SelectedManager.getCount() < this.config.minVideoSelectNum) {
                    OnSelectLimitTipsListener onSelectLimitTipsListener2 = PictureSelectionConfig.onSelectLimitTipsListener;
                    if (onSelectLimitTipsListener2 != null && onSelectLimitTipsListener2.onSelectLimitTips(getContext(), this.config, 7)) {
                        return true;
                    }
                    RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_min_video_num, String.valueOf(this.config.minVideoSelectNum)));
                    return true;
                }
                if (PictureMimeType.isHasAudio(topResultMimeType) && this.config.minAudioSelectNum > 0 && SelectedManager.getCount() < this.config.minAudioSelectNum) {
                    OnSelectLimitTipsListener onSelectLimitTipsListener3 = PictureSelectionConfig.onSelectLimitTipsListener;
                    if (onSelectLimitTipsListener3 != null && onSelectLimitTipsListener3.onSelectLimitTips(getContext(), this.config, 12)) {
                        return true;
                    }
                    RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_min_audio_num, String.valueOf(this.config.minAudioSelectNum)));
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkCompressValidity() {
        if (PictureSelectionConfig.compressEngine != null) {
            for (int i2 = 0; i2 < SelectedManager.getCount(); i2++) {
                if (PictureMimeType.isHasImage(SelectedManager.getSelectedResult().get(i2).getMimeType())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkCropValidity() {
        if (PictureSelectionConfig.cropEngine != null) {
            if (SelectedManager.getCount() == 1) {
                return PictureMimeType.isHasImage(SelectedManager.getTopResultMimeType());
            }
            for (int i2 = 0; i2 < SelectedManager.getCount(); i2++) {
                if (PictureMimeType.isHasImage(SelectedManager.getSelectedResult().get(i2).getMimeType())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkSelectLimit(LocalMedia localMedia) {
        if (PictureMimeType.isHasVideo(localMedia.getMimeType()) || PictureMimeType.isHasAudio(localMedia.getMimeType())) {
            if (this.config.selectMaxDurationSecond > 0 && localMedia.getDuration() > this.config.selectMaxDurationSecond) {
                OnSelectLimitTipsListener onSelectLimitTipsListener = PictureSelectionConfig.onSelectLimitTipsListener;
                if (onSelectLimitTipsListener != null && onSelectLimitTipsListener.onSelectLimitTips(getContext(), this.config, 10)) {
                    return true;
                }
                int i2 = this.config.selectMaxDurationSecond / 1000;
                if (PictureMimeType.isHasVideo(localMedia.getMimeType())) {
                    RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_select_video_max_second, Integer.valueOf(i2)));
                } else {
                    RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_select_audio_max_second, Integer.valueOf(i2)));
                }
                return true;
            }
            int i3 = this.config.selectMinDurationSecond;
            if (i3 > 0) {
                int i4 = i3 / 1000;
                if (localMedia.getDuration() < this.config.selectMinDurationSecond) {
                    OnSelectLimitTipsListener onSelectLimitTipsListener2 = PictureSelectionConfig.onSelectLimitTipsListener;
                    if (onSelectLimitTipsListener2 != null && onSelectLimitTipsListener2.onSelectLimitTips(getContext(), this.config, 11)) {
                        return true;
                    }
                    if (PictureMimeType.isHasVideo(localMedia.getMimeType())) {
                        RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_select_video_min_second, Integer.valueOf(i4)));
                    } else {
                        RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_select_audio_min_second, Integer.valueOf(i4)));
                    }
                    return true;
                }
            }
        }
        if (this.config.selectMaxFileSize > 0 && localMedia.getSize() > this.config.selectMaxFileSize) {
            OnSelectLimitTipsListener onSelectLimitTipsListener3 = PictureSelectionConfig.onSelectLimitTipsListener;
            if (onSelectLimitTipsListener3 != null && onSelectLimitTipsListener3.onSelectLimitTips(getContext(), this.config, 1)) {
                return true;
            }
            RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_select_max_size, PictureFileUtils.formatFileSize(this.config.selectMaxFileSize, 1)));
            return true;
        }
        if (this.config.selectMinFileSize <= 0 || localMedia.getSize() >= this.config.selectMinFileSize) {
            return false;
        }
        OnSelectLimitTipsListener onSelectLimitTipsListener4 = PictureSelectionConfig.onSelectLimitTipsListener;
        if (onSelectLimitTipsListener4 != null && onSelectLimitTipsListener4.onSelectLimitTips(getContext(), this.config, 2)) {
            return true;
        }
        RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_select_min_size, PictureFileUtils.formatFileSize(this.config.selectMinFileSize, 1)));
        return true;
    }

    private void copyExternalPathToAppInDirFor29(final ArrayList<LocalMedia> arrayList) {
        showLoading();
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<ArrayList<LocalMedia>>() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.12
            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public ArrayList<LocalMedia> doInBackground() {
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    int i3 = i2;
                    PictureSelectionConfig.sandboxFileEngine.onStartSandboxFileTransform(PictureCommonFragment.this.getContext(), PictureCommonFragment.this.config.isCheckOriginalImage, i3, (LocalMedia) arrayList.get(i2), new OnCallbackIndexListener<LocalMedia>() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.12.1
                        @Override // com.luck.picture.lib.interfaces.OnCallbackIndexListener
                        public void onCall(LocalMedia localMedia, int i4) {
                            LocalMedia localMedia2 = (LocalMedia) arrayList.get(i4);
                            localMedia2.setSandboxPath(localMedia.getSandboxPath());
                            if (PictureCommonFragment.this.config.isCheckOriginalImage) {
                                localMedia2.setOriginalPath(localMedia.getOriginalPath());
                                localMedia2.setOriginal(!TextUtils.isEmpty(localMedia.getOriginalPath()));
                            }
                        }
                    });
                }
                return arrayList;
            }

            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public void onSuccess(ArrayList<LocalMedia> arrayList2) {
                PictureThreadUtils.cancel(this);
                PictureCommonFragment.this.onCallBackResult(arrayList2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void copyOutputAudioToDir() {
        String str;
        try {
            if (TextUtils.isEmpty(this.config.outPutAudioDir) || !PictureMimeType.isContent(this.config.cameraPath)) {
                return;
            }
            InputStream contentResolverOpenInputStream = PictureContentResolver.getContentResolverOpenInputStream(getContext(), Uri.parse(this.config.cameraPath));
            if (TextUtils.isEmpty(this.config.outPutAudioFileName)) {
                str = "";
            } else {
                PictureSelectionConfig pictureSelectionConfig = this.config;
                if (pictureSelectionConfig.isOnlyCamera) {
                    str = pictureSelectionConfig.outPutAudioFileName;
                } else {
                    str = System.currentTimeMillis() + StrPool.UNDERLINE + this.config.outPutAudioFileName;
                }
            }
            Context context = getContext();
            PictureSelectionConfig pictureSelectionConfig2 = this.config;
            File fileCreateCameraFile = PictureFileUtils.createCameraFile(context, pictureSelectionConfig2.chooseMode, str, "", pictureSelectionConfig2.outPutAudioDir);
            if (PictureFileUtils.writeFileFromIS(contentResolverOpenInputStream, new FileOutputStream(fileCreateCameraFile.getAbsolutePath()))) {
                MediaUtils.deleteUri(getContext(), this.config.cameraPath);
                this.config.cameraPath = fileCreateCameraFile.getAbsolutePath();
            }
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    private void createCompressEngine() {
        PictureSelectorEngine pictureSelectorEngine;
        if (PictureSelectionConfig.getInstance().isCompressEngine && PictureSelectionConfig.compressEngine == null && (pictureSelectorEngine = PictureAppMaster.getInstance().getPictureSelectorEngine()) != null) {
            PictureSelectionConfig.compressEngine = pictureSelectorEngine.createCompressEngine();
        }
    }

    private void createImageLoaderEngine() {
        PictureSelectorEngine pictureSelectorEngine;
        if (PictureSelectionConfig.imageEngine != null || (pictureSelectorEngine = PictureAppMaster.getInstance().getPictureSelectorEngine()) == null) {
            return;
        }
        PictureSelectionConfig.imageEngine = pictureSelectorEngine.createImageLoaderEngine();
    }

    private void createLayoutResourceListener() {
        PictureSelectorEngine pictureSelectorEngine;
        if (PictureSelectionConfig.getInstance().isInjectLayoutResource && PictureSelectionConfig.onLayoutResourceListener == null && (pictureSelectorEngine = PictureAppMaster.getInstance().getPictureSelectorEngine()) != null) {
            PictureSelectionConfig.onLayoutResourceListener = pictureSelectorEngine.createLayoutResourceListener();
        }
    }

    private void createLoaderDataEngine() {
        PictureSelectorEngine pictureSelectorEngine;
        if (PictureSelectionConfig.getInstance().isLoaderDataEngine && PictureSelectionConfig.loaderDataEngine == null && (pictureSelectorEngine = PictureAppMaster.getInstance().getPictureSelectorEngine()) != null) {
            PictureSelectionConfig.loaderDataEngine = pictureSelectorEngine.createLoaderDataEngine();
        }
    }

    private void createResultCallbackListener() {
        PictureSelectorEngine pictureSelectorEngine;
        if (PictureSelectionConfig.getInstance().isResultListenerBack && PictureSelectionConfig.onResultCallListener == null && (pictureSelectorEngine = PictureAppMaster.getInstance().getPictureSelectorEngine()) != null) {
            PictureSelectionConfig.onResultCallListener = pictureSelectorEngine.getResultCallbackListener();
        }
    }

    private void createSandboxFileEngine() {
        PictureSelectorEngine pictureSelectorEngine;
        if (PictureSelectionConfig.getInstance().isSandboxFileEngine && PictureSelectionConfig.sandboxFileEngine == null && (pictureSelectorEngine = PictureAppMaster.getInstance().getPictureSelectorEngine()) != null) {
            PictureSelectionConfig.sandboxFileEngine = pictureSelectorEngine.createSandboxFileEngine();
        }
    }

    private void dispatchHandleCamera(final Intent intent) {
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<LocalMedia>() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.10
            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public LocalMedia doInBackground() {
                String outputPath = PictureCommonFragment.this.getOutputPath(intent);
                if (!TextUtils.isEmpty(outputPath)) {
                    PictureCommonFragment.this.config.cameraPath = outputPath;
                }
                if (TextUtils.isEmpty(PictureCommonFragment.this.config.cameraPath)) {
                    return null;
                }
                if (PictureCommonFragment.this.config.chooseMode == SelectMimeType.ofAudio()) {
                    PictureCommonFragment.this.copyOutputAudioToDir();
                }
                return PictureCommonFragment.this.buildLocalMedia();
            }

            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public void onSuccess(LocalMedia localMedia) {
                PictureThreadUtils.cancel(this);
                if (localMedia != null) {
                    PictureCommonFragment.this.dispatchCameraMediaResult(localMedia);
                    PictureCommonFragment.this.onScannerScanFile(localMedia);
                }
            }
        });
    }

    @SuppressLint({"StringFormatInvalid"})
    private static String getTipsMsg(Context context, String str, int i2) {
        return PictureMimeType.isHasVideo(str) ? context.getString(R.string.ps_message_video_max_num, String.valueOf(i2)) : PictureMimeType.isHasAudio(str) ? context.getString(R.string.ps_message_audio_max_num, String.valueOf(i2)) : context.getString(R.string.ps_message_max_num, String.valueOf(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void interceptCameraEvent(int i2) {
        ForegroundService.startForegroundService(getContext());
        PictureSelectionConfig.onCameraInterceptListener.openCamera(this, i2, 909);
    }

    private void mergeOriginalImage(ArrayList<LocalMedia> arrayList) {
        if (this.config.isCheckOriginalImage) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                LocalMedia localMedia = arrayList.get(i2);
                localMedia.setOriginal(true);
                localMedia.setOriginalPath(localMedia.getPath());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCallBackResult(ArrayList<LocalMedia> arrayList) {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        dismissLoading();
        if (this.config.isActivityResultBack) {
            getActivity().setResult(-1, PictureSelector.putIntentResult(arrayList));
            onSelectFinish(-1, arrayList);
        } else {
            OnResultCallbackListener<LocalMedia> onResultCallbackListener = PictureSelectionConfig.onResultCallListener;
            if (onResultCallbackListener != null) {
                onResultCallbackListener.onResult(arrayList);
            }
        }
        onExitPictureSelector();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScannerScanFile(LocalMedia localMedia) {
        int dCIMLastImageId;
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        if (SdkVersionUtils.isQ()) {
            if (PictureMimeType.isHasVideo(localMedia.getMimeType()) && PictureMimeType.isContent(this.config.cameraPath)) {
                new PictureMediaScannerConnection(getActivity(), localMedia.getRealPath());
                return;
            }
            return;
        }
        new PictureMediaScannerConnection(getActivity(), PictureMimeType.isContent(this.config.cameraPath) ? localMedia.getRealPath() : this.config.cameraPath);
        if (!PictureMimeType.isHasImage(localMedia.getMimeType()) || (dCIMLastImageId = MediaUtils.getDCIMLastImageId(getContext())) == -1) {
            return;
        }
        MediaUtils.removeMedia(getContext(), dCIMLastImageId);
    }

    private void playClickEffect() {
        SoundPool soundPool = this.soundPool;
        if (soundPool == null || !this.config.isOpenClickSound) {
            return;
        }
        soundPool.play(this.soundID, 0.1f, 0.5f, 0, 1, 1.0f);
    }

    private void releaseSoundPool() {
        try {
            SoundPool soundPool = this.soundPool;
            if (soundPool != null) {
                soundPool.release();
                this.soundPool = null;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void setTranslucentStatusBar() {
        if (this.config.isPreviewFullScreenMode) {
            ImmersiveManager.translucentStatusBar(getActivity(), PictureSelectionConfig.selectorStyle.getSelectMainStyle().isDarkStatusBarBlack());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startCameraImageCapture() {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        if (PictureSelectionConfig.onCameraInterceptListener != null) {
            ForegroundService.startForegroundService(getContext());
            interceptCameraEvent(1);
            return;
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            ForegroundService.startForegroundService(getContext());
            Uri uriCreateCameraOutImageUri = MediaStoreUtils.createCameraOutImageUri(getContext(), this.config);
            if (uriCreateCameraOutImageUri != null) {
                if (this.config.isCameraAroundState) {
                    intent.putExtra(PictureConfig.CAMERA_FACING, 1);
                }
                intent.putExtra("output", uriCreateCameraOutImageUri);
                startActivityForResult(intent, 909);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startCameraRecordSound() {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        if (PictureSelectionConfig.onCameraInterceptListener != null) {
            ForegroundService.startForegroundService(getContext());
            interceptCameraEvent(3);
            return;
        }
        Intent intent = new Intent("android.provider.MediaStore.RECORD_SOUND");
        if (intent.resolveActivity(getActivity().getPackageManager()) == null) {
            ToastUtils.showToast(getContext(), "The system is missing a recording component");
        } else {
            ForegroundService.startForegroundService(getContext());
            startActivityForResult(intent, 909);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startCameraVideoCapture() {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        if (PictureSelectionConfig.onCameraInterceptListener != null) {
            ForegroundService.startForegroundService(getContext());
            interceptCameraEvent(2);
            return;
        }
        Intent intent = new Intent("android.media.action.VIDEO_CAPTURE");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            ForegroundService.startForegroundService(getContext());
            Uri uriCreateCameraOutVideoUri = MediaStoreUtils.createCameraOutVideoUri(getContext(), this.config);
            if (uriCreateCameraOutVideoUri != null) {
                intent.putExtra("output", uriCreateCameraOutVideoUri);
                if (this.config.isCameraAroundState) {
                    intent.putExtra(PictureConfig.CAMERA_FACING, 1);
                }
                intent.putExtra(PictureConfig.EXTRA_QUICK_CAPTURE, this.config.isQuickCapture);
                intent.putExtra("android.intent.extra.durationLimit", this.config.recordVideoMaxSecond);
                intent.putExtra("android.intent.extra.videoQuality", this.config.videoQuality);
                startActivityForResult(intent, 909);
            }
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    @SuppressLint({"StringFormatInvalid"})
    public boolean checkOnlyMimeTypeValidity(boolean z2, String str, String str2, long j2) {
        int i2;
        int i3;
        int i4;
        int i5;
        if (!PictureMimeType.isMimeTypeSame(str2, str)) {
            OnSelectLimitTipsListener onSelectLimitTipsListener = PictureSelectionConfig.onSelectLimitTipsListener;
            if (onSelectLimitTipsListener != null && onSelectLimitTipsListener.onSelectLimitTips(getContext(), this.config, 3)) {
                return true;
            }
            RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_rule));
            return true;
        }
        if (!PictureMimeType.isHasVideo(str2) || this.config.maxVideoSelectNum <= 0) {
            if (!z2 && SelectedManager.getSelectedResult().size() >= this.config.maxSelectNum) {
                OnSelectLimitTipsListener onSelectLimitTipsListener2 = PictureSelectionConfig.onSelectLimitTipsListener;
                if (onSelectLimitTipsListener2 != null && onSelectLimitTipsListener2.onSelectLimitTips(getContext(), this.config, 4)) {
                    return true;
                }
                RemindDialog.showTipsDialog(getContext(), getTipsMsg(getContext(), str2, this.config.maxSelectNum));
                return true;
            }
            if (PictureMimeType.isHasVideo(str)) {
                if (!z2 && (i3 = this.config.filterVideoMinSecond) > 0 && j2 < i3) {
                    OnSelectLimitTipsListener onSelectLimitTipsListener3 = PictureSelectionConfig.onSelectLimitTipsListener;
                    if (onSelectLimitTipsListener3 != null && onSelectLimitTipsListener3.onSelectLimitTips(getContext(), this.config, 9)) {
                        return true;
                    }
                    RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_choose_min_seconds, Integer.valueOf(this.config.filterVideoMinSecond / 1000)));
                    return true;
                }
                if (!z2 && (i2 = this.config.filterVideoMaxSecond) > 0 && j2 > i2) {
                    OnSelectLimitTipsListener onSelectLimitTipsListener4 = PictureSelectionConfig.onSelectLimitTipsListener;
                    if (onSelectLimitTipsListener4 != null && onSelectLimitTipsListener4.onSelectLimitTips(getContext(), this.config, 8)) {
                        return true;
                    }
                    RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_choose_max_seconds, Integer.valueOf(this.config.filterVideoMaxSecond / 1000)));
                    return true;
                }
            }
        } else {
            if (!z2 && SelectedManager.getSelectedResult().size() >= this.config.maxVideoSelectNum) {
                OnSelectLimitTipsListener onSelectLimitTipsListener5 = PictureSelectionConfig.onSelectLimitTipsListener;
                if (onSelectLimitTipsListener5 != null && onSelectLimitTipsListener5.onSelectLimitTips(getContext(), this.config, 6)) {
                    return true;
                }
                RemindDialog.showTipsDialog(getContext(), getTipsMsg(getContext(), str2, this.config.maxVideoSelectNum));
                return true;
            }
            if (!z2 && (i5 = this.config.filterVideoMinSecond) > 0 && j2 < i5) {
                OnSelectLimitTipsListener onSelectLimitTipsListener6 = PictureSelectionConfig.onSelectLimitTipsListener;
                if (onSelectLimitTipsListener6 != null && onSelectLimitTipsListener6.onSelectLimitTips(getContext(), this.config, 9)) {
                    return true;
                }
                RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_choose_min_seconds, Integer.valueOf(this.config.filterVideoMinSecond / 1000)));
                return true;
            }
            if (!z2 && (i4 = this.config.filterVideoMaxSecond) > 0 && j2 > i4) {
                OnSelectLimitTipsListener onSelectLimitTipsListener7 = PictureSelectionConfig.onSelectLimitTipsListener;
                if (onSelectLimitTipsListener7 != null && onSelectLimitTipsListener7.onSelectLimitTips(getContext(), this.config, 8)) {
                    return true;
                }
                RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_choose_max_seconds, Integer.valueOf(this.config.filterVideoMaxSecond / 1000)));
                return true;
            }
        }
        return false;
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    @SuppressLint({"StringFormatInvalid", "StringFormatMatches"})
    public boolean checkWithMimeTypeValidity(boolean z2, String str, int i2, long j2) {
        int i3;
        int i4;
        if (PictureMimeType.isHasVideo(str)) {
            if (this.config.maxVideoSelectNum <= 0) {
                OnSelectLimitTipsListener onSelectLimitTipsListener = PictureSelectionConfig.onSelectLimitTipsListener;
                if (onSelectLimitTipsListener != null && onSelectLimitTipsListener.onSelectLimitTips(getContext(), this.config, 3)) {
                    return true;
                }
                RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_rule));
                return true;
            }
            if (!z2 && SelectedManager.getSelectedResult().size() >= this.config.maxSelectNum) {
                OnSelectLimitTipsListener onSelectLimitTipsListener2 = PictureSelectionConfig.onSelectLimitTipsListener;
                if (onSelectLimitTipsListener2 != null && onSelectLimitTipsListener2.onSelectLimitTips(getContext(), this.config, 4)) {
                    return true;
                }
                RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_message_max_num, Integer.valueOf(this.config.maxSelectNum)));
                return true;
            }
            if (!z2 && i2 >= this.config.maxVideoSelectNum) {
                OnSelectLimitTipsListener onSelectLimitTipsListener3 = PictureSelectionConfig.onSelectLimitTipsListener;
                if (onSelectLimitTipsListener3 != null && onSelectLimitTipsListener3.onSelectLimitTips(getContext(), this.config, 6)) {
                    return true;
                }
                RemindDialog.showTipsDialog(getContext(), getTipsMsg(getContext(), str, this.config.maxVideoSelectNum));
                return true;
            }
            if (!z2 && (i4 = this.config.filterVideoMinSecond) > 0 && j2 < i4) {
                OnSelectLimitTipsListener onSelectLimitTipsListener4 = PictureSelectionConfig.onSelectLimitTipsListener;
                if (onSelectLimitTipsListener4 != null && onSelectLimitTipsListener4.onSelectLimitTips(getContext(), this.config, 9)) {
                    return true;
                }
                RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_choose_min_seconds, Integer.valueOf(this.config.filterVideoMinSecond / 1000)));
                return true;
            }
            if (!z2 && (i3 = this.config.filterVideoMaxSecond) > 0 && j2 > i3) {
                OnSelectLimitTipsListener onSelectLimitTipsListener5 = PictureSelectionConfig.onSelectLimitTipsListener;
                if (onSelectLimitTipsListener5 != null && onSelectLimitTipsListener5.onSelectLimitTips(getContext(), this.config, 8)) {
                    return true;
                }
                RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_choose_max_seconds, Integer.valueOf(this.config.filterVideoMaxSecond / 1000)));
                return true;
            }
        } else if (!z2 && SelectedManager.getSelectedResult().size() >= this.config.maxSelectNum) {
            OnSelectLimitTipsListener onSelectLimitTipsListener6 = PictureSelectionConfig.onSelectLimitTipsListener;
            if (onSelectLimitTipsListener6 != null && onSelectLimitTipsListener6.onSelectLimitTips(getContext(), this.config, 4)) {
                return true;
            }
            RemindDialog.showTipsDialog(getContext(), getString(R.string.ps_message_max_num, Integer.valueOf(this.config.maxSelectNum)));
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public int confirmSelect(LocalMedia localMedia, boolean z2) {
        if (checkSelectLimit(localMedia)) {
            return -1;
        }
        String mimeType = localMedia.getMimeType();
        long duration = localMedia.getDuration();
        ArrayList<LocalMedia> selectedResult = SelectedManager.getSelectedResult();
        PictureSelectionConfig pictureSelectionConfig = this.config;
        int i2 = 0;
        if (pictureSelectionConfig.selectionMode == 2) {
            if (pictureSelectionConfig.isWithVideoImage) {
                int i3 = 0;
                for (int i4 = 0; i4 < selectedResult.size(); i4++) {
                    if (PictureMimeType.isHasVideo(selectedResult.get(i4).getMimeType())) {
                        i3++;
                    }
                }
                if (checkWithMimeTypeValidity(z2, mimeType, i3, duration)) {
                    return -1;
                }
            } else if (checkOnlyMimeTypeValidity(z2, mimeType, SelectedManager.getTopResultMimeType(), duration)) {
                return -1;
            }
        }
        if (z2) {
            selectedResult.remove(localMedia);
            i2 = 1;
        } else {
            if (this.config.selectionMode == 1 && selectedResult.size() > 0) {
                sendFixedSelectedChangeEvent(selectedResult.get(0));
                selectedResult.clear();
            }
            selectedResult.add(localMedia);
            localMedia.setNum(selectedResult.size());
            playClickEffect();
        }
        sendSelectedChangeEvent(i2 ^ 1, localMedia);
        return i2;
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void dismissLoading() {
        try {
            if (!ActivityCompatHelper.isDestroy(getActivity()) && this.mLoadingDialog.isShowing()) {
                this.mLoadingDialog.dismiss();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void dispatchCameraMediaResult(LocalMedia localMedia) {
    }

    public void dispatchTransformResult() {
        LocalMedia localMedia;
        if (checkCompleteSelectLimit()) {
            return;
        }
        ArrayList<LocalMedia> arrayList = new ArrayList<>(SelectedManager.getSelectedResult());
        if (!checkCropValidity()) {
            if (!checkCompressValidity()) {
                onResultEvent(arrayList);
                return;
            } else {
                showLoading();
                PictureSelectionConfig.compressEngine.onStartCompress(getContext(), arrayList, new OnCallbackListener<ArrayList<LocalMedia>>() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.11
                    @Override // com.luck.picture.lib.interfaces.OnCallbackListener
                    public void onCall(ArrayList<LocalMedia> arrayList2) {
                        PictureCommonFragment.this.onResultEvent(arrayList2);
                    }
                });
                return;
            }
        }
        int i2 = 0;
        while (true) {
            if (i2 >= arrayList.size()) {
                localMedia = null;
                break;
            }
            localMedia = arrayList.get(i2);
            if (PictureMimeType.isHasImage(arrayList.get(i2).getMimeType())) {
                break;
            } else {
                i2++;
            }
        }
        PictureSelectionConfig.cropEngine.onStartCrop(this, localMedia, arrayList, 69);
    }

    public long getEnterAnimationDuration() {
        long j2 = this.enterAnimDuration;
        if (j2 > 50) {
            j2 -= 50;
        }
        if (j2 >= 0) {
            return j2;
        }
        return 0L;
    }

    public String getFragmentTag() {
        return TAG;
    }

    public String getOutputPath(Intent intent) {
        if (intent != null) {
            Uri data = (this.config.chooseMode != SelectMimeType.ofAudio() || intent.getData() == null) ? (Uri) intent.getParcelableExtra("output") : intent.getData();
            if (data != null) {
                return PictureMimeType.isContent(data.toString()) ? data.toString() : data.getPath();
            }
        }
        return null;
    }

    public int getResourceId() {
        return 0;
    }

    public SelectorResult getResult(int i2, ArrayList<LocalMedia> arrayList) {
        return new SelectorResult(i2, arrayList != null ? PictureSelector.putIntentResult(arrayList) : null);
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void handlePermissionDenied(String[] strArr) {
        PermissionUtil.goIntentSetting(this, strArr == PermissionConfig.READ_WRITE_EXTERNAL_STORAGE || strArr == PermissionConfig.WRITE_EXTERNAL_STORAGE, 1102);
    }

    public void handlePermissionSettingResult() {
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void initAppLanguage() {
        PictureSelectionConfig pictureSelectionConfig = PictureSelectionConfig.getInstance();
        if (pictureSelectionConfig.language == -2 || pictureSelectionConfig.isOnlyCamera) {
            return;
        }
        PictureLanguageUtils.setAppLanguage(getActivity(), pictureSelectionConfig.language);
    }

    public boolean isNormalDefaultEnter() {
        return getActivity() instanceof PictureSelectorSupporterActivity;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        ForegroundService.stopService(getContext());
        if (i3 != -1) {
            if (i3 == 96) {
                Throwable error = intent != null ? Crop.getError(intent) : new Throwable("image crop error");
                if (error != null) {
                    ToastUtils.showToast(getContext(), error.getMessage());
                    return;
                }
                return;
            }
            if (i3 == 0) {
                if (i2 == 909) {
                    MediaUtils.deleteUri(getContext(), this.config.cameraPath);
                    return;
                } else {
                    if (i2 == 1102) {
                        handlePermissionSettingResult();
                        return;
                    }
                    return;
                }
            }
            return;
        }
        if (i2 == 909) {
            dispatchHandleCamera(intent);
            return;
        }
        if (i2 == 696) {
            onEditMedia(intent);
            return;
        }
        if (i2 == 69) {
            ArrayList<LocalMedia> selectedResult = SelectedManager.getSelectedResult();
            try {
                if (selectedResult.size() == 1) {
                    LocalMedia localMedia = selectedResult.get(0);
                    Uri output = Crop.getOutput(intent);
                    localMedia.setCutPath(output != null ? output.getPath() : "");
                    localMedia.setCut(TextUtils.isEmpty(localMedia.getCutPath()) ? false : true);
                    localMedia.setCropImageWidth(Crop.getOutputImageWidth(intent));
                    localMedia.setCropImageHeight(Crop.getOutputImageHeight(intent));
                    localMedia.setCropOffsetX(Crop.getOutputImageOffsetX(intent));
                    localMedia.setCropOffsetY(Crop.getOutputImageOffsetY(intent));
                    localMedia.setCropResultAspectRatio(Crop.getOutputCropAspectRatio(intent));
                    localMedia.setCustomData(Crop.getOutputCustomExtraData(intent));
                    localMedia.setSandboxPath(localMedia.getCutPath());
                } else {
                    JSONArray jSONArray = new JSONArray(intent.getStringExtra("output"));
                    if (jSONArray.length() == selectedResult.size()) {
                        for (int i4 = 0; i4 < selectedResult.size(); i4++) {
                            LocalMedia localMedia2 = selectedResult.get(i4);
                            JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i4);
                            localMedia2.setCutPath(jSONObjectOptJSONObject.optString(CustomIntentKey.EXTRA_OUT_PUT_PATH));
                            localMedia2.setCut(!TextUtils.isEmpty(localMedia2.getCutPath()));
                            localMedia2.setCropImageWidth(jSONObjectOptJSONObject.optInt(CustomIntentKey.EXTRA_IMAGE_WIDTH));
                            localMedia2.setCropImageHeight(jSONObjectOptJSONObject.optInt(CustomIntentKey.EXTRA_IMAGE_HEIGHT));
                            localMedia2.setCropOffsetX(jSONObjectOptJSONObject.optInt(CustomIntentKey.EXTRA_OFFSET_X));
                            localMedia2.setCropOffsetY(jSONObjectOptJSONObject.optInt(CustomIntentKey.EXTRA_OFFSET_Y));
                            localMedia2.setCropResultAspectRatio((float) jSONObjectOptJSONObject.optDouble(CustomIntentKey.EXTRA_ASPECT_RATIO));
                            localMedia2.setCustomData(jSONObjectOptJSONObject.optString(CustomIntentKey.EXTRA_CUSTOM_EXTRA_DATA));
                            localMedia2.setSandboxPath(localMedia2.getCutPath());
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                ToastUtils.showToast(getContext(), e2.getMessage());
            }
            ArrayList<LocalMedia> arrayList = new ArrayList<>(selectedResult);
            if (!checkCompressValidity()) {
                onResultEvent(arrayList);
            } else {
                showLoading();
                PictureSelectionConfig.compressEngine.onStartCompress(getContext(), arrayList, new OnCallbackListener<ArrayList<LocalMedia>>() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.9
                    @Override // com.luck.picture.lib.interfaces.OnCallbackListener
                    public void onCall(ArrayList<LocalMedia> arrayList2) {
                        PictureCommonFragment.this.onResultEvent(arrayList2);
                    }
                });
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.fragment.app.Fragment
    public void onAttach(@NonNull Context context) {
        initAppLanguage();
        onRecreateEngine();
        super.onAttach(context);
        if (getParentFragment() instanceof IBridgePictureBehavior) {
            this.iBridgePictureBehavior = (IBridgePictureBehavior) getParentFragment();
        } else if (context instanceof IBridgePictureBehavior) {
            this.iBridgePictureBehavior = (IBridgePictureBehavior) context;
        }
    }

    public void onBackOffFragment() {
        if (!ActivityCompatHelper.isDestroy(getActivity())) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
        for (int i2 = 0; i2 < fragments.size(); i2++) {
            Fragment fragment = fragments.get(i2);
            if (fragment instanceof PictureCommonFragment) {
                ((PictureCommonFragment) fragment).onFragmentResume();
            }
        }
    }

    public void onCheckOriginalChange() {
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        super.onConfigurationChanged(configuration);
        initAppLanguage();
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public Animation onCreateAnimation(int i2, boolean z2, int i3) {
        Animation animationLoadAnimation;
        PictureWindowAnimationStyle windowAnimationStyle = PictureSelectionConfig.selectorStyle.getWindowAnimationStyle();
        if (z2) {
            animationLoadAnimation = windowAnimationStyle.activityEnterAnimation != 0 ? AnimationUtils.loadAnimation(getContext(), windowAnimationStyle.activityEnterAnimation) : AnimationUtils.loadAnimation(getContext(), R.anim.ps_anim_alpha_enter);
            setEnterAnimationDuration(animationLoadAnimation.getDuration());
            onEnterFragment();
        } else {
            animationLoadAnimation = windowAnimationStyle.activityExitAnimation != 0 ? AnimationUtils.loadAnimation(getContext(), windowAnimationStyle.activityExitAnimation) : AnimationUtils.loadAnimation(getContext(), R.anim.ps_anim_alpha_exit);
            onExitFragment();
        }
        return animationLoadAnimation;
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return getResourceId() != 0 ? layoutInflater.inflate(getResourceId(), viewGroup, false) : super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        releaseSoundPool();
        super.onDestroy();
    }

    public void onEditMedia(Intent intent) {
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onEnterFragment() {
    }

    public void onExitFragment() {
    }

    public void onExitPictureSelector() {
        if (!ActivityCompatHelper.isDestroy(getActivity())) {
            if (isNormalDefaultEnter()) {
                getActivity().finish();
            } else {
                List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
                for (int i2 = 0; i2 < fragments.size(); i2++) {
                    if (fragments.get(i2) instanceof PictureCommonFragment) {
                        onBackOffFragment();
                    }
                }
            }
        }
        PictureSelectionConfig.destroy();
    }

    public void onFixedSelectedChange(LocalMedia localMedia) {
    }

    public void onFragmentResume() {
    }

    public void onKeyBackFragment() {
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onRecreateEngine() {
        createImageLoaderEngine();
        createCompressEngine();
        createSandboxFileEngine();
        createLoaderDataEngine();
        createResultCallbackListener();
        createLayoutResourceListener();
    }

    @Override // androidx.fragment.app.Fragment
    public void onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i2, strArr, iArr);
        if (this.mPermissionResultCallback != null) {
            PermissionChecker.getInstance().onRequestPermissionsResult(iArr, this.mPermissionResultCallback);
            this.mPermissionResultCallback = null;
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onResultEvent(ArrayList<LocalMedia> arrayList) {
        if (PictureSelectionConfig.sandboxFileEngine != null) {
            copyExternalPathToAppInDirFor29(arrayList);
        } else {
            mergeOriginalImage(arrayList);
            onCallBackResult(arrayList);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        PictureSelectionConfig pictureSelectionConfig = this.config;
        if (pictureSelectionConfig != null) {
            bundle.putParcelable(PictureConfig.EXTRA_PICTURE_SELECTOR_CONFIG, pictureSelectionConfig);
        }
    }

    public void onSelectFinish(int i2, ArrayList<LocalMedia> arrayList) {
        if (this.iBridgePictureBehavior != null) {
            this.iBridgePictureBehavior.onSelectFinish(getResult(i2, arrayList));
        }
    }

    public void onSelectedChange(boolean z2, LocalMedia localMedia) {
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void onSelectedOnlyCamera() {
        PhotoItemSelectedDialog photoItemSelectedDialogNewInstance = PhotoItemSelectedDialog.newInstance();
        photoItemSelectedDialogNewInstance.setOnItemClickListener(new OnItemClickListener() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.2
            @Override // com.luck.picture.lib.interfaces.OnItemClickListener
            public void onItemClick(View view, int i2) {
                if (i2 == 0) {
                    if (PictureSelectionConfig.onCameraInterceptListener != null) {
                        PictureCommonFragment.this.interceptCameraEvent(1);
                        return;
                    } else {
                        PictureCommonFragment.this.openImageCamera();
                        return;
                    }
                }
                if (i2 != 1) {
                    return;
                }
                if (PictureSelectionConfig.onCameraInterceptListener != null) {
                    PictureCommonFragment.this.interceptCameraEvent(2);
                } else {
                    PictureCommonFragment.this.openVideoCamera();
                }
            }
        });
        photoItemSelectedDialogNewInstance.show(getChildFragmentManager(), "PhotoItemSelectedDialog");
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mLoadingDialog = new PictureLoadingDialog(getContext());
        if (bundle != null) {
            this.config = (PictureSelectionConfig) bundle.getParcelable(PictureConfig.EXTRA_PICTURE_SELECTOR_CONFIG);
        }
        if (this.config == null) {
            this.config = PictureSelectionConfig.getInstance();
        }
        setTranslucentStatusBar();
        setRootViewKeyListener(requireView());
        PictureSelectionConfig pictureSelectionConfig = this.config;
        if (!pictureSelectionConfig.isOpenClickSound || pictureSelectionConfig.isOnlyCamera) {
            return;
        }
        SoundPool soundPool = new SoundPool(1, 3, 0);
        this.soundPool = soundPool;
        this.soundID = soundPool.load(getContext(), R.raw.ps_click_music, 1);
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void openImageCamera() {
        OnPermissionsInterceptListener onPermissionsInterceptListener = PictureSelectionConfig.onPermissionsEventListener;
        if (onPermissionsInterceptListener != null) {
            onPermissionsInterceptListener.requestPermission(this, PermissionConfig.CAMERA, new OnCallbackListener<Boolean>() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.3
                @Override // com.luck.picture.lib.interfaces.OnCallbackListener
                public void onCall(Boolean bool) {
                    if (bool.booleanValue()) {
                        PictureCommonFragment.this.startCameraImageCapture();
                    } else {
                        PictureCommonFragment.this.handlePermissionDenied(PermissionConfig.CAMERA);
                    }
                }
            });
        } else {
            PermissionChecker.getInstance().requestPermissions(this, PermissionConfig.CAMERA, new PermissionResultCallback() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.4
                @Override // com.luck.picture.lib.permissions.PermissionResultCallback
                public void onDenied() {
                    PictureCommonFragment.this.handlePermissionDenied(PermissionConfig.CAMERA);
                }

                @Override // com.luck.picture.lib.permissions.PermissionResultCallback
                public void onGranted() {
                    PictureCommonFragment.this.startCameraImageCapture();
                }
            });
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void openSelectedCamera() {
        PictureSelectionConfig pictureSelectionConfig = this.config;
        int i2 = pictureSelectionConfig.chooseMode;
        if (i2 == 0) {
            if (pictureSelectionConfig.ofAllCameraType == SelectMimeType.ofImage()) {
                openImageCamera();
                return;
            } else if (this.config.ofAllCameraType == SelectMimeType.ofVideo()) {
                openVideoCamera();
                return;
            } else {
                onSelectedOnlyCamera();
                return;
            }
        }
        if (i2 == 1) {
            openImageCamera();
        } else if (i2 == 2) {
            openVideoCamera();
        } else {
            if (i2 != 3) {
                return;
            }
            openSoundRecording();
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void openSoundRecording() {
        OnPermissionsInterceptListener onPermissionsInterceptListener = PictureSelectionConfig.onPermissionsEventListener;
        if (onPermissionsInterceptListener != null) {
            onPermissionsInterceptListener.requestPermission(this, PermissionConfig.RECORD_AUDIO, new OnCallbackListener<Boolean>() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.7
                @Override // com.luck.picture.lib.interfaces.OnCallbackListener
                public void onCall(Boolean bool) {
                    if (bool.booleanValue()) {
                        PictureCommonFragment.this.startCameraRecordSound();
                    } else {
                        PictureCommonFragment.this.handlePermissionDenied(PermissionConfig.RECORD_AUDIO);
                    }
                }
            });
        } else {
            PermissionChecker.getInstance().requestPermissions(this, new String[]{Permission.RECORD_AUDIO}, new PermissionResultCallback() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.8
                @Override // com.luck.picture.lib.permissions.PermissionResultCallback
                public void onDenied() {
                    PictureCommonFragment.this.handlePermissionDenied(PermissionConfig.RECORD_AUDIO);
                }

                @Override // com.luck.picture.lib.permissions.PermissionResultCallback
                public void onGranted() {
                    PictureCommonFragment.this.startCameraRecordSound();
                }
            });
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void openVideoCamera() {
        OnPermissionsInterceptListener onPermissionsInterceptListener = PictureSelectionConfig.onPermissionsEventListener;
        if (onPermissionsInterceptListener != null) {
            onPermissionsInterceptListener.requestPermission(this, PermissionConfig.CAMERA, new OnCallbackListener<Boolean>() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.5
                @Override // com.luck.picture.lib.interfaces.OnCallbackListener
                public void onCall(Boolean bool) {
                    if (bool.booleanValue()) {
                        PictureCommonFragment.this.startCameraVideoCapture();
                    } else {
                        PictureCommonFragment.this.handlePermissionDenied(PermissionConfig.CAMERA);
                    }
                }
            });
        } else {
            PermissionChecker.getInstance().requestPermissions(this, PermissionConfig.CAMERA, new PermissionResultCallback() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.6
                @Override // com.luck.picture.lib.permissions.PermissionResultCallback
                public void onDenied() {
                    PictureCommonFragment.this.handlePermissionDenied(PermissionConfig.CAMERA);
                }

                @Override // com.luck.picture.lib.permissions.PermissionResultCallback
                public void onGranted() {
                    PictureCommonFragment.this.startCameraVideoCapture();
                }
            });
        }
    }

    public void reStartSavedInstance(Bundle bundle) {
    }

    public void sendChangeSubSelectPositionEvent(boolean z2) {
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void sendFixedSelectedChangeEvent(LocalMedia localMedia) {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
        for (int i2 = 0; i2 < fragments.size(); i2++) {
            Fragment fragment = fragments.get(i2);
            if (fragment instanceof PictureCommonFragment) {
                ((PictureCommonFragment) fragment).onFixedSelectedChange(localMedia);
            }
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void sendSelectedChangeEvent(boolean z2, LocalMedia localMedia) {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
        for (int i2 = 0; i2 < fragments.size(); i2++) {
            Fragment fragment = fragments.get(i2);
            if (fragment instanceof PictureCommonFragment) {
                ((PictureCommonFragment) fragment).onSelectedChange(z2, localMedia);
            }
        }
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void sendSelectedOriginalChangeEvent() {
        if (ActivityCompatHelper.isDestroy(getActivity())) {
            return;
        }
        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
        for (int i2 = 0; i2 < fragments.size(); i2++) {
            Fragment fragment = fragments.get(i2);
            if (fragment instanceof PictureCommonFragment) {
                ((PictureCommonFragment) fragment).onCheckOriginalChange();
            }
        }
    }

    public void setEnterAnimationDuration(long j2) {
        this.enterAnimDuration = j2;
    }

    public void setPermissionsResultAction(PermissionResultCallback permissionResultCallback) {
        this.mPermissionResultCallback = permissionResultCallback;
    }

    public void setRootViewKeyListener(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() { // from class: com.luck.picture.lib.basic.PictureCommonFragment.1
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view2, int i2, KeyEvent keyEvent) {
                if (i2 != 4 || keyEvent.getAction() != 1) {
                    return false;
                }
                PictureCommonFragment.this.onKeyBackFragment();
                return true;
            }
        });
    }

    @Override // com.luck.picture.lib.basic.IPictureSelectorCommonEvent
    public void showLoading() {
        try {
            if (ActivityCompatHelper.isDestroy(getActivity())) {
                return;
            }
            if (this.mLoadingDialog.isShowing()) {
                this.mLoadingDialog.dismiss();
            }
            this.mLoadingDialog.show();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
