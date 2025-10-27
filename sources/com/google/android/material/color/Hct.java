package com.google.android.material.color;

/* loaded from: classes3.dex */
final class Hct {
    private static final float CHROMA_SEARCH_ENDPOINT = 0.4f;
    private static final float DE_MAX = 1.0f;
    private static final float DE_MAX_ERROR = 1.0E-9f;
    private static final float DL_MAX = 0.2f;
    private static final float LIGHTNESS_SEARCH_ENDPOINT = 0.01f;
    private float chroma;
    private float hue;
    private float tone;

    private Hct(float f2, float f3, float f4) {
        setInternalState(gamutMap(f2, f3, f4));
    }

    private static Cam16 findCamByJ(float f2, float f3, float f4) {
        float f5 = 100.0f;
        float f6 = 1000.0f;
        float f7 = 0.0f;
        Cam16 cam16 = null;
        float f8 = 1000.0f;
        while (Math.abs(f7 - f5) > LIGHTNESS_SEARCH_ENDPOINT) {
            float f9 = ((f5 - f7) / 2.0f) + f7;
            int i2 = Cam16.fromJch(f9, f3, f2).getInt();
            float fLstarFromInt = ColorUtils.lstarFromInt(i2);
            float fAbs = Math.abs(f4 - fLstarFromInt);
            if (fAbs < 0.2f) {
                Cam16 cam16FromInt = Cam16.fromInt(i2);
                float fDistance = cam16FromInt.distance(Cam16.fromJch(cam16FromInt.getJ(), cam16FromInt.getChroma(), f2));
                if (fDistance <= 1.0f && fDistance <= f6) {
                    cam16 = cam16FromInt;
                    f8 = fAbs;
                    f6 = fDistance;
                }
            }
            if (f8 == 0.0f && f6 < DE_MAX_ERROR) {
                break;
            }
            if (fLstarFromInt < f4) {
                f7 = f9;
            } else {
                f5 = f9;
            }
        }
        return cam16;
    }

    public static Hct from(float f2, float f3, float f4) {
        return new Hct(f2, f3, f4);
    }

    public static Hct fromInt(int i2) {
        Cam16 cam16FromInt = Cam16.fromInt(i2);
        return new Hct(cam16FromInt.getHue(), cam16FromInt.getChroma(), ColorUtils.lstarFromInt(i2));
    }

    private static int gamutMap(float f2, float f3, float f4) {
        return gamutMapInViewingConditions(f2, f3, f4, ViewingConditions.DEFAULT);
    }

    public static int gamutMapInViewingConditions(float f2, float f3, float f4, ViewingConditions viewingConditions) {
        if (f3 < 1.0d || Math.round(f4) <= 0.0d || Math.round(f4) >= 100.0d) {
            return ColorUtils.intFromLstar(f4);
        }
        float fSanitizeDegrees = MathUtils.sanitizeDegrees(f2);
        Cam16 cam16 = null;
        boolean z2 = true;
        float f5 = 0.0f;
        float f6 = f3;
        while (Math.abs(f5 - f3) >= CHROMA_SEARCH_ENDPOINT) {
            Cam16 cam16FindCamByJ = findCamByJ(fSanitizeDegrees, f6, f4);
            if (!z2) {
                if (cam16FindCamByJ == null) {
                    f3 = f6;
                } else {
                    f5 = f6;
                    cam16 = cam16FindCamByJ;
                }
                f6 = ((f3 - f5) / 2.0f) + f5;
            } else {
                if (cam16FindCamByJ != null) {
                    return cam16FindCamByJ.viewed(viewingConditions);
                }
                f6 = ((f3 - f5) / 2.0f) + f5;
                z2 = false;
            }
        }
        return cam16 == null ? ColorUtils.intFromLstar(f4) : cam16.viewed(viewingConditions);
    }

    private void setInternalState(int i2) {
        Cam16 cam16FromInt = Cam16.fromInt(i2);
        float fLstarFromInt = ColorUtils.lstarFromInt(i2);
        this.hue = cam16FromInt.getHue();
        this.chroma = cam16FromInt.getChroma();
        this.tone = fLstarFromInt;
    }

    public float getChroma() {
        return this.chroma;
    }

    public float getHue() {
        return this.hue;
    }

    public float getTone() {
        return this.tone;
    }

    public void setChroma(float f2) {
        setInternalState(gamutMap(this.hue, f2, this.tone));
    }

    public void setHue(float f2) {
        setInternalState(gamutMap(MathUtils.sanitizeDegrees(f2), this.chroma, this.tone));
    }

    public void setTone(float f2) {
        setInternalState(gamutMap(this.hue, this.chroma, f2));
    }

    public int toInt() {
        return gamutMap(this.hue, this.chroma, this.tone);
    }
}
