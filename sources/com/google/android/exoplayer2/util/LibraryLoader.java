package com.google.android.exoplayer2.util;

import java.util.Arrays;

/* loaded from: classes3.dex */
public final class LibraryLoader {
    private static final String TAG = "LibraryLoader";
    private boolean isAvailable;
    private boolean loadAttempted;
    private String[] nativeLibraries;

    public LibraryLoader(String... strArr) {
        this.nativeLibraries = strArr;
    }

    public synchronized boolean isAvailable() {
        if (this.loadAttempted) {
            return this.isAvailable;
        }
        this.loadAttempted = true;
        try {
            for (String str : this.nativeLibraries) {
                System.loadLibrary(str);
            }
            this.isAvailable = true;
        } catch (UnsatisfiedLinkError unused) {
            String strValueOf = String.valueOf(Arrays.toString(this.nativeLibraries));
            Log.w(TAG, strValueOf.length() != 0 ? "Failed to load ".concat(strValueOf) : new String("Failed to load "));
        }
        return this.isAvailable;
    }

    public synchronized void setLibraries(String... strArr) {
        Assertions.checkState(!this.loadAttempted, "Cannot set libraries after loading");
        this.nativeLibraries = strArr;
    }
}
