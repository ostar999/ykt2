package androidx.camera.camera2.internal;

import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.Pair;
import android.util.Rational;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import androidx.camera.camera2.internal.compat.CameraCharacteristicsCompat;
import androidx.camera.camera2.internal.compat.CameraManagerCompat;
import androidx.camera.camera2.internal.compat.workaround.ExcludedSupportedSizesContainer;
import androidx.camera.camera2.internal.compat.workaround.ExtraSupportedSurfaceCombinationsContainer;
import androidx.camera.camera2.internal.compat.workaround.ResolutionCorrector;
import androidx.camera.camera2.internal.compat.workaround.TargetAspectRatio;
import androidx.camera.core.CameraUnavailableException;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.AttachedSurfaceInfo;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.SurfaceCombination;
import androidx.camera.core.impl.SurfaceConfig;
import androidx.camera.core.impl.SurfaceSizeDefinition;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.utils.AspectRatioUtil;
import androidx.camera.core.impl.utils.CameraOrientationUtil;
import androidx.camera.core.impl.utils.CompareSizesByArea;
import androidx.camera.core.internal.utils.SizeUtil;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RequiresApi(21)
/* loaded from: classes.dex */
final class SupportedSurfaceCombination {
    private static final String TAG = "SupportedSurfaceCombination";
    private final CamcorderProfileHelper mCamcorderProfileHelper;
    private final String mCameraId;
    private final CameraCharacteristicsCompat mCharacteristics;

    @NonNull
    private final DisplayInfoManager mDisplayInfoManager;
    private final ExcludedSupportedSizesContainer mExcludedSupportedSizesContainer;
    private final ExtraSupportedSurfaceCombinationsContainer mExtraSupportedSurfaceCombinationsContainer;
    private final int mHardwareLevel;
    private boolean mIsBurstCaptureSupported;
    private boolean mIsRawSupported;
    private final boolean mIsSensorLandscapeResolution;

    @VisibleForTesting
    SurfaceSizeDefinition mSurfaceSizeDefinition;
    private final List<SurfaceCombination> mSurfaceCombinations = new ArrayList();
    private final Map<Integer, Size> mMaxSizeCache = new HashMap();
    private final Map<Integer, List<Size>> mExcludedSizeListCache = new HashMap();
    private Map<Integer, Size[]> mOutputSizesCache = new HashMap();
    private final ResolutionCorrector mResolutionCorrector = new ResolutionCorrector();

    public SupportedSurfaceCombination(@NonNull Context context, @NonNull String str, @NonNull CameraManagerCompat cameraManagerCompat, @NonNull CamcorderProfileHelper camcorderProfileHelper) throws CameraUnavailableException {
        this.mIsRawSupported = false;
        this.mIsBurstCaptureSupported = false;
        String str2 = (String) Preconditions.checkNotNull(str);
        this.mCameraId = str2;
        this.mCamcorderProfileHelper = (CamcorderProfileHelper) Preconditions.checkNotNull(camcorderProfileHelper);
        this.mExcludedSupportedSizesContainer = new ExcludedSupportedSizesContainer(str);
        this.mExtraSupportedSurfaceCombinationsContainer = new ExtraSupportedSurfaceCombinationsContainer();
        this.mDisplayInfoManager = DisplayInfoManager.getInstance(context);
        try {
            CameraCharacteristicsCompat cameraCharacteristicsCompat = cameraManagerCompat.getCameraCharacteristicsCompat(str2);
            this.mCharacteristics = cameraCharacteristicsCompat;
            Integer num = (Integer) cameraCharacteristicsCompat.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
            this.mHardwareLevel = num != null ? num.intValue() : 2;
            this.mIsSensorLandscapeResolution = isSensorLandscapeResolution();
            int[] iArr = (int[]) cameraCharacteristicsCompat.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
            if (iArr != null) {
                for (int i2 : iArr) {
                    if (i2 == 3) {
                        this.mIsRawSupported = true;
                    } else if (i2 == 6) {
                        this.mIsBurstCaptureSupported = true;
                    }
                }
            }
            generateSupportedCombinationList();
            generateSurfaceSizeDefinition();
            checkCustomization();
        } catch (CameraAccessExceptionCompat e2) {
            throw CameraUnavailableExceptionHelper.createFrom(e2);
        }
    }

