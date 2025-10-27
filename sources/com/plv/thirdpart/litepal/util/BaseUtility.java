package com.plv.thirdpart.litepal.util;

import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.plv.thirdpart.litepal.LitePalApplication;
import com.plv.thirdpart.litepal.exceptions.LitePalSupportException;
import com.plv.thirdpart.litepal.parser.LitePalAttr;
import com.plv.thirdpart.litepal.util.Const;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

/* loaded from: classes5.dex */
public class BaseUtility {
    private BaseUtility() {
    }

    public static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            if (str == null) {
                return null;
            }
            return "";
        }
        return str.substring(0, 1).toUpperCase(Locale.US) + str.substring(1);
    }

    public static String changeCase(String str) {
        if (str == null) {
            return null;
        }
        String cases = LitePalAttr.getInstance().getCases();
        return Const.Config.CASES_KEEP.equals(cases) ? str : Const.Config.CASES_UPPER.equals(cases) ? str.toUpperCase(Locale.US) : str.toLowerCase(Locale.US);
    }

    public static void checkConditionsCorrect(String... strArr) {
        int length;
        if (strArr != null && (length = strArr.length) > 0 && length != count(strArr[0], "?") + 1) {
            throw new LitePalSupportException(LitePalSupportException.UPDATE_CONDITIONS_EXCEPTION);
        }
    }

    public static boolean containsIgnoreCases(Collection<String> collection, String str) {
        if (collection == null) {
            return false;
        }
        if (str == null) {
            return collection.contains(null);
        }
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            if (str.equalsIgnoreCase(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static int count(String str, String str2) {
        int i2 = 0;
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            int iIndexOf = str.indexOf(str2);
            while (iIndexOf != -1) {
                i2++;
                str = str.substring(iIndexOf + str2.length());
                iIndexOf = str.indexOf(str2);
            }
        }
        return i2;
    }

    public static boolean isClassAndMethodExist(String str, String str2) throws SecurityException {
        try {
            for (Method method : Class.forName(str).getMethods()) {
                if (str2.equals(method.getName())) {
                    return true;
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return false;
    }

    public static boolean isFieldTypeSupported(String str) {
        return TypedValues.Custom.S_BOOLEAN.equals(str) || "java.lang.Boolean".equals(str) || TypedValues.Custom.S_FLOAT.equals(str) || "java.lang.Float".equals(str) || "double".equals(str) || "java.lang.Double".equals(str) || "int".equals(str) || "java.lang.Integer".equals(str) || "long".equals(str) || "java.lang.Long".equals(str) || "short".equals(str) || "java.lang.Short".equals(str) || "char".equals(str) || "java.lang.Character".equals(str) || "java.lang.String".equals(str) || "java.util.Date".equals(str);
    }

    public static boolean isGenericTypeSupported(String str) {
        return "java.lang.String".equals(str) || "java.lang.Integer".equals(str) || "java.lang.Float".equals(str) || "java.lang.Double".equals(str) || "java.lang.Long".equals(str) || "java.lang.Short".equals(str) || "java.lang.Boolean".equals(str) || "java.lang.Character".equals(str);
    }

    public static boolean isLitePalXMLExists() throws IOException {
        try {
            String[] list = LitePalApplication.getContext().getAssets().list("");
            if (list != null && list.length > 0) {
                for (String str : list) {
                    if (Const.Config.CONFIGURATION_FILE_NAME.equalsIgnoreCase(str)) {
                        return true;
                    }
                }
            }
        } catch (IOException unused) {
        }
        return false;
    }
}
