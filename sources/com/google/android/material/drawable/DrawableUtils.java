package com.google.android.material.drawable;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Xml;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.XmlRes;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public final class DrawableUtils {
    private DrawableUtils() {
    }

    @NonNull
    public static AttributeSet parseDrawableXml(@NonNull Context context, @XmlRes int i2, @NonNull CharSequence charSequence) throws XmlPullParserException, Resources.NotFoundException, IOException {
        int next;
        try {
            XmlResourceParser xml = context.getResources().getXml(i2);
            do {
                next = xml.next();
                if (next == 2) {
                    break;
                }
            } while (next != 1);
            if (next != 2) {
                throw new XmlPullParserException("No start tag found");
            }
            if (TextUtils.equals(xml.getName(), charSequence)) {
                return Xml.asAttributeSet(xml);
            }
            throw new XmlPullParserException("Must have a <" + ((Object) charSequence) + "> start tag");
        } catch (IOException | XmlPullParserException e2) {
            Resources.NotFoundException notFoundException = new Resources.NotFoundException("Can't load badge resource ID #0x" + Integer.toHexString(i2));
            notFoundException.initCause(e2);
            throw notFoundException;
        }
    }

    @TargetApi(21)
    public static void setRippleDrawableRadius(@Nullable RippleDrawable rippleDrawable, int i2) {
        rippleDrawable.setRadius(i2);
    }

    @Nullable
    public static PorterDuffColorFilter updateTintFilter(@NonNull Drawable drawable, @Nullable ColorStateList colorStateList, @Nullable PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(drawable.getState(), 0), mode);
    }
}