    private void checkCustomization() {
    }

    @NonNull
    private Size[] doGetAllOutputSizesByFormat(int i2) {
        StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) this.mCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        if (streamConfigurationMap == null) {
            throw new IllegalArgumentException("Can not retrieve SCALER_STREAM_CONFIGURATION_MAP");
        }
        Size[] outputSizes = streamConfigurationMap.getOutputSizes(i2);
        if (outputSizes != null) {
            Size[] sizeArrExcludeProblematicSizes = excludeProblematicSizes(outputSizes, i2);
            Arrays.sort(sizeArrExcludeProblematicSizes, new CompareSizesByArea(true));
            return sizeArrExcludeProblematicSizes;
        }
        throw new IllegalArgumentException("Can not get supported output size for the format: " + i2);
    }

    @NonNull
    private Size[] excludeProblematicSizes(@NonNull Size[] sizeArr, int i2) {
        List<Size> listFetchExcludedSizes = fetchExcludedSizes(i2);
        ArrayList arrayList = new ArrayList(Arrays.asList(sizeArr));
        arrayList.removeAll(listFetchExcludedSizes);
        return (Size[]) arrayList.toArray(new Size[0]);
    }

    @NonNull
    private List<Size> fetchExcludedSizes(int i2) {
        List<Size> list = this.mExcludedSizeListCache.get(Integer.valueOf(i2));
        if (list != null) {
            return list;
        }
        List<Size> list2 = this.mExcludedSupportedSizesContainer.get(i2);
        this.mExcludedSizeListCache.put(Integer.valueOf(i2), list2);
        return list2;
    }

    private Size fetchMaxSize(int i2) {
        Size size = this.mMaxSizeCache.get(Integer.valueOf(i2));
        if (size != null) {
            return size;
        }
        Size maxOutputSizeByFormat = getMaxOutputSizeByFormat(i2);
        this.mMaxSizeCache.put(Integer.valueOf(i2), maxOutputSizeByFormat);
        return maxOutputSizeByFormat;
    }

    @Nullable
    private Size flipSizeByRotation(@Nullable Size size, int i2) {
        return (size == null || !isRotationNeeded(i2)) ? size : new Size(size.getHeight(), size.getWidth());
    }

    private void generateSupportedCombinationList() {
        this.mSurfaceCombinations.addAll(GuaranteedConfigurationsUtil.generateSupportedCombinationList(this.mHardwareLevel, this.mIsRawSupported, this.mIsBurstCaptureSupported));
        this.mSurfaceCombinations.addAll(this.mExtraSupportedSurfaceCombinationsContainer.get(this.mCameraId, this.mHardwareLevel));
    }

    private void generateSurfaceSizeDefinition() {
        this.mSurfaceSizeDefinition = SurfaceSizeDefinition.create(new Size(640, 480), this.mDisplayInfoManager.getPreviewSize(), getRecordSize());
    }

    @NonNull
    private Size[] getAllOutputSizesByFormat(int i2) {
        Size[] sizeArr = this.mOutputSizesCache.get(Integer.valueOf(i2));
        if (sizeArr != null) {
            return sizeArr;
        }
        Size[] sizeArrDoGetAllOutputSizesByFormat = doGetAllOutputSizesByFormat(i2);
        this.mOutputSizesCache.put(Integer.valueOf(i2), sizeArrDoGetAllOutputSizesByFormat);
        return sizeArrDoGetAllOutputSizesByFormat;
    }

    private List<List<Size>> getAllPossibleSizeArrangements(List<List<Size>> list) {
        Iterator<List<Size>> it = list.iterator();
        int size = 1;
        while (it.hasNext()) {
            size *= it.next().size();
        }
        if (size == 0) {
            throw new IllegalArgumentException("Failed to find supported resolutions.");
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < size; i2++) {
            arrayList.add(new ArrayList());
        }
        int size2 = size / list.get(0).size();
        int i3 = size;
        for (int i4 = 0; i4 < list.size(); i4++) {
            List<Size> list2 = list.get(i4);
            for (int i5 = 0; i5 < size; i5++) {
                ((List) arrayList.get(i5)).add(list2.get((i5 % i3) / size2));
            }
            if (i4 < list.size() - 1) {
                i3 = size2;
                size2 /= list.get(i4 + 1).size();
            }
        }
        return arrayList;
    }

    @Nullable
    private Size[] getCustomizedSupportSizesFromConfig(int i2, @NonNull ImageOutputConfig imageOutputConfig) {
        Size[] sizeArr = null;
        List<Pair<Integer, Size[]>> supportedResolutions = imageOutputConfig.getSupportedResolutions(null);
        if (supportedResolutions != null) {
            Iterator<Pair<Integer, Size[]>> it = supportedResolutions.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Pair<Integer, Size[]> next = it.next();
                if (((Integer) next.first).intValue() == i2) {
                    sizeArr = (Size[]) next.second;
                    break;
                }
            }
        }
        if (sizeArr == null) {
            return sizeArr;
        }
        Size[] sizeArrExcludeProblematicSizes = excludeProblematicSizes(sizeArr, i2);
        Arrays.sort(sizeArrExcludeProblematicSizes, new CompareSizesByArea(true));
        return sizeArrExcludeProblematicSizes;
    }

    @NonNull
    private Size getRecordSize() throws NumberFormatException {
        try {
            int i2 = Integer.parseInt(this.mCameraId);
            CamcorderProfile camcorderProfile = this.mCamcorderProfileHelper.hasProfile(i2, 1) ? this.mCamcorderProfileHelper.get(i2, 1) : null;
            return camcorderProfile != null ? new Size(camcorderProfile.videoFrameWidth, camcorderProfile.videoFrameHeight) : getRecordSizeByHasProfile(i2);
        } catch (NumberFormatException unused) {
            return getRecordSizeFromStreamConfigurationMap();
        }
    }

    @NonNull
    private Size getRecordSizeByHasProfile(int i2) {
        Size size = SizeUtil.RESOLUTION_480P;
        CamcorderProfile camcorderProfile = this.mCamcorderProfileHelper.hasProfile(i2, 10) ? this.mCamcorderProfileHelper.get(i2, 10) : this.mCamcorderProfileHelper.hasProfile(i2, 8) ? this.mCamcorderProfileHelper.get(i2, 8) : this.mCamcorderProfileHelper.hasProfile(i2, 12) ? this.mCamcorderProfileHelper.get(i2, 12) : this.mCamcorderProfileHelper.hasProfile(i2, 6) ? this.mCamcorderProfileHelper.get(i2, 6) : this.mCamcorderProfileHelper.hasProfile(i2, 5) ? this.mCamcorderProfileHelper.get(i2, 5) : this.mCamcorderProfileHelper.hasProfile(i2, 4) ? this.mCamcorderProfileHelper.get(i2, 4) : null;
        return camcorderProfile != null ? new Size(camcorderProfile.videoFrameWidth, camcorderProfile.videoFrameHeight) : size;
    }

    @NonNull
    private Size getRecordSizeFromStreamConfigurationMap() {
        StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) this.mCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        if (streamConfigurationMap == null) {
            throw new IllegalArgumentException("Can not retrieve SCALER_STREAM_CONFIGURATION_MAP");
        }
        Size[] outputSizes = streamConfigurationMap.getOutputSizes(MediaRecorder.class);
        if (outputSizes == null) {
            return SizeUtil.RESOLUTION_480P;
        }
        Arrays.sort(outputSizes, new CompareSizesByArea(true));
        for (Size size : outputSizes) {
            int width = size.getWidth();
            Size size2 = SizeUtil.RESOLUTION_1080P;
            if (width <= size2.getWidth() && size.getHeight() <= size2.getHeight()) {
                return size;
            }
        }
        return SizeUtil.RESOLUTION_480P;
    }

    private Rational getTargetAspectRatio(@NonNull ImageOutputConfig imageOutputConfig) {
        Rational rational;
        int i2 = new TargetAspectRatio().get(this.mCameraId, this.mCharacteristics);
        if (i2 == 0) {
            rational = this.mIsSensorLandscapeResolution ? AspectRatioUtil.ASPECT_RATIO_4_3 : AspectRatioUtil.ASPECT_RATIO_3_4;
        } else if (i2 == 1) {
            rational = this.mIsSensorLandscapeResolution ? AspectRatioUtil.ASPECT_RATIO_16_9 : AspectRatioUtil.ASPECT_RATIO_9_16;
        } else {
            if (i2 == 2) {
                Size sizeFetchMaxSize = fetchMaxSize(256);
                return new Rational(sizeFetchMaxSize.getWidth(), sizeFetchMaxSize.getHeight());
            }
            if (i2 != 3) {
                return null;
            }
            Size targetSize = getTargetSize(imageOutputConfig);
            if (!imageOutputConfig.hasTargetAspectRatio()) {
                if (targetSize != null) {
                    return new Rational(targetSize.getWidth(), targetSize.getHeight());
                }
                return null;
            }
            int targetAspectRatio = imageOutputConfig.getTargetAspectRatio();
            if (targetAspectRatio == 0) {
                rational = this.mIsSensorLandscapeResolution ? AspectRatioUtil.ASPECT_RATIO_4_3 : AspectRatioUtil.ASPECT_RATIO_3_4;
            } else {
                if (targetAspectRatio != 1) {
                    Logger.e(TAG, "Undefined target aspect ratio: " + targetAspectRatio);
                    return null;
                }
                rational = this.mIsSensorLandscapeResolution ? AspectRatioUtil.ASPECT_RATIO_16_9 : AspectRatioUtil.ASPECT_RATIO_9_16;
            }
        }
        return rational;
    }

    @Nullable
    private Size getTargetSize(@NonNull ImageOutputConfig imageOutputConfig) {
        return flipSizeByRotation(imageOutputConfig.getTargetResolution(null), imageOutputConfig.getTargetRotation(0));
    }

    private List<Integer> getUseCasesPriorityOrder(List<UseCaseConfig<?>> list) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator<UseCaseConfig<?>> it = list.iterator();
        while (it.hasNext()) {
            int surfaceOccupancyPriority = it.next().getSurfaceOccupancyPriority(0);
            if (!arrayList2.contains(Integer.valueOf(surfaceOccupancyPriority))) {
                arrayList2.add(Integer.valueOf(surfaceOccupancyPriority));
            }
        }
        Collections.sort(arrayList2);
        Collections.reverse(arrayList2);
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            int iIntValue = ((Integer) it2.next()).intValue();
            for (UseCaseConfig<?> useCaseConfig : list) {
                if (iIntValue == useCaseConfig.getSurfaceOccupancyPriority(0)) {
                    arrayList.add(Integer.valueOf(list.indexOf(useCaseConfig)));
                }
            }
        }
        return arrayList;
    }

    private Map<Rational, List<Size>> groupSizesByAspectRatio(List<Size> list) {
        HashMap map = new HashMap();
        map.put(AspectRatioUtil.ASPECT_RATIO_4_3, new ArrayList());
        map.put(AspectRatioUtil.ASPECT_RATIO_16_9, new ArrayList());
        for (Size size : list) {
            Rational rational = null;
            for (Rational rational2 : map.keySet()) {
                if (AspectRatioUtil.hasMatchingAspectRatio(size, rational2)) {
                    List list2 = (List) map.get(rational2);
                    if (!list2.contains(size)) {
                        list2.add(size);
                    }
                    rational = rational2;
                }
            }
            if (rational == null) {
                map.put(new Rational(size.getWidth(), size.getHeight()), new ArrayList(Collections.singleton(size)));
            }
        }
        return map;
    }

    private boolean isRotationNeeded(int i2) {
        Integer num = (Integer) this.mCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        Preconditions.checkNotNull(num, "Camera HAL in bad state, unable to retrieve the SENSOR_ORIENTATION");
        int iSurfaceRotationToDegrees = CameraOrientationUtil.surfaceRotationToDegrees(i2);
        Integer num2 = (Integer) this.mCharacteristics.get(CameraCharacteristics.LENS_FACING);
        Preconditions.checkNotNull(num2, "Camera HAL in bad state, unable to retrieve the LENS_FACING");
        int relativeImageRotation = CameraOrientationUtil.getRelativeImageRotation(iSurfaceRotationToDegrees, num.intValue(), 1 == num2.intValue());
        return relativeImageRotation == 90 || relativeImageRotation == 270;
    }

    private boolean isSensorLandscapeResolution() {
        Size size = (Size) this.mCharacteristics.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE);
        return size == null || size.getWidth() >= size.getHeight();
    }

    private void refreshPreviewSize() {
        this.mDisplayInfoManager.refresh();
        if (this.mSurfaceSizeDefinition == null) {
            generateSurfaceSizeDefinition();
        } else {
            this.mSurfaceSizeDefinition = SurfaceSizeDefinition.create(this.mSurfaceSizeDefinition.getAnalysisSize(), this.mDisplayInfoManager.getPreviewSize(), this.mSurfaceSizeDefinition.getRecordSize());
        }
    }

    private void removeSupportedSizesByTargetSize(List<Size> list, Size size) {
        if (list == null || list.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        int i2 = -1;
        int i3 = 0;
        while (true) {
            int i4 = i3;
            int i5 = i2;
            i2 = i4;
            if (i2 >= list.size()) {
                break;
            }
            Size size2 = list.get(i2);
            if (size2.getWidth() < size.getWidth() || size2.getHeight() < size.getHeight()) {
                break;
            }
            if (i5 >= 0) {
                arrayList.add(list.get(i5));
            }
            i3 = i2 + 1;
        }
        list.removeAll(arrayList);
    }

    public boolean checkSupported(List<SurfaceConfig> list) {
        Iterator<SurfaceCombination> it = this.mSurfaceCombinations.iterator();
        boolean zIsSupported = false;
        while (it.hasNext() && !(zIsSupported = it.next().isSupported(list))) {
        }
        return zIsSupported;
    }

    public String getCameraId() {
        return this.mCameraId;
    }

    public Size getMaxOutputSizeByFormat(int i2) {
        return (Size) Collections.max(Arrays.asList(getAllOutputSizesByFormat(i2)), new CompareSizesByArea());
    }

    @NonNull
    public Map<UseCaseConfig<?>, Size> getSuggestedResolutions(@NonNull List<AttachedSurfaceInfo> list, @NonNull List<UseCaseConfig<?>> list2) {
        HashMap map;
        refreshPreviewSize();
        ArrayList arrayList = new ArrayList();
        Iterator<AttachedSurfaceInfo> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getSurfaceConfig());
        }
        Iterator<UseCaseConfig<?>> it2 = list2.iterator();
        while (it2.hasNext()) {
            arrayList.add(SurfaceConfig.transformSurfaceConfig(it2.next().getInputFormat(), new Size(640, 480), this.mSurfaceSizeDefinition));
        }
        if (!checkSupported(arrayList)) {
            throw new IllegalArgumentException("No supported surface combination is found for camera device - Id : " + this.mCameraId + ".  May be attempting to bind too many use cases. Existing surfaces: " + list + " New configs: " + list2);
        }
        List<Integer> useCasesPriorityOrder = getUseCasesPriorityOrder(list2);
        ArrayList arrayList2 = new ArrayList();
        Iterator<Integer> it3 = useCasesPriorityOrder.iterator();
        while (it3.hasNext()) {
            arrayList2.add(getSupportedOutputSizes(list2.get(it3.next().intValue())));
        }
        Iterator<List<Size>> it4 = getAllPossibleSizeArrangements(arrayList2).iterator();
        while (true) {
            if (!it4.hasNext()) {
                map = null;
                break;
            }
            List<Size> next = it4.next();
            ArrayList arrayList3 = new ArrayList();
            Iterator<AttachedSurfaceInfo> it5 = list.iterator();
            while (it5.hasNext()) {
                arrayList3.add(it5.next().getSurfaceConfig());
            }
            for (int i2 = 0; i2 < next.size(); i2++) {
                arrayList3.add(SurfaceConfig.transformSurfaceConfig(list2.get(useCasesPriorityOrder.get(i2).intValue()).getInputFormat(), next.get(i2), this.mSurfaceSizeDefinition));
            }
            if (checkSupported(arrayList3)) {
                map = new HashMap();
                for (UseCaseConfig<?> useCaseConfig : list2) {
                    map.put(useCaseConfig, next.get(useCasesPriorityOrder.indexOf(Integer.valueOf(list2.indexOf(useCaseConfig)))));
                }
            }
        }
        if (map != null) {
            return map;
        }
        throw new IllegalArgumentException("No supported surface combination is found for camera device - Id : " + this.mCameraId + " and Hardware level: " + this.mHardwareLevel + ". May be the specified resolution is too large and not supported. Existing surfaces: " + list + " New configs: " + list2);
    }

    @NonNull
    @VisibleForTesting
    public List<Size> getSupportedOutputSizes(@NonNull UseCaseConfig<?> useCaseConfig) {
        int inputFormat = useCaseConfig.getInputFormat();
        ImageOutputConfig imageOutputConfig = (ImageOutputConfig) useCaseConfig;
        Size[] customizedSupportSizesFromConfig = getCustomizedSupportSizesFromConfig(inputFormat, imageOutputConfig);
        if (customizedSupportSizesFromConfig == null) {
            customizedSupportSizesFromConfig = getAllOutputSizesByFormat(inputFormat);
        }
        ArrayList arrayList = new ArrayList();
        Size maxResolution = imageOutputConfig.getMaxResolution(null);
        Size maxOutputSizeByFormat = getMaxOutputSizeByFormat(inputFormat);
        if (maxResolution == null || SizeUtil.getArea(maxOutputSizeByFormat) < SizeUtil.getArea(maxResolution)) {
            maxResolution = maxOutputSizeByFormat;
        }
        Arrays.sort(customizedSupportSizesFromConfig, new CompareSizesByArea(true));
        Size targetSize = getTargetSize(imageOutputConfig);
        Size size = SizeUtil.RESOLUTION_VGA;
        int area = SizeUtil.getArea(size);
        if (SizeUtil.getArea(maxResolution) < area) {
            size = SizeUtil.RESOLUTION_ZERO;
        } else if (targetSize != null && SizeUtil.getArea(targetSize) < area) {
            size = targetSize;
        }
        for (Size size2 : customizedSupportSizesFromConfig) {
            if (SizeUtil.getArea(size2) <= SizeUtil.getArea(maxResolution) && SizeUtil.getArea(size2) >= SizeUtil.getArea(size) && !arrayList.contains(size2)) {
                arrayList.add(size2);
            }
        }
        if (arrayList.isEmpty()) {
            throw new IllegalArgumentException("Can not get supported output size under supported maximum for the format: " + inputFormat);
        }
        Rational targetAspectRatio = getTargetAspectRatio(imageOutputConfig);
        if (targetSize == null) {
            targetSize = imageOutputConfig.getDefaultResolution(null);
        }
        ArrayList arrayList2 = new ArrayList();
        new HashMap();
        if (targetAspectRatio == null) {
            arrayList2.addAll(arrayList);
            if (targetSize != null) {
                removeSupportedSizesByTargetSize(arrayList2, targetSize);
            }
        } else {
            Map<Rational, List<Size>> mapGroupSizesByAspectRatio = groupSizesByAspectRatio(arrayList);
            if (targetSize != null) {
                Iterator<Rational> it = mapGroupSizesByAspectRatio.keySet().iterator();
                while (it.hasNext()) {
                    removeSupportedSizesByTargetSize(mapGroupSizesByAspectRatio.get(it.next()), targetSize);
                }
            }
            ArrayList arrayList3 = new ArrayList(mapGroupSizesByAspectRatio.keySet());
            Collections.sort(arrayList3, new AspectRatioUtil.CompareAspectRatiosByDistanceToTargetRatio(targetAspectRatio));
            Iterator it2 = arrayList3.iterator();
            while (it2.hasNext()) {
                for (Size size3 : mapGroupSizesByAspectRatio.get((Rational) it2.next())) {
                    if (!arrayList2.contains(size3)) {
                        arrayList2.add(size3);
                    }
                }
            }
        }
        return this.mResolutionCorrector.insertOrPrioritize(SurfaceConfig.getConfigType(useCaseConfig.getInputFormat()), arrayList2);
    }

    public boolean isBurstCaptureSupported() {
        return this.mIsBurstCaptureSupported;
    }

    public boolean isRawSupported() {
        return this.mIsRawSupported;
    }

    public SurfaceConfig transformSurfaceConfig(int i2, Size size) {
        return SurfaceConfig.transformSurfaceConfig(i2, size, this.mSurfaceSizeDefinition);
    }
}
