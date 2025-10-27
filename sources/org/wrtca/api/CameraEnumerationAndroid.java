package org.wrtca.api;

import android.graphics.ImageFormat;
import cn.hutool.core.text.StrPool;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.wrtca.util.Size;
import org.wrtca.video.Histogram;

/* loaded from: classes9.dex */
public class CameraEnumerationAndroid {
    public static final ArrayList<Size> COMMON_RESOLUTIONS = new ArrayList<>(Arrays.asList(new Size(160, 120), new Size(240, 160), new Size(320, 240), new Size(400, 240), new Size(480, 320), new Size(640, 360), new Size(640, 480), new Size(R2.attr.bl_unSelected_gradient_useLevel, 480), new Size(R2.attr.buttonTint, 480), new Size(800, 600), new Size(960, R2.attr.bl_checked_gradient_centerY), new Size(960, 640), new Size(1024, R2.attr.bl_enabled_gradient_centerColor), new Size(1024, 600), new Size(1280, 720), new Size(1280, 1024), new Size(R2.attr.iconTint, R2.attr.color_hot_circle_one_end), new Size(R2.attr.iconTint, R2.attr.ease_round_radius), new Size(R2.attr.mvGravity, R2.attr.ease_round_radius), new Size(R2.attr.triangleHeight, R2.attr.labelBackground)));
    private static final String TAG = "CameraEnumerationAndroid";

    public static class CaptureFormat {
        public final FramerateRange framerate;
        public final int height;
        public final int imageFormat = 17;
        public final int width;

        public static class FramerateRange {
            public int max;
            public int min;

            public FramerateRange(int i2, int i3) {
                this.min = i2;
                this.max = i3;
            }

            public boolean equals(Object obj) {
                if (!(obj instanceof FramerateRange)) {
                    return false;
                }
                FramerateRange framerateRange = (FramerateRange) obj;
                return this.min == framerateRange.min && this.max == framerateRange.max;
            }

            public int hashCode() {
                return (this.min * 65537) + 1 + this.max;
            }

            public String toString() {
                return StrPool.BRACKET_START + (this.min / 1000.0f) + ":" + (this.max / 1000.0f) + StrPool.BRACKET_END;
            }
        }

        public CaptureFormat(int i2, int i3, int i4, int i5) {
            this.width = i2;
            this.height = i3;
            this.framerate = new FramerateRange(i4, i5);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof CaptureFormat)) {
                return false;
            }
            CaptureFormat captureFormat = (CaptureFormat) obj;
            return this.width == captureFormat.width && this.height == captureFormat.height && this.framerate.equals(captureFormat.framerate);
        }

        public int frameSize() {
            return frameSize(this.width, this.height, 17);
        }

        public int hashCode() {
            return (((this.width * 65497) + this.height) * R2.attr.actionModeSelectAllDrawable) + 1 + this.framerate.hashCode();
        }

        public String toString() {
            return this.width + "x" + this.height + "@" + this.framerate;
        }

        public static int frameSize(int i2, int i3, int i4) {
            if (i4 == 17) {
                return ((i2 * i3) * ImageFormat.getBitsPerPixel(i4)) / 8;
            }
            throw new UnsupportedOperationException("Don't know how to calculate the frame size of non-NV21 image formats.");
        }

        public CaptureFormat(int i2, int i3, FramerateRange framerateRange) {
            this.width = i2;
            this.height = i3;
            this.framerate = framerateRange;
        }
    }

    public static abstract class ClosestComparator<T> implements Comparator<T> {
        private ClosestComparator() {
        }

        @Override // java.util.Comparator
        public int compare(T t2, T t3) {
            return diff(t2) - diff(t3);
        }

        public abstract int diff(T t2);
    }

    public static CaptureFormat.FramerateRange getClosestSupportedFramerateRange(List<CaptureFormat.FramerateRange> list, final int i2) {
        return (CaptureFormat.FramerateRange) Collections.min(list, new ClosestComparator<CaptureFormat.FramerateRange>() { // from class: org.wrtca.api.CameraEnumerationAndroid.1
            private static final int MAX_FPS_DIFF_THRESHOLD = 5000;
            private static final int MAX_FPS_HIGH_DIFF_WEIGHT = 3;
            private static final int MAX_FPS_LOW_DIFF_WEIGHT = 1;
            private static final int MIN_FPS_HIGH_VALUE_WEIGHT = 4;
            private static final int MIN_FPS_LOW_VALUE_WEIGHT = 1;
            private static final int MIN_FPS_THRESHOLD = 8000;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            private int progressivePenalty(int i3, int i4, int i5, int i6) {
                if (i3 < i4) {
                    return i3 * i5;
                }
                return ((i3 - i4) * i6) + (i5 * i4);
            }

            @Override // org.wrtca.api.CameraEnumerationAndroid.ClosestComparator
            public int diff(CaptureFormat.FramerateRange framerateRange) {
                return progressivePenalty(framerateRange.min, 8000, 1, 4) + progressivePenalty(Math.abs((i2 * 1000) - framerateRange.max), 5000, 1, 3);
            }
        });
    }

    public static Size getClosestSupportedSize(List<Size> list, final int i2, final int i3) {
        return (Size) Collections.min(list, new ClosestComparator<Size>() { // from class: org.wrtca.api.CameraEnumerationAndroid.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // org.wrtca.api.CameraEnumerationAndroid.ClosestComparator
            public int diff(Size size) {
                return Math.abs(i2 - size.width) + Math.abs(i3 - size.height);
            }
        });
    }

    public static void reportCameraResolution(Histogram histogram, Size size) {
        histogram.addSample(COMMON_RESOLUTIONS.indexOf(size) + 1);
    }
}
