package org.apache.commons.compress.archivers.dump;

/* loaded from: classes9.dex */
class Dirent {
    private final int ino;
    private final String name;
    private final int parentIno;
    private final int type;

    public Dirent(int i2, int i3, int i4, String str) {
        this.ino = i2;
        this.parentIno = i3;
        this.type = i4;
        this.name = str;
    }

    public int getIno() {
        return this.ino;
    }

    public String getName() {
        return this.name;
    }

    public int getParentIno() {
        return this.parentIno;
    }

    public int getType() {
        return this.type;
    }

    public String toString() {
        return String.format("[%d]: %s", Integer.valueOf(this.ino), this.name);
    }
}
