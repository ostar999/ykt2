package a.a.a.a.f;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public abstract class d {
    public static byte[] a(InputStream inputStream) throws IOException {
        try {
            byte[] bArr = new byte[inputStream.available()];
            inputStream.read(bArr);
            inputStream.close();
            return bArr;
        } catch (IOException e2) {
            e2.printStackTrace();
            return new byte[0];
        }
    }

    public static byte[] a(AssetManager assetManager, String str) {
        try {
            return a(assetManager.open(str));
        } catch (IOException e2) {
            e2.printStackTrace();
            return new byte[0];
        }
    }

    public static String a(Context context, int i2) {
        return new String(a(context.getResources().openRawResource(i2)));
    }
}
