package org.apache.commons.compress.archivers.tar;

import com.yikaobang.yixue.R2;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.utils.ArchiveUtils;

/* loaded from: classes9.dex */
public class TarArchiveEntry implements TarConstants, ArchiveEntry {
    public static final int DEFAULT_DIR_MODE = 16877;
    public static final int DEFAULT_FILE_MODE = 33188;
    private static final TarArchiveEntry[] EMPTY_TAR_ARCHIVE_ENTRIES = new TarArchiveEntry[0];
    public static final int MAX_NAMELEN = 31;
    public static final int MILLIS_PER_SECOND = 1000;
    private boolean checkSumOK;
    private int devMajor;
    private int devMinor;
    private final File file;
    private long groupId;
    private String groupName;
    private boolean isExtended;
    private byte linkFlag;
    private String linkName;
    private String magic;
    private long modTime;
    private int mode;
    private String name;
    private boolean paxGNUSparse;
    private boolean preserveLeadingSlashes;
    private long realSize;
    private long size;
    private boolean starSparse;
    private long userId;
    private String userName;
    private String version;

    private TarArchiveEntry() {
        this.name = "";
        this.userId = 0L;
        this.groupId = 0L;
        this.size = 0L;
        this.linkName = "";
        this.magic = "ustar\u0000";
        this.version = TarConstants.VERSION_POSIX;
        this.groupName = "";
        this.devMajor = 0;
        this.devMinor = 0;
        String property = System.getProperty("user.name", "");
        this.userName = property.length() > 31 ? property.substring(0, 31) : property;
        this.file = null;
    }

    private int evaluateType(byte[] bArr) {
        if (ArchiveUtils.matchAsciiBuffer(TarConstants.MAGIC_GNU, bArr, 257, 6)) {
            return 2;
        }
        if (ArchiveUtils.matchAsciiBuffer("ustar\u0000", bArr, 257, 6)) {
            return ArchiveUtils.matchAsciiBuffer(TarConstants.MAGIC_XSTAR, bArr, 508, 4) ? 4 : 3;
        }
        return 0;
    }

    private static String normalizeFileName(String str, boolean z2) {
        int iIndexOf;
        String lowerCase = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
        if (lowerCase != null) {
            if (lowerCase.startsWith("windows")) {
                if (str.length() > 2) {
                    char cCharAt = str.charAt(0);
                    if (str.charAt(1) == ':' && ((cCharAt >= 'a' && cCharAt <= 'z') || (cCharAt >= 'A' && cCharAt <= 'Z'))) {
                        str = str.substring(2);
                    }
                }
            } else if (lowerCase.contains("netware") && (iIndexOf = str.indexOf(58)) != -1) {
                str = str.substring(iIndexOf + 1);
            }
        }
        String strReplace = str.replace(File.separatorChar, '/');
        while (!z2 && strReplace.startsWith("/")) {
            strReplace = strReplace.substring(1);
        }
        return strReplace;
    }

    private int writeEntryHeaderField(long j2, byte[] bArr, int i2, int i3, boolean z2) {
        return (z2 || (j2 >= 0 && j2 < (1 << ((i3 + (-1)) * 3)))) ? TarUtils.formatLongOctalOrBinaryBytes(j2, bArr, i2, i3) : TarUtils.formatLongOctalBytes(0L, bArr, i2, i3);
    }

    public boolean equals(TarArchiveEntry tarArchiveEntry) {
        return getName().equals(tarArchiveEntry.getName());
    }

    public void fillGNUSparse0xData(Map<String, String> map) {
        this.paxGNUSparse = true;
        this.realSize = Integer.parseInt(map.get("GNU.sparse.size"));
        if (map.containsKey("GNU.sparse.name")) {
            this.name = map.get("GNU.sparse.name");
        }
    }

    public void fillGNUSparse1xData(Map<String, String> map) {
        this.paxGNUSparse = true;
        this.realSize = Integer.parseInt(map.get("GNU.sparse.realsize"));
        this.name = map.get("GNU.sparse.name");
    }

    public void fillStarSparseData(Map<String, String> map) {
        this.starSparse = true;
        if (map.containsKey("SCHILY.realsize")) {
            this.realSize = Long.parseLong(map.get("SCHILY.realsize"));
        }
    }

    public int getDevMajor() {
        return this.devMajor;
    }

    public int getDevMinor() {
        return this.devMinor;
    }

