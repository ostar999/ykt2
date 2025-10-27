package kotlin;

import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes8.dex */
public final /* synthetic */ class a {
    public static /* synthetic */ int a(int i2, int i3) {
        return (int) ((i2 & InternalZipConstants.ZIP_64_LIMIT) % (i3 & InternalZipConstants.ZIP_64_LIMIT));
    }
}
