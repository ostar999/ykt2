package org.apache.commons.compress.changes;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.compress.archivers.ArchiveEntry;

/* loaded from: classes9.dex */
public final class ChangeSet {
    private final Set<Change> changes = new LinkedHashSet();

    private void addAddition(Change change) {
        if (2 != change.type() || change.getInput() == null) {
            return;
        }
        if (!this.changes.isEmpty()) {
            Iterator<Change> it = this.changes.iterator();
            while (it.hasNext()) {
                Change next = it.next();
                if (next.type() == 2 && next.getEntry() != null && next.getEntry().equals(change.getEntry())) {
                    if (change.isReplaceMode()) {
                        it.remove();
                        this.changes.add(change);
                        return;
                    }
                    return;
                }
            }
        }
        this.changes.add(change);
    }

    private void addDeletion(Change change) {
        String name;
        if ((1 == change.type() || 4 == change.type()) && change.targetFile() != null) {
            String strTargetFile = change.targetFile();
            if (strTargetFile != null && !this.changes.isEmpty()) {
                Iterator<Change> it = this.changes.iterator();
                while (it.hasNext()) {
                    Change next = it.next();
                    if (next.type() == 2 && next.getEntry() != null && (name = next.getEntry().getName()) != null) {
                        if (1 == change.type() && strTargetFile.equals(name)) {
                            it.remove();
                        } else if (4 == change.type()) {
                            if (name.matches(strTargetFile + "/.*")) {
                                it.remove();
                            }
                        }
                    }
                }
            }
            this.changes.add(change);
        }
    }

    public void add(ArchiveEntry archiveEntry, InputStream inputStream) {
        add(archiveEntry, inputStream, true);
    }

    public void delete(String str) {
        addDeletion(new Change(str, 1));
    }

    public void deleteDir(String str) {
        addDeletion(new Change(str, 4));
    }

    public Set<Change> getChanges() {
        return new LinkedHashSet(this.changes);
    }

    public void add(ArchiveEntry archiveEntry, InputStream inputStream, boolean z2) {
        addAddition(new Change(archiveEntry, inputStream, z2));
    }
}