    public TarArchiveEntry[] getDirectoryEntries() {
        File file = this.file;
        if (file == null || !file.isDirectory()) {
            return EMPTY_TAR_ARCHIVE_ENTRIES;
        }
        String[] list = this.file.list();
        if (list == null) {
            return EMPTY_TAR_ARCHIVE_ENTRIES;
        }
        int length = list.length;
        TarArchiveEntry[] tarArchiveEntryArr = new TarArchiveEntry[length];
        for (int i2 = 0; i2 < length; i2++) {
            tarArchiveEntryArr[i2] = new TarArchiveEntry(new File(this.file, list[i2]));
        }
        return tarArchiveEntryArr;
    }

    public File getFile() {
        return this.file;
    }

    @Deprecated
    public int getGroupId() {
        return (int) (this.groupId & (-1));
    }

    public String getGroupName() {
        return this.groupName;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public Date getLastModifiedDate() {
        return getModTime();
    }

    public String getLinkName() {
        return this.linkName;
    }

    public long getLongGroupId() {
        return this.groupId;
    }

    public long getLongUserId() {
        return this.userId;
    }

    public Date getModTime() {
        return new Date(this.modTime * 1000);
    }

    public int getMode() {
        return this.mode;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public String getName() {
        return this.name;
    }

    public long getRealSize() {
        return this.realSize;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public long getSize() {
        return this.size;
    }

    @Deprecated
    public int getUserId() {
        return (int) (this.userId & (-1));
    }

    public String getUserName() {
        return this.userName;
    }

    public int hashCode() {
        return getName().hashCode();
    }

    public boolean isBlockDevice() {
        return this.linkFlag == 52;
    }

    public boolean isCharacterDevice() {
        return this.linkFlag == 51;
    }

    public boolean isCheckSumOK() {
        return this.checkSumOK;
    }

    public boolean isDescendent(TarArchiveEntry tarArchiveEntry) {
        return tarArchiveEntry.getName().startsWith(getName());
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public boolean isDirectory() {
        File file = this.file;
        if (file != null) {
            return file.isDirectory();
        }
        if (this.linkFlag == 53) {
            return true;
        }
        return (isPaxHeader() || isGlobalPaxHeader() || !getName().endsWith("/")) ? false : true;
    }

    public boolean isExtended() {
        return this.isExtended;
    }

    public boolean isFIFO() {
        return this.linkFlag == 54;
    }

    public boolean isFile() {
        File file = this.file;
        if (file != null) {
            return file.isFile();
        }
        byte b3 = this.linkFlag;
        if (b3 == 0 || b3 == 48) {
            return true;
        }
        return !getName().endsWith("/");
    }

    public boolean isGNULongLinkEntry() {
        return this.linkFlag == 75;
    }

    public boolean isGNULongNameEntry() {
        return this.linkFlag == 76;
    }

    public boolean isGNUSparse() {
        return isOldGNUSparse() || isPaxGNUSparse();
    }

    public boolean isGlobalPaxHeader() {
        return this.linkFlag == 103;
    }

    public boolean isLink() {
        return this.linkFlag == 49;
    }

    public boolean isOldGNUSparse() {
        return this.linkFlag == 83;
    }

    public boolean isPaxGNUSparse() {
        return this.paxGNUSparse;
    }

    public boolean isPaxHeader() {
        byte b3 = this.linkFlag;
        return b3 == 120 || b3 == 88;
    }

    public boolean isSparse() {
        return isGNUSparse() || isStarSparse();
    }

    public boolean isStarSparse() {
        return this.starSparse;
    }

    public boolean isSymbolicLink() {
        return this.linkFlag == 50;
    }

    public void parseTarHeader(byte[] bArr) {
        try {
            try {
                parseTarHeader(bArr, TarUtils.DEFAULT_ENCODING);
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        } catch (IOException unused) {
            parseTarHeader(bArr, TarUtils.DEFAULT_ENCODING, true);
        }
    }

    public void setDevMajor(int i2) {
        if (i2 >= 0) {
            this.devMajor = i2;
            return;
        }
        throw new IllegalArgumentException("Major device number is out of range: " + i2);
    }

    public void setDevMinor(int i2) {
        if (i2 >= 0) {
            this.devMinor = i2;
            return;
        }
        throw new IllegalArgumentException("Minor device number is out of range: " + i2);
    }

    public void setGroupId(int i2) {
        setGroupId(i2);
    }

    public void setGroupName(String str) {
        this.groupName = str;
    }

    public void setIds(int i2, int i3) {
        setUserId(i2);
        setGroupId(i3);
    }

    public void setLinkName(String str) {
        this.linkName = str;
    }

    public void setModTime(long j2) {
        this.modTime = j2 / 1000;
    }

    public void setMode(int i2) {
        this.mode = i2;
    }

    public void setName(String str) {
        this.name = normalizeFileName(str, this.preserveLeadingSlashes);
    }

    public void setNames(String str, String str2) {
        setUserName(str);
        setGroupName(str2);
    }

    public void setSize(long j2) {
        if (j2 >= 0) {
            this.size = j2;
            return;
        }
        throw new IllegalArgumentException("Size is out of range: " + j2);
    }

    public void setUserId(int i2) {
        setUserId(i2);
    }

    public void setUserName(String str) {
        this.userName = str;
    }

    public void writeEntryHeader(byte[] bArr) {
        try {
            try {
                writeEntryHeader(bArr, TarUtils.DEFAULT_ENCODING, false);
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        } catch (IOException unused) {
            writeEntryHeader(bArr, TarUtils.FALLBACK_ENCODING, false);
        }
    }

    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return equals((TarArchiveEntry) obj);
    }

    public void setGroupId(long j2) {
        this.groupId = j2;
    }

    public void setModTime(Date date) {
        this.modTime = date.getTime() / 1000;
    }

    public void setUserId(long j2) {
        this.userId = j2;
    }

    public void parseTarHeader(byte[] bArr, ZipEncoding zipEncoding) throws IOException {
        parseTarHeader(bArr, zipEncoding, false);
    }

    public void writeEntryHeader(byte[] bArr, ZipEncoding zipEncoding, boolean z2) throws IOException {
        int iWriteEntryHeaderField = writeEntryHeaderField(this.modTime, bArr, writeEntryHeaderField(this.size, bArr, writeEntryHeaderField(this.groupId, bArr, writeEntryHeaderField(this.userId, bArr, writeEntryHeaderField(this.mode, bArr, TarUtils.formatNameBytes(this.name, bArr, 0, 100, zipEncoding), 8, z2), 8, z2), 8, z2), 12, z2), 12, z2);
        int i2 = 0;
        int i3 = iWriteEntryHeaderField;
        while (i2 < 8) {
            bArr[i3] = 32;
            i2++;
            i3++;
        }
        bArr[i3] = this.linkFlag;
        for (int iWriteEntryHeaderField2 = writeEntryHeaderField(this.devMinor, bArr, writeEntryHeaderField(this.devMajor, bArr, TarUtils.formatNameBytes(this.groupName, bArr, TarUtils.formatNameBytes(this.userName, bArr, TarUtils.formatNameBytes(this.version, bArr, TarUtils.formatNameBytes(this.magic, bArr, TarUtils.formatNameBytes(this.linkName, bArr, i3 + 1, 100, zipEncoding), 6), 2), 32, zipEncoding), 32, zipEncoding), 8, z2), 8, z2); iWriteEntryHeaderField2 < bArr.length; iWriteEntryHeaderField2++) {
            bArr[iWriteEntryHeaderField2] = 0;
        }
        TarUtils.formatCheckSumOctalBytes(TarUtils.computeCheckSum(bArr), bArr, iWriteEntryHeaderField, 8);
    }

    private void parseTarHeader(byte[] bArr, ZipEncoding zipEncoding, boolean z2) throws IOException {
        this.name = z2 ? TarUtils.parseName(bArr, 0, 100) : TarUtils.parseName(bArr, 0, 100, zipEncoding);
        this.mode = (int) TarUtils.parseOctalOrBinary(bArr, 100, 8);
        this.userId = (int) TarUtils.parseOctalOrBinary(bArr, 108, 8);
        this.groupId = (int) TarUtils.parseOctalOrBinary(bArr, 116, 8);
        this.size = TarUtils.parseOctalOrBinary(bArr, 124, 12);
        this.modTime = TarUtils.parseOctalOrBinary(bArr, 136, 12);
        this.checkSumOK = TarUtils.verifyCheckSum(bArr);
        this.linkFlag = bArr[156];
        this.linkName = z2 ? TarUtils.parseName(bArr, 157, 100) : TarUtils.parseName(bArr, 157, 100, zipEncoding);
        this.magic = TarUtils.parseName(bArr, 257, 6);
        this.version = TarUtils.parseName(bArr, 263, 2);
        this.userName = z2 ? TarUtils.parseName(bArr, R2.attr.actionViewClass, 32) : TarUtils.parseName(bArr, R2.attr.actionViewClass, 32, zipEncoding);
        this.groupName = z2 ? TarUtils.parseName(bArr, R2.attr.all_course_subject_bg1_end_color, 32) : TarUtils.parseName(bArr, R2.attr.all_course_subject_bg1_end_color, 32, zipEncoding);
        this.devMajor = (int) TarUtils.parseOctalOrBinary(bArr, R2.attr.appBarLayoutStyle, 8);
        this.devMinor = (int) TarUtils.parseOctalOrBinary(bArr, R2.attr.app_update_top_img, 8);
        int iEvaluateType = evaluateType(bArr);
        if (iEvaluateType == 2) {
            this.isExtended = TarUtils.parseBoolean(bArr, R2.attr.beizi_bav_color);
            this.realSize = TarUtils.parseOctal(bArr, R2.attr.beizi_bav_stroke_width, 12);
            return;
        }
        if (iEvaluateType != 4) {
            String name = z2 ? TarUtils.parseName(bArr, R2.attr.arcBlockAngle, 155) : TarUtils.parseName(bArr, R2.attr.arcBlockAngle, 155, zipEncoding);
            if (isDirectory() && !this.name.endsWith("/")) {
                this.name += "/";
            }
            if (name.length() > 0) {
                this.name = name + "/" + this.name;
                return;
            }
            return;
        }
        String name2 = z2 ? TarUtils.parseName(bArr, R2.attr.arcBlockAngle, 131) : TarUtils.parseName(bArr, R2.attr.arcBlockAngle, 131, zipEncoding);
        if (name2.length() > 0) {
            this.name = name2 + "/" + this.name;
        }
    }

    public TarArchiveEntry(String str) {
        this(str, false);
    }

    public TarArchiveEntry(String str, boolean z2) {
        this();
        this.preserveLeadingSlashes = z2;
        String strNormalizeFileName = normalizeFileName(str, z2);
        boolean zEndsWith = strNormalizeFileName.endsWith("/");
        this.name = strNormalizeFileName;
        this.mode = zEndsWith ? 16877 : DEFAULT_FILE_MODE;
        this.linkFlag = zEndsWith ? TarConstants.LF_DIR : TarConstants.LF_NORMAL;
        this.modTime = new Date().getTime() / 1000;
        this.userName = "";
    }

    public TarArchiveEntry(String str, byte b3) {
        this(str, b3, false);
    }

    public TarArchiveEntry(String str, byte b3, boolean z2) {
        this(str, z2);
        this.linkFlag = b3;
        if (b3 == 76) {
            this.magic = TarConstants.MAGIC_GNU;
            this.version = TarConstants.VERSION_GNU_SPACE;
        }
    }

    public TarArchiveEntry(File file) {
        this(file, file.getPath());
    }

    public TarArchiveEntry(File file, String str) {
        this.name = "";
        this.userId = 0L;
        this.groupId = 0L;
        this.size = 0L;
        this.linkName = "";
        this.magic = "ustar\u0000";
        this.version = TarConstants.VERSION_POSIX;
        this.groupName = "";
        this.devMajor = 0;
        this.devMinor = 0;
        String strNormalizeFileName = normalizeFileName(str, false);
        this.file = file;
        if (file.isDirectory()) {
            this.mode = 16877;
            this.linkFlag = TarConstants.LF_DIR;
            int length = strNormalizeFileName.length();
            if (length != 0 && strNormalizeFileName.charAt(length - 1) == '/') {
                this.name = strNormalizeFileName;
            } else {
                this.name = strNormalizeFileName + "/";
            }
        } else {
            this.mode = DEFAULT_FILE_MODE;
            this.linkFlag = TarConstants.LF_NORMAL;
            this.size = file.length();
            this.name = strNormalizeFileName;
        }
        this.modTime = file.lastModified() / 1000;
        this.userName = "";
    }

    public TarArchiveEntry(byte[] bArr) {
        this();
        parseTarHeader(bArr);
    }

    public TarArchiveEntry(byte[] bArr, ZipEncoding zipEncoding) throws IOException {
        this();
        parseTarHeader(bArr, zipEncoding);
    }
}
