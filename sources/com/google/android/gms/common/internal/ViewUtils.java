package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
/* loaded from: classes3.dex */
public class ViewUtils {
    private ViewUtils() {
    }

    @KeepForSdk
    public static String getXmlAttributeString(String str, String str2, Context context, AttributeSet attributeSet, boolean z2, boolean z3, String str3) throws Resources.NotFoundException {
        String attributeValue = attributeSet == null ? null : attributeSet.getAttributeValue(str, str2);
        if (attributeValue != null && attributeValue.startsWith("@string/") && z2) {
            String strSubstring = attributeValue.substring(8);
            String packageName = context.getPackageName();
            TypedValue typedValue = new TypedValue();
            try {
                Resources resources = context.getResources();
                StringBuilder sb = new StringBuilder(String.valueOf(packageName).length() + 8 + String.valueOf(strSubstring).length());
                sb.append(packageName);
                sb.append(":string/");
                sb.append(strSubstring);
                resources.getValue(sb.toString(), typedValue, true);
            } catch (Resources.NotFoundException unused) {
                StringBuilder sb2 = new StringBuilder(String.valueOf(str2).length() + 30 + attributeValue.length());
                sb2.append("Could not find resource for ");
                sb2.append(str2);
                sb2.append(": ");
                sb2.append(attributeValue);
                Log.w(str3, sb2.toString());
            }
            CharSequence charSequence = typedValue.string;
            if (charSequence != null) {
                attributeValue = charSequence.toString();
            } else {
                String strValueOf = String.valueOf(typedValue);
                StringBuilder sb3 = new StringBuilder(String.valueOf(str2).length() + 28 + strValueOf.length());
                sb3.append("Resource ");
                sb3.append(str2);
                sb3.append(" was not a string: ");
                sb3.append(strValueOf);
                Log.w(str3, sb3.toString());
            }
        }
        if (z3 && attributeValue == null) {
            StringBuilder sb4 = new StringBuilder(String.valueOf(str2).length() + 33);
            sb4.append("Required XML attribute \"");
            sb4.append(str2);
            sb4.append("\" missing");
            Log.w(str3, sb4.toString());
        }
        return attributeValue;
    }
}
