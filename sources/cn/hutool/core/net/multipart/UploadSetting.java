package cn.hutool.core.net.multipart;

/* loaded from: classes.dex */
public class UploadSetting {
    protected String[] fileExts;
    protected String tmpUploadPath;
    protected long maxFileSize = -1;
    protected int memoryThreshold = 8192;
    protected boolean isAllowFileExts = true;

    public String[] getFileExts() {
        return this.fileExts;
    }

    public long getMaxFileSize() {
        return this.maxFileSize;
    }

    public int getMemoryThreshold() {
        return this.memoryThreshold;
    }

    public String getTmpUploadPath() {
        return this.tmpUploadPath;
    }

    public boolean isAllowFileExts() {
        return this.isAllowFileExts;
    }

    public void setAllowFileExts(boolean z2) {
        this.isAllowFileExts = z2;
    }

    public void setFileExts(String[] strArr) {
        this.fileExts = strArr;
    }

    public void setMaxFileSize(long j2) {
        this.maxFileSize = j2;
    }

    public void setMemoryThreshold(int i2) {
        this.memoryThreshold = i2;
    }

    public void setTmpUploadPath(String str) {
        this.tmpUploadPath = str;
    }
}
