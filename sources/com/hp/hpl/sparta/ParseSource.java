package com.hp.hpl.sparta;

/* loaded from: classes4.dex */
public interface ParseSource {
    public static final ParseLog DEFAULT_LOG = new DefaultLog();
    public static final int MAXLOOKAHEAD = 71;

    int getLineNumber();

    String getSystemId();

    String toString();
}
