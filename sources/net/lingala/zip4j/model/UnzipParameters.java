package net.lingala.zip4j.model;

/* loaded from: classes9.dex */
public class UnzipParameters {
    private boolean ignoreAllFileAttributes;
    private boolean ignoreArchiveFileAttribute;
    private boolean ignoreDateTimeAttributes;
    private boolean ignoreHiddenFileAttribute;
    private boolean ignoreReadOnlyFileAttribute;
    private boolean ignoreSystemFileAttribute;

    public boolean isIgnoreAllFileAttributes() {
        return this.ignoreAllFileAttributes;
    }

    public boolean isIgnoreArchiveFileAttribute() {
        return this.ignoreArchiveFileAttribute;
    }

    public boolean isIgnoreDateTimeAttributes() {
        return this.ignoreDateTimeAttributes;
    }

    public boolean isIgnoreHiddenFileAttribute() {
        return this.ignoreHiddenFileAttribute;
    }

    public boolean isIgnoreReadOnlyFileAttribute() {
        return this.ignoreReadOnlyFileAttribute;
    }

    public boolean isIgnoreSystemFileAttribute() {
        return this.ignoreSystemFileAttribute;
    }

    public void setIgnoreAllFileAttributes(boolean z2) {
        this.ignoreAllFileAttributes = z2;
    }

    public void setIgnoreArchiveFileAttribute(boolean z2) {
        this.ignoreArchiveFileAttribute = z2;
    }

    public void setIgnoreDateTimeAttributes(boolean z2) {
        this.ignoreDateTimeAttributes = z2;
    }

    public void setIgnoreHiddenFileAttribute(boolean z2) {
        this.ignoreHiddenFileAttribute = z2;
    }

    public void setIgnoreReadOnlyFileAttribute(boolean z2) {
        this.ignoreReadOnlyFileAttribute = z2;
    }

    public void setIgnoreSystemFileAttribute(boolean z2) {
        this.ignoreSystemFileAttribute = z2;
    }
}
