package com.luck.picture.lib.config;

import android.os.Parcel;
import android.os.Parcelable;
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
import com.luck.picture.lib.magical.BuildRecycleItemViewParams;
import com.luck.picture.lib.manager.SelectedManager;
import com.luck.picture.lib.style.PictureSelectorStyle;
import com.luck.picture.lib.thread.PictureThreadUtils;
import com.luck.picture.lib.utils.SdkVersionUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class PictureSelectionConfig implements Parcelable {
    public static final Parcelable.Creator<PictureSelectionConfig> CREATOR = new Parcelable.Creator<PictureSelectionConfig>() { // from class: com.luck.picture.lib.config.PictureSelectionConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PictureSelectionConfig createFromParcel(Parcel parcel) {
            return new PictureSelectionConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PictureSelectionConfig[] newArray(int i2) {
            return new PictureSelectionConfig[i2];
        }
    };
    public static CompressEngine compressEngine;
    public static CropEngine cropEngine;
    public static ImageEngine imageEngine;
    public static ExtendLoaderEngine loaderDataEngine;
    private static PictureSelectionConfig mInstance;
    public static OnCameraInterceptListener onCameraInterceptListener;
    public static OnMediaEditInterceptListener onEditMediaEventListener;
    public static OnExternalPreviewEventListener onExternalPreviewEventListener;
    public static OnInjectLayoutResourceListener onLayoutResourceListener;
    public static OnPermissionsInterceptListener onPermissionsEventListener;
    public static OnPreviewInterceptListener onPreviewInterceptListener;
    public static OnResultCallbackListener<LocalMedia> onResultCallListener;
    public static OnSelectLimitTipsListener onSelectLimitTipsListener;
    public static SandboxFileEngine sandboxFileEngine;
    public static PictureSelectorStyle selectorStyle;
    public int animationMode;
    public String cameraImageFormat;
    public String cameraImageFormatForQ;
    public String cameraPath;
    public String cameraVideoFormat;
    public String cameraVideoFormatForQ;
    public int chooseMode;
    public long filterMaxFileSize;
    public long filterMinFileSize;
    public int filterVideoMaxSecond;
    public int filterVideoMinSecond;
    public int imageSpanCount;
    public boolean isActivityResultBack;
    public boolean isAutoRotating;
    public boolean isAutomaticTitleRecyclerTop;
    public boolean isBmp;
    public boolean isCameraAroundState;
    public boolean isCameraForegroundService;
    public boolean isCameraRotateImage;
    public boolean isCheckOriginalImage;
    public boolean isCompressEngine;
    public boolean isDirectReturnSingle;
    public boolean isDisplayCamera;
    public boolean isDisplayTimeAxis;
    public boolean isEmptyResultReturn;
    public boolean isEnablePreviewAudio;
    public boolean isEnablePreviewImage;
    public boolean isEnablePreviewVideo;
    public boolean isFilterInvalidFile;
    public boolean isGif;
    public boolean isHidePreviewDownload;
    public boolean isInjectLayoutResource;
    public boolean isLoaderDataEngine;
    public boolean isMaxSelectEnabledMask;
    public boolean isOnlyCamera;
    public boolean isOnlySandboxDir;
    public boolean isOpenClickSound;
    public boolean isOriginalControl;
    public boolean isPageStrategy;
    public boolean isPreviewFullScreenMode;
    public boolean isPreviewZoomEffect;
    public boolean isQuickCapture;
    public boolean isResultListenerBack;
    public boolean isSandboxFileEngine;
    public boolean isSyncCover;
    public boolean isWebp;
    public boolean isWithVideoImage;
    public int language;
    public int maxSelectNum;
    public int maxVideoSelectNum;
    public int minAudioSelectNum;
    public int minSelectNum;
    public int minVideoSelectNum;
    public int ofAllCameraType;
    public String originalPath;
    public String outPutAudioDir;
    public String outPutAudioFileName;
    public String outPutCameraDir;
    public String outPutCameraImageFileName;
    public String outPutCameraVideoFileName;
    public int pageSize;
    public List<String> queryOnlyList;
    public int recordVideoMaxSecond;
    public int recordVideoMinSecond;
    public int requestedOrientation;
    public String sandboxDir;
    public int selectMaxDurationSecond;
    public long selectMaxFileSize;
    public int selectMinDurationSecond;
    public long selectMinFileSize;
    public int selectionMode;
    public int videoQuality;

    public PictureSelectionConfig(Parcel parcel) {
        this.chooseMode = parcel.readInt();
        this.isOnlyCamera = parcel.readByte() != 0;
        this.isDirectReturnSingle = parcel.readByte() != 0;
        this.cameraImageFormat = parcel.readString();
        this.cameraVideoFormat = parcel.readString();
        this.cameraImageFormatForQ = parcel.readString();
        this.cameraVideoFormatForQ = parcel.readString();
        this.requestedOrientation = parcel.readInt();
        this.isCameraAroundState = parcel.readByte() != 0;
        this.selectionMode = parcel.readInt();
        this.maxSelectNum = parcel.readInt();
        this.minSelectNum = parcel.readInt();
        this.maxVideoSelectNum = parcel.readInt();
        this.minVideoSelectNum = parcel.readInt();
        this.minAudioSelectNum = parcel.readInt();
        this.videoQuality = parcel.readInt();
        this.filterVideoMaxSecond = parcel.readInt();
        this.filterVideoMinSecond = parcel.readInt();
        this.selectMaxDurationSecond = parcel.readInt();
        this.selectMinDurationSecond = parcel.readInt();
        this.recordVideoMaxSecond = parcel.readInt();
        this.recordVideoMinSecond = parcel.readInt();
        this.imageSpanCount = parcel.readInt();
        this.filterMaxFileSize = parcel.readLong();
        this.filterMinFileSize = parcel.readLong();
        this.selectMaxFileSize = parcel.readLong();
        this.selectMinFileSize = parcel.readLong();
        this.language = parcel.readInt();
        this.isDisplayCamera = parcel.readByte() != 0;
        this.isGif = parcel.readByte() != 0;
        this.isWebp = parcel.readByte() != 0;
        this.isBmp = parcel.readByte() != 0;
        this.isEnablePreviewImage = parcel.readByte() != 0;
        this.isEnablePreviewVideo = parcel.readByte() != 0;
        this.isEnablePreviewAudio = parcel.readByte() != 0;
        this.isPreviewFullScreenMode = parcel.readByte() != 0;
        this.isPreviewZoomEffect = parcel.readByte() != 0;
        this.isOpenClickSound = parcel.readByte() != 0;
        this.isEmptyResultReturn = parcel.readByte() != 0;
        this.isHidePreviewDownload = parcel.readByte() != 0;
        this.isWithVideoImage = parcel.readByte() != 0;
        this.queryOnlyList = parcel.createStringArrayList();
        this.isCheckOriginalImage = parcel.readByte() != 0;
        this.outPutCameraImageFileName = parcel.readString();
        this.outPutCameraVideoFileName = parcel.readString();
        this.outPutAudioFileName = parcel.readString();
        this.outPutCameraDir = parcel.readString();
        this.outPutAudioDir = parcel.readString();
        this.sandboxDir = parcel.readString();
        this.originalPath = parcel.readString();
        this.cameraPath = parcel.readString();
        this.pageSize = parcel.readInt();
        this.isPageStrategy = parcel.readByte() != 0;
        this.isFilterInvalidFile = parcel.readByte() != 0;
        this.isMaxSelectEnabledMask = parcel.readByte() != 0;
        this.animationMode = parcel.readInt();
        this.isAutomaticTitleRecyclerTop = parcel.readByte() != 0;
        this.isQuickCapture = parcel.readByte() != 0;
        this.isCameraRotateImage = parcel.readByte() != 0;
        this.isAutoRotating = parcel.readByte() != 0;
        this.isSyncCover = parcel.readByte() != 0;
        this.ofAllCameraType = parcel.readInt();
        this.isOnlySandboxDir = parcel.readByte() != 0;
        this.isCameraForegroundService = parcel.readByte() != 0;
        this.isResultListenerBack = parcel.readByte() != 0;
        this.isInjectLayoutResource = parcel.readByte() != 0;
        this.isActivityResultBack = parcel.readByte() != 0;
        this.isCompressEngine = parcel.readByte() != 0;
        this.isLoaderDataEngine = parcel.readByte() != 0;
        this.isSandboxFileEngine = parcel.readByte() != 0;
        this.isOriginalControl = parcel.readByte() != 0;
        this.isDisplayTimeAxis = parcel.readByte() != 0;
    }

    public static void destroy() {
        imageEngine = null;
        compressEngine = null;
        cropEngine = null;
        sandboxFileEngine = null;
        loaderDataEngine = null;
        onResultCallListener = null;
        onCameraInterceptListener = null;
        onExternalPreviewEventListener = null;
        onEditMediaEventListener = null;
        onPermissionsEventListener = null;
        onLayoutResourceListener = null;
        onPreviewInterceptListener = null;
        onSelectLimitTipsListener = null;
        PictureThreadUtils.cancel(PictureThreadUtils.getIoPool());
        SelectedManager.clear();
        BuildRecycleItemViewParams.clear();
        SelectedManager.setCurrentLocalMediaFolder(null);
    }

    public static PictureSelectionConfig getCleanInstance() {
        PictureSelectionConfig pictureSelectionConfig = getInstance();
        pictureSelectionConfig.initDefaultValue();
        return pictureSelectionConfig;
    }

    public static PictureSelectionConfig getInstance() {
        if (mInstance == null) {
            synchronized (PictureSelectionConfig.class) {
                if (mInstance == null) {
                    PictureSelectionConfig pictureSelectionConfig = new PictureSelectionConfig();
                    mInstance = pictureSelectionConfig;
                    pictureSelectionConfig.initDefaultValue();
                }
            }
        }
        return mInstance;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void initDefaultValue() {
        this.chooseMode = SelectMimeType.ofImage();
        this.isOnlyCamera = false;
        this.selectionMode = 2;
        selectorStyle = new PictureSelectorStyle();
        this.maxSelectNum = 9;
        this.minSelectNum = 0;
        this.maxVideoSelectNum = 1;
        this.minVideoSelectNum = 0;
        this.minAudioSelectNum = 0;
        this.videoQuality = 1;
        this.language = -2;
        this.filterVideoMaxSecond = 0;
        this.filterVideoMinSecond = 1000;
        this.selectMaxDurationSecond = 0;
        this.selectMinDurationSecond = 0;
        this.filterMaxFileSize = 0L;
        this.filterMinFileSize = 1024L;
        this.selectMaxFileSize = 0L;
        this.selectMinFileSize = 0L;
        this.recordVideoMaxSecond = 60;
        this.recordVideoMinSecond = 0;
        this.imageSpanCount = 4;
        this.isCameraAroundState = false;
        this.isWithVideoImage = false;
        this.isDisplayCamera = true;
        this.isGif = false;
        this.isWebp = true;
        this.isBmp = true;
        this.isCheckOriginalImage = false;
        this.isDirectReturnSingle = false;
        this.isEnablePreviewImage = true;
        this.isEnablePreviewVideo = true;
        this.isEnablePreviewAudio = true;
        this.isHidePreviewDownload = false;
        this.isOpenClickSound = false;
        this.isEmptyResultReturn = false;
        this.cameraImageFormat = ".jpeg";
        this.cameraVideoFormat = ".mp4";
        this.cameraImageFormatForQ = "image/jpeg";
        this.cameraVideoFormatForQ = "video/mp4";
        this.outPutCameraImageFileName = "";
        this.outPutCameraVideoFileName = "";
        this.outPutAudioFileName = "";
        this.queryOnlyList = new ArrayList();
        this.outPutCameraDir = "";
        this.outPutAudioDir = "";
        this.sandboxDir = "";
        this.originalPath = "";
        this.cameraPath = "";
        this.pageSize = 60;
        this.isPageStrategy = true;
        this.isFilterInvalidFile = false;
        this.isMaxSelectEnabledMask = false;
        this.animationMode = -1;
        this.isAutomaticTitleRecyclerTop = true;
        this.isQuickCapture = true;
        this.isCameraRotateImage = true;
        this.isAutoRotating = true;
        this.isSyncCover = !SdkVersionUtils.isQ();
        this.ofAllCameraType = SelectMimeType.ofAll();
        this.isOnlySandboxDir = false;
        this.requestedOrientation = -1;
        this.isCameraForegroundService = true;
        this.isResultListenerBack = true;
        this.isActivityResultBack = false;
        this.isCompressEngine = false;
        this.isLoaderDataEngine = false;
        this.isSandboxFileEngine = false;
        this.isPreviewFullScreenMode = true;
        this.isPreviewZoomEffect = true;
        this.isOriginalControl = false;
        this.isInjectLayoutResource = false;
        this.isDisplayTimeAxis = true;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.chooseMode);
        parcel.writeByte(this.isOnlyCamera ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isDirectReturnSingle ? (byte) 1 : (byte) 0);
        parcel.writeString(this.cameraImageFormat);
        parcel.writeString(this.cameraVideoFormat);
        parcel.writeString(this.cameraImageFormatForQ);
        parcel.writeString(this.cameraVideoFormatForQ);
        parcel.writeInt(this.requestedOrientation);
        parcel.writeByte(this.isCameraAroundState ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.selectionMode);
        parcel.writeInt(this.maxSelectNum);
        parcel.writeInt(this.minSelectNum);
        parcel.writeInt(this.maxVideoSelectNum);
        parcel.writeInt(this.minVideoSelectNum);
        parcel.writeInt(this.minAudioSelectNum);
        parcel.writeInt(this.videoQuality);
        parcel.writeInt(this.filterVideoMaxSecond);
        parcel.writeInt(this.filterVideoMinSecond);
        parcel.writeInt(this.selectMaxDurationSecond);
        parcel.writeInt(this.selectMinDurationSecond);
        parcel.writeInt(this.recordVideoMaxSecond);
        parcel.writeInt(this.recordVideoMinSecond);
        parcel.writeInt(this.imageSpanCount);
        parcel.writeLong(this.filterMaxFileSize);
        parcel.writeLong(this.filterMinFileSize);
        parcel.writeLong(this.selectMaxFileSize);
        parcel.writeLong(this.selectMinFileSize);
        parcel.writeInt(this.language);
        parcel.writeByte(this.isDisplayCamera ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isGif ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isWebp ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isBmp ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isEnablePreviewImage ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isEnablePreviewVideo ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isEnablePreviewAudio ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isPreviewFullScreenMode ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isPreviewZoomEffect ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isOpenClickSound ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isEmptyResultReturn ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isHidePreviewDownload ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isWithVideoImage ? (byte) 1 : (byte) 0);
        parcel.writeStringList(this.queryOnlyList);
        parcel.writeByte(this.isCheckOriginalImage ? (byte) 1 : (byte) 0);
        parcel.writeString(this.outPutCameraImageFileName);
        parcel.writeString(this.outPutCameraVideoFileName);
        parcel.writeString(this.outPutAudioFileName);
        parcel.writeString(this.outPutCameraDir);
        parcel.writeString(this.outPutAudioDir);
        parcel.writeString(this.sandboxDir);
        parcel.writeString(this.originalPath);
        parcel.writeString(this.cameraPath);
        parcel.writeInt(this.pageSize);
        parcel.writeByte(this.isPageStrategy ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isFilterInvalidFile ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isMaxSelectEnabledMask ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.animationMode);
        parcel.writeByte(this.isAutomaticTitleRecyclerTop ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isQuickCapture ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isCameraRotateImage ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isAutoRotating ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isSyncCover ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.ofAllCameraType);
        parcel.writeByte(this.isOnlySandboxDir ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isCameraForegroundService ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isResultListenerBack ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isInjectLayoutResource ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isActivityResultBack ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isCompressEngine ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isLoaderDataEngine ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isSandboxFileEngine ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isOriginalControl ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isDisplayTimeAxis ? (byte) 1 : (byte) 0);
    }

    public PictureSelectionConfig() {
    }
}
