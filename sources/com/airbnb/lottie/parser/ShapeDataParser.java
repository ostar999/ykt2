package com.airbnb.lottie.parser;

import android.graphics.PointF;
import com.airbnb.lottie.model.CubicCurveData;
import com.airbnb.lottie.model.content.ShapeData;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.MiscUtils;
import com.umeng.analytics.pro.am;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public class ShapeDataParser implements ValueParser<ShapeData> {
    public static final ShapeDataParser INSTANCE = new ShapeDataParser();
    private static final JsonReader.Options NAMES = JsonReader.Options.of(am.aF, "v", am.aC, "o");

    private ShapeDataParser() {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.airbnb.lottie.parser.ValueParser
    public ShapeData parse(JsonReader jsonReader, float f2) throws IOException {
        if (jsonReader.peek() == JsonReader.Token.BEGIN_ARRAY) {
            jsonReader.beginArray();
        }
        jsonReader.beginObject();
        List<PointF> listJsonToPoints = null;
        List<PointF> listJsonToPoints2 = null;
        List<PointF> listJsonToPoints3 = null;
        boolean zNextBoolean = false;
        while (jsonReader.hasNext()) {
            int iSelectName = jsonReader.selectName(NAMES);
            if (iSelectName == 0) {
                zNextBoolean = jsonReader.nextBoolean();
            } else if (iSelectName == 1) {
                listJsonToPoints = JsonUtils.jsonToPoints(jsonReader, f2);
            } else if (iSelectName == 2) {
                listJsonToPoints2 = JsonUtils.jsonToPoints(jsonReader, f2);
            } else if (iSelectName != 3) {
                jsonReader.skipName();
                jsonReader.skipValue();
            } else {
                listJsonToPoints3 = JsonUtils.jsonToPoints(jsonReader, f2);
            }
        }
        jsonReader.endObject();
        if (jsonReader.peek() == JsonReader.Token.END_ARRAY) {
            jsonReader.endArray();
        }
        if (listJsonToPoints == null || listJsonToPoints2 == null || listJsonToPoints3 == null) {
            throw new IllegalArgumentException("Shape data was missing information.");
        }
        if (listJsonToPoints.isEmpty()) {
            return new ShapeData(new PointF(), false, Collections.emptyList());
        }
        int size = listJsonToPoints.size();
        PointF pointF = listJsonToPoints.get(0);
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 1; i2 < size; i2++) {
            PointF pointF2 = listJsonToPoints.get(i2);
            int i3 = i2 - 1;
            arrayList.add(new CubicCurveData(MiscUtils.addPoints(listJsonToPoints.get(i3), listJsonToPoints3.get(i3)), MiscUtils.addPoints(pointF2, listJsonToPoints2.get(i2)), pointF2));
        }
        if (zNextBoolean) {
            PointF pointF3 = listJsonToPoints.get(0);
            int i4 = size - 1;
            arrayList.add(new CubicCurveData(MiscUtils.addPoints(listJsonToPoints.get(i4), listJsonToPoints3.get(i4)), MiscUtils.addPoints(pointF3, listJsonToPoints2.get(0)), pointF3));
        }
        return new ShapeData(pointF, zNextBoolean, arrayList);
    }
}
