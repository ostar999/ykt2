package com.caverock.androidsvg;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Log;
import com.psychiatrygarden.utils.MimeTypes;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes2.dex */
public class SimpleAssetResolver extends SVGExternalFileResolver {
    private static final String TAG = "SimpleAssetResolver";
    private static final Set<String> supportedFormats;
    private AssetManager assetManager;

    static {
        HashSet hashSet = new HashSet(8);
        supportedFormats = hashSet;
        hashSet.add("image/svg+xml");
        hashSet.add("image/jpeg");
        hashSet.add("image/png");
        hashSet.add("image/pjpeg");
        hashSet.add(MimeTypes.IMAGE_GIF);
        hashSet.add(MimeTypes.IMAGE_BMP);
        hashSet.add("image/x-windows-bmp");
        hashSet.add(MimeTypes.IMAGE_WEBP);
    }

    public SimpleAssetResolver(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    private String getAssetAsString(String str) throws Throwable {
        Throwable th;
        InputStream inputStreamOpen;
        try {
            inputStreamOpen = this.assetManager.open(str);
        } catch (IOException unused) {
            inputStreamOpen = null;
        } catch (Throwable th2) {
            th = th2;
            inputStreamOpen = null;
        }
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStreamOpen, Charset.forName("UTF-8"));
            char[] cArr = new char[4096];
            StringBuilder sb = new StringBuilder();
            for (int i2 = inputStreamReader.read(cArr); i2 > 0; i2 = inputStreamReader.read(cArr)) {
                sb.append(cArr, 0, i2);
            }
            String string = sb.toString();
            if (inputStreamOpen != null) {
                try {
                    inputStreamOpen.close();
                } catch (IOException unused2) {
                }
            }
            return string;
        } catch (IOException unused3) {
            if (inputStreamOpen != null) {
                try {
                    inputStreamOpen.close();
                } catch (IOException unused4) {
                }
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            if (inputStreamOpen != null) {
                try {
                    inputStreamOpen.close();
                } catch (IOException unused5) {
                }
            }
            throw th;
        }
    }

    @Override // com.caverock.androidsvg.SVGExternalFileResolver
    public boolean isFormatSupported(String str) {
        return supportedFormats.contains(str);
    }

    @Override // com.caverock.androidsvg.SVGExternalFileResolver
    public String resolveCSSStyleSheet(String str) {
        Log.i(TAG, "resolveCSSStyleSheet(" + str + ")");
        return getAssetAsString(str);
    }

    @Override // com.caverock.androidsvg.SVGExternalFileResolver
    public Typeface resolveFont(String str, int i2, String str2) {
        Log.i(TAG, "resolveFont(" + str + "," + i2 + "," + str2 + ")");
        try {
            try {
                return Typeface.createFromAsset(this.assetManager, str + ".ttf");
            } catch (RuntimeException unused) {
                return null;
            }
        } catch (RuntimeException unused2) {
            return Typeface.createFromAsset(this.assetManager, str + ".otf");
        }
    }

    @Override // com.caverock.androidsvg.SVGExternalFileResolver
    public Bitmap resolveImage(String str) {
        Log.i(TAG, "resolveImage(" + str + ")");
        try {
            return BitmapFactory.decodeStream(this.assetManager.open(str));
        } catch (IOException unused) {
            return null;
        }
    }
}
