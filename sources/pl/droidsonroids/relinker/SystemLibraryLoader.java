package pl.droidsonroids.relinker;

import android.os.Build;
import pl.droidsonroids.relinker.ReLinker;

/* loaded from: classes9.dex */
final class SystemLibraryLoader implements ReLinker.LibraryLoader {
    @Override // pl.droidsonroids.relinker.ReLinker.LibraryLoader
    public void loadLibrary(String str) {
        System.loadLibrary(str);
    }

    @Override // pl.droidsonroids.relinker.ReLinker.LibraryLoader
    public void loadPath(String str) {
        System.load(str);
    }

    @Override // pl.droidsonroids.relinker.ReLinker.LibraryLoader
    public String mapLibraryName(String str) {
        return (str.startsWith("lib") && str.endsWith(".so")) ? str : System.mapLibraryName(str);
    }

    @Override // pl.droidsonroids.relinker.ReLinker.LibraryLoader
    public String[] supportedAbis() {
        String[] strArr = Build.SUPPORTED_ABIS;
        if (strArr.length > 0) {
            return strArr;
        }
        String str = Build.CPU_ABI2;
        return !TextUtils.isEmpty(str) ? new String[]{Build.CPU_ABI, str} : new String[]{Build.CPU_ABI};
    }

    @Override // pl.droidsonroids.relinker.ReLinker.LibraryLoader
    public String unmapLibraryName(String str) {
        return str.substring(3, str.length() - 3);
    }
}
