package org.wrtca.util;

import org.wrtca.api.NativeLibraryLoader;
import org.wrtca.log.Logging;

/* loaded from: classes9.dex */
public class NativeLibrary {
    private static String TAG = "NativeLibrary";
    private static boolean libraryLoaded = false;
    private static Object lock = new Object();

    public static class DefaultLoader implements NativeLibraryLoader {
        @Override // org.wrtca.api.NativeLibraryLoader
        public boolean load(String str) {
            Logging.d(NativeLibrary.TAG, "Loading library: " + str);
            try {
                System.loadLibrary(str);
                return true;
            } catch (UnsatisfiedLinkError e2) {
                Logging.e(NativeLibrary.TAG, "Failed to load native library: " + str, e2);
                return false;
            }
        }
    }

    public static boolean initialize(NativeLibraryLoader nativeLibraryLoader) {
        synchronized (lock) {
            if (libraryLoaded) {
                Logging.d(TAG, "Native library has already been loaded.");
                return true;
            }
            Logging.d(TAG, "Loading native library.");
            boolean zLoad = nativeLibraryLoader.load("rtcengine_so");
            libraryLoaded = zLoad;
            return zLoad;
        }
    }

    public static boolean isLoaded() {
        boolean z2;
        synchronized (lock) {
            z2 = libraryLoaded;
        }
        return z2;
    }
}
