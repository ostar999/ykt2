package org.slf4j.helpers;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.slf4j.Marker;

/* loaded from: classes9.dex */
public class BasicMarker implements Marker {
    private static String CLOSE = " ]";
    private static String OPEN = "[ ";
    private static String SEP = ", ";
    private static final long serialVersionUID = 1803952589649545191L;
    private final String name;
    private List<Marker> referenceList;

    public BasicMarker(String str) {
        if (str == null) {
            throw new IllegalArgumentException("A marker name cannot be null");
        }
        this.name = str;
    }

    @Override // org.slf4j.Marker
    public synchronized void add(Marker marker) {
        if (marker == null) {
            throw new IllegalArgumentException("A null value cannot be added to a Marker as reference.");
        }
        if (contains(marker)) {
            return;
        }
        if (marker.contains(this)) {
            return;
        }
        if (this.referenceList == null) {
            this.referenceList = new Vector();
        }
        this.referenceList.add(marker);
    }

    @Override // org.slf4j.Marker
    public boolean contains(Marker marker) {
        if (marker == null) {
            throw new IllegalArgumentException("Other cannot be null");
        }
        if (equals(marker)) {
            return true;
        }
        if (!hasReferences()) {
            return false;
        }
        Iterator<Marker> it = this.referenceList.iterator();
        while (it.hasNext()) {
            if (it.next().contains(marker)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.slf4j.Marker
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof Marker)) {
            return this.name.equals(((Marker) obj).getName());
        }
        return false;
    }

    @Override // org.slf4j.Marker
    public String getName() {
        return this.name;
    }

    @Override // org.slf4j.Marker
    public boolean hasChildren() {
        return hasReferences();
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x000d  */
    @Override // org.slf4j.Marker
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean hasReferences() {
        /*
            r1 = this;
            monitor-enter(r1)
            java.util.List<org.slf4j.Marker> r0 = r1.referenceList     // Catch: java.lang.Throwable -> L10
            if (r0 == 0) goto Ld
            int r0 = r0.size()     // Catch: java.lang.Throwable -> L10
            if (r0 <= 0) goto Ld
            r0 = 1
            goto Le
        Ld:
            r0 = 0
        Le:
            monitor-exit(r1)
            return r0
        L10:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.slf4j.helpers.BasicMarker.hasReferences():boolean");
    }

    @Override // org.slf4j.Marker
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override // org.slf4j.Marker
    public synchronized Iterator<Marker> iterator() {
        List<Marker> list = this.referenceList;
        if (list != null) {
            return list.iterator();
        }
        return Collections.emptyList().iterator();
    }

    @Override // org.slf4j.Marker
    public synchronized boolean remove(Marker marker) {
        List<Marker> list = this.referenceList;
        if (list == null) {
            return false;
        }
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (marker.equals(this.referenceList.get(i2))) {
                this.referenceList.remove(i2);
                return true;
            }
        }
        return false;
    }

    public String toString() {
        if (!hasReferences()) {
            return getName();
        }
        Iterator<Marker> it = iterator();
        StringBuilder sb = new StringBuilder(getName());
        sb.append(' ');
        sb.append(OPEN);
        while (it.hasNext()) {
            sb.append(it.next().getName());
            if (it.hasNext()) {
                sb.append(SEP);
            }
        }
        sb.append(CLOSE);
        return sb.toString();
    }

    @Override // org.slf4j.Marker
    public boolean contains(String str) {
        if (str != null) {
            if (this.name.equals(str)) {
                return true;
            }
            if (!hasReferences()) {
                return false;
            }
            Iterator<Marker> it = this.referenceList.iterator();
            while (it.hasNext()) {
                if (it.next().contains(str)) {
                    return true;
                }
            }
            return false;
        }
        throw new IllegalArgumentException("Other cannot be null");
    }
}
