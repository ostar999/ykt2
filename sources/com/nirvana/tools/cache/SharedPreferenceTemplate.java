package com.nirvana.tools.cache;

/* loaded from: classes4.dex */
public class SharedPreferenceTemplate extends RepositoryTemplate {
    private String fileName;
    private String keyName;

    public SharedPreferenceTemplate(int i2, boolean z2, String str, String str2) {
        super(i2, z2);
        this.fileName = str;
        this.keyName = str2;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getKeyName() {
        return this.keyName;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public void setKeyName(String str) {
        this.keyName = str;
    }
}
