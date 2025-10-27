package com.airbnb.lottie.parser;

import android.graphics.PointF;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.content.CircleShape;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;

/* loaded from: classes2.dex */
class CircleShapeParser {
    private static JsonReader.Options NAMES = JsonReader.Options.of("nm", "p", "s", "hd", "d");

    private CircleShapeParser() {
    }

    public static CircleShape parse(JsonReader jsonReader, LottieComposition lottieComposition, int i2) throws IOException {
        boolean z2 = i2 == 3;
        boolean zNextBoolean = false;
        String strNextString = null;
        AnimatableValue<PointF, PointF> splitPath = null;
        AnimatablePointValue point = null;
        while (jsonReader.hasNext()) {
            int iSelectName = jsonReader.selectName(NAMES);
            if (iSelectName == 0) {
                strNextString = jsonReader.nextString();
            } else if (iSelectName == 1) {
                splitPath = AnimatablePathValueParser.parseSplitPath(jsonReader, lottieComposition);
            } else if (iSelectName == 2) {
                point = AnimatableValueParser.parsePoint(jsonReader, lottieComposition);
            } else if (iSelectName == 3) {
                zNextBoolean = jsonReader.nextBoolean();
            } else if (iSelectName != 4) {
                jsonReader.skipName();
                jsonReader.skipValue();
            } else {
                z2 = jsonReader.nextInt() == 3;
            }
        }
        return new CircleShape(strNextString, splitPath, point, z2, zNextBoolean);
    }
}
