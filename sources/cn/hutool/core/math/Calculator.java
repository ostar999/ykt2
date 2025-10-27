package cn.hutool.core.math;

import cn.hutool.core.text.CharPool;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.NumberUtil;
import com.xiaomi.mipush.sdk.Constants;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Stack;

/* loaded from: classes.dex */
public class Calculator {
    private final Stack<String> postfixStack = new Stack<>();
    private final int[] operatPriority = {0, 3, 2, 1, -1, 1, 0, 2};

    private boolean compare(char c3, char c4) {
        if (c3 == '%') {
            c3 = '/';
        }
        if (c4 == '%') {
            c4 = '/';
        }
        int[] iArr = this.operatPriority;
        return iArr[c4 + 65496] >= iArr[c3 + 65496];
    }

    public static double conversion(String str) {
        return new Calculator().calculate(str);
    }

    private boolean isOperator(char c3) {
        return c3 == '+' || c3 == '-' || c3 == '*' || c3 == '/' || c3 == '(' || c3 == ')' || c3 == '%';
    }

    private void prepare(String str) {
        Stack stack = new Stack();
        stack.push(',');
        char[] charArray = str.toCharArray();
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < charArray.length; i4++) {
            char c3 = charArray[i4];
            if (isOperator(c3)) {
                if (i2 > 0) {
                    this.postfixStack.push(new String(charArray, i3, i2));
                }
                if (c3 == ')') {
                    while (((Character) stack.peek()).charValue() != '(') {
                        this.postfixStack.push(String.valueOf(stack.pop()));
                    }
                    stack.pop();
                } else {
                    for (char cCharValue = ((Character) stack.peek()).charValue(); c3 != '(' && cCharValue != ',' && compare(c3, cCharValue); cCharValue = ((Character) stack.peek()).charValue()) {
                        this.postfixStack.push(String.valueOf(stack.pop()));
                    }
                    stack.push(Character.valueOf(c3));
                }
                i3 = i4 + 1;
                i2 = 0;
            } else {
                i2++;
            }
        }
        if (i2 > 1 || (i2 == 1 && !isOperator(charArray[i3]))) {
            this.postfixStack.push(new String(charArray, i3, i2));
        }
        while (((Character) stack.peek()).charValue() != ',') {
            this.postfixStack.push(String.valueOf(stack.pop()));
        }
    }

    private static String transform(String str) {
        char[] charArray = CharSequenceUtil.removeSuffix(CharSequenceUtil.cleanBlank(str), "=").toCharArray();
        for (int i2 = 0; i2 < charArray.length; i2++) {
            if (charArray[i2] == '-') {
                if (i2 == 0) {
                    charArray[i2] = '~';
                } else {
                    char c3 = charArray[i2 - 1];
                    if (c3 == '+' || c3 == '-' || c3 == '*' || c3 == '/' || c3 == '(' || c3 == 'E' || c3 == 'e') {
                        charArray[i2] = '~';
                    }
                }
            }
        }
        if (charArray[0] != '~' || charArray.length <= 1 || charArray[1] != '(') {
            return new String(charArray);
        }
        charArray[0] = CharPool.DASHED;
        return "0" + new String(charArray);
    }

    public double calculate(String str) {
        prepare(transform(str));
        Stack stack = new Stack();
        Collections.reverse(this.postfixStack);
        while (!this.postfixStack.isEmpty()) {
            String strPop = this.postfixStack.pop();
            if (isOperator(strPop.charAt(0))) {
                stack.push(calculate(((String) stack.pop()).replace(Constants.WAVE_SEPARATOR, "-"), ((String) stack.pop()).replace(Constants.WAVE_SEPARATOR, "-"), strPop.charAt(0)).toString());
            } else {
                stack.push(strPop.replace(Constants.WAVE_SEPARATOR, "-"));
            }
        }
        return NumberUtil.mul((String[]) stack.toArray(new String[0])).doubleValue();
    }

    private BigDecimal calculate(String str, String str2, char c3) {
        if (c3 == '%') {
            return NumberUtil.toBigDecimal(str).remainder(NumberUtil.toBigDecimal(str2));
        }
        if (c3 == '-') {
            return NumberUtil.sub(str, str2);
        }
        if (c3 == '/') {
            return NumberUtil.div(str, str2);
        }
        if (c3 == '*') {
            return NumberUtil.mul(str, str2);
        }
        if (c3 == '+') {
            return NumberUtil.add(str, str2);
        }
        throw new IllegalStateException("Unexpected value: " + c3);
    }
}
