package com.bumptech.glide.repackaged.com.squareup.javapoet;

import java.io.IOException;

/* loaded from: classes2.dex */
final class LineWrapper {
    private boolean closed;
    private final int columnLimit;
    private final String indent;
    private final Appendable out;
    private final StringBuilder buffer = new StringBuilder();
    private int column = 0;
    private int indentLevel = -1;

    public LineWrapper(Appendable appendable, String str, int i2) {
        Util.checkNotNull(appendable, "out == null", new Object[0]);
        this.out = appendable;
        this.indent = str;
        this.columnLimit = i2;
    }

    private void flush(boolean z2) throws IOException {
        int i2;
        if (z2) {
            this.out.append('\n');
            int i3 = 0;
            while (true) {
                i2 = this.indentLevel;
                if (i3 >= i2) {
                    break;
                }
                this.out.append(this.indent);
                i3++;
            }
            int length = i2 * this.indent.length();
            this.column = length;
            this.column = length + this.buffer.length();
        } else {
            this.out.append(' ');
        }
        this.out.append(this.buffer);
        StringBuilder sb = this.buffer;
        sb.delete(0, sb.length());
        this.indentLevel = -1;
    }

    public void append(String str) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        if (this.indentLevel != -1) {
            int iIndexOf = str.indexOf(10);
            if (iIndexOf == -1 && this.column + str.length() <= this.columnLimit) {
                this.buffer.append(str);
                this.column += str.length();
                return;
            }
            flush(iIndexOf == -1 || this.column + iIndexOf > this.columnLimit);
        }
        this.out.append(str);
        int iLastIndexOf = str.lastIndexOf(10);
        this.column = iLastIndexOf != -1 ? (str.length() - iLastIndexOf) - 1 : str.length() + this.column;
    }

    public void wrappingSpace(int i2) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        if (this.indentLevel != -1) {
            flush(false);
        }
        this.column++;
        this.indentLevel = i2;
    }
}
