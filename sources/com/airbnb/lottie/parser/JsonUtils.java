package com.airbnb.lottie.parser;

import android.graphics.Color;
import android.graphics.PointF;
import androidx.annotation.ColorInt;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
class JsonUtils {
    private static final JsonReader.Options POINT_NAMES = JsonReader.Options.of("x", "y");

    /* renamed from: com.airbnb.lottie.parser.JsonUtils$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$parser$moshi$JsonReader$Token;

        static {
            int[] iArr = new int[JsonReader.Token.values().length];
            $SwitchMap$com$airbnb$lottie$parser$moshi$JsonReader$Token = iArr;
            try {
                iArr[JsonReader.Token.NUMBER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$parser$moshi$JsonReader$Token[JsonReader.Token.BEGIN_ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$parser$moshi$JsonReader$Token[JsonReader.Token.BEGIN_OBJECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private JsonUtils() {
    }

    private static PointF jsonArrayToPoint(JsonReader jsonReader, float f2) throws IOException {
        jsonReader.beginArray();
        float fNextDouble = (float) jsonReader.nextDouble();
        float fNextDouble2 = (float) jsonReader.nextDouble();
        while (jsonReader.peek() != JsonReader.Token.END_ARRAY) {
            jsonReader.skipValue();
        }
        jsonReader.endArray();
        return new PointF(fNextDouble * f2, fNextDouble2 * f2);
    }

    private static PointF jsonNumbersToPoint(JsonReader jsonReader, float f2) throws IOException {
        float fNextDouble = (float) jsonReader.nextDouble();
        float fNextDouble2 = (float) jsonReader.nextDouble();
        while (jsonReader.hasNext()) {
            jsonReader.skipValue();
        }
        return new PointF(fNextDouble * f2, fNextDouble2 * f2);
    }

    private static PointF jsonObjectToPoint(JsonReader jsonReader, float f2) throws IOException {
        jsonReader.beginObject();
        float fValueFromObject = 0.0f;
        float fValueFromObject2 = 0.0f;
        while (jsonReader.hasNext()) {
            int iSelectName = jsonReader.selectName(POINT_NAMES);
            if (iSelectName == 0) {
                fValueFromObject = valueFromObject(jsonReader);
            } else if (iSelectName != 1) {
                jsonReader.skipName();
                jsonReader.skipValue();
            } else {
                fValueFromObject2 = valueFromObject(jsonReader);
            }
        }
        jsonReader.endObject();
        return new PointF(fValueFromObject * f2, fValueFromObject2 * f2);
    }

    @ColorInt
    public static int jsonToColor(JsonReader jsonReader) throws IOException {
        jsonReader.beginArray();
        int iNextDouble = (int) (jsonReader.nextDouble() * 255.0d);
        int iNextDouble2 = (int) (jsonReader.nextDouble() * 255.0d);
        int iNextDouble3 = (int) (jsonReader.nextDouble() * 255.0d);
        while (jsonReader.hasNext()) {
            jsonReader.skipValue();
        }
        jsonReader.endArray();
        return Color.argb(255, iNextDouble, iNextDouble2, iNextDouble3);
    }

    public static PointF jsonToPoint(JsonReader jsonReader, float f2) throws IOException {
        int i2 = AnonymousClass1.$SwitchMap$com$airbnb$lottie$parser$moshi$JsonReader$Token[jsonReader.peek().ordinal()];
        if (i2 == 1) {
            return jsonNumbersToPoint(jsonReader, f2);
        }
        if (i2 == 2) {
            return jsonArrayToPoint(jsonReader, f2);
        }
        if (i2 == 3) {
            return jsonObjectToPoint(jsonReader, f2);
        }
        throw new IllegalArgumentException("Unknown point starts with " + jsonReader.peek());
    }

    public static List<PointF> jsonToPoints(JsonReader jsonReader, float f2) throws IOException {
        ArrayList arrayList = new ArrayList();
        jsonReader.beginArray();
        while (jsonReader.peek() == JsonReader.Token.BEGIN_ARRAY) {
            jsonReader.beginArray();
            arrayList.add(jsonToPoint(jsonReader, f2));
            jsonReader.endArray();
        }
        jsonReader.endArray();
        return arrayList;
    }

    public static float valueFromObject(JsonReader jsonReader) throws IOException {
        JsonReader.Token tokenPeek = jsonReader.peek();
        int i2 = AnonymousClass1.$SwitchMap$com$airbnb$lottie$parser$moshi$JsonReader$Token[tokenPeek.ordinal()];
        if (i2 == 1) {
            return (float) jsonReader.nextDouble();
        }
        if (i2 != 2) {
            throw new IllegalArgumentException("Unknown value for token of type " + tokenPeek);
        }
        jsonReader.beginArray();
        float fNextDouble = (float) jsonReader.nextDouble();
        while (jsonReader.hasNext()) {
            jsonReader.skipValue();
        }
        jsonReader.endArray();
        return fNextDouble;
    }
}
