package androidx.camera.camera2.internal.compat.quirk;

import android.os.Build;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.Quirk;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@RequiresApi(21)
/* loaded from: classes.dex */
public class ExcludedSupportedSizesQuirk implements Quirk {
    private static final String TAG = "ExcludedSupportedSizesQuirk";

    @NonNull
    private List<Size> getHuaweiP20LiteExcludedSizes(@NonNull String str, int i2) {
        ArrayList arrayList = new ArrayList();
        if (str.equals("0") && (i2 == 34 || i2 == 35)) {
            arrayList.add(new Size(720, 720));
            arrayList.add(new Size(400, 400));
        }
        return arrayList;
    }

    @NonNull
    private List<Size> getOnePlus6ExcludedSizes(@NonNull String str, int i2) {
        ArrayList arrayList = new ArrayList();
        if (str.equals("0") && i2 == 256) {
            arrayList.add(new Size(R2.color.alivc_common_bg_white_alpha_40, R2.attr.select_vip_logo));
            arrayList.add(new Size(4000, 3000));
        }
        return arrayList;
    }

    @NonNull
    private List<Size> getOnePlus6TExcludedSizes(@NonNull String str, int i2) {
        ArrayList arrayList = new ArrayList();
        if (str.equals("0") && i2 == 256) {
            arrayList.add(new Size(R2.color.alivc_common_bg_white_alpha_40, R2.attr.select_vip_logo));
            arrayList.add(new Size(4000, 3000));
        }
        return arrayList;
    }

    @NonNull
    private List<Size> getSamsungJ7Api27AboveExcludedSizes(@NonNull String str, int i2) {
        ArrayList arrayList = new ArrayList();
        if (str.equals("0")) {
            if (i2 == 34) {
                arrayList.add(new Size(R2.color.abc_tint_switch_track, R2.attr.secondary_text_color));
                arrayList.add(new Size(R2.color.abc_tint_switch_track, R2.attr.listChoiceIndicatorSingleAnimated));
                arrayList.add(new Size(R2.attr.search_froum_write, R2.attr.search_froum_write));
                arrayList.add(new Size(R2.attr.sr_resistanceOfHeader, R2.attr.materialDividerStyle));
                arrayList.add(new Size(R2.attr.sr_resistanceOfHeader, R2.attr.ic_personal_feedback));
                arrayList.add(new Size(2048, 1536));
                arrayList.add(new Size(2048, R2.attr.contrast));
                arrayList.add(new Size(R2.attr.iconTint, R2.attr.color_hot_circle_one_end));
            } else if (i2 == 35) {
                arrayList.add(new Size(2048, 1536));
                arrayList.add(new Size(2048, R2.attr.contrast));
                arrayList.add(new Size(R2.attr.iconTint, R2.attr.color_hot_circle_one_end));
            }
        } else if (str.equals("1") && (i2 == 34 || i2 == 35)) {
            arrayList.add(new Size(R2.attr.network_load_fail, R2.attr.icon_collect_new));
            arrayList.add(new Size(R2.attr.mvGravity, R2.attr.ease_round_radius));
            arrayList.add(new Size(R2.attr.iconTint, R2.attr.iconTint));
            arrayList.add(new Size(2048, 1536));
            arrayList.add(new Size(2048, R2.attr.contrast));
            arrayList.add(new Size(R2.attr.iconTint, R2.attr.color_hot_circle_one_end));
        }
        return arrayList;
    }

