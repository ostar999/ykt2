package com.github.mikephil.charting.formatter;

import java.text.DecimalFormat;

/* loaded from: classes3.dex */
public class LargeValueFormatter extends ValueFormatter {
    private DecimalFormat mFormat;
    private int mMaxLength;
    private String[] mSuffix;
    private String mText;

    public LargeValueFormatter() {
        this.mSuffix = new String[]{"", "k", "m", "b", "t"};
        this.mMaxLength = 5;
        this.mText = "";
        this.mFormat = new DecimalFormat("###E00");
    }

    private String makePretty(double d3) {
        String str = this.mFormat.format(d3);
        int numericValue = Character.getNumericValue(str.charAt(str.length() - 1));
        String strReplaceAll = str.replaceAll("E[0-9][0-9]", this.mSuffix[Integer.valueOf(Character.getNumericValue(str.charAt(str.length() - 2)) + "" + numericValue).intValue() / 3]);
        while (true) {
            if (strReplaceAll.length() <= this.mMaxLength && !strReplaceAll.matches("[0-9]+\\.[a-z]")) {
                return strReplaceAll;
            }
            strReplaceAll = strReplaceAll.substring(0, strReplaceAll.length() - 2) + strReplaceAll.substring(strReplaceAll.length() - 1);
        }
    }

    public int getDecimalDigits() {
        return 0;
    }

    @Override // com.github.mikephil.charting.formatter.ValueFormatter
    public String getFormattedValue(float f2) {
        return makePretty(f2) + this.mText;
    }

    public void setAppendix(String str) {
        this.mText = str;
    }

    public void setMaxLength(int i2) {
        this.mMaxLength = i2;
    }

    public void setSuffix(String[] strArr) {
        this.mSuffix = strArr;
    }

    public LargeValueFormatter(String str) {
        this();
        this.mText = str;
    }
}
