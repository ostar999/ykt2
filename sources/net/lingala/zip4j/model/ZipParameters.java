package net.lingala.zip4j.model;

import cn.hutool.core.text.StrPool;
import java.util.TimeZone;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.Zip4jUtil;

/* loaded from: classes9.dex */
public class ZipParameters implements Cloneable {
    private int compressionLevel;
    private String defaultFolderPath;
    private String fileNameInZip;
    private boolean isSourceExternalStream;
    private char[] password;
    private String rootFolderInZip;
    private int sourceFileCRC;
    private int compressionMethod = 8;
    private boolean encryptFiles = false;
    private boolean readHiddenFiles = true;
    private int encryptionMethod = -1;
    private int aesKeyStrength = -1;
    private boolean includeRootFolder = true;
    private TimeZone timeZone = TimeZone.getDefault();

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getAesKeyStrength() {
        return this.aesKeyStrength;
    }

    public int getCompressionLevel() {
        return this.compressionLevel;
    }

    public int getCompressionMethod() {
        return this.compressionMethod;
    }

    public String getDefaultFolderPath() {
        return this.defaultFolderPath;
    }

    public int getEncryptionMethod() {
        return this.encryptionMethod;
    }

    public String getFileNameInZip() {
        return this.fileNameInZip;
    }

    public char[] getPassword() {
        return this.password;
    }

    public String getRootFolderInZip() {
        return this.rootFolderInZip;
    }

    public int getSourceFileCRC() {
        return this.sourceFileCRC;
    }

    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    public boolean isEncryptFiles() {
        return this.encryptFiles;
    }

    public boolean isIncludeRootFolder() {
        return this.includeRootFolder;
    }

    public boolean isReadHiddenFiles() {
        return this.readHiddenFiles;
    }

    public boolean isSourceExternalStream() {
        return this.isSourceExternalStream;
    }

    public void setAesKeyStrength(int i2) {
        this.aesKeyStrength = i2;
    }

    public void setCompressionLevel(int i2) {
        this.compressionLevel = i2;
    }

    public void setCompressionMethod(int i2) {
        this.compressionMethod = i2;
    }

    public void setDefaultFolderPath(String str) {
        this.defaultFolderPath = str;
    }

    public void setEncryptFiles(boolean z2) {
        this.encryptFiles = z2;
    }

    public void setEncryptionMethod(int i2) {
        this.encryptionMethod = i2;
    }

    public void setFileNameInZip(String str) {
        this.fileNameInZip = str;
    }

    public void setIncludeRootFolder(boolean z2) {
        this.includeRootFolder = z2;
    }

    public void setPassword(String str) {
        if (str == null) {
            return;
        }
        setPassword(str.toCharArray());
    }

    public void setReadHiddenFiles(boolean z2) {
        this.readHiddenFiles = z2;
    }

    public void setRootFolderInZip(String str) {
        if (Zip4jUtil.isStringNotNullAndNotEmpty(str)) {
            if (!str.endsWith(StrPool.BACKSLASH) && !str.endsWith("/")) {
                str = str + InternalZipConstants.FILE_SEPARATOR;
            }
            str = str.replaceAll("\\\\", "/");
        }
        this.rootFolderInZip = str;
    }

    public void setSourceExternalStream(boolean z2) {
        this.isSourceExternalStream = z2;
    }

    public void setSourceFileCRC(int i2) {
        this.sourceFileCRC = i2;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public void setPassword(char[] cArr) {
        this.password = cArr;
    }
}
