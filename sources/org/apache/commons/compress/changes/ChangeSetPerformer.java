package org.apache.commons.compress.changes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;

/* loaded from: classes9.dex */
public class ChangeSetPerformer {
    private final Set<Change> changes;

    public interface ArchiveEntryIterator {
        InputStream getInputStream() throws IOException;

        boolean hasNext() throws IOException;

        ArchiveEntry next();
    }

    public static class ArchiveInputStreamIterator implements ArchiveEntryIterator {
        private final ArchiveInputStream in;
        private ArchiveEntry next;

        public ArchiveInputStreamIterator(ArchiveInputStream archiveInputStream) {
            this.in = archiveInputStream;
        }

        @Override // org.apache.commons.compress.changes.ChangeSetPerformer.ArchiveEntryIterator
        public InputStream getInputStream() {
            return this.in;
        }

        @Override // org.apache.commons.compress.changes.ChangeSetPerformer.ArchiveEntryIterator
        public boolean hasNext() throws IOException {
            ArchiveEntry nextEntry = this.in.getNextEntry();
            this.next = nextEntry;
            return nextEntry != null;
        }

        @Override // org.apache.commons.compress.changes.ChangeSetPerformer.ArchiveEntryIterator
        public ArchiveEntry next() {
            return this.next;
        }
    }

    public static class ZipFileIterator implements ArchiveEntryIterator {
        private ZipArchiveEntry current;
        private final ZipFile in;
        private final Enumeration<ZipArchiveEntry> nestedEnum;

        public ZipFileIterator(ZipFile zipFile) {
            this.in = zipFile;
            this.nestedEnum = zipFile.getEntriesInPhysicalOrder();
        }

        @Override // org.apache.commons.compress.changes.ChangeSetPerformer.ArchiveEntryIterator
        public InputStream getInputStream() throws IOException {
            return this.in.getInputStream(this.current);
        }

        @Override // org.apache.commons.compress.changes.ChangeSetPerformer.ArchiveEntryIterator
        public boolean hasNext() {
            return this.nestedEnum.hasMoreElements();
        }

        @Override // org.apache.commons.compress.changes.ChangeSetPerformer.ArchiveEntryIterator
        public ArchiveEntry next() {
            ZipArchiveEntry zipArchiveEntryNextElement = this.nestedEnum.nextElement();
            this.current = zipArchiveEntryNextElement;
            return zipArchiveEntryNextElement;
        }
    }

    public ChangeSetPerformer(ChangeSet changeSet) {
        this.changes = changeSet.getChanges();
    }

    private void copyStream(InputStream inputStream, ArchiveOutputStream archiveOutputStream, ArchiveEntry archiveEntry) throws IOException {
        archiveOutputStream.putArchiveEntry(archiveEntry);
        IOUtils.copy(inputStream, archiveOutputStream);
        archiveOutputStream.closeArchiveEntry();
    }

    private boolean isDeletedLater(Set<Change> set, ArchiveEntry archiveEntry) {
        String name = archiveEntry.getName();
        if (set.isEmpty()) {
            return false;
        }
        for (Change change : set) {
            int iType = change.type();
            String strTargetFile = change.targetFile();
            if (iType == 1 && name.equals(strTargetFile)) {
                return true;
            }
            if (iType == 4) {
                if (name.startsWith(strTargetFile + "/")) {
                    return true;
                }
            }
        }
        return false;
    }

    public ChangeSetResults perform(ArchiveInputStream archiveInputStream, ArchiveOutputStream archiveOutputStream) throws IOException {
        return perform(new ArchiveInputStreamIterator(archiveInputStream), archiveOutputStream);
    }

    public ChangeSetResults perform(ZipFile zipFile, ArchiveOutputStream archiveOutputStream) throws IOException {
        return perform(new ZipFileIterator(zipFile), archiveOutputStream);
    }

    private ChangeSetResults perform(ArchiveEntryIterator archiveEntryIterator, ArchiveOutputStream archiveOutputStream) throws IOException {
        boolean z2;
        ChangeSetResults changeSetResults = new ChangeSetResults();
        LinkedHashSet linkedHashSet = new LinkedHashSet(this.changes);
        Iterator<Change> it = linkedHashSet.iterator();
        while (it.hasNext()) {
            Change next = it.next();
            if (next.type() == 2 && next.isReplaceMode()) {
                copyStream(next.getInput(), archiveOutputStream, next.getEntry());
                it.remove();
                changeSetResults.addedFromChangeSet(next.getEntry().getName());
            }
        }
        while (archiveEntryIterator.hasNext()) {
            ArchiveEntry next2 = archiveEntryIterator.next();
            Iterator<Change> it2 = linkedHashSet.iterator();
            while (true) {
                z2 = true;
                if (!it2.hasNext()) {
                    break;
                }
                Change next3 = it2.next();
                int iType = next3.type();
                String name = next2.getName();
                if (iType != 1 || name == null) {
                    if (iType == 4 && name != null) {
                        if (name.startsWith(next3.targetFile() + "/")) {
                            changeSetResults.deleted(name);
                            break;
                        }
                    }
                } else if (name.equals(next3.targetFile())) {
                    it2.remove();
                    changeSetResults.deleted(name);
                    break;
                }
            }
            z2 = false;
            if (z2 && !isDeletedLater(linkedHashSet, next2) && !changeSetResults.hasBeenAdded(next2.getName())) {
                copyStream(archiveEntryIterator.getInputStream(), archiveOutputStream, next2);
                changeSetResults.addedFromStream(next2.getName());
            }
        }
        Iterator<Change> it3 = linkedHashSet.iterator();
        while (it3.hasNext()) {
            Change next4 = it3.next();
            if (next4.type() == 2 && !next4.isReplaceMode() && !changeSetResults.hasBeenAdded(next4.getEntry().getName())) {
                copyStream(next4.getInput(), archiveOutputStream, next4.getEntry());
                it3.remove();
                changeSetResults.addedFromChangeSet(next4.getEntry().getName());
            }
        }
        archiveOutputStream.finish();
        return changeSetResults;
    }
}
