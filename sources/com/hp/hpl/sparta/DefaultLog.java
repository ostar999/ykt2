package com.hp.hpl.sparta;

import java.io.PrintStream;

/* loaded from: classes4.dex */
class DefaultLog implements ParseLog {
    @Override // com.hp.hpl.sparta.ParseLog
    public void error(String str, String str2, int i2) {
        PrintStream printStream = System.err;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str2);
        stringBuffer.append("(");
        stringBuffer.append(i2);
        stringBuffer.append("): ");
        stringBuffer.append(str);
        stringBuffer.append(" (ERROR)");
        printStream.println(stringBuffer.toString());
    }

    @Override // com.hp.hpl.sparta.ParseLog
    public void note(String str, String str2, int i2) {
        PrintStream printStream = System.out;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str2);
        stringBuffer.append("(");
        stringBuffer.append(i2);
        stringBuffer.append("): ");
        stringBuffer.append(str);
        stringBuffer.append(" (NOTE)");
        printStream.println(stringBuffer.toString());
    }

    @Override // com.hp.hpl.sparta.ParseLog
    public void warning(String str, String str2, int i2) {
        PrintStream printStream = System.out;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str2);
        stringBuffer.append("(");
        stringBuffer.append(i2);
        stringBuffer.append("): ");
        stringBuffer.append(str);
        stringBuffer.append(" (WARNING)");
        printStream.println(stringBuffer.toString());
    }
}
