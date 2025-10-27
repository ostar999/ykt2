package com.psychiatrygarden.bean;

import java.math.BigDecimal;
import java.util.Stack;
import org.slf4j.Marker;

/* loaded from: classes5.dex */
public class Formulautil {
    private Stack<BigDecimal> numberStack;
    private int scale;
    private Stack<Character> symbolStack;

    public Formulautil(int scale) {
        this.numberStack = null;
        this.symbolStack = null;
        this.scale = scale;
    }

    private boolean comparePri(char symbol) {
        char cCharValue;
        if (this.symbolStack.empty() || (cCharValue = this.symbolStack.peek().charValue()) == '(') {
            return true;
        }
        if (symbol == '-') {
            return false;
        }
        if (symbol == '/') {
            return cCharValue == '+' || cCharValue == '-';
        }
        if (symbol != '=') {
            switch (symbol) {
                case '*':
                    if (cCharValue == '+' || cCharValue == '-') {
                    }
                    break;
            }
            return true;
        }
        return false;
    }

    private boolean isNumber(char num) {
        return (num >= '0' && num <= '9') || num == '.';
    }

    private boolean isStandard(String numStr) {
        if (numStr == null || numStr.isEmpty()) {
            return false;
        }
        Stack stack = new Stack();
        boolean z2 = false;
        for (int i2 = 0; i2 < numStr.length(); i2++) {
            char cCharAt = numStr.charAt(i2);
            if (!isNumber(cCharAt)) {
                if (!"(".equals(cCharAt + "")) {
                    if (!")".equals(cCharAt + "")) {
                        if (!Marker.ANY_NON_NULL_MARKER.equals(cCharAt + "")) {
                            if (!"-".equals(cCharAt + "")) {
                                if (!"*".equals(cCharAt + "")) {
                                    if (!"/".equals(cCharAt + "")) {
                                        if (!"=".equals(cCharAt + "")) {
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if ("(".equals(cCharAt + "")) {
                stack.push(Character.valueOf(cCharAt));
            }
            if (")".equals(cCharAt + "")) {
                if (!stack.isEmpty()) {
                    if (!"(".equals(((Character) stack.pop()).charValue() + "")) {
                    }
                }
                return false;
            }
            if ("=".equals(cCharAt + "")) {
                if (z2) {
                    return false;
                }
                z2 = true;
            }
        }
        if (!stack.isEmpty()) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(numStr.charAt(numStr.length() - 1));
        sb.append("");
        return "=".equals(sb.toString());
    }

    public BigDecimal caculate(String numStr) {
        if (numStr.length() > 1) {
            if (!"=".equals(numStr.charAt(numStr.length() - 1) + "")) {
                numStr = numStr + "=";
            }
        }
        if (!isStandard(numStr)) {
            return null;
        }
        if (this.numberStack == null) {
            this.numberStack = new Stack<>();
        }
        this.numberStack.clear();
        if (this.symbolStack == null) {
            this.symbolStack = new Stack<>();
        }
        this.symbolStack.clear();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < numStr.length(); i2++) {
            char cCharAt = numStr.charAt(i2);
            if (isNumber(cCharAt)) {
                stringBuffer.append(cCharAt);
            } else {
                String string = stringBuffer.toString();
                if (!string.isEmpty()) {
                    this.numberStack.push(new BigDecimal(string));
                    stringBuffer = new StringBuffer();
                }
                while (!comparePri(cCharAt) && !this.symbolStack.empty()) {
                    BigDecimal bigDecimalPop = this.numberStack.pop();
                    BigDecimal bigDecimalPop2 = this.numberStack.pop();
                    char cCharValue = this.symbolStack.pop().charValue();
                    if (cCharValue == '*') {
                        this.numberStack.push(bigDecimalPop2.multiply(bigDecimalPop));
                    } else if (cCharValue == '+') {
                        this.numberStack.push(bigDecimalPop2.add(bigDecimalPop));
                    } else if (cCharValue == '-') {
                        this.numberStack.push(bigDecimalPop2.subtract(bigDecimalPop));
                    } else if (cCharValue == '/') {
                        try {
                            this.numberStack.push(bigDecimalPop2.divide(bigDecimalPop));
                        } catch (ArithmeticException unused) {
                            this.numberStack.push(bigDecimalPop2.divide(bigDecimalPop, this.scale, 6));
                        }
                    }
                }
                if (cCharAt != '=') {
                    this.symbolStack.push(new Character(cCharAt));
                    if (cCharAt == ')') {
                        this.symbolStack.pop();
                        this.symbolStack.pop();
                    }
                }
            }
        }
        return this.numberStack.pop();
    }

    public Formulautil() {
        this(32);
    }
}
