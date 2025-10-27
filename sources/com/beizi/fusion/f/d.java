package com.beizi.fusion.f;

import java.util.Stack;

/* loaded from: classes2.dex */
public class d {
    public static int a(String str) {
        char cCharAt;
        Stack stack = new Stack();
        Stack stack2 = new Stack();
        int length = str.length();
        int i2 = 0;
        while (i2 < length) {
            char cCharAt2 = str.charAt(i2);
            if (cCharAt2 >= '0' && cCharAt2 <= '9') {
                String str2 = "" + cCharAt2;
                while (true) {
                    int i3 = i2 + 1;
                    if (i3 >= length || (cCharAt = str.charAt(i3)) < '0' || cCharAt > '9') {
                        break;
                    }
                    str2 = str2 + cCharAt;
                    i2 = i3;
                }
                stack.push(str2);
            } else if (cCharAt2 == '(') {
                stack2.push("" + cCharAt2);
            } else if (cCharAt2 == ')') {
                while (!((String) stack2.peek()).equals("(")) {
                    stack.push("" + a(Integer.parseInt((String) stack.pop()), (String) stack2.pop(), Integer.parseInt((String) stack.pop())));
                }
                stack2.pop();
            } else {
                if (!a(cCharAt2)) {
                    throw new RuntimeException("无法识别" + cCharAt2);
                }
                if (stack2.empty()) {
                    stack2.push("" + cCharAt2);
                } else {
                    String str3 = "" + cCharAt2;
                    if (b(str3) > b((String) stack2.peek())) {
                        stack2.push(str3);
                    } else {
                        while (!stack2.empty() && b(str3) <= b((String) stack2.peek())) {
                            stack.push("" + a(Integer.parseInt((String) stack.pop()), (String) stack2.pop(), Integer.parseInt((String) stack.pop())));
                        }
                        stack2.push(str3);
                    }
                }
            }
            i2++;
        }
        while (!stack2.empty()) {
            stack.push("" + a(Integer.parseInt((String) stack.pop()), (String) stack2.pop(), Integer.parseInt((String) stack.pop())));
        }
        return Integer.parseInt((String) stack.pop());
    }

    private static boolean a(char c3) {
        return c3 == '+' || c3 == '-' || c3 == '*' || c3 == '/' || c3 == '%';
    }

    private static float b(String str) {
        str.hashCode();
        switch (str) {
            case "%":
            case "*":
            case "/":
                return 2.0f;
            case "(":
                return 0.0f;
            case "+":
            case "-":
                return 1.0f;
            default:
                return -1.0f;
        }
    }

    private static int a(int i2, String str, int i3) {
        str.hashCode();
        switch (str) {
            case "%":
                return i2 % i3;
            case "*":
                return i2 * i3;
            case "+":
                return i2 + i3;
            case "-":
                return i2 - i3;
            case "/":
                return i2 / i3;
            default:
                return 0;
        }
    }
}
