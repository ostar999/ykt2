package androidx.camera.core.internal.utils;

import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.yikaobang.yixue.R2;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class SizeUtil {
    public static final Size RESOLUTION_ZERO = new Size(0, 0);
    public static final Size RESOLUTION_VGA = new Size(640, 480);
    public static final Size RESOLUTION_480P = new Size(720, 480);
    public static final Size RESOLUTION_1080P = new Size(R2.attr.iconTint, R2.attr.color_hot_circle_one_end);

    private SizeUtil() {
    }

    public static int getArea(@NonNull Size size) {
        return size.getWidth() * size.getHeight();
    }
}