    @NonNull
    private List<Size> getSamsungJ7PrimeApi27AboveExcludedSizes(@NonNull String str, int i2) {
        ArrayList arrayList = new ArrayList();
        if (str.equals("0")) {
            if (i2 == 34) {
                arrayList.add(new Size(R2.color.abc_tint_switch_track, R2.attr.secondary_text_color));
                arrayList.add(new Size(R2.color.abc_tint_switch_track, R2.attr.listChoiceIndicatorSingleAnimated));
                arrayList.add(new Size(R2.attr.search_froum_write, R2.attr.search_froum_write));
                arrayList.add(new Size(R2.attr.sr_resistanceOfHeader, R2.attr.materialDividerStyle));
                arrayList.add(new Size(R2.attr.sr_resistanceOfHeader, R2.attr.ic_personal_feedback));
                arrayList.add(new Size(2048, 1536));
                arrayList.add(new Size(2048, R2.attr.contrast));
                arrayList.add(new Size(R2.attr.iconTint, R2.attr.color_hot_circle_one_end));
            } else if (i2 == 35) {
                arrayList.add(new Size(R2.color.abc_tint_switch_track, R2.attr.listChoiceIndicatorSingleAnimated));
                arrayList.add(new Size(R2.attr.search_froum_write, R2.attr.search_froum_write));
                arrayList.add(new Size(R2.attr.sr_resistanceOfHeader, R2.attr.materialDividerStyle));
                arrayList.add(new Size(R2.attr.sr_resistanceOfHeader, R2.attr.ic_personal_feedback));
                arrayList.add(new Size(2048, 1536));
                arrayList.add(new Size(2048, R2.attr.contrast));
                arrayList.add(new Size(R2.attr.iconTint, R2.attr.color_hot_circle_one_end));
            }
        } else if (str.equals("1") && (i2 == 34 || i2 == 35)) {
            arrayList.add(new Size(R2.attr.sr_resistanceOfHeader, R2.attr.materialDividerStyle));
            arrayList.add(new Size(R2.attr.sr_resistanceOfHeader, R2.attr.ic_personal_feedback));
            arrayList.add(new Size(R2.attr.materialDividerStyle, R2.attr.materialDividerStyle));
            arrayList.add(new Size(R2.attr.iconTint, R2.attr.iconTint));
            arrayList.add(new Size(2048, 1536));
            arrayList.add(new Size(2048, R2.attr.contrast));
            arrayList.add(new Size(R2.attr.iconTint, R2.attr.color_hot_circle_one_end));
        }
        return arrayList;
    }

    private static boolean isHuaweiP20Lite() {
        return "HUAWEI".equalsIgnoreCase(Build.BRAND) && "HWANE".equalsIgnoreCase(Build.DEVICE);
    }

    private static boolean isOnePlus6() {
        return "OnePlus".equalsIgnoreCase(Build.BRAND) && "OnePlus6".equalsIgnoreCase(Build.DEVICE);
    }

    private static boolean isOnePlus6T() {
        return "OnePlus".equalsIgnoreCase(Build.BRAND) && "OnePlus6T".equalsIgnoreCase(Build.DEVICE);
    }

    private static boolean isSamsungJ7Api27Above() {
        String str = Build.BRAND;
        Locale locale = Locale.US;
        return "SAMSUNG".equalsIgnoreCase(str.toUpperCase(locale)) && "J7XELTE".equalsIgnoreCase(Build.DEVICE.toUpperCase(locale)) && Build.VERSION.SDK_INT >= 27;
    }

    private static boolean isSamsungJ7PrimeApi27Above() {
        String str = Build.BRAND;
        Locale locale = Locale.US;
        return "SAMSUNG".equalsIgnoreCase(str.toUpperCase(locale)) && "ON7XELTE".equalsIgnoreCase(Build.DEVICE.toUpperCase(locale)) && Build.VERSION.SDK_INT >= 27;
    }

    public static boolean load() {
        return isOnePlus6() || isOnePlus6T() || isHuaweiP20Lite() || isSamsungJ7PrimeApi27Above() || isSamsungJ7Api27Above();
    }

    @NonNull
    public List<Size> getExcludedSizes(@NonNull String str, int i2) {
        if (isOnePlus6()) {
            return getOnePlus6ExcludedSizes(str, i2);
        }
        if (isOnePlus6T()) {
            return getOnePlus6TExcludedSizes(str, i2);
        }
        if (isHuaweiP20Lite()) {
            return getHuaweiP20LiteExcludedSizes(str, i2);
        }
        if (isSamsungJ7PrimeApi27Above()) {
            return getSamsungJ7PrimeApi27AboveExcludedSizes(str, i2);
        }
        if (isSamsungJ7Api27Above()) {
            return getSamsungJ7Api27AboveExcludedSizes(str, i2);
        }
        Logger.w(TAG, "Cannot retrieve list of supported sizes to exclude on this device.");
        return Collections.emptyList();
    }
}
