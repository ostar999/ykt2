package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableShapeValue;
import com.airbnb.lottie.model.content.ShapePath;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;

/* loaded from: classes2.dex */
class ShapePathParser {
    static JsonReader.Options NAMES = JsonReader.Options.of("nm", "ind", "ks", "hd");

    private ShapePathParser() {
    }

    public static ShapePath parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        String strNextString = null;
        int iNextInt = 0;
        boolean zNextBoolean = false;
        AnimatableShapeValue shapeData = null;
        while (jsonReader.hasNext()) {
            int iSelectName = jsonReader.selectName(NAMES);
            if (iSelectName == 0) {
                strNextString = jsonReader.nextString();
            } else if (iSelectName == 1) {
                iNextInt = jsonReader.nextInt();
            } else if (iSelectName == 2) {
                shapeData = AnimatableValueParser.parseShapeData(jsonReader, lottieComposition);
            } else if (iSelectName != 3) {
                jsonReader.skipValue();
            } else {
                zNextBoolean = jsonReader.nextBoolean();
            }
        }
        return new ShapePath(strNextString, iNextInt, shapeData, zNextBoolean);
    }
}
