package org.apache.commons.compress.archivers.sevenz;

import com.heytap.mcssdk.constant.a;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TimeZone;
import org.apache.commons.compress.archivers.ArchiveEntry;

/* loaded from: classes9.dex */
public class SevenZArchiveEntry implements ArchiveEntry {
    private long accessDate;
    private long compressedCrc;
    private long compressedSize;
    private Iterable<? extends SevenZMethodConfiguration> contentMethods;
    private long crc;
    private long creationDate;
    private boolean hasAccessDate;
    private boolean hasCrc;
    private boolean hasCreationDate;
    private boolean hasLastModifiedDate;
    private boolean hasStream;
    private boolean hasWindowsAttributes;
    private boolean isAntiItem;
    private boolean isDirectory;
    private long lastModifiedDate;
    private String name;
    private long size;
    private int windowsAttributes;

    public static long javaTimeToNtfsTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        calendar.set(1601, 0, 1, 0, 0, 0);
        calendar.set(14, 0);
        return (date.getTime() - calendar.getTimeInMillis()) * 1000 * 10;
    }

    public static Date ntfsTimeToJavaTime(long j2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        calendar.set(1601, 0, 1, 0, 0, 0);
        calendar.set(14, 0);
        return new Date(calendar.getTimeInMillis() + (j2 / a.f7153q));
    }

    public Date getAccessDate() {
        if (this.hasAccessDate) {
            return ntfsTimeToJavaTime(this.accessDate);
        }
        throw new UnsupportedOperationException("The entry doesn't have this timestamp");
    }

    @Deprecated
    public int getCompressedCrc() {
        return (int) this.compressedCrc;
    }

    public long getCompressedCrcValue() {
        return this.compressedCrc;
    }

    public long getCompressedSize() {
        return this.compressedSize;
    }

    public Iterable<? extends SevenZMethodConfiguration> getContentMethods() {
        return this.contentMethods;
    }

    @Deprecated
    public int getCrc() {
        return (int) this.crc;
    }

    public long getCrcValue() {
        return this.crc;
    }

    public Date getCreationDate() {
        if (this.hasCreationDate) {
            return ntfsTimeToJavaTime(this.creationDate);
        }
        throw new UnsupportedOperationException("The entry doesn't have this timestamp");
    }

    public boolean getHasAccessDate() {
        return this.hasAccessDate;
    }

    public boolean getHasCrc() {
        return this.hasCrc;
    }

    public boolean getHasCreationDate() {
        return this.hasCreationDate;
    }

    public boolean getHasLastModifiedDate() {
        return this.hasLastModifiedDate;
    }

    public boolean getHasWindowsAttributes() {
        return this.hasWindowsAttributes;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public Date getLastModifiedDate() {
        if (this.hasLastModifiedDate) {
            return ntfsTimeToJavaTime(this.lastModifiedDate);
        }
        throw new UnsupportedOperationException("The entry doesn't have this timestamp");
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public String getName() {
        return this.name;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public long getSize() {
        return this.size;
    }

    public int getWindowsAttributes() {
        return this.windowsAttributes;
    }

    public boolean hasStream() {
        return this.hasStream;
    }

    public boolean isAntiItem() {
        return this.isAntiItem;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public boolean isDirectory() {
        return this.isDirectory;
    }

    public void setAccessDate(long j2) {
        this.accessDate = j2;
    }

    public void setAntiItem(boolean z2) {
        this.isAntiItem = z2;
    }

    @Deprecated
    public void setCompressedCrc(int i2) {
        this.compressedCrc = i2;
    }

    public void setCompressedCrcValue(long j2) {
        this.compressedCrc = j2;
    }

    public void setCompressedSize(long j2) {
        this.compressedSize = j2;
    }

    public void setContentMethods(Iterable<? extends SevenZMethodConfiguration> iterable) {
        if (iterable == null) {
            this.contentMethods = null;
            return;
        }
        LinkedList linkedList = new LinkedList();
        Iterator<? extends SevenZMethodConfiguration> it = iterable.iterator();
        while (it.hasNext()) {
            linkedList.addLast(it.next());
        }
        this.contentMethods = Collections.unmodifiableList(linkedList);
    }

    @Deprecated
    public void setCrc(int i2) {
        this.crc = i2;
    }

    public void setCrcValue(long j2) {
        this.crc = j2;
    }

    public void setCreationDate(long j2) {
        this.creationDate = j2;
    }

    public void setDirectory(boolean z2) {
        this.isDirectory = z2;
    }

    public void setHasAccessDate(boolean z2) {
        this.hasAccessDate = z2;
    }

    public void setHasCrc(boolean z2) {
        this.hasCrc = z2;
    }

    public void setHasCreationDate(boolean z2) {
        this.hasCreationDate = z2;
    }

    public void setHasLastModifiedDate(boolean z2) {
        this.hasLastModifiedDate = z2;
    }

    public void setHasStream(boolean z2) {
        this.hasStream = z2;
    }

    public void setHasWindowsAttributes(boolean z2) {
        this.hasWindowsAttributes = z2;
    }

    public void setLastModifiedDate(long j2) {
        this.lastModifiedDate = j2;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setSize(long j2) {
        this.size = j2;
    }

    public void setWindowsAttributes(int i2) {
        this.windowsAttributes = i2;
    }

    public void setAccessDate(Date date) {
        boolean z2 = date != null;
        this.hasAccessDate = z2;
        if (z2) {
            this.accessDate = javaTimeToNtfsTime(date);
        }
    }

    public void setCreationDate(Date date) {
        boolean z2 = date != null;
        this.hasCreationDate = z2;
        if (z2) {
            this.creationDate = javaTimeToNtfsTime(date);
        }
    }

    public void setLastModifiedDate(Date date) {
        boolean z2 = date != null;
        this.hasLastModifiedDate = z2;
        if (z2) {
            this.lastModifiedDate = javaTimeToNtfsTime(date);
        }
    }
}
