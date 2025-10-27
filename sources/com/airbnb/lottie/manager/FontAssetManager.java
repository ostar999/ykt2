package com.airbnb.lottie.manager;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.Nullable;
import com.airbnb.lottie.FontAssetDelegate;
import com.airbnb.lottie.model.MutablePair;
import com.airbnb.lottie.utils.Logger;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class FontAssetManager {
    private final AssetManager assetManager;

    @Nullable
    private FontAssetDelegate delegate;
    private final MutablePair<String> tempPair = new MutablePair<>();
    private final Map<MutablePair<String>, Typeface> fontMap = new HashMap();
    private final Map<String, Typeface> fontFamilies = new HashMap();
    private String defaultFontFileExtension = ".ttf";

    public FontAssetManager(Drawable.Callback callback, @Nullable FontAssetDelegate fontAssetDelegate) {
        this.delegate = fontAssetDelegate;
        if (callback instanceof View) {
            this.assetManager = ((View) callback).getContext().getAssets();
        } else {
            Logger.warning("LottieDrawable must be inside of a view for images to work.");
            this.assetManager = null;
        }
    }

    private Typeface getFontFamily(String str) {
        String fontPath;
        Typeface typeface = this.fontFamilies.get(str);
        if (typeface != null) {
            return typeface;
        }
        FontAssetDelegate fontAssetDelegate = this.delegate;
        Typeface typefaceFetchFont = fontAssetDelegate != null ? fontAssetDelegate.fetchFont(str) : null;
        FontAssetDelegate fontAssetDelegate2 = this.delegate;
        if (fontAssetDelegate2 != null && typefaceFetchFont == null && (fontPath = fontAssetDelegate2.getFontPath(str)) != null) {
            typefaceFetchFont = Typeface.createFromAsset(this.assetManager, fontPath);
        }
        if (typefaceFetchFont == null) {
            typefaceFetchFont = Typeface.createFromAsset(this.assetManager, "fonts/" + str + this.defaultFontFileExtension);
        }
        this.fontFamilies.put(str, typefaceFetchFont);
        return typefaceFetchFont;
    }

    private Typeface typefaceForStyle(Typeface typeface, String str) {
        boolean zContains = str.contains("Italic");
        boolean zContains2 = str.contains("Bold");
        int i2 = (zContains && zContains2) ? 3 : zContains ? 2 : zContains2 ? 1 : 0;
        return typeface.getStyle() == i2 ? typeface : Typeface.create(typeface, i2);
    }

    public Typeface getTypeface(String str, String str2) {
        this.tempPair.set(str, str2);
        Typeface typeface = this.fontMap.get(this.tempPair);
        if (typeface != null) {
            return typeface;
        }
        Typeface typefaceTypefaceForStyle = typefaceForStyle(getFontFamily(str), str2);
        this.fontMap.put(this.tempPair, typefaceTypefaceForStyle);
        return typefaceTypefaceForStyle;
    }

    public void setDefaultFontFileExtension(String str) {
        this.defaultFontFileExtension = str;
    }

    public void setDelegate(@Nullable FontAssetDelegate fontAssetDelegate) {
        this.delegate = fontAssetDelegate;
    }
}
