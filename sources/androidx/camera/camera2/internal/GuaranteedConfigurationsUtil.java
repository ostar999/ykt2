package androidx.camera.camera2.internal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.impl.SurfaceCombination;
import androidx.camera.core.impl.SurfaceConfig;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class GuaranteedConfigurationsUtil {
    private GuaranteedConfigurationsUtil() {
    }

    @NonNull
    public static List<SurfaceCombination> generateSupportedCombinationList(int i2, boolean z2, boolean z3) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(getLegacySupportedCombinationList());
        if (i2 == 0 || i2 == 1 || i2 == 3) {
            arrayList.addAll(getLimitedSupportedCombinationList());
        }
        if (i2 == 1 || i2 == 3) {
            arrayList.addAll(getFullSupportedCombinationList());
        }
        if (z2) {
            arrayList.addAll(getRAWSupportedCombinationList());
        }
        if (z3 && i2 == 0) {
            arrayList.addAll(getBurstSupportedCombinationList());
        }
        if (i2 == 3) {
            arrayList.addAll(getLevel3SupportedCombinationList());
        }
        return arrayList;
    }

    @NonNull
    public static List<SurfaceCombination> getBurstSupportedCombinationList() {
        ArrayList arrayList = new ArrayList();
        SurfaceCombination surfaceCombination = new SurfaceCombination();
        SurfaceConfig.ConfigType configType = SurfaceConfig.ConfigType.PRIV;
        SurfaceConfig.ConfigSize configSize = SurfaceConfig.ConfigSize.PREVIEW;
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        SurfaceConfig.ConfigSize configSize2 = SurfaceConfig.ConfigSize.MAXIMUM;
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(configType, configSize2));
        arrayList.add(surfaceCombination);
        SurfaceCombination surfaceCombination2 = new SurfaceCombination();
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        SurfaceConfig.ConfigType configType2 = SurfaceConfig.ConfigType.YUV;
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(configType2, configSize2));
        arrayList.add(surfaceCombination2);
        SurfaceCombination surfaceCombination3 = new SurfaceCombination();
        surfaceCombination3.addSurfaceConfig(SurfaceConfig.create(configType2, configSize));
        surfaceCombination3.addSurfaceConfig(SurfaceConfig.create(configType2, configSize2));
        arrayList.add(surfaceCombination3);
        return arrayList;
    }

    @NonNull
    public static List<SurfaceCombination> getFullSupportedCombinationList() {
        ArrayList arrayList = new ArrayList();
        SurfaceCombination surfaceCombination = new SurfaceCombination();
        SurfaceConfig.ConfigType configType = SurfaceConfig.ConfigType.PRIV;
        SurfaceConfig.ConfigSize configSize = SurfaceConfig.ConfigSize.PREVIEW;
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        SurfaceConfig.ConfigSize configSize2 = SurfaceConfig.ConfigSize.MAXIMUM;
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(configType, configSize2));
        arrayList.add(surfaceCombination);
        SurfaceCombination surfaceCombination2 = new SurfaceCombination();
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        SurfaceConfig.ConfigType configType2 = SurfaceConfig.ConfigType.YUV;
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(configType2, configSize2));
        arrayList.add(surfaceCombination2);
        SurfaceCombination surfaceCombination3 = new SurfaceCombination();
        surfaceCombination3.addSurfaceConfig(SurfaceConfig.create(configType2, configSize));
        surfaceCombination3.addSurfaceConfig(SurfaceConfig.create(configType2, configSize2));
        arrayList.add(surfaceCombination3);
        SurfaceCombination surfaceCombination4 = new SurfaceCombination();
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, configSize2));
        arrayList.add(surfaceCombination4);
        SurfaceCombination surfaceCombination5 = new SurfaceCombination();
        SurfaceConfig.ConfigSize configSize3 = SurfaceConfig.ConfigSize.VGA;
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(configType2, configSize3));
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(configType2, configSize2));
        arrayList.add(surfaceCombination5);
        SurfaceCombination surfaceCombination6 = new SurfaceCombination();
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(configType2, configSize3));
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(configType2, configSize));
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(configType2, configSize2));
        arrayList.add(surfaceCombination6);
        return arrayList;
    }

    @NonNull
    public static List<SurfaceCombination> getLegacySupportedCombinationList() {
        ArrayList arrayList = new ArrayList();
        SurfaceCombination surfaceCombination = new SurfaceCombination();
        SurfaceConfig.ConfigType configType = SurfaceConfig.ConfigType.PRIV;
        SurfaceConfig.ConfigSize configSize = SurfaceConfig.ConfigSize.MAXIMUM;
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        arrayList.add(surfaceCombination);
        SurfaceCombination surfaceCombination2 = new SurfaceCombination();
        SurfaceConfig.ConfigType configType2 = SurfaceConfig.ConfigType.JPEG;
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(configType2, configSize));
        arrayList.add(surfaceCombination2);
        SurfaceCombination surfaceCombination3 = new SurfaceCombination();
        SurfaceConfig.ConfigType configType3 = SurfaceConfig.ConfigType.YUV;
        surfaceCombination3.addSurfaceConfig(SurfaceConfig.create(configType3, configSize));
        arrayList.add(surfaceCombination3);
        SurfaceCombination surfaceCombination4 = new SurfaceCombination();
        SurfaceConfig.ConfigSize configSize2 = SurfaceConfig.ConfigSize.PREVIEW;
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(configType, configSize2));
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(configType2, configSize));
        arrayList.add(surfaceCombination4);
        SurfaceCombination surfaceCombination5 = new SurfaceCombination();
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(configType3, configSize2));
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(configType2, configSize));
        arrayList.add(surfaceCombination5);
        SurfaceCombination surfaceCombination6 = new SurfaceCombination();
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(configType, configSize2));
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(configType, configSize2));
        arrayList.add(surfaceCombination6);
        SurfaceCombination surfaceCombination7 = new SurfaceCombination();
        surfaceCombination7.addSurfaceConfig(SurfaceConfig.create(configType, configSize2));
        surfaceCombination7.addSurfaceConfig(SurfaceConfig.create(configType3, configSize2));
        arrayList.add(surfaceCombination7);
        SurfaceCombination surfaceCombination8 = new SurfaceCombination();
        surfaceCombination8.addSurfaceConfig(SurfaceConfig.create(configType, configSize2));
        surfaceCombination8.addSurfaceConfig(SurfaceConfig.create(configType3, configSize2));
        surfaceCombination8.addSurfaceConfig(SurfaceConfig.create(configType2, configSize));
        arrayList.add(surfaceCombination8);
        return arrayList;
    }

    @NonNull
    public static List<SurfaceCombination> getLevel3SupportedCombinationList() {
        ArrayList arrayList = new ArrayList();
        SurfaceCombination surfaceCombination = new SurfaceCombination();
        SurfaceConfig.ConfigType configType = SurfaceConfig.ConfigType.PRIV;
        SurfaceConfig.ConfigSize configSize = SurfaceConfig.ConfigSize.PREVIEW;
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        SurfaceConfig.ConfigSize configSize2 = SurfaceConfig.ConfigSize.VGA;
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(configType, configSize2));
        SurfaceConfig.ConfigType configType2 = SurfaceConfig.ConfigType.YUV;
        SurfaceConfig.ConfigSize configSize3 = SurfaceConfig.ConfigSize.MAXIMUM;
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(configType2, configSize3));
        SurfaceConfig.ConfigType configType3 = SurfaceConfig.ConfigType.RAW;
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(configType3, configSize3));
        arrayList.add(surfaceCombination);
        SurfaceCombination surfaceCombination2 = new SurfaceCombination();
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(configType, configSize2));
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, configSize3));
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(configType3, configSize3));
        arrayList.add(surfaceCombination2);
        return arrayList;
    }

    @NonNull
    public static List<SurfaceCombination> getLimitedSupportedCombinationList() {
        ArrayList arrayList = new ArrayList();
        SurfaceCombination surfaceCombination = new SurfaceCombination();
        SurfaceConfig.ConfigType configType = SurfaceConfig.ConfigType.PRIV;
        SurfaceConfig.ConfigSize configSize = SurfaceConfig.ConfigSize.PREVIEW;
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        SurfaceConfig.ConfigSize configSize2 = SurfaceConfig.ConfigSize.RECORD;
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(configType, configSize2));
        arrayList.add(surfaceCombination);
        SurfaceCombination surfaceCombination2 = new SurfaceCombination();
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        SurfaceConfig.ConfigType configType2 = SurfaceConfig.ConfigType.YUV;
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(configType2, configSize2));
        arrayList.add(surfaceCombination2);
        SurfaceCombination surfaceCombination3 = new SurfaceCombination();
        surfaceCombination3.addSurfaceConfig(SurfaceConfig.create(configType2, configSize));
        surfaceCombination3.addSurfaceConfig(SurfaceConfig.create(configType2, configSize2));
        arrayList.add(surfaceCombination3);
        SurfaceCombination surfaceCombination4 = new SurfaceCombination();
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(configType, configSize2));
        SurfaceConfig.ConfigType configType3 = SurfaceConfig.ConfigType.JPEG;
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(configType3, configSize2));
        arrayList.add(surfaceCombination4);
        SurfaceCombination surfaceCombination5 = new SurfaceCombination();
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(configType2, configSize2));
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(configType3, configSize2));
        arrayList.add(surfaceCombination5);
        SurfaceCombination surfaceCombination6 = new SurfaceCombination();
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(configType2, configSize));
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(configType2, configSize));
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(configType3, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination6);
        return arrayList;
    }

    @NonNull
    public static List<SurfaceCombination> getRAWSupportedCombinationList() {
        ArrayList arrayList = new ArrayList();
        SurfaceCombination surfaceCombination = new SurfaceCombination();
        SurfaceConfig.ConfigType configType = SurfaceConfig.ConfigType.RAW;
        SurfaceConfig.ConfigSize configSize = SurfaceConfig.ConfigSize.MAXIMUM;
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        arrayList.add(surfaceCombination);
        SurfaceCombination surfaceCombination2 = new SurfaceCombination();
        SurfaceConfig.ConfigType configType2 = SurfaceConfig.ConfigType.PRIV;
        SurfaceConfig.ConfigSize configSize2 = SurfaceConfig.ConfigSize.PREVIEW;
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(configType2, configSize2));
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        arrayList.add(surfaceCombination2);
        SurfaceCombination surfaceCombination3 = new SurfaceCombination();
        SurfaceConfig.ConfigType configType3 = SurfaceConfig.ConfigType.YUV;
        surfaceCombination3.addSurfaceConfig(SurfaceConfig.create(configType3, configSize2));
        surfaceCombination3.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        arrayList.add(surfaceCombination3);
        SurfaceCombination surfaceCombination4 = new SurfaceCombination();
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(configType2, configSize2));
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(configType2, configSize2));
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        arrayList.add(surfaceCombination4);
        SurfaceCombination surfaceCombination5 = new SurfaceCombination();
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(configType2, configSize2));
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(configType3, configSize2));
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        arrayList.add(surfaceCombination5);
        SurfaceCombination surfaceCombination6 = new SurfaceCombination();
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(configType3, configSize2));
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(configType3, configSize2));
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        arrayList.add(surfaceCombination6);
        SurfaceCombination surfaceCombination7 = new SurfaceCombination();
        surfaceCombination7.addSurfaceConfig(SurfaceConfig.create(configType2, configSize2));
        SurfaceConfig.ConfigType configType4 = SurfaceConfig.ConfigType.JPEG;
        surfaceCombination7.addSurfaceConfig(SurfaceConfig.create(configType4, configSize));
        surfaceCombination7.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        arrayList.add(surfaceCombination7);
        SurfaceCombination surfaceCombination8 = new SurfaceCombination();
        surfaceCombination8.addSurfaceConfig(SurfaceConfig.create(configType3, configSize2));
        surfaceCombination8.addSurfaceConfig(SurfaceConfig.create(configType4, configSize));
        surfaceCombination8.addSurfaceConfig(SurfaceConfig.create(configType, configSize));
        arrayList.add(surfaceCombination8);
        return arrayList;
    }
}
