package com.psychiatrygarden.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class StringUtil {
    public static String eliminatePoint(String s2) {
        return TextUtils.isEmpty(s2) ? "" : s2.replaceAll(" ", "").replaceAll("[\\p{P}\\p{Punct}]", "");
    }

    public static boolean equalsIgnorePoint(String answer, String input) {
        if (TextUtils.isEmpty(input) || TextUtils.isEmpty(answer)) {
            return false;
        }
        if (answer.equals(input)) {
            return true;
        }
        return eliminatePoint(answer).equalsIgnoreCase(eliminatePoint(input));
    }

    public static float getBaseline(Paint p2) {
        Paint.FontMetrics fontMetrics = p2.getFontMetrics();
        float f2 = fontMetrics.descent;
        return ((f2 - fontMetrics.ascent) / 2.0f) - f2;
    }

    public static String getJson(String fileName, Context context) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return sb.toString();
    }

    public static boolean hasWord(HashSet<String> goodSentenceWords, String word) {
        if (goodSentenceWords.contains(word)) {
            return true;
        }
        Iterator<String> it = goodSentenceWords.iterator();
        while (it.hasNext()) {
            it.next();
        }
        return false;
    }

    public static boolean isLetter(char c3) {
        return (c3 >= '0' && c3 <= '9') || (c3 >= 'a' && c3 <= 'z') || ((c3 >= 'A' && c3 <= 'Z') || c3 == '\'' || c3 == '-');
    }

    public static boolean isMobilephoneNumber(String phoneNumber, String countryCode) {
        String strReplace = phoneNumber.replace(" ", "");
        return countryCode.equals("+86") ? Pattern.compile("^[1][0,1,2,3,4,5,7,6,8,9]\\d{9}$").matcher(strReplace).matches() : strReplace.length() >= 6 && strReplace.length() <= 24;
    }

    public static String millis2Minute(long time) {
        String str = (time / 60000) + "";
        String str2 = ((time % 60000) / 1000) + "";
        if (str.length() < 2) {
            str = 0 + str;
        }
        if (str2.length() < 2) {
            str2 = 0 + str2;
        }
        return str + ":" + str2;
    }

    public static String replaceCharacter(String content) {
        return TextUtils.isEmpty(content) ? content : content.replaceAll("’", "'").replaceAll("（", "(").replaceAll("）", ")").replaceAll("–", "-").replaceAll("—", "-").replaceAll("\\( ", "(").replaceAll(" \\)", ")").replaceAll(" '", "'").replaceAll("' ", "'");
    }

    public static SpannableStringBuilder setNumColor(String str, int color, int garyColor, float proportion) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        int iIndexOf = str.indexOf("/");
        int i2 = 0;
        if (iIndexOf != -1) {
            while (i2 < str.length()) {
                char cCharAt = str.charAt(i2);
                if (cCharAt >= '0' && cCharAt <= '9') {
                    int i3 = i2 + 1;
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(i2 > iIndexOf ? garyColor : color), i2, i3, 33);
                    spannableStringBuilder.setSpan(new RelativeSizeSpan(1.2f), i2, i3, 33);
                }
                i2++;
            }
        } else {
            while (i2 < str.length()) {
                char cCharAt2 = str.charAt(i2);
                if (cCharAt2 >= '0' && cCharAt2 <= '9') {
                    int i4 = i2 + 1;
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(color), i2, i4, 33);
                    spannableStringBuilder.setSpan(new RelativeSizeSpan(1.2f), i2, i4, 33);
                }
                i2++;
            }
        }
        return spannableStringBuilder;
    }

    public static void setTargetTextBold(TextView textView, String source, List<String> targetList) {
        if (TextUtils.isEmpty(source)) {
            return;
        }
        SpannableString spannableString = new SpannableString(source);
        for (String str : targetList) {
            int iIndexOf = source.indexOf(str);
            if (iIndexOf != -1) {
                spannableString.setSpan(new StyleSpan(1), iIndexOf, str.length() + iIndexOf, 33);
            }
        }
        textView.setText(spannableString);
    }

    public static void setTargetTextColor(TextView textView, String source, String target) {
        if (TextUtils.isEmpty(source)) {
            return;
        }
        int iIndexOf = source.indexOf(target);
        SpannableString spannableString = new SpannableString(source);
        if (iIndexOf != -1) {
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#006666")), iIndexOf, target.length() + iIndexOf, 18);
        }
        textView.setText(spannableString);
    }

    public static void setTargetTextColor(TextView textView, String source, List<String> targetList) {
        if (TextUtils.isEmpty(source)) {
            return;
        }
        SpannableString spannableString = new SpannableString(source);
        for (String str : targetList) {
            int iIndexOf = source.indexOf(str);
            if (iIndexOf != -1) {
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#006666")), iIndexOf, str.length() + iIndexOf, 18);
            }
        }
        textView.setText(spannableString);
    }

    public static void setTargetTextColor(TextView textView, String source, List<String> targetList, int currentColor) {
        if (TextUtils.isEmpty(source)) {
            return;
        }
        SpannableString spannableString = new SpannableString(source);
        Iterator<String> it = targetList.iterator();
        while (it.hasNext()) {
            int iIndexOf = source.indexOf(it.next());
            if (iIndexOf != -1) {
                spannableString.setSpan(new ForegroundColorSpan(currentColor), iIndexOf, iIndexOf + 1, 33);
            }
        }
        textView.setText(spannableString);
    }
}
