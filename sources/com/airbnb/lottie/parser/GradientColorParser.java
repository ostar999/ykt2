package com.airbnb.lottie.parser;

import android.graphics.Color;
import androidx.annotation.IntRange;
import com.airbnb.lottie.model.content.GradientColor;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.MiscUtils;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class GradientColorParser implements ValueParser<GradientColor> {
    private int colorPoints;

    public GradientColorParser(int i2) {
        this.colorPoints = i2;
    }

    private void addOpacityStopsToGradientIfNeeded(GradientColor gradientColor, List<Float> list) {
        int i2 = this.colorPoints * 4;
        if (list.size() <= i2) {
            return;
        }
        int size = (list.size() - i2) / 2;
        double[] dArr = new double[size];
        double[] dArr2 = new double[size];
        int i3 = 0;
        while (i2 < list.size()) {
            if (i2 % 2 == 0) {
                dArr[i3] = list.get(i2).floatValue();
            } else {
                dArr2[i3] = list.get(i2).floatValue();
                i3++;
            }
            i2++;
        }
        for (int i4 = 0; i4 < gradientColor.getSize(); i4++) {
            int i5 = gradientColor.getColors()[i4];
            gradientColor.getColors()[i4] = Color.argb(getOpacityAtPosition(gradientColor.getPositions()[i4], dArr, dArr2), Color.red(i5), Color.green(i5), Color.blue(i5));
        }
    }

    @IntRange(from = 0, to = IjkMediaMeta.AV_CH_LAYOUT_7POINT1_WIDE_BACK)
    private int getOpacityAtPosition(double d3, double[] dArr, double[] dArr2) {
        double dLerp;
        int i2 = 1;
        while (true) {
            if (i2 >= dArr.length) {
                dLerp = dArr2[dArr2.length - 1];
                break;
            }
            int i3 = i2 - 1;
            double d4 = dArr[i3];
            double d5 = dArr[i2];
            if (d5 >= d3) {
                dLerp = MiscUtils.lerp(dArr2[i3], dArr2[i2], MiscUtils.clamp((d3 - d4) / (d5 - d4), 0.0d, 1.0d));
                break;
            }
            i2++;
        }
        return (int) (dLerp * 255.0d);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.airbnb.lottie.parser.ValueParser
    public GradientColor parse(JsonReader jsonReader, float f2) throws IOException {
        ArrayList arrayList = new ArrayList();
        boolean z2 = jsonReader.peek() == JsonReader.Token.BEGIN_ARRAY;
        if (z2) {
            jsonReader.beginArray();
        }
        while (jsonReader.hasNext()) {
            arrayList.add(Float.valueOf((float) jsonReader.nextDouble()));
        }
        if (z2) {
            jsonReader.endArray();
        }
        if (this.colorPoints == -1) {
            this.colorPoints = arrayList.size() / 4;
        }
        int i2 = this.colorPoints;
        float[] fArr = new float[i2];
        int[] iArr = new int[i2];
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < this.colorPoints * 4; i5++) {
            int i6 = i5 / 4;
            double dFloatValue = arrayList.get(i5).floatValue();
            int i7 = i5 % 4;
            if (i7 == 0) {
                fArr[i6] = (float) dFloatValue;
            } else if (i7 == 1) {
                i3 = (int) (dFloatValue * 255.0d);
            } else if (i7 == 2) {
                i4 = (int) (dFloatValue * 255.0d);
            } else if (i7 == 3) {
                iArr[i6] = Color.argb(255, i3, i4, (int) (dFloatValue * 255.0d));
            }
        }
        GradientColor gradientColor = new GradientColor(fArr, iArr);
        addOpacityStopsToGradientIfNeeded(gradientColor, arrayList);
        return gradientColor;
    }
}
