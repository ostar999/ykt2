package com.hp.hpl.sparta;

/* loaded from: classes4.dex */
class CharCircBuffer {
    private final int[] buf_;
    private int next_ = 0;
    private int total_ = 0;
    private boolean enabled_ = true;

    public CharCircBuffer(int i2) {
        this.buf_ = new int[i2];
    }

    private void addRaw(int i2) {
        if (this.enabled_) {
            int[] iArr = this.buf_;
            int i3 = this.next_;
            iArr[i3] = i2;
            this.next_ = (i3 + 1) % iArr.length;
            this.total_++;
        }
    }

    public void addChar(char c3) {
        addRaw(c3);
    }

    public void addInt(int i2) {
        addRaw(i2 + 65536);
    }

    public void addString(String str) {
        for (char c3 : str.toCharArray()) {
            addChar(c3);
        }
    }

    public void disable() {
        this.enabled_ = false;
    }

    public void enable() {
        this.enabled_ = true;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer((this.buf_.length * 11) / 10);
        int i2 = this.total_;
        int[] iArr = this.buf_;
        int length = i2 < iArr.length ? iArr.length - i2 : 0;
        while (true) {
            int[] iArr2 = this.buf_;
            if (length >= iArr2.length) {
                return stringBuffer.toString();
            }
            int i3 = iArr2[(this.next_ + length) % iArr2.length];
            if (i3 < 65536) {
                stringBuffer.append((char) i3);
            } else {
                stringBuffer.append(Integer.toString(i3 - 65536));
            }
            length++;
        }
    }
}
