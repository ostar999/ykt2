package androidx.camera.camera2.internal.compat.quirk;

import android.os.Build;
import android.util.Range;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.impl.Quirk;
import androidx.camera.core.impl.SurfaceConfig;
import com.yikaobang.yixue.R2;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RequiresApi(21)
/* loaded from: classes.dex */
public class ExtraCroppingQuirk implements Quirk {
    private static final Map<String, Range<Integer>> SAMSUNG_DISTORTION_MODELS_TO_API_LEVEL_MAP;

    /* renamed from: androidx.camera.camera2.internal.compat.quirk.ExtraCroppingQuirk$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$camera$core$impl$SurfaceConfig$ConfigType;

        static {
            int[] iArr = new int[SurfaceConfig.ConfigType.values().length];
            $SwitchMap$androidx$camera$core$impl$SurfaceConfig$ConfigType = iArr;
            try {
                iArr[SurfaceConfig.ConfigType.PRIV.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$camera$core$impl$SurfaceConfig$ConfigType[SurfaceConfig.ConfigType.YUV.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$camera$core$impl$SurfaceConfig$ConfigType[SurfaceConfig.ConfigType.JPEG.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    static {
        HashMap map = new HashMap();
        SAMSUNG_DISTORTION_MODELS_TO_API_LEVEL_MAP = map;
        map.put("SM-T580", null);
        map.put("SM-J710MN", new Range(21, 26));
        map.put("SM-A320FL", null);
        map.put("SM-G570M", null);
        map.put("SM-G610F", null);
        map.put("SM-G610M", new Range(21, 26));
    }

    private static boolean isSamsungDistortion() {
        if (!("samsung".equalsIgnoreCase(Build.BRAND) && SAMSUNG_DISTORTION_MODELS_TO_API_LEVEL_MAP.containsKey(Build.MODEL.toUpperCase(Locale.US)))) {
            return false;
        }
        Range<Integer> range = SAMSUNG_DISTORTION_MODELS_TO_API_LEVEL_MAP.get(Build.MODEL.toUpperCase(Locale.US));
        if (range == null) {
            return true;
        }
        return range.contains((Range<Integer>) Integer.valueOf(Build.VERSION.SDK_INT));
    }

    public static boolean load() {
        return isSamsungDistortion();
    }

    @Nullable
    public Size getVerifiedResolution(@NonNull SurfaceConfig.ConfigType configType) {
        if (!isSamsungDistortion()) {
            return null;
        }
        int i2 = AnonymousClass1.$SwitchMap$androidx$camera$core$impl$SurfaceConfig$ConfigType[configType.ordinal()];
        if (i2 == 1) {
            return new Size(R2.attr.iconTint, R2.attr.color_hot_circle_one_end);
        }
        if (i2 == 2) {
            return new Size(1280, 720);
        }
        if (i2 != 3) {
            return null;
        }
        return new Size(R2.attr.sr_resistanceOfHeader, R2.attr.ic_personal_feedback);
    }
}
