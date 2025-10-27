package androidx.camera.core;

import androidx.annotation.RequiresApi;
import com.google.auto.value.AutoValue;

@RequiresApi(21)
/* loaded from: classes.dex */
final class ImageReaderFormatRecommender {

    @AutoValue
    public static abstract class FormatCombo {
        public static FormatCombo create(int i2, int i3) {
            return new AutoValue_ImageReaderFormatRecommender_FormatCombo(i2, i3);
        }

        public abstract int imageAnalysisFormat();

        public abstract int imageCaptureFormat();
    }

    private ImageReaderFormatRecommender() {
    }

    public static FormatCombo chooseCombo() {
        return FormatCombo.create(256, 35);
    }
}
