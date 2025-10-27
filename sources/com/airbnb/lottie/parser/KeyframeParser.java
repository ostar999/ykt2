package com.airbnb.lottie.parser;

import android.graphics.PointF;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.annotation.Nullable;
import androidx.collection.SparseArrayCompat;
import androidx.core.view.animation.PathInterpolatorCompat;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.umeng.analytics.pro.am;
import java.io.IOException;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
class KeyframeParser {
    private static final float MAX_CP_VALUE = 100.0f;
    private static SparseArrayCompat<WeakReference<Interpolator>> pathInterpolatorCache;
    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    static JsonReader.Options NAMES = JsonReader.Options.of("t", "s", AliyunLogKey.KEY_EVENT, "o", am.aC, "h", "to", "ti");

    @Nullable
    private static WeakReference<Interpolator> getInterpolator(int i2) {
        WeakReference<Interpolator> weakReference;
        synchronized (KeyframeParser.class) {
            weakReference = pathInterpolatorCache().get(i2);
        }
        return weakReference;
    }

    public static <T> Keyframe<T> parse(JsonReader jsonReader, LottieComposition lottieComposition, float f2, ValueParser<T> valueParser, boolean z2) throws IOException {
        return z2 ? parseKeyframe(lottieComposition, jsonReader, f2, valueParser) : parseStaticValue(jsonReader, f2, valueParser);
    }

    private static <T> Keyframe<T> parseKeyframe(LottieComposition lottieComposition, JsonReader jsonReader, float f2, ValueParser<T> valueParser) throws IOException {
        Interpolator interpolator;
        T t2;
        Interpolator interpolatorCreate;
        jsonReader.beginObject();
        PointF pointFJsonToPoint = null;
        PointF pointFJsonToPoint2 = null;
        T t3 = null;
        T t4 = null;
        PointF pointFJsonToPoint3 = null;
        PointF pointFJsonToPoint4 = null;
        float fNextDouble = 0.0f;
        while (true) {
            boolean z2 = false;
            while (jsonReader.hasNext()) {
                switch (jsonReader.selectName(NAMES)) {
                    case 0:
                        fNextDouble = (float) jsonReader.nextDouble();
                        break;
                    case 1:
                        t4 = valueParser.parse(jsonReader, f2);
                        break;
                    case 2:
                        t3 = valueParser.parse(jsonReader, f2);
                        break;
                    case 3:
                        pointFJsonToPoint = JsonUtils.jsonToPoint(jsonReader, f2);
                        break;
                    case 4:
                        pointFJsonToPoint2 = JsonUtils.jsonToPoint(jsonReader, f2);
                        break;
                    case 5:
                        if (jsonReader.nextInt() == 1) {
                            z2 = true;
                            break;
                        }
                        break;
                    case 6:
                        pointFJsonToPoint4 = JsonUtils.jsonToPoint(jsonReader, f2);
                        break;
                    case 7:
                        pointFJsonToPoint3 = JsonUtils.jsonToPoint(jsonReader, f2);
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
            }
            jsonReader.endObject();
            if (z2) {
                interpolator = LINEAR_INTERPOLATOR;
                t2 = t4;
            } else {
                if (pointFJsonToPoint == null || pointFJsonToPoint2 == null) {
                    interpolator = LINEAR_INTERPOLATOR;
                } else {
                    float f3 = -f2;
                    pointFJsonToPoint.x = MiscUtils.clamp(pointFJsonToPoint.x, f3, f2);
                    pointFJsonToPoint.y = MiscUtils.clamp(pointFJsonToPoint.y, -100.0f, MAX_CP_VALUE);
                    pointFJsonToPoint2.x = MiscUtils.clamp(pointFJsonToPoint2.x, f3, f2);
                    float fClamp = MiscUtils.clamp(pointFJsonToPoint2.y, -100.0f, MAX_CP_VALUE);
                    pointFJsonToPoint2.y = fClamp;
                    int iHashFor = Utils.hashFor(pointFJsonToPoint.x, pointFJsonToPoint.y, pointFJsonToPoint2.x, fClamp);
                    WeakReference<Interpolator> interpolator2 = getInterpolator(iHashFor);
                    Interpolator interpolator3 = interpolator2 != null ? interpolator2.get() : null;
                    if (interpolator2 == null || interpolator3 == null) {
                        pointFJsonToPoint.x /= f2;
                        pointFJsonToPoint.y /= f2;
                        float f4 = pointFJsonToPoint2.x / f2;
                        pointFJsonToPoint2.x = f4;
                        float f5 = pointFJsonToPoint2.y / f2;
                        pointFJsonToPoint2.y = f5;
                        try {
                            interpolatorCreate = PathInterpolatorCompat.create(pointFJsonToPoint.x, pointFJsonToPoint.y, f4, f5);
                        } catch (IllegalArgumentException e2) {
                            interpolatorCreate = e2.getMessage().equals("The Path cannot loop back on itself.") ? PathInterpolatorCompat.create(Math.min(pointFJsonToPoint.x, 1.0f), pointFJsonToPoint.y, Math.max(pointFJsonToPoint2.x, 0.0f), pointFJsonToPoint2.y) : new LinearInterpolator();
                        }
                        interpolator3 = interpolatorCreate;
                        try {
                            putInterpolator(iHashFor, new WeakReference(interpolator3));
                        } catch (ArrayIndexOutOfBoundsException unused) {
                        }
                    }
                    interpolator = interpolator3;
                }
                t2 = t3;
            }
            Keyframe<T> keyframe = new Keyframe<>(lottieComposition, t4, t2, interpolator, fNextDouble, null);
            keyframe.pathCp1 = pointFJsonToPoint4;
            keyframe.pathCp2 = pointFJsonToPoint3;
            return keyframe;
        }
    }

    private static <T> Keyframe<T> parseStaticValue(JsonReader jsonReader, float f2, ValueParser<T> valueParser) throws IOException {
        return new Keyframe<>(valueParser.parse(jsonReader, f2));
    }

    private static SparseArrayCompat<WeakReference<Interpolator>> pathInterpolatorCache() {
        if (pathInterpolatorCache == null) {
            pathInterpolatorCache = new SparseArrayCompat<>();
        }
        return pathInterpolatorCache;
    }

    private static void putInterpolator(int i2, WeakReference<Interpolator> weakReference) {
        synchronized (KeyframeParser.class) {
            pathInterpolatorCache.put(i2, weakReference);
        }
    }
}
