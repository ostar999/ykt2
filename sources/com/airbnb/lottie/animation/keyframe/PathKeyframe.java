package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;

/* loaded from: classes.dex */
public class PathKeyframe extends Keyframe<PointF> {

    @Nullable
    private Path path;
    private final Keyframe<PointF> pointKeyFrame;

    public PathKeyframe(LottieComposition lottieComposition, Keyframe<PointF> keyframe) {
        super(lottieComposition, keyframe.startValue, keyframe.endValue, keyframe.interpolator, keyframe.startFrame, keyframe.endFrame);
        this.pointKeyFrame = keyframe;
        createPath();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void createPath() {
        T t2;
        T t3 = this.endValue;
        boolean z2 = (t3 == 0 || (t2 = this.startValue) == 0 || !((PointF) t2).equals(((PointF) t3).x, ((PointF) t3).y)) ? false : true;
        T t4 = this.endValue;
        if (t4 == 0 || z2) {
            return;
        }
        Keyframe<PointF> keyframe = this.pointKeyFrame;
        this.path = Utils.createPath((PointF) this.startValue, (PointF) t4, keyframe.pathCp1, keyframe.pathCp2);
    }

    @Nullable
    public Path getPath() {
        return this.path;
    }
}
