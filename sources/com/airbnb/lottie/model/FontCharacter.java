package com.airbnb.lottie.model;

import androidx.annotation.RestrictTo;
import com.airbnb.lottie.model.content.ShapeGroup;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes2.dex */
public class FontCharacter {
    private final char character;
    private final String fontFamily;
    private final List<ShapeGroup> shapes;
    private final double size;
    private final String style;
    private final double width;

    public FontCharacter(List<ShapeGroup> list, char c3, double d3, double d4, String str, String str2) {
        this.shapes = list;
        this.character = c3;
        this.size = d3;
        this.width = d4;
        this.style = str;
        this.fontFamily = str2;
    }

    public static int hashFor(char c3, String str, String str2) {
        return ((((0 + c3) * 31) + str.hashCode()) * 31) + str2.hashCode();
    }

    public List<ShapeGroup> getShapes() {
        return this.shapes;
    }

    public double getSize() {
        return this.size;
    }

    public String getStyle() {
        return this.style;
    }

    public double getWidth() {
        return this.width;
    }

    public int hashCode() {
        return hashFor(this.character, this.fontFamily, this.style);
    }
}
